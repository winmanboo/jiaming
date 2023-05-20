package com.deepcode.jiaming.admin.service.impl;

import com.deepcode.jiaming.admin.entity.Post;
import com.deepcode.jiaming.admin.mapper.PostMapper;
import com.deepcode.jiaming.admin.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 岗位表 服务实现类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

}
