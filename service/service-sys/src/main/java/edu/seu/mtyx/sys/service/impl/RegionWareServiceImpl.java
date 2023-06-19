package edu.seu.mtyx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.common.exception.MtyxException;
import edu.seu.mtyx.common.result.ResultCodeEnum;
import edu.seu.mtyx.model.sys.RegionWare;
import edu.seu.mtyx.sys.mapper.RegionWareMapper;
import edu.seu.mtyx.sys.service.RegionWareService;
import edu.seu.mtyx.vo.sys.RegionWareQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements RegionWareService {

    @Autowired
    private RegionWareMapper regionWareMapper;

    @Override
    public IPage<RegionWare> selectPage(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo) {
        // 模糊查询
        String keyword = regionWareQueryVo.getKeyword();
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(new SFunction<RegionWare, String>() {
                @Override
                public String apply(RegionWare regionWare) {
                    return regionWare.getRegionName();
                }
            }, keyword).or().like(new SFunction<RegionWare, String>() {
                @Override
                public String apply(RegionWare regionWare) {
                    return regionWare.getWareName();
                }
            }, keyword);
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public void saveRegionWare(RegionWare regionWare) {
        // 查询是否已经存在该Region的仓库
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(new SFunction<RegionWare, Long>() {
            @Override
            public Long apply(RegionWare regionWare) {
                return regionWare.getRegionId();
            }
        }, regionWare.getRegionId());
        Long count = regionWareMapper.selectCount(wrapper);
        if (count > 0L) {
            throw new MtyxException(ResultCodeEnum.REGION_OPEN);
        }

        // save
        regionWareMapper.insert(regionWare);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        RegionWare regionWare = baseMapper.selectById(id);
        regionWare.setStatus(status);
        baseMapper.updateById(regionWare);
    }
}
