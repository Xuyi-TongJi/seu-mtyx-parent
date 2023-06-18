package edu.seu.mtyx.acl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.model.acl.Role;
import edu.seu.mtyx.vo.acl.RoleQueryVo;

import java.util.Map;

public interface RoleService extends IService<Role> {

    /**
     * 分页查询
     * @param pageParam 分页配置（一页几个数据等）
     * @param roleQueryVo 查询条件
     * @return 查询结果
     */
    IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo);

    /**
     * 根据管理员Id查询其角色
     * @param adminId 管理员Id
     * @return 角色Map
     */
    Map<String, Object> findRoleById(Long adminId);

    /**
     * 为管理员分配角色
     * @param adminId 管理员Id
     * @param roleIds 角色id列表
     */
    void saveUserRoleRelationShip(Long adminId, Long[] roleIds);
}
