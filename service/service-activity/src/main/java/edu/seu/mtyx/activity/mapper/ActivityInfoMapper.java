package edu.seu.mtyx.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.seu.mtyx.model.activity.ActivityInfo;
import edu.seu.mtyx.model.activity.ActivityRule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityInfoMapper extends BaseMapper<ActivityInfo> {

    /**
     * 根据商品id查询活动规则
     * @param skuId sku id
     * @return 活动规则
     */
    List<ActivityRule> selectActivityRuleList(@Param("skuId") Long skuId);

    /**
     * 查询已经存在活动的sku id
     * @param skuIdList skuId list
     * @return 已经存在促销活动的sku id
     */
    List<Long> selectExistSkuIdList(@Param("skuIdList") List<Long> skuIdList);
}
