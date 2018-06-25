package utils.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TitleTextView extends TextView {
    /* renamed from: a */
    private static Typeface f10312a;

    public TitleTextView(Context context) {
        super(context);
        m14193a();
    }

    public TitleTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m14193a();
    }

    public TitleTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m14193a();
    }

    /* renamed from: a */
    public static Typeface m14192a(Context context) {
        if (f10312a == null) {
            f10312a = Typeface.createFromAsset(context.getAssets(), FontUtil.m14171b());
        }
        return f10312a;
    }

    /* renamed from: a */
    private void m14193a() {
        if (!isInEditMode()) {
            setTypeface(m14192a(getContext()));
            setTextSize(0, getTextSize() * 1.2f);
        }
    }
}
