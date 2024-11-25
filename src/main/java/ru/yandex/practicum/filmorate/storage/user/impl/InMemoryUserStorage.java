package ru.yandex.practicum.filmorate.storage.user.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
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

    @Override
    public void addFriend(final User user, final User newFriend) {
        updateBothUsers(user, newFriend);
    }

    @Override
    public void deleteFriend(final User user, final User exFriend) {
        updateBothUsers(user, exFriend);
    }

    @Override
    public Optional<User> getById(final long id) {
        return Optional.ofNullable(users.get(id));
    }

    private void updateBothUsers(final User firstUser, final User secondUser) {
        users.put(firstUser.getId(), firstUser);
        users.put(secondUser.getId(), secondUser);
    }


}
