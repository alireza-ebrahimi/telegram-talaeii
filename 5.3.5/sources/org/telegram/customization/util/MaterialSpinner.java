package org.telegram.customization.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Point;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import org.ir.talaeii.R;
import org.telegram.messenger.C0906R;
import utils.view.FarsiTextView;

public class MaterialSpinner extends Spinner implements AnimatorUpdateListener {
    public static final int DEFAULT_ARROW_WIDTH_DP = 12;
    private static final String TAG = MaterialSpinner.class.getSimpleName();
    private boolean alignLabels;
    private int arrowColor;
    private float arrowSize;
    private int baseAlpha;
    private int baseColor;
    private float currentNbErrorLines;
    private int disabledColor;
    private boolean enableErrorLabel;
    private boolean enableFloatingLabel;
    private CharSequence error;
    private int errorColor;
    private ObjectAnimator errorLabelAnimator;
    private int errorLabelPosX;
    private int errorLabelSpacing;
    private int extraPaddingBottom;
    private int extraPaddingTop;
    private ObjectAnimator floatingLabelAnimator;
    private int floatingLabelBottomSpacing;
    private int floatingLabelColor;
    private int floatingLabelInsideSpacing;
    private float floatingLabelPercent;
    private CharSequence floatingLabelText;
    private int floatingLabelTopSpacing;
    private boolean floatingLabelVisible;
    private int highlightColor;
    private CharSequence hint;
    private MaterialSpinner$HintAdapter hintAdapter;
    private int hintColor;
    private int innerPaddingBottom;
    private int innerPaddingLeft;
    private int innerPaddingRight;
    private int innerPaddingTop;
    private boolean isRtl;
    private boolean isSelected;
    private int lastPosition;
    private int minContentHeight;
    private int minNbErrorLines;
    private boolean multiline;
    int paddingInt = -1;
    private Paint paint;
    private int rightLeftSpinnerPadding;
    private Path selectorPath;
    private Point[] selectorPoints;
    private StaticLayout staticLayout;
    private TextPaint textPaint;
    private float thickness;
    private float thicknessError;
    private Typeface typeface;
    private int underlineBottomSpacing;
    private int underlineTopSpacing;

    public MaterialSpinner(Context context) {
        super(context);
        init(context, null);
    }

    public MaterialSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaterialSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttributes(context, attrs);
        initPaintObjects();
        initDimensions();
        initPadding();
        initFloatingLabelAnimator();
        initOnItemSelectedListener();
        setMinimumHeight((getPaddingTop() + getPaddingBottom()) + this.minContentHeight);
        setBackgroundResource(R.drawable.my_background);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray defaultArray = context.obtainStyledAttributes(new int[]{R.attr.colorControlNormal, R.attr.colorAccent});
        int defaultBaseColor = defaultArray.getColor(0, 0);
        int defaultHighlightColor = defaultArray.getColor(1, 0);
        int defaultErrorColor = context.getResources().getColor(R.color.error_color);
        defaultArray.recycle();
        TypedArray array = context.obtainStyledAttributes(attrs, C0906R.styleable.MaterialSpinner);
        this.baseColor = array.getColor(0, defaultBaseColor);
        this.highlightColor = array.getColor(1, defaultHighlightColor);
        this.errorColor = array.getColor(2, defaultErrorColor);
        this.disabledColor = context.getResources().getColor(R.color.disabled_color);
        this.error = array.getString(3);
        this.hint = array.getString(4);
        this.paddingInt = array.getInt(15, -1);
        this.hintColor = array.getColor(5, this.baseColor);
        this.floatingLabelText = array.getString(6);
        this.floatingLabelColor = array.getColor(7, this.baseColor);
        this.multiline = array.getBoolean(8, true);
        this.minNbErrorLines = array.getInt(9, 1);
        this.alignLabels = array.getBoolean(11, true);
        this.thickness = array.getDimension(12, 1.0f);
        this.thicknessError = array.getDimension(13, 2.0f);
        this.arrowColor = array.getColor(16, this.baseColor);
        this.arrowSize = array.getDimension(14, (float) dpToPx(12.0f));
        this.enableErrorLabel = array.getBoolean(18, true);
        this.enableFloatingLabel = array.getBoolean(17, true);
        this.isRtl = array.getBoolean(19, false);
        if (array.getString(10) != null) {
            this.typeface = FarsiTextView.getTypeface(getContext());
        }
        array.recycle();
        this.floatingLabelPercent = 0.0f;
        this.errorLabelPosX = 0;
        this.isSelected = false;
        this.floatingLabelVisible = false;
        this.lastPosition = -1;
        this.currentNbErrorLines = (float) this.minNbErrorLines;
    }

    public void setSelection(int position) {
        post(new MaterialSpinner$1(this, position));
    }

    private void initPaintObjects() {
        int labelTextSize = getResources().getDimensionPixelSize(R.dimen.label_text_size);
        this.paint = new Paint(1);
        this.textPaint = new TextPaint(1);
        this.textPaint.setTextSize((float) labelTextSize);
        if (this.typeface != null) {
            this.textPaint.setTypeface(this.typeface);
        }
        this.textPaint.setColor(this.baseColor);
        this.baseAlpha = this.textPaint.getAlpha();
        this.selectorPath = new Path();
        this.selectorPath.setFillType(FillType.EVEN_ODD);
        this.selectorPoints = new Point[3];
        for (int i = 0; i < 3; i++) {
            this.selectorPoints[i] = new Point();
        }
    }

    public int getSelectedItemPosition() {
        return super.getSelectedItemPosition();
    }

    private void initPadding() {
        this.innerPaddingTop = getPaddingTop();
        this.innerPaddingLeft = getPaddingLeft();
        this.innerPaddingRight = getPaddingRight();
        this.innerPaddingBottom = getPaddingBottom();
        this.extraPaddingTop = this.enableFloatingLabel ? (this.floatingLabelTopSpacing + this.floatingLabelInsideSpacing) + this.floatingLabelBottomSpacing : this.floatingLabelBottomSpacing;
        updateBottomPadding();
    }

    private void updateBottomPadding() {
        FontMetrics textMetrics = this.textPaint.getFontMetrics();
        this.extraPaddingBottom = this.underlineTopSpacing + this.underlineBottomSpacing;
        if (this.enableErrorLabel) {
            this.extraPaddingBottom += (int) ((textMetrics.descent - textMetrics.ascent) * this.currentNbErrorLines);
        }
        updatePadding();
    }

    private void initDimensions() {
        this.underlineTopSpacing = getResources().getDimensionPixelSize(R.dimen.underline_top_spacing);
        this.underlineBottomSpacing = getResources().getDimensionPixelSize(R.dimen.underline_bottom_spacing);
        this.floatingLabelTopSpacing = getResources().getDimensionPixelSize(R.dimen.floating_label_top_spacing);
        this.floatingLabelBottomSpacing = getResources().getDimensionPixelSize(R.dimen.floating_label_bottom_spacing);
        this.rightLeftSpinnerPadding = this.alignLabels ? getResources().getDimensionPixelSize(R.dimen.right_left_spinner_padding) : 0;
        this.floatingLabelInsideSpacing = getResources().getDimensionPixelSize(R.dimen.floating_label_inside_spacing);
        this.errorLabelSpacing = (int) getResources().getDimension(R.dimen.error_label_spacing);
        this.minContentHeight = (int) getResources().getDimension(R.dimen.min_content_height);
    }

    private void initOnItemSelectedListener() {
        setOnItemSelectedListener(null);
    }

    private void initFloatingLabelAnimator() {
        if (this.floatingLabelAnimator == null) {
            this.floatingLabelAnimator = ObjectAnimator.ofFloat((Object) this, "floatingLabelPercent", 0.0f, 1.0f);
            this.floatingLabelAnimator.addUpdateListener(this);
        }
    }

    private void showFloatingLabel() {
        if (this.floatingLabelAnimator != null) {
            this.floatingLabelVisible = true;
            if (this.floatingLabelAnimator.isRunning()) {
                this.floatingLabelAnimator.reverse();
            } else {
                this.floatingLabelAnimator.start();
            }
        }
    }

    private void hideFloatingLabel() {
        if (this.floatingLabelAnimator != null) {
            this.floatingLabelVisible = false;
            this.floatingLabelAnimator.reverse();
        }
    }

    private void startErrorScrollingAnimator() {
        int textWidth = Math.round(this.textPaint.measureText(this.error.toString()));
        if (this.errorLabelAnimator == null) {
            this.errorLabelAnimator = ObjectAnimator.ofInt((Object) this, "errorLabelPosX", 0, (getWidth() / 2) + textWidth);
            this.errorLabelAnimator.setStartDelay(1000);
            this.errorLabelAnimator.setInterpolator(new LinearInterpolator());
            this.errorLabelAnimator.setDuration((long) (this.error.length() * 150));
            this.errorLabelAnimator.addUpdateListener(this);
            this.errorLabelAnimator.setRepeatCount(-1);
        } else {
            this.errorLabelAnimator.setIntValues(0, (getWidth() / 2) + textWidth);
        }
        this.errorLabelAnimator.start();
    }

    private void startErrorMultilineAnimator(float destLines) {
        if (this.errorLabelAnimator == null) {
            this.errorLabelAnimator = ObjectAnimator.ofFloat((Object) this, "currentNbErrorLines", destLines);
        } else {
            this.errorLabelAnimator.setFloatValues(destLines);
        }
        this.errorLabelAnimator.start();
    }

    private int dpToPx(float dp) {
        return Math.round(TypedValue.applyDimension(1, dp, getContext().getResources().getDisplayMetrics()));
    }

    private float pxToDp(float px) {
        return getContext().getResources().getDisplayMetrics().density * px;
    }

    private void updatePadding() {
        int left = this.innerPaddingLeft;
        int top = this.innerPaddingTop + this.extraPaddingTop;
        int right = this.innerPaddingRight;
        int bottom = this.innerPaddingBottom + this.extraPaddingBottom;
        if (this.paddingInt >= 0) {
            left = this.paddingInt;
            bottom = this.paddingInt;
            right = this.paddingInt;
            top = this.paddingInt;
        }
        super.setPadding(left, top, right, bottom);
        setMinimumHeight((top + bottom) + this.minContentHeight);
    }

    private boolean needScrollingAnimation() {
        if (this.error == null) {
            return false;
        }
        if (this.textPaint.measureText(this.error.toString(), 0, this.error.length()) > ((float) (getWidth() - this.rightLeftSpinnerPadding))) {
            return true;
        }
        return false;
    }

    private int prepareBottomPadding() {
        int targetNbLines = this.minNbErrorLines;
        if (this.error == null) {
            return targetNbLines;
        }
        this.staticLayout = new StaticLayout(this.error, this.textPaint, (getWidth() - getPaddingRight()) - getPaddingLeft(), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        return Math.max(this.minNbErrorLines, this.staticLayout.getLineCount());
    }

    private boolean isSpinnerEmpty() {
        if (this.hintAdapter.getCount() == 0 && this.hint == null) {
            return true;
        }
        return this.hintAdapter.getCount() == 1 && this.hint != null;
    }

    protected void onDraw(Canvas canvas) {
        int lineHeight;
        super.onDraw(canvas);
        int endX = getWidth();
        int startYLine = (getHeight() - getPaddingBottom()) + this.underlineTopSpacing;
        int startYFloatingLabel = (int) (((float) getPaddingTop()) - (this.floatingLabelPercent * ((float) this.floatingLabelBottomSpacing)));
        if (this.error == null || !this.enableErrorLabel) {
            lineHeight = dpToPx(this.thickness);
            if (this.isSelected || hasFocus()) {
                this.paint.setColor(this.highlightColor);
            } else {
                this.paint.setColor(isEnabled() ? this.baseColor : this.disabledColor);
            }
        } else {
            lineHeight = dpToPx(this.thicknessError);
            int startYErrorLabel = (this.errorLabelSpacing + startYLine) + lineHeight;
            this.paint.setColor(this.errorColor);
            this.textPaint.setColor(this.errorColor);
            if (this.multiline) {
                canvas.save();
                canvas.translate((float) (this.rightLeftSpinnerPadding + 0), (float) (startYErrorLabel - this.errorLabelSpacing));
                this.staticLayout.draw(canvas);
                canvas.restore();
            } else {
                canvas.drawText(this.error.toString(), (float) ((this.rightLeftSpinnerPadding + 0) - this.errorLabelPosX), (float) startYErrorLabel, this.textPaint);
                if (this.errorLabelPosX > 0) {
                    canvas.save();
                    canvas.translate(this.textPaint.measureText(this.error.toString()) + ((float) (getWidth() / 2)), 0.0f);
                    canvas.drawText(this.error.toString(), (float) ((this.rightLeftSpinnerPadding + 0) - this.errorLabelPosX), (float) startYErrorLabel, this.textPaint);
                    canvas.restore();
                }
            }
        }
        canvas.drawRect((float) null, (float) startYLine, (float) endX, (float) (startYLine + lineHeight), this.paint);
        if (!(this.hint == null && this.floatingLabelText == null) && this.enableFloatingLabel) {
            if (this.isSelected || hasFocus()) {
                this.textPaint.setColor(this.highlightColor);
            } else {
                this.textPaint.setColor(isEnabled() ? this.floatingLabelColor : this.disabledColor);
            }
            if (this.floatingLabelAnimator.isRunning() || !this.floatingLabelVisible) {
                this.textPaint.setAlpha((int) ((((0.8d * ((double) this.floatingLabelPercent)) + 0.2d) * ((double) this.baseAlpha)) * ((double) this.floatingLabelPercent)));
            }
            String textToDraw = this.floatingLabelText != null ? this.floatingLabelText.toString() : this.hint.toString();
            if (this.isRtl) {
                canvas.drawText(textToDraw, ((float) (getWidth() - this.rightLeftSpinnerPadding)) - this.textPaint.measureText(textToDraw), (float) startYFloatingLabel, this.textPaint);
            } else {
                canvas.drawText(textToDraw, (float) (this.rightLeftSpinnerPadding + 0), (float) startYFloatingLabel, this.textPaint);
            }
        }
        drawSelector(canvas, getWidth() - this.rightLeftSpinnerPadding, getPaddingTop() + dpToPx(8.0f));
    }

    private void drawSelector(Canvas canvas, int posX, int posY) {
        if (this.isSelected || hasFocus()) {
            this.paint.setColor(this.highlightColor);
        } else {
            this.paint.setColor(isEnabled() ? this.arrowColor : this.disabledColor);
        }
        Point point1 = this.selectorPoints[0];
        Point point2 = this.selectorPoints[1];
        Point point3 = this.selectorPoints[2];
        point1.set(posX, posY);
        point2.set((int) (((float) posX) - this.arrowSize), posY);
        point3.set((int) (((float) posX) - (this.arrowSize / 2.0f)), (int) (((float) posY) + (this.arrowSize / 2.0f)));
        this.selectorPath.reset();
        this.selectorPath.moveTo((float) point1.x, (float) point1.y);
        this.selectorPath.lineTo((float) point2.x, (float) point2.y);
        this.selectorPath.lineTo((float) point3.x, (float) point3.y);
        this.selectorPath.close();
        canvas.drawPath(this.selectorPath, this.paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            switch (event.getAction()) {
                case 0:
                    this.isSelected = true;
                    break;
                case 1:
                case 3:
                    this.isSelected = false;
                    break;
            }
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        super.setOnItemSelectedListener(new MaterialSpinner$2(this, listener));
    }

    public void onAnimationUpdate(ValueAnimator animation) {
        invalidate();
    }

    public int getBaseColor() {
        return this.baseColor;
    }

    public void setBaseColor(int baseColor) {
        this.baseColor = baseColor;
        this.textPaint.setColor(baseColor);
        this.baseAlpha = this.textPaint.getAlpha();
        invalidate();
    }

    public int getHighlightColor() {
        return this.highlightColor;
    }

    public void setHighlightColor(int highlightColor) {
        this.highlightColor = highlightColor;
        invalidate();
    }

    public int getHintColor() {
        return this.hintColor;
    }

    public void setHintColor(int hintColor) {
        this.hintColor = hintColor;
        invalidate();
    }

    public int getErrorColor() {
        return this.errorColor;
    }

    public void setErrorColor(int errorColor) {
        this.errorColor = errorColor;
        invalidate();
    }

    public void setHint(CharSequence hint) {
        this.hint = hint;
        invalidate();
    }

    public void setHint(int resid) {
        setHint(getResources().getString(resid));
    }

    public CharSequence getHint() {
        return this.hint;
    }

    public void setFloatingLabelText(CharSequence floatingLabelText) {
        this.floatingLabelText = floatingLabelText;
        invalidate();
    }

    public void setFloatingLabelText(int resid) {
        setFloatingLabelText(getResources().getString(resid));
    }

    public CharSequence getFloatingLabelText() {
        return this.floatingLabelText;
    }

    public void setError(CharSequence error) {
        this.error = error;
        if (this.errorLabelAnimator != null) {
            this.errorLabelAnimator.end();
        }
        if (this.multiline) {
            startErrorMultilineAnimator((float) prepareBottomPadding());
        } else if (needScrollingAnimation()) {
            startErrorScrollingAnimator();
        }
        requestLayout();
    }

    public void setError(int resid) {
        setError(getResources().getString(resid));
    }

    public void setEnabled(boolean enabled) {
        if (!enabled) {
            this.isSelected = false;
            invalidate();
        }
        super.setEnabled(enabled);
    }

    public CharSequence getError() {
        return this.error;
    }

    public void setRtl() {
        this.isRtl = true;
        invalidate();
    }

    public boolean isRtl() {
        return this.isRtl;
    }

    @Deprecated
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
    }

    public void setPaddingSafe(int left, int top, int right, int bottom) {
        this.innerPaddingRight = right;
        this.innerPaddingLeft = left;
        this.innerPaddingTop = top;
        this.innerPaddingBottom = bottom;
        updatePadding();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setAdapter(SpinnerAdapter adapter) {
        this.hintAdapter = new MaterialSpinner$HintAdapter(this, adapter, getContext());
        super.setAdapter(adapter);
    }

    public SpinnerAdapter getAdapter() {
        return this.hintAdapter != null ? MaterialSpinner$HintAdapter.access$800(this.hintAdapter) : null;
    }

    private float getFloatingLabelPercent() {
        return this.floatingLabelPercent;
    }

    private void setFloatingLabelPercent(float floatingLabelPercent) {
        this.floatingLabelPercent = floatingLabelPercent;
    }

    private int getErrorLabelPosX() {
        return this.errorLabelPosX;
    }

    private void setErrorLabelPosX(int errorLabelPosX) {
        this.errorLabelPosX = errorLabelPosX;
    }

    private float getCurrentNbErrorLines() {
        return this.currentNbErrorLines;
    }

    private void setCurrentNbErrorLines(float currentNbErrorLines) {
        this.currentNbErrorLines = currentNbErrorLines;
        updateBottomPadding();
    }

    public Object getItemAtPosition(int position) {
        if (this.hint != null) {
            position++;
        }
        return (this.hintAdapter == null || position < 0) ? null : this.hintAdapter.getItem(position);
    }

    public long getItemIdAtPosition(int position) {
        if (this.hint != null) {
            position++;
        }
        return (this.hintAdapter == null || position < 0) ? Long.MIN_VALUE : this.hintAdapter.getItemId(position);
    }
}
