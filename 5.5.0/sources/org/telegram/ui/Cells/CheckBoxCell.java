package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.CheckBoxSquare;
import org.telegram.ui.Components.LayoutHelper;

public class CheckBoxCell extends FrameLayout {
    private CheckBoxSquare checkBox;
    private boolean needDivider;
    private TextView textView;
    private TextView valueTextView;

    public CheckBoxCell(Context context, boolean z) {
        int i = 17;
        int i2 = 5;
        super(context);
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(z ? Theme.key_dialogTextBlack : Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setEllipsize(TruncateAt.END);
        this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        addView(this.textView, LayoutHelper.createFrame(-1, -1.0f, (LocaleController.isRTL ? 5 : 3) | 48, (float) (LocaleController.isRTL ? 17 : 46), BitmapDescriptorFactory.HUE_RED, (float) (LocaleController.isRTL ? 46 : 17), BitmapDescriptorFactory.HUE_RED));
        this.valueTextView = new TextView(context);
        this.valueTextView.setTextColor(Theme.getColor(z ? Theme.key_dialogTextBlue : Theme.key_windowBackgroundWhiteValueText));
        this.valueTextView.setTextSize(1, 16.0f);
        this.valueTextView.setLines(1);
        this.valueTextView.setMaxLines(1);
        this.valueTextView.setSingleLine(true);
        this.valueTextView.setEllipsize(TruncateAt.END);
        this.valueTextView.setGravity((LocaleController.isRTL ? 3 : 5) | 16);
        addView(this.valueTextView, LayoutHelper.createFrame(-2, -1.0f, (LocaleController.isRTL ? 3 : 5) | 48, 17.0f, BitmapDescriptorFactory.HUE_RED, 17.0f, BitmapDescriptorFactory.HUE_RED));
        this.checkBox = new CheckBoxSquare(context, z);
        View view = this.checkBox;
        if (!LocaleController.isRTL) {
            i2 = 3;
        }
        i2 |= 48;
        float f = (float) (LocaleController.isRTL ? 0 : 17);
        if (!LocaleController.isRTL) {
            i = 0;
        }
        addView(view, LayoutHelper.createFrame(18, 18.0f, i2, f, 15.0f, (float) i, BitmapDescriptorFactory.HUE_RED));
    }

    public CheckBoxSquare getCheckBox() {
        return this.checkBox;
    }

    public TextView getTextView() {
        return this.textView;
    }

    public TextView getValueTextView() {
        return this.valueTextView;
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
        setMeasuredDimension(MeasureSpec.getSize(i), (this.needDivider ? 1 : 0) + AndroidUtilities.dp(48.0f));
        int measuredWidth = ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - AndroidUtilities.dp(34.0f);
        this.valueTextView.measure(MeasureSpec.makeMeasureSpec(measuredWidth / 2, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
        this.textView.measure(MeasureSpec.makeMeasureSpec((measuredWidth - this.valueTextView.getMeasuredWidth()) - AndroidUtilities.dp(8.0f), 1073741824), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
        this.checkBox.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(18.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(18.0f), 1073741824));
    }

    public void setChecked(boolean z, boolean z2) {
        this.checkBox.setChecked(z, z2);
    }

    public void setEnabled(boolean z) {
        float f = 1.0f;
        super.setEnabled(z);
        this.textView.setAlpha(z ? 1.0f : 0.5f);
        this.valueTextView.setAlpha(z ? 1.0f : 0.5f);
        CheckBoxSquare checkBoxSquare = this.checkBox;
        if (!z) {
            f = 0.5f;
        }
        checkBoxSquare.setAlpha(f);
    }

    public void setText(String str, String str2, boolean z, boolean z2) {
        boolean z3 = false;
        this.textView.setText(str);
        this.checkBox.setChecked(z, false);
        this.valueTextView.setText(str2);
        this.needDivider = z2;
        if (!z2) {
            z3 = true;
        }
        setWillNotDraw(z3);
    }

    public void setTextColor(int i) {
        this.textView.setTextColor(i);
    }
}
