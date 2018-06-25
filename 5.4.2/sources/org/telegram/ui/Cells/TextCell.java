package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;

public class TextCell extends FrameLayout {
    private ImageView imageView;
    private SimpleTextView textView;
    private ImageView valueImageView;
    private SimpleTextView valueTextView;

    public TextCell(Context context) {
        int i = 3;
        super(context);
        this.textView = new SimpleTextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(16);
        this.textView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        this.textView.setGravity(LocaleController.isRTL ? 5 : 3);
        addView(this.textView);
        this.valueTextView = new SimpleTextView(context);
        this.valueTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteValueText));
        this.valueTextView.setTextSize(16);
        this.valueTextView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
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
        this.valueImageView = new ImageView(context);
        this.valueImageView.setScaleType(ScaleType.CENTER);
        addView(this.valueImageView);
    }

    public SimpleTextView getTextView() {
        return this.textView;
    }

    public ImageView getValueImageView() {
        return this.valueImageView;
    }

    public SimpleTextView getValueTextView() {
        return this.valueTextView;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i4 - i2;
        int i6 = i3 - i;
        int textHeight = (i5 - this.valueTextView.getTextHeight()) / 2;
        int dp = LocaleController.isRTL ? AndroidUtilities.dp(24.0f) : 0;
        this.valueTextView.layout(dp, textHeight, this.valueTextView.getMeasuredWidth() + dp, this.valueTextView.getMeasuredHeight() + textHeight);
        textHeight = (i5 - this.textView.getTextHeight()) / 2;
        dp = !LocaleController.isRTL ? AndroidUtilities.dp(71.0f) : AndroidUtilities.dp(24.0f);
        this.textView.layout(dp, textHeight, this.textView.getMeasuredWidth() + dp, this.textView.getMeasuredHeight() + textHeight);
        textHeight = AndroidUtilities.dp(5.0f);
        dp = !LocaleController.isRTL ? AndroidUtilities.dp(16.0f) : (i6 - this.imageView.getMeasuredWidth()) - AndroidUtilities.dp(16.0f);
        this.imageView.layout(dp, textHeight, this.imageView.getMeasuredWidth() + dp, this.imageView.getMeasuredHeight() + textHeight);
        i5 = (i5 - this.valueImageView.getMeasuredHeight()) / 2;
        dp = LocaleController.isRTL ? AndroidUtilities.dp(24.0f) : (i6 - this.valueImageView.getMeasuredWidth()) - AndroidUtilities.dp(24.0f);
        this.valueImageView.layout(dp, i5, this.valueImageView.getMeasuredWidth() + dp, this.valueImageView.getMeasuredHeight() + i5);
    }

    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int dp = AndroidUtilities.dp(48.0f);
        this.valueTextView.measure(MeasureSpec.makeMeasureSpec(size - AndroidUtilities.dp(24.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
        this.textView.measure(MeasureSpec.makeMeasureSpec(size - AndroidUtilities.dp(95.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
        this.imageView.measure(MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(dp, Integer.MIN_VALUE));
        this.valueImageView.measure(MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(dp, Integer.MIN_VALUE));
        setMeasuredDimension(size, AndroidUtilities.dp(48.0f));
    }

    public void setText(String str) {
        this.textView.setText(str);
        this.valueTextView.setText(null);
        this.imageView.setVisibility(4);
        this.valueTextView.setVisibility(4);
        this.valueImageView.setVisibility(4);
    }

    public void setTextAndIcon(String str, int i) {
        this.textView.setText(str);
        this.valueTextView.setText(null);
        this.imageView.setImageResource(i);
        this.imageView.setVisibility(0);
        this.valueTextView.setVisibility(4);
        this.valueImageView.setVisibility(4);
        this.imageView.setPadding(0, AndroidUtilities.dp(7.0f), 0, 0);
    }

    public void setTextAndValue(String str, String str2) {
        this.textView.setText(str);
        this.valueTextView.setText(str2);
        this.valueTextView.setVisibility(0);
        this.imageView.setVisibility(4);
        this.valueImageView.setVisibility(4);
    }

    public void setTextAndValueAndIcon(String str, String str2, int i) {
        this.textView.setText(str);
        this.valueTextView.setText(str2);
        this.valueTextView.setVisibility(0);
        this.valueImageView.setVisibility(4);
        this.imageView.setVisibility(0);
        this.imageView.setPadding(0, AndroidUtilities.dp(7.0f), 0, 0);
        this.imageView.setImageResource(i);
    }

    public void setTextAndValueDrawable(String str, Drawable drawable) {
        this.textView.setText(str);
        this.valueTextView.setText(null);
        this.valueImageView.setVisibility(0);
        this.valueImageView.setImageDrawable(drawable);
        this.valueTextView.setVisibility(4);
        this.imageView.setVisibility(4);
        this.imageView.setPadding(0, AndroidUtilities.dp(7.0f), 0, 0);
    }

    public void setTextColor(int i) {
        this.textView.setTextColor(i);
    }
}
