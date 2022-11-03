package ua.bondar.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.bondar.dao.UserSimpleDAO;
import ua.bondar.entity.User;
import ua.bondar.service.UserService;

import java.util.Optional;

@Service
@Slf4j
@Primary
public class UserServiceImpl implements UserService {

    private final UserSimpleDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserSimpleDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userDAO.findByName(username);

        if (optionalUser.isEmpty()) {
            log.trace("This user [{}] doesnt exist", username);
            throw new UsernameNotFoundException("This user doesnt exist");
        }
        return optionalUser.get();
    }

    @Override
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.insertUser(user);
    }
}
