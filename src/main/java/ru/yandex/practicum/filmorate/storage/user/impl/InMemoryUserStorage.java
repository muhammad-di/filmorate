package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public User create(final User user) {
        log.info("Creating in Repository a user: {}", user);
        user.setId(User.getNextId());
        if (ObjectUtils.isEmpty(user.getFriends())) {
            user.setFriends(new HashSet<>());
        }
        users.put(user.getId(), user);

        return users.get(user.getId());
    }

    @Override
    public boolean contains(final User user) {
        return users.containsKey(user.getId());
    }

    @Override
    public User update(final User user) {
        users.put(user.getId(), user);

        return users.get(user.getId());
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

    @Override
    public Collection<User> getUsersByIds(Collection<Long> ids) {
        return ids.stream()
                .map(this::getById)
                .map(opt -> opt.orElse(null))
                .toList();
    }

    @Override
    public Collection<User> getCommonFriends(final User firstUser, final User secondUser) {
        Set<Long> mutualFriends = new HashSet<>(firstUser.getFriends());
        mutualFriends.retainAll(secondUser.getFriends());

        return mutualFriends.stream().map(users::get).toList();
    }

    private void updateBothUsers(final User firstUser, final User secondUser) {
        users.put(firstUser.getId(), firstUser);
        users.put(secondUser.getId(), secondUser);
    }


}
