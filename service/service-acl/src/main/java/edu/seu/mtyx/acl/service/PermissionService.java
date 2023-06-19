package edu.seu.mtyx.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.seu.mtyx.model.acl.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {

    /**
     * 获取所有菜单列表
     * @return 所有菜单列表
     */
    List<Permission> queryAllMenu();

    /**
     * 递归删除
     * @param id 删除的id
     * @return 是否删除成功
     */
    boolean removeChildById(Long id);

}
