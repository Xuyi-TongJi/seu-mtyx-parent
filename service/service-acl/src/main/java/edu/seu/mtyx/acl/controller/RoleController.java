package edu.seu.mtyx.acl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.seu.mtyx.common.result.Result;
import edu.seu.mtyx.model.acl.Role;
import edu.seu.mtyx.acl.service.RoleService;
import edu.seu.mtyx.vo.acl.RoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/acl/role")
@Api(tags = "角色管理")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public Result<Role> get(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.ok(role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public Result<Object> save(@RequestBody Role role) {
        if (roleService.save(role)) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public Result<Object> update(@RequestBody Role role) {
        if (roleService.updateById(role)) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/remove/{id}")
    public Result<Object> remove(@PathVariable Long id) {
        if (roleService.removeById(id)) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    @ApiOperation(value = "批量删除角色")
    @DeleteMapping("batchRemove")
    public Result<Object> batchRemove(@RequestBody List<Long> idList) {
        if (roleService.removeByIds(idList)) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    @ApiOperation(value = "分页获得角色列表")
    @GetMapping("{page}/{limit}")
    public Result<IPage<Role>> index(
            @ApiParam(name = "page", value = "当前页码")
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数")
            @PathVariable Long limit,
            @ApiParam(name = "roleQueryVo", value = "模糊查询对象")
            RoleQueryVo roleQueryVo) {
        Page<Role> pageParam = new Page<>(page, limit);
        return Result.ok(roleService.selectPage(pageParam, roleQueryVo));
    }
}
