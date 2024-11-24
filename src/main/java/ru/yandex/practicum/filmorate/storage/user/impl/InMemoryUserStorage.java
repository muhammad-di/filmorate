package ru.yandex.practicum.filmorate.storage.user.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public User create(final User user) {
        user.setId(User.getNextId());

        return users.put(user.getId(), user);
    }

    @Override
    public boolean contains(final User user) {
        return users.containsKey(user.getId());
    }

    @Override
    public User update(final User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }
}
