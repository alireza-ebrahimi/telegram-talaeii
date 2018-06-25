package org.telegram.ui.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.MeasureSpec;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;

public class EditTextCaption extends EditTextBoldCursor {
    private String caption;
    private StaticLayout captionLayout;
    private boolean copyPasteShowed;
    private int hintColor;
    private int triesCount = 0;
    private int userNameLength;
    private int xOffset;
    private int yOffset;

    public EditTextCaption(Context context) {
        super(context);
    }

    public void setCaption(String value) {
        if ((this.caption != null && this.caption.length() != 0) || (value != null && value.length() != 0)) {
            if (this.caption == null || value == null || !this.caption.equals(value)) {
                this.caption = value;
                if (this.caption != null) {
                    this.caption = this.caption.replace('\n', ' ');
                }
                requestLayout();
            }
        }
    }

    private void makeSelectedBold() {
        applyTextStyleToSelection(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")));
    }

    private void makeSelectedItalic() {
        applyTextStyleToSelection(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/ritalic.ttf")));
    }

    private void makeSelectedRegular() {
        applyTextStyleToSelection(null);
    }

    private void applyTextStyleToSelection(TypefaceSpan span) {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        Editable editable = getText();
        URLSpanUserMention[] spansMentions = (URLSpanUserMention[]) editable.getSpans(start, end, URLSpanUserMention.class);
        if (spansMentions == null || spansMentions.length <= 0) {
            TypefaceSpan[] spans = (TypefaceSpan[]) editable.getSpans(start, end, TypefaceSpan.class);
            if (spans != null && spans.length > 0) {
                for (TypefaceSpan oldSpan : spans) {
                    int spanStart = editable.getSpanStart(oldSpan);
                    int spanEnd = editable.getSpanEnd(oldSpan);
                    editable.removeSpan(oldSpan);
                    if (spanStart < start) {
                        editable.setSpan(new TypefaceSpan(oldSpan.getTypeface()), spanStart, start, 33);
                    }
                    if (spanEnd > end) {
                        editable.setSpan(new TypefaceSpan(oldSpan.getTypeface()), end, spanEnd, 33);
                    }
                }
            }
            if (span != null) {
                editable.setSpan(span, start, end, 33);
            }
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (VERSION.SDK_INT >= 23 || hasWindowFocus || !this.copyPasteShowed) {
            super.onWindowFocusChanged(hasWindowFocus);
        }
    }

    private Callback overrideCallback(final Callback callback) {
        return new Callback() {
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                EditTextCaption.this.copyPasteShowed = true;
                return callback.onCreateActionMode(mode, menu);
            }

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return callback.onPrepareActionMode(mode, menu);
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                boolean z = true;
                if (item.getItemId() == R.id.menu_regular) {
                    EditTextCaption.this.makeSelectedRegular();
                    mode.finish();
                } else if (item.getItemId() == R.id.menu_bold) {
                    EditTextCaption.this.makeSelectedBold();
                    mode.finish();
                } else if (item.getItemId() == R.id.menu_italic) {
                    EditTextCaption.this.makeSelectedItalic();
                    mode.finish();
                } else {
                    try {
                        z = callback.onActionItemClicked(mode, item);
                    } catch (Exception e) {
                    }
                }
                return z;
            }

            public void onDestroyActionMode(ActionMode mode) {
                EditTextCaption.this.copyPasteShowed = false;
                callback.onDestroyActionMode(mode);
            }
        };
    }

    public ActionMode startActionMode(Callback callback, int type) {
        return super.startActionMode(overrideCallback(callback), type);
    }

    public ActionMode startActionMode(Callback callback) {
        return super.startActionMode(overrideCallback(callback));
    }

    @SuppressLint({"DrawAllocation"})
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } catch (Exception e) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), AndroidUtilities.dp(51.0f));
            FileLog.e(e);
        }
        this.captionLayout = null;
        if (this.caption != null && this.caption.length() > 0) {
            CharSequence text = getText();
            if (text.length() > 1 && text.charAt(0) == '@') {
                int index = TextUtils.indexOf(text, ' ');
                if (index != -1) {
                    TextPaint paint = getPaint();
                    int size = (int) Math.ceil((double) paint.measureText(text, 0, index + 1));
                    int width = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
                    this.userNameLength = text.subSequence(0, index + 1).length();
                    CharSequence captionFinal = TextUtils.ellipsize(this.caption, paint, (float) (width - size), TruncateAt.END);
                    this.xOffset = size;
                    try {
                        this.captionLayout = new StaticLayout(captionFinal, getPaint(), width - size, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                        if (this.captionLayout.getLineCount() > 0) {
                            this.xOffset = (int) (((float) this.xOffset) + (-this.captionLayout.getLineLeft(0)));
                        }
                        this.yOffset = ((getMeasuredHeight() - this.captionLayout.getLineBottom(0)) / 2) + AndroidUtilities.dp(0.5f);
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                }
            }
        }
    }

    public String getCaption() {
        return this.caption;
    }

    public void setHintColor(int value) {
        super.setHintColor(value);
        this.hintColor = value;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (this.captionLayout != null && this.userNameLength == length()) {
                Paint paint = getPaint();
                int oldColor = getPaint().getColor();
                paint.setColor(this.hintColor);
                canvas.save();
                canvas.translate((float) this.xOffset, (float) this.yOffset);
                this.captionLayout.draw(canvas);
                canvas.restore();
                paint.setColor(oldColor);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }
}
