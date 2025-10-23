package edu.homasapienss.weather.services;

import edu.homasapienss.weather.models.Session;
import edu.homasapienss.weather.models.User;
import edu.homasapienss.weather.repositories.SessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@PropertySource("classpath:application.properties")
@Service
public class SessionService {
    @Value("${session.extend.time.minutes}")
    private long extendTimeSessionMinutes;
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public boolean isSessionValid(HttpServletRequest req, HttpServletResponse resp) {
        Session session = takeSessionFromRequest(req);

        if (session == null) return false;
        if (isSessionExpired(session)) {
            deleteSession(session);
            deleteCookie(resp);
            return false;
        } else {
            extendSession(session);
            return true;
        }
    }

    public Session takeSessionFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if (cookies == null) {
            return null;
        }

        UUID uuidOfSession = Arrays.stream(cookies)
                .filter(c -> "SESSION_UUID".equals(c.getName()))
                .map(c -> {
                    try {
                        return UUID.fromString(c.getValue());
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(u -> u != null)
                .findFirst()
                .orElse(null);

        if (uuidOfSession == null) return null;
        return getSession(uuidOfSession);
    }

    public Session getSession(UUID uuid) {
        return sessionRepository.getById(uuid).orElse(null);
    }

    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }

    public UUID createSession(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setExpiresAt(renewExpiresAt(extendTimeSessionMinutes));
        return sessionRepository.saveOrUpdate(session).getId();
    }

    public boolean isSessionExpired(Session session) {
        return session.getExpiresAt().isBefore(LocalDateTime.now());
    }


    public void extendSession(Session session) {
        session.setExpiresAt(renewExpiresAt(extendTimeSessionMinutes));
        sessionRepository.saveOrUpdate(session);
    }

    private LocalDateTime renewExpiresAt(Long extendTimeSessionMinutes) {
        return LocalDateTime.now().plusMinutes(extendTimeSessionMinutes);
    }

    public void deleteCookie(HttpServletResponse resp) {
        ResponseCookie expired = ResponseCookie.from("SESSION_UUID", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        resp.addHeader(HttpHeaders.SET_COOKIE, expired.toString());
    }
}
