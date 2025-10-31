package edu.homasapienss.weather.exceptions;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
}
