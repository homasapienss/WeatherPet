package edu.homasapienss.weather.repositories;

import edu.homasapienss.weather.models.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionRepository implements CrudRepository<Session, UUID> {

    private final SessionFactory sessionFactory;

    @Autowired
    public SessionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Session> getAll() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("FROM Session", Session.class)
                .list();
    }

    @Override
    public Optional<Session> getById(UUID uuid) {
        return Optional.ofNullable(sessionFactory
                .getCurrentSession()
                .get(Session.class, uuid));
    }

    @Override
    public Session saveOrUpdate(Session entityToSave) {
        return sessionFactory
                .getCurrentSession()
                .merge(entityToSave);
    }

    @Override
    public void delete(Session entityToDelete) {
        sessionFactory
                .getCurrentSession()
                .remove(entityToDelete);
    }
}
