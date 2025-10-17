package edu.homasapienss.weather.services;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.mappers.UserMapper;
import edu.homasapienss.weather.models.User;
import edu.homasapienss.weather.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, SessionService sessionService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.sessionService = sessionService;
    }

    @Transactional
    public UserDto registrationUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.getByLogin(userDto.login());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("Пользователь с таким именем уже существует");
        }
        if (userDto.login().equals(userDto.password())) {
            throw new IllegalArgumentException("Пароль не должен совпадать с логином");
        }
        User userToSave = userMapper.toUser(userDto);
        userToSave.setPassword(passwordEncoder.encode(userDto.password()));
        User savedUser = userRepository.saveOrUpdate(userToSave);
        return userMapper.toUserDto(savedUser);
    }

    @Transactional
    public UUID loginUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.getByLogin(userDto.login());
        User user = existingUser.orElseThrow(() -> new RuntimeException("Неверный логин или пароль"));

        if (!passwordEncoder.matches(userDto.password(), user.getPassword())) {
            throw new RuntimeException("Неверный логин или пароль");
        }
        return sessionService.createSession(user);

    }
}
