package uk.co.samuelwall.materialtaptargetprompt;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.GravityCompat;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import java.text.Bidi;

public class MaterialTapTargetPrompt {
    public static final int STATE_DISMISSED = 6;
    public static final int STATE_DISMISSING = 5;
    public static final int STATE_FINISHED = 4;
    public static final int STATE_FOCAL_PRESSED = 3;
    public static final int STATE_REVEALED = 2;
    public static final int STATE_REVEALING = 1;
    float m88dp;
    ValueAnimator mAnimationCurrent;
    ValueAnimator mAnimationFocalRipple;
    Interpolator mAnimationInterpolator;
    boolean mAutoDismiss;
    boolean mAutoFinish;
    int mBaseBackgroundColourAlpha;
    PointF mBaseBackgroundPosition = new PointF();
    float mBaseBackgroundRadius;
    int mBaseFocalColourAlpha;
    float mBaseFocalRadius;
    int mBaseFocalRippleAlpha;
    View mClipToView;
    RectF mClipViewBoundsInset88dp;
    boolean mDismissing;
    float mFocalRadius10Percent;
    float mFocalRippleProgress;
    float mFocalToTextPadding;
    final OnGlobalLayoutListener mGlobalLayoutListener;
    boolean mHorizontalTextPositionLeft;
    boolean mIdleAnimationEnabled = true;
    boolean mInside88dpBounds;
    @Deprecated
    boolean mIsDismissingOld;
    float mMaxTextWidth;
    @Deprecated
    OnHidePromptListener mOnHidePromptListener;
    TextPaint mPaintPrimaryText;
    TextPaint mPaintSecondaryText;
    ViewGroup mParentView;
    String mPrimaryText;
    Alignment mPrimaryTextAlignment;
    int mPrimaryTextColourAlpha;
    MaterialTapTargetPrompt$PromptStateChangeListener mPromptStateChangeListener;
    ResourceFinder mResourceFinder;
    float mRevealedAmount;
    String mSecondaryText;
    Alignment mSecondaryTextAlignment;
    int mSecondaryTextColourAlpha;
    final float mStatusBarHeight;
    PointF mTargetPosition;
    View mTargetView;
    float mTextPadding;
    boolean mVerticalTextPositionAbove;
    MaterialTapTargetPrompt$PromptView mView;

    public static class Builder {
        private float m88dp;
        private Interpolator mAnimationInterpolator;
        private boolean mAutoDismiss;
        private boolean mAutoFinish;
        private boolean mBackButtonDismissEnabled;
        private int mBackgroundColour;
        private int mBackgroundColourAlpha;
        private boolean mCaptureTouchEventOnFocal;
        private boolean mCaptureTouchEventOutsidePrompt;
        private View mClipToView;
        private int mFocalColour;
        private int mFocalColourAlpha;
        private float mFocalRadius;
        private float mFocalToTextPadding;
        private boolean mHasIconDrawableTint;
        private Drawable mIconDrawable;
        private int mIconDrawableColourFilter;
        private ColorStateList mIconDrawableTintList;
        private Mode mIconDrawableTintMode;
        private boolean mIdleAnimationEnabled;
        private float mMaxTextWidth;
        @Deprecated
        private OnHidePromptListener mOnHidePromptListener;
        private String mPrimaryText;
        private int mPrimaryTextColour;
        private int mPrimaryTextGravity;
        private float mPrimaryTextSize;
        private Typeface mPrimaryTextTypeface;
        private int mPrimaryTextTypefaceStyle;
        private MaterialTapTargetPrompt$PromptStateChangeListener mPromptStateChangeListener;
        final ResourceFinder mResourceFinder;
        private String mSecondaryText;
        private int mSecondaryTextColour;
        private int mSecondaryTextGravity;
        private float mSecondaryTextSize;
        private Typeface mSecondaryTextTypeface;
        private int mSecondaryTextTypefaceStyle;
        private PointF mTargetPosition;
        private View mTargetRenderView;
        private boolean mTargetSet;
        private View mTargetView;
        private float mTextPadding;
        private float mTextSeparation;

        @TargetApi(11)
        public Builder(Fragment fragment) {
            this(fragment.getActivity(), 0);
        }

        @TargetApi(11)
        public Builder(Fragment fragment, int themeResId) {
            this(fragment.getActivity(), themeResId);
        }

        @TargetApi(11)
        public Builder(DialogFragment dialogFragment) {
            this(dialogFragment, 0);
        }

        @TargetApi(11)
        public Builder(DialogFragment dialogFragment, int themeResId) {
            this(dialogFragment.getDialog(), themeResId);
        }

        public Builder(Dialog dialog) {
            this(dialog, 0);
        }

        public Builder(Dialog dialog, int themeResId) {
            this(new DialogResourceFinder(dialog), themeResId);
        }

        public Builder(Activity activity) {
            this(activity, 0);
        }

        public Builder(Activity activity, int themeResId) {
            this(new ActivityResourceFinder(activity), themeResId);
        }

        public Builder(ResourceFinder resourceFinder, int themeResId) {
            this.mIconDrawableTintList = null;
            this.mIconDrawableTintMode = null;
            this.mIdleAnimationEnabled = true;
            this.mPrimaryTextGravity = GravityCompat.START;
            this.mSecondaryTextGravity = GravityCompat.START;
            this.mResourceFinder = resourceFinder;
            if (themeResId == 0) {
                TypedValue outValue = new TypedValue();
                this.mResourceFinder.getTheme().resolveAttribute(C3463R.attr.MaterialTapTargetPromptTheme, outValue, true);
                themeResId = outValue.resourceId;
            }
            float density = this.mResourceFinder.getResources().getDisplayMetrics().density;
            this.m88dp = 88.0f * density;
            TypedArray a = this.mResourceFinder.obtainStyledAttributes(themeResId, C3463R.styleable.PromptView);
            this.mPrimaryTextColour = a.getColor(C3463R.styleable.PromptView_primaryTextColour, -1);
            this.mSecondaryTextColour = a.getColor(C3463R.styleable.PromptView_secondaryTextColour, Color.argb(179, 255, 255, 255));
            this.mPrimaryText = a.getString(C3463R.styleable.PromptView_primaryText);
            this.mSecondaryText = a.getString(C3463R.styleable.PromptView_secondaryText);
            this.mBackgroundColour = a.getColor(C3463R.styleable.PromptView_backgroundColour, Color.argb(244, 63, 81, 181));
            this.mFocalColour = a.getColor(C3463R.styleable.PromptView_focalColour, -1);
            this.mFocalRadius = a.getDimension(C3463R.styleable.PromptView_focalRadius, 44.0f * density);
            this.mPrimaryTextSize = a.getDimension(C3463R.styleable.PromptView_primaryTextSize, 22.0f * density);
            this.mSecondaryTextSize = a.getDimension(C3463R.styleable.PromptView_secondaryTextSize, 18.0f * density);
            this.mMaxTextWidth = a.getDimension(C3463R.styleable.PromptView_maxTextWidth, 400.0f * density);
            this.mTextPadding = a.getDimension(C3463R.styleable.PromptView_textPadding, 40.0f * density);
            this.mFocalToTextPadding = a.getDimension(C3463R.styleable.PromptView_focalToTextPadding, 20.0f * density);
            this.mTextSeparation = a.getDimension(C3463R.styleable.PromptView_textSeparation, 16.0f * density);
            this.mAutoDismiss = a.getBoolean(C3463R.styleable.PromptView_autoDismiss, true);
            this.mAutoFinish = a.getBoolean(C3463R.styleable.PromptView_autoFinish, true);
            this.mCaptureTouchEventOutsidePrompt = a.getBoolean(C3463R.styleable.PromptView_captureTouchEventOutsidePrompt, false);
            this.mCaptureTouchEventOnFocal = a.getBoolean(C3463R.styleable.PromptView_captureTouchEventOnFocal, false);
            this.mPrimaryTextTypefaceStyle = a.getInt(C3463R.styleable.PromptView_primaryTextStyle, 0);
            this.mSecondaryTextTypefaceStyle = a.getInt(C3463R.styleable.PromptView_secondaryTextStyle, 0);
            this.mPrimaryTextTypeface = setTypefaceFromAttrs(a.getString(C3463R.styleable.PromptView_primaryTextFontFamily), a.getInt(C3463R.styleable.PromptView_primaryTextTypeface, 0), this.mPrimaryTextTypefaceStyle);
            this.mSecondaryTextTypeface = setTypefaceFromAttrs(a.getString(C3463R.styleable.PromptView_secondaryTextFontFamily), a.getInt(C3463R.styleable.PromptView_secondaryTextTypeface, 0), this.mSecondaryTextTypefaceStyle);
            this.mBackgroundColourAlpha = a.getInt(C3463R.styleable.PromptView_backgroundColourAlpha, 244);
            this.mFocalColourAlpha = a.getInt(C3463R.styleable.PromptView_focalColourAlpha, 255);
            this.mIconDrawableColourFilter = a.getColor(C3463R.styleable.PromptView_iconColourFilter, this.mBackgroundColour);
            this.mIconDrawableTintList = a.getColorStateList(C3463R.styleable.PromptView_iconTint);
            this.mIconDrawableTintMode = parseTintMode(a.getInt(C3463R.styleable.PromptView_iconTintMode, -1), Mode.MULTIPLY);
            this.mHasIconDrawableTint = true;
            int targetId = a.getResourceId(C3463R.styleable.PromptView_target, 0);
            a.recycle();
            if (targetId != 0) {
                this.mTargetView = this.mResourceFinder.findViewById(targetId);
                if (this.mTargetView != null) {
                    this.mTargetSet = true;
                }
            }
            this.mClipToView = this.mResourceFinder.findViewById(16908290);
        }

        public Builder setTarget(View target) {
            this.mTargetView = target;
            this.mTargetSet = this.mTargetView != null;
            return this;
        }

        public Builder setTarget(@IdRes int target) {
            this.mTargetView = this.mResourceFinder.findViewById(target);
            this.mTargetPosition = null;
            this.mTargetSet = this.mTargetView != null;
            return this;
        }

        public Builder setTarget(float left, float top) {
            this.mTargetView = null;
            this.mTargetPosition = new PointF(left, top);
            this.mTargetSet = true;
            return this;
        }

        public Builder setTargetRenderView(View view) {
            this.mTargetRenderView = view;
            return this;
        }

        public boolean isTargetSet() {
            return this.mTargetSet;
        }

        public Builder setPrimaryText(@StringRes int resId) {
            this.mPrimaryText = this.mResourceFinder.getString(resId);
            return this;
        }

        public Builder setPrimaryText(String text) {
            this.mPrimaryText = text;
            return this;
        }

        public Builder setPrimaryTextSize(@DimenRes int resId) {
            this.mPrimaryTextSize = this.mResourceFinder.getResources().getDimension(resId);
            return this;
        }

        public Builder setPrimaryTextSize(float size) {
            this.mPrimaryTextSize = size;
            return this;
        }

        public Builder setPrimaryTextColour(@ColorInt int colour) {
            this.mPrimaryTextColour = colour;
            return this;
        }

        @Deprecated
        public Builder setPrimaryTextColourFromRes(@ColorRes int resId) {
            this.mPrimaryTextColour = getColour(resId);
            return this;
        }

        public Builder setPrimaryTextTypeface(Typeface typeface) {
            return setPrimaryTextTypeface(typeface, 0);
        }

        public Builder setPrimaryTextTypeface(Typeface typeface, int style) {
            this.mPrimaryTextTypeface = typeface;
            this.mPrimaryTextTypefaceStyle = style;
            return this;
        }

        public Builder setSecondaryText(@StringRes int resId) {
            this.mSecondaryText = this.mResourceFinder.getString(resId);
            return this;
        }

        public Builder setSecondaryText(String text) {
            this.mSecondaryText = text;
            return this;
        }

        public Builder setSecondaryTextSize(@DimenRes int resId) {
            this.mSecondaryTextSize = this.mResourceFinder.getResources().getDimension(resId);
            return this;
        }

        public Builder setSecondaryTextSize(float size) {
            this.mSecondaryTextSize = size;
            return this;
        }

        public Builder setSecondaryTextColour(@ColorInt int colour) {
            this.mSecondaryTextColour = colour;
            return this;
        }

        @Deprecated
        public Builder setSecondaryTextColourFromRes(@ColorRes int resId) {
            this.mSecondaryTextColour = getColour(resId);
            return this;
        }

        public Builder setSecondaryTextTypeface(Typeface typeface) {
            return setSecondaryTextTypeface(typeface, 0);
        }

        public Builder setSecondaryTextTypeface(Typeface typeface, int style) {
            this.mSecondaryTextTypeface = typeface;
            this.mSecondaryTextTypefaceStyle = style;
            return this;
        }

        public Builder setTextPadding(float padding) {
            this.mTextPadding = padding;
            return this;
        }

        public Builder setTextPadding(@DimenRes int resId) {
            this.mTextPadding = this.mResourceFinder.getResources().getDimension(resId);
            return this;
        }

        public Builder setTextSeparation(float separation) {
            this.mTextSeparation = separation;
            return this;
        }

        public Builder setTextSeparation(@DimenRes int resId) {
            this.mTextSeparation = this.mResourceFinder.getResources().getDimension(resId);
            return this;
        }

        @Deprecated
        public Builder setFocalToTextPadding(float padding) {
            return setFocalPadding(padding);
        }

        public Builder setFocalPadding(float padding) {
            this.mFocalToTextPadding = padding;
            return this;
        }

        @Deprecated
        public Builder setFocalToTextPadding(@DimenRes int resId) {
            return setFocalPadding(resId);
        }

        public Builder setFocalPadding(@DimenRes int resId) {
            this.mFocalToTextPadding = this.mResourceFinder.getResources().getDimension(resId);
            return this;
        }

        public Builder setAnimationInterpolator(Interpolator interpolator) {
            this.mAnimationInterpolator = interpolator;
            return this;
        }

        public Builder setIdleAnimationEnabled(boolean enabled) {
            this.mIdleAnimationEnabled = enabled;
            return this;
        }

        public Builder setIcon(@DrawableRes int resId) {
            this.mIconDrawable = this.mResourceFinder.getDrawable(resId);
            return this;
        }

        public Builder setIconDrawable(Drawable drawable) {
            this.mIconDrawable = drawable;
            return this;
        }

        public Builder setIconDrawableTintList(@Nullable ColorStateList tint) {
            this.mIconDrawableTintList = tint;
            this.mHasIconDrawableTint = tint != null;
            return this;
        }

        public Builder setIconDrawableTintMode(@Nullable Mode tintMode) {
            this.mIconDrawableTintMode = tintMode;
            if (tintMode == null) {
                this.mIconDrawableTintList = null;
                this.mHasIconDrawableTint = false;
            }
            return this;
        }

        public Builder setIconDrawableColourFilter(@ColorInt int colour) {
            this.mIconDrawableColourFilter = colour;
            this.mIconDrawableTintList = null;
            this.mHasIconDrawableTint = true;
            return this;
        }

        @Deprecated
        public Builder setIconDrawableColourFilterFromRes(@ColorRes int id) {
            return setIconDrawableColourFilter(getColour(id));
        }

        @Deprecated
        public Builder setOnHidePromptListener(OnHidePromptListener listener) {
            this.mOnHidePromptListener = listener;
            return this;
        }

        public Builder setPromptStateChangeListener(MaterialTapTargetPrompt$PromptStateChangeListener listener) {
            this.mPromptStateChangeListener = listener;
            return this;
        }

        public Builder setCaptureTouchEventOnFocal(boolean captureTouchEvent) {
            this.mCaptureTouchEventOnFocal = captureTouchEvent;
            return this;
        }

        public Builder setMaxTextWidth(float width) {
            this.mMaxTextWidth = width;
            return this;
        }

        public Builder setMaxTextWidth(@DimenRes int resId) {
            this.mMaxTextWidth = this.mResourceFinder.getResources().getDimension(resId);
            return this;
        }

        public Builder setBackgroundColour(@ColorInt int colour) {
            this.mBackgroundColour = colour;
            return this;
        }

        @Deprecated
        public Builder setBackgroundColourFromRes(@ColorRes int resId) {
            this.mBackgroundColour = getColour(resId);
            return this;
        }

        @Deprecated
        public Builder setBackgroundColourAlpha(int alpha) {
            this.mBackgroundColourAlpha = alpha;
            return this;
        }

        public Builder setFocalColour(@ColorInt int colour) {
            this.mFocalColour = colour;
            return this;
        }

        @Deprecated
        public Builder setFocalColourFromRes(@ColorRes int resId) {
            this.mFocalColour = getColour(resId);
            return this;
        }

        @Deprecated
        public Builder setFocalColourAlpha(int alpha) {
            this.mFocalColourAlpha = alpha;
            return this;
        }

        public Builder setFocalRadius(float radius) {
            this.mFocalRadius = radius;
            return this;
        }

        public Builder setFocalRadius(@DimenRes int resId) {
            this.mFocalRadius = this.mResourceFinder.getResources().getDimension(resId);
            return this;
        }

        public Builder setAutoDismiss(boolean autoDismiss) {
            this.mAutoDismiss = autoDismiss;
            return this;
        }

        public Builder setAutoFinish(boolean autoFinish) {
            this.mAutoFinish = autoFinish;
            return this;
        }

        public Builder setCaptureTouchEventOutsidePrompt(boolean captureTouchEventOutsidePrompt) {
            this.mCaptureTouchEventOutsidePrompt = captureTouchEventOutsidePrompt;
            return this;
        }

        public Builder setTextGravity(int gravity) {
            this.mPrimaryTextGravity = gravity;
            this.mSecondaryTextGravity = gravity;
            return this;
        }

        public Builder setPrimaryTextGravity(int gravity) {
            this.mPrimaryTextGravity = gravity;
            return this;
        }

        public Builder setSecondaryTextGravity(int gravity) {
            this.mSecondaryTextGravity = gravity;
            return this;
        }

        public Builder setClipToView(View view) {
            this.mClipToView = view;
            return this;
        }

        public Builder setBackButtonDismissEnabled(boolean enabled) {
            this.mBackButtonDismissEnabled = enabled;
            return this;
        }

        public MaterialTapTargetPrompt create() {
            if (!this.mTargetSet || (this.mPrimaryText == null && this.mSecondaryText == null)) {
                return null;
            }
            boolean z;
            MaterialTapTargetPrompt mPrompt = new MaterialTapTargetPrompt(this.mResourceFinder);
            if (this.mTargetView != null) {
                mPrompt.mTargetView = this.mTargetView;
                mPrompt.mView.mTargetView = this.mTargetView;
            } else {
                mPrompt.mTargetPosition = this.mTargetPosition;
            }
            mPrompt.mParentView = this.mResourceFinder.getPromptParentView();
            MaterialTapTargetPrompt$PromptView materialTapTargetPrompt$PromptView = mPrompt.mView;
            if (VERSION.SDK_INT < 11 || !this.mIdleAnimationEnabled) {
                z = false;
            } else {
                z = true;
            }
            materialTapTargetPrompt$PromptView.mDrawRipple = z;
            mPrompt.mIdleAnimationEnabled = this.mIdleAnimationEnabled;
            mPrompt.mClipToView = this.mClipToView;
            mPrompt.mPrimaryText = this.mPrimaryText;
            mPrompt.mPrimaryTextColourAlpha = Color.alpha(this.mPrimaryTextColour);
            mPrompt.mSecondaryText = this.mSecondaryText;
            mPrompt.mSecondaryTextColourAlpha = Color.alpha(this.mSecondaryTextColour);
            mPrompt.mMaxTextWidth = this.mMaxTextWidth;
            mPrompt.mTextPadding = this.mTextPadding;
            mPrompt.mFocalToTextPadding = this.mFocalToTextPadding;
            mPrompt.mBaseFocalRippleAlpha = 150;
            mPrompt.m88dp = this.m88dp;
            mPrompt.mBaseBackgroundColourAlpha = this.mBackgroundColourAlpha;
            mPrompt.mBaseFocalColourAlpha = this.mFocalColourAlpha;
            mPrompt.mView.mTextSeparation = this.mTextSeparation;
            mPrompt.mOnHidePromptListener = this.mOnHidePromptListener;
            mPrompt.mPromptStateChangeListener = this.mPromptStateChangeListener;
            mPrompt.mView.mCaptureTouchEventOnFocal = this.mCaptureTouchEventOnFocal;
            if (this.mAnimationInterpolator != null) {
                mPrompt.mAnimationInterpolator = this.mAnimationInterpolator;
            } else {
                mPrompt.mAnimationInterpolator = new AccelerateDecelerateInterpolator();
            }
            mPrompt.mBaseFocalRadius = this.mFocalRadius;
            mPrompt.mFocalRadius10Percent = (this.mFocalRadius / 100.0f) * 10.0f;
            if (this.mIconDrawable != null) {
                this.mIconDrawable.mutate();
                this.mIconDrawable.setBounds(0, 0, this.mIconDrawable.getIntrinsicWidth(), this.mIconDrawable.getIntrinsicHeight());
                if (this.mHasIconDrawableTint) {
                    if (this.mIconDrawableTintList == null) {
                        this.mIconDrawable.setColorFilter(this.mIconDrawableColourFilter, this.mIconDrawableTintMode);
                        this.mIconDrawable.setAlpha(Color.alpha(this.mIconDrawableColourFilter));
                    } else if (VERSION.SDK_INT >= 21) {
                        this.mIconDrawable.setTintList(this.mIconDrawableTintList);
                    }
                }
            }
            mPrompt.mView.mBackButtonDismissEnabled = this.mBackButtonDismissEnabled;
            mPrompt.mView.mIconDrawable = this.mIconDrawable;
            mPrompt.mView.mPaintFocal = new Paint();
            mPrompt.mView.mPaintFocal.setColor(this.mFocalColour);
            mPrompt.mView.mPaintFocal.setAlpha(this.mFocalColourAlpha);
            mPrompt.mView.mPaintFocal.setAntiAlias(true);
            mPrompt.mView.mPaintBackground = new Paint();
            mPrompt.mView.mPaintBackground.setColor(this.mBackgroundColour);
            mPrompt.mView.mPaintBackground.setAlpha(this.mBackgroundColourAlpha);
            mPrompt.mView.mPaintBackground.setAntiAlias(true);
            if (this.mPrimaryText != null) {
                mPrompt.mPaintPrimaryText = new TextPaint();
                mPrompt.mPaintPrimaryText.setColor(this.mPrimaryTextColour);
                mPrompt.mPaintPrimaryText.setAlpha(Color.alpha(this.mPrimaryTextColour));
                mPrompt.mPaintPrimaryText.setAntiAlias(true);
                mPrompt.mPaintPrimaryText.setTextSize(this.mPrimaryTextSize);
                setTypeface(mPrompt.mPaintPrimaryText, this.mPrimaryTextTypeface, this.mPrimaryTextTypefaceStyle);
                mPrompt.mPrimaryTextAlignment = getTextAlignment(this.mPrimaryTextGravity, this.mPrimaryText);
            }
            if (this.mSecondaryText != null) {
                mPrompt.mPaintSecondaryText = new TextPaint();
                mPrompt.mPaintSecondaryText.setColor(this.mSecondaryTextColour);
                mPrompt.mPaintSecondaryText.setAlpha(Color.alpha(this.mSecondaryTextColour));
                mPrompt.mPaintSecondaryText.setAntiAlias(true);
                mPrompt.mPaintSecondaryText.setTextSize(this.mSecondaryTextSize);
                setTypeface(mPrompt.mPaintSecondaryText, this.mSecondaryTextTypeface, this.mSecondaryTextTypefaceStyle);
                mPrompt.mSecondaryTextAlignment = getTextAlignment(this.mSecondaryTextGravity, this.mSecondaryText);
            }
            mPrompt.mAutoDismiss = this.mAutoDismiss;
            mPrompt.mAutoFinish = this.mAutoFinish;
            mPrompt.mView.mCaptureTouchEventOutsidePrompt = this.mCaptureTouchEventOutsidePrompt;
            if (this.mTargetRenderView == null) {
                mPrompt.mView.mTargetRenderView = mPrompt.mView.mTargetView;
                return mPrompt;
            }
            mPrompt.mView.mTargetRenderView = this.mTargetRenderView;
            return mPrompt;
        }

        public MaterialTapTargetPrompt show() {
            MaterialTapTargetPrompt mPrompt = create();
            if (mPrompt != null) {
                mPrompt.show();
            }
            return mPrompt;
        }

        private int getColour(int resId) {
            if (VERSION.SDK_INT >= 23) {
                return this.mResourceFinder.getContext().getColor(resId);
            }
            return this.mResourceFinder.getResources().getColor(resId);
        }

        private void setTypeface(TextPaint textPaint, Typeface typeface, int style) {
            boolean z = false;
            if (style > 0) {
                int typefaceStyle;
                if (typeface == null) {
                    typeface = Typeface.defaultFromStyle(style);
                } else {
                    typeface = Typeface.create(typeface, style);
                }
                textPaint.setTypeface(typeface);
                if (typeface != null) {
                    typefaceStyle = typeface.getStyle();
                } else {
                    typefaceStyle = 0;
                }
                int need = style & (typefaceStyle ^ -1);
                if ((need & 1) != 0) {
                    z = true;
                }
                textPaint.setFakeBoldText(z);
                textPaint.setTextSkewX((need & 2) != 0 ? -0.25f : 0.0f);
                return;
            }
            textPaint.setTypeface(typeface);
        }

        private Typeface setTypefaceFromAttrs(String familyName, int typefaceIndex, int styleIndex) {
            Typeface typeface = null;
            if (familyName != null) {
                typeface = Typeface.create(familyName, styleIndex);
                if (typeface != null) {
                    return typeface;
                }
            }
            switch (typefaceIndex) {
                case 1:
                    typeface = Typeface.SANS_SERIF;
                    break;
                case 2:
                    typeface = Typeface.SERIF;
                    break;
                case 3:
                    typeface = Typeface.MONOSPACE;
                    break;
            }
            return typeface;
        }

        Mode parseTintMode(int value, Mode defaultMode) {
            switch (value) {
                case 3:
                    return Mode.SRC_OVER;
                case 5:
                    return Mode.SRC_IN;
                case 9:
                    return Mode.SRC_ATOP;
                case 14:
                    return Mode.MULTIPLY;
                case 15:
                    return Mode.SCREEN;
                case 16:
                    if (VERSION.SDK_INT >= 11) {
                        return Mode.valueOf("ADD");
                    }
                    return defaultMode;
                default:
                    return defaultMode;
            }
        }

        @SuppressLint({"RtlHardcoded"})
        Alignment getTextAlignment(int gravity, String text) {
            int absoluteGravity;
            if (isVersionAfterJellyBeanMR1()) {
                int realGravity = gravity;
                int layoutDirection = getLayoutDirection();
                if (text != null && layoutDirection == 1 && new Bidi(text, -2).isRightToLeft()) {
                    if (gravity == GravityCompat.START) {
                        realGravity = GravityCompat.END;
                    } else if (gravity == GravityCompat.END) {
                        realGravity = GravityCompat.START;
                    }
                }
                absoluteGravity = Gravity.getAbsoluteGravity(realGravity, layoutDirection);
            } else if ((gravity & GravityCompat.START) == GravityCompat.START) {
                absoluteGravity = 3;
            } else if ((gravity & GravityCompat.END) == GravityCompat.END) {
                absoluteGravity = 5;
            } else {
                absoluteGravity = gravity & 7;
            }
            switch (absoluteGravity) {
                case 1:
                    return Alignment.ALIGN_CENTER;
                case 5:
                    return Alignment.ALIGN_OPPOSITE;
                default:
                    return Alignment.ALIGN_NORMAL;
            }
        }

        @TargetApi(17)
        int getLayoutDirection() {
            return this.mResourceFinder.getResources().getConfiguration().getLayoutDirection();
        }

        boolean isVersionAfterJellyBeanMR1() {
            return VERSION.SDK_INT >= 17;
        }
    }

    @Deprecated
    public interface OnHidePromptListener {
        @Deprecated
        void onHidePrompt(@Nullable MotionEvent motionEvent, boolean z);

        @Deprecated
        void onHidePromptComplete();
    }

    MaterialTapTargetPrompt(ResourceFinder resourceFinder) {
        this.mResourceFinder = resourceFinder;
        this.mView = new MaterialTapTargetPrompt$PromptView(this.mResourceFinder.getContext());
        this.mView.mPromptTouchedListener = new MaterialTapTargetPrompt$1(this);
        Rect rect = new Rect();
        this.mResourceFinder.getPromptParentView().getWindowVisibleDisplayFrame(rect);
        this.mStatusBarHeight = (float) rect.top;
        this.mGlobalLayoutListener = new MaterialTapTargetPrompt$2(this);
    }

    public void show() {
        this.mParentView.addView(this.mView);
        addGlobalLayoutListener();
        onPromptStateChanged(1);
        if (VERSION.SDK_INT >= 11) {
            startRevealAnimation();
            return;
        }
        this.mRevealedAmount = 1.0f;
        this.mView.mBackgroundRadius = this.mBaseBackgroundRadius;
        this.mView.mFocalRadius = this.mBaseFocalRadius;
        this.mView.mPaintFocal.setAlpha(this.mBaseFocalColourAlpha);
        this.mView.mPaintBackground.setAlpha(this.mBaseBackgroundColourAlpha);
        this.mPaintSecondaryText.setAlpha(this.mSecondaryTextColourAlpha);
        this.mPaintPrimaryText.setAlpha(this.mPrimaryTextColourAlpha);
        onPromptStateChanged(2);
    }

    void addGlobalLayoutListener() {
        ViewTreeObserver viewTreeObserver = this.mParentView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener);
        }
    }

    void removeGlobalLayoutListener() {
        ViewTreeObserver viewTreeObserver = this.mParentView.getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) {
            return;
        }
        if (VERSION.SDK_INT >= 16) {
            viewTreeObserver.removeOnGlobalLayoutListener(this.mGlobalLayoutListener);
        } else {
            viewTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
        }
    }

    public void finish() {
        if (VERSION.SDK_INT < 11) {
            cleanUpPrompt(4);
        } else if (!this.mDismissing && !this.mIsDismissingOld) {
            this.mDismissing = true;
            if (this.mAnimationCurrent != null) {
                this.mAnimationCurrent.removeAllListeners();
                this.mAnimationCurrent.cancel();
                this.mAnimationCurrent = null;
            }
            this.mAnimationCurrent = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
            this.mAnimationCurrent.setDuration(225);
            this.mAnimationCurrent.setInterpolator(this.mAnimationInterpolator);
            this.mAnimationCurrent.addUpdateListener(new MaterialTapTargetPrompt$3(this));
            this.mAnimationCurrent.addListener(new MaterialTapTargetPrompt$4(this));
            this.mAnimationCurrent.start();
        }
    }

    public void dismiss() {
        if (VERSION.SDK_INT < 11) {
            cleanUpPrompt(6);
        } else if (!this.mDismissing && !this.mIsDismissingOld) {
            this.mDismissing = true;
            if (this.mAnimationCurrent != null) {
                this.mAnimationCurrent.removeAllListeners();
                this.mAnimationCurrent.cancel();
                this.mAnimationCurrent = null;
            }
            this.mAnimationCurrent = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
            this.mAnimationCurrent.setDuration(225);
            this.mAnimationCurrent.setInterpolator(this.mAnimationInterpolator);
            this.mAnimationCurrent.addUpdateListener(new MaterialTapTargetPrompt$5(this));
            this.mAnimationCurrent.addListener(new MaterialTapTargetPrompt$6(this));
            this.mAnimationCurrent.start();
        }
    }

    void cleanUpPrompt(int state) {
        if (VERSION.SDK_INT >= 11 && this.mAnimationCurrent != null) {
            this.mAnimationCurrent.removeAllUpdateListeners();
            this.mAnimationCurrent = null;
        }
        removeGlobalLayoutListener();
        this.mParentView.removeView(this.mView);
        if (this.mDismissing || this.mIsDismissingOld) {
            onHidePromptComplete();
            onPromptStateChanged(state);
            this.mDismissing = false;
            this.mIsDismissingOld = false;
        }
    }

    @TargetApi(11)
    void startRevealAnimation() {
        if (this.mPaintSecondaryText != null) {
            this.mPaintSecondaryText.setAlpha(0);
        }
        if (this.mPaintPrimaryText != null) {
            this.mPaintPrimaryText.setAlpha(0);
        }
        this.mView.mPaintBackground.setAlpha(0);
        this.mView.mPaintFocal.setAlpha(0);
        this.mView.mFocalRadius = 0.0f;
        this.mView.mBackgroundRadius = 0.0f;
        this.mView.mBackgroundPosition.set(this.mView.mFocalCentre);
        if (this.mView.mIconDrawable != null) {
            this.mView.mIconDrawable.setAlpha(0);
        }
        this.mRevealedAmount = 0.0f;
        this.mAnimationCurrent = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mAnimationCurrent.setInterpolator(this.mAnimationInterpolator);
        this.mAnimationCurrent.setDuration(225);
        this.mAnimationCurrent.addUpdateListener(new MaterialTapTargetPrompt$7(this));
        this.mAnimationCurrent.addListener(new MaterialTapTargetPrompt$8(this));
        this.mAnimationCurrent.start();
    }

    @TargetApi(11)
    void startIdleAnimations() {
        if (this.mAnimationCurrent != null) {
            this.mAnimationCurrent.removeAllUpdateListeners();
            this.mAnimationCurrent.cancel();
            this.mAnimationCurrent = null;
        }
        this.mAnimationCurrent = ValueAnimator.ofFloat(new float[]{0.0f, this.mFocalRadius10Percent, 0.0f});
        this.mAnimationCurrent.setInterpolator(this.mAnimationInterpolator);
        this.mAnimationCurrent.setDuration(1000);
        this.mAnimationCurrent.setStartDelay(225);
        this.mAnimationCurrent.setRepeatCount(-1);
        this.mAnimationCurrent.addUpdateListener(new MaterialTapTargetPrompt$9(this));
        this.mAnimationCurrent.start();
        if (this.mAnimationFocalRipple != null) {
            this.mAnimationFocalRipple.removeAllUpdateListeners();
            this.mAnimationFocalRipple.cancel();
            this.mAnimationFocalRipple = null;
        }
        float baseRadius = this.mBaseFocalRadius + this.mFocalRadius10Percent;
        this.mAnimationFocalRipple = ValueAnimator.ofFloat(new float[]{baseRadius, (this.mFocalRadius10Percent * 6.0f) + baseRadius});
        this.mAnimationFocalRipple.setInterpolator(this.mAnimationInterpolator);
        this.mAnimationFocalRipple.setDuration(500);
        this.mAnimationFocalRipple.addUpdateListener(new MaterialTapTargetPrompt$10(this));
    }

    void updateFocalCentrePosition() {
        boolean z;
        boolean z2 = false;
        updateClipBounds();
        if (this.mTargetView != null) {
            int[] viewPosition = new int[2];
            this.mView.getLocationInWindow(viewPosition);
            int[] targetPosition = new int[2];
            this.mTargetView.getLocationInWindow(targetPosition);
            this.mView.mFocalCentre.x = (float) ((targetPosition[0] - viewPosition[0]) + (this.mTargetView.getWidth() / 2));
            this.mView.mFocalCentre.y = (float) ((targetPosition[1] - viewPosition[1]) + (this.mTargetView.getHeight() / 2));
        } else {
            this.mView.mFocalCentre.x = this.mTargetPosition.x;
            this.mView.mFocalCentre.y = this.mTargetPosition.y;
        }
        if (this.mView.mFocalCentre.y > ((float) this.mView.mClipBounds.centerY())) {
            z = true;
        } else {
            z = false;
        }
        this.mVerticalTextPositionAbove = z;
        if (this.mView.mFocalCentre.x > ((float) this.mView.mClipBounds.centerX())) {
            z = true;
        } else {
            z = false;
        }
        this.mHorizontalTextPositionLeft = z;
        if ((this.mView.mFocalCentre.x > this.mClipViewBoundsInset88dp.left && this.mView.mFocalCentre.x < this.mClipViewBoundsInset88dp.right) || (this.mView.mFocalCentre.y > this.mClipViewBoundsInset88dp.top && this.mView.mFocalCentre.y < this.mClipViewBoundsInset88dp.bottom)) {
            z2 = true;
        }
        this.mInside88dpBounds = z2;
        updateTextPositioning();
        updateIconPosition();
    }

    void updateTextPositioning() {
        float maxWidth = Math.max(80.0f, Math.min(this.mMaxTextWidth, ((float) (this.mView.mClipToBounds ? this.mView.mClipBounds.right - this.mView.mClipBounds.left : this.mParentView.getWidth())) - (this.mTextPadding * 2.0f)));
        if (this.mPrimaryText != null) {
            this.mView.mPrimaryTextLayout = createStaticTextLayout(this.mPrimaryText, this.mPaintPrimaryText, (int) maxWidth, this.mPrimaryTextAlignment);
        } else {
            this.mView.mPrimaryTextLayout = null;
        }
        if (this.mSecondaryText != null) {
            this.mView.mSecondaryTextLayout = createStaticTextLayout(this.mSecondaryText, this.mPaintSecondaryText, (int) maxWidth, this.mSecondaryTextAlignment);
        } else {
            this.mView.mSecondaryTextLayout = null;
        }
        float textWidth = Math.max(calculateMaxTextWidth(this.mView.mPrimaryTextLayout), calculateMaxTextWidth(this.mView.mSecondaryTextLayout));
        if (this.mInside88dpBounds) {
            this.mView.mPrimaryTextLeft = (float) this.mView.mClipBounds.left;
            float width = Math.min(textWidth, maxWidth);
            if (this.mHorizontalTextPositionLeft) {
                this.mView.mPrimaryTextLeft = (this.mView.mFocalCentre.x - width) + this.mFocalToTextPadding;
            } else {
                this.mView.mPrimaryTextLeft = (this.mView.mFocalCentre.x - width) - this.mFocalToTextPadding;
            }
            if (this.mView.mPrimaryTextLeft < ((float) this.mView.mClipBounds.left) + this.mTextPadding) {
                this.mView.mPrimaryTextLeft = ((float) this.mView.mClipBounds.left) + this.mTextPadding;
            }
            if (this.mView.mPrimaryTextLeft + width > ((float) this.mView.mClipBounds.right) - this.mTextPadding) {
                this.mView.mPrimaryTextLeft = (((float) this.mView.mClipBounds.right) - this.mTextPadding) - width;
            }
        } else if (this.mHorizontalTextPositionLeft) {
            this.mView.mPrimaryTextLeft = (((float) (this.mView.mClipToBounds ? this.mView.mClipBounds.right : this.mParentView.getRight())) - this.mTextPadding) - textWidth;
        } else {
            this.mView.mPrimaryTextLeft = ((float) (this.mView.mClipToBounds ? this.mView.mClipBounds.left : this.mParentView.getLeft())) + this.mTextPadding;
        }
        this.mView.mPrimaryTextTop = this.mView.mFocalCentre.y;
        MaterialTapTargetPrompt$PromptView materialTapTargetPrompt$PromptView;
        if (this.mVerticalTextPositionAbove) {
            this.mView.mPrimaryTextTop = (this.mView.mPrimaryTextTop - this.mBaseFocalRadius) - this.mFocalToTextPadding;
            if (this.mView.mPrimaryTextLayout != null) {
                materialTapTargetPrompt$PromptView = this.mView;
                materialTapTargetPrompt$PromptView.mPrimaryTextTop -= (float) this.mView.mPrimaryTextLayout.getHeight();
            }
        } else {
            materialTapTargetPrompt$PromptView = this.mView;
            materialTapTargetPrompt$PromptView.mPrimaryTextTop += this.mBaseFocalRadius + this.mFocalToTextPadding;
        }
        if (this.mSecondaryText != null) {
            if (this.mVerticalTextPositionAbove) {
                this.mView.mPrimaryTextTop = (this.mView.mPrimaryTextTop - this.mView.mTextSeparation) - ((float) this.mView.mSecondaryTextLayout.getHeight());
            }
            if (this.mView.mPrimaryTextLayout != null) {
                this.mView.mSecondaryTextOffsetTop = ((float) this.mView.mPrimaryTextLayout.getHeight()) + this.mView.mTextSeparation;
            }
        }
        updateBackgroundRadius(textWidth);
        this.mView.mSecondaryTextLeft = this.mView.mPrimaryTextLeft;
        this.mView.mPrimaryTextLeftChange = 0.0f;
        this.mView.mSecondaryTextLeftChange = 0.0f;
        float change = maxWidth - textWidth;
        if (isRtlText(this.mView.mPrimaryTextLayout)) {
            this.mView.mPrimaryTextLeftChange = change;
        }
        if (isRtlText(this.mView.mSecondaryTextLayout)) {
            this.mView.mSecondaryTextLeftChange = change;
        }
    }

    private boolean isRtlText(Layout layout) {
        if (layout == null) {
            return false;
        }
        boolean result = layout.getAlignment() == Alignment.ALIGN_OPPOSITE;
        if (VERSION.SDK_INT < 14) {
            return result;
        }
        boolean textIsRtl = layout.isRtlCharAt(0);
        if ((!(result && textIsRtl) && (result || textIsRtl)) || textIsRtl) {
            result = true;
        } else {
            result = false;
        }
        if (result || layout.getAlignment() != Alignment.ALIGN_NORMAL || VERSION.SDK_INT < 17) {
            if (layout.getAlignment() == Alignment.ALIGN_OPPOSITE && textIsRtl) {
                return false;
            }
            return result;
        } else if (textIsRtl && this.mResourceFinder.getResources().getConfiguration().getLayoutDirection() == 1) {
            return true;
        } else {
            return false;
        }
    }

    private StaticLayout createStaticTextLayout(String text, TextPaint paint, int maxTextWidth, Alignment textAlignment) {
        if (VERSION.SDK_INT < 23) {
            return new StaticLayout(text, paint, maxTextWidth, textAlignment, 1.0f, 0.0f, false);
        }
        android.text.StaticLayout.Builder builder = android.text.StaticLayout.Builder.obtain(text, 0, text.length(), paint, maxTextWidth);
        builder.setAlignment(textAlignment);
        return builder.build();
    }

    float calculateMaxTextWidth(Layout textLayout) {
        float maxTextWidth = 0.0f;
        if (textLayout != null) {
            int count = textLayout.getLineCount();
            for (int i = 0; i < count; i++) {
                maxTextWidth = Math.max(maxTextWidth, textLayout.getLineWidth(i));
            }
        }
        return maxTextWidth;
    }

    void updateBackgroundRadius(float maxTextWidth) {
        if (this.mInside88dpBounds) {
            float y1;
            float y2;
            float x1 = this.mView.mFocalCentre.x;
            float x2 = this.mView.mPrimaryTextLeft - this.mTextPadding;
            if (this.mVerticalTextPositionAbove) {
                y1 = (this.mView.mFocalCentre.y + this.mBaseFocalRadius) + this.mTextPadding;
                y2 = this.mView.mPrimaryTextTop;
            } else {
                y1 = this.mView.mFocalCentre.y - ((this.mBaseFocalRadius + this.mFocalToTextPadding) + this.mTextPadding);
                float baseY2 = this.mView.mPrimaryTextTop + ((float) this.mView.mPrimaryTextLayout.getHeight());
                if (this.mView.mSecondaryTextLayout != null) {
                    baseY2 += ((float) this.mView.mSecondaryTextLayout.getHeight()) + this.mView.mTextSeparation;
                }
                y2 = baseY2;
            }
            float y3 = y2;
            float x3 = ((x2 + maxTextWidth) + this.mTextPadding) + this.mTextPadding;
            float focalLeft = (this.mView.mFocalCentre.x - this.mBaseFocalRadius) - this.mFocalToTextPadding;
            float focalRight = (this.mView.mFocalCentre.x + this.mBaseFocalRadius) + this.mFocalToTextPadding;
            if (x2 <= focalLeft || x2 >= focalRight) {
                if (x3 > focalLeft && x3 < focalRight) {
                    if (this.mVerticalTextPositionAbove) {
                        x1 += this.mBaseFocalRadius + this.mFocalToTextPadding;
                    } else {
                        x3 += this.mBaseFocalRadius + this.mFocalToTextPadding;
                    }
                }
            } else if (this.mVerticalTextPositionAbove) {
                x1 -= this.mBaseFocalRadius - this.mFocalToTextPadding;
            } else {
                x2 -= this.mBaseFocalRadius - this.mFocalToTextPadding;
            }
            double offset = Math.pow((double) x2, 2.0d) + Math.pow((double) y2, 2.0d);
            double bc = ((Math.pow((double) x1, 2.0d) + Math.pow((double) y1, 2.0d)) - offset) / 2.0d;
            double cd = ((offset - Math.pow((double) x3, 2.0d)) - Math.pow((double) y3, 2.0d)) / 2.0d;
            double idet = 1.0d / ((double) (((x1 - x2) * (y2 - y3)) - ((x2 - x3) * (y1 - y2))));
            this.mBaseBackgroundPosition.set((float) (((((double) (y2 - y3)) * bc) - (((double) (y1 - y2)) * cd)) * idet), (float) (((((double) (x1 - x2)) * cd) - (((double) (x2 - x3)) * bc)) * idet));
            this.mBaseBackgroundRadius = (float) Math.sqrt(Math.pow((double) (x2 - this.mBaseBackgroundPosition.x), 2.0d) + Math.pow((double) (y2 - this.mBaseBackgroundPosition.y), 2.0d));
        } else {
            this.mBaseBackgroundPosition.set(this.mView.mFocalCentre.x, this.mView.mFocalCentre.y);
            float f = this.mView.mPrimaryTextLeft;
            if (this.mHorizontalTextPositionLeft) {
                maxTextWidth = 0.0f;
            }
            float length = Math.abs((f + maxTextWidth) - this.mView.mFocalCentre.x) + this.mTextPadding;
            float height = this.mBaseFocalRadius + this.mFocalToTextPadding;
            if (this.mView.mPrimaryTextLayout != null) {
                height += (float) this.mView.mPrimaryTextLayout.getHeight();
            }
            if (this.mView.mSecondaryTextLayout != null) {
                height += ((float) this.mView.mSecondaryTextLayout.getHeight()) + this.mView.mTextSeparation;
            }
            this.mBaseBackgroundRadius = (float) Math.sqrt(Math.pow((double) length, 2.0d) + Math.pow((double) height, 2.0d));
        }
        this.mView.mBackgroundPosition.set(this.mBaseBackgroundPosition);
        this.mView.mBackgroundRadius = this.mBaseBackgroundRadius * this.mRevealedAmount;
    }

    void updateIconPosition() {
        if (this.mView.mIconDrawable != null) {
            this.mView.mIconDrawableLeft = this.mView.mFocalCentre.x - ((float) (this.mView.mIconDrawable.getIntrinsicWidth() / 2));
            this.mView.mIconDrawableTop = this.mView.mFocalCentre.y - ((float) (this.mView.mIconDrawable.getIntrinsicHeight() / 2));
        } else if (this.mView.mTargetRenderView != null) {
            int[] viewPosition = new int[2];
            this.mView.getLocationInWindow(viewPosition);
            int[] targetPosition = new int[2];
            this.mView.mTargetRenderView.getLocationInWindow(targetPosition);
            this.mView.mIconDrawableLeft = (float) (targetPosition[0] - viewPosition[0]);
            this.mView.mIconDrawableTop = (float) (targetPosition[1] - viewPosition[1]);
        }
    }

    void updateClipBounds() {
        if (this.mClipToView != null) {
            this.mView.mClipToBounds = true;
            this.mView.mClipBounds.set(0, 0, 0, 0);
            Point offset = new Point();
            this.mClipToView.getGlobalVisibleRect(this.mView.mClipBounds, offset);
            if (offset.y == 0) {
                Rect rect = this.mView.mClipBounds;
                rect.top = (int) (((float) rect.top) + this.mStatusBarHeight);
            }
            this.mClipViewBoundsInset88dp = new RectF(this.mView.mClipBounds);
            this.mClipViewBoundsInset88dp.inset(this.m88dp, this.m88dp);
            return;
        }
        View contentView = this.mResourceFinder.findViewById(16908290);
        if (contentView != null) {
            contentView.getGlobalVisibleRect(this.mView.mClipBounds, new Point());
            this.mClipViewBoundsInset88dp = new RectF(this.mView.mClipBounds);
            this.mClipViewBoundsInset88dp.inset(this.m88dp, this.m88dp);
        }
        this.mView.mClipToBounds = false;
    }

    protected void onPromptStateChanged(int state) {
        if (this.mPromptStateChangeListener != null) {
            this.mPromptStateChangeListener.onPromptStateChanged(this, state);
        }
    }

    @Deprecated
    protected void onHidePrompt(@Nullable MotionEvent event, boolean targetTapped) {
        if (this.mOnHidePromptListener != null) {
            this.mOnHidePromptListener.onHidePrompt(event, targetTapped);
        }
    }

    @Deprecated
    protected void onHidePromptComplete() {
        if (this.mOnHidePromptListener != null) {
            this.mOnHidePromptListener.onHidePromptComplete();
        }
    }
}
