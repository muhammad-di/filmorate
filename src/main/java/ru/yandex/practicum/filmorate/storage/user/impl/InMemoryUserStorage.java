package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public User create(final User user) {
        user.setId(User.getNextId());

        return users.put(user.getId(), user);
    }
}
