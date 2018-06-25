package org.telegram.messenger;

import com.persianswitch.sdk.base.log.LogCollector;

class FileLog$1 implements Runnable {
    final /* synthetic */ Throwable val$exception;
    final /* synthetic */ String val$message;

    FileLog$1(String str, Throwable th) {
        this.val$message = str;
        this.val$exception = th;
    }

    public void run() {
        try {
            FileLog.access$100(FileLog.getInstance()).write(FileLog.access$000(FileLog.getInstance()).format(System.currentTimeMillis()) + " E/tmessages: " + this.val$message + LogCollector.LINE_SEPARATOR);
            FileLog.access$100(FileLog.getInstance()).write(this.val$exception.toString());
            FileLog.access$100(FileLog.getInstance()).flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
