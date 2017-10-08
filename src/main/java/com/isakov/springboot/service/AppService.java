package com.isakov.springboot.service;

import com.isakov.springboot.model.App;

import java.util.List;

public interface AppService {

    App findById(Long id);

    App saveApp(App app, long publisherId);

    App get(long id, long publisherId);

    void deleteAppById(Long id);

    List<App> getAll(Long publisherId);
}
