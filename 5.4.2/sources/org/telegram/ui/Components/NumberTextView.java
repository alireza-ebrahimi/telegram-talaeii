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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;

public class NumberTextView extends View {
    private ObjectAnimator animator;
    private int currentNumber = 1;
    private ArrayList<StaticLayout> letters = new ArrayList();
    private ArrayList<StaticLayout> oldLetters = new ArrayList();
    private float progress = BitmapDescriptorFactory.HUE_RED;
    private TextPaint textPaint = new TextPaint(1);

    /* renamed from: org.telegram.ui.Components.NumberTextView$1 */
    class C44631 extends AnimatorListenerAdapter {
        C44631() {
        }

        public void onAnimationEnd(Animator animator) {
            NumberTextView.this.animator = null;
            NumberTextView.this.oldLetters.clear();
        }
    }

    public NumberTextView(Context context) {
        super(context);
    }

    public float getProgress() {
        return this.progress;
    }

    protected void onDraw(Canvas canvas) {
        if (!this.letters.isEmpty()) {
            float height = (float) ((StaticLayout) this.letters.get(0)).getHeight();
            canvas.save();
            canvas.translate((float) getPaddingLeft(), (((float) getMeasuredHeight()) - height) / 2.0f);
            int max = Math.max(this.letters.size(), this.oldLetters.size());
            int i = 0;
            while (i < max) {
                canvas.save();
                StaticLayout staticLayout = i < this.oldLetters.size() ? (StaticLayout) this.oldLetters.get(i) : null;
                StaticLayout staticLayout2 = i < this.letters.size() ? (StaticLayout) this.letters.get(i) : null;
                if (this.progress > BitmapDescriptorFactory.HUE_RED) {
                    if (staticLayout != null) {
                        this.textPaint.setAlpha((int) (this.progress * 255.0f));
                        canvas.save();
                        canvas.translate(BitmapDescriptorFactory.HUE_RED, (this.progress - 1.0f) * height);
                        staticLayout.draw(canvas);
                        canvas.restore();
                        if (staticLayout2 != null) {
                            this.textPaint.setAlpha((int) ((1.0f - this.progress) * 255.0f));
                            canvas.translate(BitmapDescriptorFactory.HUE_RED, this.progress * height);
                        }
                    } else {
                        this.textPaint.setAlpha(255);
                    }
                } else if (this.progress < BitmapDescriptorFactory.HUE_RED) {
                    if (staticLayout != null) {
                        this.textPaint.setAlpha((int) ((-this.progress) * 255.0f));
                        canvas.save();
                        canvas.translate(BitmapDescriptorFactory.HUE_RED, (this.progress + 1.0f) * height);
                        staticLayout.draw(canvas);
                        canvas.restore();
                    }
                    if (staticLayout2 != null) {
                        if (i == max - 1 || staticLayout != null) {
                            this.textPaint.setAlpha((int) ((this.progress + 1.0f) * 255.0f));
                            canvas.translate(BitmapDescriptorFactory.HUE_RED, this.progress * height);
                        } else {
                            this.textPaint.setAlpha(255);
                        }
                    }
                } else if (staticLayout2 != null) {
                    this.textPaint.setAlpha(255);
                }
                if (staticLayout2 != null) {
                    staticLayout2.draw(canvas);
                }
                canvas.restore();
                canvas.translate(staticLayout2 != null ? staticLayout2.getLineWidth(0) : staticLayout.getLineWidth(0) + ((float) AndroidUtilities.dp(1.0f)), BitmapDescriptorFactory.HUE_RED);
                i++;
            }
            canvas.restore();
        }
    }

    public void setNumber(int i, boolean z) {
        if (this.currentNumber != i || !z) {
            if (this.animator != null) {
                this.animator.cancel();
                this.animator = null;
            }
            this.oldLetters.clear();
            this.oldLetters.addAll(this.letters);
            this.letters.clear();
            String format = String.format(Locale.US, "%d", new Object[]{Integer.valueOf(this.currentNumber)});
            String format2 = String.format(Locale.US, "%d", new Object[]{Integer.valueOf(i)});
            Object obj = i > this.currentNumber ? 1 : null;
            this.currentNumber = i;
            this.progress = BitmapDescriptorFactory.HUE_RED;
            int i2 = 0;
            while (i2 < format2.length()) {
                CharSequence substring = format2.substring(i2, i2 + 1);
                String substring2 = (this.oldLetters.isEmpty() || i2 >= format.length()) ? null : format.substring(i2, i2 + 1);
                if (substring2 == null || !substring2.equals(substring)) {
                    this.letters.add(new StaticLayout(substring, this.textPaint, (int) Math.ceil((double) this.textPaint.measureText(substring)), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false));
                } else {
                    this.letters.add(this.oldLetters.get(i2));
                    this.oldLetters.set(i2, null);
                }
                i2++;
            }
            if (z && !this.oldLetters.isEmpty()) {
                String str = "progress";
                float[] fArr = new float[2];
                fArr[0] = obj != null ? -1.0f : 1.0f;
                fArr[1] = BitmapDescriptorFactory.HUE_RED;
                this.animator = ObjectAnimator.ofFloat(this, str, fArr);
                this.animator.setDuration(150);
                this.animator.addListener(new C44631());
                this.animator.start();
            }
            invalidate();
        }
    }

    public void setProgress(float f) {
        if (this.progress != f) {
            this.progress = f;
            invalidate();
        }
    }

    public void setTextColor(int i) {
        this.textPaint.setColor(i);
        invalidate();
    }

    public void setTextSize(int i) {
        this.textPaint.setTextSize((float) AndroidUtilities.dp((float) i));
        this.oldLetters.clear();
        this.letters.clear();
        setNumber(this.currentNumber, false);
    }

    public void setTypeface(Typeface typeface) {
        this.textPaint.setTypeface(typeface);
        this.oldLetters.clear();
        this.letters.clear();
        setNumber(this.currentNumber, false);
    }
}
