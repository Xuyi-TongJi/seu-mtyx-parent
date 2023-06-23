package edu.seu.mtyx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.model.product.SkuPoster;
import edu.seu.mtyx.product.mapper.SkuPosterMapper;
import edu.seu.mtyx.product.service.SkuPosterService;
import edu.seu.mtyx.product.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuPosterServiceImpl extends ServiceImpl<SkuPosterMapper, SkuPoster> implements SkuPosterService {

    @Autowired
    private SkuPosterMapper skuPosterMapper;

    @Override
    public List<SkuPoster> findBySkuId(Long id) {
        QueryWrapper<SkuPoster> wrapper = new QueryWrapper<SkuPoster>().eq(Constants.SKU_ID, id);
        return skuPosterMapper.selectList(wrapper);
    }
}
