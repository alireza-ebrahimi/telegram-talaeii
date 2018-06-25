package utils.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class FarsiEditText extends EditText {
    private static Typeface defaultTypeface;

    public FarsiEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FarsiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FarsiEditText(Context context) {
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
            setGravity(5);
        }
    }
}
