package ua.bondar.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> findById(int id);

    Optional<T> findByName(String name);

    List<T> findAll();

    List<T> findFromTo(int from, int to);

    void save(T saveObject);

    void update(T updateObj);

    void delete(T deleteObj);
}
