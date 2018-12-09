package utils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class RoundedImageView extends ImageView {
    /* renamed from: a */
    public static float f10288a = 10.0f;

    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RoundedImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected void onDraw(Canvas canvas) {
        if (VERSION.SDK_INT >= 18 || VERSION.SDK_INT < 11) {
            Path path = new Path();
            path.addRoundRect(new RectF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getWidth(), (float) getHeight()), f10288a, f10288a, Direction.CW);
            canvas.clipPath(path);
        } else {
            setLayerType(1, null);
        }
        super.onDraw(canvas);
    }
}
