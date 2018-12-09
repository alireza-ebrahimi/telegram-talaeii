package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.tgnet.TLRPC$TL_messageMediaVenue;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;

public class LocationCell extends FrameLayout {
    private TextView addressTextView;
    private BackupImageView imageView;
    private TextView nameTextView;
    private boolean needDivider;

    public LocationCell(Context context) {
        int i = 16;
        int i2 = 5;
        super(context);
        this.imageView = new BackupImageView(context);
        this.imageView.setBackgroundResource(R.drawable.round_grey);
        this.imageView.setSize(AndroidUtilities.dp(30.0f), AndroidUtilities.dp(30.0f));
        this.imageView.getImageReceiver().setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3), Mode.MULTIPLY));
        addView(this.imageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 17.0f, 8.0f, LocaleController.isRTL ? 17.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.nameTextView = new TextView(context);
        this.nameTextView.setTextSize(1, 16.0f);
        this.nameTextView.setMaxLines(1);
        this.nameTextView.setEllipsize(TruncateAt.END);
        this.nameTextView.setSingleLine(true);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.nameTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        addView(this.nameTextView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, (float) (LocaleController.isRTL ? 16 : 72), 5.0f, (float) (LocaleController.isRTL ? 72 : 16), BitmapDescriptorFactory.HUE_RED));
        this.addressTextView = new TextView(context);
        this.addressTextView.setTextSize(1, 14.0f);
        this.addressTextView.setMaxLines(1);
        this.addressTextView.setEllipsize(TruncateAt.END);
        this.addressTextView.setSingleLine(true);
        this.addressTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3));
        this.addressTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        View view = this.addressTextView;
        if (!LocaleController.isRTL) {
            i2 = 3;
        }
        int i3 = i2 | 48;
        float f = (float) (LocaleController.isRTL ? 16 : 72);
        if (LocaleController.isRTL) {
            i = 72;
        }
        addView(view, LayoutHelper.createFrame(-2, -2.0f, i3, f, 30.0f, (float) i, BitmapDescriptorFactory.HUE_RED));
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine((float) AndroidUtilities.dp(72.0f), (float) (getHeight() - 1), (float) getWidth(), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(56.0f), 1073741824));
    }

    public void setLocation(TLRPC$TL_messageMediaVenue tLRPC$TL_messageMediaVenue, String str, boolean z) {
        this.needDivider = z;
        this.nameTextView.setText(tLRPC$TL_messageMediaVenue.title);
        this.addressTextView.setText(tLRPC$TL_messageMediaVenue.address);
        this.imageView.setImage(str, null, null);
        setWillNotDraw(!z);
    }
}
