package edu.homasapienss.weather.exceptions.auth.register;

import edu.homasapienss.weather.exceptions.auth.RegisterException;

public class PasswordReplicateLoginException extends RegisterException {
    public PasswordReplicateLoginException() {
        super("Пароль дублирует логин");
    }
}
