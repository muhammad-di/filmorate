package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.impl.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@Slf4j
public class InMemoryUserStorageTest {
    private static UserStorage userStorage;
    private User firstUser;
    private User secondUser;
    private User thirdUser;


    @BeforeEach
    public void init() {
        userStorage = new InMemoryUserStorage();
        firstUser = User.builder()
                .login("firstUserLogin")
                .email("firstUser@email.com")
                .name("firstUserName")
                .birthday(LocalDate.of(1999, 9, 9))
                .build();
        secondUser = User.builder()
                .login("secondUserLogin")
                .email("secondUser@email.com")
                .name("secondUserName")
                .birthday(LocalDate.of(1999, 9, 10))
                .build();
        thirdUser = User.builder()
                .login("thirdUserLogin")
                .email("thirdUser@email.com")
                .name("thirdUserName")
                .birthday(LocalDate.of(1999, 9, 11))
                .build();
    }

    @Test
    public void testGetMutualFriends_whenGivenTwoUsers_ShouldReturnCommon() {
        long firstUserId = 3L;
        long secondUserId = 2L;
        long thirdUserId = 15L;
        firstUser.setId(firstUserId);
        secondUser.setId(secondUserId);
        thirdUser.setId(thirdUserId);
        firstUser.setFriends(new HashSet<>(Set.of(secondUserId, thirdUserId)));
        secondUser.setFriends(new HashSet<>(Set.of(firstUserId, thirdUserId)));
        thirdUser.setFriends(new HashSet<>(Set.of(firstUserId, secondUserId)));
        userStorage.addFriend(firstUser, secondUser);
        userStorage.addFriend(firstUser, thirdUser);
        userStorage.addFriend(secondUser, firstUser);
        userStorage.addFriend(secondUser, thirdUser);
        userStorage.addFriend(thirdUser, firstUser);
        userStorage.addFriend(thirdUser, secondUser);

        Collection<User> expected = new HashSet<>(Set.of(secondUser));
        Collection<User> actual;

        actual = userStorage.getCommonFriends(firstUser, thirdUser);

        assertIterableEquals(expected, actual);
    }

}
