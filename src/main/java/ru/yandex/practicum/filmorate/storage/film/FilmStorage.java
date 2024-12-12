package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    Optional<Film> findById(long id);

    Collection<Film> findAll();

    Collection<Film> findPopular(int count);

    void addLike(Film film);

    void removeLike(Film film);
}
