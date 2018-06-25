package utils;

import android.content.Intent;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;

class Utilities$1 implements Runnable {
    final /* synthetic */ int val$count;
    final /* synthetic */ String val$launcherClassName;

    Utilities$1(int i, String str) {
        this.val$count = i;
        this.val$launcherClassName = str;
    }

    public void run() {
        try {
            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", this.val$count);
            intent.putExtra("badge_count_package_name", ApplicationLoader.applicationContext.getPackageName());
            intent.putExtra("badge_count_class_name", this.val$launcherClassName);
            ApplicationLoader.applicationContext.sendBroadcast(intent);
        } catch (Exception e) {
            FileLog.e("tmessages", e);
        }
    }
}
