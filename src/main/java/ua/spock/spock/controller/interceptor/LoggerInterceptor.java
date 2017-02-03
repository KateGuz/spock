package ua.spock.spock.controller.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ua.spock.spock.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class LoggerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

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
        MDC.put("userEmail", " " + userEmail);
        MDC.put("requestId", " " + requestId);
        long startTime = System.currentTimeMillis();
        MDC.put("startTime", String.valueOf(startTime));
        if (logger.isDebugEnabled()) {
            logger.debug("request processing started");
        }
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView)
            throws Exception {
        long startTime = Long.valueOf(MDC.get("startTime"));
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;

        if (logger.isDebugEnabled()) {
            logger.debug("request processing completed, executeTime : " + executeTime + "ms");
        }

        MDC.clear();
    }
}
