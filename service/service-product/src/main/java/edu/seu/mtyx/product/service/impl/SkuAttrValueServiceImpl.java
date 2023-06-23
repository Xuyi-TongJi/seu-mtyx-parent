package edu.seu.mtyx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.model.product.SkuAttrValue;
import edu.seu.mtyx.product.mapper.SkuAttrValueMapper;
import edu.seu.mtyx.product.service.SkuAttrValueService;
import edu.seu.mtyx.product.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuAttrValueServiceImpl extends ServiceImpl<SkuAttrValueMapper, SkuAttrValue> implements SkuAttrValueService {

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Override
    public List<SkuAttrValue> findBySkuId(Long id) {
        QueryWrapper<SkuAttrValue> wrapper = new QueryWrapper<SkuAttrValue>().eq(Constants.SKU_ID, id);
        return skuAttrValueMapper.selectList(wrapper);
    }
}
