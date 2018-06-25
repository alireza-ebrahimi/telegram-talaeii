package org.telegram.customization.service;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.IBinder;
import com.google.p098a.C1768f;
import java.util.Calendar;
import org.telegram.customization.Model.LocationHelper;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.ui.LocationActivity;
import utils.C3792d;
import utils.p178a.C3791b;

public class LocationService extends C2827a implements C2497d {
    /* renamed from: a */
    public static Calendar m13184a() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 12);
        instance.set(12, C3792d.m14088c());
        instance.set(13, 0);
        return instance;
    }

    /* renamed from: b */
    public static void m13185b(Context context) {
        C2827a.m13164a(context, new Intent(context, LocationService.class), m13184a(), C3791b.m13903G(context));
    }

    /* renamed from: b */
    public boolean m13186b() {
        return C3791b.m13896C(getApplicationContext()) + C3791b.m13903G(getApplicationContext()) < System.currentTimeMillis();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case 15:
                C3791b.m14001j(getApplicationContext(), System.currentTimeMillis());
                break;
        }
        stopSelf();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        boolean z = false;
        if (VERSION.SDK_INT >= 23 && getApplicationContext().checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0) {
            stopSelf();
        }
        if (intent != null) {
            z = intent.getBooleanExtra("EXTRA_IS_FORCE", false);
        }
        if (!C3791b.m13938a((Context) this)) {
            stopSelf();
            return super.onStartCommand(intent, i, i2);
        } else if (z || m13186b()) {
            try {
                Location lastLocation = LocationActivity.getLastLocation();
                if (lastLocation != null) {
                    Object locationHelper = new LocationHelper();
                    locationHelper.setLatitude(lastLocation.getLatitude());
                    locationHelper.setLongitude(lastLocation.getLongitude());
                    C2818c.m13087a(getApplicationContext(), (C2497d) this).m13144k(new C1768f().m8395a(locationHelper));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        } else {
            stopSelf();
            return super.onStartCommand(intent, i, i2);
        }
    }
}
