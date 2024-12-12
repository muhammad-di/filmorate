package ru.yandex.practicum.filmorate.validation.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.user.UserValidator;

import java.time.LocalDate;

@Slf4j
@Component
public class BirthdayValidator implements UserValidator {
    private static final LocalDate FUTURE_DATE = LocalDate.now();

    @Override
    public void validate(User user) {
        if (user.getBirthday().isAfter(FUTURE_DATE)) {
            log.warn("User with email {} has birthday in future", user.getEmail());
            throw new ValidationException("invalid birthday value");
        }
    }
}
