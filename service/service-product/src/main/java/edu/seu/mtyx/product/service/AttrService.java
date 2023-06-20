package edu.seu.mtyx.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.model.product.Attr;

import java.util.List;

public interface AttrService extends IService<Attr> {
    List<Attr> findByAttrGroupId(Long attrGroupId);
}
