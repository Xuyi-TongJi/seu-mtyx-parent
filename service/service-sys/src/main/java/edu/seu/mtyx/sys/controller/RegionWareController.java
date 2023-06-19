package edu.seu.mtyx.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.seu.mtyx.common.result.Result;
import edu.seu.mtyx.model.sys.RegionWare;
import edu.seu.mtyx.sys.service.RegionWareService;
import edu.seu.mtyx.vo.sys.RegionWareQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/sys/regionWare")
@Api(tags = "RegionWare管理")
@CrossOrigin //跨域
public class RegionWareController {

    @Autowired
    private RegionWareService regionWareService;

    @GetMapping("{page}/{limit}")
    public Result<IPage<RegionWare>> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "一页条目数", required = true)
            @PathVariable Long limit,
            RegionWareQueryVo regionWareQueryVo
    ) {
        Page<RegionWare> pageParam = new Page<>(page, limit);
        return Result.ok(regionWareService.selectPage(pageParam, regionWareQueryVo));
    }
}
