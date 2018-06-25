package org.telegram.customization.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.animation.Animation;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import java.lang.reflect.Field;

public class CircularProgressBarAutoRotate extends CircularProgressBar {
    public CircularProgressBarAutoRotate(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    int getStartAngle() {
        int value = 0;
        try {
            Field field = CircularProgressBar.class.getDeclaredField("startAngle");
            field.setAccessible(true);
            value = ((Integer) field.get(this)).intValue();
            field.setAccessible(false);
            return value;
        } catch (Exception e) {
            return value;
        }
    }

    public void setStartAngle(int angle) {
        try {
            Field field = CircularProgressBar.class.getDeclaredField("startAngle");
            field.setAccessible(true);
            field.set(this, Integer.valueOf(angle));
            field.setAccessible(false);
        } catch (Exception e) {
        }
    }

    void addStartAngle(int angle) {
        setStartAngle(getStartAngle() + angle);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void startAnimation(Animation animation) {
    }
}
