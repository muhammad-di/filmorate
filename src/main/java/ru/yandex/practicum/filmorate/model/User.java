package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class User {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private final String email;
    private String login;
    private String name;
    private LocalDate birthday;
}
