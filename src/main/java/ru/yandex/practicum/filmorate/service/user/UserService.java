package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    User create(User user);

    User update(User user);

    Collection<User> findAll();
}
