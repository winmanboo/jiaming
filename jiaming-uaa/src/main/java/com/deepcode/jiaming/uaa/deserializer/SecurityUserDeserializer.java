package com.deepcode.jiaming.uaa.deserializer;

import com.deepcode.jiaming.uaa.entity.SecurityUser;
import com.deepcode.jiaming.uaa.utils.JsonNodeUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

/**
 * @author winmanboo
 * @date 2023/6/2 19:24
 */
public class SecurityUserDeserializer extends JsonDeserializer<SecurityUser> {
    @Override
    public SecurityUser deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode jsonNode = mapper.readTree(jsonParser);
        return deserialize(mapper, jsonNode);
    }

    private SecurityUser deserialize(ObjectMapper mapper, JsonNode root) {
        if (Objects.nonNull(root)) {
            String username = root.get("username").textValue();
            String password = root.get("password").textValue();
            Set<GrantedAuthority> authorities = JsonNodeUtil.findValue(root, "authorities", JsonNodeUtil.GRANTED_AUTHORITY_SET, mapper);
            boolean accountNonExpired = root.get("accountNonExpired").booleanValue();
            boolean accountNonLocked = root.get("accountNonLocked").booleanValue();
            boolean credentialsNonExpired = root.get("credentialsNonExpired").booleanValue();
            boolean enabled = root.get("enabled").booleanValue();
            long userId = root.get("userId").longValue();
            int isAdmin = root.get("isAdmin").intValue();
            long tenantId = root.get("tenantId").longValue();
            SecurityUser securityUser = new SecurityUser(username,
                    password,
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    authorities);
            securityUser.setUserId(userId);
            securityUser.setIsAdmin(isAdmin);
            securityUser.setTenantId(tenantId);
            return securityUser;
        }
        return null;
    }
}
