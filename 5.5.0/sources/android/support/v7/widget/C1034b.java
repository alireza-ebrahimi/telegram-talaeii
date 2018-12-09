package android.support.v7.widget;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

@TargetApi(9)
/* renamed from: android.support.v7.widget.b */
class C1034b extends Drawable {
    /* renamed from: a */
    final ActionBarContainer f2976a;

    public C1034b(ActionBarContainer actionBarContainer) {
        this.f2976a = actionBarContainer;
    }

    public void draw(Canvas canvas) {
        if (!this.f2976a.f2277d) {
            if (this.f2976a.f2274a != null) {
                this.f2976a.f2274a.draw(canvas);
            }
            if (this.f2976a.f2275b != null && this.f2976a.f2278e) {
                this.f2976a.f2275b.draw(canvas);
            }
        } else if (this.f2976a.f2276c != null) {
            this.f2976a.f2276c.draw(canvas);
        }
    }

    public int getOpacity() {
        return 0;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }
}
