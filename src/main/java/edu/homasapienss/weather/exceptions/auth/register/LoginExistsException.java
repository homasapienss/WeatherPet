package edu.homasapienss.weather.exceptions.auth.register;

import edu.homasapienss.weather.exceptions.auth.RegisterException;

public class LoginExistsException extends RegisterException {
    public LoginExistsException() {
        super("Пользователь уже существует");
    }
}
