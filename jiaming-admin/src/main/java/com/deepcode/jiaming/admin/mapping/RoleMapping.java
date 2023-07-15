package com.deepcode.jiaming.admin.mapping;

import com.deepcode.jiaming.admin.dto.RoleDTO;
import com.deepcode.jiaming.admin.entity.Role;
import com.deepcode.jiaming.admin.vo.RoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author winmanboo
 * @date 2023/7/5 15:38
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapping {

    RoleVo toRoleVo(Role role);

    Role toRole(RoleDTO roleDTO);
}
