package org.telegram.customization.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import java.util.Calendar;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import utils.C3792d;
import utils.p178a.C3791b;

public class UStateService extends C2827a implements C2497d {
    /* renamed from: a */
    public static Calendar m13196a() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 2);
        instance.set(12, C3792d.m14090c());
        instance.set(13, 0);
        return instance;
    }

    /* renamed from: b */
    public static void m13197b(Context context) {
        C2827a.m13164a(context, new Intent(context, UStateService.class), m13196a(), C3791b.m13906J(context));
    }

    /* renamed from: b */
    public boolean m13198b() {
        return C3791b.m13907K(getApplicationContext()) + C3791b.m13906J(getApplicationContext()) < System.currentTimeMillis();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case 37:
                C3791b.m14038r(getApplicationContext(), System.currentTimeMillis());
                break;
        }
        stopSelf();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        boolean z = false;
        if (C3791b.m13999i()) {
            if (intent != null) {
                z = intent.getBooleanExtra("EXTRA_IS_FORCE", false);
            }
            if (!C3791b.m13938a((Context) this)) {
                stopSelf();
                return super.onStartCommand(intent, i, i2);
            } else if (z || m13198b()) {
                C2818c.m13087a(getApplicationContext(), (C2497d) this).m13136g();
                return 1;
            } else {
                stopSelf();
                return super.onStartCommand(intent, i, i2);
            }
        }
        stopSelf();
        return super.onStartCommand(intent, i, i2);
    }
}
