package ru.yandex.practicum.filmorate.service.film.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validation.film.FilmValidator;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final List<FilmValidator> filmValidators;


    @Override
    public Film create(final Film film) {
        log.info("Service: starting creation of a film with name {}", film.getName());

        filmValidators.forEach(v -> v.validate(film));
        log.info("Service: successful validation  of a film with name {}", film.getName());

        log.info("Service: saving a film with name {} into repository", film.getName());
        return filmStorage.create(film);
    }

    @Override
    public Film update(Film film) {
        log.info("Service: update creation of a film with name {}", film.getName());

        filmValidators.forEach(v -> v.validate(film));
        log.info("Service: successful validation  of a film with name {}", film.getName());

        log.info("Service: saving a film with name {} into repository", film.getName());
        return filmStorage.create(film);
    }

    @Override
    public Film findByIf(long id) {
        return null;
    }

    @Override
    public Collection<Film> findAll() {
        return List.of();
    }

    @Override
    public Collection<Film> findPopular(int count) {
        return List.of();
    }

    @Override
    public void addLike(long id, long userId) {

    }

    @Override
    public void removeLike(long id, long userId) {

    }
}
