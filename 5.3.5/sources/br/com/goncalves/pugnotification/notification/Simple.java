package br.com.goncalves.pugnotification.notification;

import android.support.v4.app.NotificationCompat.Builder;

public class Simple extends Builder {
    public Simple(Builder builder, int identifier, String tag) {
        super(builder, identifier, tag);
    }

    public void build() {
        super.build();
        super.notificationNotify();
    }
}
