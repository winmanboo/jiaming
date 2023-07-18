package com.deepcode.jiaming.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deepcode.jiaming.admin.dto.PostDTO;
import com.deepcode.jiaming.admin.entity.Post;
import com.deepcode.jiaming.admin.mapper.PostMapper;
import com.deepcode.jiaming.admin.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcode.jiaming.admin.vo.PostVo;
import com.deepcode.jiaming.base.PageList;
import com.deepcode.jiaming.base.PageParam;
import com.deepcode.jiaming.security.context.UserInfoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 岗位表 服务实现类
 * </p>
 *
 * @author winmanboo
 * @since 2023-05-20
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Override
    public List<PostVo> listVo() {
        Long tenantId = UserInfoContext.get().getTenantId();
        return baseMapper.listVo(tenantId);
    }

    @Override
    public PageList<PostVo> pageVo(PageParam pageParam, PostDTO postDTO) {
        IPage<PostVo> page = baseMapper.pageVo(pageParam.toPage(), postDTO);
        return PageList.turnTo(page);
    }
}
