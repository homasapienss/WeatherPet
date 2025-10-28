package edu.homasapienss.weather.exceptions;

import edu.homasapienss.weather.exceptions.auth.LoginException;
import edu.homasapienss.weather.exceptions.auth.login.BadCredentialsException;
import edu.homasapienss.weather.exceptions.auth.RegistrationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoginException.class)
    public String handleLoginError(BadCredentialsException ex, Model model) {
        model.addAttribute("error_message", ex.getCustomExceptionMessage());
        return "login-error";
    }

    @ExceptionHandler(RegistrationException.class)
    public String handleRegistrationError(RegistrationException ex, Model model) {
        model.addAttribute("error_message", ex.getCustomExceptionMessage());
        return "register-error";
    }
}
