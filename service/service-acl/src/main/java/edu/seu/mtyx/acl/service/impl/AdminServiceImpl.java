package edu.seu.mtyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.acl.mapper.AdminMapper;
import edu.seu.mtyx.acl.service.AdminService;
import edu.seu.mtyx.acl.service.RoleService;
import edu.seu.mtyx.model.acl.Admin;
import edu.seu.mtyx.vo.acl.AdminQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public IPage<Admin> selectPage(Page<Admin> pageParam, AdminQueryVo adminQueryVo) {
        String adminName = adminQueryVo.getName();
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(adminName)) {
            // 模糊查询
            wrapper.like(new SFunction<Admin, Object>() {

                @Override
                public Object apply(Admin admin) {
                    return admin.getRoleName();
                }
            }, adminName);
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }
}
