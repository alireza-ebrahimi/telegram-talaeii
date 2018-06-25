package br.com.goncalves.pugnotification.notification;

import android.support.v4.app.NotificationCompat.Builder;

public class Progress extends Builder {
    public Progress(Builder builder, int identifier, String tag) {
        super(builder, identifier, tag);
    }

    public void build() {
        super.build();
        super.notificationNotify();
    }

    public Progress update(int identifier, int progress, int max, boolean indeterminate) {
        Builder builder = new Builder(PugNotification.mSingleton.mContext);
        builder.setProgress(max, progress, indeterminate);
        this.notification = builder.build();
        notificationNotify(identifier);
        return this;
    }

    public Progress value(int progress, int max, boolean indeterminate) {
        this.builder.setProgress(max, progress, indeterminate);
        return this;
    }
}
