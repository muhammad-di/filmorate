package ru.yandex.practicum.filmorate.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.service.user.impl.UserServiceImpl;
import ru.yandex.practicum.filmorate.validation.user.UserValidator;
import ru.yandex.practicum.filmorate.validation.user.impl.BirthdayValidator;
import ru.yandex.practicum.filmorate.validation.user.impl.EmailValidator;
import ru.yandex.practicum.filmorate.validation.user.impl.LoginValidator;
import ru.yandex.practicum.filmorate.validation.user.impl.UserNameValidator;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Slf4j
public class UserControllerUnitTest {
    private static UserController controller;
    private static UserService service;
    private User firstUser;
    private User secondUser;
    private User thirdUser;

    @BeforeAll
    public static void setUp() {
        service = mock(UserServiceImpl.class);
        controller = new UserController(service);
    }

    @BeforeEach
    public void init() {
        List<UserValidator> list = List.of(
                new BirthdayValidator(),
                new EmailValidator(),
                new LoginValidator(),
                new UserNameValidator()
        );
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
    public void testCreate_whenGivenArgumentUserWithoutIdParameter_ShouldReturnUser() {
        User actual;
        User expected = User.builder()
                .login("firstUserLogin")
                .name("firstUserName")
                .email("firstUser@email.com")
                .birthday(LocalDate.of(1999, 9, 9))
                .build();

        when(service.create(firstUser)).thenReturn(firstUser);
        actual = controller.create(firstUser);

        assertEquals(expected, actual);
        log.info("""
                testCreate_whenGivenArgumentUserWithoutIdParameter_ShouldReturnUser
                expected: {}
                actual:   {}
                """, expected, actual);
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

        when(service.update(firstUser)).thenReturn(firstUser);
        actual = controller.update(firstUser);

        assertEquals(expected, actual);
        log.info("""
                testUpdate_whenGivenArgumentUser_ShouldReturnUser
                expected: {}
                actual:   {}
                """, expected, actual);
    }

    @Test
    public void testFindAll_whenGivenNoArgument_ShouldReturnCollectionOfUsers() {
        long firstUserId = 1L;
        long secondUserId = 2L;
        long thirdUserId = 3L;


        firstUser.setId(firstUserId);
        secondUser.setId(secondUserId);
        thirdUser.setId(thirdUserId);

        Collection<User> actual;
        Collection<User> expected = List.of(
                firstUser.toBuilder().id(firstUserId).build(),
                secondUser.toBuilder().id(secondUserId).build(),
                thirdUser.toBuilder().id(thirdUserId).build()
        );

        when(service.findAll()).thenReturn(List.of(firstUser, secondUser, thirdUser));
        actual = controller.findAll();

        assertIterableEquals(expected, actual);
        log.info("""
                testFindAll_whenGivenNoArgument_ShouldReturnCollectionOfUsers
                expected: {}
                actual:   {}
                """, expected, actual);
    }
//
//    @Test
//    public void testUpdate_whenGivenArgumentUserWithChangedName_ShouldReturnUserWithNewName() {
//        User returnedUser = controller.create(firstUser);
//        User actual;
//        User expected = User.builder()
//                .id(returnedUser.getId())
//                .login("firstUserLogin")
//                .name("newNameForFirstUser")
//                .email("firstUser@email.com")
//                .birthday(LocalDate.of(1999, 9, 9))
//                .build();
//        User updatedFirstUser = firstUser.toBuilder()
//                .id(returnedUser.getId())
//                .name("newNameForFirstUser")
//                .build();
//        actual = controller.update(updatedFirstUser);
//
//        log.info("\nexpected: {}\nactual:   {}", expected, actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testUpdate_whenGivenArgumentUserWithChangedEmail_ShouldReturnUserWithNewName() {
//        User returnedUser = controller.create(firstUser);
//        User actual;
//        User expected = User.builder()
//                .id(returnedUser.getId())
//                .login("firstUserLogin")
//                .email("newfirstUserEmail@email.com")
//                .name("firstUserName")
//                .birthday(LocalDate.of(1999, 9, 9))
//                .build();
//        User updatedFirstUser = firstUser.toBuilder()
//                .id(returnedUser.getId())
//                .email("newfirstUserEmail@email.com")
//                .build();
//        actual = controller.update(updatedFirstUser);
//
//        log.info("\nexpected: {}\nactual:   {}", expected, actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testUpdate_whenGivenUserWhoDoesNotExist_ShouldThrowValidationException() {
//        String actual;
//        String expected = "User does not exist";
//        User userWhoDoesNotExist = firstUser.toBuilder()
//                .id(10)
//                .build();
//
//        controller.create(firstUser);
//        ValidationException exception = assertThrows(
//                ValidationException.class,
//                () -> controller.update(userWhoDoesNotExist)
//        );
//        actual = exception.getMessage();
//
//        log.info("\nexpected: {}\nactual:   {}", expected, actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testFindAll_whenGivenNoArgument_ShouldReturnCollectionOfUsers() {
//        User firstReturnedUser = controller.create(firstUser);
//        User secondReturnedUser = controller.create(secondUser);
//        User thirdReturnedUser = controller.create(thirdUser);
//        Collection<User> actual;
//        Collection<User> expected = List.of(
//                firstUser.toBuilder().id(firstReturnedUser.getId()).build(),
//                secondUser.toBuilder().id(secondReturnedUser.getId()).build(),
//                thirdUser.toBuilder().id(thirdReturnedUser.getId()).build()
//        );
//        actual = controller.findAll();
//
//        log.info("\nexpected: {}\nactual:   {}", expected, actual);
//        assertIterableEquals(expected, actual);
//    }


}
