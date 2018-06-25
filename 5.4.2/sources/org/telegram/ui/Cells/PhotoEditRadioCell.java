package org.telegram.ui.Cells;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    class C40141 implements OnClickListener {
        C40141() {
        }

        public void onClick(View view) {
            RadioButton radioButton = (RadioButton) view;
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
        addView(this.nameTextView, LayoutHelper.createFrame(80, -2.0f, 19, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.tintButtonsContainer = new LinearLayout(context);
        this.tintButtonsContainer.setOrientation(0);
        for (int i = 0; i < this.tintShadowColors.length; i++) {
            View radioButton = new RadioButton(context);
            radioButton.setSize(AndroidUtilities.dp(20.0f));
            radioButton.setTag(Integer.valueOf(i));
            this.tintButtonsContainer.addView(radioButton, LayoutHelper.createLinear(0, -1, 1.0f / ((float) this.tintShadowColors.length)));
            radioButton.setOnClickListener(new C40141());
        }
        addView(this.tintButtonsContainer, LayoutHelper.createFrame(-1, 40.0f, 51, 96.0f, BitmapDescriptorFactory.HUE_RED, 24.0f, BitmapDescriptorFactory.HUE_RED));
    }

    private void updateSelectedTintButton(boolean z) {
        int childCount = this.tintButtonsContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.tintButtonsContainer.getChildAt(i);
            if (childAt instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) childAt;
                int intValue = ((Integer) radioButton.getTag()).intValue();
                radioButton.setChecked(this.currentColor == (this.currentType == 0 ? this.tintShadowColors[intValue] : this.tintHighlighsColors[intValue]), z);
                int i2 = intValue == 0 ? -1 : this.currentType == 0 ? this.tintShadowColors[intValue] : this.tintHighlighsColors[intValue];
                int i3 = intValue == 0 ? -1 : this.currentType == 0 ? this.tintShadowColors[intValue] : this.tintHighlighsColors[intValue];
                radioButton.setColor(i2, i3);
            }
        }
    }

    public int getCurrentColor() {
        return this.currentColor;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(40.0f), 1073741824));
    }

    public void setIconAndTextAndValue(String str, int i, int i2) {
        this.currentType = i;
        this.currentColor = i2;
        this.nameTextView.setText(str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase());
        updateSelectedTintButton(false);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
