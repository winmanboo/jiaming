package com.deepcode.jiaming.uaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.uaa.entity.OAuth2Token;
import com.deepcode.jiaming.uaa.mapper.OAuth2TokenMapper;
import com.deepcode.jiaming.uaa.service.OAuth2TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author winmanboo
 * @date 2023/7/19 21:32
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2TokenServiceImpl extends ServiceImpl<OAuth2TokenMapper, OAuth2Token> implements OAuth2TokenService {
}
