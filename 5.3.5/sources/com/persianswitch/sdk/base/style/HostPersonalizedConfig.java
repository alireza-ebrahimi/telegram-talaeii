package com.persianswitch.sdk.base.style;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import com.persianswitch.sdk.BuildConfig;
import com.persianswitch.sdk.C0770R;

public class HostPersonalizedConfig implements PersonalizedConfig {
    private final int hostId;

    public HostPersonalizedConfig(int hostId) {
        this.hostId = hostId;
    }

    public int getTheme() {
        switch (this.hostId) {
            case BuildConfig.SETARE_AVAL_HOST_ID /*1108*/:
                return C0770R.style.asanpardakht_SDKTheme_setare_aval;
            default:
                return C0770R.style.asanpardakht_SDKTheme;
        }
    }

    public int getPaymentLayout() {
        switch (this.hostId) {
            case BuildConfig.SETARE_AVAL_HOST_ID /*1108*/:
                return C0770R.layout.asanpardakht_fragment_payment_setare_aval;
            default:
                return C0770R.layout.asanpardakht_fragment_payment;
        }
    }

    public int getReportLayout() {
        switch (this.hostId) {
            case BuildConfig.SETARE_AVAL_HOST_ID /*1108*/:
                return C0770R.layout.asanpardakht_fragment_report_setare_aval;
            default:
                return C0770R.layout.asanpardakht_fragment_report;
        }
    }

    public boolean needLoadLogo() {
        switch (this.hostId) {
            case BuildConfig.SETARE_AVAL_HOST_ID /*1108*/:
                return false;
            default:
                return true;
        }
    }

    public Drawable prepareReportStatusBG(Context context, int statusColor) {
        if (this.hostId == BuildConfig.SETARE_AVAL_HOST_ID) {
            return ContextCompat.getDrawable(context, C0770R.drawable.asanpardakht_amount_bg_setare_aval);
        }
        Drawable drawableStatus = DrawableCompat.wrap(ContextCompat.getDrawable(context, C0770R.drawable.asanpardakht_report_status_bg));
        DrawableCompat.setTint(drawableStatus, statusColor);
        return drawableStatus;
    }

    public Drawable prepareReportBG(Context context, int statusColor) {
        if (this.hostId == BuildConfig.SETARE_AVAL_HOST_ID) {
            return new ColorDrawable(ContextCompat.getColor(context, C0770R.color.asanpardakht_textHighlightColor_setare_aval));
        }
        GradientDrawable defaultDrawable = (GradientDrawable) ContextCompat.getDrawable(context, C0770R.drawable.asanpardakht_report_content_bg);
        defaultDrawable.setStroke(2, statusColor);
        return defaultDrawable;
    }

    public ColorPaletteConfig getColorConfig() {
        return new HostColorPaletteConfig(this.hostId);
    }
}
