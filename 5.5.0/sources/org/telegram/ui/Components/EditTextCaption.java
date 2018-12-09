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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

    private void applyTextStyleToSelection(TypefaceSpan typefaceSpan) {
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        Editable text = getText();
        URLSpanUserMention[] uRLSpanUserMentionArr = (URLSpanUserMention[]) text.getSpans(selectionStart, selectionEnd, URLSpanUserMention.class);
        if (uRLSpanUserMentionArr == null || uRLSpanUserMentionArr.length <= 0) {
            TypefaceSpan[] typefaceSpanArr = (TypefaceSpan[]) text.getSpans(selectionStart, selectionEnd, TypefaceSpan.class);
            if (typefaceSpanArr != null && typefaceSpanArr.length > 0) {
                for (TypefaceSpan typefaceSpan2 : typefaceSpanArr) {
                    int spanStart = text.getSpanStart(typefaceSpan2);
                    int spanEnd = text.getSpanEnd(typefaceSpan2);
                    text.removeSpan(typefaceSpan2);
                    if (spanStart < selectionStart) {
                        text.setSpan(new TypefaceSpan(typefaceSpan2.getTypeface()), spanStart, selectionStart, 33);
                    }
                    if (spanEnd > selectionEnd) {
                        text.setSpan(new TypefaceSpan(typefaceSpan2.getTypeface()), selectionEnd, spanEnd, 33);
                    }
                }
            }
            if (typefaceSpan != null) {
                text.setSpan(typefaceSpan, selectionStart, selectionEnd, 33);
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

    private Callback overrideCallback(final Callback callback) {
        return new Callback() {
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                boolean z = true;
                if (menuItem.getItemId() == R.id.menu_regular) {
                    EditTextCaption.this.makeSelectedRegular();
                    actionMode.finish();
                } else if (menuItem.getItemId() == R.id.menu_bold) {
                    EditTextCaption.this.makeSelectedBold();
                    actionMode.finish();
                } else if (menuItem.getItemId() == R.id.menu_italic) {
                    EditTextCaption.this.makeSelectedItalic();
                    actionMode.finish();
                } else {
                    try {
                        z = callback.onActionItemClicked(actionMode, menuItem);
                    } catch (Exception e) {
                    }
                }
                return z;
            }

            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                EditTextCaption.this.copyPasteShowed = true;
                return callback.onCreateActionMode(actionMode, menu);
            }

            public void onDestroyActionMode(ActionMode actionMode) {
                EditTextCaption.this.copyPasteShowed = false;
                callback.onDestroyActionMode(actionMode);
            }

            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return callback.onPrepareActionMode(actionMode, menu);
            }
        };
    }

    public String getCaption() {
        return this.caption;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (this.captionLayout != null && this.userNameLength == length()) {
                Paint paint = getPaint();
                int color = getPaint().getColor();
                paint.setColor(this.hintColor);
                canvas.save();
                canvas.translate((float) this.xOffset, (float) this.yOffset);
                this.captionLayout.draw(canvas);
                canvas.restore();
                paint.setColor(color);
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    @SuppressLint({"DrawAllocation"})
    protected void onMeasure(int i, int i2) {
        try {
            super.onMeasure(i, i2);
        } catch (Throwable e) {
            setMeasuredDimension(MeasureSpec.getSize(i), AndroidUtilities.dp(51.0f));
            FileLog.e(e);
        }
        this.captionLayout = null;
        if (this.caption != null && this.caption.length() > 0) {
            CharSequence text = getText();
            if (text.length() > 1 && text.charAt(0) == '@') {
                int indexOf = TextUtils.indexOf(text, ' ');
                if (indexOf != -1) {
                    TextPaint paint = getPaint();
                    int ceil = (int) Math.ceil((double) paint.measureText(text, 0, indexOf + 1));
                    int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
                    this.userNameLength = text.subSequence(0, indexOf + 1).length();
                    CharSequence ellipsize = TextUtils.ellipsize(this.caption, paint, (float) (measuredWidth - ceil), TruncateAt.END);
                    this.xOffset = ceil;
                    try {
                        this.captionLayout = new StaticLayout(ellipsize, getPaint(), measuredWidth - ceil, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                        if (this.captionLayout.getLineCount() > 0) {
                            this.xOffset = (int) (((float) this.xOffset) + (-this.captionLayout.getLineLeft(0)));
                        }
                        this.yOffset = ((getMeasuredHeight() - this.captionLayout.getLineBottom(0)) / 2) + AndroidUtilities.dp(0.5f);
                    } catch (Throwable e2) {
                        FileLog.e(e2);
                    }
                }
            }
        }
    }

    public void onWindowFocusChanged(boolean z) {
        if (VERSION.SDK_INT >= 23 || z || !this.copyPasteShowed) {
            super.onWindowFocusChanged(z);
        }
    }

    public void setCaption(String str) {
        if ((this.caption != null && this.caption.length() != 0) || (str != null && str.length() != 0)) {
            if (this.caption == null || str == null || !this.caption.equals(str)) {
                this.caption = str;
                if (this.caption != null) {
                    this.caption = this.caption.replace('\n', ' ');
                }
                requestLayout();
            }
        }
    }

    public void setHintColor(int i) {
        super.setHintColor(i);
        this.hintColor = i;
        invalidate();
    }

    public ActionMode startActionMode(Callback callback) {
        return super.startActionMode(overrideCallback(callback));
    }

    public ActionMode startActionMode(Callback callback, int i) {
        return super.startActionMode(overrideCallback(callback), i);
    }
}
