package edu.seu.mtyx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.model.product.Category;
import edu.seu.mtyx.product.mapper.CategoryMapper;
import edu.seu.mtyx.product.service.CategoryService;
import edu.seu.mtyx.vo.product.CategoryQueryVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public IPage<Category> selectPage(Page<Category> pageParam, CategoryQueryVo categoryQueryVo) {

        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();

        String keyword = categoryQueryVo.getName();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(new SFunction<Category, String>() {
                @Override
                public String apply(Category category) {
                    return category.getName();
                }
            }, keyword);
        }

        return categoryMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public List<Category> findAllList() {
        // 按sort字段升序
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<Category>().orderByAsc(new SFunction<Category, Integer>() {
            @Override
            public Integer apply(Category category) {
                return category.getSort();
            }
        });
        return categoryMapper.selectList(wrapper);
    }
}
