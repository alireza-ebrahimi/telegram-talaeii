package org.telegram.ui.ActionBar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.LineProgressView;

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
    class C19853 implements View.OnClickListener {
        C19853() {
        }

        public void onClick(View v) {
            if (AlertDialog.this.onClickListener != null) {
                AlertDialog.this.onClickListener.onClick(AlertDialog.this, ((Integer) v.getTag()).intValue());
            }
            AlertDialog.this.dismiss();
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.AlertDialog$6 */
    class C19886 implements View.OnClickListener {
        C19886() {
        }

        public void onClick(View v) {
            if (AlertDialog.this.positiveButtonListener != null) {
                AlertDialog.this.positiveButtonListener.onClick(AlertDialog.this, -1);
            }
            AlertDialog.this.dismiss();
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.AlertDialog$8 */
    class C19908 implements View.OnClickListener {
        C19908() {
        }

        public void onClick(View v) {
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

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
        }

        public void setTextColor(int color) {
            this.textView.setTextColor(color);
        }

        public void setGravity(int gravity) {
            this.textView.setGravity(gravity);
        }

        public void setTextAndIcon(CharSequence text, int icon) {
            this.textView.setText(text);
            if (icon != 0) {
                this.imageView.setImageResource(icon);
                this.imageView.setVisibility(0);
                this.textView.setPadding(LocaleController.isRTL ? 0 : AndroidUtilities.dp(56.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(56.0f) : 0, 0);
                return;
            }
            this.imageView.setVisibility(4);
            this.textView.setPadding(0, 0, 0, 0);
        }
    }

    public static class Builder {
        private AlertDialog alertDialog;

        public Builder(Context context) {
            this.alertDialog = new AlertDialog(context, 0);
        }

        public Builder(Context context, int progressViewStyle) {
            this.alertDialog = new AlertDialog(context, progressViewStyle);
        }

        public Context getContext() {
            return this.alertDialog.getContext();
        }

        public Builder setItems(CharSequence[] items, OnClickListener onClickListener) {
            this.alertDialog.items = items;
            this.alertDialog.onClickListener = onClickListener;
            return this;
        }

        public Builder setItems(CharSequence[] items, int[] icons, OnClickListener onClickListener) {
            this.alertDialog.items = items;
            this.alertDialog.itemIcons = icons;
            this.alertDialog.onClickListener = onClickListener;
            return this;
        }

        public Builder setView(View view) {
            this.alertDialog.customView = view;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.alertDialog.title = title;
            return this;
        }

        public Builder setSubtitle(CharSequence subtitle) {
            this.alertDialog.subtitle = subtitle;
            return this;
        }

        public Builder setTopImage(int resId, int backgroundColor) {
            this.alertDialog.topResId = resId;
            this.alertDialog.topBackgroundColor = backgroundColor;
            return this;
        }

        public Builder setTopImage(Drawable drawable, int backgroundColor) {
            this.alertDialog.topDrawable = drawable;
            this.alertDialog.topBackgroundColor = backgroundColor;
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.alertDialog.message = message;
            return this;
        }

        public Builder setPositiveButton(CharSequence text, OnClickListener listener) {
            this.alertDialog.positiveButtonText = text;
            this.alertDialog.positiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text, OnClickListener listener) {
            this.alertDialog.negativeButtonText = text;
            this.alertDialog.negativeButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(CharSequence text, OnClickListener listener) {
            this.alertDialog.neutralButtonText = text;
            this.alertDialog.neutralButtonListener = listener;
            return this;
        }

        public Builder setOnBackButtonListener(OnClickListener listener) {
            this.alertDialog.onBackButtonListener = listener;
            return this;
        }

        public AlertDialog create() {
            return this.alertDialog;
        }

        public AlertDialog show() {
            this.alertDialog.show();
            return this.alertDialog;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            this.alertDialog.setOnDismissListener(onDismissListener);
            return this;
        }
    }

    public AlertDialog(Context context, int progressStyle) {
        super(context, R.style.TransparentDialog);
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.popup_fixed_alert).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogBackground), Mode.MULTIPLY));
        this.shadowDrawable.getPadding(this.backgroundPaddings);
        this.progressViewStyle = progressStyle;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onCreate(android.os.Bundle r25) {
        /*
        r24 = this;
        super.onCreate(r25);
        r12 = new org.telegram.ui.ActionBar.AlertDialog$1;
        r2 = r24.getContext();
        r0 = r24;
        r12.<init>(r2);
        r2 = 1;
        r12.setOrientation(r2);
        r0 = r24;
        r2 = r0.shadowDrawable;
        r12.setBackgroundDrawable(r2);
        r2 = android.os.Build.VERSION.SDK_INT;
        r3 = 21;
        if (r2 < r3) goto L_0x0385;
    L_0x001f:
        r2 = 1;
    L_0x0020:
        r12.setFitsSystemWindows(r2);
        r0 = r24;
        r0.setContentView(r12);
        r0 = r24;
        r2 = r0.positiveButtonText;
        if (r2 != 0) goto L_0x003a;
    L_0x002e:
        r0 = r24;
        r2 = r0.negativeButtonText;
        if (r2 != 0) goto L_0x003a;
    L_0x0034:
        r0 = r24;
        r2 = r0.neutralButtonText;
        if (r2 == 0) goto L_0x0388;
    L_0x003a:
        r13 = 1;
    L_0x003b:
        r0 = r24;
        r2 = r0.topResId;
        if (r2 != 0) goto L_0x0047;
    L_0x0041:
        r0 = r24;
        r2 = r0.topDrawable;
        if (r2 == 0) goto L_0x00c2;
    L_0x0047:
        r2 = new android.widget.ImageView;
        r3 = r24.getContext();
        r2.<init>(r3);
        r0 = r24;
        r0.topImageView = r2;
        r0 = r24;
        r2 = r0.topDrawable;
        if (r2 == 0) goto L_0x038b;
    L_0x005a:
        r0 = r24;
        r2 = r0.topImageView;
        r0 = r24;
        r3 = r0.topDrawable;
        r2.setImageDrawable(r3);
    L_0x0065:
        r0 = r24;
        r2 = r0.topImageView;
        r3 = android.widget.ImageView.ScaleType.CENTER;
        r2.setScaleType(r3);
        r0 = r24;
        r2 = r0.topImageView;
        r3 = r24.getContext();
        r3 = r3.getResources();
        r4 = 2130838214; // 0x7f0202c6 float:1.7281404E38 double:1.0527739584E-314;
        r3 = r3.getDrawable(r4);
        r2.setBackgroundDrawable(r3);
        r0 = r24;
        r2 = r0.topImageView;
        r2 = r2.getBackground();
        r3 = new android.graphics.PorterDuffColorFilter;
        r0 = r24;
        r4 = r0.topBackgroundColor;
        r5 = android.graphics.PorterDuff.Mode.MULTIPLY;
        r3.<init>(r4, r5);
        r2.setColorFilter(r3);
        r0 = r24;
        r2 = r0.topImageView;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r2.setPadding(r3, r4, r5, r6);
        r0 = r24;
        r0 = r0.topImageView;
        r22 = r0;
        r2 = -1;
        r3 = 132; // 0x84 float:1.85E-43 double:6.5E-322;
        r4 = org.telegram.messenger.LocaleController.isRTL;
        if (r4 == 0) goto L_0x0398;
    L_0x00b2:
        r4 = 5;
    L_0x00b3:
        r4 = r4 | 48;
        r5 = -8;
        r6 = -8;
        r7 = 0;
        r8 = 0;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3, r4, r5, r6, r7, r8);
        r0 = r22;
        r12.addView(r0, r2);
    L_0x00c2:
        r0 = r24;
        r2 = r0.title;
        if (r2 == 0) goto L_0x0139;
    L_0x00c8:
        r2 = new android.widget.TextView;
        r3 = r24.getContext();
        r2.<init>(r3);
        r0 = r24;
        r0.titleTextView = r2;
        r0 = r24;
        r2 = r0.titleTextView;
        r0 = r24;
        r3 = r0.title;
        r2.setText(r3);
        r0 = r24;
        r2 = r0.titleTextView;
        r3 = "dialogTextBlack";
        r3 = org.telegram.ui.ActionBar.Theme.getColor(r3);
        r2.setTextColor(r3);
        r0 = r24;
        r2 = r0.titleTextView;
        r3 = 1;
        r4 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r2.setTextSize(r3, r4);
        r0 = r24;
        r2 = r0.titleTextView;
        r3 = "fonts/rmedium.ttf";
        r3 = org.telegram.messenger.AndroidUtilities.getTypeface(r3);
        r2.setTypeface(r3);
        r0 = r24;
        r3 = r0.titleTextView;
        r2 = org.telegram.messenger.LocaleController.isRTL;
        if (r2 == 0) goto L_0x039b;
    L_0x010e:
        r2 = 5;
    L_0x010f:
        r2 = r2 | 48;
        r3.setGravity(r2);
        r0 = r24;
        r0 = r0.titleTextView;
        r22 = r0;
        r2 = -2;
        r3 = -2;
        r4 = org.telegram.messenger.LocaleController.isRTL;
        if (r4 == 0) goto L_0x039e;
    L_0x0120:
        r4 = 5;
    L_0x0121:
        r4 = r4 | 48;
        r5 = 24;
        r6 = 19;
        r7 = 24;
        r0 = r24;
        r8 = r0.subtitle;
        if (r8 == 0) goto L_0x03a1;
    L_0x012f:
        r8 = 2;
    L_0x0130:
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3, r4, r5, r6, r7, r8);
        r0 = r22;
        r12.addView(r0, r2);
    L_0x0139:
        r0 = r24;
        r2 = r0.subtitle;
        if (r2 == 0) goto L_0x01a2;
    L_0x013f:
        r2 = new android.widget.TextView;
        r3 = r24.getContext();
        r2.<init>(r3);
        r0 = r24;
        r0.subtitleTextView = r2;
        r0 = r24;
        r2 = r0.subtitleTextView;
        r0 = r24;
        r3 = r0.subtitle;
        r2.setText(r3);
        r0 = r24;
        r2 = r0.subtitleTextView;
        r3 = "dialogIcon";
        r3 = org.telegram.ui.ActionBar.Theme.getColor(r3);
        r2.setTextColor(r3);
        r0 = r24;
        r2 = r0.subtitleTextView;
        r3 = 1;
        r4 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r2.setTextSize(r3, r4);
        r0 = r24;
        r3 = r0.subtitleTextView;
        r2 = org.telegram.messenger.LocaleController.isRTL;
        if (r2 == 0) goto L_0x03af;
    L_0x0177:
        r2 = 5;
    L_0x0178:
        r2 = r2 | 48;
        r3.setGravity(r2);
        r0 = r24;
        r0 = r0.subtitleTextView;
        r22 = r0;
        r2 = -2;
        r3 = -2;
        r4 = org.telegram.messenger.LocaleController.isRTL;
        if (r4 == 0) goto L_0x03b2;
    L_0x0189:
        r4 = 5;
    L_0x018a:
        r4 = r4 | 48;
        r5 = 24;
        r6 = 0;
        r7 = 24;
        r0 = r24;
        r8 = r0.items;
        if (r8 == 0) goto L_0x03b5;
    L_0x0197:
        r8 = 14;
    L_0x0199:
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3, r4, r5, r6, r7, r8);
        r0 = r22;
        r12.addView(r0, r2);
    L_0x01a2:
        r0 = r24;
        r2 = r0.progressViewStyle;
        if (r2 != 0) goto L_0x026b;
    L_0x01a8:
        r0 = r24;
        r3 = r0.shadow;
        r4 = 0;
        r2 = r24.getContext();
        r2 = r2.getResources();
        r5 = 2130837859; // 0x7f020163 float:1.7280684E38 double:1.052773783E-314;
        r2 = r2.getDrawable(r5);
        r2 = r2.mutate();
        r2 = (android.graphics.drawable.BitmapDrawable) r2;
        r3[r4] = r2;
        r0 = r24;
        r3 = r0.shadow;
        r4 = 1;
        r2 = r24.getContext();
        r2 = r2.getResources();
        r5 = 2130837860; // 0x7f020164 float:1.7280686E38 double:1.0527737835E-314;
        r2 = r2.getDrawable(r5);
        r2 = r2.mutate();
        r2 = (android.graphics.drawable.BitmapDrawable) r2;
        r3[r4] = r2;
        r0 = r24;
        r2 = r0.shadow;
        r3 = 0;
        r2 = r2[r3];
        r3 = 0;
        r2.setAlpha(r3);
        r0 = r24;
        r2 = r0.shadow;
        r3 = 1;
        r2 = r2[r3];
        r3 = 0;
        r2.setAlpha(r3);
        r0 = r24;
        r2 = r0.shadow;
        r3 = 0;
        r2 = r2[r3];
        r0 = r24;
        r2.setCallback(r0);
        r0 = r24;
        r2 = r0.shadow;
        r3 = 1;
        r2 = r2[r3];
        r0 = r24;
        r2.setCallback(r0);
        r2 = new org.telegram.ui.ActionBar.AlertDialog$2;
        r3 = r24.getContext();
        r0 = r24;
        r2.<init>(r3);
        r0 = r24;
        r0.contentScrollView = r2;
        r0 = r24;
        r2 = r0.contentScrollView;
        r3 = 0;
        r2.setVerticalScrollBarEnabled(r3);
        r0 = r24;
        r2 = r0.contentScrollView;
        r3 = "dialogScrollGlow";
        r3 = org.telegram.ui.ActionBar.Theme.getColor(r3);
        org.telegram.messenger.AndroidUtilities.setScrollViewEdgeEffectColor(r2, r3);
        r0 = r24;
        r8 = r0.contentScrollView;
        r2 = -1;
        r3 = -2;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3, r4, r5, r6, r7);
        r12.addView(r8, r2);
        r2 = new android.widget.LinearLayout;
        r3 = r24.getContext();
        r2.<init>(r3);
        r0 = r24;
        r0.scrollContainer = r2;
        r0 = r24;
        r2 = r0.scrollContainer;
        r3 = 1;
        r2.setOrientation(r3);
        r0 = r24;
        r2 = r0.contentScrollView;
        r0 = r24;
        r3 = r0.scrollContainer;
        r4 = new android.widget.FrameLayout$LayoutParams;
        r5 = -1;
        r6 = -2;
        r4.<init>(r5, r6);
        r2.addView(r3, r4);
    L_0x026b:
        r2 = new android.widget.TextView;
        r3 = r24.getContext();
        r2.<init>(r3);
        r0 = r24;
        r0.messageTextView = r2;
        r0 = r24;
        r2 = r0.messageTextView;
        r3 = "dialogTextBlack";
        r3 = org.telegram.ui.ActionBar.Theme.getColor(r3);
        r2.setTextColor(r3);
        r0 = r24;
        r2 = r0.messageTextView;
        r3 = 1;
        r4 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r2.setTextSize(r3, r4);
        r0 = r24;
        r3 = r0.messageTextView;
        r2 = org.telegram.messenger.LocaleController.isRTL;
        if (r2 == 0) goto L_0x03b9;
    L_0x0298:
        r2 = 5;
    L_0x0299:
        r2 = r2 | 48;
        r3.setGravity(r2);
        r0 = r24;
        r2 = r0.progressViewStyle;
        r3 = 1;
        if (r2 != r3) goto L_0x03cc;
    L_0x02a5:
        r2 = new android.widget.FrameLayout;
        r3 = r24.getContext();
        r2.<init>(r3);
        r0 = r24;
        r0.progressViewContainer = r2;
        r0 = r24;
        r0 = r0.progressViewContainer;
        r22 = r0;
        r2 = -1;
        r3 = 44;
        r4 = 51;
        r5 = 23;
        r0 = r24;
        r6 = r0.title;
        if (r6 != 0) goto L_0x03bc;
    L_0x02c5:
        r6 = 24;
    L_0x02c7:
        r7 = 23;
        r8 = 24;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3, r4, r5, r6, r7, r8);
        r0 = r22;
        r12.addView(r0, r2);
        r17 = new org.telegram.ui.Components.RadialProgressView;
        r2 = r24.getContext();
        r0 = r17;
        r0.<init>(r2);
        r2 = "dialogProgressCircle";
        r2 = org.telegram.ui.ActionBar.Theme.getColor(r2);
        r0 = r17;
        r0.setProgressColor(r2);
        r0 = r24;
        r3 = r0.progressViewContainer;
        r4 = 44;
        r5 = 44;
        r2 = org.telegram.messenger.LocaleController.isRTL;
        if (r2 == 0) goto L_0x03bf;
    L_0x02f7:
        r2 = 5;
    L_0x02f8:
        r2 = r2 | 48;
        r2 = org.telegram.ui.Components.LayoutHelper.createFrame(r4, r5, r2);
        r0 = r17;
        r3.addView(r0, r2);
        r0 = r24;
        r2 = r0.messageTextView;
        r3 = 1;
        r2.setLines(r3);
        r0 = r24;
        r2 = r0.messageTextView;
        r3 = 1;
        r2.setSingleLine(r3);
        r0 = r24;
        r2 = r0.messageTextView;
        r3 = android.text.TextUtils.TruncateAt.END;
        r2.setEllipsize(r3);
        r0 = r24;
        r0 = r0.progressViewContainer;
        r22 = r0;
        r0 = r24;
        r0 = r0.messageTextView;
        r23 = r0;
        r2 = -2;
        r3 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r4 = org.telegram.messenger.LocaleController.isRTL;
        if (r4 == 0) goto L_0x03c2;
    L_0x032f:
        r4 = 5;
    L_0x0330:
        r4 = r4 | 16;
        r5 = org.telegram.messenger.LocaleController.isRTL;
        if (r5 == 0) goto L_0x03c5;
    L_0x0336:
        r5 = 0;
    L_0x0337:
        r5 = (float) r5;
        r6 = 0;
        r7 = org.telegram.messenger.LocaleController.isRTL;
        if (r7 == 0) goto L_0x03c9;
    L_0x033d:
        r7 = 62;
    L_0x033f:
        r7 = (float) r7;
        r8 = 0;
        r2 = org.telegram.ui.Components.LayoutHelper.createFrame(r2, r3, r4, r5, r6, r7, r8);
        r0 = r22;
        r1 = r23;
        r0.addView(r1, r2);
    L_0x034c:
        r0 = r24;
        r2 = r0.message;
        r2 = android.text.TextUtils.isEmpty(r2);
        if (r2 != 0) goto L_0x04f3;
    L_0x0356:
        r0 = r24;
        r2 = r0.messageTextView;
        r0 = r24;
        r3 = r0.message;
        r2.setText(r3);
        r0 = r24;
        r2 = r0.messageTextView;
        r3 = 0;
        r2.setVisibility(r3);
    L_0x0369:
        r0 = r24;
        r2 = r0.items;
        if (r2 == 0) goto L_0x053f;
    L_0x036f:
        r18 = 0;
        r14 = 0;
        r9 = 0;
    L_0x0373:
        r0 = r24;
        r2 = r0.items;
        r2 = r2.length;
        if (r9 >= r2) goto L_0x053f;
    L_0x037a:
        r0 = r24;
        r2 = r0.items;
        r2 = r2[r9];
        if (r2 != 0) goto L_0x04fe;
    L_0x0382:
        r9 = r9 + 1;
        goto L_0x0373;
    L_0x0385:
        r2 = 0;
        goto L_0x0020;
    L_0x0388:
        r13 = 0;
        goto L_0x003b;
    L_0x038b:
        r0 = r24;
        r2 = r0.topImageView;
        r0 = r24;
        r3 = r0.topResId;
        r2.setImageResource(r3);
        goto L_0x0065;
    L_0x0398:
        r4 = 3;
        goto L_0x00b3;
    L_0x039b:
        r2 = 3;
        goto L_0x010f;
    L_0x039e:
        r4 = 3;
        goto L_0x0121;
    L_0x03a1:
        r0 = r24;
        r8 = r0.items;
        if (r8 == 0) goto L_0x03ab;
    L_0x03a7:
        r8 = 14;
        goto L_0x0130;
    L_0x03ab:
        r8 = 10;
        goto L_0x0130;
    L_0x03af:
        r2 = 3;
        goto L_0x0178;
    L_0x03b2:
        r4 = 3;
        goto L_0x018a;
    L_0x03b5:
        r8 = 10;
        goto L_0x0199;
    L_0x03b9:
        r2 = 3;
        goto L_0x0299;
    L_0x03bc:
        r6 = 0;
        goto L_0x02c7;
    L_0x03bf:
        r2 = 3;
        goto L_0x02f8;
    L_0x03c2:
        r4 = 3;
        goto L_0x0330;
    L_0x03c5:
        r5 = 62;
        goto L_0x0337;
    L_0x03c9:
        r7 = 0;
        goto L_0x033f;
    L_0x03cc:
        r0 = r24;
        r2 = r0.progressViewStyle;
        r3 = 2;
        if (r2 != r3) goto L_0x04ba;
    L_0x03d3:
        r0 = r24;
        r0 = r0.messageTextView;
        r22 = r0;
        r2 = -2;
        r3 = -2;
        r4 = org.telegram.messenger.LocaleController.isRTL;
        if (r4 == 0) goto L_0x04b0;
    L_0x03df:
        r4 = 5;
    L_0x03e0:
        r4 = r4 | 48;
        r5 = 24;
        r0 = r24;
        r6 = r0.title;
        if (r6 != 0) goto L_0x04b3;
    L_0x03ea:
        r6 = 19;
    L_0x03ec:
        r7 = 24;
        r8 = 20;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3, r4, r5, r6, r7, r8);
        r0 = r22;
        r12.addView(r0, r2);
        r2 = new org.telegram.ui.Components.LineProgressView;
        r3 = r24.getContext();
        r2.<init>(r3);
        r0 = r24;
        r0.lineProgressView = r2;
        r0 = r24;
        r2 = r0.lineProgressView;
        r0 = r24;
        r3 = r0.currentProgress;
        r3 = (float) r3;
        r4 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r3 = r3 / r4;
        r4 = 0;
        r2.setProgress(r3, r4);
        r0 = r24;
        r2 = r0.lineProgressView;
        r3 = "dialogLineProgress";
        r3 = org.telegram.ui.ActionBar.Theme.getColor(r3);
        r2.setProgressColor(r3);
        r0 = r24;
        r2 = r0.lineProgressView;
        r3 = "dialogLineProgressBackground";
        r3 = org.telegram.ui.ActionBar.Theme.getColor(r3);
        r2.setBackColor(r3);
        r0 = r24;
        r0 = r0.lineProgressView;
        r22 = r0;
        r2 = -1;
        r3 = 4;
        r4 = 19;
        r5 = 24;
        r6 = 0;
        r7 = 24;
        r8 = 0;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3, r4, r5, r6, r7, r8);
        r0 = r22;
        r12.addView(r0, r2);
        r2 = new android.widget.TextView;
        r3 = r24.getContext();
        r2.<init>(r3);
        r0 = r24;
        r0.lineProgressViewPercent = r2;
        r0 = r24;
        r2 = r0.lineProgressViewPercent;
        r3 = "fonts/rmedium.ttf";
        r3 = org.telegram.messenger.AndroidUtilities.getTypeface(r3);
        r2.setTypeface(r3);
        r0 = r24;
        r3 = r0.lineProgressViewPercent;
        r2 = org.telegram.messenger.LocaleController.isRTL;
        if (r2 == 0) goto L_0x04b6;
    L_0x046e:
        r2 = 5;
    L_0x046f:
        r2 = r2 | 48;
        r3.setGravity(r2);
        r0 = r24;
        r2 = r0.lineProgressViewPercent;
        r3 = "dialogTextGray2";
        r3 = org.telegram.ui.ActionBar.Theme.getColor(r3);
        r2.setTextColor(r3);
        r0 = r24;
        r2 = r0.lineProgressViewPercent;
        r3 = 1;
        r4 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r2.setTextSize(r3, r4);
        r0 = r24;
        r0 = r0.lineProgressViewPercent;
        r22 = r0;
        r2 = -2;
        r3 = -2;
        r4 = org.telegram.messenger.LocaleController.isRTL;
        if (r4 == 0) goto L_0x04b8;
    L_0x0498:
        r4 = 5;
    L_0x0499:
        r4 = r4 | 48;
        r5 = 23;
        r6 = 4;
        r7 = 23;
        r8 = 24;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3, r4, r5, r6, r7, r8);
        r0 = r22;
        r12.addView(r0, r2);
        r24.updateLineProgressTextView();
        goto L_0x034c;
    L_0x04b0:
        r4 = 3;
        goto L_0x03e0;
    L_0x04b3:
        r6 = 0;
        goto L_0x03ec;
    L_0x04b6:
        r2 = 3;
        goto L_0x046f;
    L_0x04b8:
        r4 = 3;
        goto L_0x0499;
    L_0x04ba:
        r0 = r24;
        r0 = r0.scrollContainer;
        r22 = r0;
        r0 = r24;
        r0 = r0.messageTextView;
        r23 = r0;
        r2 = -2;
        r3 = -2;
        r4 = org.telegram.messenger.LocaleController.isRTL;
        if (r4 == 0) goto L_0x04ef;
    L_0x04cc:
        r4 = 5;
    L_0x04cd:
        r4 = r4 | 48;
        r5 = 24;
        r6 = 0;
        r7 = 24;
        r0 = r24;
        r8 = r0.customView;
        if (r8 != 0) goto L_0x04e0;
    L_0x04da:
        r0 = r24;
        r8 = r0.items;
        if (r8 == 0) goto L_0x04f1;
    L_0x04e0:
        r8 = 20;
    L_0x04e2:
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3, r4, r5, r6, r7, r8);
        r0 = r22;
        r1 = r23;
        r0.addView(r1, r2);
        goto L_0x034c;
    L_0x04ef:
        r4 = 3;
        goto L_0x04cd;
    L_0x04f1:
        r8 = 0;
        goto L_0x04e2;
    L_0x04f3:
        r0 = r24;
        r2 = r0.messageTextView;
        r3 = 8;
        r2.setVisibility(r3);
        goto L_0x0369;
    L_0x04fe:
        r11 = new org.telegram.ui.ActionBar.AlertDialog$AlertDialogCell;
        r2 = r24.getContext();
        r11.<init>(r2);
        r0 = r24;
        r2 = r0.items;
        r3 = r2[r9];
        r0 = r24;
        r2 = r0.itemIcons;
        if (r2 == 0) goto L_0x053d;
    L_0x0513:
        r0 = r24;
        r2 = r0.itemIcons;
        r2 = r2[r9];
    L_0x0519:
        r11.setTextAndIcon(r3, r2);
        r0 = r24;
        r2 = r0.scrollContainer;
        r3 = -1;
        r4 = 48;
        r3 = org.telegram.ui.Components.LayoutHelper.createLinear(r3, r4);
        r2.addView(r11, r3);
        r2 = java.lang.Integer.valueOf(r9);
        r11.setTag(r2);
        r2 = new org.telegram.ui.ActionBar.AlertDialog$3;
        r0 = r24;
        r2.<init>();
        r11.setOnClickListener(r2);
        goto L_0x0382;
    L_0x053d:
        r2 = 0;
        goto L_0x0519;
    L_0x053f:
        r0 = r24;
        r2 = r0.customView;
        if (r2 == 0) goto L_0x0573;
    L_0x0545:
        r0 = r24;
        r2 = r0.customView;
        r2 = r2.getParent();
        if (r2 == 0) goto L_0x0562;
    L_0x054f:
        r0 = r24;
        r2 = r0.customView;
        r20 = r2.getParent();
        r20 = (android.view.ViewGroup) r20;
        r0 = r24;
        r2 = r0.customView;
        r0 = r20;
        r0.removeView(r2);
    L_0x0562:
        r0 = r24;
        r2 = r0.scrollContainer;
        r0 = r24;
        r3 = r0.customView;
        r4 = -1;
        r5 = -2;
        r4 = org.telegram.ui.Components.LayoutHelper.createLinear(r4, r5);
        r2.addView(r3, r4);
    L_0x0573:
        if (r13 == 0) goto L_0x077f;
    L_0x0575:
        r2 = new org.telegram.ui.ActionBar.AlertDialog$4;
        r3 = r24.getContext();
        r0 = r24;
        r2.<init>(r3);
        r0 = r24;
        r0.buttonsLayout = r2;
        r0 = r24;
        r2 = r0.buttonsLayout;
        r3 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r3 = org.telegram.messenger.AndroidUtilities.dp(r3);
        r4 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r5 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r6 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r2.setPadding(r3, r4, r5, r6);
        r0 = r24;
        r2 = r0.buttonsLayout;
        r3 = -1;
        r4 = 52;
        r3 = org.telegram.ui.Components.LayoutHelper.createLinear(r3, r4);
        r12.addView(r2, r3);
        r0 = r24;
        r2 = r0.positiveButtonText;
        if (r2 == 0) goto L_0x064b;
    L_0x05b7:
        r19 = new org.telegram.ui.ActionBar.AlertDialog$5;
        r2 = r24.getContext();
        r0 = r19;
        r1 = r24;
        r0.<init>(r2);
        r2 = 1115684864; // 0x42800000 float:64.0 double:5.51221563E-315;
        r2 = org.telegram.messenger.AndroidUtilities.dp(r2);
        r0 = r19;
        r0.setMinWidth(r2);
        r2 = -1;
        r2 = java.lang.Integer.valueOf(r2);
        r0 = r19;
        r0.setTag(r2);
        r2 = 1;
        r3 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r0 = r19;
        r0.setTextSize(r2, r3);
        r2 = "dialogButton";
        r2 = org.telegram.ui.ActionBar.Theme.getColor(r2);
        r0 = r19;
        r0.setTextColor(r2);
        r2 = 17;
        r0 = r19;
        r0.setGravity(r2);
        r2 = "fonts/rmedium.ttf";
        r2 = org.telegram.messenger.AndroidUtilities.getTypeface(r2);
        r0 = r19;
        r0.setTypeface(r2);
        r0 = r24;
        r2 = r0.positiveButtonText;
        r2 = r2.toString();
        r2 = r2.toUpperCase();
        r0 = r19;
        r0.setText(r2);
        r2 = org.telegram.ui.ActionBar.Theme.getRoundRectSelectorDrawable();
        r0 = r19;
        r0.setBackgroundDrawable(r2);
        r2 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r2 = org.telegram.messenger.AndroidUtilities.dp(r2);
        r3 = 0;
        r4 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r5 = 0;
        r0 = r19;
        r0.setPadding(r2, r3, r4, r5);
        r0 = r24;
        r2 = r0.buttonsLayout;
        r3 = -2;
        r4 = 36;
        r5 = 53;
        r3 = org.telegram.ui.Components.LayoutHelper.createFrame(r3, r4, r5);
        r0 = r19;
        r2.addView(r0, r3);
        r2 = new org.telegram.ui.ActionBar.AlertDialog$6;
        r0 = r24;
        r2.<init>();
        r0 = r19;
        r0.setOnClickListener(r2);
    L_0x064b:
        r0 = r24;
        r2 = r0.negativeButtonText;
        if (r2 == 0) goto L_0x06e5;
    L_0x0651:
        r19 = new org.telegram.ui.ActionBar.AlertDialog$7;
        r2 = r24.getContext();
        r0 = r19;
        r1 = r24;
        r0.<init>(r2);
        r2 = 1115684864; // 0x42800000 float:64.0 double:5.51221563E-315;
        r2 = org.telegram.messenger.AndroidUtilities.dp(r2);
        r0 = r19;
        r0.setMinWidth(r2);
        r2 = -2;
        r2 = java.lang.Integer.valueOf(r2);
        r0 = r19;
        r0.setTag(r2);
        r2 = 1;
        r3 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r0 = r19;
        r0.setTextSize(r2, r3);
        r2 = "dialogButton";
        r2 = org.telegram.ui.ActionBar.Theme.getColor(r2);
        r0 = r19;
        r0.setTextColor(r2);
        r2 = 17;
        r0 = r19;
        r0.setGravity(r2);
        r2 = "fonts/rmedium.ttf";
        r2 = org.telegram.messenger.AndroidUtilities.getTypeface(r2);
        r0 = r19;
        r0.setTypeface(r2);
        r0 = r24;
        r2 = r0.negativeButtonText;
        r2 = r2.toString();
        r2 = r2.toUpperCase();
        r0 = r19;
        r0.setText(r2);
        r2 = org.telegram.ui.ActionBar.Theme.getRoundRectSelectorDrawable();
        r0 = r19;
        r0.setBackgroundDrawable(r2);
        r2 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r2 = org.telegram.messenger.AndroidUtilities.dp(r2);
        r3 = 0;
        r4 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r5 = 0;
        r0 = r19;
        r0.setPadding(r2, r3, r4, r5);
        r0 = r24;
        r2 = r0.buttonsLayout;
        r3 = -2;
        r4 = 36;
        r5 = 53;
        r3 = org.telegram.ui.Components.LayoutHelper.createFrame(r3, r4, r5);
        r0 = r19;
        r2.addView(r0, r3);
        r2 = new org.telegram.ui.ActionBar.AlertDialog$8;
        r0 = r24;
        r2.<init>();
        r0 = r19;
        r0.setOnClickListener(r2);
    L_0x06e5:
        r0 = r24;
        r2 = r0.neutralButtonText;
        if (r2 == 0) goto L_0x077f;
    L_0x06eb:
        r19 = new org.telegram.ui.ActionBar.AlertDialog$9;
        r2 = r24.getContext();
        r0 = r19;
        r1 = r24;
        r0.<init>(r2);
        r2 = 1115684864; // 0x42800000 float:64.0 double:5.51221563E-315;
        r2 = org.telegram.messenger.AndroidUtilities.dp(r2);
        r0 = r19;
        r0.setMinWidth(r2);
        r2 = -3;
        r2 = java.lang.Integer.valueOf(r2);
        r0 = r19;
        r0.setTag(r2);
        r2 = 1;
        r3 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r0 = r19;
        r0.setTextSize(r2, r3);
        r2 = "dialogButton";
        r2 = org.telegram.ui.ActionBar.Theme.getColor(r2);
        r0 = r19;
        r0.setTextColor(r2);
        r2 = 17;
        r0 = r19;
        r0.setGravity(r2);
        r2 = "fonts/rmedium.ttf";
        r2 = org.telegram.messenger.AndroidUtilities.getTypeface(r2);
        r0 = r19;
        r0.setTypeface(r2);
        r0 = r24;
        r2 = r0.neutralButtonText;
        r2 = r2.toString();
        r2 = r2.toUpperCase();
        r0 = r19;
        r0.setText(r2);
        r2 = org.telegram.ui.ActionBar.Theme.getRoundRectSelectorDrawable();
        r0 = r19;
        r0.setBackgroundDrawable(r2);
        r2 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r2 = org.telegram.messenger.AndroidUtilities.dp(r2);
        r3 = 0;
        r4 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r5 = 0;
        r0 = r19;
        r0.setPadding(r2, r3, r4, r5);
        r0 = r24;
        r2 = r0.buttonsLayout;
        r3 = -2;
        r4 = 36;
        r5 = 51;
        r3 = org.telegram.ui.Components.LayoutHelper.createFrame(r3, r4, r5);
        r0 = r19;
        r2.addView(r0, r3);
        r2 = new org.telegram.ui.ActionBar.AlertDialog$10;
        r0 = r24;
        r2.<init>();
        r0 = r19;
        r0.setOnClickListener(r2);
    L_0x077f:
        r2 = org.telegram.messenger.AndroidUtilities.displaySize;
        r2 = r2.x;
        r0 = r24;
        r0.lastScreenWidth = r2;
        r2 = org.telegram.messenger.AndroidUtilities.displaySize;
        r2 = r2.x;
        r3 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
        r3 = org.telegram.messenger.AndroidUtilities.dp(r3);
        r10 = r2 - r3;
        r2 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r2 == 0) goto L_0x080a;
    L_0x0799:
        r2 = org.telegram.messenger.AndroidUtilities.isSmallTablet();
        if (r2 == 0) goto L_0x0803;
    L_0x079f:
        r2 = 1138688000; // 0x43df0000 float:446.0 double:5.62586622E-315;
        r15 = org.telegram.messenger.AndroidUtilities.dp(r2);
    L_0x07a5:
        r21 = r24.getWindow();
        r16 = new android.view.WindowManager$LayoutParams;
        r16.<init>();
        r2 = r21.getAttributes();
        r0 = r16;
        r0.copyFrom(r2);
        r2 = 1058642330; // 0x3f19999a float:0.6 double:5.230388065E-315;
        r0 = r16;
        r0.dimAmount = r2;
        r2 = java.lang.Math.min(r15, r10);
        r0 = r24;
        r3 = r0.backgroundPaddings;
        r3 = r3.left;
        r2 = r2 + r3;
        r0 = r24;
        r3 = r0.backgroundPaddings;
        r3 = r3.right;
        r2 = r2 + r3;
        r0 = r16;
        r0.width = r2;
        r0 = r16;
        r2 = r0.flags;
        r2 = r2 | 2;
        r0 = r16;
        r0.flags = r2;
        r0 = r24;
        r2 = r0.customView;
        if (r2 == 0) goto L_0x07f0;
    L_0x07e4:
        r0 = r24;
        r2 = r0.customView;
        r0 = r24;
        r2 = r0.canTextInput(r2);
        if (r2 != 0) goto L_0x07fb;
    L_0x07f0:
        r0 = r16;
        r2 = r0.flags;
        r3 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        r2 = r2 | r3;
        r0 = r16;
        r0.flags = r2;
    L_0x07fb:
        r0 = r21;
        r1 = r16;
        r0.setAttributes(r1);
        return;
    L_0x0803:
        r2 = 1140326400; // 0x43f80000 float:496.0 double:5.633960993E-315;
        r15 = org.telegram.messenger.AndroidUtilities.dp(r2);
        goto L_0x07a5;
    L_0x080a:
        r2 = 1135738880; // 0x43b20000 float:356.0 double:5.611295633E-315;
        r15 = org.telegram.messenger.AndroidUtilities.dp(r2);
        goto L_0x07a5;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.ActionBar.AlertDialog.onCreate(android.os.Bundle):void");
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.onBackButtonListener != null) {
            this.onBackButtonListener.onClick(this, -2);
        }
    }

    private void runShadowAnimation(final int num, boolean show) {
        if ((show && !this.shadowVisibility[num]) || (!show && this.shadowVisibility[num])) {
            this.shadowVisibility[num] = show;
            if (this.shadowAnimation[num] != null) {
                this.shadowAnimation[num].cancel();
            }
            this.shadowAnimation[num] = new AnimatorSet();
            if (this.shadow[num] != null) {
                AnimatorSet animatorSet = this.shadowAnimation[num];
                Animator[] animatorArr = new Animator[1];
                Object obj = this.shadow[num];
                String str = "alpha";
                int[] iArr = new int[1];
                iArr[0] = show ? 255 : 0;
                animatorArr[0] = ObjectAnimator.ofInt(obj, str, iArr);
                animatorSet.playTogether(animatorArr);
            }
            this.shadowAnimation[num].setDuration(150);
            this.shadowAnimation[num].addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    if (AlertDialog.this.shadowAnimation[num] != null && AlertDialog.this.shadowAnimation[num].equals(animation)) {
                        AlertDialog.this.shadowAnimation[num] = null;
                    }
                }

                public void onAnimationCancel(Animator animation) {
                    if (AlertDialog.this.shadowAnimation[num] != null && AlertDialog.this.shadowAnimation[num].equals(animation)) {
                        AlertDialog.this.shadowAnimation[num] = null;
                    }
                }
            });
            try {
                this.shadowAnimation[num].start();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    public void setProgressStyle(int style) {
        this.progressViewStyle = style;
    }

    public void setProgress(int progress) {
        this.currentProgress = progress;
        if (this.lineProgressView != null) {
            this.lineProgressView.setProgress(((float) progress) / 100.0f, true);
            updateLineProgressTextView();
        }
    }

    private void updateLineProgressTextView() {
        this.lineProgressViewPercent.setText(String.format("%d%%", new Object[]{Integer.valueOf(this.currentProgress)}));
    }

    private boolean canTextInput(View v) {
        if (v.onCheckIsTextEditor()) {
            return true;
        }
        if (!(v instanceof ViewGroup)) {
            return false;
        }
        ViewGroup vg = (ViewGroup) v;
        int i = vg.getChildCount();
        while (i > 0) {
            i--;
            if (canTextInput(vg.getChildAt(i))) {
                return true;
            }
        }
        return false;
    }

    public void dismiss() {
        super.dismiss();
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    public void setMessage(CharSequence text) {
        this.message = text;
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

    public void setButton(int type, CharSequence text, OnClickListener listener) {
        switch (type) {
            case -3:
                this.neutralButtonText = text;
                this.neutralButtonListener = listener;
                return;
            case -2:
                this.negativeButtonText = text;
                this.negativeButtonListener = listener;
                return;
            case -1:
                this.positiveButtonText = text;
                this.positiveButtonListener = listener;
                return;
            default:
                return;
        }
    }

    public View getButton(int type) {
        return this.buttonsLayout.findViewWithTag(Integer.valueOf(type));
    }

    public void invalidateDrawable(Drawable who) {
        this.contentScrollView.invalidate();
        this.scrollContainer.invalidate();
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (this.contentScrollView != null) {
            this.contentScrollView.postDelayed(what, when);
        }
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (this.contentScrollView != null) {
            this.contentScrollView.removeCallbacks(what);
        }
    }
}
