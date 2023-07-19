package com.deepcode.jiaming.uaa.mapping;

import com.deepcode.jiaming.api.uaa.OAuth2ClientVo;
import com.deepcode.jiaming.api.uaa.dto.OAuth2ClientDTO;
import com.deepcode.jiaming.uaa.entity.OAuth2Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author winmanboo
 * @date 2023/7/19 13:06
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OAuth2ClientMapping {
    OAuth2ClientVo toOAuth2ClientVo(OAuth2Client oAuth2Client);

    OAuth2Client toOAuth2Client(OAuth2ClientDTO oAuth2ClientDTO);
}
