package com.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {

    // controller가 시전되기 후에 작동하는 것
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        super.postHandle(request, response, handler, modelAndView);
        // 주소정보
        String pathName = request.getServletPath();
        // 쿼리정보
        String queryName = request.getQueryString();

        System.out.println(pathName + "," + queryName);

        // 세션객체 가져오기
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("BACKURL", pathName);
        System.out.println("thisssssssssssss: " + queryName);
        // 세션에 추가하기
        if (queryName != null) {
            httpSession.setAttribute("BACKURL", pathName + "?" + queryName);
        }

        System.out.println("interceptor------------------");
        System.out.println("this : " + pathName + ", " + queryName);
        System.out.println("BACKURL : " + httpSession.getAttribute("BACKURL"));
    }

    // prehandler는 시전되기 전에 작동하는 것

}
