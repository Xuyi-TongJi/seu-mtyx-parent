package edu.seu.mtyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.acl.mapper.PermissionMapper;
import edu.seu.mtyx.acl.service.PermissionService;
import edu.seu.mtyx.acl.utils.Constants;
import edu.seu.mtyx.model.acl.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> queryAllMenu() {
        List<Permission> permissions = permissionMapper.selectList(new QueryWrapper<Permission>());
        return PermissionHelper.build(permissions);
    }

    @Override
    public boolean removeChildById(Long id) {
        List<Long> permissions = new ArrayList<>();
        selectChildren(id, permissions);
        return permissionMapper.deleteBatchIds(permissions) == permissions.size();
    }

    private void selectChildren(Long id, List<Long> permissions) {
        permissions.add(id);
        // query child
        List<Permission> children = permissionMapper.selectList(new QueryWrapper<Permission>().eq(Constants.P_ID, id).select(Constants.ID));
        for (Permission child : children) {
            selectChildren(child.getId(), permissions);
        }
    }

    static class PermissionHelper {

        /**
         * 使用递归方法建菜单,填充Permission实体类的level和children字段
         * @param treeNodes 所有permission
         * @return 树节点的Permission List
         */
        public static List<Permission> build(List<Permission> treeNodes) {
            List<Permission> result = new ArrayList<>();
            for (Permission node : treeNodes) {
                if (node.getPid() == 0L) {
                    node.setLevel(1);
                    node.setChildren(new ArrayList<Permission>());
                    buildChildren(node, treeNodes);
                }
            }
            return result;
        }

        private static void buildChildren(Permission parent, List<Permission> treeNodes) {
            for (Permission node : treeNodes) {
                if (node.getPid().longValue() == parent.getId().longValue()) {
                    node.setLevel(parent.getLevel() + 1);
                    if (node.getChildren() == null) {
                        node.setChildren(new ArrayList<Permission>());
                    }
                    buildChildren(node, treeNodes);
                    parent.getChildren().add(node);
                }
            }
        }
    }
}
