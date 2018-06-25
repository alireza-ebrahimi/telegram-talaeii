package com.persianswitch.sdk.base.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

final class LogCollector {
    public static final String LINE_SEPARATOR = "\n";
    private static final String TAG = "LogCollector";

    LogCollector() {
    }

    public static String collectLog() {
        StringBuilder log = new StringBuilder(250);
        try {
            ArrayList<String> commandLine = new ArrayList();
            commandLine.add("logcat");
            commandLine.add("-d");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec((String[]) commandLine.toArray(new String[commandLine.size()])).getInputStream()));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                log.append(line);
                log.append(LINE_SEPARATOR);
            }
        } catch (IOException e) {
            SDKLog.m36e(TAG, "LogCollector.doInBackground failed", e, new Object[0]);
        }
        return log.toString();
    }
}
