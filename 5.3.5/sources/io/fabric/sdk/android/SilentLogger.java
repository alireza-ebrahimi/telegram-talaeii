package io.fabric.sdk.android;

public class SilentLogger implements Logger {
    private int logLevel = 7;

    public boolean isLoggable(String tag, int level) {
        return false;
    }

    /* renamed from: d */
    public void mo4382d(String tag, String text, Throwable throwable) {
    }

    /* renamed from: v */
    public void mo4393v(String tag, String text, Throwable throwable) {
    }

    /* renamed from: i */
    public void mo4387i(String tag, String text, Throwable throwable) {
    }

    /* renamed from: w */
    public void mo4395w(String tag, String text, Throwable throwable) {
    }

    /* renamed from: e */
    public void mo4384e(String tag, String text, Throwable throwable) {
    }

    /* renamed from: d */
    public void mo4381d(String tag, String text) {
    }

    /* renamed from: v */
    public void mo4392v(String tag, String text) {
    }

    /* renamed from: i */
    public void mo4386i(String tag, String text) {
    }

    /* renamed from: w */
    public void mo4394w(String tag, String text) {
    }

    /* renamed from: e */
    public void mo4383e(String tag, String text) {
    }

    public void log(int priority, String tag, String msg) {
    }

    public void log(int priority, String tag, String msg, boolean forceLog) {
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(int logLevel) {
    }
}
