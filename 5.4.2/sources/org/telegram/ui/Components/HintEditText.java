package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class HintEditText extends EditTextBoldCursor {
    private String hintText;
    private float numberSize;
    private Paint paint = new Paint();
    private Rect rect = new Rect();
    private float spaceSize;
    private float textOffset;

    public HintEditText(Context context) {
        super(context);
        this.paint.setColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
    }

    public String getHintText() {
        return this.hintText;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.hintText != null && length() < this.hintText.length()) {
            int measuredHeight = getMeasuredHeight() / 2;
            float f = this.textOffset;
            for (int length = length(); length < this.hintText.length(); length++) {
                if (this.hintText.charAt(length) == ' ') {
                    f += this.spaceSize;
                } else {
                    this.rect.set(((int) f) + AndroidUtilities.dp(1.0f), measuredHeight, ((int) (this.numberSize + f)) - AndroidUtilities.dp(1.0f), AndroidUtilities.dp(2.0f) + measuredHeight);
                    canvas.drawRect(this.rect, this.paint);
                    f += this.numberSize;
                }
            }
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        onTextChange();
    }

    public void onTextChange() {
        this.textOffset = length() > 0 ? getPaint().measureText(getText(), 0, length()) : BitmapDescriptorFactory.HUE_RED;
        this.spaceSize = getPaint().measureText(" ");
        this.numberSize = getPaint().measureText("1");
        invalidate();
    }

    public void setHintText(String str) {
        this.hintText = str;
        onTextChange();
        setText(getText());
    }
}
