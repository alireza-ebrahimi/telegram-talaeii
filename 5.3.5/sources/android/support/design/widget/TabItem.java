package android.support.design.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.C0016R;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;

public final class TabItem extends View {
    final int mCustomLayout;
    final Drawable mIcon;
    final CharSequence mText;

    public TabItem(Context context) {
        this(context, null);
    }

    public TabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, C0016R.styleable.TabItem);
        this.mText = a.getText(C0016R.styleable.TabItem_android_text);
        this.mIcon = a.getDrawable(C0016R.styleable.TabItem_android_icon);
        this.mCustomLayout = a.getResourceId(C0016R.styleable.TabItem_android_layout, 0);
        a.recycle();
    }
}
