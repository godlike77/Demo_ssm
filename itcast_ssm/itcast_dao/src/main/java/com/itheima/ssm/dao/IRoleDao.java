package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IRoleDao {

    //根据用户id查询出所有对应的角色
    @Select("select * from role where id in (select roleId from users_role where userId=#{userId})")

    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "permissions",column = "id",
            many = @Many(select = "com.itheima.ssm.dao.IPermissionDao.findPermissionByRoleId")
            )
    })
    public List<Role> findRoleByUserId(String userId) throws Exception;


    @Select("select * from role ")
    List<Role> findAll() throws Exception;

    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    void save(Role role);

    @Delete("delete  from role where id= #{id}")
    void delete(String id)throws Exception;
    @Delete("delete from users_role where roleId=#{id}")
    void deleteFromUser_RoleByRoleId(String id);
    @Delete("delete from role_permission where roleId=#{id}")
    void deleteFromRole_PermissionByRoleId(String id);

    @Select("select * from role where id=#{roleId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "permissions",column = "id",many =@Many(select = "com.itheima.ssm.dao.IPermissionDao.findPermissionByRoleId") )
    })
    Role findById(String roleId);

    @Select("select * from permission where id not in " +
            "(select permissionId from role_permission where roleId=#{roleId})"
    )
    List<Permission> findOtherPermissions(String roleId);

    @Insert("insert into role_permission(roleId,permissionId) values(#{roleId},#{permissionId})")
    void addPermissionToRole(@Param("roleId") String roleId, @Param("permissionId") String permissionId);


}
