package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.Switch2;

public class TextCheckCell2 extends FrameLayout {
    private Switch2 checkBox;
    private boolean isMultiline;
    private boolean needDivider;
    private TextView textView;
    private TextView valueTextView;

    public TextCheckCell2(Context context) {
        float f = 17.0f;
        int i = 3;
        super(context);
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        this.textView.setEllipsize(TruncateAt.END);
        addView(this.textView, LayoutHelper.createFrame(-1, -1.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 64.0f : 17.0f, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? 17.0f : 64.0f, BitmapDescriptorFactory.HUE_RED));
        this.valueTextView = new TextView(context);
        this.valueTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
        this.valueTextView.setTextSize(1, 13.0f);
        this.valueTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        this.valueTextView.setLines(1);
        this.valueTextView.setMaxLines(1);
        this.valueTextView.setSingleLine(true);
        this.valueTextView.setPadding(0, 0, 0, 0);
        this.valueTextView.setEllipsize(TruncateAt.END);
        View view = this.valueTextView;
        int i2 = (LocaleController.isRTL ? 5 : 3) | 48;
        float f2 = LocaleController.isRTL ? 64.0f : 17.0f;
        if (!LocaleController.isRTL) {
            f = 64.0f;
        }
        addView(view, LayoutHelper.createFrame(-2, -2.0f, i2, f2, 35.0f, f, BitmapDescriptorFactory.HUE_RED));
        this.checkBox = new Switch2(context);
        View view2 = this.checkBox;
        if (!LocaleController.isRTL) {
            i = 5;
        }
        addView(view2, LayoutHelper.createFrame(44, 40.0f, i | 16, 14.0f, BitmapDescriptorFactory.HUE_RED, 14.0f, BitmapDescriptorFactory.HUE_RED));
    }

    public boolean isChecked() {
        return this.checkBox.isChecked();
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine((float) getPaddingLeft(), (float) (getHeight() - 1), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }

    protected void onMeasure(int i, int i2) {
        if (this.isMultiline) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
            return;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(this.valueTextView.getVisibility() == 0 ? 64.0f : 48.0f), 1073741824));
    }

    public void setChecked(boolean z) {
        this.checkBox.setChecked(z, true);
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (z) {
            this.textView.setAlpha(1.0f);
            this.valueTextView.setAlpha(1.0f);
            this.checkBox.setAlpha(1.0f);
            return;
        }
        this.checkBox.setAlpha(0.5f);
        this.textView.setAlpha(0.5f);
        this.valueTextView.setAlpha(0.5f);
    }

    public void setTextAndCheck(String str, boolean z, boolean z2) {
        this.textView.setText(str);
        this.isMultiline = false;
        this.checkBox.setChecked(z, false);
        this.needDivider = z2;
        this.valueTextView.setVisibility(8);
        LayoutParams layoutParams = (LayoutParams) this.textView.getLayoutParams();
        layoutParams.height = -1;
        layoutParams.topMargin = 0;
        this.textView.setLayoutParams(layoutParams);
        setWillNotDraw(!z2);
    }

    public void setTextAndValueAndCheck(String str, String str2, boolean z, boolean z2, boolean z3) {
        this.textView.setText(str);
        this.valueTextView.setText(str2);
        this.checkBox.setChecked(z, false);
        this.needDivider = z3;
        this.valueTextView.setVisibility(0);
        this.isMultiline = z2;
        if (z2) {
            this.valueTextView.setLines(0);
            this.valueTextView.setMaxLines(0);
            this.valueTextView.setSingleLine(false);
            this.valueTextView.setEllipsize(null);
            this.valueTextView.setPadding(0, 0, 0, AndroidUtilities.dp(11.0f));
        } else {
            this.valueTextView.setLines(1);
            this.valueTextView.setMaxLines(1);
            this.valueTextView.setSingleLine(true);
            this.valueTextView.setEllipsize(TruncateAt.END);
            this.valueTextView.setPadding(0, 0, 0, 0);
        }
        LayoutParams layoutParams = (LayoutParams) this.textView.getLayoutParams();
        layoutParams.height = -2;
        layoutParams.topMargin = AndroidUtilities.dp(10.0f);
        this.textView.setLayoutParams(layoutParams);
        setWillNotDraw(!z3);
    }
}
