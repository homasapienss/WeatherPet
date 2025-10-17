package edu.homasapienss.weather.services;

import edu.homasapienss.weather.models.Session;
import edu.homasapienss.weather.models.User;
import edu.homasapienss.weather.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public boolean isSessionValid() {
        return true;
    }

    public UUID createSession(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        return sessionRepository.saveOrUpdate(session).getId();
    }

    public boolean isSessionExpired(UUID sessionUUID) {
        Session session = sessionRepository.getById(sessionUUID).orElseThrow(() -> new RuntimeException("Сессия не найдена"));
        return !session.getExpiresAt().isBefore(LocalDateTime.now());
    }
}
