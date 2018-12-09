package org.telegram.ui.ActionBar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.LineProgressView;
import org.telegram.ui.Components.RadialProgressView;

public class AlertDialog extends Dialog implements Callback {
    private Rect backgroundPaddings = new Rect();
    private FrameLayout buttonsLayout;
    private ScrollView contentScrollView;
    private int currentProgress;
    private View customView;
    private int[] itemIcons;
    private CharSequence[] items;
    private int lastScreenWidth;
    private LineProgressView lineProgressView;
    private TextView lineProgressViewPercent;
    private CharSequence message;
    private TextView messageTextView;
    private OnClickListener negativeButtonListener;
    private CharSequence negativeButtonText;
    private OnClickListener neutralButtonListener;
    private CharSequence neutralButtonText;
    private OnClickListener onBackButtonListener;
    private OnClickListener onClickListener;
    private OnDismissListener onDismissListener;
    private OnScrollChangedListener onScrollChangedListener;
    private OnClickListener positiveButtonListener;
    private CharSequence positiveButtonText;
    private FrameLayout progressViewContainer;
    private int progressViewStyle;
    private TextView progressViewTextView;
    private LinearLayout scrollContainer;
    private BitmapDrawable[] shadow = new BitmapDrawable[2];
    private AnimatorSet[] shadowAnimation = new AnimatorSet[2];
    private Drawable shadowDrawable;
    private boolean[] shadowVisibility = new boolean[2];
    private CharSequence subtitle;
    private TextView subtitleTextView;
    private CharSequence title;
    private TextView titleTextView;
    private int topBackgroundColor;
    private Drawable topDrawable;
    private ImageView topImageView;
    private int topResId;

    /* renamed from: org.telegram.ui.ActionBar.AlertDialog$3 */
    class C38233 implements View.OnClickListener {
        C38233() {
        }

        public void onClick(View view) {
            if (AlertDialog.this.onClickListener != null) {
                AlertDialog.this.onClickListener.onClick(AlertDialog.this, ((Integer) view.getTag()).intValue());
            }
            AlertDialog.this.dismiss();
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.AlertDialog$6 */
    class C38266 implements View.OnClickListener {
        C38266() {
        }

        public void onClick(View view) {
            if (AlertDialog.this.positiveButtonListener != null) {
                AlertDialog.this.positiveButtonListener.onClick(AlertDialog.this, -1);
            }
            AlertDialog.this.dismiss();
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.AlertDialog$8 */
    class C38288 implements View.OnClickListener {
        C38288() {
        }

        public void onClick(View view) {
            if (AlertDialog.this.negativeButtonListener != null) {
                AlertDialog.this.negativeButtonListener.onClick(AlertDialog.this, -2);
            }
            AlertDialog.this.cancel();
        }
    }

    public static class AlertDialogCell extends FrameLayout {
        private ImageView imageView;
        private TextView textView;

        public AlertDialogCell(Context context) {
            int i = 3;
            super(context);
            setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 2));
            setPadding(AndroidUtilities.dp(23.0f), 0, AndroidUtilities.dp(23.0f), 0);
            this.imageView = new ImageView(context);
            this.imageView.setScaleType(ScaleType.CENTER);
            this.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogIcon), Mode.MULTIPLY));
            addView(this.imageView, LayoutHelper.createFrame(24, 24, (LocaleController.isRTL ? 5 : 3) | 16));
            this.textView = new TextView(context);
            this.textView.setLines(1);
            this.textView.setSingleLine(true);
            this.textView.setGravity(1);
            this.textView.setEllipsize(TruncateAt.END);
            this.textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
            this.textView.setTextSize(1, 16.0f);
            View view = this.textView;
            if (LocaleController.isRTL) {
                i = 5;
            }
            addView(view, LayoutHelper.createFrame(-2, -2, i | 16));
        }

        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
        }

        public void setGravity(int i) {
            this.textView.setGravity(i);
        }

        public void setTextAndIcon(CharSequence charSequence, int i) {
            this.textView.setText(charSequence);
            if (i != 0) {
                this.imageView.setImageResource(i);
                this.imageView.setVisibility(0);
                this.textView.setPadding(LocaleController.isRTL ? 0 : AndroidUtilities.dp(56.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(56.0f) : 0, 0);
                return;
            }
            this.imageView.setVisibility(4);
            this.textView.setPadding(0, 0, 0, 0);
        }

        public void setTextColor(int i) {
            this.textView.setTextColor(i);
        }
    }

    public static class Builder {
        private AlertDialog alertDialog;

        public Builder(Context context) {
            this.alertDialog = new AlertDialog(context, 0);
        }

        public Builder(Context context, int i) {
            this.alertDialog = new AlertDialog(context, i);
        }

        public AlertDialog create() {
            return this.alertDialog;
        }

        public Context getContext() {
            return this.alertDialog.getContext();
        }

        public Builder setItems(CharSequence[] charSequenceArr, OnClickListener onClickListener) {
            this.alertDialog.items = charSequenceArr;
            this.alertDialog.onClickListener = onClickListener;
            return this;
        }

        public Builder setItems(CharSequence[] charSequenceArr, int[] iArr, OnClickListener onClickListener) {
            this.alertDialog.items = charSequenceArr;
            this.alertDialog.itemIcons = iArr;
            this.alertDialog.onClickListener = onClickListener;
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            this.alertDialog.message = charSequence;
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, OnClickListener onClickListener) {
            this.alertDialog.negativeButtonText = charSequence;
            this.alertDialog.negativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(CharSequence charSequence, OnClickListener onClickListener) {
            this.alertDialog.neutralButtonText = charSequence;
            this.alertDialog.neutralButtonListener = onClickListener;
            return this;
        }

        public Builder setOnBackButtonListener(OnClickListener onClickListener) {
            this.alertDialog.onBackButtonListener = onClickListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            this.alertDialog.setOnDismissListener(onDismissListener);
            return this;
        }

        public Builder setPositiveButton(CharSequence charSequence, OnClickListener onClickListener) {
            this.alertDialog.positiveButtonText = charSequence;
            this.alertDialog.positiveButtonListener = onClickListener;
            return this;
        }

        public Builder setSubtitle(CharSequence charSequence) {
            this.alertDialog.subtitle = charSequence;
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.alertDialog.title = charSequence;
            return this;
        }

        public Builder setTopImage(int i, int i2) {
            this.alertDialog.topResId = i;
            this.alertDialog.topBackgroundColor = i2;
            return this;
        }

        public Builder setTopImage(Drawable drawable, int i) {
            this.alertDialog.topDrawable = drawable;
            this.alertDialog.topBackgroundColor = i;
            return this;
        }

        public Builder setView(View view) {
            this.alertDialog.customView = view;
            return this;
        }

        public AlertDialog show() {
            this.alertDialog.show();
            return this.alertDialog;
        }
    }

    public AlertDialog(Context context, int i) {
        super(context, R.style.TransparentDialog);
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.popup_fixed_alert).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogBackground), Mode.MULTIPLY));
        this.shadowDrawable.getPadding(this.backgroundPaddings);
        this.progressViewStyle = i;
    }

    private boolean canTextInput(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (canTextInput(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    private void runShadowAnimation(final int i, boolean z) {
        if ((z && !this.shadowVisibility[i]) || (!z && this.shadowVisibility[i])) {
            this.shadowVisibility[i] = z;
            if (this.shadowAnimation[i] != null) {
                this.shadowAnimation[i].cancel();
            }
            this.shadowAnimation[i] = new AnimatorSet();
            if (this.shadow[i] != null) {
                AnimatorSet animatorSet = this.shadowAnimation[i];
                Animator[] animatorArr = new Animator[1];
                Object obj = this.shadow[i];
                String str = "alpha";
                int[] iArr = new int[1];
                iArr[0] = z ? 255 : 0;
                animatorArr[0] = ObjectAnimator.ofInt(obj, str, iArr);
                animatorSet.playTogether(animatorArr);
            }
            this.shadowAnimation[i].setDuration(150);
            this.shadowAnimation[i].addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    if (AlertDialog.this.shadowAnimation[i] != null && AlertDialog.this.shadowAnimation[i].equals(animator)) {
                        AlertDialog.this.shadowAnimation[i] = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (AlertDialog.this.shadowAnimation[i] != null && AlertDialog.this.shadowAnimation[i].equals(animator)) {
                        AlertDialog.this.shadowAnimation[i] = null;
                    }
                }
            });
            try {
                this.shadowAnimation[i].start();
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    private void updateLineProgressTextView() {
        this.lineProgressViewPercent.setText(String.format("%d%%", new Object[]{Integer.valueOf(this.currentProgress)}));
    }

    public void dismiss() {
        super.dismiss();
    }

    public View getButton(int i) {
        return this.buttonsLayout.findViewWithTag(Integer.valueOf(i));
    }

    public void invalidateDrawable(Drawable drawable) {
        this.contentScrollView.invalidate();
        this.scrollContainer.invalidate();
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.onBackButtonListener != null) {
            this.onBackButtonListener.onClick(this, -2);
        }
    }

    protected void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        View c38211 = new LinearLayout(getContext()) {
            private boolean inLayout;

            /* renamed from: org.telegram.ui.ActionBar.AlertDialog$1$1 */
            class C38191 implements Runnable {
                C38191() {
                }

                public void run() {
                    AlertDialog.this.lastScreenWidth = AndroidUtilities.displaySize.x;
                    int dp = AndroidUtilities.displaySize.x - AndroidUtilities.dp(56.0f);
                    int dp2 = AndroidUtilities.isTablet() ? AndroidUtilities.isSmallTablet() ? AndroidUtilities.dp(446.0f) : AndroidUtilities.dp(496.0f) : AndroidUtilities.dp(356.0f);
                    Window window = AlertDialog.this.getWindow();
                    LayoutParams layoutParams = new LayoutParams();
                    layoutParams.copyFrom(window.getAttributes());
                    layoutParams.width = (Math.min(dp2, dp) + AlertDialog.this.backgroundPaddings.left) + AlertDialog.this.backgroundPaddings.right;
                    window.setAttributes(layoutParams);
                }
            }

            /* renamed from: org.telegram.ui.ActionBar.AlertDialog$1$2 */
            class C38202 implements OnScrollChangedListener {
                C38202() {
                }

                public void onScrollChanged() {
                    boolean z = false;
                    AlertDialog alertDialog = AlertDialog.this;
                    boolean z2 = AlertDialog.this.titleTextView != null && AlertDialog.this.contentScrollView.getScrollY() > AlertDialog.this.scrollContainer.getTop();
                    alertDialog.runShadowAnimation(0, z2);
                    AlertDialog alertDialog2 = AlertDialog.this;
                    if (AlertDialog.this.buttonsLayout != null && AlertDialog.this.contentScrollView.getScrollY() + AlertDialog.this.contentScrollView.getHeight() < AlertDialog.this.scrollContainer.getBottom()) {
                        z = true;
                    }
                    alertDialog2.runShadowAnimation(1, z);
                    AlertDialog.this.contentScrollView.invalidate();
                }
            }

            public boolean hasOverlappingRendering() {
                return false;
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                if (AlertDialog.this.contentScrollView != null) {
                    if (AlertDialog.this.onScrollChangedListener == null) {
                        AlertDialog.this.onScrollChangedListener = new C38202();
                        AlertDialog.this.contentScrollView.getViewTreeObserver().addOnScrollChangedListener(AlertDialog.this.onScrollChangedListener);
                    }
                    AlertDialog.this.onScrollChangedListener.onScrollChanged();
                }
            }

            protected void onMeasure(int i, int i2) {
                int i3;
                this.inLayout = true;
                int size = MeasureSpec.getSize(i);
                int size2 = (MeasureSpec.getSize(i2) - getPaddingTop()) - getPaddingBottom();
                int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(paddingLeft - AndroidUtilities.dp(48.0f), 1073741824);
                int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824);
                if (AlertDialog.this.buttonsLayout != null) {
                    int childCount = AlertDialog.this.buttonsLayout.getChildCount();
                    for (i3 = 0; i3 < childCount; i3++) {
                        ((TextView) AlertDialog.this.buttonsLayout.getChildAt(i3)).setMaxWidth(AndroidUtilities.dp((float) ((paddingLeft - AndroidUtilities.dp(24.0f)) / 2)));
                    }
                    AlertDialog.this.buttonsLayout.measure(makeMeasureSpec2, i2);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) AlertDialog.this.buttonsLayout.getLayoutParams();
                    i3 = size2 - (layoutParams.topMargin + (AlertDialog.this.buttonsLayout.getMeasuredHeight() + layoutParams.bottomMargin));
                } else {
                    i3 = size2;
                }
                if (AlertDialog.this.titleTextView != null) {
                    AlertDialog.this.titleTextView.measure(makeMeasureSpec, i2);
                    layoutParams = (LinearLayout.LayoutParams) AlertDialog.this.titleTextView.getLayoutParams();
                    i3 -= layoutParams.topMargin + (AlertDialog.this.titleTextView.getMeasuredHeight() + layoutParams.bottomMargin);
                }
                if (AlertDialog.this.subtitleTextView != null) {
                    AlertDialog.this.subtitleTextView.measure(makeMeasureSpec, i2);
                    layoutParams = (LinearLayout.LayoutParams) AlertDialog.this.subtitleTextView.getLayoutParams();
                    i3 -= layoutParams.topMargin + (AlertDialog.this.subtitleTextView.getMeasuredHeight() + layoutParams.bottomMargin);
                }
                if (AlertDialog.this.topImageView != null) {
                    AlertDialog.this.topImageView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(132.0f), 1073741824));
                    i3 -= AlertDialog.this.topImageView.getMeasuredHeight() - AndroidUtilities.dp(8.0f);
                }
                if (AlertDialog.this.progressViewStyle == 0) {
                    layoutParams = (LinearLayout.LayoutParams) AlertDialog.this.contentScrollView.getLayoutParams();
                    if (AlertDialog.this.customView != null) {
                        paddingLeft = (AlertDialog.this.titleTextView == null && AlertDialog.this.messageTextView.getVisibility() == 8 && AlertDialog.this.items == null) ? AndroidUtilities.dp(16.0f) : 0;
                        layoutParams.topMargin = paddingLeft;
                        layoutParams.bottomMargin = AlertDialog.this.buttonsLayout == null ? AndroidUtilities.dp(8.0f) : 0;
                    } else if (AlertDialog.this.items != null) {
                        paddingLeft = (AlertDialog.this.titleTextView == null && AlertDialog.this.messageTextView.getVisibility() == 8) ? AndroidUtilities.dp(8.0f) : 0;
                        layoutParams.topMargin = paddingLeft;
                        layoutParams.bottomMargin = AndroidUtilities.dp(8.0f);
                    } else if (AlertDialog.this.messageTextView.getVisibility() == 0) {
                        layoutParams.topMargin = AlertDialog.this.titleTextView == null ? AndroidUtilities.dp(19.0f) : 0;
                        layoutParams.bottomMargin = AndroidUtilities.dp(20.0f);
                    }
                    int i4 = i3 - (layoutParams.topMargin + layoutParams.bottomMargin);
                    AlertDialog.this.contentScrollView.measure(makeMeasureSpec2, MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE));
                    i3 = i4 - AlertDialog.this.contentScrollView.getMeasuredHeight();
                } else {
                    if (AlertDialog.this.progressViewContainer != null) {
                        AlertDialog.this.progressViewContainer.measure(makeMeasureSpec, MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE));
                        layoutParams = (LinearLayout.LayoutParams) AlertDialog.this.progressViewContainer.getLayoutParams();
                        i3 -= layoutParams.topMargin + (AlertDialog.this.progressViewContainer.getMeasuredHeight() + layoutParams.bottomMargin);
                    } else if (AlertDialog.this.messageTextView != null) {
                        AlertDialog.this.messageTextView.measure(makeMeasureSpec, MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE));
                        if (AlertDialog.this.messageTextView.getVisibility() != 8) {
                            layoutParams = (LinearLayout.LayoutParams) AlertDialog.this.messageTextView.getLayoutParams();
                            i3 -= layoutParams.topMargin + (AlertDialog.this.messageTextView.getMeasuredHeight() + layoutParams.bottomMargin);
                        }
                    }
                    if (AlertDialog.this.lineProgressView != null) {
                        AlertDialog.this.lineProgressView.measure(makeMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(4.0f), 1073741824));
                        layoutParams = (LinearLayout.LayoutParams) AlertDialog.this.lineProgressView.getLayoutParams();
                        i3 -= layoutParams.topMargin + (AlertDialog.this.lineProgressView.getMeasuredHeight() + layoutParams.bottomMargin);
                        AlertDialog.this.lineProgressViewPercent.measure(makeMeasureSpec, MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE));
                        layoutParams = (LinearLayout.LayoutParams) AlertDialog.this.lineProgressViewPercent.getLayoutParams();
                        i3 -= layoutParams.topMargin + (AlertDialog.this.lineProgressViewPercent.getMeasuredHeight() + layoutParams.bottomMargin);
                    }
                }
                setMeasuredDimension(size, ((size2 - i3) + getPaddingTop()) + getPaddingBottom());
                this.inLayout = false;
                if (AlertDialog.this.lastScreenWidth != AndroidUtilities.displaySize.x) {
                    AndroidUtilities.runOnUIThread(new C38191());
                }
            }

            public void requestLayout() {
                if (!this.inLayout) {
                    super.requestLayout();
                }
            }
        };
        c38211.setOrientation(1);
        c38211.setBackgroundDrawable(this.shadowDrawable);
        c38211.setFitsSystemWindows(VERSION.SDK_INT >= 21);
        setContentView(c38211);
        Object obj = (this.positiveButtonText == null && this.negativeButtonText == null && this.neutralButtonText == null) ? null : 1;
        if (!(this.topResId == 0 && this.topDrawable == null)) {
            this.topImageView = new ImageView(getContext());
            if (this.topDrawable != null) {
                this.topImageView.setImageDrawable(this.topDrawable);
            } else {
                this.topImageView.setImageResource(this.topResId);
            }
            this.topImageView.setScaleType(ScaleType.CENTER);
            this.topImageView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.popup_fixed_top));
            this.topImageView.getBackground().setColorFilter(new PorterDuffColorFilter(this.topBackgroundColor, Mode.MULTIPLY));
            this.topImageView.setPadding(0, 0, 0, 0);
            c38211.addView(this.topImageView, LayoutHelper.createLinear(-1, 132, (LocaleController.isRTL ? 5 : 3) | 48, -8, -8, 0, 0));
        }
        if (this.title != null) {
            this.titleTextView = new TextView(getContext());
            this.titleTextView.setText(this.title);
            this.titleTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
            this.titleTextView.setTextSize(1, 20.0f);
            this.titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.titleTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
            View view = this.titleTextView;
            int i2 = (LocaleController.isRTL ? 5 : 3) | 48;
            int i3 = this.subtitle != null ? 2 : this.items != null ? 14 : 10;
            c38211.addView(view, LayoutHelper.createLinear(-2, -2, i2, 24, 19, 24, i3));
        }
        if (this.subtitle != null) {
            this.subtitleTextView = new TextView(getContext());
            this.subtitleTextView.setText(this.subtitle);
            this.subtitleTextView.setTextColor(Theme.getColor(Theme.key_dialogIcon));
            this.subtitleTextView.setTextSize(1, 14.0f);
            this.subtitleTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
            c38211.addView(this.subtitleTextView, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 24, 0, 24, this.items != null ? 14 : 10));
        }
        if (this.progressViewStyle == 0) {
            this.shadow[0] = (BitmapDrawable) getContext().getResources().getDrawable(R.drawable.header_shadow).mutate();
            this.shadow[1] = (BitmapDrawable) getContext().getResources().getDrawable(R.drawable.header_shadow_reverse).mutate();
            this.shadow[0].setAlpha(0);
            this.shadow[1].setAlpha(0);
            this.shadow[0].setCallback(this);
            this.shadow[1].setCallback(this);
            this.contentScrollView = new ScrollView(getContext()) {
                protected boolean drawChild(Canvas canvas, View view, long j) {
                    boolean drawChild = super.drawChild(canvas, view, j);
                    if (AlertDialog.this.shadow[0].getPaint().getAlpha() != 0) {
                        AlertDialog.this.shadow[0].setBounds(0, getScrollY(), getMeasuredWidth(), getScrollY() + AndroidUtilities.dp(3.0f));
                        AlertDialog.this.shadow[0].draw(canvas);
                    }
                    if (AlertDialog.this.shadow[1].getPaint().getAlpha() != 0) {
                        AlertDialog.this.shadow[1].setBounds(0, (getScrollY() + getMeasuredHeight()) - AndroidUtilities.dp(3.0f), getMeasuredWidth(), getScrollY() + getMeasuredHeight());
                        AlertDialog.this.shadow[1].draw(canvas);
                    }
                    return drawChild;
                }
            };
            this.contentScrollView.setVerticalScrollBarEnabled(false);
            AndroidUtilities.setScrollViewEdgeEffectColor(this.contentScrollView, Theme.getColor(Theme.key_dialogScrollGlow));
            c38211.addView(this.contentScrollView, LayoutHelper.createLinear(-1, -2, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.scrollContainer = new LinearLayout(getContext());
            this.scrollContainer.setOrientation(1);
            this.contentScrollView.addView(this.scrollContainer, new FrameLayout.LayoutParams(-1, -2));
        }
        this.messageTextView = new TextView(getContext());
        this.messageTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.messageTextView.setTextSize(1, 16.0f);
        this.messageTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        if (this.progressViewStyle == 1) {
            this.progressViewContainer = new FrameLayout(getContext());
            c38211.addView(this.progressViewContainer, LayoutHelper.createLinear(-1, 44, 51, 23, this.title == null ? 24 : 0, 23, 24));
            View radialProgressView = new RadialProgressView(getContext());
            radialProgressView.setProgressColor(Theme.getColor(Theme.key_dialogProgressCircle));
            this.progressViewContainer.addView(radialProgressView, LayoutHelper.createFrame(44, 44, (LocaleController.isRTL ? 5 : 3) | 48));
            this.messageTextView.setLines(1);
            this.messageTextView.setSingleLine(true);
            this.messageTextView.setEllipsize(TruncateAt.END);
            this.progressViewContainer.addView(this.messageTextView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 16, (float) (LocaleController.isRTL ? 0 : 62), BitmapDescriptorFactory.HUE_RED, (float) (LocaleController.isRTL ? 62 : 0), BitmapDescriptorFactory.HUE_RED));
        } else if (this.progressViewStyle == 2) {
            c38211.addView(this.messageTextView, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 24, this.title == null ? 19 : 0, 24, 20));
            this.lineProgressView = new LineProgressView(getContext());
            this.lineProgressView.setProgress(((float) this.currentProgress) / 100.0f, false);
            this.lineProgressView.setProgressColor(Theme.getColor(Theme.key_dialogLineProgress));
            this.lineProgressView.setBackColor(Theme.getColor(Theme.key_dialogLineProgressBackground));
            c38211.addView(this.lineProgressView, LayoutHelper.createLinear(-1, 4, 19, 24, 0, 24, 0));
            this.lineProgressViewPercent = new TextView(getContext());
            this.lineProgressViewPercent.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.lineProgressViewPercent.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
            this.lineProgressViewPercent.setTextColor(Theme.getColor(Theme.key_dialogTextGray2));
            this.lineProgressViewPercent.setTextSize(1, 14.0f);
            c38211.addView(this.lineProgressViewPercent, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 23, 4, 23, 24));
            updateLineProgressTextView();
        } else {
            LinearLayout linearLayout = this.scrollContainer;
            View view2 = this.messageTextView;
            i2 = (LocaleController.isRTL ? 5 : 3) | 48;
            i3 = (this.customView == null && this.items == null) ? 0 : 20;
            linearLayout.addView(view2, LayoutHelper.createLinear(-2, -2, i2, 24, 0, 24, i3));
        }
        if (TextUtils.isEmpty(this.message)) {
            this.messageTextView.setVisibility(8);
        } else {
            this.messageTextView.setText(this.message);
            this.messageTextView.setVisibility(0);
        }
        if (this.items != null) {
            i = 0;
            while (i < this.items.length) {
                if (this.items[i] != null) {
                    View alertDialogCell = new AlertDialogCell(getContext());
                    alertDialogCell.setTextAndIcon(this.items[i], this.itemIcons != null ? this.itemIcons[i] : 0);
                    this.scrollContainer.addView(alertDialogCell, LayoutHelper.createLinear(-1, 48));
                    alertDialogCell.setTag(Integer.valueOf(i));
                    alertDialogCell.setOnClickListener(new C38233());
                }
                i++;
            }
        }
        if (this.customView != null) {
            if (this.customView.getParent() != null) {
                ((ViewGroup) this.customView.getParent()).removeView(this.customView);
            }
            this.scrollContainer.addView(this.customView, LayoutHelper.createLinear(-1, -2));
        }
        if (obj != null) {
            View c38255;
            this.buttonsLayout = new FrameLayout(getContext()) {
                protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                    int childCount = getChildCount();
                    View view = null;
                    int i5 = i3 - i;
                    int i6 = 0;
                    while (i6 < childCount) {
                        View view2;
                        View childAt = getChildAt(i6);
                        if (((Integer) childAt.getTag()).intValue() == -1) {
                            childAt.layout((i5 - getPaddingRight()) - childAt.getMeasuredWidth(), getPaddingTop(), (i5 - getPaddingRight()) + childAt.getMeasuredWidth(), getPaddingTop() + childAt.getMeasuredHeight());
                            view2 = childAt;
                        } else if (((Integer) childAt.getTag()).intValue() == -2) {
                            int paddingRight = (i5 - getPaddingRight()) - childAt.getMeasuredWidth();
                            if (view != null) {
                                paddingRight -= view.getMeasuredWidth() + AndroidUtilities.dp(8.0f);
                            }
                            childAt.layout(paddingRight, getPaddingTop(), childAt.getMeasuredWidth() + paddingRight, getPaddingTop() + childAt.getMeasuredHeight());
                            view2 = view;
                        } else {
                            childAt.layout(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + childAt.getMeasuredWidth(), getPaddingTop() + childAt.getMeasuredHeight());
                            view2 = view;
                        }
                        i6++;
                        view = view2;
                    }
                }
            };
            this.buttonsLayout.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f));
            c38211.addView(this.buttonsLayout, LayoutHelper.createLinear(-1, 52));
            if (this.positiveButtonText != null) {
                c38255 = new TextView(getContext()) {
                    public void setEnabled(boolean z) {
                        super.setEnabled(z);
                        setAlpha(z ? 1.0f : 0.5f);
                    }
                };
                c38255.setMinWidth(AndroidUtilities.dp(64.0f));
                c38255.setTag(Integer.valueOf(-1));
                c38255.setTextSize(1, 14.0f);
                c38255.setTextColor(Theme.getColor(Theme.key_dialogButton));
                c38255.setGravity(17);
                c38255.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                c38255.setText(this.positiveButtonText.toString().toUpperCase());
                c38255.setBackgroundDrawable(Theme.getRoundRectSelectorDrawable());
                c38255.setPadding(AndroidUtilities.dp(10.0f), 0, AndroidUtilities.dp(10.0f), 0);
                this.buttonsLayout.addView(c38255, LayoutHelper.createFrame(-2, 36, 53));
                c38255.setOnClickListener(new C38266());
            }
            if (this.negativeButtonText != null) {
                c38255 = new TextView(getContext()) {
                    public void setEnabled(boolean z) {
                        super.setEnabled(z);
                        setAlpha(z ? 1.0f : 0.5f);
                    }
                };
                c38255.setMinWidth(AndroidUtilities.dp(64.0f));
                c38255.setTag(Integer.valueOf(-2));
                c38255.setTextSize(1, 14.0f);
                c38255.setTextColor(Theme.getColor(Theme.key_dialogButton));
                c38255.setGravity(17);
                c38255.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                c38255.setText(this.negativeButtonText.toString().toUpperCase());
                c38255.setBackgroundDrawable(Theme.getRoundRectSelectorDrawable());
                c38255.setPadding(AndroidUtilities.dp(10.0f), 0, AndroidUtilities.dp(10.0f), 0);
                this.buttonsLayout.addView(c38255, LayoutHelper.createFrame(-2, 36, 53));
                c38255.setOnClickListener(new C38288());
            }
            if (this.neutralButtonText != null) {
                c38255 = new TextView(getContext()) {
                    public void setEnabled(boolean z) {
                        super.setEnabled(z);
                        setAlpha(z ? 1.0f : 0.5f);
                    }
                };
                c38255.setMinWidth(AndroidUtilities.dp(64.0f));
                c38255.setTag(Integer.valueOf(-3));
                c38255.setTextSize(1, 14.0f);
                c38255.setTextColor(Theme.getColor(Theme.key_dialogButton));
                c38255.setGravity(17);
                c38255.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                c38255.setText(this.neutralButtonText.toString().toUpperCase());
                c38255.setBackgroundDrawable(Theme.getRoundRectSelectorDrawable());
                c38255.setPadding(AndroidUtilities.dp(10.0f), 0, AndroidUtilities.dp(10.0f), 0);
                this.buttonsLayout.addView(c38255, LayoutHelper.createFrame(-2, 36, 51));
                c38255.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (AlertDialog.this.neutralButtonListener != null) {
                            AlertDialog.this.neutralButtonListener.onClick(AlertDialog.this, -2);
                        }
                        AlertDialog.this.dismiss();
                    }
                });
            }
        }
        this.lastScreenWidth = AndroidUtilities.displaySize.x;
        int dp = AndroidUtilities.displaySize.x - AndroidUtilities.dp(48.0f);
        i = AndroidUtilities.isTablet() ? AndroidUtilities.isSmallTablet() ? AndroidUtilities.dp(446.0f) : AndroidUtilities.dp(496.0f) : AndroidUtilities.dp(356.0f);
        Window window = getWindow();
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.dimAmount = 0.6f;
        layoutParams.width = (Math.min(i, dp) + this.backgroundPaddings.left) + this.backgroundPaddings.right;
        layoutParams.flags |= 2;
        if (this.customView == null || !canTextInput(this.customView)) {
            layoutParams.flags |= 131072;
        }
        window.setAttributes(layoutParams);
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        if (this.contentScrollView != null) {
            this.contentScrollView.postDelayed(runnable, j);
        }
    }

    public void setButton(int i, CharSequence charSequence, OnClickListener onClickListener) {
        switch (i) {
            case -3:
                this.neutralButtonText = charSequence;
                this.neutralButtonListener = onClickListener;
                return;
            case -2:
                this.negativeButtonText = charSequence;
                this.negativeButtonListener = onClickListener;
                return;
            case -1:
                this.positiveButtonText = charSequence;
                this.positiveButtonListener = onClickListener;
                return;
            default:
                return;
        }
    }

    public void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
    }

    public void setMessage(CharSequence charSequence) {
        this.message = charSequence;
        if (this.messageTextView == null) {
            return;
        }
        if (TextUtils.isEmpty(this.message)) {
            this.messageTextView.setVisibility(8);
            return;
        }
        this.messageTextView.setText(this.message);
        this.messageTextView.setVisibility(0);
    }

    public void setProgress(int i) {
        this.currentProgress = i;
        if (this.lineProgressView != null) {
            this.lineProgressView.setProgress(((float) i) / 100.0f, true);
            updateLineProgressTextView();
        }
    }

    public void setProgressStyle(int i) {
        this.progressViewStyle = i;
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        if (this.contentScrollView != null) {
            this.contentScrollView.removeCallbacks(runnable);
        }
    }
}
