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
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@RequestBody @Valid final User user) {
        log.debug("users create called in Controller with argument: {}", user);

        return userService.create(user);
    }

    @PutMapping
    public User update(@RequestBody @Valid final User user) {
        log.debug("users update called in Controller with argument: {}", user);

        return user;
    }

    @GetMapping
    public Collection<User> findAll() {
        log.debug("users findAll called in Controller");

        return userService.findAll();
    }
}
