package edu.seu.mtyx.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.model.product.SkuImage;

import java.util.List;

public interface SkuImageService extends IService<SkuImage> {
    List<SkuImage> findBySkuId(Long id);
}
