package utils.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class FarsiEditText extends EditText {
    /* renamed from: a */
    private static Typeface f10273a;

    public FarsiEditText(Context context) {
        super(context);
        m14169a();
    }

    public FarsiEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m14169a();
    }

    public FarsiEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m14169a();
    }

    /* renamed from: a */
    public static Typeface m14168a(Context context) {
        if (f10273a == null) {
            f10273a = Typeface.createFromAsset(context.getAssets(), FontUtil.m14172a());
        }
        return f10273a;
    }

    /* renamed from: a */
    private void m14169a() {
        if (!isInEditMode()) {
            setTypeface(m14168a(getContext()));
            setGravity(5);
        }
    }
}
