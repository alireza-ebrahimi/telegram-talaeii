package com.p077f.p078a.p086b.p087a;

import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/* renamed from: com.f.a.b.a.h */
public enum C1557h {
    FIT_INSIDE,
    CROP;

    /* renamed from: com.f.a.b.a.h$1 */
    static /* synthetic */ class C15561 {
        /* renamed from: a */
        static final /* synthetic */ int[] f4718a = null;

        static {
            f4718a = new int[ScaleType.values().length];
            try {
                f4718a[ScaleType.FIT_CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f4718a[ScaleType.FIT_XY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f4718a[ScaleType.FIT_START.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f4718a[ScaleType.FIT_END.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f4718a[ScaleType.CENTER_INSIDE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f4718a[ScaleType.MATRIX.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f4718a[ScaleType.CENTER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f4718a[ScaleType.CENTER_CROP.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    /* renamed from: a */
    public static C1557h m7677a(ImageView imageView) {
        switch (C15561.f4718a[imageView.getScaleType().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return FIT_INSIDE;
            default:
                return CROP;
        }
    }
}
