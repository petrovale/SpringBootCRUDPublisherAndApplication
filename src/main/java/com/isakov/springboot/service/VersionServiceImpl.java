package com.isakov.springboot.service;

import com.isakov.springboot.model.Version;
import com.isakov.springboot.repository.AppRepository;
import com.isakov.springboot.repository.VersionRepository;
import com.isakov.springboot.repository.PublisherRepository;
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

    @Transactional
    @Override
    public Version saveVersion(Version newVersion, int appId, int publisherId) {
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
    public Version get(int versionId, int appId, int publisherId) {
        Version version = versionRepository.findOne(versionId);
        return version != null &&
                version.getApp().getId() == appId &&
                version.getPublisher().getId() == publisherId ?
                version : null;
    }

    @Override
    public int deleteById(int versionId, int appId, int publisherId) {
        return versionRepository.delete(versionId, appId, publisherId);
    }

    @Override
    public List<Version> getAll(int appId, int publisherId) {
        return versionRepository.getAll(appId, publisherId);
    }
}
