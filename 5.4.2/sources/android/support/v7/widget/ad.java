package android.support.v7.widget;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.az.C0995a;

@TargetApi(17)
class ad extends ab {

    /* renamed from: android.support.v7.widget.ad$1 */
    class C09971 implements C0995a {
        /* renamed from: a */
        final /* synthetic */ ad f2784a;

        C09971(ad adVar) {
            this.f2784a = adVar;
        }

        /* renamed from: a */
        public void mo902a(Canvas canvas, RectF rectF, float f, Paint paint) {
            canvas.drawRoundRect(rectF, f, f, paint);
        }
    }

    ad() {
    }

    /* renamed from: a */
    public void mo904a() {
        az.f2958c = new C09971(this);
    }
}
