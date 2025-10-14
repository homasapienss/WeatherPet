package edu.homasapienss.weather.repositories;

import edu.homasapienss.weather.models.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements CrudRepository<User, Long> {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAll() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("FROM User", User.class)
                .list();
    }

    @Override
    public Optional<User> getById(Long aLong) {
        return Optional.ofNullable(sessionFactory
                .getCurrentSession()
                .get(User.class, aLong));
    }

    @Override
    public User saveOrUpdate(User entityToSave) {
        return sessionFactory
                .getCurrentSession()
                .merge(entityToSave);
    }

    @Override
    public void delete(User entityToDelete) {
        sessionFactory
                .getCurrentSession()
                .remove(entityToDelete);
    }
}
