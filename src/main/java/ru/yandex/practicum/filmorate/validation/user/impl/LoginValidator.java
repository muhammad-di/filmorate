package ru.yandex.practicum.filmorate.validation.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.user.UserValidator;

@Slf4j
@Component
public class LoginValidator implements UserValidator {
    @Override
    public void validate(User user) {
        if (!StringUtils.hasText(user.getLogin()) || StringUtils.containsWhitespace(user.getLogin())) {
            log.warn("User with email {} has invalid login", user.getEmail());
            throw new ValidationException("invalid login name");
        }
    }
}
