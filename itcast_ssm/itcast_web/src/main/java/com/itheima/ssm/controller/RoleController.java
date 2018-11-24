package com.itheima.ssm.controller;


import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.service.IRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;


        @RequestMapping("/findRoleByIdAndAllPermission.do")
        public ModelAndView findRoleByIdAndAllPermission(@RequestParam(name ="id")String roleId)throws Exception{
                   ModelAndView modelAndView=new ModelAndView();
                        Role role=roleService.findById(roleId);
                 List<Permission> otherPermissions  =roleService.findOtherPermissions(roleId);
            modelAndView.addObject("role", role);
            modelAndView.addObject("permissionList", otherPermissions);
            modelAndView.setViewName("role-permission-add");
            return modelAndView;

        }

        @RequestMapping("/addPermissionToRole.do")
        public String addPermissionToRole(@RequestParam(name = "roleId")String roleId,@RequestParam(name = "ids")String[]permissionIds)throws Exception{
            roleService.addPermissionToRole(roleId,permissionIds);
            return "redirect:findAll.do";
        }
        @RequestMapping("findAll.do")
        public ModelAndView findAll() throws Exception{
            ModelAndView modelAndView=new ModelAndView();
         List<Role> roleList= roleService.findAll();
            modelAndView.addObject("roleList",roleList);
            modelAndView.setViewName("role-list");
            return modelAndView;
        }


    @RequestMapping("/save.do")
    public String save(Role role) throws Exception{
        roleService.save(role);
        return "redirect:findAll.do";

    }

    @RequestMapping("/deleteRole.do")
    public String delete(String id)throws Exception{
            roleService.delete(id);
            return "redirect:findAll.do";
    }

}
