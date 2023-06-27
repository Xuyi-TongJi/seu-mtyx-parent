package edu.seu.mtyx.activity.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.seu.mtyx.activity.service.CouponInfoService;
import edu.seu.mtyx.common.result.Result;
import edu.seu.mtyx.model.activity.CouponInfo;
import edu.seu.mtyx.vo.activity.CouponRuleVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/activity/couponInfo")
public class CouponInfoController {

    @Autowired
    private CouponInfoService couponInfoService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result<IPage<CouponInfo>> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit
    ) {
        Page<CouponInfo> pageParam = new Page<>(page, limit);
        IPage<CouponInfo> pageModel = couponInfoService.selectPage(pageParam);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取优惠券")
    @GetMapping("get/{id}")
    public Result<CouponInfo> get(@PathVariable String id) {
        CouponInfo couponInfo = couponInfoService.getCouponInfo(id);
        return Result.ok(couponInfo);
    }

    @ApiOperation(value = "新增优惠券")
    @PostMapping("save")
    public Result<Object> save(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改优惠券")
    @PutMapping("update")
    public Result<Object> updateById(@RequestBody CouponInfo couponInfo) {
        couponInfoService.updateById(couponInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除优惠券")
    @DeleteMapping("remove/{id}")
    public Result<Object> remove(@PathVariable String id) {
        couponInfoService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value="根据id列表删除优惠券")
    @DeleteMapping("batchRemove")
    public Result<Object> batchRemove(@RequestBody List<String> idList){
        couponInfoService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation(value = "获取优惠券信息")
    @GetMapping("findCouponRuleList/{id}")
    public Result<Object> findActivityRuleList(@PathVariable Long id) {
        return Result.ok(couponInfoService.findCouponRuleList(id));
    }

    @ApiOperation(value = "新增活动")
    @PostMapping("saveCouponRule")
    public Result<Object> saveCouponRule(@RequestBody CouponRuleVo couponRuleVo) {
        couponInfoService.saveCouponRule(couponRuleVo);
        return Result.ok(null);
    }

    /**
     * 根据关键字获取sku列表，活动使用
     * @param keyword
     * @return
     */
    @GetMapping("findCouponByKeyword/{keyword}")
    public Result<Object> findCouponByKeyword(@PathVariable("keyword") String keyword) {
        return Result.ok(couponInfoService.findCouponByKeyword(keyword));
    }
}