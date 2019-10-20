package com.xmg.p2p.base.util;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 专门用来登陆拦截的拦截器
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //判断要访问的方法是否需要登陆验证
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequireLogin methodAnnotation = handlerMethod.getMethodAnnotation(RequireLogin.class);
            //如果要访问的方法需要登陆验证，则校验是否已经登陆，未登陆则重定向到登陆页面
            if(methodAnnotation != null && UserContext.getCurrent() == null){
                response.sendRedirect("/login.html");
                return false;
            }
        }
        return super.preHandle(request,response,handler);
    }

}
