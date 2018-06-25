package com.crashlytics.android.core;

import java.io.File;
import java.util.Map;

interface Report {
    Map<String, String> getCustomHeaders();

    File getFile();

    String getFileName();

    File[] getFiles();

    String getIdentifier();

    void remove();
}
