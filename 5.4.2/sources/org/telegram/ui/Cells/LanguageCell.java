package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocaleController.LocaleInfo;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class LanguageCell extends FrameLayout {
    private ImageView checkImage;
    private LocaleInfo currentLocale;
    private boolean isDialog;
    private boolean needDivider;
    private TextView textView;
    private TextView textView2;

    public LanguageCell(Context context, boolean z) {
        float f;
        float f2;
        int i = 3;
        super(context);
        setWillNotDraw(false);
        this.isDialog = z;
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(z ? Theme.key_dialogTextBlack : Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setEllipsize(TruncateAt.END);
        this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        View view = this.textView;
        int i2 = (LocaleController.isRTL ? 5 : 3) | 48;
        if (LocaleController.isRTL) {
            f = 71.0f;
        } else {
            f = (float) (z ? 23 : 16);
        }
        float f3 = (float) (this.isDialog ? 4 : 6);
        if (LocaleController.isRTL) {
            f2 = (float) (z ? 23 : 16);
        } else {
            f2 = 71.0f;
        }
        addView(view, LayoutHelper.createFrame(-1, -1.0f, i2, f, f3, f2, BitmapDescriptorFactory.HUE_RED));
        this.textView2 = new TextView(context);
        this.textView2.setTextColor(Theme.getColor(z ? Theme.key_dialogTextGray3 : Theme.key_windowBackgroundWhiteGrayText3));
        this.textView2.setTextSize(1, 13.0f);
        this.textView2.setLines(1);
        this.textView2.setMaxLines(1);
        this.textView2.setSingleLine(true);
        this.textView2.setEllipsize(TruncateAt.END);
        this.textView2.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        view = this.textView2;
        i2 = (LocaleController.isRTL ? 5 : 3) | 48;
        if (LocaleController.isRTL) {
            f = 71.0f;
        } else {
            f = (float) (z ? 23 : 16);
        }
        f3 = (float) (this.isDialog ? 25 : 28);
        if (LocaleController.isRTL) {
            f2 = (float) (z ? 23 : 16);
        } else {
            f2 = 71.0f;
        }
        addView(view, LayoutHelper.createFrame(-1, -1.0f, i2, f, f3, f2, BitmapDescriptorFactory.HUE_RED));
        this.checkImage = new ImageView(context);
        this.checkImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_featuredStickers_addedIcon), Mode.MULTIPLY));
        this.checkImage.setImageResource(R.drawable.sticker_added);
        View view2 = this.checkImage;
        if (!LocaleController.isRTL) {
            i = 5;
        }
        addView(view2, LayoutHelper.createFrame(19, 14.0f, i | 16, 23.0f, BitmapDescriptorFactory.HUE_RED, 23.0f, BitmapDescriptorFactory.HUE_RED));
    }

    public LocaleInfo getCurrentLocale() {
        return this.currentLocale;
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine((float) getPaddingLeft(), (float) (getHeight() - 1), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(this.isDialog ? 48.0f : 54.0f), 1073741824));
    }

    public void setLanguage(LocaleInfo localeInfo, String str, boolean z) {
        CharSequence charSequence;
        TextView textView = this.textView;
        if (str == null) {
            charSequence = localeInfo.name;
        }
        textView.setText(charSequence);
        this.textView2.setText(localeInfo.nameEnglish);
        this.currentLocale = localeInfo;
        this.needDivider = z;
    }

    public void setLanguageSelected(boolean z) {
        this.checkImage.setVisibility(z ? 0 : 4);
    }

    public void setValue(String str, String str2) {
        this.textView.setText(str);
        this.textView2.setText(str2);
        this.checkImage.setVisibility(4);
        this.currentLocale = null;
        this.needDivider = false;
    }
}
