package org.telegram.ui.Cells;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.tgnet.TLRPC$TL_messageMediaInvoice;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;

public class PaymentInfoCell extends FrameLayout {
    private TextView detailExTextView;
    private TextView detailTextView;
    private BackupImageView imageView;
    private TextView nameTextView;

    public PaymentInfoCell(Context context) {
        super(context);
        this.imageView = new BackupImageView(context);
        addView(this.imageView, LayoutHelper.createFrame(100, 100.0f, LocaleController.isRTL ? 5 : 3, 10.0f, 10.0f, 10.0f, BitmapDescriptorFactory.HUE_RED));
        this.nameTextView = new TextView(context);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setTextSize(1, 16.0f);
        this.nameTextView.setLines(1);
        this.nameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.nameTextView.setMaxLines(1);
        this.nameTextView.setSingleLine(true);
        this.nameTextView.setEllipsize(TruncateAt.END);
        this.nameTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        addView(this.nameTextView, LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 10.0f : 123.0f, 9.0f, LocaleController.isRTL ? 123.0f : 10.0f, BitmapDescriptorFactory.HUE_RED));
        this.detailTextView = new TextView(context);
        this.detailTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.detailTextView.setTextSize(1, 14.0f);
        this.detailTextView.setMaxLines(3);
        this.detailTextView.setEllipsize(TruncateAt.END);
        this.detailTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        addView(this.detailTextView, LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 10.0f : 123.0f, 33.0f, LocaleController.isRTL ? 123.0f : 10.0f, BitmapDescriptorFactory.HUE_RED));
        this.detailExTextView = new TextView(context);
        this.detailExTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
        this.detailExTextView.setTextSize(1, 13.0f);
        this.detailExTextView.setLines(1);
        this.detailExTextView.setMaxLines(1);
        this.detailExTextView.setSingleLine(true);
        this.detailExTextView.setEllipsize(TruncateAt.END);
        this.detailExTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        addView(this.detailExTextView, LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 10.0f : 123.0f, 90.0f, LocaleController.isRTL ? 123.0f : 10.0f, BitmapDescriptorFactory.HUE_RED));
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int bottom = this.detailTextView.getBottom() + AndroidUtilities.dp(3.0f);
        this.detailExTextView.layout(this.detailExTextView.getLeft(), bottom, this.detailExTextView.getRight(), this.detailExTextView.getMeasuredHeight() + bottom);
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(120.0f), 1073741824));
    }

    public void setInvoice(TLRPC$TL_messageMediaInvoice tLRPC$TL_messageMediaInvoice, String str) {
        this.nameTextView.setText(tLRPC$TL_messageMediaInvoice.title);
        this.detailTextView.setText(tLRPC$TL_messageMediaInvoice.description);
        this.detailExTextView.setText(str);
        float minTabletSide = ((float) 640) / ((float) ((AndroidUtilities.isTablet() ? (int) (((float) AndroidUtilities.getMinTabletSide()) * 0.7f) : (int) (((float) Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) * 0.7f)) - AndroidUtilities.dp(2.0f)));
        int i = (int) (((float) 640) / minTabletSide);
        int i2 = (int) (((float) 360) / minTabletSide);
        if (tLRPC$TL_messageMediaInvoice.photo == null || !tLRPC$TL_messageMediaInvoice.photo.mime_type.startsWith("image/")) {
            this.nameTextView.setLayoutParams(LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 17.0f, 9.0f, 17.0f, BitmapDescriptorFactory.HUE_RED));
            this.detailTextView.setLayoutParams(LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 17.0f, 33.0f, 17.0f, BitmapDescriptorFactory.HUE_RED));
            this.detailExTextView.setLayoutParams(LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 17.0f, 90.0f, 17.0f, BitmapDescriptorFactory.HUE_RED));
            this.imageView.setVisibility(8);
            return;
        }
        this.nameTextView.setLayoutParams(LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 10.0f : 123.0f, 9.0f, LocaleController.isRTL ? 123.0f : 10.0f, BitmapDescriptorFactory.HUE_RED));
        this.detailTextView.setLayoutParams(LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 10.0f : 123.0f, 33.0f, LocaleController.isRTL ? 123.0f : 10.0f, BitmapDescriptorFactory.HUE_RED));
        this.detailExTextView.setLayoutParams(LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 10.0f : 123.0f, 90.0f, LocaleController.isRTL ? 123.0f : 10.0f, BitmapDescriptorFactory.HUE_RED));
        this.imageView.setVisibility(0);
        this.imageView.getImageReceiver().setImage(tLRPC$TL_messageMediaInvoice.photo, null, String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}), null, null, null, -1, null, 1);
    }
}
