package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.user.UserValidator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Validated
public class UserController {
    private final List<UserValidator> validators;
    private final Map<Long, User> users = new ConcurrentHashMap<>();

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        validators.forEach(v -> v.validate(user));
        user.setId(User.getNextId());
        users.put(user.getId(), user);
        log.debug("users created: {}", user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        contains(user);
        validators.forEach(v -> v.validate(user));
        users.put(user.getId(), user);
        log.debug("users updated: {}", users);
        return user;
    }

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }


    private void contains(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("User does not exist");
        }
    }
}
