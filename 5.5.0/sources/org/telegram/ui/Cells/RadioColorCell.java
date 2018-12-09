package org.telegram.ui.Cells;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RadioButton;

public class RadioColorCell extends FrameLayout {
    private RadioButton radioButton;
    private TextView textView;

    public RadioColorCell(Context context) {
        int i = 0;
        int i2 = 5;
        super(context);
        this.radioButton = new RadioButton(context);
        this.radioButton.setSize(AndroidUtilities.dp(20.0f));
        this.radioButton.setColor(Theme.getColor(Theme.key_dialogRadioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
        View view = this.radioButton;
        int i3 = (LocaleController.isRTL ? 5 : 3) | 48;
        float f = (float) (LocaleController.isRTL ? 0 : 18);
        if (LocaleController.isRTL) {
            i = 18;
        }
        addView(view, LayoutHelper.createFrame(22, 22.0f, i3, f, 13.0f, (float) i, BitmapDescriptorFactory.HUE_RED));
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        view = this.textView;
        if (!LocaleController.isRTL) {
            i2 = 3;
        }
        addView(view, LayoutHelper.createFrame(-2, -2.0f, i2 | 48, (float) (LocaleController.isRTL ? 17 : 51), 12.0f, (float) (LocaleController.isRTL ? 51 : 17), BitmapDescriptorFactory.HUE_RED));
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
    }

    public void setCheckColor(int i, int i2) {
        this.radioButton.setColor(i, i2);
    }

    public void setChecked(boolean z, boolean z2) {
        this.radioButton.setChecked(z, z2);
    }

    public void setTextAndValue(String str, boolean z) {
        this.textView.setText(str);
        this.radioButton.setChecked(z, false);
    }
}
