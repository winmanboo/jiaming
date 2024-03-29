package com.deepcode.jiaming.uaa.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepcode.jiaming.base.BaseController;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;
import com.deepcode.jiaming.result.Result;
import com.deepcode.jiaming.uaa.entity.OAuth2Token;
import com.deepcode.jiaming.uaa.service.OAuth2TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author winmanboo
 * @date 2023/7/19 21:08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/uaa/oauth2-token")
public class OAuth2TokenController extends BaseController {

    private final OAuth2TokenService oAuth2TokenService;

    @GetMapping("/page")
    public Result<PageList<OAuth2Token>> page(PageParam pageParam) {
        LambdaQueryWrapper<OAuth2Token> queryWrapper = Wrappers.<OAuth2Token>lambdaQuery()
                .orderByDesc(OAuth2Token::getAccessTokenExpiresAt);
        Page<OAuth2Token> page = oAuth2TokenService.page(pageParam.toPage(), queryWrapper);
        return ok(PageList.turnTo(page));
    }

    /**
     * 删除
     *
     * @param token 自定义令牌
     * @return {@link Result}<{@link Void}>
     */
    @DeleteMapping("/remove/{token}")
    public Result<Void> remove(@PathVariable String token) {
        oAuth2TokenService.removeByToken(token);
        return ok();
    }
}
