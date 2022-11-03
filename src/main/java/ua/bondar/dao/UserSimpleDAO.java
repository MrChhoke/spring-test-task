package ua.bondar.dao;

import ua.bondar.entity.User;

import java.util.Optional;

public interface UserSimpleDAO {

    Optional<User> findByName(String name);

    void insertUser(User user);

}
