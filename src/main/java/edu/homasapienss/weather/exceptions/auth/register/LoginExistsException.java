package edu.homasapienss.weather.exceptions.auth.register;

import edu.homasapienss.weather.exceptions.auth.RegistrationException;

public class LoginExistsException extends RegistrationException {
    public LoginExistsException() {
        super("Пользователь уже существует");
    }
}
