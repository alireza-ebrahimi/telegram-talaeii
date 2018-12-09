package utils.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class FarsiButton extends Button {
    /* renamed from: a */
    private static Typeface f10269a;

    public FarsiButton(Context context) {
        super(context);
        m14164a();
    }

    public FarsiButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m14164a();
    }

    public FarsiButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m14164a();
    }

    /* renamed from: a */
    public static Typeface m14163a(Context context) {
        if (f10269a == null) {
            f10269a = Typeface.createFromAsset(context.getAssets(), FontUtil.m14172a());
        }
        return f10269a;
    }

    /* renamed from: a */
    private void m14164a() {
        if (!isInEditMode()) {
            setTypeface(m14163a(getContext()));
            setTextSize(0, getTextSize() * 1.2f);
        }
    }

    public boolean isFocused() {
        return true;
    }

    protected void onFocusChanged(boolean z, int i, Rect rect) {
        if (z) {
            super.onFocusChanged(z, 66, rect);
        }
    }

    public void onWindowFocusChanged(boolean z) {
        if (z) {
            super.onWindowFocusChanged(z);
        }
    }
}
