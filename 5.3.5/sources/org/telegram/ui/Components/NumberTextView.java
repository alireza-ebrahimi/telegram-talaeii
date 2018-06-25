package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import java.util.ArrayList;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;

public class NumberTextView extends View {
    private ObjectAnimator animator;
    private int currentNumber = 1;
    private ArrayList<StaticLayout> letters = new ArrayList();
    private ArrayList<StaticLayout> oldLetters = new ArrayList();
    private float progress = 0.0f;
    private TextPaint textPaint = new TextPaint(1);

    /* renamed from: org.telegram.ui.Components.NumberTextView$1 */
    class C26251 extends AnimatorListenerAdapter {
        C26251() {
        }

        public void onAnimationEnd(Animator animation) {
            NumberTextView.this.animator = null;
            NumberTextView.this.oldLetters.clear();
        }
    }

    public NumberTextView(Context context) {
        super(context);
    }

    public void setProgress(float value) {
        if (this.progress != value) {
            this.progress = value;
            invalidate();
        }
    }

    public float getProgress() {
        return this.progress;
    }

    public void setNumber(int number, boolean animated) {
        if (this.currentNumber != number || !animated) {
            if (this.animator != null) {
                this.animator.cancel();
                this.animator = null;
            }
            this.oldLetters.clear();
            this.oldLetters.addAll(this.letters);
            this.letters.clear();
            String oldText = String.format(Locale.US, "%d", new Object[]{Integer.valueOf(this.currentNumber)});
            String text = String.format(Locale.US, "%d", new Object[]{Integer.valueOf(number)});
            boolean forwardAnimation = number > this.currentNumber;
            this.currentNumber = number;
            this.progress = 0.0f;
            int a = 0;
            while (a < text.length()) {
                String ch = text.substring(a, a + 1);
                String oldCh = (this.oldLetters.isEmpty() || a >= oldText.length()) ? null : oldText.substring(a, a + 1);
                if (oldCh == null || !oldCh.equals(ch)) {
                    this.letters.add(new StaticLayout(ch, this.textPaint, (int) Math.ceil((double) this.textPaint.measureText(ch)), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false));
                } else {
                    this.letters.add(this.oldLetters.get(a));
                    this.oldLetters.set(a, null);
                }
                a++;
            }
            if (animated && !this.oldLetters.isEmpty()) {
                String str = "progress";
                float[] fArr = new float[2];
                fArr[0] = forwardAnimation ? -1.0f : 1.0f;
                fArr[1] = 0.0f;
                this.animator = ObjectAnimator.ofFloat(this, str, fArr);
                this.animator.setDuration(150);
                this.animator.addListener(new C26251());
                this.animator.start();
            }
            invalidate();
        }
    }

    public void setTextSize(int size) {
        this.textPaint.setTextSize((float) AndroidUtilities.dp((float) size));
        this.oldLetters.clear();
        this.letters.clear();
        setNumber(this.currentNumber, false);
    }

    public void setTextColor(int value) {
        this.textPaint.setColor(value);
        invalidate();
    }

    public void setTypeface(Typeface typeface) {
        this.textPaint.setTypeface(typeface);
        this.oldLetters.clear();
        this.letters.clear();
        setNumber(this.currentNumber, false);
    }

    protected void onDraw(Canvas canvas) {
        if (!this.letters.isEmpty()) {
            float height = (float) ((StaticLayout) this.letters.get(0)).getHeight();
            canvas.save();
            canvas.translate((float) getPaddingLeft(), (((float) getMeasuredHeight()) - height) / 2.0f);
            int count = Math.max(this.letters.size(), this.oldLetters.size());
            int a = 0;
            while (a < count) {
                float lineWidth;
                canvas.save();
                StaticLayout old = a < this.oldLetters.size() ? (StaticLayout) this.oldLetters.get(a) : null;
                StaticLayout layout = a < this.letters.size() ? (StaticLayout) this.letters.get(a) : null;
                if (this.progress > 0.0f) {
                    if (old != null) {
                        this.textPaint.setAlpha((int) (this.progress * 255.0f));
                        canvas.save();
                        canvas.translate(0.0f, (this.progress - 1.0f) * height);
                        old.draw(canvas);
                        canvas.restore();
                        if (layout != null) {
                            this.textPaint.setAlpha((int) ((1.0f - this.progress) * 255.0f));
                            canvas.translate(0.0f, this.progress * height);
                        }
                    } else {
                        this.textPaint.setAlpha(255);
                    }
                } else if (this.progress < 0.0f) {
                    if (old != null) {
                        this.textPaint.setAlpha((int) ((-this.progress) * 255.0f));
                        canvas.save();
                        canvas.translate(0.0f, (this.progress + 1.0f) * height);
                        old.draw(canvas);
                        canvas.restore();
                    }
                    if (layout != null) {
                        if (a == count - 1 || old != null) {
                            this.textPaint.setAlpha((int) ((this.progress + 1.0f) * 255.0f));
                            canvas.translate(0.0f, this.progress * height);
                        } else {
                            this.textPaint.setAlpha(255);
                        }
                    }
                } else if (layout != null) {
                    this.textPaint.setAlpha(255);
                }
                if (layout != null) {
                    layout.draw(canvas);
                }
                canvas.restore();
                if (layout != null) {
                    lineWidth = layout.getLineWidth(0);
                } else {
                    lineWidth = old.getLineWidth(0) + ((float) AndroidUtilities.dp(1.0f));
                }
                canvas.translate(lineWidth, 0.0f);
                a++;
            }
            canvas.restore();
        }
    }
}
