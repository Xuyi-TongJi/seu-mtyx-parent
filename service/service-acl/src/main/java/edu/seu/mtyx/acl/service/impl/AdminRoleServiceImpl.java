package edu.seu.mtyx.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.seu.mtyx.acl.mapper.AdminRoleMapper;
import edu.seu.mtyx.acl.service.AdminRoleService;
import edu.seu.mtyx.model.acl.AdminRole;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {
}
