package edu.seu.mtyx.product.controller;

import edu.seu.mtyx.common.result.Result;
import edu.seu.mtyx.model.product.Attr;
import edu.seu.mtyx.product.service.AttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Attr管理", tags = "平台属性管理")
@RestController
@RequestMapping(value="/admin/product/attr")
@CrossOrigin
public class AttrController {

    @Autowired
    private AttrService attrService;

    @ApiOperation(value = "获取列表")
    @GetMapping("{attrGroupId}")
    public Result<List<Attr>> index(
            @ApiParam(name = "attrGroupId", value = "分组id", required = true)
            @PathVariable Long attrGroupId) {
        return Result.ok(attrService.findByAttrGroupId(attrGroupId));
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result<Attr> get(@PathVariable Long id) {
        Attr attr = attrService.getById(id);
        return Result.ok(attr);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result<Object> save(@RequestBody Attr attr) {
        attrService.save(attr);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result<Object> updateById(@RequestBody Attr attr) {
        attrService.updateById(attr);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result<Object> remove(@PathVariable Long id) {
        attrService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result<Object> batchRemove(@RequestBody List<Long> idList) {
        attrService.removeByIds(idList);
        return Result.ok(null);
    }
}