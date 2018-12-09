package org.telegram.ui.Cells;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class ShadowSectionCell extends View {
    private int size = 12;

    public ShadowSectionCell(Context context) {
        super(context);
        setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp((float) this.size), 1073741824));
    }

    public void setSize(int i) {
        this.size = i;
    }
}
