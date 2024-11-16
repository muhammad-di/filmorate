package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.film.FilmValidator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/films")
@AllArgsConstructor
@Validated
@Slf4j
public class FilmController {
    private final List<FilmValidator> validators;
    private final Map<Long, Film> films = new ConcurrentHashMap<>();


    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        validators.forEach(v -> v.validate(film));
        film.setId(User.getNextId());
        films.put(film.getId(), film);
        log.debug("users: {}", film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        contains(film);
        validators.forEach(v -> v.validate(film));
        films.put(film.getId(), film);
        log.debug("films: {}", films);
        return film;
    }

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }


    private void contains(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Film does not exist");
        }
    }
}
