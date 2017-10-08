package com.isakov.springboot.service;

import com.isakov.springboot.model.App;
import com.isakov.springboot.model.Version;

import java.util.List;

public interface AppService {

    App saveApp(App app, long publisherId, long genreId);

    App get(long id, long publisherId);

    int deleteAppById(Long id, Long publisherId);

    List<App> getAll(Long publisherId);

    List<App> getAllWithGenre(long id);
}
