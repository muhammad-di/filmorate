package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FilmService {
    Film create(Film film);

    Film update(Film film);

    Film findByIf(long id);


    Collection<Film> findAll();

    Collection<Film> findPopular(int count);

    void addLike(long id, long userId);

    void removeLike(long id, long userId);
}
