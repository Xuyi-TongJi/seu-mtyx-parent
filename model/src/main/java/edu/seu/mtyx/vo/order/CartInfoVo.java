package edu.seu.mtyx.vo.order;

import edu.seu.mtyx.model.activity.ActivityRule;
import edu.seu.mtyx.model.order.CartInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * OrderDetailActivity
 * </p>
 *
 * @author qy
 */
@Data
public class CartInfoVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 购物项凑单，同一活动对应的最优活动规则
	 */
	@ApiModelProperty(value = "cartInfoList")
	private List<CartInfo> cartInfoList;

	@ApiModelProperty(value = "活动规则")
	private ActivityRule activityRule;


}

