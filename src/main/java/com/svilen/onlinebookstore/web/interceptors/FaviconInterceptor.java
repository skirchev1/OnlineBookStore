package com.svilen.onlinebookstore.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        String link = "https://img.favpng.com/17/7/10/e-book-computer-icons-clip-art-png-favpng-k2reA3N0V4ycRgySD2B7MhetY.jpg";

        if (modelAndView != null) {
            modelAndView.addObject("favicon", link);
        }
    }
}
