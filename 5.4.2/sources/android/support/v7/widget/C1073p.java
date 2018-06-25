package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.widget.C0724s;
import android.support.v7.p025a.C0748a.C0747j;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/* renamed from: android.support.v7.widget.p */
class C1073p extends PopupWindow {
    /* renamed from: a */
    private static final boolean f3187a = (VERSION.SDK_INT < 21);
    /* renamed from: b */
    private boolean f3188b;

    public C1073p(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m5891a(context, attributeSet, i, 0);
    }

    @TargetApi(11)
    public C1073p(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        m5891a(context, attributeSet, i, i2);
    }

    /* renamed from: a */
    private void m5891a(Context context, AttributeSet attributeSet, int i, int i2) {
        bk a = bk.m5654a(context, attributeSet, C0747j.PopupWindow, i, i2);
        if (a.m5671g(C0747j.PopupWindow_overlapAnchor)) {
            m5893a(a.m5659a(C0747j.PopupWindow_overlapAnchor, false));
        }
        setBackgroundDrawable(a.m5657a(C0747j.PopupWindow_android_popupBackground));
        int i3 = VERSION.SDK_INT;
        if (i2 != 0 && i3 < 11 && a.m5671g(C0747j.PopupWindow_android_popupAnimationStyle)) {
            setAnimationStyle(a.m5670g(C0747j.PopupWindow_android_popupAnimationStyle, -1));
        }
        a.m5658a();
        if (VERSION.SDK_INT < 14) {
            C1073p.m5892a((PopupWindow) this);
        }
    }

    /* renamed from: a */
    private static void m5892a(final PopupWindow popupWindow) {
        try {
            final Field declaredField = PopupWindow.class.getDeclaredField("mAnchor");
            declaredField.setAccessible(true);
            Field declaredField2 = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
            declaredField2.setAccessible(true);
            final OnScrollChangedListener onScrollChangedListener = (OnScrollChangedListener) declaredField2.get(popupWindow);
            declaredField2.set(popupWindow, new OnScrollChangedListener() {
                public void onScrollChanged() {
                    try {
                        WeakReference weakReference = (WeakReference) declaredField.get(popupWindow);
                        if (weakReference != null && weakReference.get() != null) {
                            onScrollChangedListener.onScrollChanged();
                        }
                    } catch (IllegalAccessException e) {
                    }
                }
            });
        } catch (Throwable e) {
            Log.d("AppCompatPopupWindow", "Exception while installing workaround OnScrollChangedListener", e);
        }
    }

    /* renamed from: a */
    public void m5893a(boolean z) {
        if (f3187a) {
            this.f3188b = z;
        } else {
            C0724s.m3534a((PopupWindow) this, z);
        }
    }

    public void showAsDropDown(View view, int i, int i2) {
        if (f3187a && this.f3188b) {
            i2 -= view.getHeight();
        }
        super.showAsDropDown(view, i, i2);
    }

    @TargetApi(19)
    public void showAsDropDown(View view, int i, int i2, int i3) {
        if (f3187a && this.f3188b) {
            i2 -= view.getHeight();
        }
        super.showAsDropDown(view, i, i2, i3);
    }

    public void update(View view, int i, int i2, int i3, int i4) {
        int height = (f3187a && this.f3188b) ? i2 - view.getHeight() : i2;
        super.update(view, i, height, i3, i4);
    }
}
