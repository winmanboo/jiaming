package com.deepcode.jiaming.uaa.authorization;

import cn.hutool.core.util.IdUtil;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.Assert;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * 自定义 JdbcOAuth2AuthorizationService。主要是添加了自定义字段，生成自定义 token 保存到数据库中，在返回访问令牌时将自定义 token 返回
 *
 * @author winmanboo
 * @date 2023/7/20 20:47
 */
public class CustomJdbcOAuth2AuthorizationService extends JdbcOAuth2AuthorizationService {

    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "registered_client_id, "
            + "principal_name, "
            + "authorization_grant_type, "
            + "authorized_scopes, "
            + "attributes, "
            + "state, "
            + "authorization_code_value, "
            + "authorization_code_issued_at, "
            + "authorization_code_expires_at,"
            + "authorization_code_metadata,"
            + "access_token_value,"
            + "access_token_issued_at,"
            + "access_token_expires_at,"
            + "access_token_metadata,"
            + "access_token_type,"
            + "access_token_scopes,"
            + "oidc_id_token_value,"
            + "oidc_id_token_issued_at,"
            + "oidc_id_token_expires_at,"
            + "oidc_id_token_metadata,"
            + "refresh_token_value,"
            + "refresh_token_issued_at,"
            + "refresh_token_expires_at,"
            + "refresh_token_metadata,"
            + "token";
    // @formatter:on

    private static final String TABLE_NAME = "oauth2_authorization";
    private static final String PK_FILTER = "id = ?";

    // @formatter:off
    private static final String SAVE_AUTHORIZATION_SQL = "INSERT INTO " + TABLE_NAME
            + " (" + COLUMN_NAMES + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    // @formatter:on

    // @formatter:off
    private static final String UPDATE_AUTHORIZATION_SQL = "UPDATE " + TABLE_NAME
            + " SET registered_client_id = ?, principal_name = ?, authorization_grant_type = ?, authorized_scopes = ?, attributes = ?, state = ?,"
            + " authorization_code_value = ?, authorization_code_issued_at = ?, authorization_code_expires_at = ?, authorization_code_metadata = ?,"
            + " access_token_value = ?, access_token_issued_at = ?, access_token_expires_at = ?, access_token_metadata = ?, access_token_type = ?, access_token_scopes = ?,"
            + " oidc_id_token_value = ?, oidc_id_token_issued_at = ?, oidc_id_token_expires_at = ?, oidc_id_token_metadata = ?,"
            + " refresh_token_value = ?, refresh_token_issued_at = ?, refresh_token_expires_at = ?, refresh_token_metadata = ?, token = ?"
            + " WHERE " + PK_FILTER;
    // @formatter:on

    public CustomJdbcOAuth2AuthorizationService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
        super(jdbcOperations, registeredClientRepository);
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        OAuth2Authorization existingAuthorization = findById(authorization.getId());
        if (existingAuthorization == null) {
            internalInsertAuthorization(authorization);
        } else {
            internalUpdateAuthorization(authorization);
        }
    }

    private void internalUpdateAuthorization(OAuth2Authorization authorization) {
        List<SqlParameterValue> parameters = getAuthorizationParametersMapper().apply(authorization);
        SqlParameterValue id = parameters.remove(0);
        parameters.add(id);
        try (LobCreator lobCreator = getLobHandler().getLobCreator()) {
            PreparedStatementSetter pss = new LobCreatorArgumentPreparedStatementSetter(lobCreator,
                    parameters.toArray());
            getJdbcOperations().update(UPDATE_AUTHORIZATION_SQL, pss);
        }
    }

    private void internalInsertAuthorization(OAuth2Authorization authorization) {
        List<SqlParameterValue> parameters = getAuthorizationParametersMapper().apply(authorization);
        try (LobCreator lobCreator = getLobHandler().getLobCreator()) {
            PreparedStatementSetter pss = new LobCreatorArgumentPreparedStatementSetter(lobCreator,
                    parameters.toArray());
            getJdbcOperations().update(SAVE_AUTHORIZATION_SQL, pss);
        }
    }

    private static final class LobCreatorArgumentPreparedStatementSetter extends ArgumentPreparedStatementSetter {
        private final LobCreator lobCreator;

        private LobCreatorArgumentPreparedStatementSetter(LobCreator lobCreator, Object[] args) {
            super(args);
            this.lobCreator = lobCreator;
        }

        @Override
        protected void doSetValue(PreparedStatement ps, int parameterPosition, Object argValue) throws SQLException {
            if (argValue instanceof SqlParameterValue paramValue) {
                if (paramValue.getSqlType() == Types.BLOB) {
                    if (paramValue.getValue() != null) {
                        Assert.isInstanceOf(byte[].class, paramValue.getValue(),
                                "Value of blob parameter must be byte[]");
                    }
                    byte[] valueBytes = (byte[]) paramValue.getValue();
                    this.lobCreator.setBlobAsBytes(ps, parameterPosition, valueBytes);
                    return;
                }
                if (paramValue.getSqlType() == Types.CLOB) {
                    if (paramValue.getValue() != null) {
                        Assert.isInstanceOf(String.class, paramValue.getValue(),
                                "Value of clob parameter must be String");
                    }
                    String valueString = (String) paramValue.getValue();
                    this.lobCreator.setClobAsString(ps, parameterPosition, valueString);
                    return;
                }
            }
            super.doSetValue(ps, parameterPosition, argValue);
        }

    }

    public static class CustomOAuth2AuthorizationParametersMapper extends JdbcOAuth2AuthorizationService.OAuth2AuthorizationParametersMapper {
        @Override
        public List<SqlParameterValue> apply(OAuth2Authorization authorization) {
            List<SqlParameterValue> parameters = super.apply(authorization);
            // 这里添加的顺序要与数据库的字段的顺序一一对应
            parameters.add(new SqlParameterValue(Types.VARCHAR, generateToken()));
            return parameters;
        }

        /**
         * 生成自定义令牌
         *
         * @return {@link String}
         */
        protected String generateToken() {
            return IdUtil.fastSimpleUUID();
        }
    }

}
