package edu.seu.mtyx.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.activity.mapper.CouponInfoMapper;
import edu.seu.mtyx.model.activity.CouponInfo;
import edu.seu.mtyx.vo.activity.CouponRuleVo;

import java.util.List;
import java.util.Map;

public interface CouponInfoService extends IService<CouponInfo> {
    IPage<CouponInfo> selectPage(Page<CouponInfo> pageParam);

    CouponInfo getCouponInfo(String id);

    /**
     * 根据couponId获取拥有该优惠规则的商品(SKU)/商品种类(Category)信息
     * 需要RPC调用产品服务
     * @param couponId coupon id
     * @return Map 包含两个字段 skuList 和 Category List
     */
    Map<String, Object> findCouponRuleList(Long couponId);

    void saveCouponRule(CouponRuleVo couponRuleVo);

    List<CouponInfo> findCouponByKeyword(String keyword);
}
