package edu.seu.mtyx.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.model.product.AttrGroup;
import edu.seu.mtyx.product.mapper.AttrGroupMapper;
import edu.seu.mtyx.product.service.AttrGroupService;
import edu.seu.mtyx.vo.product.AttrGroupQueryVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {
    @Override
    public IPage<AttrGroup> selectPage(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo) {
        String name = attrGroupQueryVo.getName();
        LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(AttrGroup::getName,name);
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public List<AttrGroup> findAllList() {
        return list();
    }
}
