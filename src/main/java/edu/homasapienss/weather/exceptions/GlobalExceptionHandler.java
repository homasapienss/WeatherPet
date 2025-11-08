package edu.homasapienss.weather.exceptions;

import edu.homasapienss.weather.exceptions.auth.LoginException;
import edu.homasapienss.weather.exceptions.auth.RegisterException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_ATTRIBUTE = "error_message";

    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception ex, Model model) {
        addErrorToPage(ex, model);
        return "error";
    }

    @ExceptionHandler(LoginException.class)
    public String handleLoginError(LoginException ex, Model model) {
        addErrorToPage(ex, model);
        return "login-error";
    }

    @ExceptionHandler(RegisterException.class)
    public String handleRegisterError(RegisterException ex, Model model) {
        addErrorToPage(ex, model);
        return "register-error";
    }

    private void addErrorToPage (Exception ex, Model model) {
        model.addAttribute(ERROR_ATTRIBUTE, ex.getMessage());
    }
}
