package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.p025a.C0748a.C0738a;
import android.util.AttributeSet;
import android.widget.SeekBar;

/* renamed from: android.support.v7.widget.t */
public class C1077t extends SeekBar {
    /* renamed from: a */
    private C1078u f3194a;

    public C1077t(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0738a.seekBarStyle);
    }

    public C1077t(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f3194a = new C1078u(this);
        this.f3194a.mo1010a(attributeSet, i);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.f3194a.m5904c();
    }

    @TargetApi(11)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        this.f3194a.mo1011b();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.f3194a.m5900a(canvas);
    }
}
