package com.mohamadamin.persianmaterialdatetimepicker;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.TypedValue;
import android.view.View;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendarUtils;

public class Utils {
    public static final int FULL_ALPHA = 255;
    public static final int PULSE_ANIMATOR_DURATION = 544;
    public static final int SELECTED_ALPHA = 255;
    public static final int SELECTED_ALPHA_THEME_DARK = 255;

    public static boolean isJellybeanOrLater() {
        return VERSION.SDK_INT >= 16;
    }

    @SuppressLint({"NewApi"})
    public static void tryAccessibilityAnnounce(View view, CharSequence text) {
        if (isJellybeanOrLater() && view != null && text != null) {
            view.announceForAccessibility(text);
        }
    }

    public static int getDaysInMonth(int month, int year) {
        if (month < 6) {
            return 31;
        }
        if (month < 11 || PersianCalendarUtils.isPersianLeapYear(year)) {
            return 30;
        }
        return 29;
    }

    public static ObjectAnimator getPulseAnimator(View labelToAnimate, float decreaseRatio, float increaseRatio) {
        Keyframe k0 = Keyframe.ofFloat(0.0f, 1.0f);
        Keyframe k1 = Keyframe.ofFloat(0.275f, decreaseRatio);
        Keyframe k2 = Keyframe.ofFloat(0.69f, increaseRatio);
        Keyframe k3 = Keyframe.ofFloat(1.0f, 1.0f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofKeyframe("scaleX", new Keyframe[]{k0, k1, k2, k3});
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofKeyframe("scaleY", new Keyframe[]{k0, k1, k2, k3});
        ObjectAnimator pulseAnimator = ObjectAnimator.ofPropertyValuesHolder(labelToAnimate, new PropertyValuesHolder[]{scaleX, scaleY});
        pulseAnimator.setDuration(544);
        return pulseAnimator;
    }

    public static int dpToPx(float dp, Resources resources) {
        return (int) TypedValue.applyDimension(1, dp, resources.getDisplayMetrics());
    }
}
