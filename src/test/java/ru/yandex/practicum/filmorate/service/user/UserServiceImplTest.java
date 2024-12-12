package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exseption.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.impl.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.storage.user.impl.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.validation.user.UserValidator;
import ru.yandex.practicum.filmorate.validation.user.impl.BirthdayValidator;
import ru.yandex.practicum.filmorate.validation.user.impl.EmailValidator;
import ru.yandex.practicum.filmorate.validation.user.impl.LoginValidator;
import ru.yandex.practicum.filmorate.validation.user.impl.UserNameValidator;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class UserServiceImplTest {
    private static UserService userService;
    private static UserStorage userStorage;
    private User firstUser;
    private User secondUser;
    private User thirdUser;

    @BeforeAll
    public static void setUp() {
        userStorage = mock(InMemoryUserStorage.class);
        List<UserValidator> validators = List.of(
                mock(BirthdayValidator.class),
                mock(EmailValidator.class),
                mock(LoginValidator.class),
                mock(UserNameValidator.class)
        );

        userService = new UserServiceImpl(userStorage, validators);
    }

    @BeforeEach
    public void init() {
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
    public void testCreate_whenGivenArgumentUser_ShouldReturnUser() {
        User actual;
        User expected = User.builder()
                .id(2)
                .login("firstUserLogin")
                .name("firstUserName")
                .email("firstUser@email.com")
                .birthday(LocalDate.of(1999, 9, 9))
                .build();
        firstUser.setId(2);

        when(userStorage.create(firstUser)).thenReturn(firstUser);
        actual = userStorage.create(firstUser);

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_whenGivenArgumentUser_ShouldReturnUser() {
        User actual;
        User expected = User.builder()
                .id(2)
                .login("firstUserLogin")
                .name("firstUserName")
                .email("firstUser@email.com")
                .birthday(LocalDate.of(1999, 9, 9))
                .build();
        firstUser.setId(2);

        when(userStorage.create(firstUser)).thenReturn(firstUser);
        actual = userStorage.create(firstUser);

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_whenGivenArgumentUserWhichDoesNotExist_ShouldThrowEntityNotFoundException() {
        String actual;
        String expected = "User does not exist";

        when(userStorage.contains(firstUser)).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.update(firstUser));
        actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_whenGivenArgumentUserExists_ShouldNotThrowValidationException() {
        User actual;
        User expected = User.builder()
                .id(2)
                .login("firstUserLogin")
                .name("firstUserName")
                .email("firstUser@email.com")
                .birthday(LocalDate.of(1999, 9, 9))
                .build();
        firstUser.setId(2);

        when(userStorage.update(firstUser)).thenReturn(firstUser);
        when(userStorage.contains(firstUser)).thenReturn(true);
        actual = assertDoesNotThrow(() -> userService.update(firstUser));

        assertEquals(expected, actual);
    }

    @Test
    public void testFindAll_whenGivenNoArgument_ShouldReturnListOfAllUsers() {
        long firstUserId = 3L;
        long secondUserId = 7L;
        long thirdUserId = 15L;
        Collection<User> actual;
        Collection<User> expected = List.of(
                firstUser.toBuilder().id(firstUserId).build(),
                secondUser.toBuilder().id(secondUserId).build(),
                thirdUser.toBuilder().id(thirdUserId).build()
        );
        firstUser.setId(firstUserId);
        secondUser.setId(secondUserId);
        thirdUser.setId(thirdUserId);


        when(userStorage.findAll()).thenReturn(List.of(firstUser, secondUser, thirdUser));
        actual = userService.findAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    public void testAddFriend_whenGivenTwoUsers_ShouldAddFriendsInFriendsField() {
        long firstUserId = 3L;
        long thirdUserId = 15L;
        firstUser.setId(firstUserId);
        thirdUser.setId(thirdUserId);
        firstUser.setFriends(new HashSet<>());
        thirdUser.setFriends(new HashSet<>());

        Collection<Long> expected1 = Set.of(firstUserId);
        Collection<Long> expected2 = Set.of(thirdUserId);
        Collection<Long> actual1;
        Collection<Long> actual2;

        when(userStorage.getById(firstUserId)).thenReturn(Optional.ofNullable(firstUser));
        when(userStorage.getById(thirdUserId)).thenReturn(Optional.ofNullable(thirdUser));
        doNothing().when(userStorage).addFriend(firstUser, thirdUser);
        userService.addFriend(firstUserId, thirdUserId);
        actual1 = thirdUser.getFriends();
        actual2 = firstUser.getFriends();

        assertIterableEquals(expected1, actual1);
        assertIterableEquals(expected2, actual2);
    }

    @Test
    public void testDeleteFriend_whenGivenTwoUsers_ShouldRemoveFriendsInFriendsField() {
        long firstUserId = 3L;
        long secondUserId = 2L;
        long thirdUserId = 15L;
        firstUser.setId(firstUserId);
        secondUser.setId(secondUserId);
        thirdUser.setId(thirdUserId);
        firstUser.setFriends(new HashSet<>(Set.of(secondUserId, thirdUserId)));
        secondUser.setFriends(new HashSet<>(Set.of(firstUserId, thirdUserId)));
        thirdUser.setFriends(new HashSet<>(Set.of(firstUserId, secondUserId)));


        Collection<Long> expected = new HashSet<>(Set.of(secondUserId));
        Collection<Long> actual1;
        Collection<Long> actual2;

        when(userStorage.getById(firstUserId)).thenReturn(Optional.ofNullable(firstUser));
        when(userStorage.getById(thirdUserId)).thenReturn(Optional.ofNullable(thirdUser));
        doNothing().when(userStorage).deleteFriend(firstUser, thirdUser);
        userService.deleteFriend(firstUserId, thirdUserId);
        actual1 = thirdUser.getFriends();
        actual2 = firstUser.getFriends();

        assertIterableEquals(expected, actual1);
        assertIterableEquals(expected, actual2);
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

        Collection<User> expected = new HashSet<>(Set.of(secondUser));
        Collection<User> actual;

        when(userStorage.getById(firstUserId)).thenReturn(Optional.ofNullable(firstUser));
        when(userStorage.getById(thirdUserId)).thenReturn(Optional.ofNullable(thirdUser));
        when(userStorage.getCommonFriends(firstUser, thirdUser)).thenReturn(Set.of(secondUser));
        actual = userService.getCommonFriends(firstUserId, thirdUserId);

        assertIterableEquals(expected, actual);
    }

}
