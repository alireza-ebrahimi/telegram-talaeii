package org.telegram.customization.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.google.p098a.C1768f;
import java.util.Calendar;
import org.telegram.customization.Model.CUrl;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import utils.p178a.C3791b;

public class CurlService extends C2827a implements C2497d {
    /* renamed from: a */
    public static Calendar m13177a() {
        return Calendar.getInstance();
    }

    /* renamed from: a */
    public static void m13178a(Context context, CUrl cUrl) {
        Intent intent = new Intent(context, CurlService.class);
        if (cUrl.isEnable()) {
            C2827a.m13164a(context, intent, m13177a(), cUrl.getPrd());
            intent.putExtra("EXTRA_IS_URL_DATA", new C1768f().m8395a((Object) cUrl));
            context.startService(intent);
            return;
        }
        C2827a.m13163a(context, intent);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onResult(Object obj, int i) {
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null) {
            String stringExtra = intent.getStringExtra("EXTRA_IS_URL_DATA");
            CUrl cUrl;
            if (TextUtils.isEmpty(stringExtra)) {
                cUrl = (CUrl) new C1768f().m8392a(C3791b.m14010l(), CUrl.class);
                if (cUrl != null) {
                    if (!cUrl.isEnable() || C3791b.m14006k() + cUrl.getPrd() >= System.currentTimeMillis()) {
                        stopSelf();
                    } else {
                        C2818c.m13087a(getApplicationContext(), (C2497d) this).m13148o(cUrl.getUrl());
                    }
                }
            } else {
                cUrl = (CUrl) new C1768f().m8392a(stringExtra, CUrl.class);
                if (cUrl != null) {
                    if (!cUrl.isEnable() || C3791b.m14006k() + cUrl.getPrd() >= System.currentTimeMillis()) {
                        stopSelf();
                    } else {
                        C2818c.m13087a(getApplicationContext(), (C2497d) this).m13148o(cUrl.getUrl());
                    }
                }
            }
        }
        return 1;
    }
}
