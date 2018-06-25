package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.widget.TextView;

public class AutoResizeTextView extends TextView {
    private static final int NO_LINE_LIMIT = -1;
    private final RectF _availableSpaceRect;
    private boolean _enableSizeCache;
    private boolean _initiallized;
    private int _maxLines;
    private float _maxTextSize;
    private float _minTextSize;
    private final SizeTester _sizeTester;
    private float _spacingAdd;
    private float _spacingMult;
    private final SparseIntArray _textCachedSizes;
    private int _widthLimit;
    private TextPaint paint;

    private interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.AutoResizeTextView$1 */
    class C07891 implements SizeTester {
        final RectF textRect = new RectF();

        C07891() {
        }

        public int onTestSize(int suggestedSize, RectF availableSPace) {
            AutoResizeTextView.this.paint.setTextSize((float) suggestedSize);
            String text = AutoResizeTextView.this.getText().toString();
            if (AutoResizeTextView.this.getSupportedMaxLines() == 1) {
                this.textRect.bottom = AutoResizeTextView.this.paint.getFontSpacing();
                this.textRect.right = AutoResizeTextView.this.paint.measureText(text);
            } else {
                StaticLayout layout = new StaticLayout(text, AutoResizeTextView.this.paint, AutoResizeTextView.this._widthLimit, Alignment.ALIGN_NORMAL, AutoResizeTextView.this._spacingMult, AutoResizeTextView.this._spacingAdd, true);
                if (AutoResizeTextView.this.getSupportedMaxLines() != -1 && layout.getLineCount() > AutoResizeTextView.this.getSupportedMaxLines()) {
                    return 1;
                }
                this.textRect.bottom = (float) layout.getHeight();
                int maxWidth = -1;
                for (int i = 0; i < layout.getLineCount(); i++) {
                    if (((float) maxWidth) < layout.getLineRight(i) - layout.getLineLeft(i)) {
                        maxWidth = ((int) layout.getLineRight(i)) - ((int) layout.getLineLeft(i));
                    }
                }
                this.textRect.right = (float) maxWidth;
            }
            this.textRect.offsetTo(0.0f, 0.0f);
            if (availableSPace.contains(this.textRect)) {
                return -1;
            }
            return 1;
        }
    }

    public AutoResizeTextView(Context context) {
        this(context, null, 0);
    }

    public AutoResizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoResizeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this._availableSpaceRect = new RectF();
        this._textCachedSizes = new SparseIntArray();
        this._spacingMult = 1.0f;
        this._spacingAdd = 0.0f;
        this._enableSizeCache = true;
        this._initiallized = false;
        this._minTextSize = TypedValue.applyDimension(2, 12.0f, getResources().getDisplayMetrics());
        this._maxTextSize = getTextSize();
        if (this._maxLines == 0) {
            this._maxLines = -1;
        }
        this._sizeTester = new C07891();
        this._initiallized = true;
    }

    public void setTypeface(Typeface tf) {
        if (this.paint == null) {
            this.paint = new TextPaint(getPaint());
        }
        this.paint.setTypeface(tf);
        adjustTextSize();
        super.setTypeface(tf);
    }

    public void setTextSize(float size) {
        this._maxTextSize = size;
        this._textCachedSizes.clear();
        adjustTextSize();
    }

    public void setMaxLines(int maxlines) {
        super.setMaxLines(maxlines);
        this._maxLines = maxlines;
        reAdjust();
    }

    private int getSupportedMaxLines() {
        return this._maxLines;
    }

    public void setSingleLine() {
        super.setSingleLine();
        this._maxLines = 1;
        reAdjust();
    }

    public void setSingleLine(boolean singleLine) {
        super.setSingleLine(singleLine);
        if (singleLine) {
            this._maxLines = 1;
        } else {
            this._maxLines = -1;
        }
        reAdjust();
    }

    public void setLines(int lines) {
        super.setLines(lines);
        this._maxLines = lines;
        reAdjust();
    }

    public void setTextSize(int unit, float size) {
        Resources r;
        Context c = getContext();
        if (c == null) {
            r = Resources.getSystem();
        } else {
            r = c.getResources();
        }
        this._maxTextSize = TypedValue.applyDimension(unit, size, r.getDisplayMetrics());
        this._textCachedSizes.clear();
        adjustTextSize();
    }

    public void setLineSpacing(float add, float mult) {
        super.setLineSpacing(add, mult);
        this._spacingMult = mult;
        this._spacingAdd = add;
    }

    public void setMinTextSize(float minTextSize) {
        this._minTextSize = minTextSize;
        reAdjust();
    }

    private void reAdjust() {
        adjustTextSize();
    }

    private void adjustTextSize() {
        if (this._initiallized) {
            int startSize = (int) this._minTextSize;
            int heightLimit = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
            this._widthLimit = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            if (this._widthLimit > 0) {
                this._availableSpaceRect.right = (float) this._widthLimit;
                this._availableSpaceRect.bottom = (float) heightLimit;
                superSetTextSize(startSize);
            }
        }
    }

    private void superSetTextSize(int startSize) {
        super.setTextSize(0, (float) efficientTextSizeSearch(startSize, (int) this._maxTextSize, this._sizeTester, this._availableSpaceRect));
    }

    public void setEnableSizeCache(boolean enable) {
        this._enableSizeCache = enable;
        this._textCachedSizes.clear();
        adjustTextSize();
    }

    private int efficientTextSizeSearch(int start, int end, SizeTester sizeTester, RectF availableSpace) {
        if (!this._enableSizeCache) {
            return binarySearch(start, end, sizeTester, availableSpace);
        }
        String text = getText().toString();
        int key = text == null ? 0 : text.length();
        int size = this._textCachedSizes.get(key);
        if (size != 0) {
            return size;
        }
        size = binarySearch(start, end, sizeTester, availableSpace);
        this._textCachedSizes.put(key, size);
        return size;
    }

    private int binarySearch(int start, int end, SizeTester sizeTester, RectF availableSpace) {
        int lastBest = start;
        int lo = start;
        int hi = end - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int midValCmp = sizeTester.onTestSize(mid, availableSpace);
            if (midValCmp < 0) {
                lastBest = lo;
                lo = mid + 1;
            } else if (midValCmp <= 0) {
                return mid;
            } else {
                hi = mid - 1;
                lastBest = hi;
            }
        }
        return lastBest;
    }

    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        reAdjust();
    }

    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        this._textCachedSizes.clear();
        super.onSizeChanged(width, height, oldwidth, oldheight);
        if (width != oldwidth || height != oldheight) {
            reAdjust();
        }
    }
}
