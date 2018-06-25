package com.persianswitch.sdk.base.style;

import android.support.annotation.ColorRes;

public interface ColorPaletteConfig {
    @ColorRes
    int getAmountHighlightColorRes();

    @ColorRes
    int getFailedColor();

    @ColorRes
    int getReportAmountHighlightColorRes();

    @ColorRes
    int getSuccessColor();

    @ColorRes
    int getUnknownColor();
}
