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
import org.telegram.messenger.Emoji;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class TextDetailSettingsCell extends FrameLayout {
    private boolean multiline;
    private boolean needDivider;
    private TextView textView;
    private TextView valueTextView;

    public TextDetailSettingsCell(Context context) {
        int i = 5;
        super(context);
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setEllipsize(TruncateAt.END);
        this.textView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        addView(this.textView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 17.0f, 10.0f, 17.0f, BitmapDescriptorFactory.HUE_RED));
        this.valueTextView = new TextView(context);
        this.valueTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
        this.valueTextView.setTextSize(1, 13.0f);
        this.valueTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        this.valueTextView.setLines(1);
        this.valueTextView.setMaxLines(1);
        this.valueTextView.setSingleLine(true);
        this.valueTextView.setPadding(0, 0, 0, 0);
        this.valueTextView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        View view = this.valueTextView;
        if (!LocaleController.isRTL) {
            i = 3;
        }
        addView(view, LayoutHelper.createFrame(-2, -2.0f, i | 48, 17.0f, 35.0f, 17.0f, BitmapDescriptorFactory.HUE_RED));
    }

    public void invalidate() {
        super.invalidate();
        this.textView.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine((float) getPaddingLeft(), (float) (getHeight() - 1), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }

    protected void onMeasure(int i, int i2) {
        int i3 = 0;
        if (this.multiline) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
            return;
        }
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824);
        int dp = AndroidUtilities.dp(64.0f);
        if (this.needDivider) {
            i3 = 1;
        }
        super.onMeasure(makeMeasureSpec, MeasureSpec.makeMeasureSpec(i3 + dp, 1073741824));
    }

    public void setMultilineDetail(boolean z) {
        this.multiline = z;
        if (z) {
            this.valueTextView.setLines(0);
            this.valueTextView.setMaxLines(0);
            this.valueTextView.setSingleLine(false);
            this.valueTextView.setPadding(0, 0, 0, AndroidUtilities.dp(12.0f));
            return;
        }
        this.valueTextView.setLines(1);
        this.valueTextView.setMaxLines(1);
        this.valueTextView.setSingleLine(true);
        this.valueTextView.setPadding(0, 0, 0, 0);
    }

    public void setTextAndValue(String str, CharSequence charSequence, boolean z) {
        this.textView.setText(str);
        this.valueTextView.setText(charSequence);
        this.needDivider = z;
        setWillNotDraw(!z);
    }

    public void setTextWithEmojiAndValue(String str, CharSequence charSequence, boolean z) {
        boolean z2 = false;
        this.textView.setText(Emoji.replaceEmoji(str, this.textView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
        this.valueTextView.setText(charSequence);
        this.needDivider = z;
        if (!z) {
            z2 = true;
        }
        setWillNotDraw(z2);
    }
}
