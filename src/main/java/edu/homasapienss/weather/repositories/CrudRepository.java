package edu.homasapienss.weather.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository <T, ID> {
    List<T> getAll();
    Optional<T> getById(ID id);
    T saveOrUpdate(T entityToSave);
    void delete(T entityToDelete);
}
