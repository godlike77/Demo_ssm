package com.itheima.ssm.controller;


import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //查询用户
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception{
            ModelAndView modelAndView =new ModelAndView();
           List<UserInfo> userList = userService.findAll();
            modelAndView.addObject("userList",userList);
            modelAndView.setViewName("user-list");
            return modelAndView;
    }

    //添加用户
    @RequestMapping("/save.do")
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
