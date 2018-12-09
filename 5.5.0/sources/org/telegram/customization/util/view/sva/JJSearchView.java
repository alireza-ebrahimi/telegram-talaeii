package org.telegram.customization.util.view.sva;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import org.telegram.customization.util.view.sva.p173a.C2960a;
import org.telegram.customization.util.view.sva.p173a.p174a.C2961a;
import org.telegram.messenger.C3336R;

public class JJSearchView extends View {
    /* renamed from: a */
    int f9811a;
    /* renamed from: b */
    int f9812b;
    /* renamed from: c */
    float f9813c;
    /* renamed from: d */
    float f9814d;
    /* renamed from: e */
    private Paint f9815e;
    /* renamed from: f */
    private Path f9816f;
    /* renamed from: g */
    private C2960a f9817g;

    public JJSearchView(Context context) {
        this(context, null);
    }

    public JJSearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        m13648a(context, attributeSet);
    }

    public JJSearchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f9811a = 0;
        this.f9812b = 0;
        this.f9813c = 7.0f;
        this.f9814d = 2.0f;
        this.f9817g = new C2961a();
        m13648a(context, attributeSet);
        m13646c();
    }

    /* renamed from: c */
    private void m13646c() {
        this.f9815e = new Paint(1);
        this.f9815e.setStrokeWidth(4.0f);
        this.f9816f = new Path();
    }

    /* renamed from: a */
    public void m13647a() {
        if (this.f9817g != null) {
            this.f9817g.mo3518d();
        }
    }

    /* renamed from: a */
    void m13648a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C3336R.styleable.JJSearchView);
        this.f9811a = obtainStyledAttributes.getColor(1, this.f9811a);
        this.f9812b = obtainStyledAttributes.getColor(1, this.f9812b);
        this.f9813c = obtainStyledAttributes.getFloat(2, this.f9813c);
        this.f9814d = obtainStyledAttributes.getFloat(3, this.f9814d);
        obtainStyledAttributes.recycle();
    }

    /* renamed from: b */
    public void m13649b() {
        if (this.f9817g != null) {
            this.f9817g.mo3519e();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.f9817g.m13654a(this.f9811a);
        this.f9817g.m13659b(this.f9812b);
        this.f9817g.m13653a(this.f9813c);
        this.f9817g.m13658b(this.f9814d);
        this.f9817g.mo3517a(canvas, this.f9815e);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    public void setController(C2960a c2960a) {
        this.f9817g = c2960a;
        this.f9817g.m13656a((View) this);
        invalidate();
    }
}
