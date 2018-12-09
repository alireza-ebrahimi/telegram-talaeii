package org.telegram.ui.Cells;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class HeaderCell extends FrameLayout {
    private TextView textView = new TextView(getContext());

    public HeaderCell(Context context) {
        int i = 5;
        super(context);
        this.textView.setTextSize(1, 15.0f);
        this.textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueHeader));
        this.textView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        View view = this.textView;
        if (!LocaleController.isRTL) {
            i = 3;
        }
        addView(view, LayoutHelper.createFrame(-1, -1.0f, i | 48, 17.0f, 15.0f, 17.0f, BitmapDescriptorFactory.HUE_RED));
    }

    public TextView getTextView() {
        return this.textView;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(38.0f), 1073741824));
    }

    public void setText(String str) {
        this.textView.setText(str);
    }
}
