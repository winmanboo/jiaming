package com.deepcode.jiaming.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepcode.jiaming.admin.dto.PostDTO;
import com.deepcode.jiaming.admin.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepcode.jiaming.admin.vo.PostVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 岗位表 Mapper 接口
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface PostMapper extends BaseMapper<Post> {

    /**
     * 岗位列表
     *
     * @param tenantId 租户id
     * @return {@link List}<{@link PostVo}>
     */
    List<PostVo> listVo(@Param("tenantId") Long tenantId);

    /**
     * 岗位分页
     *
     * @param page    页面
     * @param postDTO 岗位参数
     * @return {@link IPage}<{@link PostVo}>
     */
    IPage<PostVo> pageVo(@Param("page") Page<PostVo> page, @Param("post") PostDTO postDTO);
}
