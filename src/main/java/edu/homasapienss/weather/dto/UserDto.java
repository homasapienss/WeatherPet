package edu.homasapienss.weather.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDto (
        @NotBlank(message = "Логин не может быть пустым")
        @Size(min = 3, max = 30, message = "Логин должен быть от 3 до 30 символов")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Логин может содержать только буквы, цифры и _ . -")
        String login,
        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 6, max = 50, message = "Пароль должен быть от 6 до 50 символов")
        String password
){

}
