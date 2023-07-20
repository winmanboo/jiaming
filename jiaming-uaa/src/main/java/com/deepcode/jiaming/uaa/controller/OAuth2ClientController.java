package com.deepcode.jiaming.uaa.controller;

import com.deepcode.jiaming.api.uaa.OAuth2ClientVo;
import com.deepcode.jiaming.api.uaa.dto.OAuth2ClientDTO;
import com.deepcode.jiaming.api.uaa.entity.TokenSettingVo;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;
import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.uaa.service.OAuth2ClientService;
import com.deepcode.jiaming.valid.AddGroup;
import com.deepcode.jiaming.valid.UpdateGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 客户端控制器
 *
 * @author winmanboo
 * @date 2023/6/3 21:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/uaa/oauth2-client")
public class OAuth2ClientController {

    private final JdbcRegisteredClientRepository registeredClientRepository;

    private final OAuth2ClientService oAuth2ClientService;

    private final PasswordEncoder passwordEncoder;

    /**
     * 注册客户端信息
     *
     * @param registeredClient 客户端信息
     * @return Result 结果
     */
    @PostMapping("/register")
    public Result<Void> registerClient(@Validated({AddGroup.class}) @RequestBody OAuth2ClientDTO oAuth2ClientDTO) {
        RegisteredClient.Builder builder = RegisteredClient.withId(oAuth2ClientDTO.getId())
                .clientId(oAuth2ClientDTO.getClientId())
                .clientIdIssuedAt(Instant.now())
                .clientSecret(passwordEncoder.encode(oAuth2ClientDTO.getClientSecret()))
                .clientSecretExpiresAt(null) // 暂定 secret 不会过期
                .clientName(oAuth2ClientDTO.getClientName())
                .clientAuthenticationMethods((authenticationMethods) -> {
                    /*String[] methods = oAuth2ClientDTO.getClientAuthenticationMethods().split(",");
                    for (String method : methods) {
                        authenticationMethods.add(new ClientAuthenticationMethod(method));
                    }*/
                    authenticationMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC); // 暂定所有注册客户端只支持 basic 认证
                })
                .authorizationGrantTypes((grantTypes) -> {
                    String[] types = oAuth2ClientDTO.getAuthorizationGrantTypes().split(",");
                    for (String type : types) {
                        grantTypes.add(new AuthorizationGrantType(type));
                    }
                })
                .redirectUris((uris) -> {
                    String[] redirectUris = oAuth2ClientDTO.getRedirectUris().split(",");
                    uris.addAll(Arrays.asList(redirectUris));
                })
                .scopes((scopes) -> {
                    String[] scopeArray = oAuth2ClientDTO.getScopes().split(",");
                    scopes.addAll(Arrays.asList(scopeArray));
                })
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofSeconds(oAuth2ClientDTO.getAccessTokenTimeToLive()))
                        .refreshTokenTimeToLive(Duration.ofSeconds(oAuth2ClientDTO.getRefreshTokenTimeToLive()))
                        .build());
        registeredClientRepository.save(builder.build());
        return Result.ok();
    }

    @GetMapping("/page")
    public Result<PageList<OAuth2ClientVo>> page(PageParam pageParam, OAuth2ClientDTO oAuth2ClientDTO) {
        PageList<OAuth2ClientVo> pageList = oAuth2ClientService.pageVo(pageParam, oAuth2ClientDTO);
        return Result.ok(pageList);
    }

    @GetMapping("/info/{id}")
    public Result<OAuth2ClientVo> info(@PathVariable String id) {
        RegisteredClient registeredClient = registeredClientRepository.findById(id);
        if (Objects.isNull(registeredClient)) {
            return Result.ok();
        }
        OAuth2ClientVo oAuth2ClientVo = new OAuth2ClientVo();
        oAuth2ClientVo.setId(registeredClient.getId());
        oAuth2ClientVo.setClientId(registeredClient.getClientId());
        oAuth2ClientVo.setClientName(registeredClient.getClientName());
        oAuth2ClientVo.setClientSecret(registeredClient.getClientSecret());
        oAuth2ClientVo.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
        oAuth2ClientVo.setClientSecretExpiresAt(registeredClient.getClientSecretExpiresAt());
        List<String> methods = registeredClient.getClientAuthenticationMethods().stream()
                .map(ClientAuthenticationMethod::getValue)
                .toList();
        oAuth2ClientVo.setClientAuthenticationMethods(String.join(",", methods));
        oAuth2ClientVo.setScopes(String.join(",", registeredClient.getScopes()));
        oAuth2ClientVo.setRedirectUris(String.join(",", registeredClient.getRedirectUris()));
        List<String> types = registeredClient.getAuthorizationGrantTypes().stream()
                .map(AuthorizationGrantType::getValue)
                .toList();
        oAuth2ClientVo.setAuthorizationGrantTypes(String.join(",", types));

        TokenSettingVo tokenSettingVo = new TokenSettingVo();
        tokenSettingVo.setAccessTokenTimeToLive((double) registeredClient.getTokenSettings().getAccessTokenTimeToLive().toSeconds());
        tokenSettingVo.setRefreshTokenTimeToLive((double) registeredClient.getTokenSettings().getRefreshTokenTimeToLive().toSeconds());

        oAuth2ClientVo.setTokenSettingVo(tokenSettingVo);
        return Result.ok(oAuth2ClientVo);
    }

    @PutMapping("/update")
    public Result<Void> update(@Validated(UpdateGroup.class) @RequestBody OAuth2ClientDTO oAuth2ClientDTO) {
        RegisteredClient registeredClient = registeredClientRepository.findById(oAuth2ClientDTO.getId());
        if (Objects.isNull(registeredClient)) {
            return Result.fail("客户端不存在");
        }
        oAuth2ClientService.update(oAuth2ClientDTO);
        return Result.ok();
    }
}
