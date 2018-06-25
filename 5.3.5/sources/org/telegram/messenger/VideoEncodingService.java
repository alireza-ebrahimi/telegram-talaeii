package org.telegram.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationManagerCompat;
import org.ir.talaeii.R;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;

public class VideoEncodingService extends Service implements NotificationCenterDelegate {
    private Builder builder;
    private int currentProgress;
    private String path;

    public VideoEncodingService() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileUploadProgressChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.stopEncodingService);
    }

    public IBinder onBind(Intent arg2) {
        return null;
    }

    public void onDestroy() {
        stopForeground(true);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileUploadProgressChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.stopEncodingService);
        FileLog.e("destroy video service");
    }

    public void didReceivedNotification(int id, Object... args) {
        boolean z = true;
        if (id == NotificationCenter.FileUploadProgressChanged) {
            String fileName = args[0];
            if (this.path != null && this.path.equals(fileName)) {
                Boolean enc = args[2];
                this.currentProgress = (int) (args[1].floatValue() * 100.0f);
                Builder builder = this.builder;
                int i = this.currentProgress;
                if (this.currentProgress != 0) {
                    z = false;
                }
                builder.setProgress(100, i, z);
                try {
                    NotificationManagerCompat.from(ApplicationLoader.applicationContext).notify(4, this.builder.build());
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        } else if (id == NotificationCenter.stopEncodingService) {
            String filepath = args[0];
            if (filepath == null || filepath.equals(this.path)) {
                stopSelf();
            }
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean z = false;
        this.path = intent.getStringExtra("path");
        boolean isGif = intent.getBooleanExtra("gif", false);
        if (this.path == null) {
            stopSelf();
        } else {
            FileLog.e("start video service");
            if (this.builder == null) {
                this.builder = new Builder(ApplicationLoader.applicationContext);
                this.builder.setSmallIcon(17301640);
                this.builder.setWhen(System.currentTimeMillis());
                this.builder.setContentTitle(LocaleController.getString("AppName", R.string.AppName));
                if (isGif) {
                    this.builder.setTicker(LocaleController.getString("SendingGif", R.string.SendingGif));
                    this.builder.setContentText(LocaleController.getString("SendingGif", R.string.SendingGif));
                } else {
                    this.builder.setTicker(LocaleController.getString("SendingVideo", R.string.SendingVideo));
                    this.builder.setContentText(LocaleController.getString("SendingVideo", R.string.SendingVideo));
                }
            }
            this.currentProgress = 0;
            Builder builder = this.builder;
            int i = this.currentProgress;
            if (this.currentProgress == 0) {
                z = true;
            }
            builder.setProgress(100, i, z);
            startForeground(4, this.builder.build());
            NotificationManagerCompat.from(ApplicationLoader.applicationContext).notify(4, this.builder.build());
        }
        return 2;
    }
}
