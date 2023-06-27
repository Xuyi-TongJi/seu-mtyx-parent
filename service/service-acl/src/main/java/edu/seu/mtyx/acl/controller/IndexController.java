package edu.seu.mtyx.acl.controller;

import edu.seu.mtyx.common.result.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
public class IndexController {

    /**
     * 请求登陆的接口
     */
    @PostMapping("login")
    public Result<Map<String, Object>> login() {
        Map<String, Object> maps = new HashMap<>();
        maps.put("token", "admin-token");
        return Result.ok(maps);
    }

    /**
     * 获取用户登陆信息
     */
    @GetMapping("info")
    public Result<Map<String, Object>> info() {
        Map<String, Object> maps = new HashMap<>();
        maps.put("name", "mtyx-admin");
        // TODO
        maps.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(maps);
    }

    /**
     * 退出登陆
     */
    public Result<?> logout() {
        return Result.ok(null);
    }
}
