package com.persianswitch.sdk.base.style;

import com.persianswitch.sdk.C2262R;

public class HostColorPaletteConfig implements ColorPaletteConfig {
    /* renamed from: a */
    private final int f7100a;

    public HostColorPaletteConfig(int i) {
        this.f7100a = i;
    }

    /* renamed from: a */
    public int mo3262a() {
        switch (this.f7100a) {
            case 1108:
                return C2262R.color.asanpardakht_textHighlightColor_setare_aval;
            default:
                return C2262R.color.asanpardakht_textHighlightColor;
        }
    }

    /* renamed from: b */
    public int mo3263b() {
        switch (this.f7100a) {
            case 1108:
                return C2262R.color.asanpardakht_SDKTheme_setare_aval_accent_color;
            default:
                return mo3262a();
        }
    }

    /* renamed from: c */
    public int mo3264c() {
        switch (this.f7100a) {
            case 1108:
                return C2262R.color.asanpardakht_SDKTheme_setare_aval_accent_color;
            default:
                return C2262R.color.asanpardakht_colorSuccess;
        }
    }

    /* renamed from: d */
    public int mo3265d() {
        switch (this.f7100a) {
            case 1108:
                return C2262R.color.asanpardakht_SDKTheme_setare_aval_accent_color;
            default:
                return C2262R.color.asanpardakht_colorUnknown;
        }
    }

    /* renamed from: e */
    public int mo3266e() {
        switch (this.f7100a) {
            case 1108:
                return C2262R.color.asanpardakht_SDKTheme_setare_aval_accent_color;
            default:
                return C2262R.color.asanpardakht_colorFail;
        }
    }
}
