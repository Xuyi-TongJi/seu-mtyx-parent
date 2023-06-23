package edu.seu.mtyx.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.model.product.SkuPoster;

import java.util.List;

public interface SkuPosterService extends IService<SkuPoster> {

    List<SkuPoster> findBySkuId(Long id);
}
