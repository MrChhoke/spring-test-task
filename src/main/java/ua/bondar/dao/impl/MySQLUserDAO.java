package ua.bondar.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.bondar.dao.UserSimpleDAO;
import ua.bondar.entity.User;
import ua.bondar.exception.DuplicateDepartmentException;
import ua.bondar.mapper.UserMapper;

import java.util.Optional;

@Component
@Slf4j
public class MySQLUserDAO implements UserSimpleDAO {

    private final JdbcTemplate jdbcTemplate;

    private final static String QUERY_FIND_USER_BY_NAME = "SELECT * FROM users WHERE username = ?";
    private final static String QUERY_INSERT_USER = "INSERT INTO users(username, password) VALUES (?,?)";

    public MySQLUserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByName(String name) {
        return jdbcTemplate
                .query(QUERY_FIND_USER_BY_NAME, new Object[]{name}, new UserMapper())
                .stream()
                .findFirst();
    }

    @Override
    public void insertUser(User user) {
        try {
            jdbcTemplate.update(QUERY_INSERT_USER,
                    user.getUsername(),
                    user.getPassword());
        } catch (DuplicateKeyException exception) {
            log.trace("This user has already exists");
            throw new DuplicateDepartmentException("This user has already exists");
        }
    }
}
