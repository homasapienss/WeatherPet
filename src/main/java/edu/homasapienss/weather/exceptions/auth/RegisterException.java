package edu.homasapienss.weather.exceptions.auth;

import edu.homasapienss.weather.exceptions.ApplicationException;

public class RegisterException extends ApplicationException {
    public RegisterException(String message) {
        super(message);
    }
}
