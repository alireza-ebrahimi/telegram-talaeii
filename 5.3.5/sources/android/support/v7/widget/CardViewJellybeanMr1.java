package android.support.v7.widget;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.RequiresApi;

@TargetApi(17)
@RequiresApi(17)
class CardViewJellybeanMr1 extends CardViewGingerbread {

    /* renamed from: android.support.v7.widget.CardViewJellybeanMr1$1 */
    class C03391 implements RoundRectHelper {
        C03391() {
        }

        public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius, Paint paint) {
            canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
        }
    }

    CardViewJellybeanMr1() {
    }

    public void initStatic() {
        RoundRectDrawableWithShadow.sRoundRectHelper = new C03391();
    }
}
