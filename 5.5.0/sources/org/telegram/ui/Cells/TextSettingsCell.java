package org.telegram.ui.Cells;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class TextSettingsCell extends FrameLayout {
    private boolean needDivider;
    private TextView textView;
    private ImageView valueImageView;
    private TextView valueTextView;

    public TextSettingsCell(Context context) {
        int i = 3;
        super(context);
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setEllipsize(TruncateAt.END);
        this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        addView(this.textView, LayoutHelper.createFrame(-1, -1.0f, (LocaleController.isRTL ? 5 : 3) | 48, 17.0f, BitmapDescriptorFactory.HUE_RED, 17.0f, BitmapDescriptorFactory.HUE_RED));
        this.valueTextView = new TextView(context);
        this.valueTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteValueText));
        this.valueTextView.setTextSize(1, 16.0f);
        this.valueTextView.setLines(1);
        this.valueTextView.setMaxLines(1);
        this.valueTextView.setSingleLine(true);
        this.valueTextView.setEllipsize(TruncateAt.END);
        this.valueTextView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        this.valueTextView.setGravity((LocaleController.isRTL ? 3 : 5) | 16);
        addView(this.valueTextView, LayoutHelper.createFrame(-2, -1.0f, (LocaleController.isRTL ? 3 : 5) | 48, 17.0f, BitmapDescriptorFactory.HUE_RED, 17.0f, BitmapDescriptorFactory.HUE_RED));
        this.valueImageView = new ImageView(context);
        this.valueImageView.setScaleType(ScaleType.CENTER);
        this.valueImageView.setVisibility(4);
        this.valueImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayIcon), Mode.MULTIPLY));
        View view = this.valueImageView;
        if (!LocaleController.isRTL) {
            i = 5;
        }
        addView(view, LayoutHelper.createFrame(-2, -2.0f, i | 16, 17.0f, BitmapDescriptorFactory.HUE_RED, 17.0f, BitmapDescriptorFactory.HUE_RED));
    }

    public TextView getTextView() {
        return this.textView;
    }

    public TextView getValueTextView() {
        return this.valueTextView;
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine((float) getPaddingLeft(), (float) (getHeight() - 1), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(MeasureSpec.getSize(i), (this.needDivider ? 1 : 0) + AndroidUtilities.dp(48.0f));
        int measuredWidth = ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - AndroidUtilities.dp(34.0f);
        int i3 = measuredWidth / 2;
        if (this.valueImageView.getVisibility() == 0) {
            this.valueImageView.measure(MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
        }
        if (this.valueTextView.getVisibility() == 0) {
            this.valueTextView.measure(MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
            measuredWidth = (measuredWidth - this.valueTextView.getMeasuredWidth()) - AndroidUtilities.dp(8.0f);
        }
        this.textView.measure(MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
    }

    public void setEnabled(boolean z, ArrayList<Animator> arrayList) {
        float f = 1.0f;
        setEnabled(z);
        if (arrayList != null) {
            TextView textView = this.textView;
            String str = "alpha";
            float[] fArr = new float[1];
            fArr[0] = z ? 1.0f : 0.5f;
            arrayList.add(ObjectAnimator.ofFloat(textView, str, fArr));
            if (this.valueTextView.getVisibility() == 0) {
                textView = this.valueTextView;
                str = "alpha";
                fArr = new float[1];
                fArr[0] = z ? 1.0f : 0.5f;
                arrayList.add(ObjectAnimator.ofFloat(textView, str, fArr));
            }
            if (this.valueImageView.getVisibility() == 0) {
                ImageView imageView = this.valueImageView;
                String str2 = "alpha";
                float[] fArr2 = new float[1];
                if (!z) {
                    f = 0.5f;
                }
                fArr2[0] = f;
                arrayList.add(ObjectAnimator.ofFloat(imageView, str2, fArr2));
                return;
            }
            return;
        }
        this.textView.setAlpha(z ? 1.0f : 0.5f);
        if (this.valueTextView.getVisibility() == 0) {
            this.valueTextView.setAlpha(z ? 1.0f : 0.5f);
        }
        if (this.valueImageView.getVisibility() == 0) {
            imageView = this.valueImageView;
            if (!z) {
                f = 0.5f;
            }
            imageView.setAlpha(f);
        }
    }

    public void setText(String str, boolean z) {
        this.textView.setText(str);
        this.valueTextView.setVisibility(4);
        this.valueImageView.setVisibility(4);
        this.needDivider = z;
        setWillNotDraw(!z);
    }

    public void setTextAndIcon(String str, int i, boolean z) {
        boolean z2 = false;
        this.textView.setText(str);
        this.valueTextView.setVisibility(4);
        if (i != 0) {
            this.valueImageView.setVisibility(0);
            this.valueImageView.setImageResource(i);
        } else {
            this.valueImageView.setVisibility(4);
        }
        this.needDivider = z;
        if (!z) {
            z2 = true;
        }
        setWillNotDraw(z2);
    }

    public void setTextAndValue(String str, String str2, boolean z) {
        boolean z2 = false;
        this.textView.setText(str);
        this.valueImageView.setVisibility(4);
        if (str2 != null) {
            this.valueTextView.setText(str2);
            this.valueTextView.setVisibility(0);
        } else {
            this.valueTextView.setVisibility(4);
        }
        this.needDivider = z;
        if (!z) {
            z2 = true;
        }
        setWillNotDraw(z2);
        requestLayout();
    }

    public void setTextColor(int i) {
        this.textView.setTextColor(i);
    }

    public void setTextValueColor(int i) {
        this.valueTextView.setTextColor(i);
    }
}
