package edu.homasapienss.weather.services;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.exceptions.auth.register.LoginExistsException;
import edu.homasapienss.weather.exceptions.auth.register.PasswordReplicateLoginException;
import edu.homasapienss.weather.mappers.UserMapper;
import edu.homasapienss.weather.models.User;
import edu.homasapienss.weather.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registrationUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.getByLogin(userDto.login());
        if (existingUser.isPresent()) {
            throw new LoginExistsException();
        }
        if (userDto.login().equals(userDto.password())) {
            throw new PasswordReplicateLoginException();
        }
        User userToSave = userMapper.toUser(userDto);
        userToSave.setPassword(passwordEncoder.encode(userDto.password()));
        userRepository.saveOrUpdate(userToSave);
    }
}
