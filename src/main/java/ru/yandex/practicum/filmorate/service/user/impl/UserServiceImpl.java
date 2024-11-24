package ru.yandex.practicum.filmorate.service.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.user.UserValidator;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final List<UserValidator> validators;


    public User create(final User user) {
        User userCreated;

        validators.forEach(v -> v.validate(user));
        userCreated = userStorage.create(user);
        log.debug("user created in service layer: {}", user);

        return userCreated;
    }
}
