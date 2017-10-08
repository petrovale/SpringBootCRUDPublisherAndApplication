package com.isakov.springboot.service;

import com.isakov.springboot.model.Version;

import java.util.List;

public interface VersionService {

    Version findById(Long id);

    Version findByName(String name);

    Version saveVersion(Version newVersion, long appId, long publisherId);

    void updateAppVersion(Version version, long appId, long publisherId);

    void deleteById(long versionId, long appId, long publisherId);

    void deleteAllAppVersions();

    List<Version> findAllAppVersions(long appId, long publisherId);

    boolean isAppVersionExist(Version version);

    Version get(long versionId, long appId, long publisherId);
}
