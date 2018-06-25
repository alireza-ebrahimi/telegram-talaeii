package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.SystemClock;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;

public class EditTextBoldCursor extends EditText {
    private static Method getVerticalOffsetMethod;
    private static Field mCursorDrawableField;
    private static Field mCursorDrawableResField;
    private static Field mEditor;
    private static Field mScrollYField;
    private static Field mShowCursorField;
    private boolean allowDrawCursor = true;
    private int cursorSize;
    private float cursorWidth = 2.0f;
    private Object editor;
    private GradientDrawable gradientDrawable;
    private float hintAlpha = 1.0f;
    private int hintColor;
    private StaticLayout hintLayout;
    private boolean hintVisible = true;
    private int ignoreBottomCount;
    private int ignoreTopCount;
    private long lastUpdateTime;
    private float lineSpacingExtra;
    private Drawable[] mCursorDrawable;
    private Rect rect = new Rect();
    private int scrollY;

    public EditTextBoldCursor(Context context) {
        super(context);
        if (mCursorDrawableField == null) {
            try {
                mScrollYField = View.class.getDeclaredField("mScrollY");
                mScrollYField.setAccessible(true);
                mCursorDrawableResField = TextView.class.getDeclaredField("mCursorDrawableRes");
                mCursorDrawableResField.setAccessible(true);
                mEditor = TextView.class.getDeclaredField("mEditor");
                mEditor.setAccessible(true);
                Class cls = Class.forName("android.widget.Editor");
                mShowCursorField = cls.getDeclaredField("mShowCursor");
                mShowCursorField.setAccessible(true);
                mCursorDrawableField = cls.getDeclaredField("mCursorDrawable");
                mCursorDrawableField.setAccessible(true);
                getVerticalOffsetMethod = TextView.class.getDeclaredMethod("getVerticalOffset", new Class[]{Boolean.TYPE});
                getVerticalOffsetMethod.setAccessible(true);
            } catch (Throwable th) {
            }
        }
        try {
            this.gradientDrawable = new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{-11230757, -11230757});
            this.editor = mEditor.get(this);
            this.mCursorDrawable = (Drawable[]) mCursorDrawableField.get(this.editor);
            mCursorDrawableResField.set(this, Integer.valueOf(R.drawable.field_carret_empty));
        } catch (Throwable e) {
            FileLog.e(e);
        }
        this.cursorSize = AndroidUtilities.dp(24.0f);
    }

    public int getExtendedPaddingBottom() {
        if (this.ignoreBottomCount == 0) {
            return super.getExtendedPaddingBottom();
        }
        this.ignoreBottomCount--;
        return this.scrollY != Integer.MAX_VALUE ? -this.scrollY : 0;
    }

    public int getExtendedPaddingTop() {
        if (this.ignoreTopCount == 0) {
            return super.getExtendedPaddingTop();
        }
        this.ignoreTopCount--;
        return 0;
    }

    protected void onDraw(Canvas canvas) {
        int i = 0;
        int extendedPaddingTop = getExtendedPaddingTop();
        this.scrollY = Integer.MAX_VALUE;
        try {
            this.scrollY = mScrollYField.getInt(this);
            mScrollYField.set(this, Integer.valueOf(0));
        } catch (Exception e) {
        }
        this.ignoreTopCount = 1;
        this.ignoreBottomCount = 1;
        canvas.save();
        canvas.translate(BitmapDescriptorFactory.HUE_RED, (float) extendedPaddingTop);
        try {
            super.onDraw(canvas);
        } catch (Exception e2) {
        }
        if (this.scrollY != Integer.MAX_VALUE) {
            try {
                mScrollYField.set(this, Integer.valueOf(this.scrollY));
            } catch (Exception e3) {
            }
        }
        canvas.restore();
        if (length() == 0 && this.hintLayout != null && (this.hintVisible || this.hintAlpha != BitmapDescriptorFactory.HUE_RED)) {
            if ((this.hintVisible && this.hintAlpha != 1.0f) || !(this.hintVisible || this.hintAlpha == BitmapDescriptorFactory.HUE_RED)) {
                long currentTimeMillis = System.currentTimeMillis();
                long j = currentTimeMillis - this.lastUpdateTime;
                if (j < 0 || j > 17) {
                    j = 17;
                }
                this.lastUpdateTime = currentTimeMillis;
                if (this.hintVisible) {
                    this.hintAlpha = (((float) j) / 150.0f) + this.hintAlpha;
                    if (this.hintAlpha > 1.0f) {
                        this.hintAlpha = 1.0f;
                    }
                } else {
                    this.hintAlpha -= ((float) j) / 150.0f;
                    if (this.hintAlpha < BitmapDescriptorFactory.HUE_RED) {
                        this.hintAlpha = BitmapDescriptorFactory.HUE_RED;
                    }
                }
                invalidate();
            }
            int color = getPaint().getColor();
            getPaint().setColor(this.hintColor);
            getPaint().setAlpha((int) (255.0f * this.hintAlpha));
            canvas.save();
            float lineLeft = this.hintLayout.getLineLeft(0);
            canvas.translate((float) (lineLeft != BitmapDescriptorFactory.HUE_RED ? (int) (((float) null) - lineLeft) : 0), ((float) (getMeasuredHeight() - this.hintLayout.getHeight())) / 2.0f);
            this.hintLayout.draw(canvas);
            getPaint().setColor(color);
            canvas.restore();
        }
        try {
            if (this.allowDrawCursor && mShowCursorField != null && this.mCursorDrawable != null && this.mCursorDrawable[0] != null) {
                extendedPaddingTop = ((SystemClock.uptimeMillis() - mShowCursorField.getLong(this.editor)) % 1000 >= 500 || !isFocused()) ? 0 : 1;
                if (extendedPaddingTop != 0) {
                    canvas.save();
                    if ((getGravity() & 112) != 48) {
                        i = ((Integer) getVerticalOffsetMethod.invoke(this, new Object[]{Boolean.valueOf(true)})).intValue();
                    }
                    canvas.translate((float) getPaddingLeft(), (float) (getExtendedPaddingTop() + i));
                    Layout layout = getLayout();
                    color = layout.getLineForOffset(getSelectionStart());
                    extendedPaddingTop = layout.getLineCount();
                    Rect bounds = this.mCursorDrawable[0].getBounds();
                    this.rect.left = bounds.left;
                    this.rect.right = bounds.left + AndroidUtilities.dp(this.cursorWidth);
                    this.rect.bottom = bounds.bottom;
                    this.rect.top = bounds.top;
                    if (this.lineSpacingExtra != BitmapDescriptorFactory.HUE_RED && color < extendedPaddingTop - 1) {
                        Rect rect = this.rect;
                        rect.bottom = (int) (((float) rect.bottom) - this.lineSpacingExtra);
                    }
                    this.rect.top = this.rect.centerY() - (this.cursorSize / 2);
                    this.rect.bottom = this.rect.top + this.cursorSize;
                    this.gradientDrawable.setBounds(this.rect);
                    this.gradientDrawable.draw(canvas);
                    canvas.restore();
                }
            }
        } catch (Throwable th) {
        }
    }

    public void setAllowDrawCursor(boolean z) {
        this.allowDrawCursor = z;
    }

    public void setCursorColor(int i) {
        this.gradientDrawable.setColor(i);
        invalidate();
    }

    public void setCursorSize(int i) {
        this.cursorSize = i;
    }

    public void setCursorWidth(float f) {
        this.cursorWidth = f;
    }

    public void setHintColor(int i) {
        this.hintColor = i;
        invalidate();
    }

    public void setHintText(String str) {
        this.hintLayout = new StaticLayout(str, getPaint(), AndroidUtilities.dp(1000.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
    }

    public void setHintVisible(boolean z) {
        if (this.hintVisible != z) {
            this.lastUpdateTime = System.currentTimeMillis();
            this.hintVisible = z;
            invalidate();
        }
    }

    public void setLineSpacing(float f, float f2) {
        super.setLineSpacing(f, f2);
        this.lineSpacingExtra = f;
    }
}
