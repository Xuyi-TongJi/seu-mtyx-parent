package edu.seu.mtyx.search.service;

public interface SkuService {

    /**
     * 上架
     * @param skuId sku id
     */
    void upperSku(Long skuId);

    /**
     * 下架
     * @param skuId sku id
     */
    void lowerSku(Long skuId);
}
