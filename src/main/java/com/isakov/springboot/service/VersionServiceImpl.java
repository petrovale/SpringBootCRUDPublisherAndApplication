package com.isakov.springboot.service;

import com.isakov.springboot.model.Version;
import com.isakov.springboot.repositories.AppRepository;
import com.isakov.springboot.repositories.VersionRepository;
import com.isakov.springboot.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.isakov.springboot.util.VersionUtil.prepareToSave;

@Service
public class VersionServiceImpl implements VersionService {
    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public Version findById(Long id) {
        return versionRepository.findOne(id);
    }

    @Override
    public Version findByName(String name) {
        return versionRepository.findByName(name);
    }

    @Transactional
    @Override
    public Version saveVersion(Version newVersion, long appId, long publisherId) {
        if (!newVersion.isNew() && get(newVersion.getId(), appId, publisherId) == null) {
            return null;
        }
        newVersion.setApp(appRepository.getOne(appId));
        newVersion.setPublisher(publisherRepository.getOne(publisherId));
        if (newVersion.isActive()) {
            Version version = versionRepository.findByActiveTrue();
            if (version != null) {
                versionRepository.save(prepareToSave(version));
            }
        }
        return versionRepository.save(newVersion);
    }

    @Override
    public Version get(long versionId, long appId, long publisherId) {
        Version version = versionRepository.findOne(versionId);
        return version != null &&
                version.getApp().getId() == appId &&
                version.getPublisher().getId() == publisherId ?
                version : null;
    }

    @Override
    public void updateAppVersion(Version appVersion, long appId, long publisherId) {
        saveVersion(appVersion, appId, publisherId);
    }

    @Override
    public void deleteById(long versionId, long appId, long publisherId) {
        versionRepository.delete(versionId, appId, publisherId);
    }

    @Override
    public void deleteAllAppVersions() {
        versionRepository.deleteAll();
    }

    @Override
    public List<Version> findAllAppVersions(long appId,long publisherId) {
        return versionRepository.findByApp_idAndPublisher_id(appId, publisherId);
    }

    @Override
    public boolean isAppVersionExist(Version appVersion) {
        return findByName(appVersion.getName()) != null;
    }
}
