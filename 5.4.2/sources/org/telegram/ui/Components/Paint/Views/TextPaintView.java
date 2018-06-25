package org.telegram.ui.Components.Paint.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessagesController;
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
    class C44921 implements TextWatcher {
        private int beforeCursorPosition = 0;
        private String text;

        C44921() {
        }

        public void afterTextChanged(Editable editable) {
            TextPaintView.this.editText.removeTextChangedListener(this);
            if (TextPaintView.this.editText.getLineCount() > 9) {
                TextPaintView.this.editText.setText(this.text);
                TextPaintView.this.editText.setSelection(this.beforeCursorPosition);
            }
            TextPaintView.this.editText.addTextChangedListener(this);
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            this.text = charSequence.toString();
            this.beforeCursorPosition = i;
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    public class TextViewSelectionView extends SelectionView {
        public TextViewSelectionView(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            int i;
            super.onDraw(canvas);
            float dp = (float) AndroidUtilities.dp(3.0f);
            float dp2 = (float) AndroidUtilities.dp(3.0f);
            float dp3 = (float) AndroidUtilities.dp(1.0f);
            float dp4 = (float) AndroidUtilities.dp(4.5f);
            float dp5 = (dp4 + dp3) + ((float) AndroidUtilities.dp(15.0f));
            float width = ((float) getWidth()) - (2.0f * dp5);
            float height = ((float) getHeight()) - (2.0f * dp5);
            int floor = (int) Math.floor((double) (width / (dp + dp2)));
            float ceil = (float) Math.ceil((double) (((width - (((float) floor) * (dp + dp2))) + dp) / 2.0f));
            for (i = 0; i < floor; i++) {
                float f = (((float) i) * (dp2 + dp)) + (ceil + dp5);
                canvas.drawRect(f, dp5 - (dp3 / 2.0f), f + dp2, dp5 + (dp3 / 2.0f), this.paint);
                Canvas canvas2 = canvas;
                canvas2.drawRect(f, (dp5 + height) - (dp3 / 2.0f), f + dp2, (dp3 / 2.0f) + (dp5 + height), this.paint);
            }
            floor = (int) Math.floor((double) (height / (dp + dp2)));
            ceil = (float) Math.ceil((double) (((height - (((float) floor) * (dp + dp2))) + dp) / 2.0f));
            for (i = 0; i < floor; i++) {
                float f2 = (ceil + dp5) + (((float) i) * (dp2 + dp));
                canvas.drawRect(dp5 - (dp3 / 2.0f), f2, dp5 + (dp3 / 2.0f), f2 + dp2, this.paint);
                canvas2 = canvas;
                canvas2.drawRect((dp5 + width) - (dp3 / 2.0f), f2, (dp3 / 2.0f) + (dp5 + width), f2 + dp2, this.paint);
            }
            canvas.drawCircle(dp5, (height / 2.0f) + dp5, dp4, this.dotPaint);
            canvas.drawCircle(dp5, (height / 2.0f) + dp5, dp4, this.dotStrokePaint);
            canvas.drawCircle(dp5 + width, (height / 2.0f) + dp5, dp4, this.dotPaint);
            canvas.drawCircle(dp5 + width, (height / 2.0f) + dp5, dp4, this.dotStrokePaint);
        }

        protected int pointInsideHandle(float f, float f2) {
            float dp = (float) AndroidUtilities.dp(19.5f);
            float dp2 = ((float) AndroidUtilities.dp(1.0f)) + dp;
            float width = ((float) getWidth()) - (dp2 * 2.0f);
            float height = ((float) getHeight()) - (dp2 * 2.0f);
            float f3 = (height / 2.0f) + dp2;
            return (f <= dp2 - dp || f2 <= f3 - dp || f >= dp2 + dp || f2 >= f3 + dp) ? (f <= (dp2 + width) - dp || f2 <= f3 - dp || f >= (dp2 + width) + dp || f2 >= dp + f3) ? (f <= dp2 || f >= width || f2 <= dp2 || f2 >= height) ? 0 : 3 : 2 : 1;
        }
    }

    public TextPaintView(Context context, TextPaintView textPaintView, Point point) {
        this(context, point, textPaintView.baseFontSize, textPaintView.getText(), textPaintView.getSwatch(), textPaintView.stroke);
        setRotation(textPaintView.getRotation());
        setScale(textPaintView.getScale());
    }

    public TextPaintView(Context context, Point point, int i, String str, Swatch swatch, boolean z) {
        super(context, point);
        this.baseFontSize = i;
        this.editText = new EditTextOutline(context);
        this.editText.setBackgroundColor(0);
        this.editText.setPadding(AndroidUtilities.dp(7.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(7.0f));
        this.editText.setClickable(false);
        this.editText.setEnabled(false);
        this.editText.setTextSize(0, (float) this.baseFontSize);
        this.editText.setText(str);
        this.editText.setTextColor(swatch.color);
        this.editText.setTypeface(null, 1);
        this.editText.setGravity(17);
        this.editText.setHorizontallyScrolling(false);
        this.editText.setImeOptions(ErrorDialogData.BINDER_CRASH);
        this.editText.setFocusableInTouchMode(true);
        this.editText.setInputType(this.editText.getInputType() | MessagesController.UPDATE_MASK_CHAT_ADMINS);
        addView(this.editText, LayoutHelper.createFrame(-2, -2, 51));
        if (VERSION.SDK_INT >= 23) {
            this.editText.setBreakStrategy(0);
        }
        setSwatch(swatch);
        setStroke(z);
        updatePosition();
        this.editText.addTextChangedListener(new C44921());
    }

    private void updateColor() {
        if (this.stroke) {
            this.editText.setTextColor(-1);
            this.editText.setStrokeColor(this.swatch.color);
            this.editText.setShadowLayer(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
            return;
        }
        this.editText.setTextColor(this.swatch.color);
        this.editText.setStrokeColor(0);
        this.editText.setShadowLayer(8.0f, BitmapDescriptorFactory.HUE_RED, 2.0f, -1442840576);
    }

    public void beginEditing() {
        this.editText.setEnabled(true);
        this.editText.setClickable(true);
        this.editText.requestFocus();
        this.editText.setSelection(this.editText.getText().length());
    }

    protected TextViewSelectionView createSelectionView() {
        return new TextViewSelectionView(getContext());
    }

    public void endEditing() {
        this.editText.clearFocus();
        this.editText.setEnabled(false);
        this.editText.setClickable(false);
        updateSelectionView();
    }

    public View getFocusedView() {
        return this.editText;
    }

    protected Rect getSelectionBounds() {
        float scaleX = ((ViewGroup) getParent()).getScaleX();
        float width = (((float) getWidth()) * getScale()) + (((float) AndroidUtilities.dp(46.0f)) / scaleX);
        float height = (((float) getHeight()) * getScale()) + (((float) AndroidUtilities.dp(20.0f)) / scaleX);
        return new Rect((this.position.f10184x - (width / 2.0f)) * scaleX, (this.position.f10185y - (height / 2.0f)) * scaleX, width * scaleX, scaleX * height);
    }

    public Swatch getSwatch() {
        return this.swatch;
    }

    public String getText() {
        return this.editText.getText().toString();
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updatePosition();
    }

    public void setMaxWidth(int i) {
        this.editText.setMaxWidth(i);
    }

    public void setStroke(boolean z) {
        this.stroke = z;
        updateColor();
    }

    public void setSwatch(Swatch swatch) {
        this.swatch = swatch;
        updateColor();
    }

    public void setText(String str) {
        this.editText.setText(str);
    }
}
