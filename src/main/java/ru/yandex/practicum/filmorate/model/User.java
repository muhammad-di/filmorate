package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(exclude = "friends")
public class User {
    private static long nextId = 0;
    private long id;
    @Email
    private final String email;
    @NotBlank
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<User> friends;

    public static long getNextId() {
        return ++nextId;
    }

    public String toString() {
        return "User.UserBuilder(id=" + this.id + ", email=" + this.email + ", login=" + this.login + ", name=" + this.name + ", birthday=" + this.birthday + ", friends=" + this.friends.stream().map(User::getId).toList() + ")";
    }
}
