package com.findany.productservice.config;

import com.findany.productservice.constants.AppConstants;
import com.findany.productservice.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthHeaderInterceptor implements HandlerInterceptor {

    @Autowired
    private UserDetails userDetails;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader(AppConstants.X_USER_ID);
        userDetails.setUserId(userId);
        return true;
    }
}
