package com.deepcode.jiaming.uaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcode.jiaming.api.uaa.OAuth2ClientVo;
import com.deepcode.jiaming.api.uaa.dto.OAuth2ClientDTO;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;
import com.deepcode.jiaming.uaa.entity.OAuth2Client;

/**
 * @author winmanboo
 * @date 2023/7/19 13:02
 */
public interface OAuth2ClientService extends IService<OAuth2Client> {
    /**
     * 分页信息
     *
     * @param pageParam       页面参数
     * @param oAuth2ClientDTO 客户端查询参数
     * @return {@link PageList}<{@link OAuth2ClientVo}>
     */
    PageList<OAuth2ClientVo> pageVo(PageParam pageParam, OAuth2ClientDTO oAuth2ClientDTO);

    /**
     * 更新
     *
     * @param oAuth2ClientDTO o auth2客户dto
     */
    void update(OAuth2ClientDTO oAuth2ClientDTO);
}
