package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    User create(User user);

    boolean contains(User user);

    User update(User user);

    Collection<User> findAll();

    void addFriend(User user, User newFriend);

    void deleteFriend(User user, User exFriend);

    Optional<User> getById(long id);
}
