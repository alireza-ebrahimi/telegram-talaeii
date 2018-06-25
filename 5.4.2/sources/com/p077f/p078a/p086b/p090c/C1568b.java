package com.p077f.p078a.p086b.p090c;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.p077f.p078a.p086b.p087a.C1554f;
import com.p077f.p078a.p086b.p092e.C1580a;

/* renamed from: com.f.a.b.c.b */
public class C1568b implements C1567a {
    /* renamed from: a */
    private final int f4764a;
    /* renamed from: b */
    private final boolean f4765b;
    /* renamed from: c */
    private final boolean f4766c;
    /* renamed from: d */
    private final boolean f4767d;

    /* renamed from: a */
    public static void m7743a(View view, int i) {
        if (view != null) {
            Animation alphaAnimation = new AlphaAnimation(BitmapDescriptorFactory.HUE_RED, 1.0f);
            alphaAnimation.setDuration((long) i);
            alphaAnimation.setInterpolator(new DecelerateInterpolator());
            view.startAnimation(alphaAnimation);
        }
    }

    /* renamed from: a */
    public void mo1226a(Bitmap bitmap, C1580a c1580a, C1554f c1554f) {
        c1580a.mo1229a(bitmap);
        if ((this.f4765b && c1554f == C1554f.NETWORK) || ((this.f4766c && c1554f == C1554f.DISC_CACHE) || (this.f4767d && c1554f == C1554f.MEMORY_CACHE))) {
            C1568b.m7743a(c1580a.mo1233d(), this.f4764a);
        }
    }
}
