package android.support.v4.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.widget.CompoundButton;

@TargetApi(21)
/* renamed from: android.support.v4.widget.f */
class C0690f {
    /* renamed from: a */
    static void m3375a(CompoundButton compoundButton, ColorStateList colorStateList) {
        compoundButton.setButtonTintList(colorStateList);
    }

    /* renamed from: a */
    static void m3376a(CompoundButton compoundButton, Mode mode) {
        compoundButton.setButtonTintMode(mode);
    }
}
