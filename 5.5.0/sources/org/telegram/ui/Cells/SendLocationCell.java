package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocationController;
import org.telegram.messenger.LocationController.SharingLocationInfo;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.LayoutHelper;

public class SendLocationCell extends FrameLayout {
    private SimpleTextView accurateTextView;
    private long dialogId;
    private ImageView imageView;
    private Runnable invalidateRunnable = new C40241();
    private RectF rect;
    private SimpleTextView titleTextView;

    /* renamed from: org.telegram.ui.Cells.SendLocationCell$1 */
    class C40241 implements Runnable {
        C40241() {
        }

        public void run() {
            SendLocationCell.this.checkText();
            SendLocationCell.this.invalidate(((int) SendLocationCell.this.rect.left) - 5, ((int) SendLocationCell.this.rect.top) - 5, ((int) SendLocationCell.this.rect.right) + 5, ((int) SendLocationCell.this.rect.bottom) + 5);
            AndroidUtilities.runOnUIThread(SendLocationCell.this.invalidateRunnable, 1000);
        }
    }

    public SendLocationCell(Context context, boolean z) {
        int i = 5;
        super(context);
        this.imageView = new ImageView(context);
        Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(40.0f), Theme.getColor(z ? Theme.key_location_sendLiveLocationBackground : Theme.key_location_sendLocationBackground), Theme.getColor(z ? Theme.key_location_sendLiveLocationBackground : Theme.key_location_sendLocationBackground));
        Drawable drawable;
        Drawable combinedDrawable;
        if (z) {
            this.rect = new RectF();
            drawable = getResources().getDrawable(R.drawable.livelocationpin);
            drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_location_sendLocationIcon), Mode.MULTIPLY));
            combinedDrawable = new CombinedDrawable(createSimpleSelectorCircleDrawable, drawable);
            combinedDrawable.setCustomSize(AndroidUtilities.dp(40.0f), AndroidUtilities.dp(40.0f));
            this.imageView.setBackgroundDrawable(combinedDrawable);
            AndroidUtilities.runOnUIThread(this.invalidateRunnable, 1000);
            setWillNotDraw(false);
        } else {
            drawable = getResources().getDrawable(R.drawable.pin);
            drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_location_sendLocationIcon), Mode.MULTIPLY));
            combinedDrawable = new CombinedDrawable(createSimpleSelectorCircleDrawable, drawable);
            combinedDrawable.setCustomSize(AndroidUtilities.dp(40.0f), AndroidUtilities.dp(40.0f));
            combinedDrawable.setIconSize(AndroidUtilities.dp(24.0f), AndroidUtilities.dp(24.0f));
            this.imageView.setBackgroundDrawable(combinedDrawable);
        }
        addView(this.imageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 17.0f, 13.0f, LocaleController.isRTL ? 17.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.titleTextView = new SimpleTextView(context);
        this.titleTextView.setTextSize(16);
        if (z) {
            this.titleTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText2));
        } else {
            this.titleTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText7));
        }
        this.titleTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        this.titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        addView(this.titleTextView, LayoutHelper.createFrame(-1, 20.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 16.0f : 73.0f, 12.0f, LocaleController.isRTL ? 73.0f : 16.0f, BitmapDescriptorFactory.HUE_RED));
        this.accurateTextView = new SimpleTextView(context);
        this.accurateTextView.setTextSize(14);
        this.accurateTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3));
        this.accurateTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        View view = this.accurateTextView;
        if (!LocaleController.isRTL) {
            i = 3;
        }
        addView(view, LayoutHelper.createFrame(-1, 20.0f, i | 48, LocaleController.isRTL ? 16.0f : 73.0f, 37.0f, LocaleController.isRTL ? 73.0f : 16.0f, BitmapDescriptorFactory.HUE_RED));
    }

    private void checkText() {
        SharingLocationInfo sharingLocationInfo = LocationController.getInstance().getSharingLocationInfo(this.dialogId);
        if (sharingLocationInfo != null) {
            setText(LocaleController.getString("StopLiveLocation", R.string.StopLiveLocation), LocaleController.formatLocationUpdateDate(sharingLocationInfo.messageObject.messageOwner.edit_date != 0 ? (long) sharingLocationInfo.messageObject.messageOwner.edit_date : (long) sharingLocationInfo.messageObject.messageOwner.date));
        } else {
            setText(LocaleController.getString("SendLiveLocation", R.string.SendLiveLocation), LocaleController.getString("SendLiveLocationInfo", R.string.SendLiveLocationInfo));
        }
    }

    private ImageView getImageView() {
        return this.imageView;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.rect != null) {
            AndroidUtilities.runOnUIThread(this.invalidateRunnable, 1000);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AndroidUtilities.cancelRunOnUIThread(this.invalidateRunnable);
    }

    protected void onDraw(Canvas canvas) {
        SharingLocationInfo sharingLocationInfo = LocationController.getInstance().getSharingLocationInfo(this.dialogId);
        if (sharingLocationInfo != null) {
            int currentTime = ConnectionsManager.getInstance().getCurrentTime();
            if (sharingLocationInfo.stopTime >= currentTime) {
                float abs = ((float) Math.abs(sharingLocationInfo.stopTime - currentTime)) / ((float) sharingLocationInfo.period);
                if (LocaleController.isRTL) {
                    this.rect.set((float) AndroidUtilities.dp(13.0f), (float) AndroidUtilities.dp(18.0f), (float) AndroidUtilities.dp(43.0f), (float) AndroidUtilities.dp(48.0f));
                } else {
                    this.rect.set((float) (getMeasuredWidth() - AndroidUtilities.dp(43.0f)), (float) AndroidUtilities.dp(18.0f), (float) (getMeasuredWidth() - AndroidUtilities.dp(13.0f)), (float) AndroidUtilities.dp(48.0f));
                }
                int color = Theme.getColor("location_liveLocationProgress");
                Theme.chat_radialProgress2Paint.setColor(color);
                Theme.chat_livePaint.setColor(color);
                canvas.drawArc(this.rect, -90.0f, -360.0f * abs, false, Theme.chat_radialProgress2Paint);
                String formatLocationLeftTime = LocaleController.formatLocationLeftTime(Math.abs(sharingLocationInfo.stopTime - currentTime));
                canvas.drawText(formatLocationLeftTime, this.rect.centerX() - (Theme.chat_livePaint.measureText(formatLocationLeftTime) / 2.0f), (float) AndroidUtilities.dp(37.0f), Theme.chat_livePaint);
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(66.0f), 1073741824));
    }

    public void setDialogId(long j) {
        this.dialogId = j;
        checkText();
    }

    public void setHasLocation(boolean z) {
        float f = 1.0f;
        if (LocationController.getInstance().getSharingLocationInfo(this.dialogId) == null) {
            this.titleTextView.setAlpha(z ? 1.0f : 0.5f);
            this.accurateTextView.setAlpha(z ? 1.0f : 0.5f);
            ImageView imageView = this.imageView;
            if (!z) {
                f = 0.5f;
            }
            imageView.setAlpha(f);
        }
    }

    public void setText(String str, String str2) {
        this.titleTextView.setText(str);
        this.accurateTextView.setText(str2);
    }
}
