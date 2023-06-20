package edu.seu.mtyx.sys.controller;

import edu.seu.mtyx.common.result.Result;
import edu.seu.mtyx.model.sys.Region;
import edu.seu.mtyx.sys.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "区域接口")
@RestController
@RequestMapping("/admin/sys/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @ApiOperation(value = "根据关键字获取地区列表")
    @GetMapping("findRegionByKeyword/{keyword}")
    public Result<List<Region>> findSkuInfoByKeyword(@PathVariable String keyword) {
        return Result.ok(regionService.findRegionByKeyword(keyword));
    }
}
