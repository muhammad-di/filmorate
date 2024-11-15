package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
public class User {
    private static long nextId = 0;
    private long id;
    @Email
    private final String email;
    @NotBlank
    private String login;
    private String name;
    private LocalDate birthday;

    public static long getNextId() {
        return ++nextId;
    }
}
