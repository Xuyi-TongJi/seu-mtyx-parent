package edu.seu.mtyx.product.api;

import edu.seu.mtyx.model.product.Category;
import edu.seu.mtyx.model.product.SkuInfo;
import edu.seu.mtyx.product.service.CategoryService;
import edu.seu.mtyx.product.service.SkuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ApiOperation(value = "获取category信息")
    @GetMapping("inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId) {
        return categoryService.getById(categoryId);
    }

    @ApiOperation(value = "根据id获取category信息")
    @PostMapping("inner/findCategoryList")
    public List<Category> findCategoryList(@RequestBody List<Long> categoryIdList) {
        return categoryService.listByIds(categoryIdList);
    }

    /**
     * 根据sku id获取sku信息
     */
    @ApiOperation(value = "获取sku信息")
    @GetMapping("inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId) {
        return skuInfoService.getById(skuId);
    }

    @ApiOperation(value = "批量获取sku信息")
    @PostMapping("inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList) {
        return skuInfoService.findSkuInfoList(skuIdList);
    }

    @ApiOperation(value = "根据关键字获取sku列表")
    @GetMapping("inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable("keyword") String keyword) {
        return skuInfoService.findSkuInfoByKeyword(keyword);
    }
}
