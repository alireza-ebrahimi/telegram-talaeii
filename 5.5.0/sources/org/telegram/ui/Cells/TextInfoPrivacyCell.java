package org.telegram.ui.Cells;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class TextInfoPrivacyCell extends FrameLayout {
    private TextView textView;

    public TextInfoPrivacyCell(Context context) {
        int i = 5;
        super(context);
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4));
        this.textView.setLinkTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteLinkText));
        this.textView.setTextSize(1, 14.0f);
        this.textView.setGravity(LocaleController.isRTL ? 5 : 3);
        this.textView.setPadding(0, AndroidUtilities.dp(10.0f), 0, AndroidUtilities.dp(17.0f));
        this.textView.setMovementMethod(LinkMovementMethod.getInstance());
        this.textView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        View view = this.textView;
        if (!LocaleController.isRTL) {
            i = 3;
        }
        addView(view, LayoutHelper.createFrame(-2, -2.0f, i | 48, 17.0f, BitmapDescriptorFactory.HUE_RED, 17.0f, BitmapDescriptorFactory.HUE_RED));
    }

    public TextView getTextView() {
        return this.textView;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
    }

    public void setEnabled(boolean z, ArrayList<Animator> arrayList) {
        float f = 1.0f;
        if (arrayList != null) {
            TextView textView = this.textView;
            String str = "alpha";
            float[] fArr = new float[1];
            if (!z) {
                f = 0.5f;
            }
            fArr[0] = f;
            arrayList.add(ObjectAnimator.ofFloat(textView, str, fArr));
            return;
        }
        textView = this.textView;
        if (!z) {
            f = 0.5f;
        }
        textView.setAlpha(f);
    }

    public void setText(CharSequence charSequence) {
        if (charSequence == null) {
            this.textView.setPadding(0, AndroidUtilities.dp(2.0f), 0, 0);
        } else {
            this.textView.setPadding(0, AndroidUtilities.dp(10.0f), 0, AndroidUtilities.dp(17.0f));
        }
        this.textView.setText(charSequence);
    }

    public void setTextColor(int i) {
        this.textView.setTextColor(i);
    }
}
