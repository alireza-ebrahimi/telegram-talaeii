package com.crashlytics.android.core;

interface FileLogStore {
    void closeLogFile();

    void deleteLogFile();

    ByteString getLogAsByteString();

    void writeToLog(long j, String str);
}
