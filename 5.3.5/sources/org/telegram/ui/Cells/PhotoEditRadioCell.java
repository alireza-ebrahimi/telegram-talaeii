package org.telegram.ui.Cells;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RadioButton;

public class PhotoEditRadioCell extends FrameLayout {
    private int currentColor;
    private int currentType;
    private TextView nameTextView;
    private OnClickListener onClickListener;
    private LinearLayout tintButtonsContainer;
    private final int[] tintHighlighsColors = new int[]{0, -1076602, -1388894, -859780, -5968466, -7742235, -13726776, -3303195};
    private final int[] tintShadowColors = new int[]{0, -45747, -753630, -13056, -8269183, -9321002, -16747844, -10080879};

    /* renamed from: org.telegram.ui.Cells.PhotoEditRadioCell$1 */
    class C21761 implements OnClickListener {
        C21761() {
        }

        public void onClick(View v) {
            RadioButton radioButton = (RadioButton) v;
            if (PhotoEditRadioCell.this.currentType == 0) {
                PhotoEditRadioCell.this.currentColor = PhotoEditRadioCell.this.tintShadowColors[((Integer) radioButton.getTag()).intValue()];
            } else {
                PhotoEditRadioCell.this.currentColor = PhotoEditRadioCell.this.tintHighlighsColors[((Integer) radioButton.getTag()).intValue()];
            }
            PhotoEditRadioCell.this.updateSelectedTintButton(true);
            PhotoEditRadioCell.this.onClickListener.onClick(PhotoEditRadioCell.this);
        }
    }

    public PhotoEditRadioCell(Context context) {
        super(context);
        this.nameTextView = new TextView(context);
        this.nameTextView.setGravity(5);
        this.nameTextView.setTextColor(-1);
        this.nameTextView.setTextSize(1, 12.0f);
        this.nameTextView.setMaxLines(1);
        this.nameTextView.setSingleLine(true);
        this.nameTextView.setEllipsize(TruncateAt.END);
        addView(this.nameTextView, LayoutHelper.createFrame(80, -2.0f, 19, 0.0f, 0.0f, 0.0f, 0.0f));
        this.tintButtonsContainer = new LinearLayout(context);
        this.tintButtonsContainer.setOrientation(0);
        for (int a = 0; a < this.tintShadowColors.length; a++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setSize(AndroidUtilities.dp(20.0f));
            radioButton.setTag(Integer.valueOf(a));
            this.tintButtonsContainer.addView(radioButton, LayoutHelper.createLinear(0, -1, 1.0f / ((float) this.tintShadowColors.length)));
            radioButton.setOnClickListener(new C21761());
        }
        addView(this.tintButtonsContainer, LayoutHelper.createFrame(-1, 40.0f, 51, 96.0f, 0.0f, 24.0f, 0.0f));
    }

    public int getCurrentColor() {
        return this.currentColor;
    }

    private void updateSelectedTintButton(boolean animated) {
        int childCount = this.tintButtonsContainer.getChildCount();
        for (int a = 0; a < childCount; a++) {
            View child = this.tintButtonsContainer.getChildAt(a);
            if (child instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) child;
                int num = ((Integer) radioButton.getTag()).intValue();
                radioButton.setChecked(this.currentColor == (this.currentType == 0 ? this.tintShadowColors[num] : this.tintHighlighsColors[num]), animated);
                int i = num == 0 ? -1 : this.currentType == 0 ? this.tintShadowColors[num] : this.tintHighlighsColors[num];
                int i2 = num == 0 ? -1 : this.currentType == 0 ? this.tintShadowColors[num] : this.tintHighlighsColors[num];
                radioButton.setColor(i, i2);
            }
        }
    }

    public void setOnClickListener(OnClickListener l) {
        this.onClickListener = l;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(40.0f), 1073741824));
    }

    public void setIconAndTextAndValue(String text, int type, int value) {
        this.currentType = type;
        this.currentColor = value;
        this.nameTextView.setText(text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase());
        updateSelectedTintButton(false);
    }
}
