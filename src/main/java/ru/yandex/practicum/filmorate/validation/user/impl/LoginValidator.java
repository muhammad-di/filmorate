package ru.yandex.practicum.filmorate.validation.user.impl;

import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.user.UserValidator;

public class LoginValidator implements UserValidator {
    @Override
    public void validate(User user) {
        if (!StringUtils.hasText(user.getLogin()) || StringUtils.containsWhitespace(user.getLogin())) {
            throw new ValidationException("invalid login name");
        }
    }
}
