package com.deepcode.jiaming.entity;

import lombok.Data;

/**
 * OAuth2 获取访问令牌的错误实体
 * <br/>
 * like this: {"error_description":"OAuth 2.0 Parameter: grant_type","error":"invalid_request","error_uri":"https://datatracker.ietf.org/doc/html/rfc6749#section-5.2"}
 *
 * @author winmanboo
 * @date 2023/6/6 22:14
 */
@Data
public class OAuth2Error {
    private String error_description;

    private String error;

    private String error_uri;
}
