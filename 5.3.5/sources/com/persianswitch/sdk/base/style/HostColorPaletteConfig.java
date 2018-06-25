package com.persianswitch.sdk.base.style;

import com.persianswitch.sdk.BuildConfig;
import com.persianswitch.sdk.C0770R;

public class HostColorPaletteConfig implements ColorPaletteConfig {
    private final int hostId;

    public HostColorPaletteConfig(int hostId) {
        this.hostId = hostId;
    }

    public int getAmountHighlightColorRes() {
        switch (this.hostId) {
            case BuildConfig.SETARE_AVAL_HOST_ID /*1108*/:
                return C0770R.color.asanpardakht_textHighlightColor_setare_aval;
            default:
                return C0770R.color.asanpardakht_textHighlightColor;
        }
    }

    public int getReportAmountHighlightColorRes() {
        switch (this.hostId) {
            case BuildConfig.SETARE_AVAL_HOST_ID /*1108*/:
                return C0770R.color.asanpardakht_SDKTheme_setare_aval_accent_color;
            default:
                return getAmountHighlightColorRes();
        }
    }

    public int getSuccessColor() {
        switch (this.hostId) {
            case BuildConfig.SETARE_AVAL_HOST_ID /*1108*/:
                return C0770R.color.asanpardakht_SDKTheme_setare_aval_accent_color;
            default:
                return C0770R.color.asanpardakht_colorSuccess;
        }
    }

    public int getUnknownColor() {
        switch (this.hostId) {
            case BuildConfig.SETARE_AVAL_HOST_ID /*1108*/:
                return C0770R.color.asanpardakht_SDKTheme_setare_aval_accent_color;
            default:
                return C0770R.color.asanpardakht_colorUnknown;
        }
    }

    public int getFailedColor() {
        switch (this.hostId) {
            case BuildConfig.SETARE_AVAL_HOST_ID /*1108*/:
                return C0770R.color.asanpardakht_SDKTheme_setare_aval_accent_color;
            default:
                return C0770R.color.asanpardakht_colorFail;
        }
    }
}
