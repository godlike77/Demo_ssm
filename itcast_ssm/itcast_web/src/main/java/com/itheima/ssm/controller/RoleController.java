package com.itheima.ssm.controller;


import com.itheima.ssm.domain.Role;
import com.itheima.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

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
