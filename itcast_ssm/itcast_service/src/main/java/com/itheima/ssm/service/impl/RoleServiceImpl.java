package com.itheima.ssm.service.impl;

import com.itheima.ssm.dao.IRoleDao;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService{
    @Autowired
    private IRoleDao roleDao;

    @Override
    public List<Role> findAll() throws  Exception{

        return roleDao.findAll() ;
    }

    @Override
    public void save(Role role) {
        roleDao.save( role);
    }

    @Override
    public void delete(String id) throws Exception {
        //删除相关的中间表和本表
        roleDao.deleteFromUser_RoleByRoleId(id);
        roleDao.deleteFromRole_PermissionByRoleId(id);
         roleDao.delete(id);
    }
}
