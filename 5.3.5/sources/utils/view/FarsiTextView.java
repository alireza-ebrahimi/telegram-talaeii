package utils.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class FarsiTextView extends AppCompatTextView {
    private static Typeface defaultTypeface = null;
    public static final float scale = 1.2f;

    public FarsiTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FarsiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FarsiTextView(Context context) {
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
}
