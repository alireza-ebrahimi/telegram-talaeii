package android.support.v4.p007b.p008a;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.p007b.p008a.C0385i.C0383a;

@TargetApi(11)
/* renamed from: android.support.v4.b.a.j */
class C0387j extends C0385i {

    /* renamed from: android.support.v4.b.a.j$a */
    private static class C0386a extends C0383a {
        C0386a(C0383a c0383a, Resources resources) {
            super(c0383a, resources);
        }

        public Drawable newDrawable(Resources resources) {
            return new C0387j(this, resources);
        }
    }

    C0387j(Drawable drawable) {
        super(drawable);
    }

    C0387j(C0383a c0383a, Resources resources) {
        super(c0383a, resources);
    }

    /* renamed from: b */
    C0383a mo309b() {
        return new C0386a(this.b, null);
    }

    public void jumpToCurrentState() {
        this.c.jumpToCurrentState();
    }
}
