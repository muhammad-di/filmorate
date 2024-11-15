package ru.yandex.practicum.filmorate.validation.user.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.user.UserValidator;

@Component
public class UserNameValidator implements UserValidator {
    @Override
    public void validate(User user) {
        if (!StringUtils.hasText(user.getName())) {
            user.setName(user.getLogin());
        }
    }
}
