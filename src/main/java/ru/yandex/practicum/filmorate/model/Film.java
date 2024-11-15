package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
@Builder(toBuilder = true)
public class Film {
    private final long id;
    private String name;
    private String description;
    private final LocalDate releaseDate;
    private final long duration;
}
