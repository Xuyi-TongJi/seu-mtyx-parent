package edu.seu.mtyx.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.model.product.SkuInfo;
import edu.seu.mtyx.vo.product.SkuInfoQueryVo;

public interface SkuInfoService extends IService<SkuInfo> {
    IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);
}
