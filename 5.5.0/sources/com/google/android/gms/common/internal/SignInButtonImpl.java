package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.p007b.p008a.C0375a;
import android.util.AttributeSet;
import android.widget.Button;
import com.google.android.gms.base.C1787R;
import com.google.android.gms.common.util.DeviceProperties;

public final class SignInButtonImpl extends Button {
    public SignInButtonImpl(Context context) {
        this(context, null);
    }

    public SignInButtonImpl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842824);
    }

    private static int zza(int i, int i2, int i3, int i4) {
        switch (i) {
            case 0:
                return i2;
            case 1:
                return i3;
            case 2:
                return i4;
            default:
                throw new IllegalStateException("Unknown color scheme: " + i);
        }
    }

    public final void configure(Resources resources, int i, int i2) {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        float f = resources.getDisplayMetrics().density;
        setMinHeight((int) ((f * 48.0f) + 0.5f));
        setMinWidth((int) ((f * 48.0f) + 0.5f));
        int zza = zza(i2, C1787R.drawable.common_google_signin_btn_icon_dark, C1787R.drawable.common_google_signin_btn_icon_light, C1787R.drawable.common_google_signin_btn_icon_light);
        int zza2 = zza(i2, C1787R.drawable.common_google_signin_btn_text_dark, C1787R.drawable.common_google_signin_btn_text_light, C1787R.drawable.common_google_signin_btn_text_light);
        switch (i) {
            case 0:
            case 1:
                break;
            case 2:
                zza2 = zza;
                break;
            default:
                throw new IllegalStateException("Unknown button size: " + i);
        }
        Drawable g = C0375a.m1784g(resources.getDrawable(zza2));
        C0375a.m1773a(g, resources.getColorStateList(C1787R.color.common_google_signin_btn_tint));
        C0375a.m1776a(g, Mode.SRC_ATOP);
        setBackgroundDrawable(g);
        setTextColor((ColorStateList) Preconditions.checkNotNull(resources.getColorStateList(zza(i2, C1787R.color.common_google_signin_btn_text_dark, C1787R.color.common_google_signin_btn_text_light, C1787R.color.common_google_signin_btn_text_light))));
        switch (i) {
            case 0:
                setText(resources.getString(C1787R.string.common_signin_button_text));
                break;
            case 1:
                setText(resources.getString(C1787R.string.common_signin_button_text_long));
                break;
            case 2:
                setText(null);
                break;
            default:
                throw new IllegalStateException("Unknown button size: " + i);
        }
        setTransformationMethod(null);
        if (DeviceProperties.isWearable(getContext())) {
            setGravity(19);
        }
    }

    public final void configure(Resources resources, SignInButtonConfig signInButtonConfig) {
        configure(resources, signInButtonConfig.getButtonSize(), signInButtonConfig.getColorScheme());
    }
}
