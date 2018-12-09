package android.support.v4.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings.Secure;
import android.support.v4.app.ad.C0250a;
import android.support.v4.p014d.C0432c;
import android.util.Log;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class au {
    /* renamed from: a */
    static final int f986a = f992i.mo231a();
    /* renamed from: b */
    private static final Object f987b = new Object();
    /* renamed from: c */
    private static String f988c;
    /* renamed from: d */
    private static Set<String> f989d = new HashSet();
    /* renamed from: g */
    private static final Object f990g = new Object();
    /* renamed from: h */
    private static C0304i f991h;
    /* renamed from: i */
    private static final C0296b f992i;
    /* renamed from: e */
    private final Context f993e;
    /* renamed from: f */
    private final NotificationManager f994f = ((NotificationManager) this.f993e.getSystemService("notification"));

    /* renamed from: android.support.v4.app.au$j */
    private interface C0294j {
        /* renamed from: a */
        void mo230a(ad adVar);
    }

    /* renamed from: android.support.v4.app.au$a */
    private static class C0295a implements C0294j {
        /* renamed from: a */
        final String f966a;
        /* renamed from: b */
        final int f967b;
        /* renamed from: c */
        final String f968c;
        /* renamed from: d */
        final boolean f969d = false;

        public C0295a(String str, int i, String str2) {
            this.f966a = str;
            this.f967b = i;
            this.f968c = str2;
        }

        /* renamed from: a */
        public void mo230a(ad adVar) {
            if (this.f969d) {
                adVar.mo201a(this.f966a);
            } else {
                adVar.mo202a(this.f966a, this.f967b, this.f968c);
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("CancelTask[");
            stringBuilder.append("packageName:").append(this.f966a);
            stringBuilder.append(", id:").append(this.f967b);
            stringBuilder.append(", tag:").append(this.f968c);
            stringBuilder.append(", all:").append(this.f969d);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    /* renamed from: android.support.v4.app.au$b */
    interface C0296b {
        /* renamed from: a */
        int mo231a();

        /* renamed from: a */
        void mo232a(NotificationManager notificationManager, String str, int i);

        /* renamed from: a */
        void mo233a(NotificationManager notificationManager, String str, int i, Notification notification);

        /* renamed from: a */
        boolean mo234a(Context context, NotificationManager notificationManager);
    }

    /* renamed from: android.support.v4.app.au$d */
    static class C0297d implements C0296b {
        C0297d() {
        }

        /* renamed from: a */
        public int mo231a() {
            return 1;
        }

        /* renamed from: a */
        public void mo232a(NotificationManager notificationManager, String str, int i) {
            notificationManager.cancel(str, i);
        }

        /* renamed from: a */
        public void mo233a(NotificationManager notificationManager, String str, int i, Notification notification) {
            notificationManager.notify(str, i, notification);
        }

        /* renamed from: a */
        public boolean mo234a(Context context, NotificationManager notificationManager) {
            return true;
        }
    }

    /* renamed from: android.support.v4.app.au$e */
    static class C0298e extends C0297d {
        C0298e() {
        }

        /* renamed from: a */
        public int mo231a() {
            return 33;
        }
    }

    /* renamed from: android.support.v4.app.au$f */
    static class C0299f extends C0298e {
        C0299f() {
        }

        /* renamed from: a */
        public boolean mo234a(Context context, NotificationManager notificationManager) {
            return aw.m1388a(context);
        }
    }

    /* renamed from: android.support.v4.app.au$c */
    static class C0300c extends C0299f {
        C0300c() {
        }

        /* renamed from: a */
        public boolean mo234a(Context context, NotificationManager notificationManager) {
            return av.m1387a(notificationManager);
        }
    }

    /* renamed from: android.support.v4.app.au$g */
    private static class C0301g implements C0294j {
        /* renamed from: a */
        final String f970a;
        /* renamed from: b */
        final int f971b;
        /* renamed from: c */
        final String f972c;
        /* renamed from: d */
        final Notification f973d;

        public C0301g(String str, int i, String str2, Notification notification) {
            this.f970a = str;
            this.f971b = i;
            this.f972c = str2;
            this.f973d = notification;
        }

        /* renamed from: a */
        public void mo230a(ad adVar) {
            adVar.mo203a(this.f970a, this.f971b, this.f972c, this.f973d);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("NotifyTask[");
            stringBuilder.append("packageName:").append(this.f970a);
            stringBuilder.append(", id:").append(this.f971b);
            stringBuilder.append(", tag:").append(this.f972c);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    /* renamed from: android.support.v4.app.au$h */
    private static class C0302h {
        /* renamed from: a */
        final ComponentName f974a;
        /* renamed from: b */
        final IBinder f975b;

        public C0302h(ComponentName componentName, IBinder iBinder) {
            this.f974a = componentName;
            this.f975b = iBinder;
        }
    }

    /* renamed from: android.support.v4.app.au$i */
    private static class C0304i implements ServiceConnection, Callback {
        /* renamed from: a */
        private final Context f981a;
        /* renamed from: b */
        private final HandlerThread f982b;
        /* renamed from: c */
        private final Handler f983c;
        /* renamed from: d */
        private final Map<ComponentName, C0303a> f984d = new HashMap();
        /* renamed from: e */
        private Set<String> f985e = new HashSet();

        /* renamed from: android.support.v4.app.au$i$a */
        private static class C0303a {
            /* renamed from: a */
            public final ComponentName f976a;
            /* renamed from: b */
            public boolean f977b = false;
            /* renamed from: c */
            public ad f978c;
            /* renamed from: d */
            public LinkedList<C0294j> f979d = new LinkedList();
            /* renamed from: e */
            public int f980e = 0;

            public C0303a(ComponentName componentName) {
                this.f976a = componentName;
            }
        }

        public C0304i(Context context) {
            this.f981a = context;
            this.f982b = new HandlerThread("NotificationManagerCompat");
            this.f982b.start();
            this.f983c = new Handler(this.f982b.getLooper(), this);
        }

        /* renamed from: a */
        private void m1368a() {
            Set b = au.m1381b(this.f981a);
            if (!b.equals(this.f985e)) {
                this.f985e = b;
                List<ResolveInfo> queryIntentServices = this.f981a.getPackageManager().queryIntentServices(new Intent().setAction("android.support.BIND_NOTIFICATION_SIDE_CHANNEL"), 4);
                Set<ComponentName> hashSet = new HashSet();
                for (ResolveInfo resolveInfo : queryIntentServices) {
                    if (b.contains(resolveInfo.serviceInfo.packageName)) {
                        ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
                        if (resolveInfo.serviceInfo.permission != null) {
                            Log.w("NotifManCompat", "Permission present on component " + componentName + ", not adding listener record.");
                        } else {
                            hashSet.add(componentName);
                        }
                    }
                }
                for (ComponentName componentName2 : hashSet) {
                    if (!this.f984d.containsKey(componentName2)) {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            Log.d("NotifManCompat", "Adding listener record for " + componentName2);
                        }
                        this.f984d.put(componentName2, new C0303a(componentName2));
                    }
                }
                Iterator it = this.f984d.entrySet().iterator();
                while (it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    if (!hashSet.contains(entry.getKey())) {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            Log.d("NotifManCompat", "Removing listener record for " + entry.getKey());
                        }
                        m1373b((C0303a) entry.getValue());
                        it.remove();
                    }
                }
            }
        }

        /* renamed from: a */
        private void m1369a(ComponentName componentName) {
            C0303a c0303a = (C0303a) this.f984d.get(componentName);
            if (c0303a != null) {
                m1373b(c0303a);
            }
        }

        /* renamed from: a */
        private void m1370a(ComponentName componentName, IBinder iBinder) {
            C0303a c0303a = (C0303a) this.f984d.get(componentName);
            if (c0303a != null) {
                c0303a.f978c = C0250a.m1152a(iBinder);
                c0303a.f980e = 0;
                m1376d(c0303a);
            }
        }

        /* renamed from: a */
        private boolean m1371a(C0303a c0303a) {
            if (c0303a.f977b) {
                return true;
            }
            c0303a.f977b = this.f981a.bindService(new Intent("android.support.BIND_NOTIFICATION_SIDE_CHANNEL").setComponent(c0303a.f976a), this, au.f986a);
            if (c0303a.f977b) {
                c0303a.f980e = 0;
            } else {
                Log.w("NotifManCompat", "Unable to bind to listener " + c0303a.f976a);
                this.f981a.unbindService(this);
            }
            return c0303a.f977b;
        }

        /* renamed from: b */
        private void m1372b(ComponentName componentName) {
            C0303a c0303a = (C0303a) this.f984d.get(componentName);
            if (c0303a != null) {
                m1376d(c0303a);
            }
        }

        /* renamed from: b */
        private void m1373b(C0303a c0303a) {
            if (c0303a.f977b) {
                this.f981a.unbindService(this);
                c0303a.f977b = false;
            }
            c0303a.f978c = null;
        }

        /* renamed from: b */
        private void m1374b(C0294j c0294j) {
            m1368a();
            for (C0303a c0303a : this.f984d.values()) {
                c0303a.f979d.add(c0294j);
                m1376d(c0303a);
            }
        }

        /* renamed from: c */
        private void m1375c(C0303a c0303a) {
            if (!this.f983c.hasMessages(3, c0303a.f976a)) {
                c0303a.f980e++;
                if (c0303a.f980e > 6) {
                    Log.w("NotifManCompat", "Giving up on delivering " + c0303a.f979d.size() + " tasks to " + c0303a.f976a + " after " + c0303a.f980e + " retries");
                    c0303a.f979d.clear();
                    return;
                }
                int i = (1 << (c0303a.f980e - 1)) * 1000;
                if (Log.isLoggable("NotifManCompat", 3)) {
                    Log.d("NotifManCompat", "Scheduling retry for " + i + " ms");
                }
                this.f983c.sendMessageDelayed(this.f983c.obtainMessage(3, c0303a.f976a), (long) i);
            }
        }

        /* renamed from: d */
        private void m1376d(C0303a c0303a) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                Log.d("NotifManCompat", "Processing component " + c0303a.f976a + ", " + c0303a.f979d.size() + " queued tasks");
            }
            if (!c0303a.f979d.isEmpty()) {
                if (!m1371a(c0303a) || c0303a.f978c == null) {
                    m1375c(c0303a);
                    return;
                }
                while (true) {
                    C0294j c0294j = (C0294j) c0303a.f979d.peek();
                    if (c0294j == null) {
                        break;
                    }
                    try {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            Log.d("NotifManCompat", "Sending task " + c0294j);
                        }
                        c0294j.mo230a(c0303a.f978c);
                        c0303a.f979d.remove();
                    } catch (DeadObjectException e) {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            Log.d("NotifManCompat", "Remote service has died: " + c0303a.f976a);
                        }
                    } catch (Throwable e2) {
                        Log.w("NotifManCompat", "RemoteException communicating with " + c0303a.f976a, e2);
                    }
                }
                if (!c0303a.f979d.isEmpty()) {
                    m1375c(c0303a);
                }
            }
        }

        /* renamed from: a */
        public void m1377a(C0294j c0294j) {
            this.f983c.obtainMessage(0, c0294j).sendToTarget();
        }

        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    m1374b((C0294j) message.obj);
                    return true;
                case 1:
                    C0302h c0302h = (C0302h) message.obj;
                    m1370a(c0302h.f974a, c0302h.f975b);
                    return true;
                case 2:
                    m1369a((ComponentName) message.obj);
                    return true;
                case 3:
                    m1372b((ComponentName) message.obj);
                    return true;
                default:
                    return false;
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                Log.d("NotifManCompat", "Connected to service " + componentName);
            }
            this.f983c.obtainMessage(1, new C0302h(componentName, iBinder)).sendToTarget();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                Log.d("NotifManCompat", "Disconnected from service " + componentName);
            }
            this.f983c.obtainMessage(2, componentName).sendToTarget();
        }
    }

    static {
        if (C0432c.m1912a()) {
            f992i = new C0300c();
        } else if (VERSION.SDK_INT >= 19) {
            f992i = new C0299f();
        } else if (VERSION.SDK_INT >= 14) {
            f992i = new C0298e();
        } else {
            f992i = new C0297d();
        }
    }

    private au(Context context) {
        this.f993e = context;
    }

    /* renamed from: a */
    public static au m1378a(Context context) {
        return new au(context);
    }

    /* renamed from: a */
    private void m1379a(C0294j c0294j) {
        synchronized (f990g) {
            if (f991h == null) {
                f991h = new C0304i(this.f993e.getApplicationContext());
            }
            f991h.m1377a(c0294j);
        }
    }

    /* renamed from: a */
    private static boolean m1380a(Notification notification) {
        Bundle a = al.m1316a(notification);
        return a != null && a.getBoolean("android.support.useSideChannel");
    }

    /* renamed from: b */
    public static Set<String> m1381b(Context context) {
        String string = Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        synchronized (f987b) {
            if (string != null) {
                if (!string.equals(f988c)) {
                    String[] split = string.split(":");
                    Set hashSet = new HashSet(split.length);
                    for (String unflattenFromString : split) {
                        ComponentName unflattenFromString2 = ComponentName.unflattenFromString(unflattenFromString);
                        if (unflattenFromString2 != null) {
                            hashSet.add(unflattenFromString2.getPackageName());
                        }
                    }
                    f989d = hashSet;
                    f988c = string;
                }
            }
        }
        return f989d;
    }

    /* renamed from: a */
    public void m1382a(int i) {
        m1384a(null, i);
    }

    /* renamed from: a */
    public void m1383a(int i, Notification notification) {
        m1385a(null, i, notification);
    }

    /* renamed from: a */
    public void m1384a(String str, int i) {
        f992i.mo232a(this.f994f, str, i);
        if (VERSION.SDK_INT <= 19) {
            m1379a(new C0295a(this.f993e.getPackageName(), i, str));
        }
    }

    /* renamed from: a */
    public void m1385a(String str, int i, Notification notification) {
        if (m1380a(notification)) {
            m1379a(new C0301g(this.f993e.getPackageName(), i, str, notification));
            f992i.mo232a(this.f994f, str, i);
            return;
        }
        f992i.mo233a(this.f994f, str, i, notification);
    }

    /* renamed from: a */
    public boolean m1386a() {
        return f992i.mo234a(this.f993e, this.f994f);
    }
}
