package edu.homasapienss.weather.exceptions.auth.register;

import edu.homasapienss.weather.exceptions.auth.RegistrationException;

public class PasswordReplicateLoginException extends RegistrationException {
    public PasswordReplicateLoginException() {
        super("Пароль дублирует логин");
    }
}
