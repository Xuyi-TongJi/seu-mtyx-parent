package edu.seu.mtyx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.model.product.SkuImage;
import edu.seu.mtyx.product.mapper.SkuImageMapper;
import edu.seu.mtyx.product.service.SkuImageService;
import edu.seu.mtyx.product.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuImageServiceImpl extends ServiceImpl<SkuImageMapper, SkuImage> implements SkuImageService {

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Override
    public List<SkuImage> findBySkuId(Long id) {
        QueryWrapper<SkuImage> wrapper = new QueryWrapper<SkuImage>().eq(Constants.SKU_ID, id);
        return skuImageMapper.selectList(wrapper);
    }
}
