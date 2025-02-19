package ch.cern.todo.service;

import ch.cern.todo.entity.User;
import ch.cern.todo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user;
    }

    public User getUserById(Long userId) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user;
    }
}