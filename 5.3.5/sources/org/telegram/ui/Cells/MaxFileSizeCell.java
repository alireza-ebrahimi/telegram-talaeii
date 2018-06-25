package org.telegram.ui.Cells;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.SeekBarView;
import org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate;

public class MaxFileSizeCell extends FrameLayout {
    private long maxSize;
    private SeekBarView seekBarView;
    private SimpleTextView sizeTextView;
    private TextView textView;

    /* renamed from: org.telegram.ui.Cells.MaxFileSizeCell$2 */
    class C21742 implements SeekBarViewDelegate {
        C21742() {
        }

        public void onSeekBarDrag(float progress) {
            int size;
            if (MaxFileSizeCell.this.maxSize <= 10485760) {
                size = (int) (((float) MaxFileSizeCell.this.maxSize) * progress);
            } else if (progress <= 0.8f) {
                size = (int) (((float) 104857600) * (progress / 0.8f));
            } else {
                size = (int) (((float) 104857600) + ((((float) (MaxFileSizeCell.this.maxSize - ((long) 104857600))) * (progress - 0.8f)) / 0.2f));
            }
            MaxFileSizeCell.this.sizeTextView.setText(LocaleController.formatString("AutodownloadSizeLimitUpTo", R.string.AutodownloadSizeLimitUpTo, new Object[]{AndroidUtilities.formatFileSize((long) size)}));
            MaxFileSizeCell.this.didChangedSizeValue(size);
        }
    }

    public MaxFileSizeCell(Context context) {
        int i;
        int i2;
        int i3 = 5;
        super(context);
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setText(LocaleController.getString("AutodownloadSizeLimit", R.string.AutodownloadSizeLimit));
        this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        this.textView.setEllipsize(TruncateAt.END);
        View view = this.textView;
        if (LocaleController.isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        addView(view, LayoutHelper.createFrame(-1, -1.0f, i | 48, LocaleController.isRTL ? 64.0f : 17.0f, 13.0f, LocaleController.isRTL ? 17.0f : 64.0f, 0.0f));
        this.sizeTextView = new SimpleTextView(context);
        this.sizeTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText6));
        this.sizeTextView.setTextSize(16);
        SimpleTextView simpleTextView = this.sizeTextView;
        if (LocaleController.isRTL) {
            i2 = 3;
        } else {
            i2 = 5;
        }
        simpleTextView.setGravity(i2 | 48);
        view = this.sizeTextView;
        if (!LocaleController.isRTL) {
            i3 = 3;
        }
        addView(view, LayoutHelper.createFrame(-1, -1.0f, i3 | 48, LocaleController.isRTL ? 17.0f : 64.0f, 13.0f, LocaleController.isRTL ? 64.0f : 17.0f, 0.0f));
        this.seekBarView = new SeekBarView(context) {
            public boolean onTouchEvent(MotionEvent event) {
                if (event.getAction() == 0) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.onTouchEvent(event);
            }
        };
        this.seekBarView.setReportChanges(true);
        this.seekBarView.setDelegate(new C21742());
        addView(this.seekBarView, LayoutHelper.createFrame(-1, 30.0f, 51, 4.0f, 40.0f, 4.0f, 0.0f));
    }

    protected void didChangedSizeValue(int value) {
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(80.0f), 1073741824));
    }

    public void setSize(long size, long max) {
        float progress;
        this.maxSize = max;
        if (this.maxSize <= 10485760) {
            progress = ((float) size) / ((float) this.maxSize);
        } else if (size <= ((long) 104857600)) {
            progress = (((float) size) / ((float) 104857600)) * 0.8f;
        } else {
            progress = 0.8f + ((((float) (size - ((long) 104857600))) / ((float) (this.maxSize - ((long) 104857600)))) * 0.2f);
        }
        this.seekBarView.setProgress(progress);
        this.sizeTextView.setText(LocaleController.formatString("AutodownloadSizeLimitUpTo", R.string.AutodownloadSizeLimitUpTo, new Object[]{AndroidUtilities.formatFileSize(size)}));
    }
}
