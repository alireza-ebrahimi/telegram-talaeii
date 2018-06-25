package org.telegram.customization.util.view.sva;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import org.telegram.customization.util.view.sva.anim.JJBaseController;
import org.telegram.customization.util.view.sva.anim.controller.JJChangeArrowController;
import org.telegram.messenger.C0906R;

public class JJSearchView extends View {
    int bgColor;
    int color;
    private JJBaseController mController;
    private Paint mPaint;
    private Path mPath;
    float scale;
    float size;

    public JJSearchView(Context context) {
        this(context, null);
    }

    public JJSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        handleAttributes(context, attrs);
    }

    public JJSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.color = 0;
        this.bgColor = 0;
        this.size = 7.0f;
        this.scale = 2.0f;
        this.mController = new JJChangeArrowController();
        handleAttributes(context, attrs);
        init();
    }

    void handleAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, C0906R.styleable.JJSearchView);
        this.color = a.getColor(1, this.color);
        this.bgColor = a.getColor(1, this.bgColor);
        this.size = a.getFloat(2, this.size);
        this.scale = a.getFloat(3, this.scale);
        a.recycle();
    }

    private void init() {
        this.mPaint = new Paint(1);
        this.mPaint.setStrokeWidth(4.0f);
        this.mPath = new Path();
    }

    public void setController(JJBaseController controller) {
        this.mController = controller;
        this.mController.setSearchView(this);
        invalidate();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mController.setColor(this.color);
        this.mController.setBgColor(this.bgColor);
        this.mController.setSize(this.size);
        this.mController.setScale(this.scale);
        this.mController.draw(canvas, this.mPaint);
    }

    public void startAnim() {
        if (this.mController != null) {
            this.mController.startAnim();
        }
    }

    public void resetAnim() {
        if (this.mController != null) {
            this.mController.resetAnim();
        }
    }
}
