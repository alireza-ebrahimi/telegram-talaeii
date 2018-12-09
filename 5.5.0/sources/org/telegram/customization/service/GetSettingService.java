package org.telegram.customization.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;

public class GetSettingService extends Service {

    /* renamed from: org.telegram.customization.service.GetSettingService$1 */
    class C28351 implements C2497d {
        /* renamed from: a */
        final /* synthetic */ GetSettingService f9313a;

        C28351(GetSettingService getSettingService) {
            this.f9313a = getSettingService;
        }

        public void onResult(Object obj, int i) {
            this.f9313a.stopSelf();
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        C2818c.m13087a(getApplicationContext(), new C28351(this)).m13130d();
        return 2;
    }
}
