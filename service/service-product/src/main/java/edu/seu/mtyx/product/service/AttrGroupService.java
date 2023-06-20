package edu.seu.mtyx.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.model.product.AttrGroup;
import edu.seu.mtyx.vo.product.AttrGroupQueryVo;

import java.util.List;

public interface AttrGroupService extends IService<AttrGroup> {
    IPage<AttrGroup> selectPage(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo);

    List<AttrGroup> findAllList();
}
