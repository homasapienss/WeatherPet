package edu.homasapienss.weather.repositories;

import edu.homasapienss.weather.models.Location;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepository implements CrudRepository<Location, Long> {

    @Autowired
    private final SessionFactory sessionFactory;

    public LocationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Location> getAll() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("FROM Location", Location.class)
                .list();
    }

    @Override
    public Optional<Location> getById(Long aLong) {
        return Optional.ofNullable(sessionFactory
                .getCurrentSession()
                .get(Location.class, aLong));
    }

    @Override
    public Location saveOrUpdate(Location entityToSave) {
        return sessionFactory
                .getCurrentSession()
                .merge(entityToSave);
    }

    @Override
    public void delete(Location entityToDelete) {
        sessionFactory
                .getCurrentSession()
                .remove(entityToDelete);
    }
}
