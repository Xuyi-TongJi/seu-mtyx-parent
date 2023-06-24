package edu.seu.mtyx.search.service.impl;

import com.alibaba.fastjson.JSON;
import edu.seu.mtyx.client.product.ProductFeignClient;
import edu.seu.mtyx.enums.SkuType;
import edu.seu.mtyx.model.product.Category;
import edu.seu.mtyx.model.product.SkuInfo;
import edu.seu.mtyx.model.search.SkuEs;
import edu.seu.mtyx.search.repository.SkuRepository;
import edu.seu.mtyx.search.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuRepository skuRepository;

    /**
     * 远程调用客户端Bean，可以发起远程调用
     */
    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public void upperSku(Long skuId) {
        // 远程调用获取sku信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if (skuInfo == null) {
            return;
        }
        Long categoryId = skuInfo.getCategoryId();
        Category category = productFeignClient.getCategory(categoryId);

        // 封装
        SkuEs skuEs = new SkuEs();
        if (category != null) {
            skuEs.setCategoryId(category.getId());
            skuEs.setCategoryName(category.getName());
        }
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName()+","+skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if(skuInfo.getSkuType().equals(SkuType.COMMON.getCode())) {
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        } else {
            //TODO 秒杀商品

        }
        SkuEs save = skuRepository.save(skuEs);
        log.info("upperSku："+ JSON.toJSONString(save));
    }

    @Override
    public void lowerSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }
}
