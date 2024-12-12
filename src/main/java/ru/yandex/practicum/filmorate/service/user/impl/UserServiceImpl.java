package ru.yandex.practicum.filmorate.service.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exseption.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.user.UserValidator;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final List<UserValidator> validators;

    @Override
    public User create(final User user) {
        log.info("Starting creation of a new user with email {}", user.getEmail());
        User userCreated;

        validators.forEach(v -> v.validate(user));
        log.info("User with email {} validated", user.getEmail());

        userCreated = userStorage.create(user);
        log.debug("user created in service layer: {}", user);

        return userCreated;
    }

    @Override
    public User update(final User user) {
        User userUpdated;

        contains(user);
        validators.forEach(v -> v.validate(user));
        log.info("Validated user with ID {}", user.getId());

        userUpdated = userStorage.update(user);
        log.debug("user updated in service layer: {}", user);

        return userUpdated;
    }

    @Override
    public User findByIf(final long id) {
        log.info("Stared to look for user by ID {}", id);
        return checkIfExist(id);
    }

    @Override
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public Collection<User> finaAllFriends(final long userId) {
        log.info("Service: Started to look for friends of user with ID {}", userId);

        final User user = checkIfExist(userId);
        log.info("Service: Validated existence of user with ID {}", userId);

        log.info("Service: Returning friends of user with ID {}", userId);
        return userStorage.getUsersByIds(user.getFriends());
    }

    @Override
    public void addFriend(final long userId, final long newFriendId) {
        final User user = checkIfExist(userId);
        final User newFriend = checkIfExist(newFriendId);

        user.getFriends().add(newFriendId);
        newFriend.getFriends().add(userId);

        userStorage.addFriend(user, newFriend);
    }

    @Override
    public void deleteFriend(final long userId, final long exFriendId) {
        final User user = checkIfExist(userId);
        final User exFriend = checkIfExist(exFriendId);

        user.getFriends().remove(exFriendId);
        exFriend.getFriends().remove(userId);

        userStorage.deleteFriend(user, exFriend);
    }

    @Override
    public Collection<User> getCommonFriends(final long firstUserId, final long secondUserId) {
        log.info("Started to look for common friends of users with ID {} and ID {}", firstUserId, secondUserId);

        final User firstUser = checkIfExist(firstUserId);
        log.info("Validated user with ID {}", firstUser.getId());
        final User secondUser = checkIfExist(secondUserId);
        log.info("Validated user with ID {}", secondUser.getId());

        return userStorage.getCommonFriends(firstUser, secondUser);
    }

    @Override
    public void contains(final long userId) {
        log.info("Service: started to check if user with ID {} exist", userId);
        if (!userStorage.contains(userId)) {
            throw new EntityNotFoundException("User does not exist");
        }
    }

    private void contains(final User user) {
        log.info("Service: started to check if user with ID {} exist", user.getId());
        if (!userStorage.contains(user)) {
            throw new EntityNotFoundException("User does not exist");
        }
    }

    private User checkIfExist(final long userId) {
        return userStorage.getById(userId)
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found in the database", userId);
                    return new EntityNotFoundException("User does not exist");
                });
    }
}
