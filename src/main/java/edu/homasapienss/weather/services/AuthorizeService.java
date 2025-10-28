package edu.homasapienss.weather.services;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.exceptions.auth.login.BadCredentialsException;
import edu.homasapienss.weather.models.Session;
import edu.homasapienss.weather.models.User;
import edu.homasapienss.weather.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        var user = findUserOrThrow(userDto.login(), userDto.password());
        var uuidOfCreatedSession = sessionService.createSession(user);
        sessionService.addCookie(resp, uuidOfCreatedSession);
    }

    public User findUserOrThrow (String login, String password) {
        User user = userRepository.getByLogin(login).orElseThrow(BadCredentialsException::new);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException();
        }
        return user;
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
