package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(exclude = "friends")
public class User {
    private static long nextId = 0;
    private long id;
    private final String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Long> friends;

    public static long getNextId() {
        return ++nextId;
    }

    @Override
    public String toString() {
        String friendsIdList = "";
        if (!ObjectUtils.isEmpty(friends)) {
            friendsIdList = friends.toString();
        }

        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", friends=" + friendsIdList +
                '}';
    }
}
