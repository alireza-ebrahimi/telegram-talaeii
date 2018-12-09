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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;

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

    private void calcOffset(int i) {
        if (this.layout.getLineCount() > 0) {
            this.textWidth = (int) Math.ceil((double) this.layout.getLineWidth(0));
            this.textHeight = this.layout.getLineBottom(0);
            if ((this.gravity & 7) == 3) {
                this.offsetX = -((int) this.layout.getLineLeft(0));
            } else if (this.layout.getLineLeft(0) == BitmapDescriptorFactory.HUE_RED) {
                this.offsetX = i - this.textWidth;
            } else {
                this.offsetX = -AndroidUtilities.dp(8.0f);
            }
            this.offsetX += getPaddingLeft();
        }
    }

    private boolean createLayout(int i) {
        if (this.text != null) {
            try {
                int intrinsicWidth = this.leftDrawable != null ? (i - this.leftDrawable.getIntrinsicWidth()) - this.drawablePadding : i;
                int intrinsicWidth2 = this.rightDrawable != null ? (intrinsicWidth - this.rightDrawable.getIntrinsicWidth()) - this.drawablePadding : intrinsicWidth;
                CharSequence ellipsize = TextUtils.ellipsize(this.text, this.textPaint, (float) intrinsicWidth2, TruncateAt.END);
                this.layout = new StaticLayout(ellipsize, 0, ellipsize.length(), this.textPaint, AndroidUtilities.dp(8.0f) + intrinsicWidth2, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                calcOffset(intrinsicWidth2);
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

    private boolean recreateLayoutMaybe() {
        if (this.wasLayout) {
            return createLayout(getMeasuredWidth());
        }
        requestLayout();
        return true;
    }

    public Paint getPaint() {
        return this.textPaint;
    }

    public int getSideDrawablesSize() {
        int i = 0;
        if (this.leftDrawable != null) {
            i = 0 + (this.leftDrawable.getIntrinsicWidth() + this.drawablePadding);
        }
        return this.rightDrawable != null ? i + (this.rightDrawable.getIntrinsicWidth() + this.drawablePadding) : i;
    }

    public CharSequence getText() {
        return this.text == null ? TtmlNode.ANONYMOUS_REGION_ID : this.text;
    }

    public int getTextHeight() {
        return this.textHeight;
    }

    public int getTextWidth() {
        return this.textWidth;
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public void invalidateDrawable(Drawable drawable) {
        if (drawable == this.leftDrawable) {
            invalidate(this.leftDrawable.getBounds());
        } else if (drawable == this.rightDrawable) {
            invalidate(this.rightDrawable.getBounds());
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.wasLayout = false;
    }

    protected void onDraw(Canvas canvas) {
        int i = 0;
        if (this.leftDrawable != null) {
            int intrinsicHeight = ((this.textHeight - this.leftDrawable.getIntrinsicHeight()) / 2) + this.leftDrawableTopPadding;
            this.leftDrawable.setBounds(0, intrinsicHeight, this.leftDrawable.getIntrinsicWidth(), this.leftDrawable.getIntrinsicHeight() + intrinsicHeight);
            this.leftDrawable.draw(canvas);
            if ((this.gravity & 7) == 3) {
                i = 0 + (this.drawablePadding + this.leftDrawable.getIntrinsicWidth());
            }
        }
        if (this.rightDrawable != null) {
            intrinsicHeight = (this.textWidth + i) + this.drawablePadding;
            int intrinsicHeight2 = ((this.textHeight - this.rightDrawable.getIntrinsicHeight()) / 2) + this.rightDrawableTopPadding;
            this.rightDrawable.setBounds(intrinsicHeight, intrinsicHeight2, this.rightDrawable.getIntrinsicWidth() + intrinsicHeight, this.rightDrawable.getIntrinsicHeight() + intrinsicHeight2);
            this.rightDrawable.draw(canvas);
        }
        if (this.layout != null) {
            if (this.offsetX + i != 0) {
                canvas.save();
                canvas.translate((float) (this.offsetX + i), BitmapDescriptorFactory.HUE_RED);
            }
            this.layout.draw(canvas);
            if (i + this.offsetX != 0) {
                canvas.restore();
            }
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.wasLayout = true;
    }

    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        createLayout((size - getPaddingLeft()) - getPaddingRight());
        if (MeasureSpec.getMode(i2) != 1073741824) {
            size2 = this.textHeight;
        }
        setMeasuredDimension(size, size2);
    }

    public void setDrawablePadding(int i) {
        if (this.drawablePadding != i) {
            this.drawablePadding = i;
            if (!recreateLayoutMaybe()) {
                invalidate();
            }
        }
    }

    public void setGravity(int i) {
        this.gravity = i;
    }

    public void setLeftDrawable(int i) {
        setLeftDrawable(i == 0 ? null : getContext().getResources().getDrawable(i));
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

    public void setLeftDrawableTopPadding(int i) {
        this.leftDrawableTopPadding = i;
    }

    public void setLinkTextColor(int i) {
        this.textPaint.linkColor = i;
        invalidate();
    }

    public void setRightDrawable(int i) {
        setRightDrawable(i == 0 ? null : getContext().getResources().getDrawable(i));
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

    public void setRightDrawableTopPadding(int i) {
        this.rightDrawableTopPadding = i;
    }

    public void setText(CharSequence charSequence) {
        setText(charSequence, false);
    }

    public void setText(CharSequence charSequence, boolean z) {
        if (this.text != null || charSequence != null) {
            if (z || this.text == null || charSequence == null || !this.text.equals(charSequence)) {
                this.text = charSequence;
                recreateLayoutMaybe();
            }
        }
    }

    public void setTextColor(int i) {
        this.textPaint.setColor(i);
        invalidate();
    }

    public void setTextSize(int i) {
        int dp = AndroidUtilities.dp((float) i);
        if (((float) dp) != this.textPaint.getTextSize()) {
            this.textPaint.setTextSize((float) dp);
            if (!recreateLayoutMaybe()) {
                invalidate();
            }
        }
    }

    public void setTypeface(Typeface typeface) {
        this.textPaint.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
    }
}
