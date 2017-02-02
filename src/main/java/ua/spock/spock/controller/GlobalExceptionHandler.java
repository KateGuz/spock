package ua.spock.spock.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleBadFileNameException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("400");
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }
}
