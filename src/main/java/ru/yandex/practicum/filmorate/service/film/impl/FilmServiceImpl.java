package ru.yandex.practicum.filmorate.service.film.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exseption.EntityNotFoundException;
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
        validate(film);

        log.info("Service: saving a film with name {} into repository", film.getName());
        return filmStorage.create(film);
    }

    @Override
    public Film update(Film film) {
        log.info("Service: update creation of a film with name {}", film.getName());
        validate(film);

        log.info("Service: updating a film with name {} into repository", film.getName());
        return filmStorage.update(film);
    }

    @Override
    public Film findByIf(final long id) {
        log.info("Service: getting film by ID {}", id);

        log.info("Service: starting request to repository to get film by ID {}", id);
        Film film = getIfExist(id);

        log.info("Service: Film with ID {} was found in the database, returning film", id);
        return film;
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

    private void validate(Film film) {
        filmValidators.forEach(v -> v.validate(film));
        log.info("Service: successful validation  of a film with name {}", film.getName());
    }

    private Film getIfExist(final long id) {
        return filmStorage.findById(id).orElseThrow(() -> {
                    log.warn("Service: Film with ID {} not found in the database", id);
                    return new EntityNotFoundException("Film does not exist");
                }
        );
    }
}
