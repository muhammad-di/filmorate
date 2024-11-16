package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
@Builder(toBuilder = true)
public class Film {
    private static long nextId = 0;
    private long id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    private final LocalDate releaseDate;
    @Min(1)
    private final long duration;

    public static long getNextId() {
        return ++nextId;
    }
}
