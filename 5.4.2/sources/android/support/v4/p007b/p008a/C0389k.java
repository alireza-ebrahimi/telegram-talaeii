package android.support.v4.p007b.p008a;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.p007b.p008a.C0385i.C0383a;

@TargetApi(19)
/* renamed from: android.support.v4.b.a.k */
class C0389k extends C0387j {

    /* renamed from: android.support.v4.b.a.k$a */
    private static class C0388a extends C0383a {
        C0388a(C0383a c0383a, Resources resources) {
            super(c0383a, resources);
        }

        public Drawable newDrawable(Resources resources) {
            return new C0389k(this, resources);
        }
    }

    C0389k(Drawable drawable) {
        super(drawable);
    }

    C0389k(C0383a c0383a, Resources resources) {
        super(c0383a, resources);
    }

    /* renamed from: b */
    C0383a mo309b() {
        return new C0388a(this.b, null);
    }

    public boolean isAutoMirrored() {
        return this.c.isAutoMirrored();
    }

    public void setAutoMirrored(boolean z) {
        this.c.setAutoMirrored(z);
    }
}
