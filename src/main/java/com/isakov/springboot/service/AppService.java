package com.isakov.springboot.service;

import com.isakov.springboot.model.App;

import java.util.List;

public interface AppService {

    App findById(Long id);

    App findByName(String name);

    void saveApp(App app);

    void updateApp(App app);

    void deleteAppById(Long id);

    void deleteAllApps();

    List<App> findAllApps(Long publisherId);

    boolean isAppExist(App app);
}
