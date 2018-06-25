package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Action;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ap.C0261a;
import android.support.v4.app.ba.C0312a;
import android.widget.RemoteViews;
import java.util.ArrayList;

@TargetApi(20)
class am {

    /* renamed from: android.support.v4.app.am$a */
    public static class C0288a implements aj, ak {
        /* renamed from: a */
        private Builder f940a;
        /* renamed from: b */
        private Bundle f941b;
        /* renamed from: c */
        private RemoteViews f942c;
        /* renamed from: d */
        private RemoteViews f943d;

        public C0288a(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, CharSequence charSequence4, boolean z4, ArrayList<String> arrayList, Bundle bundle, String str, boolean z5, String str2, RemoteViews remoteViews2, RemoteViews remoteViews3) {
            this.f940a = new Builder(context).setWhen(notification.when).setShowWhen(z2).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent2, (notification.flags & 128) != 0).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(z3).setPriority(i4).setProgress(i2, i3, z).setLocalOnly(z4).setGroup(str).setGroupSummary(z5).setSortKey(str2);
            this.f941b = new Bundle();
            if (bundle != null) {
                this.f941b.putAll(bundle);
            }
            if (!(arrayList == null || arrayList.isEmpty())) {
                this.f941b.putStringArray("android.people", (String[]) arrayList.toArray(new String[arrayList.size()]));
            }
            this.f942c = remoteViews2;
            this.f943d = remoteViews3;
        }

        /* renamed from: a */
        public Builder mo227a() {
            return this.f940a;
        }

        /* renamed from: a */
        public void mo228a(C0261a c0261a) {
            am.m1325a(this.f940a, c0261a);
        }

        /* renamed from: b */
        public Notification mo229b() {
            this.f940a.setExtras(this.f941b);
            Notification build = this.f940a.build();
            if (this.f942c != null) {
                build.contentView = this.f942c;
            }
            if (this.f943d != null) {
                build.bigContentView = this.f943d;
            }
            return build;
        }
    }

    /* renamed from: a */
    private static Action m1323a(C0261a c0261a) {
        Action.Builder builder = new Action.Builder(c0261a.mo209a(), c0261a.mo210b(), c0261a.mo211c());
        Bundle bundle = c0261a.mo212d() != null ? new Bundle(c0261a.mo212d()) : new Bundle();
        bundle.putBoolean("android.support.allowGeneratedReplies", c0261a.mo213e());
        builder.addExtras(bundle);
        C0312a[] g = c0261a.mo214g();
        if (g != null) {
            for (RemoteInput addRemoteInput : az.m1409a(g)) {
                builder.addRemoteInput(addRemoteInput);
            }
        }
        return builder.build();
    }

    /* renamed from: a */
    public static ArrayList<Parcelable> m1324a(C0261a[] c0261aArr) {
        if (c0261aArr == null) {
            return null;
        }
        ArrayList<Parcelable> arrayList = new ArrayList(c0261aArr.length);
        for (C0261a a : c0261aArr) {
            arrayList.add(m1323a(a));
        }
        return arrayList;
    }

    /* renamed from: a */
    public static void m1325a(Builder builder, C0261a c0261a) {
        Action.Builder builder2 = new Action.Builder(c0261a.mo209a(), c0261a.mo210b(), c0261a.mo211c());
        if (c0261a.mo214g() != null) {
            for (RemoteInput addRemoteInput : az.m1409a(c0261a.mo214g())) {
                builder2.addRemoteInput(addRemoteInput);
            }
        }
        Bundle bundle = c0261a.mo212d() != null ? new Bundle(c0261a.mo212d()) : new Bundle();
        bundle.putBoolean("android.support.allowGeneratedReplies", c0261a.mo213e());
        builder2.addExtras(bundle);
        builder.addAction(builder2.build());
    }
}
