package edu.seu.mtyx.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.seu.mtyx.common.result.Result;
import edu.seu.mtyx.model.product.Category;
import edu.seu.mtyx.product.service.CategoryService;
import edu.seu.mtyx.vo.product.CategoryQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Category管理", tags = "商品分类管理")
@RestController
@RequestMapping(value="/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取品类分页列表")
    @GetMapping("{page}/{limit}")
    public Result<IPage<Category>> index(@PathVariable Long page,
                                         @PathVariable Long limit,
                                         CategoryQueryVo categoryQueryVo) {
        Page<Category> pageParam = new Page<>(page, limit);
        return Result.ok(categoryService.selectPage(pageParam, categoryQueryVo));
    }

    @ApiOperation(value = "获取商品分类信息")
    @GetMapping("get/{id}")
    public Result<Category> get(@PathVariable Long id) {
        return Result.ok(categoryService.getById(id));
    }

    @ApiOperation(value = "新增商品分类")
    @PostMapping("save")
    public Result<Object> save(@RequestBody Category category) {
        categoryService.save(category);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改商品分类")
    @PutMapping("update")
    public Result<Object> updateById(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除商品分类")
    @DeleteMapping("remove/{id}")
    public Result<Object> remove(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除商品分类")
    @DeleteMapping("batchRemove")
    public Result<Object> batchRemove(@RequestBody List<Long> idList) {
        categoryService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation(value = "获取全部商品分类")
    @GetMapping("findAllList")
    public Result<List<Category>> findAllList() {
        return Result.ok(categoryService.findAllList());
    }
}
