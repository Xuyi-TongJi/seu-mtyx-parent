package edu.seu.mtyx.sys.controller;

import edu.seu.mtyx.common.result.Result;
import edu.seu.mtyx.model.sys.Ware;
import edu.seu.mtyx.sys.service.WareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Ware管理", tags = "Ware管理")
@RestController
@RequestMapping(value="/admin/sys/ware")
public class WareController {

    @Autowired
    private WareService wareService;

    @ApiOperation(value = "获取全部仓库")
    @GetMapping("findAllList")
    public Result<List<Ware>> findAllList() {
        return Result.ok(wareService.list());
    }
}
