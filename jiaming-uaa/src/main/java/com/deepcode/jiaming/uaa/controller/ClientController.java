package com.deepcode.jiaming.uaa.controller;

import cn.hutool.core.util.ArrayUtil;
import com.deepcode.jiaming.exception.JiamingException;
import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.uaa.constants.Oauth2Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 客户端控制器
 *
 * @author winmanboo
 * @date 2023/6/3 21:43
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/uaa/client")
public class ClientController {
    private final JdbcRegisteredClientRepository registeredClientRepository;

    private final LoadBalancerClient loadBalancerClient;

    @Value("${spring.application.name}")
    private String serviceName;

    /**
     * 注册客户端信息
     *
     * @param registeredClient 客户端信息
     * @return Result 结果
     */
    @ResponseBody
    @PostMapping("/register")
    public Result<Void> registerClient(@RequestBody RegisteredClient registeredClient) {
        registeredClientRepository.save(registeredClient);
        return Result.ok();
    }

    /**
     * 重定向获取授权码（主要是前端第一次请求时，没有传递自定义 token 的话，在网关处会重定向到该接口发起获取授权码，走授权码模式）
     *
     * @param clientId 客户端 id
     * @return
     */
    @GetMapping("/redirect_code")
    public String redirectCode() {
        RegisteredClient client = registeredClientRepository.findByClientId(Oauth2Constant.DEFAULT_CLIENT_ID);
        if (client == null) {
            throw new JiamingException("无法获取客户端信息");
        }

        // 重定向发起获取授权码
        // 地址根据服务器名获取对应的 IP 地址和端口号，有三种方式
        // 1. 通过 LoadBalancer 获取
        // 2. 通过 DiscoveryClient 获取
        // 3. 通过 NacosServiceManager 获取
        // 方法请参考：https://blog.csdn.net/weixin_43888891/article/details/126755927
        ServiceInstance instance = loadBalancerClient.choose(serviceName);
        Set<String> scopes = client.getScopes();
        String scope = getFirstScope(scopes);

        Set<String> redirectUris = client.getRedirectUris();
        String redirectUri = getFirstRedirectUri(redirectUris);

        String url = instance.getUri() + "/oauth2/authorize?response_type=code" +
                "&client_id=" + client.getClientId() +
                "&scope=" + scope +
                "&redirect_uri=" + redirectUri;
        return "redirect:" + url;
    }

    private String getFirstScope(Set<String> scopes) {
        String[] scopeArray = scopes.toArray(new String[0]);

        String scope = "";
        if (ArrayUtil.isNotEmpty(scopeArray)) {
            scope = scopeArray[0];
        }

        return scope;
    }

    private String getFirstRedirectUri(Set<String> redirectUris) {
        String[] redirectUriArray = redirectUris.toArray(new String[0]);

        String redirectUri = "";
        if (ArrayUtil.isNotEmpty(redirectUriArray)) {
            redirectUri = redirectUriArray[0];
        }

        return redirectUri;
    }
}
