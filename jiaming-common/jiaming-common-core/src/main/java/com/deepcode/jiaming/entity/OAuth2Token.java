package com.deepcode.jiaming.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winmanboo
 * @date 2023/6/8 23:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2Token {
    private String accessToken;

    private String tokenType;
}
