package edu.homasapienss.weather.exceptions;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final String customExceptionMessage;
    public ApplicationException(String message) {
        this.customExceptionMessage = message;
    }
}
