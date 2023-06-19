package edu.seu.mtyx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.model.sys.Region;

import java.util.List;

public interface RegionService extends IService<Region> {

    List<Region> findRegionByKeyword(String keyword);
}
