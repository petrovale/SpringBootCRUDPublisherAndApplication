package com.isakov.springboot.service;

import com.isakov.springboot.model.App;

import java.util.List;

public interface AppService {

    App findById(Long id);

    App findByName(String name);

    App saveApp(App app, long publisherId);

    App get(long id, long publisherId);

    void updateApp(App app, long publisherId);

    void deleteAppById(Long id);

    void deleteAllApps();

    List<App> findAllApps(Long publisherId);

    boolean isAppExist(App app);
}
