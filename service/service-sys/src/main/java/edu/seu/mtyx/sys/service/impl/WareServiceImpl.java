package edu.seu.mtyx.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.model.sys.Ware;
import edu.seu.mtyx.sys.mapper.WareMapper;
import edu.seu.mtyx.sys.service.WareService;
import org.springframework.stereotype.Service;

@Service
public class WareServiceImpl extends ServiceImpl<WareMapper, Ware> implements WareService {
}
