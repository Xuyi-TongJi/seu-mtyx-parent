package edu.seu.mtyx.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.model.activity.ActivityInfo;
import edu.seu.mtyx.model.activity.ActivityRule;
import edu.seu.mtyx.model.product.SkuInfo;
import edu.seu.mtyx.vo.activity.ActivityRuleVo;

import java.util.List;
import java.util.Map;

public interface ActivityInfoService extends IService<ActivityInfo> {
    void saveActivityRule(ActivityRuleVo activityRuleVo);

    Map<String, Object> findActivityRuleList(Long id);

    List<ActivityRule> findActivityRule(Long skuId);

    List<SkuInfo> findSkuInfoByKeyword(String keyword);

    IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam);
}
