package edu.homasapienss.weather.services;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.models.Session;
import edu.homasapienss.weather.models.User;
import edu.homasapienss.weather.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Duration;

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
    public void loginUser(UserDto userDto, HttpServletResponse resp) {
        User user = userRepository.getByLogin(userDto.login()).orElseThrow(() -> new RuntimeException("Неверный логин или пароль"));

        if (!passwordEncoder.matches(userDto.password(), user.getPassword())) {
            throw new RuntimeException("Неверный логин или пароль");
        }
        var uuidOfCreatedSession = sessionService.createSession(user);
        ResponseCookie cookie = ResponseCookie.from("SESSION_UUID", uuidOfCreatedSession.toString())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
        resp.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @Transactional
    public void logoutUser(HttpServletRequest req, HttpServletResponse resp) {
        Session session = sessionService.takeSessionFromRequest(req);
        if (session!=null) {
            sessionService.deleteSession(session);
            sessionService.deleteCookie(resp);
        }
    }

    @Transactional
    public boolean isUserAuthorized(HttpServletRequest req, HttpServletResponse resp) {
        return sessionService.isSessionValid(req, resp);
    }
}
