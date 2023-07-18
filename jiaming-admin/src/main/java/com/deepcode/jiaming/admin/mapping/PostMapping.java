package com.deepcode.jiaming.admin.mapping;

import com.deepcode.jiaming.admin.dto.PostDTO;
import com.deepcode.jiaming.admin.entity.Post;
import com.deepcode.jiaming.admin.vo.PostVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author winmanboo
 * @date 2023/7/18 18:17
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapping {

    Post toPost(PostDTO postDTO);

    PostVo toPostVo(Post post);
}
