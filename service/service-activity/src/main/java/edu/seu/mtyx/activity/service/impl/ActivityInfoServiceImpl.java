package edu.seu.mtyx.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.activity.constant.Constants;
import edu.seu.mtyx.activity.mapper.ActivityInfoMapper;
import edu.seu.mtyx.activity.mapper.ActivityRuleMapper;
import edu.seu.mtyx.activity.mapper.ActivitySkuMapper;
import edu.seu.mtyx.activity.service.ActivityInfoService;
import edu.seu.mtyx.client.product.ProductFeignClient;
import edu.seu.mtyx.enums.ActivityType;
import edu.seu.mtyx.model.activity.ActivityInfo;
import edu.seu.mtyx.model.activity.ActivityRule;
import edu.seu.mtyx.model.activity.ActivitySku;
import edu.seu.mtyx.model.product.SkuInfo;
import edu.seu.mtyx.vo.activity.ActivityRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {

    /**
     * 远程调用客户端，注入productFeignClient发起远程调用
     */
    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private ActivityInfoMapper activityInfoMapper;

    @Autowired
    private ActivityRuleMapper activityRuleMapper;

    @Autowired
    private ActivitySkuMapper activitySkuMapper;

    /**
     * 保存活动规则
     */
    @Override
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {
        activityRuleMapper.delete(new QueryWrapper<ActivityRule>().eq(Constants.ACTIVITY_ID, activityRuleVo.getActivityId()));
        activitySkuMapper.delete(new QueryWrapper<ActivitySku>().eq(Constants.ACTIVITY_ID, activityRuleVo.getActivityId()));

        List<ActivityRule> activityRuleList = activityRuleVo.getActivityRuleList();
        List<ActivitySku> activitySkuList = activityRuleVo.getActivitySkuList();
        List<Long> couponIdList = activityRuleVo.getCouponIdList(); // TODO

        ActivityInfo activityInfo = activityInfoMapper.selectById(activityRuleVo.getActivityId());
        for (ActivityRule activityRule : activityRuleList) {
            activityRule.setActivityId(activityRuleVo.getActivityId());
            activityRule.setActivityType(activityInfo.getActivityType());
            activityRuleMapper.insert(activityRule);
        }

        for (ActivitySku activitySku : activitySkuList) {
            activitySku.setActivityId(activityRuleVo.getActivityId());
            activitySkuMapper.insert(activitySku);
        }

    }

    /**
     * 活动规则列表方法
     * @param id activity id
     * @return 结果Map
     */
    @Override
    public Map<String, Object> findActivityRuleList(Long id) {
        if (id == null) {
            return new HashMap<>();
        }

        // activity rule list
        Map<String, Object> result = new HashMap<>();
        QueryWrapper<ActivityRule> wrapper = new QueryWrapper<ActivityRule>().eq(Constants.ACTIVITY_ID, id);
        List<ActivityRule> activityRules = activityRuleMapper.selectList(wrapper);
        result.put(Constants.ACTIVITY_RULE_LIST, activityRules);

        // skuInfo List (RPC)
        QueryWrapper<ActivitySku> wrapper1 = new QueryWrapper<ActivitySku>().eq(Constants.ACTIVITY_ID, id);
        List<ActivitySku> activitySkus = activitySkuMapper.selectList(wrapper1);
        List<Long> skuIds = new ArrayList<>();
        for (ActivitySku activitySku : activitySkus) {
            skuIds.add(activitySku.getSkuId());
        }
        // RPC
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(skuIds);
        result.put(Constants.SKU_INFO_LIST, skuInfoList);

        return result;
    }

    /**
     * 查询商品获取规则数据
     * @param skuId sku id
     * @return 规则数据
     */
    @Override
    public List<ActivityRule> findActivityRule(Long skuId) {
        List<ActivityRule> activityRuleList = activityInfoMapper.selectActivityRuleList(skuId);
        for (ActivityRule activityRule :  activityRuleList) {
            activityRule.setRuleDesc(this.getRuleDesc(activityRule)); // 规则优惠描述
        }
        return activityRuleList;
    }

    private String getRuleDesc(ActivityRule activityRule) {
        ActivityType activityType = activityRule.getActivityType();
        StringBuilder ruleDesc = new StringBuilder();
        // 满减
        if (activityType == ActivityType.FULL_REDUCTION) {
            ruleDesc
                    .append("满")
                    .append(activityRule.getConditionAmount())
                    .append("元减")
                    .append(activityRule.getBenefitAmount())
                    .append("元");
        } else {
            ruleDesc
                    .append("满")
                    .append(activityRule.getConditionNum())
                    .append("元打")
                    .append(activityRule.getBenefitDiscount())
                    .append("折");
        }
        return ruleDesc.toString();
    }


    /**
     * 根据keyword搜索没有参加任何活动的sku信息
     * @param keyword keyword
     * @return sku info list which not exists in any activity
     */
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoByKeyword(keyword);
        List<Long> skuIdList = skuInfoList.stream().map(SkuInfo::getId).collect(Collectors.toList());

        List<SkuInfo> notExistSkuInfoList = new ArrayList<>();
        //已经存在的skuId，一个sku只能参加一个促销活动，所以存在的得排除
        List<Long> existSkuIdList = activityInfoMapper.selectExistSkuIdList(skuIdList);
        Set<Long> existSkuIdSet = new HashSet<>(existSkuIdList);
        for (SkuInfo skuInfo : skuInfoList) {
            if (!existSkuIdSet.contains(skuInfo.getId())) {
                notExistSkuInfoList.add(skuInfo);
            }
        }
        return notExistSkuInfoList;
    }

    @Override
    public IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam) {
        QueryWrapper<ActivityInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        IPage<ActivityInfo> page = activityInfoMapper.selectPage(pageParam, queryWrapper);
        page.getRecords().forEach(item -> {
            item.setActivityTypeString(item.getActivityType().getComment());
        });
        return page;
    }
}
