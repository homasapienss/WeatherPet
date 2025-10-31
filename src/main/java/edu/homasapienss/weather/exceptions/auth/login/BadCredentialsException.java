package edu.homasapienss.weather.exceptions.auth.login;

import edu.homasapienss.weather.exceptions.auth.LoginException;

public class BadCredentialsException extends LoginException {
    public BadCredentialsException() {
        super("Не верный логин или пароль");
    }
}
