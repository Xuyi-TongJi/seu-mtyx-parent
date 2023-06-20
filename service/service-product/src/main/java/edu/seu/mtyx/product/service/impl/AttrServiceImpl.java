package edu.seu.mtyx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.model.product.Attr;
import edu.seu.mtyx.product.mapper.AttrMapper;
import edu.seu.mtyx.product.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.seu.mtyx.product.utils.Constants.ATTR_GROUP_ID;

@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    @Autowired
    private AttrMapper attrMapper;

    @Override
    public List<Attr> findByAttrGroupId(Long attrGroupId) {
        return attrMapper.selectList(new QueryWrapper<Attr>().eq(ATTR_GROUP_ID , attrGroupId));
    }
}
