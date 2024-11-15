package ru.yandex.practicum.filmorate.validation.user.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.user.UserValidator;

import java.time.LocalDate;

@Component
public class BirthdayValidator implements UserValidator {
    private static final LocalDate FUTURE_DATE = LocalDate.now();

    @Override
    public void validate(User user) {
        if (user.getBirthday().isAfter(FUTURE_DATE)) {
            throw new ValidationException("invalid birthday value");
        }
    }
}
