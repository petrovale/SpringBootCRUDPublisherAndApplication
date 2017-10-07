package com.isakov.springboot.service;

import com.isakov.springboot.model.App;
import com.isakov.springboot.model.AppVersion;
import com.isakov.springboot.repositories.AppVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.isakov.springboot.util.VersionUtil.prepareToSave;

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

    @Transactional
    @Override
    public void saveAppVersion(AppVersion newVersion) {
        if (newVersion.isActive()) {
            AppVersion version = appVersionRepository.findByActiveTrue();
            if (version != null) {
                appVersionRepository.save(
                        prepareToSave(version));
            }
        }
        appVersionRepository.save(newVersion);
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
    public List<AppVersion> findAllAppVersions(Long appId) {
        return appVersionRepository.findByApp_id(appId);
    }

    @Override
    public boolean isAppVersionExist(AppVersion appVersion) {
        return findByName(appVersion.getName()) != null;
    }
}
