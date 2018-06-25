package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;
import android.support.v7.appcompat.C0299R;
import android.support.v7.text.AllCapsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

@TargetApi(9)
@RequiresApi(9)
class AppCompatTextHelper {
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableTopTint;
    final TextView mView;

    static AppCompatTextHelper create(TextView textView) {
        if (VERSION.SDK_INT >= 17) {
            return new AppCompatTextHelperV17(textView);
        }
        return new AppCompatTextHelper(textView);
    }

    AppCompatTextHelper(TextView view) {
        this.mView = view;
    }

    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        Context context = this.mView.getContext();
        AppCompatDrawableManager drawableManager = AppCompatDrawableManager.get();
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, C0299R.styleable.AppCompatTextHelper, defStyleAttr, 0);
        int ap = a.getResourceId(C0299R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (a.hasValue(C0299R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.mDrawableLeftTint = createTintInfo(context, drawableManager, a.getResourceId(C0299R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (a.hasValue(C0299R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.mDrawableTopTint = createTintInfo(context, drawableManager, a.getResourceId(C0299R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (a.hasValue(C0299R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.mDrawableRightTint = createTintInfo(context, drawableManager, a.getResourceId(C0299R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (a.hasValue(C0299R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.mDrawableBottomTint = createTintInfo(context, drawableManager, a.getResourceId(C0299R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        a.recycle();
        boolean hasPwdTm = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        boolean allCaps = false;
        boolean allCapsSet = false;
        ColorStateList textColor = null;
        ColorStateList textColorHint = null;
        if (ap != -1) {
            a = TintTypedArray.obtainStyledAttributes(context, ap, C0299R.styleable.TextAppearance);
            if (!hasPwdTm && a.hasValue(C0299R.styleable.TextAppearance_textAllCaps)) {
                allCapsSet = true;
                allCaps = a.getBoolean(C0299R.styleable.TextAppearance_textAllCaps, false);
            }
            if (VERSION.SDK_INT < 23) {
                if (a.hasValue(C0299R.styleable.TextAppearance_android_textColor)) {
                    textColor = a.getColorStateList(C0299R.styleable.TextAppearance_android_textColor);
                }
                if (a.hasValue(C0299R.styleable.TextAppearance_android_textColorHint)) {
                    textColorHint = a.getColorStateList(C0299R.styleable.TextAppearance_android_textColorHint);
                }
            }
            a.recycle();
        }
        a = TintTypedArray.obtainStyledAttributes(context, attrs, C0299R.styleable.TextAppearance, defStyleAttr, 0);
        if (!hasPwdTm && a.hasValue(C0299R.styleable.TextAppearance_textAllCaps)) {
            allCapsSet = true;
            allCaps = a.getBoolean(C0299R.styleable.TextAppearance_textAllCaps, false);
        }
        if (VERSION.SDK_INT < 23) {
            if (a.hasValue(C0299R.styleable.TextAppearance_android_textColor)) {
                textColor = a.getColorStateList(C0299R.styleable.TextAppearance_android_textColor);
            }
            if (a.hasValue(C0299R.styleable.TextAppearance_android_textColorHint)) {
                textColorHint = a.getColorStateList(C0299R.styleable.TextAppearance_android_textColorHint);
            }
        }
        a.recycle();
        if (textColor != null) {
            this.mView.setTextColor(textColor);
        }
        if (textColorHint != null) {
            this.mView.setHintTextColor(textColorHint);
        }
        if (!hasPwdTm && allCapsSet) {
            setAllCaps(allCaps);
        }
    }

    void onSetTextAppearance(Context context, int resId) {
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, resId, C0299R.styleable.TextAppearance);
        if (a.hasValue(C0299R.styleable.TextAppearance_textAllCaps)) {
            setAllCaps(a.getBoolean(C0299R.styleable.TextAppearance_textAllCaps, false));
        }
        if (VERSION.SDK_INT < 23 && a.hasValue(C0299R.styleable.TextAppearance_android_textColor)) {
            ColorStateList textColor = a.getColorStateList(C0299R.styleable.TextAppearance_android_textColor);
            if (textColor != null) {
                this.mView.setTextColor(textColor);
            }
        }
        a.recycle();
    }

    void setAllCaps(boolean allCaps) {
        this.mView.setTransformationMethod(allCaps ? new AllCapsTransformationMethod(this.mView.getContext()) : null);
    }

    void applyCompoundDrawablesTints() {
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
    }

    final void applyCompoundDrawableTint(Drawable drawable, TintInfo info) {
        if (drawable != null && info != null) {
            AppCompatDrawableManager.tintDrawable(drawable, info, this.mView.getDrawableState());
        }
    }

    protected static TintInfo createTintInfo(Context context, AppCompatDrawableManager drawableManager, int drawableId) {
        ColorStateList tintList = drawableManager.getTintList(context, drawableId);
        if (tintList == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = tintList;
        return tintInfo;
    }
}
