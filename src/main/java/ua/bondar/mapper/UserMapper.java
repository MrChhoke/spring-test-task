package ua.bondar.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.bondar.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    private final static String FIELD_ID_USER = "userID";
    private final static String FIELD_USERNAME_USER = "username";
    private final static String FIELD_PASSWORD_USER = "password";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt(FIELD_ID_USER))
                .username(rs.getString(FIELD_USERNAME_USER))
                .password(rs.getString(FIELD_PASSWORD_USER))
                .build();
    }
}
