package com.deepcode.jiaming.admin.mapping;

import com.deepcode.jiaming.admin.dto.DeptDTO;
import com.deepcode.jiaming.admin.entity.Dept;
import com.deepcode.jiaming.admin.vo.DeptVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author winmanboo
 * @date 2023/7/18 14:43
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeptMapping {
    DeptVo toDeptVo(Dept dept);

    Dept toDept(DeptDTO deptDTO);
}
