package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$StickerSetCovered;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.Switch;

public class ArchivedStickerSetCell extends FrameLayout {
    private Switch checkBox;
    private BackupImageView imageView;
    private boolean needDivider;
    private OnCheckedChangeListener onCheckedChangeListener;
    private Rect rect = new Rect();
    private TLRPC$StickerSetCovered stickersSet;
    private TextView textView;
    private TextView valueTextView;

    /* renamed from: org.telegram.ui.Cells.ArchivedStickerSetCell$1 */
    class C21571 implements OnClickListener {
        C21571() {
        }

        public void onClick(View v) {
        }
    }

    public ArchivedStickerSetCell(Context context, boolean needCheckBox) {
        int i;
        int i2;
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
        View view = this.textView;
        if (LocaleController.isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        addView(view, LayoutHelper.createFrame(-2, -2.0f, i, 71.0f, 10.0f, 71.0f, 0.0f));
        this.valueTextView = new TextView(context);
        this.valueTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
        this.valueTextView.setTextSize(1, 13.0f);
        this.valueTextView.setLines(1);
        this.valueTextView.setMaxLines(1);
        this.valueTextView.setSingleLine(true);
        TextView textView = this.valueTextView;
        if (LocaleController.isRTL) {
            i2 = 5;
        } else {
            i2 = 3;
        }
        textView.setGravity(i2);
        view = this.valueTextView;
        if (LocaleController.isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        addView(view, LayoutHelper.createFrame(-2, -2.0f, i, 71.0f, 35.0f, 71.0f, 0.0f));
        this.imageView = new BackupImageView(context);
        this.imageView.setAspectFit(true);
        view = this.imageView;
        if (LocaleController.isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        addView(view, LayoutHelper.createFrame(48, 48.0f, i | 48, LocaleController.isRTL ? 0.0f : 12.0f, 8.0f, LocaleController.isRTL ? 12.0f : 0.0f, 0.0f));
        if (needCheckBox) {
            this.checkBox = new Switch(context);
            this.checkBox.setDuplicateParentStateEnabled(false);
            this.checkBox.setFocusable(false);
            this.checkBox.setFocusableInTouchMode(false);
            View view2 = this.checkBox;
            if (!LocaleController.isRTL) {
                i3 = 5;
            }
            addView(view2, LayoutHelper.createFrame(-2, -2.0f, i3 | 16, 14.0f, 0.0f, 14.0f, 0.0f));
        }
    }

    public TextView getTextView() {
        return this.textView;
    }

    public TextView getValueTextView() {
        return this.valueTextView;
    }

    public Switch getCheckBox() {
        return this.checkBox;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(64.0f), 1073741824));
    }

    public void setStickersSet(TLRPC$StickerSetCovered set, boolean divider) {
        this.needDivider = divider;
        this.stickersSet = set;
        setWillNotDraw(!this.needDivider);
        this.textView.setText(this.stickersSet.set.title);
        this.valueTextView.setText(LocaleController.formatPluralString("Stickers", set.set.count));
        if (set.cover != null && set.cover.thumb != null && set.cover.thumb.location != null) {
            this.imageView.setImage(set.cover.thumb.location, null, "webp", null);
        } else if (!set.covers.isEmpty()) {
            this.imageView.setImage(((TLRPC$Document) set.covers.get(0)).thumb.location, null, "webp", null);
        }
    }

    public void setOnCheckClick(OnCheckedChangeListener listener) {
        Switch switchR = this.checkBox;
        this.onCheckedChangeListener = listener;
        switchR.setOnCheckedChangeListener(listener);
        this.checkBox.setOnClickListener(new C21571());
    }

    public void setChecked(boolean checked) {
        this.checkBox.setOnCheckedChangeListener(null);
        this.checkBox.setChecked(checked);
        this.checkBox.setOnCheckedChangeListener(this.onCheckedChangeListener);
    }

    public boolean isChecked() {
        return this.checkBox != null && this.checkBox.isChecked();
    }

    public TLRPC$StickerSetCovered getStickersSet() {
        return this.stickersSet;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.checkBox != null) {
            this.checkBox.getHitRect(this.rect);
            if (this.rect.contains((int) event.getX(), (int) event.getY())) {
                event.offsetLocation(-this.checkBox.getX(), -this.checkBox.getY());
                return this.checkBox.onTouchEvent(event);
            }
        }
        return super.onTouchEvent(event);
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine(0.0f, (float) (getHeight() - 1), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }
}
