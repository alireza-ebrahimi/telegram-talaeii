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
        this.textView.setTypeface(AndroidUtilities.getTypeface(""));
        this.textView.setGravity(LocaleController.isRTL ? 5 : 3);
        addView(this.textView);
        this.valueTextView = new SimpleTextView(context);
        this.valueTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteValueText));
        this.valueTextView.setTextSize(16);
        this.valueTextView.setTypeface(AndroidUtilities.getTypeface(""));
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

    public SimpleTextView getValueTextView() {
        return this.valueTextView;
    }

    public ImageView getValueImageView() {
        return this.valueImageView;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = AndroidUtilities.dp(48.0f);
        this.valueTextView.measure(MeasureSpec.makeMeasureSpec(width - AndroidUtilities.dp(24.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
        this.textView.measure(MeasureSpec.makeMeasureSpec(width - AndroidUtilities.dp(95.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
        this.imageView.measure(MeasureSpec.makeMeasureSpec(width, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE));
        this.valueImageView.measure(MeasureSpec.makeMeasureSpec(width, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE));
        setMeasuredDimension(width, AndroidUtilities.dp(48.0f));
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int height = bottom - top;
        int width = right - left;
        int viewTop = (height - this.valueTextView.getTextHeight()) / 2;
        int viewLeft = LocaleController.isRTL ? AndroidUtilities.dp(24.0f) : 0;
        this.valueTextView.layout(viewLeft, viewTop, this.valueTextView.getMeasuredWidth() + viewLeft, this.valueTextView.getMeasuredHeight() + viewTop);
        viewTop = (height - this.textView.getTextHeight()) / 2;
        viewLeft = !LocaleController.isRTL ? AndroidUtilities.dp(71.0f) : AndroidUtilities.dp(24.0f);
        this.textView.layout(viewLeft, viewTop, this.textView.getMeasuredWidth() + viewLeft, this.textView.getMeasuredHeight() + viewTop);
        viewTop = AndroidUtilities.dp(5.0f);
        viewLeft = !LocaleController.isRTL ? AndroidUtilities.dp(16.0f) : (width - this.imageView.getMeasuredWidth()) - AndroidUtilities.dp(16.0f);
        this.imageView.layout(viewLeft, viewTop, this.imageView.getMeasuredWidth() + viewLeft, this.imageView.getMeasuredHeight() + viewTop);
        viewTop = (height - this.valueImageView.getMeasuredHeight()) / 2;
        viewLeft = LocaleController.isRTL ? AndroidUtilities.dp(24.0f) : (width - this.valueImageView.getMeasuredWidth()) - AndroidUtilities.dp(24.0f);
        this.valueImageView.layout(viewLeft, viewTop, this.valueImageView.getMeasuredWidth() + viewLeft, this.valueImageView.getMeasuredHeight() + viewTop);
    }

    public void setTextColor(int color) {
        this.textView.setTextColor(color);
    }

    public void setText(String text) {
        this.textView.setText(text);
        this.valueTextView.setText(null);
        this.imageView.setVisibility(4);
        this.valueTextView.setVisibility(4);
        this.valueImageView.setVisibility(4);
    }

    public void setTextAndIcon(String text, int resId) {
        this.textView.setText(text);
        this.valueTextView.setText(null);
        this.imageView.setImageResource(resId);
        this.imageView.setVisibility(0);
        this.valueTextView.setVisibility(4);
        this.valueImageView.setVisibility(4);
        this.imageView.setPadding(0, AndroidUtilities.dp(7.0f), 0, 0);
    }

    public void setTextAndValue(String text, String value) {
        this.textView.setText(text);
        this.valueTextView.setText(value);
        this.valueTextView.setVisibility(0);
        this.imageView.setVisibility(4);
        this.valueImageView.setVisibility(4);
    }

    public void setTextAndValueAndIcon(String text, String value, int resId) {
        this.textView.setText(text);
        this.valueTextView.setText(value);
        this.valueTextView.setVisibility(0);
        this.valueImageView.setVisibility(4);
        this.imageView.setVisibility(0);
        this.imageView.setPadding(0, AndroidUtilities.dp(7.0f), 0, 0);
        this.imageView.setImageResource(resId);
    }

    public void setTextAndValueDrawable(String text, Drawable drawable) {
        this.textView.setText(text);
        this.valueTextView.setText(null);
        this.valueImageView.setVisibility(0);
        this.valueImageView.setImageDrawable(drawable);
        this.valueTextView.setVisibility(4);
        this.imageView.setVisibility(4);
        this.imageView.setPadding(0, AndroidUtilities.dp(7.0f), 0, 0);
    }
}
