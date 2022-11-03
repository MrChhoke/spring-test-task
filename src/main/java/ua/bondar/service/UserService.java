package ua.bondar.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ua.bondar.entity.User;

public interface UserService extends UserDetailsService {

    void registerUser(User user);

}
