package ua.spock.spock.controller.interceptor;

import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ua.spock.spock.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class LoggerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        String userEmail;
        String requestId = UUID.randomUUID().toString();
        if (session != null && session.getAttribute("loggedUser") != null) {
            userEmail = ((User) session.getAttribute("loggedUser")).getEmail();
        } else {
            userEmail = "guest";
        }
        MDC.put("userEmail", userEmail);
        MDC.put("requestId", requestId);
        MDC.put("space", " ");
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView)
            throws Exception {
        MDC.clear();
    }
}
