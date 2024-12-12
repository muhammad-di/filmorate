package ru.yandex.practicum.filmorate.service.film.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exseption.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validation.film.FilmValidator;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;
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
        getIfExist(film.getId());
        log.info("Service: validated existence of film with ID {}", film.getId());

        log.info("Service: updating a film with name {} into repository", film.getName());
        return filmStorage.update(film);
    }

    @Override
    public Film findById(final long id) {
        log.info("Service: getting film by ID {}", id);

        log.info("Service: starting request to repository to get film by ID {}", id);
        Film film = getIfExist(id);

        log.info("Service: Film with ID {} was found in the database, returning film", id);
        return film;
    }

    @Override
    public Collection<Film> findAll() {
        log.info("Service: starting to get all films");

        return filmStorage.findAll();
    }

    @Override
    public Collection<Film> findPopular(final int count) {
        log.info("Service: starting to get {} popular films", count);

        return filmStorage.findPopular(count);
    }

    @Override
    public void addLike(final long id, final long userId) {
        log.info("Service: starting to add like from user with ID {} to film with ID {}", userId, id);

        Film film = validateUserAndFilm(id, userId);
        film.getLikes().add(userId);
        saveFilmToStorage(film);
    }

    @Override
    public void removeLike(long id, long userId) {
        log.info("Service: starting to delete like for user with ID {} from film with ID {}", userId, id);

        Film film = validateUserAndFilm(id, userId);
        film.getLikes().remove(userId);
        saveFilmToStorage(film);
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

    private Film validateUserAndFilm(final long id, final long userId) {
        userService.contains(userId);
        log.info("Service: validated existence of user with ID {}", userId);
        Film film = getIfExist(id);
        log.info("Service: validated existence of film with ID {}", id);

        return film;
    }

    private void saveFilmToStorage(final Film film) {
        log.info("Service: saving to storage updated film with ID {}", film.getId());
        filmStorage.addLike(film);
    }
}
