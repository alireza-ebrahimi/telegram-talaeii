package utils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundedImageView extends ImageView {
    public static float radius = 10.0f;

    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onDraw(Canvas canvas) {
        if (VERSION.SDK_INT >= 18 || VERSION.SDK_INT < 11) {
            Path clipPath = new Path();
            clipPath.addRoundRect(new RectF(0.0f, 0.0f, (float) getWidth(), (float) getHeight()), radius, radius, Direction.CW);
            canvas.clipPath(clipPath);
        } else {
            setLayerType(1, null);
        }
        super.onDraw(canvas);
    }
}
