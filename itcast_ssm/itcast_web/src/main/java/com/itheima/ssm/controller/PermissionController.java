package com.itheima.ssm.controller;


import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception{

        ModelAndView modelAndView =new ModelAndView();
            List<Permission> permissionList = permissionService.findAll();
             modelAndView.addObject("permissionList",permissionList);
             modelAndView.setViewName("permission-list");
             return modelAndView;
    }

    //给角色添加新的权限

    @RequestMapping("/save.do")

    public String save(Permission permission)throws Exception{
            permissionService.save(permission);
            return "redirect:findAll.do";
    }

}
