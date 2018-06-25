package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.thin.downloadmanager.BuildConfig;
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

    public void setHintText(String value) {
        this.hintText = value;
        onTextChange();
        setText(getText());
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        onTextChange();
    }

    public void onTextChange() {
        this.textOffset = length() > 0 ? getPaint().measureText(getText(), 0, length()) : 0.0f;
        this.spaceSize = getPaint().measureText(" ");
        this.numberSize = getPaint().measureText(BuildConfig.VERSION_NAME);
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.hintText != null && length() < this.hintText.length()) {
            int top = getMeasuredHeight() / 2;
            float offsetX = this.textOffset;
            for (int a = length(); a < this.hintText.length(); a++) {
                if (this.hintText.charAt(a) == ' ') {
                    offsetX += this.spaceSize;
                } else {
                    this.rect.set(((int) offsetX) + AndroidUtilities.dp(1.0f), top, ((int) (this.numberSize + offsetX)) - AndroidUtilities.dp(1.0f), AndroidUtilities.dp(2.0f) + top);
                    canvas.drawRect(this.rect, this.paint);
                    offsetX += this.numberSize;
                }
            }
        }
    }
}
