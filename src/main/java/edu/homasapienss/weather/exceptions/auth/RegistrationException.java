package edu.homasapienss.weather.exceptions.auth;

import edu.homasapienss.weather.exceptions.ApplicationException;

public class RegistrationException extends ApplicationException {
    public RegistrationException(String message) {
        super(message);
    }
}
