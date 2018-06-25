package org.telegram.messenger;

import com.persianswitch.sdk.base.log.LogCollector;

class FileLog$2 implements Runnable {
    final /* synthetic */ String val$message;

    FileLog$2(String str) {
        this.val$message = str;
    }

    public void run() {
        try {
            FileLog.access$100(FileLog.getInstance()).write(FileLog.access$000(FileLog.getInstance()).format(System.currentTimeMillis()) + " E/tmessages: " + this.val$message + LogCollector.LINE_SEPARATOR);
            FileLog.access$100(FileLog.getInstance()).flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
