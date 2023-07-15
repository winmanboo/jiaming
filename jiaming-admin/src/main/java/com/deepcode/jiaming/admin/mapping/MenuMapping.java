package com.deepcode.jiaming.admin.mapping;

import com.deepcode.jiaming.admin.dto.MenuDTO;
import com.deepcode.jiaming.admin.entity.Menu;
import com.deepcode.jiaming.admin.mapping.convert.BooleanIntegerConverter;
import com.deepcode.jiaming.admin.vo.RouteVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author winmanboo
 * @date 2023/7/5 15:38
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BooleanIntegerConverter.class)
public interface MenuMapping {

    RouteVo toRouteVo(Menu menu);

    Menu toMenu(MenuDTO menuDTO);
}
