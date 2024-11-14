package ru.yandex.practicum.filmorate.validation.user.impl;

import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.user.UserValidator;

import java.time.LocalDate;

public class BirthdayValidator implements UserValidator {
    private static final LocalDate FUTURE_DATE = LocalDate.now();

    @Override
    public void validate(User user) {
        if (user.getBirthday().isAfter(FUTURE_DATE)) {
            throw new ValidationException("invalid birthday value");
        }
    }
}
