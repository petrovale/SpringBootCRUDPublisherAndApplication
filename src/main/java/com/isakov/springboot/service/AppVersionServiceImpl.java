package com.isakov.springboot.service;

import com.isakov.springboot.model.AppVersion;
import com.isakov.springboot.repositories.AppVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppVersionServiceImpl implements AppVersionService {
    @Autowired
    private AppVersionRepository appVersionRepository;

    @Override
    public AppVersion findById(Long id) {
        return appVersionRepository.findOne(id);
    }

    @Override
    public AppVersion findByName(String name) {
        return appVersionRepository.findByName(name);
    }

    @Override
    public void saveAppVersion(AppVersion appVersion) {
        appVersionRepository.save(appVersion);
    }

    @Override
    public void updateAppVersion(AppVersion appVersion) {
        saveAppVersion(appVersion);
    }

    @Override
    public void deleteAppById(Long id) {
        appVersionRepository.delete(id);
    }

    @Override
    public void deleteAllAppVersions() {
        appVersionRepository.deleteAll();
    }

    @Override
    public List<AppVersion> findAllAppVersions() {
        return appVersionRepository.findAll();
    }

    @Override
    public boolean isAppVersionExist(AppVersion appVersion) {
        return findByName(appVersion.getName()) != null;
    }
}
