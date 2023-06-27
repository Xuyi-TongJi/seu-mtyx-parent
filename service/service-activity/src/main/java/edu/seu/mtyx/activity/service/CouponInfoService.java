package edu.seu.mtyx.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.activity.mapper.CouponInfoMapper;
import edu.seu.mtyx.model.activity.CouponInfo;
import edu.seu.mtyx.vo.activity.CouponRuleVo;

import java.util.List;

public interface CouponInfoService extends IService<CouponInfo> {
    IPage<CouponInfo> selectPage(Page<CouponInfo> pageParam);

    CouponInfo getCouponInfo(String id);

    Object findCouponRuleList(Long id);

    void saveCouponRule(CouponRuleVo couponRuleVo);

    List<CouponInfo> findCouponByKeyword(String keyword);
}
