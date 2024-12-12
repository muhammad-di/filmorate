package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    User create(User user);

    User update(User user);

    User findByIf(long id);

    Collection<User> findAll();

    Collection<User> finaAllFriends(long userId);

    void addFriend(long userId, long newFriendId);

    void deleteFriend(long userId, long exFriendId);

    Collection<User> getCommonFriends(long firstUserId, long secondUserId);

    void contains(final long userId);
}
