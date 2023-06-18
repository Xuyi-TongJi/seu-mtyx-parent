package edu.seu.mtyx.acl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.seu.mtyx.acl.service.AdminService;
import edu.seu.mtyx.acl.service.RoleService;
import edu.seu.mtyx.acl.utils.MD5;
import edu.seu.mtyx.common.result.Result;
import edu.seu.mtyx.model.acl.Admin;
import edu.seu.mtyx.vo.acl.AdminQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/user")
@Api(tags = "用户管理")
@CrossOrigin //跨域
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public Result<IPage<Admin>> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "userQueryVo", value = "查询对象", required = false)
            AdminQueryVo userQueryVo) {
        Page<Admin> pageParam = new Page<>(page, limit);
        IPage<Admin> pageModel = adminService.selectPage(pageParam, userQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取管理用户")
    @GetMapping("get/{id}")
    public Result<Admin> get(@PathVariable Long id) {
        Admin user = adminService.getById(id);
        return Result.ok(user);
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public Result<Object> save(@RequestBody Admin user) {
        //对密码进行MD5处理
        user.setPassword(MD5.encrypt(user.getPassword()));
        adminService.save(user);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public Result<Object> updateById(@RequestBody Admin user) {
        adminService.updateById(user);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public Result<Object> remove(@PathVariable Long id) {
        adminService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public Result<Object> batchRemove(@RequestBody List<Long> idList) {
        adminService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{adminId}")
    public Result<Map<String, Object>> toAssign(@PathVariable Long adminId) {
        return Result.ok(roleService.findRoleById(adminId));
    }

    @ApiOperation(value = "分配用户角色")
    @PostMapping("/doAssign")
    public Result<Object> doAssign(@RequestParam Long adminId, @RequestParam Long[] roleId) {
        roleService.saveUserRoleRelationShip(adminId, roleId);
        return Result.ok(null);
    }
}
