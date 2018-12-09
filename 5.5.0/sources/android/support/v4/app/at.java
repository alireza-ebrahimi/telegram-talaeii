package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ap.C0261a;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.List;

@TargetApi(19)
class at {

    /* renamed from: android.support.v4.app.at$a */
    public static class C0293a implements aj, ak {
        /* renamed from: a */
        private Builder f961a;
        /* renamed from: b */
        private Bundle f962b;
        /* renamed from: c */
        private List<Bundle> f963c = new ArrayList();
        /* renamed from: d */
        private RemoteViews f964d;
        /* renamed from: e */
        private RemoteViews f965e;

        public C0293a(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, CharSequence charSequence4, boolean z4, ArrayList<String> arrayList, Bundle bundle, String str, boolean z5, String str2, RemoteViews remoteViews2, RemoteViews remoteViews3) {
            this.f961a = new Builder(context).setWhen(notification.when).setShowWhen(z2).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent2, (notification.flags & 128) != 0).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(z3).setPriority(i4).setProgress(i2, i3, z);
            this.f962b = new Bundle();
            if (bundle != null) {
                this.f962b.putAll(bundle);
            }
            if (!(arrayList == null || arrayList.isEmpty())) {
                this.f962b.putStringArray("android.people", (String[]) arrayList.toArray(new String[arrayList.size()]));
            }
            if (z4) {
                this.f962b.putBoolean("android.support.localOnly", true);
            }
            if (str != null) {
                this.f962b.putString("android.support.groupKey", str);
                if (z5) {
                    this.f962b.putBoolean("android.support.isGroupSummary", true);
                } else {
                    this.f962b.putBoolean("android.support.useSideChannel", true);
                }
            }
            if (str2 != null) {
                this.f962b.putString("android.support.sortKey", str2);
            }
            this.f964d = remoteViews2;
            this.f965e = remoteViews3;
        }

        /* renamed from: a */
        public Builder mo227a() {
            return this.f961a;
        }

        /* renamed from: a */
        public void mo228a(C0261a c0261a) {
            this.f963c.add(as.m1342a(this.f961a, c0261a));
        }

        /* renamed from: b */
        public Notification mo229b() {
            SparseArray a = as.m1345a(this.f963c);
            if (a != null) {
                this.f962b.putSparseParcelableArray("android.support.actionExtras", a);
            }
            this.f961a.setExtras(this.f962b);
            Notification build = this.f961a.build();
            if (this.f964d != null) {
                build.contentView = this.f964d;
            }
            if (this.f965e != null) {
                build.bigContentView = this.f965e;
            }
            return build;
        }
    }

    /* renamed from: a */
    public static Bundle m1353a(Notification notification) {
        return notification.extras;
    }
}
