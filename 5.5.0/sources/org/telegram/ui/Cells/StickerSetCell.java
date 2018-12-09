package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RadialProgressView;

public class StickerSetCell extends FrameLayout {
    private BackupImageView imageView;
    private boolean needDivider;
    private ImageView optionsButton;
    private RadialProgressView progressView;
    private Rect rect = new Rect();
    private TLRPC$TL_messages_stickerSet stickersSet;
    private TextView textView;
    private TextView valueTextView;

    public StickerSetCell(Context context, int i) {
        int i2 = 5;
        int i3 = 3;
        super(context);
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setEllipsize(TruncateAt.END);
        this.textView.setGravity(LocaleController.isRTL ? 5 : 3);
        addView(this.textView, LayoutHelper.createFrame(-2, -2.0f, LocaleController.isRTL ? 5 : 3, LocaleController.isRTL ? 40.0f : 71.0f, 10.0f, LocaleController.isRTL ? 71.0f : 40.0f, BitmapDescriptorFactory.HUE_RED));
        this.valueTextView = new TextView(context);
        this.valueTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
        this.valueTextView.setTextSize(1, 13.0f);
        this.valueTextView.setLines(1);
        this.valueTextView.setMaxLines(1);
        this.valueTextView.setSingleLine(true);
        this.valueTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        addView(this.valueTextView, LayoutHelper.createFrame(-2, -2.0f, LocaleController.isRTL ? 5 : 3, LocaleController.isRTL ? 40.0f : 71.0f, 35.0f, LocaleController.isRTL ? 71.0f : 40.0f, BitmapDescriptorFactory.HUE_RED));
        this.imageView = new BackupImageView(context);
        this.imageView.setAspectFit(true);
        addView(this.imageView, LayoutHelper.createFrame(48, 48.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 12.0f, 8.0f, LocaleController.isRTL ? 12.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        View view;
        if (i == 2) {
            this.progressView = new RadialProgressView(getContext());
            this.progressView.setProgressColor(Theme.getColor(Theme.key_dialogProgressCircle));
            this.progressView.setSize(AndroidUtilities.dp(30.0f));
            view = this.progressView;
            if (!LocaleController.isRTL) {
                i2 = 3;
            }
            addView(view, LayoutHelper.createFrame(48, 48.0f, i2 | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 12.0f, 8.0f, LocaleController.isRTL ? 12.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        } else if (i != 0) {
            this.optionsButton = new ImageView(context);
            this.optionsButton.setFocusable(false);
            this.optionsButton.setScaleType(ScaleType.CENTER);
            this.optionsButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_stickers_menuSelector)));
            if (i == 1) {
                this.optionsButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_stickers_menu), Mode.MULTIPLY));
                this.optionsButton.setImageResource(R.drawable.msg_actions);
                View view2 = this.optionsButton;
                if (!LocaleController.isRTL) {
                    i3 = 5;
                }
                addView(view2, LayoutHelper.createFrame(40, 40, i3 | 48));
            } else if (i == 3) {
                this.optionsButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_featuredStickers_addedIcon), Mode.MULTIPLY));
                this.optionsButton.setImageResource(R.drawable.sticker_added);
                view = this.optionsButton;
                if (!LocaleController.isRTL) {
                    i3 = 5;
                }
                addView(view, LayoutHelper.createFrame(40, 40.0f, i3 | 48, (float) (LocaleController.isRTL ? 10 : 0), 12.0f, (float) (LocaleController.isRTL ? 0 : 10), BitmapDescriptorFactory.HUE_RED));
            }
        }
    }

    public TLRPC$TL_messages_stickerSet getStickersSet() {
        return this.stickersSet;
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine(BitmapDescriptorFactory.HUE_RED, (float) (getHeight() - 1), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(64.0f), 1073741824));
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!(VERSION.SDK_INT < 21 || getBackground() == null || this.optionsButton == null)) {
            this.optionsButton.getHitRect(this.rect);
            if (this.rect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setChecked(boolean z) {
        if (this.optionsButton != null) {
            this.optionsButton.setVisibility(z ? 0 : 4);
        }
    }

    public void setOnOptionsClick(OnClickListener onClickListener) {
        if (this.optionsButton != null) {
            this.optionsButton.setOnClickListener(onClickListener);
        }
    }

    public void setStickersSet(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet, boolean z) {
        this.needDivider = z;
        this.stickersSet = tLRPC$TL_messages_stickerSet;
        this.imageView.setVisibility(0);
        if (this.progressView != null) {
            this.progressView.setVisibility(4);
        }
        this.textView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        this.textView.setText(this.stickersSet.set.title);
        if (this.stickersSet.set.archived) {
            this.textView.setAlpha(0.5f);
            this.valueTextView.setAlpha(0.5f);
            this.imageView.setAlpha(0.5f);
        } else {
            this.textView.setAlpha(1.0f);
            this.valueTextView.setAlpha(1.0f);
            this.imageView.setAlpha(1.0f);
        }
        ArrayList arrayList = tLRPC$TL_messages_stickerSet.documents;
        if (arrayList == null || arrayList.isEmpty()) {
            this.valueTextView.setText(LocaleController.formatPluralString("Stickers", 0));
            return;
        }
        this.valueTextView.setText(LocaleController.formatPluralString("Stickers", arrayList.size()));
        Document document = (Document) arrayList.get(0);
        if (document.thumb != null && document.thumb.location != null) {
            this.imageView.setImage(document.thumb.location, null, "webp", null);
        }
    }

    public void setText(String str, String str2, int i, boolean z) {
        this.needDivider = z;
        this.stickersSet = null;
        this.textView.setText(str);
        this.valueTextView.setText(str2);
        if (TextUtils.isEmpty(str2)) {
            this.textView.setTranslationY((float) AndroidUtilities.dp(10.0f));
        } else {
            this.textView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        }
        if (i != 0) {
            this.imageView.setImageResource(i, Theme.getColor(Theme.key_windowBackgroundWhiteGrayIcon));
            this.imageView.setVisibility(0);
            if (this.progressView != null) {
                this.progressView.setVisibility(4);
                return;
            }
            return;
        }
        this.imageView.setVisibility(4);
        if (this.progressView != null) {
            this.progressView.setVisibility(0);
        }
    }
}
