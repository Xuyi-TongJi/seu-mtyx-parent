package edu.seu.mtyx.vo.product;

import edu.seu.mtyx.model.product.*;
import edu.seu.mtyx.model.product.SkuAttrValue;
import edu.seu.mtyx.model.product.SkuImage;
import edu.seu.mtyx.model.product.SkuInfo;
import edu.seu.mtyx.model.product.SkuPoster;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SkuInfoVo extends SkuInfo {

	@ApiModelProperty(value = "海报列表")
	private List<SkuPoster> skuPosterList;

	@ApiModelProperty(value = "属性值")
	private List<SkuAttrValue> skuAttrValueList;

	@ApiModelProperty(value = "图片")
	private List<SkuImage> skuImagesList;

}

