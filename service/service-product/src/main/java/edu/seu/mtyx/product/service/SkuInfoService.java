package edu.seu.mtyx.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.model.product.SkuInfo;
import edu.seu.mtyx.vo.product.SkuInfoQueryVo;
import edu.seu.mtyx.vo.product.SkuInfoVo;

public interface SkuInfoService extends IService<SkuInfo> {
    IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    /**
     * 添加商品
     * @param skuInfoVo 商品信息
     */
    void saveSkuInfo(SkuInfoVo skuInfoVo);

    SkuInfoVo getSkuInfo(Long id);

    void updateSkuInfo(SkuInfoVo skuInfoVo);

    /**
     * 商品审核
     * @param skuId sku id
     * @param status checkStatus
     */
    void check(Long skuId, Integer status);

    /**
     * 商品下架,上架
     * @param skuId sku id
     * @param status publish status
     */
    void publish(Long skuId, Integer status);

    /**
     * 新人专享
     * @param skuId sku id
     * @param status new person
     */
    void isNewPerson(Long skuId, Integer status);
}
