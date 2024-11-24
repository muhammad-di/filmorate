package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new ConcurrentHashMap<>();
}
