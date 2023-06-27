package edu.seu.mtyx.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.activity.constant.Constants;
import edu.seu.mtyx.activity.mapper.CouponInfoMapper;
import edu.seu.mtyx.activity.mapper.CouponRangeMapper;
import edu.seu.mtyx.activity.mapper.CouponUseMapper;
import edu.seu.mtyx.activity.service.CouponInfoService;
import edu.seu.mtyx.client.product.ProductFeignClient;
import edu.seu.mtyx.model.activity.CouponInfo;
import edu.seu.mtyx.vo.activity.CouponRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

    @Autowired
    private CouponInfoMapper couponInfoMapper;

    @Autowired
    private CouponUseMapper couponUseMapper;

    @Autowired
    private CouponRangeMapper couponRangeMapper;

    /**
     * feign client 发起远程调用
     */
    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public IPage<CouponInfo> selectPage(Page<CouponInfo> pageParam) {
        // 按ID降序排序
        QueryWrapper<CouponInfo> wrapper = new QueryWrapper<CouponInfo>().orderByDesc(Constants.ID);
        IPage<CouponInfo> pages = couponInfoMapper.selectPage(pageParam, wrapper);
        for (CouponInfo couponInfo : pages.getRecords()) {
            if (couponInfo.getCouponType() != null) {
                couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
            }
            if (couponInfo.getRangeType() != null) {
                couponInfo.setRangeTypeString(couponInfo.getRangeType().getComment());
            }
        }
        return pages;
    }

    @Override
    public CouponInfo getCouponInfo(String id) {
        CouponInfo couponInfo = this.getById(id);
        couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
        if (null != couponInfo.getRangeType()) {
            couponInfo.setRangeTypeString(couponInfo.getRangeType().getComment());
        }
        return couponInfo;
    }

    @Override
    public Object findCouponRuleList(Long id) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCouponRule(CouponRuleVo couponRuleVo) {
        // 删除couponRange
    }

    @Override
    public List<CouponInfo> findCouponByKeyword(String keyword) {
        return couponInfoMapper.selectList(new QueryWrapper<CouponInfo>().like(Constants.COUPON_NAME, keyword));
    }
}
