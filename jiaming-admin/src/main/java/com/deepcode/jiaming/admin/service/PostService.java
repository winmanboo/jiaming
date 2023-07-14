package com.deepcode.jiaming.admin.service;

import com.deepcode.jiaming.admin.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcode.jiaming.admin.vo.PostVo;

import java.util.List;

/**
 * <p>
 * 岗位表 服务类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
public interface PostService extends IService<Post> {

    /**
     * 岗位列表
     *
     * @return {@link List}<{@link PostVo}>
     */
    List<PostVo> listVo();
}
