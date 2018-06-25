package com.persianswitch.sdk.base.log;

import android.util.Log;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

public class MemoryLogCatLogger extends DefaultLogger {
    public static final int MEMORY_SIZE = 50;
    Queue<String> memoryLog = new LinkedList();

    public void log(int priority, String tag, String message, Throwable t, Object... args) {
        super.log(priority, tag, message, t, args);
        if (message != null && message.length() == 0) {
            message = null;
        }
        if (message == null) {
            if (t == null) {
                return;
            }
        } else if (args.length > 0) {
            message = String.format(Locale.US, message, args);
        }
        logInMemory(priority, tag, message);
    }

    private void logInMemory(int priority, String tag, String message) {
        if (this.memoryLog.size() >= 50) {
            this.memoryLog.poll();
        }
        String color = "black";
        switch (priority) {
            case 3:
                color = "blue";
                Log.d(tag, message);
                break;
            case 4:
                color = "green";
                break;
            case 5:
                color = "yellow";
                break;
            case 6:
                color = "red";
                break;
        }
        this.memoryLog.add(String.format(Locale.US, "<font color=\"%s\">%s</font>: %s<br/>", new Object[]{color, tag, message}));
    }

    public String collect() {
        StringBuilder stringBuilder = new StringBuilder(100);
        for (String line : this.memoryLog) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    public void clear() {
        this.memoryLog.clear();
    }
}
