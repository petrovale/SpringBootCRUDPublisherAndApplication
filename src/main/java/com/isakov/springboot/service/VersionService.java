package com.isakov.springboot.service;

import com.isakov.springboot.model.Version;

import java.util.List;

public interface VersionService {

    Version saveVersion(Version newVersion, long appId, long publisherId);

    void deleteById(long versionId, long appId, long publisherId);

    List<Version> getAll(long appId, long publisherId);

    Version get(long versionId, long appId, long publisherId);
}
