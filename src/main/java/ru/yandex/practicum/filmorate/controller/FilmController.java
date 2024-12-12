package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Validated
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        log.info("Controller: creating film with name {}", film.getName());

        return filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        log.info("Controller: updating film with name {}", film.getName());

        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film findById(@PathVariable @Positive long id) {
        log.info("Controller: getting film with ID {}", id);

        return filmService.findById(id);
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Controller: getting all films");

        return filmService.findAll();
    }


}
