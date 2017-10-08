package com.isakov.springboot.util;

import com.isakov.springboot.model.Version;

public class VersionUtil {
    private VersionUtil() {
    }

    public static Version prepareToSave(Version version) {
        if (version.isActive())
            version.setActive(false);
        return version;
    }
}
