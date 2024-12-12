package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        return userService.update(user);
    }


    @GetMapping("/{id}")
    public User findById(@PathVariable @Positive long id) {
        log.info("users findById called in Controller");
        return userService.findByIf(id);
    }

    @GetMapping
    public Collection<User> findAll() {
        log.debug("users findAll called in Controller");
        return userService.findAll();
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable @Positive long id) {
        log.info("Controller getting friends for user with ID {}", id);
        return userService.finaAllFriends(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable @Positive long id, @PathVariable @Positive long friendId) {
        log.info("user with ID {} is about to add friend user with ID {}", id, friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable @Positive long id, @PathVariable @Positive long friendId) {
        log.info("user with ID {} is about to delete friend user with ID {}", id, friendId);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable @Positive long id, @PathVariable @Positive long otherId) {
        log.info("Controller getting common friends for users with ID {} and ID {}", id, otherId);
        return userService.getCommonFriends(id, otherId);
    }
}
