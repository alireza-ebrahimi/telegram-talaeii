package org.telegram.customization.util;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

/* renamed from: org.telegram.customization.util.d */
public class C2873d extends FrameLayout {
    /* renamed from: a */
    private TextView f9488a;

    public C2873d(Context context) {
        super(context);
        this.f9488a = new TextView(context);
        this.f9488a.setTextColor(context.getResources().getColor(R.color.green));
        this.f9488a.setTextSize(1, 12.0f);
        this.f9488a.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.f9488a.setLines(1);
        this.f9488a.setMaxLines(1);
        this.f9488a.setSingleLine(true);
        this.f9488a.setGravity(19);
        this.f9488a.setCompoundDrawablePadding(AndroidUtilities.dp(34.0f));
        addView(this.f9488a, LayoutHelper.createFrame(-1, -1.0f, 51, 14.0f, BitmapDescriptorFactory.HUE_RED, 10.0f, BitmapDescriptorFactory.HUE_RED));
    }

    /* renamed from: a */
    public void m13351a(String str, int i) {
        try {
            this.f9488a.setText(str);
        } catch (Throwable th) {
            FileLog.m13728e(th);
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.f9488a.setTextColor(Theme.getColor(Theme.key_chats_menuItemText));
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
    }
}
