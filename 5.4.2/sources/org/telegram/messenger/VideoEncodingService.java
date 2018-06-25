package org.telegram.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.al.C0266d;
import android.support.v4.app.au;
import org.ir.talaeii.R;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;

public class VideoEncodingService extends Service implements NotificationCenterDelegate {
    private C0266d builder;
    private int currentProgress;
    private String path;

    public VideoEncodingService() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileUploadProgressChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.stopEncodingService);
    }

    public void didReceivedNotification(int i, Object... objArr) {
        String str;
        if (i == NotificationCenter.FileUploadProgressChanged) {
            str = (String) objArr[0];
            if (this.path != null && this.path.equals(str)) {
                Boolean bool = (Boolean) objArr[2];
                this.currentProgress = (int) (((Float) objArr[1]).floatValue() * 100.0f);
                this.builder.m1229a(100, this.currentProgress, this.currentProgress == 0);
                try {
                    au.m1378a(ApplicationLoader.applicationContext).m1383a(4, this.builder.m1242b());
                } catch (Throwable th) {
                    FileLog.m13728e(th);
                }
            }
        } else if (i == NotificationCenter.stopEncodingService) {
            str = (String) objArr[0];
            if (str == null || str.equals(this.path)) {
                stopSelf();
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        stopForeground(true);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileUploadProgressChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.stopEncodingService);
        FileLog.m13726e("destroy video service");
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        boolean z = false;
        this.path = intent.getStringExtra("path");
        boolean booleanExtra = intent.getBooleanExtra("gif", false);
        if (this.path == null) {
            stopSelf();
        } else {
            FileLog.m13726e("start video service");
            if (this.builder == null) {
                this.builder = new C0266d(ApplicationLoader.applicationContext);
                this.builder.m1227a(17301640);
                this.builder.m1231a(System.currentTimeMillis());
                this.builder.m1238a(LocaleController.getString("AppName", R.string.AppName));
                if (booleanExtra) {
                    this.builder.m1249c(LocaleController.getString("SendingGif", R.string.SendingGif));
                    this.builder.m1245b(LocaleController.getString("SendingGif", R.string.SendingGif));
                } else {
                    this.builder.m1249c(LocaleController.getString("SendingVideo", R.string.SendingVideo));
                    this.builder.m1245b(LocaleController.getString("SendingVideo", R.string.SendingVideo));
                }
            }
            this.currentProgress = 0;
            C0266d c0266d = this.builder;
            int i3 = this.currentProgress;
            if (this.currentProgress == 0) {
                z = true;
            }
            c0266d.m1229a(100, i3, z);
            startForeground(4, this.builder.m1242b());
            au.m1378a(ApplicationLoader.applicationContext).m1383a(4, this.builder.m1242b());
        }
        return 2;
    }
}
