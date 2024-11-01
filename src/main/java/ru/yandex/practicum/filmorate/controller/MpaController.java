package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {
   private final MpaService ratingService;

   @GetMapping
    public Collection<Mpa> findAll() {
       return ratingService.findAll();
   }

   @GetMapping("/{id}")
    public Mpa getRatingById(@PathVariable Long id) {
       return ratingService.findById(id);
   }
}
