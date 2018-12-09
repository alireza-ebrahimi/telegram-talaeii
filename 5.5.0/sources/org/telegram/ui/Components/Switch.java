package org.telegram.ui.Components;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.CompoundButton;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.customization.util.C2872c;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;

public class Switch extends CompoundButton {
    private static final int THUMB_ANIMATION_DURATION = 250;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private boolean attachedToWindow;
    private int mMinFlingVelocity;
    private ObjectAnimator mPositionAnimator;
    private boolean mSplitTrack;
    private int mSwitchBottom;
    private int mSwitchHeight;
    private int mSwitchLeft;
    private int mSwitchMinWidth;
    private int mSwitchPadding;
    private int mSwitchRight;
    private int mSwitchTop;
    private int mSwitchWidth;
    private final Rect mTempRect = new Rect();
    private Drawable mThumbDrawable;
    private int mThumbTextPadding;
    private int mThumbWidth;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private Drawable mTrackDrawable;
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    private float thumbPosition;
    private boolean wasLayout;

    public static class Insets {
        public static final Insets NONE = new Insets(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
        public final int bottom;
        public final int left;
        public final int right;
        public final int top;

        private Insets(int i, int i2, int i3, int i4) {
            this.left = i;
            this.top = i2;
            this.right = i3;
            this.bottom = i4;
        }
    }

    public Switch(Context context) {
        super(context);
        this.mThumbDrawable = context.getResources().getDrawable(R.drawable.switch_thumb);
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback(this);
        }
        this.mTrackDrawable = context.getResources().getDrawable(R.drawable.switch_track);
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(this);
        }
        if (AndroidUtilities.density < 1.0f) {
            this.mSwitchMinWidth = AndroidUtilities.dp(30.0f);
        } else {
            this.mSwitchMinWidth = 0;
        }
        this.mSwitchPadding = 0;
        this.mSplitTrack = false;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        refreshDrawableState();
        setChecked(isChecked());
    }

    private void animateThumbToCheckedState(boolean z) {
        float f = z ? 1.0f : BitmapDescriptorFactory.HUE_RED;
        this.mPositionAnimator = ObjectAnimator.ofFloat(this, "thumbPosition", new float[]{f});
        this.mPositionAnimator.setDuration(250);
        this.mPositionAnimator.start();
    }

    private void cancelPositionAnimator() {
        if (this.mPositionAnimator != null) {
            this.mPositionAnimator.cancel();
        }
    }

    private void cancelSuperTouch(MotionEvent motionEvent) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        super.onTouchEvent(obtain);
        obtain.recycle();
    }

    public static float constrain(float f, float f2, float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f;
    }

    private boolean getTargetCheckedState() {
        return this.thumbPosition > 0.5f;
    }

    private int getThumbOffset() {
        return (int) (((LocaleController.isRTL ? 1.0f - this.thumbPosition : this.thumbPosition) * ((float) getThumbScrollRange())) + 0.5f);
    }

    private int getThumbScrollRange() {
        if (this.mTrackDrawable == null) {
            return 0;
        }
        Rect rect = this.mTempRect;
        this.mTrackDrawable.getPadding(rect);
        Insets insets = this.mThumbDrawable != null ? Insets.NONE : Insets.NONE;
        return ((((this.mSwitchWidth - this.mThumbWidth) - rect.left) - rect.right) - insets.left) - insets.right;
    }

    private boolean hitThumb(float f, float f2) {
        int thumbOffset = getThumbOffset();
        this.mThumbDrawable.getPadding(this.mTempRect);
        thumbOffset = (thumbOffset + this.mSwitchLeft) - this.mTouchSlop;
        return f > ((float) thumbOffset) && f < ((float) ((((this.mThumbWidth + thumbOffset) + this.mTempRect.left) + this.mTempRect.right) + this.mTouchSlop)) && f2 > ((float) (this.mSwitchTop - this.mTouchSlop)) && f2 < ((float) (this.mSwitchBottom + this.mTouchSlop));
    }

    private void setThumbPosition(float f) {
        this.thumbPosition = f;
        invalidate();
    }

    private void stopDrag(MotionEvent motionEvent) {
        boolean z = true;
        this.mTouchMode = 0;
        boolean z2 = motionEvent.getAction() == 1 && isEnabled();
        if (z2) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float xVelocity = this.mVelocityTracker.getXVelocity();
            if (Math.abs(xVelocity) <= ((float) this.mMinFlingVelocity)) {
                z = getTargetCheckedState();
            } else if (LocaleController.isRTL) {
                if (xVelocity >= BitmapDescriptorFactory.HUE_RED) {
                    z = false;
                }
            } else if (xVelocity <= BitmapDescriptorFactory.HUE_RED) {
                z = false;
            }
        } else {
            z = isChecked();
        }
        setChecked(z);
        cancelSuperTouch(motionEvent);
    }

    public void checkColorFilters() {
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setColorFilter(new PorterDuffColorFilter(isChecked() ? Theme.getColor(Theme.key_switchTrackChecked) : Theme.getColor(Theme.key_switchTrack), Mode.MULTIPLY));
        }
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setColorFilter(new PorterDuffColorFilter(isChecked() ? Theme.getColor(Theme.key_switchThumbChecked) : Theme.getColor(Theme.key_switchThumb), Mode.MULTIPLY));
        }
    }

    public void draw(Canvas canvas) {
        int i;
        Rect rect = this.mTempRect;
        int i2 = this.mSwitchLeft;
        int i3 = this.mSwitchTop;
        int i4 = this.mSwitchRight;
        int i5 = this.mSwitchBottom;
        int thumbOffset = i2 + getThumbOffset();
        Insets insets = this.mThumbDrawable != null ? Insets.NONE : Insets.NONE;
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding(rect);
            int i6 = rect.left + thumbOffset;
            if (insets != Insets.NONE) {
                if (insets.left > rect.left) {
                    i2 += insets.left - rect.left;
                }
                thumbOffset = insets.top > rect.top ? (insets.top - rect.top) + i3 : i3;
                if (insets.right > rect.right) {
                    i4 -= insets.right - rect.right;
                }
                i = insets.bottom > rect.bottom ? i5 - (insets.bottom - rect.bottom) : i5;
            } else {
                i = i5;
                thumbOffset = i3;
            }
            this.mTrackDrawable.setBounds(i2, thumbOffset, i4, i);
            i = i6;
        } else {
            i = thumbOffset;
        }
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.getPadding(rect);
            i2 = i - rect.left;
            thumbOffset = rect.right + (i + this.mThumbWidth);
            i = AndroidUtilities.density == 1.5f ? AndroidUtilities.dp(1.0f) : 0;
            this.mThumbDrawable.setBounds(i2, i3 + i, thumbOffset, i + i5);
            Drawable background = getBackground();
            if (background != null && VERSION.SDK_INT >= 21) {
                background.setHotspotBounds(i2, i3, thumbOffset, i5);
            }
        }
        super.draw(canvas);
    }

    @SuppressLint({"NewApi"})
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setHotspot(f, f2);
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setHotspot(f, f2);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setState(drawableState);
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setState(drawableState);
        }
        invalidate();
    }

    public int getCompoundPaddingLeft() {
        return !LocaleController.isRTL ? super.getCompoundPaddingLeft() : super.getCompoundPaddingLeft() + this.mSwitchWidth;
    }

    public int getCompoundPaddingRight() {
        return LocaleController.isRTL ? super.getCompoundPaddingRight() : super.getCompoundPaddingRight() + this.mSwitchWidth;
    }

    public boolean getSplitTrack() {
        return this.mSplitTrack;
    }

    public int getSwitchMinWidth() {
        return this.mSwitchMinWidth;
    }

    public int getSwitchPadding() {
        return this.mSwitchPadding;
    }

    public Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    public float getThumbPosition() {
        return this.thumbPosition;
    }

    public int getThumbTextPadding() {
        return this.mThumbTextPadding;
    }

    public Drawable getTrackDrawable() {
        return this.mTrackDrawable;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.jumpToCurrentState();
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.jumpToCurrentState();
        }
        if (this.mPositionAnimator != null && this.mPositionAnimator.isRunning()) {
            this.mPositionAnimator.end();
            this.mPositionAnimator = null;
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.attachedToWindow = true;
        requestLayout();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.attachedToWindow = false;
        this.wasLayout = false;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = this.mTempRect;
        Drawable drawable = this.mTrackDrawable;
        if (drawable != null) {
            drawable.getPadding(rect);
        } else {
            rect.setEmpty();
        }
        int i = this.mSwitchTop;
        i = this.mSwitchBottom;
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable != null) {
            if (!this.mSplitTrack || drawable2 == null) {
                drawable.draw(canvas);
            } else {
                Insets insets = Insets.NONE;
                drawable2.copyBounds(rect);
                rect.left += insets.left;
                rect.right -= insets.right;
                int save = canvas.save();
                canvas.clipRect(rect, Op.DIFFERENCE);
                drawable.draw(canvas);
                canvas.restoreToCount(save);
            }
        }
        int save2 = canvas.save();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        canvas.restoreToCount(save2);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int max;
        int paddingLeft;
        int paddingTop;
        int i5 = 0;
        super.onLayout(z, i, i2, i3, i4);
        this.wasLayout = true;
        if (this.mThumbDrawable != null) {
            Rect rect = this.mTempRect;
            if (this.mTrackDrawable != null) {
                this.mTrackDrawable.getPadding(rect);
            } else {
                rect.setEmpty();
            }
            Insets insets = Insets.NONE;
            max = Math.max(0, insets.left - rect.left);
            i5 = Math.max(0, insets.right - rect.right);
        } else {
            max = 0;
        }
        if (LocaleController.isRTL) {
            paddingLeft = getPaddingLeft() + max;
            max = ((this.mSwitchWidth + paddingLeft) - max) - i5;
            i5 = paddingLeft;
        } else {
            paddingLeft = (getWidth() - getPaddingRight()) - i5;
            i5 += max + (paddingLeft - this.mSwitchWidth);
            max = paddingLeft;
        }
        switch (getGravity() & 112) {
            case 16:
                paddingTop = (((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2) - (this.mSwitchHeight / 2);
                paddingLeft = this.mSwitchHeight + paddingTop;
                break;
            case 80:
                paddingLeft = getHeight() - getPaddingBottom();
                paddingTop = paddingLeft - this.mSwitchHeight;
                break;
            default:
                paddingTop = getPaddingTop();
                paddingLeft = this.mSwitchHeight + paddingTop;
                break;
        }
        this.mSwitchLeft = i5;
        this.mSwitchTop = paddingTop;
        this.mSwitchBottom = paddingLeft;
        this.mSwitchRight = max;
    }

    public void onMeasure(int i, int i2) {
        int intrinsicWidth;
        int intrinsicHeight;
        int i3 = 0;
        Rect rect = this.mTempRect;
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.getPadding(rect);
            intrinsicWidth = (this.mThumbDrawable.getIntrinsicWidth() - rect.left) - rect.right;
            intrinsicHeight = this.mThumbDrawable.getIntrinsicHeight();
        } else {
            intrinsicHeight = 0;
            intrinsicWidth = 0;
        }
        this.mThumbWidth = intrinsicWidth;
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding(rect);
            i3 = this.mTrackDrawable.getIntrinsicHeight();
        } else {
            rect.setEmpty();
        }
        int i4 = rect.left;
        intrinsicWidth = rect.right;
        if (this.mThumbDrawable != null) {
            Insets insets = Insets.NONE;
            i4 = Math.max(i4, insets.left);
            intrinsicWidth = Math.max(intrinsicWidth, insets.right);
        }
        intrinsicWidth = Math.max(this.mSwitchMinWidth, intrinsicWidth + (i4 + (this.mThumbWidth * 2)));
        intrinsicHeight = Math.max(i3, intrinsicHeight);
        this.mSwitchWidth = intrinsicWidth;
        this.mSwitchHeight = intrinsicHeight;
        super.onMeasure(i, i2);
        if (getMeasuredHeight() < intrinsicHeight) {
            setMeasuredDimension(intrinsicWidth, intrinsicHeight);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mVelocityTracker.addMovement(motionEvent);
        float x;
        float y;
        switch (motionEvent.getActionMasked()) {
            case 0:
                x = motionEvent.getX();
                y = motionEvent.getY();
                if (isEnabled() && hitThumb(x, y)) {
                    this.mTouchMode = 1;
                    this.mTouchX = x;
                    this.mTouchY = y;
                    break;
                }
            case 1:
            case 3:
                if (this.mTouchMode != 2) {
                    this.mTouchMode = 0;
                    this.mVelocityTracker.clear();
                    break;
                }
                stopDrag(motionEvent);
                super.onTouchEvent(motionEvent);
                return true;
            case 2:
                switch (this.mTouchMode) {
                    case 0:
                        break;
                    case 1:
                        x = motionEvent.getX();
                        y = motionEvent.getY();
                        if (Math.abs(x - this.mTouchX) > ((float) this.mTouchSlop) || Math.abs(y - this.mTouchY) > ((float) this.mTouchSlop)) {
                            this.mTouchMode = 2;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            this.mTouchX = x;
                            this.mTouchY = y;
                            return true;
                        }
                    case 2:
                        float x2 = motionEvent.getX();
                        int thumbScrollRange = getThumbScrollRange();
                        float f = x2 - this.mTouchX;
                        x = thumbScrollRange != 0 ? f / ((float) thumbScrollRange) : f > BitmapDescriptorFactory.HUE_RED ? 1.0f : -1.0f;
                        if (LocaleController.isRTL) {
                            x = -x;
                        }
                        x = constrain(x + this.thumbPosition, BitmapDescriptorFactory.HUE_RED, 1.0f);
                        if (x != this.thumbPosition) {
                            this.mTouchX = x2;
                            setThumbPosition(x);
                        }
                        return true;
                    default:
                        break;
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void resetLayout() {
        this.wasLayout = false;
    }

    public void setChecked(boolean z) {
        super.setChecked(z);
        boolean isChecked = isChecked();
        if (this.attachedToWindow && this.wasLayout) {
            animateThumbToCheckedState(isChecked);
        } else {
            cancelPositionAnimator();
            setThumbPosition(isChecked ? 1.0f : BitmapDescriptorFactory.HUE_RED);
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setColorFilter(new PorterDuffColorFilter(isChecked ? Theme.getColor(Theme.key_switchTrackChecked) : Theme.getColor(Theme.key_switchTrack), Mode.MULTIPLY));
        }
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setColorFilter(new PorterDuffColorFilter(isChecked ? Theme.getColor(Theme.key_switchThumbChecked) : Theme.getColor(Theme.key_switchThumb), Mode.MULTIPLY));
        }
    }

    public void setColor(int i) {
        boolean isChecked = isChecked();
        int i2 = ApplicationLoader.applicationContext.getSharedPreferences("theme", 0).getInt("themeColor", C2872c.f9484b);
        int a = C2872c.a("themeColor", C2872c.f9484b, 0.5f);
        int a2 = C2872c.a(i, 127);
        if (i != i2) {
            a = a2;
        }
        if (this.mTrackDrawable != null) {
            Drawable drawable = this.mTrackDrawable;
            if (!isChecked) {
                i = -3684409;
            }
            drawable.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
        }
        if (this.mThumbDrawable != null) {
            drawable = this.mThumbDrawable;
            if (!isChecked) {
                a = -1184275;
            }
            drawable.setColorFilter(new PorterDuffColorFilter(a, Mode.MULTIPLY));
        }
    }

    public void setSplitTrack(boolean z) {
        this.mSplitTrack = z;
        invalidate();
    }

    public void setSwitchMinWidth(int i) {
        this.mSwitchMinWidth = i;
        requestLayout();
    }

    public void setSwitchPadding(int i) {
        this.mSwitchPadding = i;
        requestLayout();
    }

    public void setThumbDrawable(Drawable drawable) {
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback(null);
        }
        this.mThumbDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void setThumbTextPadding(int i) {
        this.mThumbTextPadding = i;
        requestLayout();
    }

    public void setTrackDrawable(Drawable drawable) {
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(null);
        }
        this.mTrackDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mThumbDrawable || drawable == this.mTrackDrawable;
    }
}
