package org.telegram.customization.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.animation.Animation;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import java.lang.reflect.Field;

public class CircularProgressBarAutoRotate extends CircularProgressBar {
    public CircularProgressBarAutoRotate(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    int getStartAngle() {
        try {
            Field declaredField = CircularProgressBar.class.getDeclaredField("f");
            declaredField.setAccessible(true);
            int intValue = ((Integer) declaredField.get(this)).intValue();
            try {
                declaredField.setAccessible(false);
                return intValue;
            } catch (Exception e) {
                return intValue;
            }
        } catch (Exception e2) {
            return 0;
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setStartAngle(int i) {
        try {
            Field declaredField = CircularProgressBar.class.getDeclaredField("f");
            declaredField.setAccessible(true);
            declaredField.set(this, Integer.valueOf(i));
            declaredField.setAccessible(false);
        } catch (Exception e) {
        }
    }

    public void startAnimation(Animation animation) {
    }
}
