package com.persianswitch.sdk.base.style;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;

public interface PersonalizedConfig {
    ColorPaletteConfig getColorConfig();

    @LayoutRes
    int getPaymentLayout();

    @LayoutRes
    int getReportLayout();

    @StyleRes
    int getTheme();

    boolean needLoadLogo();

    Drawable prepareReportBG(Context context, int i);

    Drawable prepareReportStatusBG(Context context, int i);
}
