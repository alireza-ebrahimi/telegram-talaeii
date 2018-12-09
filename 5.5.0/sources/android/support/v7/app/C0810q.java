package android.support.v7.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.C0428o;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.util.Calendar;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkedTrackBlacklistUtil;

/* renamed from: android.support.v7.app.q */
class C0810q {
    /* renamed from: a */
    private static C0810q f1886a;
    /* renamed from: b */
    private final Context f1887b;
    /* renamed from: c */
    private final LocationManager f1888c;
    /* renamed from: d */
    private final C0809a f1889d = new C0809a();

    /* renamed from: android.support.v7.app.q$a */
    private static class C0809a {
        /* renamed from: a */
        boolean f1880a;
        /* renamed from: b */
        long f1881b;
        /* renamed from: c */
        long f1882c;
        /* renamed from: d */
        long f1883d;
        /* renamed from: e */
        long f1884e;
        /* renamed from: f */
        long f1885f;

        C0809a() {
        }
    }

    C0810q(Context context, LocationManager locationManager) {
        this.f1887b = context;
        this.f1888c = locationManager;
    }

    /* renamed from: a */
    private Location m3847a(String str) {
        if (this.f1888c != null) {
            try {
                if (this.f1888c.isProviderEnabled(str)) {
                    return this.f1888c.getLastKnownLocation(str);
                }
            } catch (Throwable e) {
                Log.d("TwilightManager", "Failed to get last known location", e);
            }
        }
        return null;
    }

    /* renamed from: a */
    static C0810q m3848a(Context context) {
        if (f1886a == null) {
            Context applicationContext = context.getApplicationContext();
            f1886a = new C0810q(applicationContext, (LocationManager) applicationContext.getSystemService(C1797b.LOCATION));
        }
        return f1886a;
    }

    /* renamed from: a */
    private void m3849a(Location location) {
        long j;
        C0809a c0809a = this.f1889d;
        long currentTimeMillis = System.currentTimeMillis();
        C0808p a = C0808p.m3845a();
        a.m3846a(currentTimeMillis - 86400000, location.getLatitude(), location.getLongitude());
        long j2 = a.f1877a;
        a.m3846a(currentTimeMillis, location.getLatitude(), location.getLongitude());
        boolean z = a.f1879c == 1;
        long j3 = a.f1878b;
        long j4 = a.f1877a;
        a.m3846a(86400000 + currentTimeMillis, location.getLatitude(), location.getLongitude());
        long j5 = a.f1878b;
        if (j3 == -1 || j4 == -1) {
            j = 43200000 + currentTimeMillis;
        } else {
            j = currentTimeMillis > j4 ? 0 + j5 : currentTimeMillis > j3 ? 0 + j4 : 0 + j3;
            j += ChunkedTrackBlacklistUtil.DEFAULT_TRACK_BLACKLIST_MS;
        }
        c0809a.f1880a = z;
        c0809a.f1881b = j2;
        c0809a.f1882c = j3;
        c0809a.f1883d = j4;
        c0809a.f1884e = j5;
        c0809a.f1885f = j;
    }

    /* renamed from: b */
    private Location m3850b() {
        Location location = null;
        Location a = C0428o.m1906a(this.f1887b, "android.permission.ACCESS_COARSE_LOCATION") == 0 ? m3847a("network") : null;
        if (C0428o.m1906a(this.f1887b, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            location = m3847a("gps");
        }
        if (location != null && a != null) {
            return location.getTime() > a.getTime() ? location : a;
        } else {
            if (location == null) {
                location = a;
            }
            return location;
        }
    }

    /* renamed from: c */
    private boolean m3851c() {
        return this.f1889d != null && this.f1889d.f1885f > System.currentTimeMillis();
    }

    /* renamed from: a */
    boolean m3852a() {
        C0809a c0809a = this.f1889d;
        if (m3851c()) {
            return c0809a.f1880a;
        }
        Location b = m3850b();
        if (b != null) {
            m3849a(b);
            return c0809a.f1880a;
        }
        Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
        int i = Calendar.getInstance().get(11);
        return i < 6 || i >= 22;
    }
}
