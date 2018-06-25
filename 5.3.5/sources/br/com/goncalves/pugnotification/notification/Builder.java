package br.com.goncalves.pugnotification.notification;

import android.app.Notification;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

public abstract class Builder {
    private static final String TAG = Builder.class.getSimpleName();
    protected android.support.v4.app.NotificationCompat.Builder builder;
    protected Notification notification;
    protected int notificationId;
    protected String tag;

    public Builder(android.support.v4.app.NotificationCompat.Builder builder, int identifier, String tag) {
        this.builder = builder;
        this.notificationId = identifier;
        this.tag = tag;
    }

    public void build() {
        this.notification = this.builder.build();
    }

    public void setBigContentView(RemoteViews views) {
        if (VERSION.SDK_INT >= 16) {
            this.notification.bigContentView = views;
        } else {
            Log.w(TAG, "Version does not support big content view");
        }
    }

    protected Notification notificationNotify() {
        if (this.tag != null) {
            return notificationNotify(this.tag, this.notificationId);
        }
        return notificationNotify(this.notificationId);
    }

    protected Notification notificationNotify(int identifier) {
        NotificationManagerCompat.from(PugNotification.mSingleton.mContext).notify(identifier, this.notification);
        return this.notification;
    }

    protected Notification notificationNotify(String tag, int identifier) {
        NotificationManagerCompat.from(PugNotification.mSingleton.mContext).notify(tag, identifier, this.notification);
        return this.notification;
    }
}
