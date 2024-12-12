package ru.yandex.practicum.filmorate.validation.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.user.UserValidator;

@Slf4j
@Component
public class UserNameValidator implements UserValidator {
    @Override
    public void validate(User user) {
        if (!StringUtils.hasText(user.getName())) {
            log.info("User with email {} will use login as name", user.getEmail());
            user.setName(user.getLogin());
        }
    }
}
