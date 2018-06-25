package org.telegram.ui.Components.Paint.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.Paint.Swatch;
import org.telegram.ui.Components.Paint.Views.EntityView.SelectionView;
import org.telegram.ui.Components.Point;
import org.telegram.ui.Components.Rect;

public class TextPaintView extends EntityView {
    private int baseFontSize;
    private EditTextOutline editText;
    private boolean stroke;
    private Swatch swatch;

    /* renamed from: org.telegram.ui.Components.Paint.Views.TextPaintView$1 */
    class C26541 implements TextWatcher {
        private int beforeCursorPosition = 0;
        private String text;

        C26541() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            this.text = s.toString();
            this.beforeCursorPosition = start;
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            TextPaintView.this.editText.removeTextChangedListener(this);
            if (TextPaintView.this.editText.getLineCount() > 9) {
                TextPaintView.this.editText.setText(this.text);
                TextPaintView.this.editText.setSelection(this.beforeCursorPosition);
            }
            TextPaintView.this.editText.addTextChangedListener(this);
        }
    }

    public class TextViewSelectionView extends SelectionView {
        public TextViewSelectionView(Context context) {
            super(context);
        }

        protected int pointInsideHandle(float x, float y) {
            float radius = (float) AndroidUtilities.dp(19.5f);
            float inset = radius + ((float) AndroidUtilities.dp(1.0f));
            float width = ((float) getWidth()) - (inset * 2.0f);
            float height = ((float) getHeight()) - (inset * 2.0f);
            float middle = inset + (height / 2.0f);
            if (x > inset - radius && y > middle - radius && x < inset + radius && y < middle + radius) {
                return 1;
            }
            if (x > (inset + width) - radius && y > middle - radius && x < (inset + width) + radius && y < middle + radius) {
                return 2;
            }
            if (x <= inset || x >= width || y <= inset || y >= height) {
                return 0;
            }
            return 3;
        }

        protected void onDraw(Canvas canvas) {
            int i;
            super.onDraw(canvas);
            float space = (float) AndroidUtilities.dp(3.0f);
            float length = (float) AndroidUtilities.dp(3.0f);
            float thickness = (float) AndroidUtilities.dp(1.0f);
            float radius = (float) AndroidUtilities.dp(4.5f);
            float inset = (radius + thickness) + ((float) AndroidUtilities.dp(15.0f));
            float width = ((float) getWidth()) - (2.0f * inset);
            float height = ((float) getHeight()) - (2.0f * inset);
            int xCount = (int) Math.floor((double) (width / (space + length)));
            float xGap = (float) Math.ceil((double) (((width - (((float) xCount) * (space + length))) + space) / 2.0f));
            for (i = 0; i < xCount; i++) {
                float x = (xGap + inset) + (((float) i) * (length + space));
                canvas.drawRect(x, inset - (thickness / 2.0f), x + length, inset + (thickness / 2.0f), this.paint);
                canvas.drawRect(x, (inset + height) - (thickness / 2.0f), x + length, (inset + height) + (thickness / 2.0f), this.paint);
            }
            int yCount = (int) Math.floor((double) (height / (space + length)));
            float yGap = (float) Math.ceil((double) (((height - (((float) yCount) * (space + length))) + space) / 2.0f));
            for (i = 0; i < yCount; i++) {
                float y = (yGap + inset) + (((float) i) * (length + space));
                canvas.drawRect(inset - (thickness / 2.0f), y, inset + (thickness / 2.0f), y + length, this.paint);
                canvas.drawRect((inset + width) - (thickness / 2.0f), y, (inset + width) + (thickness / 2.0f), y + length, this.paint);
            }
            canvas.drawCircle(inset, (height / 2.0f) + inset, radius, this.dotPaint);
            canvas.drawCircle(inset, (height / 2.0f) + inset, radius, this.dotStrokePaint);
            canvas.drawCircle(inset + width, (height / 2.0f) + inset, radius, this.dotPaint);
            canvas.drawCircle(inset + width, (height / 2.0f) + inset, radius, this.dotStrokePaint);
        }
    }

    public TextPaintView(Context context, Point position, int fontSize, String text, Swatch swatch, boolean stroke) {
        super(context, position);
        this.baseFontSize = fontSize;
        this.editText = new EditTextOutline(context);
        this.editText.setBackgroundColor(0);
        this.editText.setPadding(AndroidUtilities.dp(7.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(7.0f));
        this.editText.setClickable(false);
        this.editText.setEnabled(false);
        this.editText.setTextSize(0, (float) this.baseFontSize);
        this.editText.setText(text);
        this.editText.setTextColor(swatch.color);
        this.editText.setTypeface(null, 1);
        this.editText.setGravity(17);
        this.editText.setHorizontallyScrolling(false);
        this.editText.setImeOptions(268435456);
        this.editText.setFocusableInTouchMode(true);
        this.editText.setInputType(this.editText.getInputType() | 16384);
        addView(this.editText, LayoutHelper.createFrame(-2, -2, 51));
        if (VERSION.SDK_INT >= 23) {
            this.editText.setBreakStrategy(0);
        }
        setSwatch(swatch);
        setStroke(stroke);
        updatePosition();
        this.editText.addTextChangedListener(new C26541());
    }

    public TextPaintView(Context context, TextPaintView textPaintView, Point position) {
        this(context, position, textPaintView.baseFontSize, textPaintView.getText(), textPaintView.getSwatch(), textPaintView.stroke);
        setRotation(textPaintView.getRotation());
        setScale(textPaintView.getScale());
    }

    public void setMaxWidth(int maxWidth) {
        this.editText.setMaxWidth(maxWidth);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        updatePosition();
    }

    public String getText() {
        return this.editText.getText().toString();
    }

    public void setText(String text) {
        this.editText.setText(text);
    }

    public View getFocusedView() {
        return this.editText;
    }

    public void beginEditing() {
        this.editText.setEnabled(true);
        this.editText.setClickable(true);
        this.editText.requestFocus();
        this.editText.setSelection(this.editText.getText().length());
    }

    public void endEditing() {
        this.editText.clearFocus();
        this.editText.setEnabled(false);
        this.editText.setClickable(false);
        updateSelectionView();
    }

    public Swatch getSwatch() {
        return this.swatch;
    }

    public void setSwatch(Swatch swatch) {
        this.swatch = swatch;
        updateColor();
    }

    public void setStroke(boolean stroke) {
        this.stroke = stroke;
        updateColor();
    }

    private void updateColor() {
        if (this.stroke) {
            this.editText.setTextColor(-1);
            this.editText.setStrokeColor(this.swatch.color);
            this.editText.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
            return;
        }
        this.editText.setTextColor(this.swatch.color);
        this.editText.setStrokeColor(0);
        this.editText.setShadowLayer(8.0f, 0.0f, 2.0f, -1442840576);
    }

    protected Rect getSelectionBounds() {
        float scale = ((ViewGroup) getParent()).getScaleX();
        float width = (((float) getWidth()) * getScale()) + (((float) AndroidUtilities.dp(46.0f)) / scale);
        float height = (((float) getHeight()) * getScale()) + (((float) AndroidUtilities.dp(20.0f)) / scale);
        return new Rect((this.position.f105x - (width / 2.0f)) * scale, (this.position.f106y - (height / 2.0f)) * scale, width * scale, height * scale);
    }

    protected TextViewSelectionView createSelectionView() {
        return new TextViewSelectionView(getContext());
    }
}
