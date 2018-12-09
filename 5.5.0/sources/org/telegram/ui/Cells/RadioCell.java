package org.telegram.ui.Cells;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RadioButton;

public class RadioCell extends FrameLayout {
    private boolean needDivider;
    private RadioButton radioButton;
    private TextView textView;

    public RadioCell(Context context) {
        int i = 3;
        super(context);
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setEllipsize(TruncateAt.END);
        this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        addView(this.textView, LayoutHelper.createFrame(-1, -1.0f, (LocaleController.isRTL ? 5 : 3) | 48, 17.0f, BitmapDescriptorFactory.HUE_RED, 17.0f, BitmapDescriptorFactory.HUE_RED));
        this.radioButton = new RadioButton(context);
        this.radioButton.setSize(AndroidUtilities.dp(20.0f));
        this.radioButton.setColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_radioBackgroundChecked));
        View view = this.radioButton;
        if (!LocaleController.isRTL) {
            i = 5;
        }
        addView(view, LayoutHelper.createFrame(22, 22.0f, i | 48, (float) (LocaleController.isRTL ? 18 : 0), 13.0f, (float) (LocaleController.isRTL ? 0 : 18), BitmapDescriptorFactory.HUE_RED));
    }

    public boolean isChecked() {
        return this.radioButton.isChecked();
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine((float) getPaddingLeft(), (float) (getHeight() - 1), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(MeasureSpec.getSize(i), (this.needDivider ? 1 : 0) + AndroidUtilities.dp(48.0f));
        int measuredWidth = ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - AndroidUtilities.dp(34.0f);
        this.radioButton.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(22.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(22.0f), 1073741824));
        this.textView.measure(MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
    }

    public void setChecked(boolean z, boolean z2) {
        this.radioButton.setChecked(z, z2);
    }

    public void setEnabled(boolean z, ArrayList<Animator> arrayList) {
        float f = 1.0f;
        if (arrayList != null) {
            TextView textView = this.textView;
            String str = "alpha";
            float[] fArr = new float[1];
            fArr[0] = z ? 1.0f : 0.5f;
            arrayList.add(ObjectAnimator.ofFloat(textView, str, fArr));
            RadioButton radioButton = this.radioButton;
            String str2 = "alpha";
            float[] fArr2 = new float[1];
            if (!z) {
                f = 0.5f;
            }
            fArr2[0] = f;
            arrayList.add(ObjectAnimator.ofFloat(radioButton, str2, fArr2));
            return;
        }
        this.textView.setAlpha(z ? 1.0f : 0.5f);
        radioButton = this.radioButton;
        if (!z) {
            f = 0.5f;
        }
        radioButton.setAlpha(f);
    }

    public void setText(String str, boolean z, boolean z2) {
        boolean z3 = false;
        this.textView.setText(str);
        this.radioButton.setChecked(z, false);
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
