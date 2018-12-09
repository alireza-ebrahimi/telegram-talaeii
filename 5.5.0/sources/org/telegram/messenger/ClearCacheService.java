package org.telegram.messenger;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build.VERSION;
import android.system.Os;
import android.system.StructStat;
import java.io.File;
import java.util.Map.Entry;

public class ClearCacheService extends IntentService {
    public ClearCacheService() {
        super("ClearCacheService");
    }

    protected void onHandleIntent(Intent intent) {
        ApplicationLoader.postInitApplication();
        final int i = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("keep_media", 2);
        if (i != 2) {
            Utilities.globalQueue.postRunnable(new Runnable() {
                public void run() {
                    long currentTimeMillis = System.currentTimeMillis();
                    int i = i == 0 ? 7 : i == 1 ? 30 : 3;
                    long j = (long) (i * 86400000);
                    for (Entry entry : ImageLoader.getInstance().createMediaPaths().entrySet()) {
                        if (((Integer) entry.getKey()).intValue() != 4) {
                            File[] listFiles = ((File) entry.getValue()).listFiles();
                            if (listFiles != null) {
                                for (File file : listFiles) {
                                    if (file.isFile() && !file.getName().equals(".nomedia")) {
                                        if (VERSION.SDK_INT >= 21) {
                                            try {
                                                StructStat stat = Os.stat(file.getPath());
                                                if (stat.st_atime != 0) {
                                                    if (stat.st_atime + j < currentTimeMillis) {
                                                        file.delete();
                                                    }
                                                } else if (stat.st_mtime + j < currentTimeMillis) {
                                                    file.delete();
                                                }
                                            } catch (Throwable e) {
                                                FileLog.m13728e(e);
                                            } catch (Throwable e2) {
                                                FileLog.m13728e(e2);
                                            }
                                        } else if (file.lastModified() + j < currentTimeMillis) {
                                            file.delete();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }
}
