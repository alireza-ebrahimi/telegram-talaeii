package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.MotionEvent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.v4.view.t */
public final class C0659t {
    /* renamed from: a */
    static final C0655d f1397a;

    /* renamed from: android.support.v4.view.t$d */
    interface C0655d {
        /* renamed from: a */
        float mo566a(MotionEvent motionEvent, int i);
    }

    /* renamed from: android.support.v4.view.t$a */
    static class C0656a implements C0655d {
        C0656a() {
        }

        /* renamed from: a */
        public float mo566a(MotionEvent motionEvent, int i) {
            return BitmapDescriptorFactory.HUE_RED;
        }
    }

    /* renamed from: android.support.v4.view.t$b */
    static class C0657b extends C0656a {
        C0657b() {
        }

        /* renamed from: a */
        public float mo566a(MotionEvent motionEvent, int i) {
            return C0660u.m3207a(motionEvent, i);
        }
    }

    /* renamed from: android.support.v4.view.t$c */
    private static class C0658c extends C0657b {
        C0658c() {
        }
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            f1397a = new C0658c();
        } else if (VERSION.SDK_INT >= 12) {
            f1397a = new C0657b();
        } else {
            f1397a = new C0656a();
        }
    }

    /* renamed from: a */
    public static float m3204a(MotionEvent motionEvent, int i) {
        return f1397a.mo566a(motionEvent, i);
    }

    /* renamed from: a */
    public static int m3205a(MotionEvent motionEvent) {
        return motionEvent.getAction() & 255;
    }

    /* renamed from: b */
    public static int m3206b(MotionEvent motionEvent) {
        return (motionEvent.getAction() & 65280) >> 8;
    }
}
