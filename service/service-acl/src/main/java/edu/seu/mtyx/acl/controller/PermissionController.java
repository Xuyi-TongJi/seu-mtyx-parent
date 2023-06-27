package edu.seu.mtyx.acl.controller;

import edu.seu.mtyx.acl.service.PermissionService;
import edu.seu.mtyx.common.result.Result;
import edu.seu.mtyx.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/acl/permission")
@Api(tags = "菜单管理")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "获取菜单")
    @GetMapping
    public Result<List<Permission>> index() {
        return Result.ok(permissionService.queryAllMenu());
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result<Object> save(@RequestBody Permission permission) {
        if (permissionService.save(permission)) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result<Object> updateById(@RequestBody Permission permission) {
        if (permissionService.updateById(permission)) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public Result<Object> remove(@PathVariable Long id) {
        if (permissionService.removeChildById(id)) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }
}
