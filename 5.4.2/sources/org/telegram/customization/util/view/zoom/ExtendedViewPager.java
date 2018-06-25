package org.telegram.customization.util.view.zoom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class ExtendedViewPager extends ViewPager {
    public ExtendedViewPager(Context context) {
        super(context);
    }

    public ExtendedViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        return view instanceof TouchImageView ? ((TouchImageView) view).m13723a(-i) : super.canScroll(view, z, i, i2, i3);
    }
}
