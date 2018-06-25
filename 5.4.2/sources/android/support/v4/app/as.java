package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.Notification.InboxStyle;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ap.C0261a;
import android.util.Log;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(16)
class as {
    /* renamed from: a */
    private static final Object f957a = new Object();
    /* renamed from: b */
    private static Field f958b;
    /* renamed from: c */
    private static boolean f959c;
    /* renamed from: d */
    private static final Object f960d = new Object();

    /* renamed from: android.support.v4.app.as$a */
    public static class C0292a implements aj, ak {
        /* renamed from: a */
        private Builder f952a;
        /* renamed from: b */
        private final Bundle f953b;
        /* renamed from: c */
        private List<Bundle> f954c = new ArrayList();
        /* renamed from: d */
        private RemoteViews f955d;
        /* renamed from: e */
        private RemoteViews f956e;

        public C0292a(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, int i4, CharSequence charSequence4, boolean z3, Bundle bundle, String str, boolean z4, String str2, RemoteViews remoteViews2, RemoteViews remoteViews3) {
            this.f952a = new Builder(context).setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent2, (notification.flags & 128) != 0).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(z2).setPriority(i4).setProgress(i2, i3, z);
            this.f953b = new Bundle();
            if (bundle != null) {
                this.f953b.putAll(bundle);
            }
            if (z3) {
                this.f953b.putBoolean("android.support.localOnly", true);
            }
            if (str != null) {
                this.f953b.putString("android.support.groupKey", str);
                if (z4) {
                    this.f953b.putBoolean("android.support.isGroupSummary", true);
                } else {
                    this.f953b.putBoolean("android.support.useSideChannel", true);
                }
            }
            if (str2 != null) {
                this.f953b.putString("android.support.sortKey", str2);
            }
            this.f955d = remoteViews2;
            this.f956e = remoteViews3;
        }

        /* renamed from: a */
        public Builder mo227a() {
            return this.f952a;
        }

        /* renamed from: a */
        public void mo228a(C0261a c0261a) {
            this.f954c.add(as.m1342a(this.f952a, c0261a));
        }

        /* renamed from: b */
        public Notification mo229b() {
            Notification build = this.f952a.build();
            Bundle a = as.m1343a(build);
            Bundle bundle = new Bundle(this.f953b);
            for (String str : this.f953b.keySet()) {
                if (a.containsKey(str)) {
                    bundle.remove(str);
                }
            }
            a.putAll(bundle);
            SparseArray a2 = as.m1345a(this.f954c);
            if (a2 != null) {
                as.m1343a(build).putSparseParcelableArray("android.support.actionExtras", a2);
            }
            if (this.f955d != null) {
                build.contentView = this.f955d;
            }
            if (this.f956e != null) {
                build.bigContentView = this.f956e;
            }
            return build;
        }
    }

    /* renamed from: a */
    public static Bundle m1342a(Builder builder, C0261a c0261a) {
        builder.addAction(c0261a.mo209a(), c0261a.mo210b(), c0261a.mo211c());
        Bundle bundle = new Bundle(c0261a.mo212d());
        if (c0261a.mo214g() != null) {
            bundle.putParcelableArray("android.support.remoteInputs", bb.m1415a(c0261a.mo214g()));
        }
        bundle.putBoolean("android.support.allowGeneratedReplies", c0261a.mo213e());
        return bundle;
    }

    /* renamed from: a */
    public static Bundle m1343a(Notification notification) {
        synchronized (f957a) {
            if (f959c) {
                return null;
            }
            try {
                if (f958b == null) {
                    Field declaredField = Notification.class.getDeclaredField("extras");
                    if (Bundle.class.isAssignableFrom(declaredField.getType())) {
                        declaredField.setAccessible(true);
                        f958b = declaredField;
                    } else {
                        Log.e("NotificationCompat", "Notification.extras field is not of type Bundle");
                        f959c = true;
                        return null;
                    }
                }
                Bundle bundle = (Bundle) f958b.get(notification);
                if (bundle == null) {
                    bundle = new Bundle();
                    f958b.set(notification, bundle);
                }
                return bundle;
            } catch (Throwable e) {
                Log.e("NotificationCompat", "Unable to access notification extras", e);
                f959c = true;
                return null;
            } catch (Throwable e2) {
                Log.e("NotificationCompat", "Unable to access notification extras", e2);
                f959c = true;
                return null;
            }
        }
    }

    /* renamed from: a */
    private static Bundle m1344a(C0261a c0261a) {
        Bundle bundle = new Bundle();
        bundle.putInt("icon", c0261a.mo209a());
        bundle.putCharSequence("title", c0261a.mo210b());
        bundle.putParcelable("actionIntent", c0261a.mo211c());
        Bundle bundle2 = c0261a.mo212d() != null ? new Bundle(c0261a.mo212d()) : new Bundle();
        bundle2.putBoolean("android.support.allowGeneratedReplies", c0261a.mo213e());
        bundle.putBundle("extras", bundle2);
        bundle.putParcelableArray("remoteInputs", bb.m1415a(c0261a.mo214g()));
        return bundle;
    }

    /* renamed from: a */
    public static SparseArray<Bundle> m1345a(List<Bundle> list) {
        SparseArray<Bundle> sparseArray = null;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Bundle bundle = (Bundle) list.get(i);
            if (bundle != null) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                }
                sparseArray.put(i, bundle);
            }
        }
        return sparseArray;
    }

    /* renamed from: a */
    public static ArrayList<Parcelable> m1346a(C0261a[] c0261aArr) {
        if (c0261aArr == null) {
            return null;
        }
        ArrayList<Parcelable> arrayList = new ArrayList(c0261aArr.length);
        for (C0261a a : c0261aArr) {
            arrayList.add(m1344a(a));
        }
        return arrayList;
    }

    /* renamed from: a */
    public static void m1347a(ak akVar, CharSequence charSequence, boolean z, CharSequence charSequence2, Bitmap bitmap, Bitmap bitmap2, boolean z2) {
        BigPictureStyle bigPicture = new BigPictureStyle(akVar.mo227a()).setBigContentTitle(charSequence).bigPicture(bitmap);
        if (z2) {
            bigPicture.bigLargeIcon(bitmap2);
        }
        if (z) {
            bigPicture.setSummaryText(charSequence2);
        }
    }

    /* renamed from: a */
    public static void m1348a(ak akVar, CharSequence charSequence, boolean z, CharSequence charSequence2, CharSequence charSequence3) {
        BigTextStyle bigText = new BigTextStyle(akVar.mo227a()).setBigContentTitle(charSequence).bigText(charSequence3);
        if (z) {
            bigText.setSummaryText(charSequence2);
        }
    }

    /* renamed from: a */
    public static void m1349a(ak akVar, CharSequence charSequence, boolean z, CharSequence charSequence2, ArrayList<CharSequence> arrayList) {
        InboxStyle bigContentTitle = new InboxStyle(akVar.mo227a()).setBigContentTitle(charSequence);
        if (z) {
            bigContentTitle.setSummaryText(charSequence2);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            bigContentTitle.addLine((CharSequence) it.next());
        }
    }
}
