package com.itheima.ssm.controller;


import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private ISysLogService sysLogService;
    @Autowired
    private HttpServletRequest request;
    private Date visitTime; //开始的时间
    private Class clazz;//访问的类
    private Method method;//访问的方法
    //前置通知，主要是获取开始的时间，执行的类是哪一个，执行的方法是哪个
    @Before("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp)throws NoSuchMethodException{
        visitTime  = new Date();//开始访问的时间
        clazz = jp.getTarget().getClass();//访问的类
        String methodName = jp.getSignature().getName();//获取访问的方法名称
        Object[] args = jp.getArgs();//获取访问的方法参数
        //获取具体执行的方法的method对象
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
         method = methodSignature.getMethod();
    }


    @After("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp)throws Exception{
        long time = new Date().getTime()-visitTime.getTime();//获取访问的时长
        if(clazz!=null&& method != null && clazz != LogAop.class){
            //获取类上的@RequestMapping("/orders")
            RequestMapping clazzAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (clazzAnnotation!=null){
                String[] classValue = clazzAnnotation.value();
                //获取方法上的@RequestMapping()
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();
                    //获取url
                   String url= classValue[0]+methodValue[0];
                   //获取访问的ip
                    String ip = request.getRemoteAddr();
                    //获取当前操作的用户
                    SecurityContext context = SecurityContextHolder.getContext();
                    //从上下文中获得当前登录的用户
                    String username = context.getAuthentication().getName();

                    //将日志信息封装到SysLog对象中
                    SysLog sysLog = new SysLog();
                    sysLog.setExecutionTime(time);
                    sysLog.setIp(ip);
                    sysLog.setMethod("[类名]"+clazz.getName()+"[方法名]"+method.getName());
                    sysLog.setUrl(url);
                    sysLog.setUsername(username);
                    sysLog.setVisitTime(visitTime);
                    //调用service操作
                    sysLogService.save(sysLog);
                }
            }
        }
    }
}
