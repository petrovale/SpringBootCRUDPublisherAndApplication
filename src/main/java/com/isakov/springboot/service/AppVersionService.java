package com.isakov.springboot.service;

import com.isakov.springboot.model.App;
import com.isakov.springboot.model.AppVersion;

import java.util.List;

public interface AppVersionService {

    AppVersion findById(Long id);

    AppVersion findByName(String name);

    void saveAppVersion(AppVersion appVersion);

    void updateAppVersion(AppVersion appVersion);

    void deleteAppById(Long id);

    void deleteAllAppVersions();

    List<AppVersion> findAllAppVersions(Long appId);

    boolean isAppVersionExist(AppVersion appVersion);
}
