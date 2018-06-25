package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;
import android.widget.FrameLayout;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.ActionBar;

public class SizeNotifierFrameLayout extends FrameLayout {
    private Drawable backgroundDrawable;
    private int bottomClip;
    private SizeNotifierFrameLayoutDelegate delegate;
    private int keyboardHeight;
    private Rect rect = new Rect();

    public interface SizeNotifierFrameLayoutDelegate {
        void onSizeChanged(int i, boolean z);
    }

    public SizeNotifierFrameLayout(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public Drawable getBackgroundImage() {
        return this.backgroundDrawable;
    }

    public int getKeyboardHeight() {
        View rootView = getRootView();
        getWindowVisibleDisplayFrame(this.rect);
        return ((rootView.getHeight() - (this.rect.top != 0 ? AndroidUtilities.statusBarHeight : 0)) - AndroidUtilities.getViewInset(rootView)) - (this.rect.bottom - this.rect.top);
    }

    protected boolean isActionBarVisible() {
        return true;
    }

    public void notifyHeightChanged() {
        if (this.delegate != null) {
            this.keyboardHeight = getKeyboardHeight();
            final boolean z = AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y;
            post(new Runnable() {
                public void run() {
                    if (SizeNotifierFrameLayout.this.delegate != null) {
                        SizeNotifierFrameLayout.this.delegate.onSizeChanged(SizeNotifierFrameLayout.this.keyboardHeight, z);
                    }
                }
            });
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this.backgroundDrawable == null) {
            super.onDraw(canvas);
        } else if (this.backgroundDrawable instanceof ColorDrawable) {
            if (this.bottomClip != 0) {
                canvas.save();
                canvas.clipRect(0, 0, getMeasuredWidth(), getMeasuredHeight() - this.bottomClip);
            }
            this.backgroundDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            this.backgroundDrawable.draw(canvas);
            if (this.bottomClip != 0) {
                canvas.restore();
            }
        } else if (!(this.backgroundDrawable instanceof BitmapDrawable)) {
        } else {
            float f;
            if (((BitmapDrawable) this.backgroundDrawable).getTileModeX() == TileMode.REPEAT) {
                canvas.save();
                f = 2.0f / AndroidUtilities.density;
                canvas.scale(f, f);
                this.backgroundDrawable.setBounds(0, 0, (int) Math.ceil((double) (((float) getMeasuredWidth()) / f)), (int) Math.ceil((double) (((float) getMeasuredHeight()) / f)));
                this.backgroundDrawable.draw(canvas);
                canvas.restore();
                return;
            }
            int currentActionBarHeight = (isActionBarVisible() ? ActionBar.getCurrentActionBarHeight() : 0) + (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0);
            int measuredHeight = getMeasuredHeight() - currentActionBarHeight;
            float measuredWidth = ((float) getMeasuredWidth()) / ((float) this.backgroundDrawable.getIntrinsicWidth());
            f = ((float) (this.keyboardHeight + measuredHeight)) / ((float) this.backgroundDrawable.getIntrinsicHeight());
            if (measuredWidth >= f) {
                f = measuredWidth;
            }
            int ceil = (int) Math.ceil((double) (((float) this.backgroundDrawable.getIntrinsicWidth()) * f));
            int ceil2 = (int) Math.ceil((double) (f * ((float) this.backgroundDrawable.getIntrinsicHeight())));
            int measuredWidth2 = (getMeasuredWidth() - ceil) / 2;
            measuredHeight = (((measuredHeight - ceil2) + this.keyboardHeight) / 2) + currentActionBarHeight;
            canvas.save();
            canvas.clipRect(0, currentActionBarHeight, ceil, getMeasuredHeight() - this.bottomClip);
            this.backgroundDrawable.setBounds(measuredWidth2, measuredHeight, ceil + measuredWidth2, ceil2 + measuredHeight);
            this.backgroundDrawable.draw(canvas);
            canvas.restore();
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        notifyHeightChanged();
    }

    public void setBackgroundImage(Drawable drawable) {
        this.backgroundDrawable = drawable;
        invalidate();
    }

    public void setBottomClip(int i) {
        this.bottomClip = i;
    }

    public void setDelegate(SizeNotifierFrameLayoutDelegate sizeNotifierFrameLayoutDelegate) {
        this.delegate = sizeNotifierFrameLayoutDelegate;
    }
}
