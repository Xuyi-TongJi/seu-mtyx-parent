package edu.seu.mtyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.acl.service.AdminRoleService;
import edu.seu.mtyx.acl.service.RoleService;
import edu.seu.mtyx.acl.mapper.RoleMapper;
import edu.seu.mtyx.model.acl.AdminRole;
import edu.seu.mtyx.model.acl.Role;
import edu.seu.mtyx.vo.acl.RoleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static edu.seu.mtyx.acl.utils.Constants.*;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private AdminRoleService adminRoleService;

    @Override
    public IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {
        String roleName = roleQueryVo.getRoleName();
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(roleName)) {
            // 模糊查询
            wrapper.like(new SFunction<Role, Object>() {

                @Override
                public Object apply(Role role) {
                    return role.getRoleName();
                }
            }, roleName);
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public Map<String, Object> findRoleById(Long adminId) {
        if (StringUtils.isEmpty(String.valueOf(adminId))) {
            return new HashMap<>();
        }

        // 查询所有角色
        List<Role> allRoleList = baseMapper.selectList(null);

        // 拥有的角色id
        List<AdminRole> adminUserList = adminRoleService.list(new QueryWrapper<AdminRole>().eq(ADMIN_ID, adminId).select(ROLE_ID));
        Set<Long> adminUserIdSet = new HashSet<>();
        for (AdminRole adminRole : adminUserList) {
            adminUserIdSet.add(adminRole.getRoleId());
        }
        List<Role> assignRoleList = new ArrayList<>();
        for (Role role : allRoleList) {
            if (adminUserIdSet.contains(role.getId())) {
                assignRoleList.add(role);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put(ALL_ROLES_LIST, allRoleList);
        result.put(ASSIGN_ROLES, assignRoleList);
        return result;
    }

    @Override
    public void saveUserRoleRelationShip(Long adminId, Long[] roleIds) {
        if (StringUtils.isEmpty(String.valueOf(adminId)) || roleIds == null || roleIds.length == 0) {
            return;
        }

        // 删除用户分配的角色数据
        QueryWrapper<AdminRole> wrapper = new QueryWrapper<AdminRole>().eq(ADMIN_ID, adminId);
        adminRoleService.remove(wrapper);

        // 分配新的角色
        List<AdminRole> list = new ArrayList<>();
        for (Long id : roleIds) {
            if (StringUtils.isEmpty(String.valueOf(id))) {
                continue;
            }
            AdminRole adminRole = new AdminRole();
            adminRole.setRoleId(id);
            adminRole.setAdminId(adminId);
            list.add(adminRole);
        }
        adminRoleService.saveBatch(list);
    }
}
