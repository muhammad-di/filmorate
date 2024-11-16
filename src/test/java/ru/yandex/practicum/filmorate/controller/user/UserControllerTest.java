package ru.yandex.practicum.filmorate.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
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
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
public class UserControllerTest {
    private UserController controller;
    private User firstUser;
    private User secondUser;
    private User thirdUser;

    @BeforeEach
    public void init() {
        List<UserValidator> list = List.of(
                new BirthdayValidator(),
                new EmailValidator(),
                new LoginValidator(),
                new UserNameValidator()
        );
        controller = new UserController(list);
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
        User actual = controller.create(firstUser);
        User expected = User.builder()
                .id(actual.getId())
                .login("firstUserLogin")
                .name("firstUserName")
                .email("firstUser@email.com")
                .birthday(LocalDate.of(1999, 9, 9))
                .build();

        log.info("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_whenGivenArgumentUserWithChangedName_ShouldReturnUserWithNewName() {
        User returnedUser = controller.create(firstUser);
        User actual;
        User expected = User.builder()
                .id(returnedUser.getId())
                .login("firstUserLogin")
                .name("newNameForFirstUser")
                .email("firstUser@email.com")
                .birthday(LocalDate.of(1999, 9, 9))
                .build();
        User updatedFirstUser = firstUser.toBuilder()
                .id(returnedUser.getId())
                .name("newNameForFirstUser")
                .build();
        actual = controller.update(updatedFirstUser);

        log.info("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_whenGivenArgumentUserWithChangedEmail_ShouldReturnUserWithNewName() {
        User returnedUser = controller.create(firstUser);
        User actual;
        User expected = User.builder()
                .id(returnedUser.getId())
                .login("firstUserLogin")
                .email("newfirstUserEmail@email.com")
                .name("firstUserName")
                .birthday(LocalDate.of(1999, 9, 9))
                .build();
        User updatedFirstUser = firstUser.toBuilder()
                .id(returnedUser.getId())
                .email("newfirstUserEmail@email.com")
                .build();
        actual = controller.update(updatedFirstUser);

        log.info("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_whenGivenUserWhoDoesNotExist_ShouldThrowValidationException() {
        String actual;
        String expected = "User does not exist";
        User userWhoDoesNotExist = firstUser.toBuilder()
                .id(10)
                .build();

        controller.create(firstUser);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.update(userWhoDoesNotExist)
        );
        actual = exception.getMessage();

        log.info("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindAll_whenGivenNoArgument_ShouldReturnCollectionOfUsers() {
        User firstReturnedUser = controller.create(firstUser);
        User secondReturnedUser = controller.create(secondUser);
        User thirdReturnedUser = controller.create(thirdUser);
        Collection<User> actual;
        Collection<User> expected = List.of(
                firstUser.toBuilder().id(firstReturnedUser.getId()).build(),
                secondUser.toBuilder().id(secondReturnedUser.getId()).build(),
                thirdUser.toBuilder().id(thirdReturnedUser.getId()).build()
        );
        actual = controller.findAll();

        log.info("\nexpected: {}\nactual:   {}", expected, actual);
        assertIterableEquals(expected, actual);
    }


}
