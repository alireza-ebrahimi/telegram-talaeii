package com.crashlytics.android.p066c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.crashlytics.android.c.q */
class C1455q {
    /* renamed from: a */
    private static final IntentFilter f4390a = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    /* renamed from: b */
    private static final IntentFilter f4391b = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
    /* renamed from: c */
    private static final IntentFilter f4392c = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
    /* renamed from: d */
    private final AtomicBoolean f4393d;
    /* renamed from: e */
    private final Context f4394e;
    /* renamed from: f */
    private final BroadcastReceiver f4395f;
    /* renamed from: g */
    private final BroadcastReceiver f4396g;
    /* renamed from: h */
    private boolean f4397h;

    /* renamed from: com.crashlytics.android.c.q$1 */
    class C14531 extends BroadcastReceiver {
        /* renamed from: a */
        final /* synthetic */ C1455q f4388a;

        C14531(C1455q c1455q) {
            this.f4388a = c1455q;
        }

        public void onReceive(Context context, Intent intent) {
            this.f4388a.f4397h = true;
        }
    }

    /* renamed from: com.crashlytics.android.c.q$2 */
    class C14542 extends BroadcastReceiver {
        /* renamed from: a */
        final /* synthetic */ C1455q f4389a;

        C14542(C1455q c1455q) {
            this.f4389a = c1455q;
        }

        public void onReceive(Context context, Intent intent) {
            this.f4389a.f4397h = false;
        }
    }

    public C1455q(Context context) {
        int i = -1;
        this.f4394e = context;
        Intent registerReceiver = context.registerReceiver(null, f4390a);
        if (registerReceiver != null) {
            i = registerReceiver.getIntExtra("status", -1);
        }
        boolean z = i == 2 || i == 5;
        this.f4397h = z;
        this.f4396g = new C14531(this);
        this.f4395f = new C14542(this);
        context.registerReceiver(this.f4396g, f4391b);
        context.registerReceiver(this.f4395f, f4392c);
        this.f4393d = new AtomicBoolean(true);
    }

    /* renamed from: a */
    public boolean m7247a() {
        return this.f4397h;
    }

    /* renamed from: b */
    public void m7248b() {
        if (this.f4393d.getAndSet(false)) {
            this.f4394e.unregisterReceiver(this.f4396g);
            this.f4394e.unregisterReceiver(this.f4395f);
        }
    }
}
