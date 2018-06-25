package utils.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class FarsiButton extends Button {
    private static Typeface defaultTypeface = null;
    public static final float scale = 1.2f;

    public FarsiButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FarsiButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FarsiButton(Context context) {
        super(context);
        init();
    }

    public static Typeface getTypeface(Context ctx) {
        if (defaultTypeface == null) {
            defaultTypeface = Typeface.createFromAsset(ctx.getAssets(), FontUtil.getDeafultFontAddress());
        }
        return defaultTypeface;
    }

    private void init() {
        if (!isInEditMode()) {
            setTypeface(getTypeface(getContext()));
            setTextSize(0, getTextSize() * 1.2f);
        }
    }

    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) {
            super.onFocusChanged(focused, 66, previouslyFocusedRect);
        }
    }

    public void onWindowFocusChanged(boolean focused) {
        if (focused) {
            super.onWindowFocusChanged(focused);
        }
    }

    public boolean isFocused() {
        return true;
    }
}
