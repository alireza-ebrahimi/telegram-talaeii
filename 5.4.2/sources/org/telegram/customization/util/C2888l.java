package org.telegram.customization.util;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager.C0494g;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: org.telegram.customization.util.l */
public class C2888l implements C0494g {
    @SuppressLint({"NewApi"})
    /* renamed from: a */
    public void mo3498a(View view, float f) {
        int width = view.getWidth();
        if (f < -1.0f) {
            view.setAlpha(BitmapDescriptorFactory.HUE_RED);
        } else if (f <= BitmapDescriptorFactory.HUE_RED) {
            view.setAlpha(1.0f);
            view.setTranslationX(BitmapDescriptorFactory.HUE_RED);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        } else if (f <= 1.0f) {
            view.setAlpha(1.0f - f);
            view.setTranslationX(((float) width) * (-f));
            view.setScaleY(0.75f + (0.25f * (1.0f - Math.abs(f))));
        } else {
            view.setAlpha(BitmapDescriptorFactory.HUE_RED);
        }
    }
}
