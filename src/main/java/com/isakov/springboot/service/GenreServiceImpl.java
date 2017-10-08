package com.isakov.springboot.service;

import com.isakov.springboot.model.Genre;
import com.isakov.springboot.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Genre> getAll() {
        return (List<Genre>) genreRepository.findAll();
    }


}
