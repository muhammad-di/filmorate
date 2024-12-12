package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Film create(@RequestBody final Film film) {
        log.info("Controller: creating film with name {}", film.getName());

        return filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody final Film film) {
        log.info("Controller: updating film with name {}", film.getName());

        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film findById(@PathVariable @Positive final long id) {
        log.info("Controller: getting film with ID {}", id);

        return filmService.findById(id);
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Controller: getting all films");

        return filmService.findAll();
    }


    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable @Valid final long id,
                        @PathVariable @Valid final long userId) {
        log.info("Controller: adding like from user with ID {} to film with ID {}", id, userId);

        filmService.addLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable @Valid final long id,
                           @PathVariable @Valid final long userId) {
        log.info("Controller: deleting like from user with ID {} for film with ID {}", id, userId);

        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> findPopular(@RequestParam(defaultValue = "10") @Valid final int count) {
        log.info("Controller: getting all popular films");

        return filmService.findPopular(count);
    }
}
