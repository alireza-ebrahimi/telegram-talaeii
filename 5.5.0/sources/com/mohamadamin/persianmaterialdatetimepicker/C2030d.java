package com.mohamadamin.persianmaterialdatetimepicker;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2020d;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.d */
public class C2030d {
    /* renamed from: a */
    public static int m9114a(int i, int i2) {
        return i < 6 ? 31 : (i < 11 || C2020d.m9105a(i2)) ? 30 : 29;
    }

    /* renamed from: a */
    public static ObjectAnimator m9115a(View view, float f, float f2) {
        Keyframe ofFloat = Keyframe.ofFloat(BitmapDescriptorFactory.HUE_RED, 1.0f);
        Keyframe ofFloat2 = Keyframe.ofFloat(0.275f, f);
        Keyframe ofFloat3 = Keyframe.ofFloat(0.69f, f2);
        Keyframe ofFloat4 = Keyframe.ofFloat(1.0f, 1.0f);
        PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe("scaleX", new Keyframe[]{ofFloat, ofFloat2, ofFloat3, ofFloat4});
        PropertyValuesHolder ofKeyframe2 = PropertyValuesHolder.ofKeyframe("scaleY", new Keyframe[]{ofFloat, ofFloat2, ofFloat3, ofFloat4});
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, new PropertyValuesHolder[]{ofKeyframe, ofKeyframe2});
        ofPropertyValuesHolder.setDuration(544);
        return ofPropertyValuesHolder;
    }

    @SuppressLint({"NewApi"})
    /* renamed from: a */
    public static void m9116a(View view, CharSequence charSequence) {
        if (C2030d.m9117a() && view != null && charSequence != null) {
            view.announceForAccessibility(charSequence);
        }
    }

    /* renamed from: a */
    public static boolean m9117a() {
        return VERSION.SDK_INT >= 16;
    }
}
