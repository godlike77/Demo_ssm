package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUserDao {
    @Select("select * from users where id=#{id}")
    @Results({@Result(id=true,property ="id",column = "id"),
            @Result(property = "roles",column ="id",
                    many = @Many(select = "com.itheima.ssm.dao.IRoleDao.findRoleByUserId"))})
    public UserInfo findById(String id) throws Exception;




    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "roles",column = "id",many =
            @Many(select = "com.itheima.ssm.dao.IRoleDao.findRoleByUserId"))
    })
    @Select("select * from users where username=#{username}")

    public  UserInfo findByUsername(String username) throws Exception;



    @Select("select * from users ")
    List<UserInfo> findAll() throws Exception;

    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
    void save(UserInfo userInfo) throws Exception;

    @Select("select * from role where id not in (select roleId from users_role where userId=#{userId})")
    List<Role> findOtherRoles(String userId);

    @Insert("insert into users_role(userId,roleId) values(#{userId},#{roleId})")
    void addRoleToUser(@Param("userId") String userId, @Param("roleId") String roleId);
}
