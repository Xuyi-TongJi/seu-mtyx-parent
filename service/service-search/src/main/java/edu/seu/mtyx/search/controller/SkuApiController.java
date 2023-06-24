package edu.seu.mtyx.search.controller;

import edu.seu.mtyx.common.result.Result;
import edu.seu.mtyx.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/search/sku")
public class SkuApiController {

    @Autowired
    private SkuService skuService;

    /**
     * 商品上架
     */
    @GetMapping("inner/upperSku/{skuId}")
    public Result<Object> upperSku(@PathVariable Long skuId) {
        skuService.upperSku(skuId);
        return Result.ok(null);
    }

    /**
     * 商品下架
     */
    @GetMapping("inner/lowerSku/{skuId}")
    public Result<Object> lowerSku(@PathVariable Long skuId) {
        skuService.lowerSku(skuId);
        return Result.ok(null);
    }
}
