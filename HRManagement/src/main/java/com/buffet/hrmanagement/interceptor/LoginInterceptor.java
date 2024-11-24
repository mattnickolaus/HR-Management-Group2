package com.buffet.hrmanagement.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Check if the user is logged in in current session
        if (request.getSession().getAttribute("loggedInUser") == null) {
            // Redirect to the login page if they are not
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}

