package org.abraham.kanbantaskmanager.service;

import org.abraham.kanbantaskmanager.Exceptions.DuplicateEntityException;
import org.abraham.kanbantaskmanager.dtos.UserEvent;
import org.abraham.kanbantaskmanager.entities.User;
import org.abraham.kanbantaskmanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserEvent userEvent) {
        var userId = Long.valueOf(userEvent.id());
        userRepository.findById(userId).ifPresent(user -> {
            throw new DuplicateEntityException("User","id", userId.toString());
        });

        var user = new User();
        user.setId(userId);
        user.setUsername(userEvent.username());
        user.setEmail(userEvent.email());

        userRepository.save(user);
        log.info(String.format("Saved user %s", user));
    }

}
