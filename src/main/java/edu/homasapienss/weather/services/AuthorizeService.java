package edu.homasapienss.weather.services;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.models.Session;
import edu.homasapienss.weather.models.User;
import edu.homasapienss.weather.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
public class AuthorizeService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;

    public AuthorizeService(UserRepository userRepository, PasswordEncoder passwordEncoder, SessionService sessionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionService = sessionService;
    }

    @Transactional
    public UUID loginUser(UserDto userDto) {
        User user = userRepository.getByLogin(userDto.login()).orElseThrow(() -> new RuntimeException("Неверный логин или пароль"));

        if (!passwordEncoder.matches(userDto.password(), user.getPassword())) {
            throw new RuntimeException("Неверный логин или пароль");
        }
        return sessionService.createSession(user);
    }

    @Transactional
    public void logoutUser(HttpServletRequest req) {
        Session session = sessionService.takeSessionFromRequest(req);
        if (session!=null) {
            sessionService.deleteSession(session);
        }
    }

    @Transactional
    public boolean isUserAuthorized(HttpServletRequest req) {
        return sessionService.isSessionValid(req);
    }
}
