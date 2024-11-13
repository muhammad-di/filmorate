package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Film.
 */
@Data
@Builder
@EqualsAndHashCode(of = "id")
public class Film {
    private final UUID id;
    private String description;
    private final LocalDate releaseDate;
    private final long duration;
}
