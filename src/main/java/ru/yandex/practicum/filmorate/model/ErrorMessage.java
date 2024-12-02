package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ErrorMessage {
    private final int statusCode;
    private final LocalDateTime timestamp;
    private final String message;
}
