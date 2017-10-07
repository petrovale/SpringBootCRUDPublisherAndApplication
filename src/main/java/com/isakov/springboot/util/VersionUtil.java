package com.isakov.springboot.util;

import com.isakov.springboot.model.AppVersion;

public class VersionUtil {
    private VersionUtil() {
    }

    public static AppVersion prepareToSave(AppVersion version) {
        if (version.isActive())
            version.setActive(false);
        return version;
    }
}
