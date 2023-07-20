package com.deepcode.jiaming.uaa.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.api.uaa.OAuth2ClientVo;
import com.deepcode.jiaming.api.uaa.dto.OAuth2ClientDTO;
import com.deepcode.jiaming.api.uaa.entity.TokenSettingVo;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;
import com.deepcode.jiaming.uaa.entity.OAuth2Client;
import com.deepcode.jiaming.uaa.mapper.OAuth2ClientMapper;
import com.deepcode.jiaming.uaa.mapping.OAuth2ClientMapping;
import com.deepcode.jiaming.uaa.service.OAuth2ClientService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author winmanboo
 * @date 2023/7/19 13:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2ClientServiceImpl extends ServiceImpl<OAuth2ClientMapper, OAuth2Client> implements OAuth2ClientService {

    private final OAuth2ClientMapping oAuth2ClientMapping;

    private final ObjectMapper objectMapper;

    @Override
    public PageList<OAuth2ClientVo> pageVo(PageParam pageParam, OAuth2ClientDTO oAuth2ClientDTO) {
        String clientName = oAuth2ClientDTO.getClientName();
        Page<OAuth2Client> page = lambdaQuery().eq(
                CharSequenceUtil.isNotEmpty(clientName),
                OAuth2Client::getClientName,
                clientName
        ).page(pageParam.toPage());
        List<OAuth2ClientVo> clientVos = page.getRecords().stream().map(item -> {
            OAuth2ClientVo oAuth2ClientVo = oAuth2ClientMapping.toOAuth2ClientVo(item);
            Map<String, Object> tokenSettingsMap = parseMap(item.getTokenSettings());
            TokenSettings.Builder tokenSettingsBuilder = TokenSettings.withSettings(tokenSettingsMap);
            if (!tokenSettingsMap.containsKey(ConfigurationSettingNames.Token.ACCESS_TOKEN_FORMAT)) {
                tokenSettingsBuilder.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED);
            }
            TokenSettings tokenSettings = tokenSettingsBuilder.build();
            Map<String, Object> settings = tokenSettings.getSettings();
            Double accessTokenTimeToLive = (Double) ((ArrayList) settings.get("settings.token.access-token-time-to-live")).get(1);
            Double refreshTokenTimeToLive = (Double) ((ArrayList) settings.get("settings.token.refresh-token-time-to-live")).get(1);
            TokenSettingVo tokenSettingVo = new TokenSettingVo();
            tokenSettingVo.setAccessTokenTimeToLive(accessTokenTimeToLive);
            tokenSettingVo.setRefreshTokenTimeToLive(refreshTokenTimeToLive);
            oAuth2ClientVo.setTokenSettingVo(tokenSettingVo);
            return oAuth2ClientVo;
        }).toList();
        Page<OAuth2ClientVo> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
        voPage.setRecords(clientVos);
        return PageList.turnTo(voPage);
    }

    @Override
    public void update(OAuth2ClientDTO oAuth2ClientDTO) {
        // FIXME: 2023/7/20 TokenSettings 没有更新
        OAuth2Client oAuth2Client = oAuth2ClientMapping.toOAuth2Client(oAuth2ClientDTO);
        this.updateById(oAuth2Client);
    }

    private Map<String, Object> parseMap(String data) {
        try {
            return this.objectMapper.readValue(data, new TypeReference<>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}
