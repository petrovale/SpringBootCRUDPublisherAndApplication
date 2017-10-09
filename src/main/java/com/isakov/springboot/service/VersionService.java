package com.isakov.springboot.service;

import com.isakov.springboot.model.Version;

import java.util.List;

public interface VersionService {

    Version saveVersion(Version newVersion, int appId, int publisherId);

    int deleteById(int versionId, int appId, int publisherId);

    List<Version> getAll(int appId, int publisherId);

    Version get(int versionId, int appId, int publisherId);
}
