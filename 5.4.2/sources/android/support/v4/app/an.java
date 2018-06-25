package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ap.C0261a;
import android.support.v4.app.ap.C0271b;
import android.support.v4.app.ba.C0312a;
import android.widget.RemoteViews;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.messenger.exoplayer2.util.MimeTypes;

@TargetApi(21)
class an {

    /* renamed from: android.support.v4.app.an$a */
    public static class C0289a implements aj, ak {
        /* renamed from: a */
        private Builder f944a;
        /* renamed from: b */
        private Bundle f945b;
        /* renamed from: c */
        private RemoteViews f946c;
        /* renamed from: d */
        private RemoteViews f947d;
        /* renamed from: e */
        private RemoteViews f948e;

        public C0289a(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, CharSequence charSequence4, boolean z4, String str, ArrayList<String> arrayList, Bundle bundle, int i5, int i6, Notification notification2, String str2, boolean z5, String str3, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4) {
            this.f944a = new Builder(context).setWhen(notification.when).setShowWhen(z2).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent2, (notification.flags & 128) != 0).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(z3).setPriority(i4).setProgress(i2, i3, z).setLocalOnly(z4).setGroup(str2).setGroupSummary(z5).setSortKey(str3).setCategory(str).setColor(i5).setVisibility(i6).setPublicVersion(notification2);
            this.f945b = new Bundle();
            if (bundle != null) {
                this.f945b.putAll(bundle);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.f944a.addPerson((String) it.next());
            }
            this.f946c = remoteViews2;
            this.f947d = remoteViews3;
            this.f948e = remoteViews4;
        }

        /* renamed from: a */
        public Builder mo227a() {
            return this.f944a;
        }

        /* renamed from: a */
        public void mo228a(C0261a c0261a) {
            am.m1325a(this.f944a, c0261a);
        }

        /* renamed from: b */
        public Notification mo229b() {
            this.f944a.setExtras(this.f945b);
            Notification build = this.f944a.build();
            if (this.f946c != null) {
                build.contentView = this.f946c;
            }
            if (this.f947d != null) {
                build.bigContentView = this.f947d;
            }
            if (this.f948e != null) {
                build.headsUpContentView = this.f948e;
            }
            return build;
        }
    }

    /* renamed from: a */
    private static RemoteInput m1329a(C0312a c0312a) {
        return new RemoteInput.Builder(c0312a.mo236a()).setLabel(c0312a.mo237b()).setChoices(c0312a.mo238c()).setAllowFreeFormInput(c0312a.mo239d()).addExtras(c0312a.mo240e()).build();
    }

    /* renamed from: a */
    static Bundle m1330a(C0271b c0271b) {
        String str = null;
        int i = 0;
        if (c0271b == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        if (c0271b.mo218e() != null && c0271b.mo218e().length > 1) {
            str = c0271b.mo218e()[0];
        }
        Parcelable[] parcelableArr = new Parcelable[c0271b.mo215a().length];
        while (i < parcelableArr.length) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(MimeTypes.BASE_TYPE_TEXT, c0271b.mo215a()[i]);
            bundle2.putString("author", str);
            parcelableArr[i] = bundle2;
            i++;
        }
        bundle.putParcelableArray("messages", parcelableArr);
        C0312a g = c0271b.mo220g();
        if (g != null) {
            bundle.putParcelable("remote_input", m1329a(g));
        }
        bundle.putParcelable("on_reply", c0271b.mo216c());
        bundle.putParcelable("on_read", c0271b.mo217d());
        bundle.putStringArray("participants", c0271b.mo218e());
        bundle.putLong(Param.TIMESTAMP, c0271b.mo219f());
        return bundle;
    }
}
