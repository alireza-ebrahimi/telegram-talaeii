package org.telegram.ui.Cells;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    class C40122 implements SeekBarViewDelegate {
        C40122() {
        }

        public void onSeekBarDrag(float f) {
            int access$000;
            if (MaxFileSizeCell.this.maxSize <= 10485760) {
                access$000 = (int) (((float) MaxFileSizeCell.this.maxSize) * f);
            } else if (f <= 0.8f) {
                access$000 = (int) (((float) 104857600) * (f / 0.8f));
            } else {
                access$000 = (int) (((((float) (MaxFileSizeCell.this.maxSize - ((long) 104857600))) * (f - 0.8f)) / 0.2f) + ((float) 104857600));
            }
            MaxFileSizeCell.this.sizeTextView.setText(LocaleController.formatString("AutodownloadSizeLimitUpTo", R.string.AutodownloadSizeLimitUpTo, new Object[]{AndroidUtilities.formatFileSize((long) access$000)}));
            MaxFileSizeCell.this.didChangedSizeValue(access$000);
        }
    }

    public MaxFileSizeCell(Context context) {
        int i = 5;
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
        addView(this.textView, LayoutHelper.createFrame(-1, -1.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 64.0f : 17.0f, 13.0f, LocaleController.isRTL ? 17.0f : 64.0f, BitmapDescriptorFactory.HUE_RED));
        this.sizeTextView = new SimpleTextView(context);
        this.sizeTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText6));
        this.sizeTextView.setTextSize(16);
        this.sizeTextView.setGravity((LocaleController.isRTL ? 3 : 5) | 48);
        View view = this.sizeTextView;
        if (!LocaleController.isRTL) {
            i = 3;
        }
        addView(view, LayoutHelper.createFrame(-1, -1.0f, i | 48, LocaleController.isRTL ? 17.0f : 64.0f, 13.0f, LocaleController.isRTL ? 64.0f : 17.0f, BitmapDescriptorFactory.HUE_RED));
        this.seekBarView = new SeekBarView(context) {
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.onTouchEvent(motionEvent);
            }
        };
        this.seekBarView.setReportChanges(true);
        this.seekBarView.setDelegate(new C40122());
        addView(this.seekBarView, LayoutHelper.createFrame(-1, 30.0f, 51, 4.0f, 40.0f, 4.0f, BitmapDescriptorFactory.HUE_RED));
    }

    protected void didChangedSizeValue(int i) {
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(80.0f), 1073741824));
    }

    public void setSize(long j, long j2) {
        this.maxSize = j2;
        float f = this.maxSize > 10485760 ? j <= ((long) 104857600) ? (((float) j) / ((float) 104857600)) * 0.8f : ((((float) (j - ((long) 104857600))) / ((float) (this.maxSize - ((long) 104857600)))) * 0.2f) + 0.8f : ((float) j) / ((float) this.maxSize);
        this.seekBarView.setProgress(f);
        this.sizeTextView.setText(LocaleController.formatString("AutodownloadSizeLimitUpTo", R.string.AutodownloadSizeLimitUpTo, new Object[]{AndroidUtilities.formatFileSize(j)}));
    }
}
