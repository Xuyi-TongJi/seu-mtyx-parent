package edu.seu.mtyx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.model.sys.Region;
import edu.seu.mtyx.sys.mapper.RegionMapper;
import edu.seu.mtyx.sys.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public List<Region> findRegionByKeyword(String keyword) {
        LambdaQueryWrapper<Region> wrapper = new LambdaQueryWrapper<Region>().like(new SFunction<Region, String>() {
            @Override
            public String apply(Region region) {
                return region.getName();
            }
        }, keyword);
        return regionMapper.selectList(wrapper);
    }
}
