package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;

public class ManageChatTextCell extends FrameLayout {
    private boolean divider;
    private ImageView imageView;
    private SimpleTextView textView;
    private SimpleTextView valueTextView;

    public ManageChatTextCell(Context context) {
        int i = 3;
        super(context);
        this.textView = new SimpleTextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(16);
        this.textView.setGravity(LocaleController.isRTL ? 5 : 3);
        addView(this.textView);
        this.valueTextView = new SimpleTextView(context);
        this.valueTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteValueText));
        this.valueTextView.setTextSize(16);
        SimpleTextView simpleTextView = this.valueTextView;
        if (!LocaleController.isRTL) {
            i = 5;
        }
        simpleTextView.setGravity(i);
        addView(this.valueTextView);
        this.imageView = new ImageView(context);
        this.imageView.setScaleType(ScaleType.CENTER);
        this.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayIcon), Mode.MULTIPLY));
        addView(this.imageView);
    }

    public SimpleTextView getTextView() {
        return this.textView;
    }

    public SimpleTextView getValueTextView() {
        return this.valueTextView;
    }

    protected void onDraw(Canvas canvas) {
        if (this.divider) {
            canvas.drawLine((float) AndroidUtilities.dp(71.0f), (float) (getMeasuredHeight() - 1), (float) getMeasuredWidth(), (float) (getMeasuredHeight() - 1), Theme.dividerPaint);
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i4 - i2;
        int i6 = i3 - i;
        int textHeight = (i5 - this.valueTextView.getTextHeight()) / 2;
        int dp = LocaleController.isRTL ? AndroidUtilities.dp(24.0f) : 0;
        this.valueTextView.layout(dp, textHeight, this.valueTextView.getMeasuredWidth() + dp, this.valueTextView.getMeasuredHeight() + textHeight);
        i5 = (i5 - this.textView.getTextHeight()) / 2;
        dp = !LocaleController.isRTL ? AndroidUtilities.dp(71.0f) : AndroidUtilities.dp(24.0f);
        this.textView.layout(dp, i5, this.textView.getMeasuredWidth() + dp, this.textView.getMeasuredHeight() + i5);
        i5 = AndroidUtilities.dp(9.0f);
        dp = !LocaleController.isRTL ? AndroidUtilities.dp(16.0f) : (i6 - this.imageView.getMeasuredWidth()) - AndroidUtilities.dp(16.0f);
        this.imageView.layout(dp, i5, this.imageView.getMeasuredWidth() + dp, this.imageView.getMeasuredHeight() + i5);
    }

    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int dp = AndroidUtilities.dp(48.0f);
        this.valueTextView.measure(MeasureSpec.makeMeasureSpec(size - AndroidUtilities.dp(24.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
        this.textView.measure(MeasureSpec.makeMeasureSpec(size - AndroidUtilities.dp(95.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
        this.imageView.measure(MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(dp, Integer.MIN_VALUE));
        setMeasuredDimension(size, (this.divider ? 1 : 0) + AndroidUtilities.dp(56.0f));
    }

    public void setText(String str, String str2, int i, boolean z) {
        boolean z2 = false;
        this.textView.setText(str);
        if (str2 != null) {
            this.valueTextView.setText(str2);
            this.valueTextView.setVisibility(0);
        } else {
            this.valueTextView.setVisibility(4);
        }
        this.imageView.setPadding(0, AndroidUtilities.dp(7.0f), 0, 0);
        this.imageView.setImageResource(i);
        this.divider = z;
        if (!this.divider) {
            z2 = true;
        }
        setWillNotDraw(z2);
    }

    public void setTextColor(int i) {
        this.textView.setTextColor(i);
    }
}
