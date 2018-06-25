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
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocaleController$LocaleInfo;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class LanguageCell extends FrameLayout {
    private ImageView checkImage;
    private LocaleController$LocaleInfo currentLocale;
    private boolean isDialog;
    private boolean needDivider;
    private TextView textView;
    private TextView textView2;

    public LanguageCell(Context context, boolean dialog) {
        int i;
        int i2;
        float f;
        float f2;
        int i3 = 3;
        super(context);
        setWillNotDraw(false);
        this.isDialog = dialog;
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(dialog ? Theme.key_dialogTextBlack : Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setEllipsize(TruncateAt.END);
        TextView textView = this.textView;
        if (LocaleController.isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        textView.setGravity(i | 48);
        View view = this.textView;
        if (LocaleController.isRTL) {
            i2 = 5;
        } else {
            i2 = 3;
        }
        i2 |= 48;
        if (LocaleController.isRTL) {
            f = 71.0f;
        } else {
            f = (float) (dialog ? 23 : 16);
        }
        float f3 = (float) (this.isDialog ? 4 : 6);
        if (LocaleController.isRTL) {
            f2 = (float) (dialog ? 23 : 16);
        } else {
            f2 = 71.0f;
        }
        addView(view, LayoutHelper.createFrame(-1, -1.0f, i2, f, f3, f2, 0.0f));
        this.textView2 = new TextView(context);
        this.textView2.setTextColor(Theme.getColor(dialog ? Theme.key_dialogTextGray3 : Theme.key_windowBackgroundWhiteGrayText3));
        this.textView2.setTextSize(1, 13.0f);
        this.textView2.setLines(1);
        this.textView2.setMaxLines(1);
        this.textView2.setSingleLine(true);
        this.textView2.setEllipsize(TruncateAt.END);
        textView = this.textView2;
        if (LocaleController.isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        textView.setGravity(i | 48);
        view = this.textView2;
        if (LocaleController.isRTL) {
            i2 = 5;
        } else {
            i2 = 3;
        }
        i2 |= 48;
        if (LocaleController.isRTL) {
            f = 71.0f;
        } else {
            f = (float) (dialog ? 23 : 16);
        }
        f3 = (float) (this.isDialog ? 25 : 28);
        if (LocaleController.isRTL) {
            f2 = (float) (dialog ? 23 : 16);
        } else {
            f2 = 71.0f;
        }
        addView(view, LayoutHelper.createFrame(-1, -1.0f, i2, f, f3, f2, 0.0f));
        this.checkImage = new ImageView(context);
        this.checkImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_featuredStickers_addedIcon), Mode.MULTIPLY));
        this.checkImage.setImageResource(R.drawable.sticker_added);
        View view2 = this.checkImage;
        if (!LocaleController.isRTL) {
            i3 = 5;
        }
        addView(view2, LayoutHelper.createFrame(19, 14.0f, i3 | 16, 23.0f, 0.0f, 23.0f, 0.0f));
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(this.isDialog ? 48.0f : 54.0f), 1073741824));
    }

    public void setLanguage(LocaleController$LocaleInfo language, String desc, boolean divider) {
        TextView textView = this.textView;
        if (desc == null) {
            desc = language.name;
        }
        textView.setText(desc);
        this.textView2.setText(language.nameEnglish);
        this.currentLocale = language;
        this.needDivider = divider;
    }

    public void setValue(String name, String nameEnglish) {
        this.textView.setText(name);
        this.textView2.setText(nameEnglish);
        this.checkImage.setVisibility(4);
        this.currentLocale = null;
        this.needDivider = false;
    }

    public LocaleController$LocaleInfo getCurrentLocale() {
        return this.currentLocale;
    }

    public void setLanguageSelected(boolean value) {
        this.checkImage.setVisibility(value ? 0 : 4);
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine((float) getPaddingLeft(), (float) (getHeight() - 1), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }
}
