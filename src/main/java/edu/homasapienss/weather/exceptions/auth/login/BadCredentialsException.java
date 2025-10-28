package edu.homasapienss.weather.exceptions.auth.login;

import edu.homasapienss.weather.exceptions.ApplicationException;

public class BadCredentialsException extends ApplicationException {
    public BadCredentialsException() {
        super("Не верный логин или пароль");
    }
}
