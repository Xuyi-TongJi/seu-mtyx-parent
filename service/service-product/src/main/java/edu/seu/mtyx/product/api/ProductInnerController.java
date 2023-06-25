package edu.seu.mtyx.product.api;

import edu.seu.mtyx.model.product.Category;
import edu.seu.mtyx.model.product.SkuInfo;
import edu.seu.mtyx.product.service.CategoryService;
import edu.seu.mtyx.product.service.SkuInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "产品远程调用api") // 别的服务远程调用这个Controller中的接口
@RequestMapping("/api/product")
@RestController
public class ProductInnerController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 根据分类id获取分类信息
     */
    @GetMapping("inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        return categoryService.getById(categoryId);
    }

    /**
     * 根据sku id获取sku信息
     */
    @GetMapping("inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable Long skuId) {
        return skuInfoService.getById(skuId);
    }
}
