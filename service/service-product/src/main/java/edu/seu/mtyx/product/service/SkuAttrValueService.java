package edu.seu.mtyx.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.model.product.SkuAttrValue;

import java.util.List;

public interface SkuAttrValueService extends IService<SkuAttrValue> {
    List<SkuAttrValue> findBySkuId(Long id);
}
