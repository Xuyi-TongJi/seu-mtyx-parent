package edu.seu.mtyx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.model.product.SkuAttrValue;
import edu.seu.mtyx.model.product.SkuImage;
import edu.seu.mtyx.model.product.SkuInfo;
import edu.seu.mtyx.model.product.SkuPoster;
import edu.seu.mtyx.product.mapper.SkuInfoMapper;
import edu.seu.mtyx.product.service.SkuAttrValueService;
import edu.seu.mtyx.product.service.SkuImageService;
import edu.seu.mtyx.product.service.SkuInfoService;
import edu.seu.mtyx.product.service.SkuPosterService;
import edu.seu.mtyx.vo.product.SkuInfoQueryVo;
import edu.seu.mtyx.vo.product.SkuInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Autowired
    private SkuPosterService skuPosterService;

    @Autowired
    private SkuImageService skuImagesService;

    @Autowired
    private SkuAttrValueService skuAttrValueService;

    @Override
    public IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        //获取条件值
        String keyword = skuInfoQueryVo.getKeyword();
        String skuType = skuInfoQueryVo.getSkuType();
        Long categoryId = skuInfoQueryVo.getCategoryId();
        //封装条件
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(keyword)) {
            wrapper.like(SkuInfo::getSkuName,keyword);
        }
        if(!StringUtils.isEmpty(skuType)) {
            wrapper.eq(SkuInfo::getSkuType,skuType);
        }
        if(!StringUtils.isEmpty(categoryId)) {
            wrapper.eq(SkuInfo::getCategoryId,categoryId);
        }
        //调用方法查询
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSkuInfo(SkuInfoVo skuInfoVo) {
        // save sku info
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        this.save(skuInfo);

        // save image
        int sort = 1;
        if (skuInfoVo.getSkuImagesList() != null) {
            for (SkuImage skuImage : skuInfoVo.getSkuImagesList()) {
                skuImage.setSort(sort);
                sort += 1;
                skuImage.setSkuId(skuInfoVo.getId());
            }
            skuImagesService.saveBatch(skuInfoVo.getSkuImagesList());
        }

        // save sku attr
        sort = 1;
        if (skuInfoVo.getSkuAttrValueList() != null) {
            for (SkuAttrValue skuAttrValue : skuInfoVo.getSkuAttrValueList()) {
                skuAttrValue.setSort(sort);
                sort += 1;
                skuAttrValue.setSkuId(skuInfoVo.getId());
            }
            skuAttrValueService.saveBatch(skuInfoVo.getSkuAttrValueList());
        }


        // save sku poster
        if (skuInfoVo.getSkuPosterList() != null) {
            for (SkuPoster skuPoster : skuInfoVo.getSkuPosterList()) {
                skuPoster.setSkuId(skuInfoVo.getId());
            }
            skuPosterService.saveBatch(skuInfoVo.getSkuPosterList());
        }
    }

    @Override
    public SkuInfoVo getSkuInfo(Long id) {
        SkuInfo sku = this.getById(id);
        if (sku != null) {
            SkuInfoVo result = new SkuInfoVo();
            BeanUtils.copyProperties(sku, result);
            // query image / poster / attrValue
            List<SkuPoster> skuPosters = skuPosterService.findBySkuId(id);
            List<SkuImage> skuImages = skuImagesService.findBySkuId(id);
            List<SkuAttrValue> skuAttrValues = skuAttrValueService.findBySkuId(id);
            result.setSkuImagesList(skuImages);
            result.setSkuPosterList(skuPosters);
            result.setSkuAttrValueList(skuAttrValues);
            return result;
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSkuInfo(SkuInfoVo skuInfoVo) {
        // save sku info
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        this.updateById(skuInfo);

        Long skuId = skuInfoVo.getId();
        // save image
        skuImagesService.remove(new LambdaQueryWrapper<SkuImage>().eq(new SFunction<SkuImage, Long>() {
            @Override
            public Long apply(SkuImage skuImage) {
                return skuImage.getSkuId();
            }
        }, skuId));
        int sort = 1;
        if (skuInfoVo.getSkuImagesList() != null) {
            for (SkuImage skuImage : skuInfoVo.getSkuImagesList()) {
                skuImage.setSort(sort);
                sort += 1;
                skuImage.setSkuId(skuInfoVo.getId());
            }
            skuImagesService.saveBatch(skuInfoVo.getSkuImagesList());
        }

        skuAttrValueService.remove(new LambdaQueryWrapper<SkuAttrValue>().eq(new SFunction<SkuAttrValue, Long>() {
            @Override
            public Long apply(SkuAttrValue skuAttrValue) {
                return skuAttrValue.getSkuId();
            }
        }, skuId));
        // save sku attr
        sort = 1;
        if (skuInfoVo.getSkuAttrValueList() != null) {
            for (SkuAttrValue skuAttrValue : skuInfoVo.getSkuAttrValueList()) {
                skuAttrValue.setSort(sort);
                sort += 1;
                skuAttrValue.setSkuId(skuInfoVo.getId());
            }
            skuAttrValueService.saveBatch(skuInfoVo.getSkuAttrValueList());
        }

        skuPosterService.remove(new LambdaQueryWrapper<SkuPoster>().eq(new SFunction<SkuPoster, Long>() {
            @Override
            public Long apply(SkuPoster skuPoster) {
                return skuPoster.getSkuId();
            }
        }, skuId));
        // save sku poster
        if (skuInfoVo.getSkuPosterList() != null) {
            for (SkuPoster skuPoster : skuInfoVo.getSkuPosterList()) {
                skuPoster.setSkuId(skuInfoVo.getId());
            }
            skuPosterService.saveBatch(skuInfoVo.getSkuPosterList());
        }
    }

    @Override
    public void check(Long skuId, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setCheckStatus(status);
        baseMapper.updateById(skuInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(Long skuId, Integer status) {
        // 更改发布状态
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setPublishStatus(status);
        baseMapper.updateById(skuInfo);
        if (status == 1) {
            //TODO 商品上架 后续会完善：发送mq消息更新es数据
        } else {
            //TODO 商品下架 后续会完善：发送mq消息更新es数据
        }
    }

    @Override
    public void isNewPerson(Long skuId, Integer status) {
        SkuInfo skuInfoUp = new SkuInfo();
        skuInfoUp.setId(skuId);
        skuInfoUp.setIsNewPerson(status);
        baseMapper.updateById(skuInfoUp);
    }
}
