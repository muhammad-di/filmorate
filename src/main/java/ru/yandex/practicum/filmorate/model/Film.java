package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
@Builder(toBuilder = true)
public class Film {
    private static long nextId = 0;
    private long id;
    private String name;
    private String description;
    private final LocalDate releaseDate;
    private final long duration;
    @Builder.Default
    private Set<Long> likes = new HashSet<>();

    public Film(long id, String name, String description, LocalDate releaseDate, long duration, Set<Long> likes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = ObjectUtils.isEmpty(likes) ? new HashSet<>() : likes;
    }

    public static long getNextId() {
        return ++nextId;
    }
}
