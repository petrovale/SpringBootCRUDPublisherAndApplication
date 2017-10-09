package com.isakov.springboot.service;

import com.isakov.springboot.model.App;
import com.isakov.springboot.model.Version;

import java.util.List;

public interface AppService {

    App saveApp(App app, int publisherId, int genreId);

    App get(int id, int publisherId);

    int deleteAppById(int id, int publisherId);

    List<App> getAll(int publisherId);

    List<App> getAllWithGenre(int id);
}
