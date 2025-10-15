package edu.homasapienss.weather.services;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.mappers.UserMapper;
import edu.homasapienss.weather.models.User;
import edu.homasapienss.weather.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public AuthenticationService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDto registrationUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.getByLogin(userDto.login());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("Пользователь с таким именем уже существует");
        }
        User savedUser = userRepository.saveOrUpdate(userMapper.toUser(userDto));
        return userMapper.toUserDto(savedUser);
    }
}
