package edu.seu.mtyx.activity.service.impl;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
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
import edu.seu.mtyx.enums.CouponRangeType;
import edu.seu.mtyx.model.activity.CouponInfo;
import edu.seu.mtyx.model.activity.CouponRange;
import edu.seu.mtyx.model.product.Category;
import edu.seu.mtyx.model.product.SkuInfo;
import edu.seu.mtyx.vo.activity.CouponRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Map<String, Object> findCouponRuleList(Long couponId) {
        Map<String, Object> result = new HashMap<>();

        CouponInfo couponInfo = this.getById(couponId);

        QueryWrapper<CouponRange> couponRangeQueryWrapper = new QueryWrapper<>();
        couponRangeQueryWrapper.eq(Constants.COUPON_ID, couponId);
        List<CouponRange> activitySkuList = couponRangeMapper.selectList(couponRangeQueryWrapper);

        List<Long> rangeIdList = activitySkuList.stream().map(CouponRange::getRangeId).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(rangeIdList)) {
            if (couponInfo.getRangeType() == CouponRangeType.SKU) {
                // PRC
                List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(rangeIdList);
                result.put("skuInfoList", skuInfoList);

            } else if (couponInfo.getRangeType() == CouponRangeType.CATEGORY) {
                // RPC
                List<Category> categoryList = productFeignClient.findCategoryList(rangeIdList);
                result.put("categoryList", categoryList);
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCouponRule(CouponRuleVo couponRuleVo) {
        // 删除couponRange， 更新couponInfo，新增couponRange
        QueryWrapper<CouponRange> wrapper =
                new QueryWrapper<CouponRange>().eq(Constants.COUPON_ID, couponRuleVo.getCouponId());
        couponRangeMapper.delete(wrapper);

        // 更新
        CouponInfo couponInfo = this.getById(couponRuleVo.getCouponId());
        if (couponInfo == null) {
            return;
        }
        couponInfo.setRangeType(couponRuleVo.getRangeType());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setAmount(couponRuleVo.getAmount());
        couponInfo.setRangeDesc(couponRuleVo.getRangeDesc());

        couponInfoMapper.updateById(couponInfo);

        List<CouponRange> couponRangeList = couponRuleVo.getCouponRangeList();
        for (CouponRange couponRange : couponRangeList) {
            couponRange.setCouponId(couponRuleVo.getCouponId());
            //  插入数据
            couponRangeMapper.insert(couponRange);
        }
    }

    @Override
    public List<CouponInfo> findCouponByKeyword(String keyword) {
        return couponInfoMapper.selectList(new QueryWrapper<CouponInfo>().like(Constants.COUPON_NAME, keyword));
    }
}
