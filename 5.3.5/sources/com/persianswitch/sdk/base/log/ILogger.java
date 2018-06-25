package com.persianswitch.sdk.base.log;

public interface ILogger {
    void clear();

    String collect();

    void log(int i, String str, String str2, Throwable th, Object... objArr);
}
