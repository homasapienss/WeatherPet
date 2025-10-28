package edu.homasapienss.weather.exceptions.auth;

import edu.homasapienss.weather.exceptions.ApplicationException;

public class LoginException extends ApplicationException {
    public LoginException(String message) {
        super(message);
    }
}
