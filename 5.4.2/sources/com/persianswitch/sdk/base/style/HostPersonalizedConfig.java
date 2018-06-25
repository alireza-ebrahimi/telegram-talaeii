package com.persianswitch.sdk.base.style;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.C0235a;
import android.support.v4.p007b.p008a.C0375a;
import com.persianswitch.sdk.C2262R;

public class HostPersonalizedConfig implements PersonalizedConfig {
    /* renamed from: a */
    private final int f7101a;

    public HostPersonalizedConfig(int i) {
        this.f7101a = i;
    }

    /* renamed from: a */
    public int mo3267a() {
        switch (this.f7101a) {
            case 1108:
                return C2262R.style.asanpardakht_SDKTheme_setare_aval;
            default:
                return C2262R.style.asanpardakht_SDKTheme;
        }
    }

    /* renamed from: a */
    public Drawable mo3268a(Context context, int i) {
        if (this.f7101a == 1108) {
            return C0235a.m1066a(context, C2262R.drawable.asanpardakht_amount_bg_setare_aval);
        }
        Drawable g = C0375a.m1784g(C0235a.m1066a(context, C2262R.drawable.asanpardakht_report_status_bg));
        C0375a.m1771a(g, i);
        return g;
    }

    /* renamed from: b */
    public int mo3269b() {
        switch (this.f7101a) {
            case 1108:
                return C2262R.layout.asanpardakht_fragment_payment_setare_aval;
            default:
                return C2262R.layout.asanpardakht_fragment_payment;
        }
    }

    /* renamed from: b */
    public Drawable mo3270b(Context context, int i) {
        if (this.f7101a == 1108) {
            return new ColorDrawable(C0235a.m1075c(context, C2262R.color.asanpardakht_textHighlightColor_setare_aval));
        }
        GradientDrawable gradientDrawable = (GradientDrawable) C0235a.m1066a(context, C2262R.drawable.asanpardakht_report_content_bg);
        gradientDrawable.setStroke(2, i);
        return gradientDrawable;
    }

    /* renamed from: c */
    public int mo3271c() {
        switch (this.f7101a) {
            case 1108:
                return C2262R.layout.asanpardakht_fragment_report_setare_aval;
            default:
                return C2262R.layout.asanpardakht_fragment_report;
        }
    }

    /* renamed from: d */
    public boolean mo3272d() {
        switch (this.f7101a) {
            case 1108:
                return false;
            default:
                return true;
        }
    }

    /* renamed from: e */
    public ColorPaletteConfig mo3273e() {
        return new HostColorPaletteConfig(this.f7101a);
    }
}
