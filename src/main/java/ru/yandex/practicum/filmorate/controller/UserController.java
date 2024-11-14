package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    @PostMapping
    public User create(@RequestBody User user) {
        user.setId(UUID.randomUUID());
        users.put(user.getId(), user);
        log.debug("users: {}", users);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user, @PathVariable UUID userId) {
        users.put(userId, user);
        log.debug("users: {}", users);
        return user;
    }

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }
}
