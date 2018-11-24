package com.itheima.ssm.controller;


import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;



    //查询用户可添加的角色
    @RequestMapping("/findUserByIdAndAllRole.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id" ,required = true)String userId)throws Exception{
                ModelAndView modelAndView=new ModelAndView();
                //根据用户id查询用户
                 UserInfo userInfo = userService.findById(userId);
                 //根据用户id查询可以添加用户的角色
                 List<Role> otherRoles =userService.findOtherRoles(userId);
                 modelAndView.addObject("user",userInfo);
                 modelAndView.addObject("roleList",otherRoles);
                 modelAndView.setViewName("user-role-add");
                 return modelAndView;
    }
    //给用户添加角色
    @RequestMapping("/addRoleToUser.do")
    public  String addRoleToUser(@RequestParam(name ="userId",required = true) String userId,@RequestParam(name ="ids" ) String[] roleIds) throws Exception{
                userService.addRoleToUser(userId,roleIds);
                return "redirect:findAll.do";
    }

    //查询用户
    @RequestMapping("/findAll.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findAll() throws Exception{
            ModelAndView modelAndView =new ModelAndView();
           List<UserInfo> userList = userService.findAll();
            modelAndView.addObject("userList",userList);
            modelAndView.setViewName("user-list");
            return modelAndView;
    }

    //添加用户
    @RequestMapping("/save.do")
    @PreAuthorize("authentication.principal.username =='tom'")
    public String save(UserInfo userInfo) throws Exception{
        userService.save(userInfo);
        return "redirect:findAll.do";
    }

    //用户详情查询
    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        UserInfo userInfo= userService.findById(id);
        modelAndView.addObject("user",userInfo);
        modelAndView.setViewName("user-show");
        return modelAndView;
    }


}
