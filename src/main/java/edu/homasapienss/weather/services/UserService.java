package edu.homasapienss.weather.services;

import edu.homasapienss.weather.models.User;
import edu.homasapienss.weather.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User getUser(Long id) {
        return userRepository.getById(id).orElse(null);
    }

    @Transactional
    public User getUserByLogin(String login) {
        return userRepository.getByLogin(login).orElse(null);
    }
}
