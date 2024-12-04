package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new ConcurrentHashMap<>();


    @Override
    public Film create(Film film) {
        film.setId(Film.getNextId());
        films.put(film.getId(), film);
        log.info("Repository: created film with ID {}", film.getId());

        return film;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        log.info("Repository: updated film with ID {}", film.getId());

        return film;
    }

    @Override
    public Collection<Film> findAll() {
        log.info("Repository: returning all films size {}", films.size());


        return films.values();
    }

    @Override
    public Collection<Film> findPopular(int count) {
        log.info("Repository: returning {} most popular Films", count);

        Comparator<Film> comparator = Comparator.comparingLong(f -> f.getLikes().size());
        Comparator<Film> comparatorReverse = comparator.reversed();
        List<Film> mostPopular = films.values()
                .stream()
                .sorted(comparatorReverse)
                .toList();


        return mostPopular.subList(0, count-1);
    }

    @Override
    public void addLike(Film film) {
        update(film);
        log.info("Repository: like was successfully added");
    }

    @Override
    public void removeLike(Film film) {
        update(film);
        log.info("Repository: like was successfully deleted");
    }
}
