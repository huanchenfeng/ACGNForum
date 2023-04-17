package com.ACGN.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object username = request.getSession().getAttribute("username");

        if (username==null){
            request.setAttribute("msg","您还未登录，请先登录");
            request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }

        return true;
    }

}
