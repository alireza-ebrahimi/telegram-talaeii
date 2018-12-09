package android.support.v7.widget;

import android.annotation.TargetApi;
import android.graphics.Outline;

@TargetApi(21)
/* renamed from: android.support.v7.widget.c */
class C1044c extends C1034b {
    public C1044c(ActionBarContainer actionBarContainer) {
        super(actionBarContainer);
    }

    public void getOutline(Outline outline) {
        if (this.a.f2277d) {
            if (this.a.f2276c != null) {
                this.a.f2276c.getOutline(outline);
            }
        } else if (this.a.f2274a != null) {
            this.a.f2274a.getOutline(outline);
        }
    }
}
