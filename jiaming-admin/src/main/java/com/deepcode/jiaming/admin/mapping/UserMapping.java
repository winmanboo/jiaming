package com.deepcode.jiaming.admin.mapping;

import com.deepcode.jiaming.admin.dto.UserDTO;
import com.deepcode.jiaming.admin.entity.User;
import com.deepcode.jiaming.admin.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author winmanboo
 * @date 2023/7/5 15:38
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapping {

    UserVo toUserVo(User user);

    User toUser(UserDTO userDTO);
}
