package org.telegram.ui.ActionBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import org.telegram.messenger.AndroidUtilities;

public class SimpleTextView extends View implements Callback {
    private int drawablePadding = AndroidUtilities.dp(4.0f);
    private int gravity = 51;
    private Layout layout;
    private Drawable leftDrawable;
    private int leftDrawableTopPadding;
    private int offsetX;
    private Drawable rightDrawable;
    private int rightDrawableTopPadding;
    private SpannableStringBuilder spannableStringBuilder;
    private CharSequence text;
    private int textHeight;
    private TextPaint textPaint = new TextPaint(1);
    private int textWidth;
    private boolean wasLayout;

    public SimpleTextView(Context context) {
        super(context);
    }

    public void setTextColor(int color) {
        this.textPaint.setColor(color);
        invalidate();
    }

    public void setLinkTextColor(int color) {
        this.textPaint.linkColor = color;
        invalidate();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.wasLayout = false;
    }

    public void setTextSize(int size) {
        int newSize = AndroidUtilities.dp((float) size);
        if (((float) newSize) != this.textPaint.getTextSize()) {
            this.textPaint.setTextSize((float) newSize);
            if (!recreateLayoutMaybe()) {
                invalidate();
            }
        }
    }

    public void setGravity(int value) {
        this.gravity = value;
    }

    public void setTypeface(Typeface typeface) {
        this.textPaint.setTypeface(AndroidUtilities.getTypeface(""));
    }

    public int getSideDrawablesSize() {
        int size = 0;
        if (this.leftDrawable != null) {
            size = 0 + (this.leftDrawable.getIntrinsicWidth() + this.drawablePadding);
        }
        if (this.rightDrawable != null) {
            return size + (this.rightDrawable.getIntrinsicWidth() + this.drawablePadding);
        }
        return size;
    }

    public Paint getPaint() {
        return this.textPaint;
    }

    private void calcOffset(int width) {
        if (this.layout.getLineCount() > 0) {
            this.textWidth = (int) Math.ceil((double) this.layout.getLineWidth(0));
            this.textHeight = this.layout.getLineBottom(0);
            if ((this.gravity & 7) == 3) {
                this.offsetX = -((int) this.layout.getLineLeft(0));
            } else if (this.layout.getLineLeft(0) == 0.0f) {
                this.offsetX = width - this.textWidth;
            } else {
                this.offsetX = -AndroidUtilities.dp(8.0f);
            }
            this.offsetX += getPaddingLeft();
        }
    }

    private boolean createLayout(int width) {
        if (this.text != null) {
            try {
                if (this.leftDrawable != null) {
                    width = (width - this.leftDrawable.getIntrinsicWidth()) - this.drawablePadding;
                }
                if (this.rightDrawable != null) {
                    width = (width - this.rightDrawable.getIntrinsicWidth()) - this.drawablePadding;
                }
                CharSequence string = TextUtils.ellipsize(this.text, this.textPaint, (float) width, TruncateAt.END);
                this.layout = new StaticLayout(string, 0, string.length(), this.textPaint, AndroidUtilities.dp(8.0f) + width, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                calcOffset(width);
            } catch (Exception e) {
            }
        } else {
            this.layout = null;
            this.textWidth = 0;
            this.textHeight = 0;
        }
        invalidate();
        return true;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int finalHeight;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        createLayout((width - getPaddingLeft()) - getPaddingRight());
        if (MeasureSpec.getMode(heightMeasureSpec) == 1073741824) {
            finalHeight = height;
        } else {
            finalHeight = this.textHeight;
        }
        setMeasuredDimension(width, finalHeight);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.wasLayout = true;
    }

    public int getTextWidth() {
        return this.textWidth;
    }

    public int getTextHeight() {
        return this.textHeight;
    }

    public void setLeftDrawableTopPadding(int value) {
        this.leftDrawableTopPadding = value;
    }

    public void setRightDrawableTopPadding(int value) {
        this.rightDrawableTopPadding = value;
    }

    public void setLeftDrawable(int resId) {
        setLeftDrawable(resId == 0 ? null : getContext().getResources().getDrawable(resId));
    }

    public void setRightDrawable(int resId) {
        setRightDrawable(resId == 0 ? null : getContext().getResources().getDrawable(resId));
    }

    public void setLeftDrawable(Drawable drawable) {
        if (this.leftDrawable != drawable) {
            if (this.leftDrawable != null) {
                this.leftDrawable.setCallback(null);
            }
            this.leftDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
            }
            if (!recreateLayoutMaybe()) {
                invalidate();
            }
        }
    }

    public void setRightDrawable(Drawable drawable) {
        if (this.rightDrawable != drawable) {
            if (this.rightDrawable != null) {
                this.rightDrawable.setCallback(null);
            }
            this.rightDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
            }
            if (!recreateLayoutMaybe()) {
                invalidate();
            }
        }
    }

    public void setText(CharSequence value) {
        setText(value, false);
    }

    public void setText(CharSequence value, boolean force) {
        if (this.text != null || value != null) {
            if (force || this.text == null || value == null || !this.text.equals(value)) {
                this.text = value;
                recreateLayoutMaybe();
            }
        }
    }

    public void setDrawablePadding(int value) {
        if (this.drawablePadding != value) {
            this.drawablePadding = value;
            if (!recreateLayoutMaybe()) {
                invalidate();
            }
        }
    }

    private boolean recreateLayoutMaybe() {
        if (this.wasLayout) {
            return createLayout(getMeasuredWidth());
        }
        requestLayout();
        return true;
    }

    public CharSequence getText() {
        if (this.text == null) {
            return "";
        }
        return this.text;
    }

    protected void onDraw(Canvas canvas) {
        int textOffsetX = 0;
        if (this.leftDrawable != null) {
            int y = ((this.textHeight - this.leftDrawable.getIntrinsicHeight()) / 2) + this.leftDrawableTopPadding;
            this.leftDrawable.setBounds(0, y, this.leftDrawable.getIntrinsicWidth(), this.leftDrawable.getIntrinsicHeight() + y);
            this.leftDrawable.draw(canvas);
            if ((this.gravity & 7) == 3) {
                textOffsetX = 0 + (this.drawablePadding + this.leftDrawable.getIntrinsicWidth());
            }
        }
        if (this.rightDrawable != null) {
            int x = (this.textWidth + textOffsetX) + this.drawablePadding;
            y = ((this.textHeight - this.rightDrawable.getIntrinsicHeight()) / 2) + this.rightDrawableTopPadding;
            this.rightDrawable.setBounds(x, y, this.rightDrawable.getIntrinsicWidth() + x, this.rightDrawable.getIntrinsicHeight() + y);
            this.rightDrawable.draw(canvas);
        }
        if (this.layout != null) {
            if (this.offsetX + textOffsetX != 0) {
                canvas.save();
                canvas.translate((float) (this.offsetX + textOffsetX), 0.0f);
            }
            this.layout.draw(canvas);
            if (this.offsetX + textOffsetX != 0) {
                canvas.restore();
            }
        }
    }

    public void invalidateDrawable(Drawable who) {
        if (who == this.leftDrawable) {
            invalidate(this.leftDrawable.getBounds());
        } else if (who == this.rightDrawable) {
            invalidate(this.rightDrawable.getBounds());
        }
    }

    public boolean hasOverlappingRendering() {
        return false;
    }
}
