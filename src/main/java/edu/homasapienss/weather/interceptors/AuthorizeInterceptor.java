package edu.homasapienss.weather.interceptors;

import edu.homasapienss.weather.services.SessionService;
import edu.homasapienss.weather.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizeInterceptor implements HandlerInterceptor {
    private final SessionService sessionService;
    private final UserService userService;

    @Autowired
    public AuthorizeInterceptor(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (sessionService.isSessionValid(request, response)) {
            request.setAttribute("user", userService.getUser((Long) request.getAttribute("user_id")));
            return true;
        } else {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return false;
        }
    }
}
