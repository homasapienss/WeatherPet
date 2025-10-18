package edu.homasapienss.weather.interceptors;

import edu.homasapienss.weather.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizeInterceptor implements HandlerInterceptor {
    private final SessionService sessionService;

    @Autowired
    public AuthorizeInterceptor(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (sessionService.isSessionValid(request, response)) {
            return true;
        } else {
            response.sendRedirect("/auth/sign-in");
            return false;
        }
    }
}
