package org.telegram.customization.util.view.sva.p173a.p174a;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import org.telegram.customization.util.view.sva.p173a.C2960a;

/* renamed from: org.telegram.customization.util.view.sva.a.a.a */
public class C2961a extends C2960a {
    /* renamed from: h */
    private float f9829h;
    /* renamed from: i */
    private float f9830i;
    /* renamed from: j */
    private float f9831j;
    /* renamed from: k */
    private float f9832k;
    /* renamed from: l */
    private float f9833l;
    /* renamed from: m */
    private float f9834m;
    /* renamed from: n */
    private RectF f9835n = new RectF();
    /* renamed from: o */
    private RectF f9836o = new RectF();
    /* renamed from: p */
    private float f9837p = 0.707f;

    /* renamed from: a */
    private void m13664a(Paint paint, Canvas canvas) {
        canvas.save();
        if (((double) this.a) <= 0.25d) {
            canvas.drawLine(this.f9829h - this.f9831j, this.f9834m - this.f9832k, this.f9833l, this.f9834m - this.f9832k, paint);
            canvas.drawLine(this.f9829h - this.f9831j, this.f9834m - this.f9832k, (this.f9832k * (0.25f - this.a)) + (this.f9829h - this.f9831j), (this.f9834m - this.f9832k) - (this.f9832k * (0.25f - this.a)), paint);
            canvas.drawLine(this.f9829h - this.f9831j, this.f9834m - this.f9832k, (this.f9832k * (0.25f - this.a)) + (this.f9829h - this.f9831j), (this.f9832k * (0.25f - this.a)) + (this.f9834m - this.f9832k), paint);
        } else if (((double) this.a) <= 0.25d || this.a > 0.5f) {
            canvas.drawLine(((this.f9831j * this.f9837p) * (1.0f - ((this.a - 0.5f) * 2.0f))) + (this.f9829h + (this.f9831j * this.f9837p)), ((this.f9831j * this.f9837p) * (1.0f - ((this.a - 0.5f) * 2.0f))) + (this.f9830i + (this.f9831j * this.f9837p)), ((this.f9831j * 2.0f) * this.f9837p) + this.f9829h, ((this.f9831j * 2.0f) * this.f9837p) + this.f9830i, paint);
            canvas.drawArc(this.f9836o, 45.0f, (this.a - 0.5f) * 720.0f, false, paint);
        } else {
            canvas.drawArc(this.f9835n, -90.0f, 4.0f * (180.0f * (this.a - 0.25f)), false, paint);
            canvas.drawLine(((((this.f9833l - this.f9829h) + this.f9831j) * (this.a - 0.25f)) * 4.0f) + (this.f9829h - this.f9831j), this.f9834m - this.f9832k, this.f9833l, this.f9834m - this.f9832k, paint);
        }
        canvas.restore();
    }

    /* renamed from: b */
    private void m13665b(Paint paint, Canvas canvas) {
        canvas.save();
        if (((double) this.a) <= 0.75d) {
            canvas.drawArc(this.f9836o, 45.0f, (1.0f - (this.a / 0.75f)) * 360.0f, false, paint);
        }
        if (((double) this.a) <= 0.25d) {
            canvas.drawLine((this.f9829h + (this.f9831j * this.f9837p)) + (((this.f9831j * this.f9837p) * this.a) * 4.0f), (this.f9830i + (this.f9831j * this.f9837p)) + (((this.f9831j * this.f9837p) * this.a) * 4.0f), this.f9829h + ((this.f9831j * 2.0f) * this.f9837p), this.f9830i + ((this.f9831j * 2.0f) * this.f9837p), paint);
            canvas.drawArc(this.f9835n, 90.0f, (-180.0f * this.a) * 4.0f, false, paint);
        } else if (((double) this.a) > 0.25d && this.a <= 0.5f) {
            canvas.drawArc(this.f9835n, -90.0f, (1.0f - ((this.a - 0.25f) * 4.0f)) * 180.0f, false, paint);
            canvas.drawLine(this.f9829h - ((this.f9831j * (this.a - 0.25f)) * 4.0f), this.f9834m - this.f9832k, this.f9833l, this.f9834m - this.f9832k, paint);
        } else if (this.a <= 0.5f || this.a >= 0.75f) {
            canvas.drawLine(this.f9829h - this.f9831j, this.f9834m - this.f9832k, this.f9833l - 20.0f, this.f9834m - this.f9832k, paint);
            canvas.drawLine(this.f9829h - this.f9831j, this.f9834m - this.f9832k, (this.f9832k * this.a) + (this.f9829h - this.f9831j), (this.f9834m - this.f9832k) - (this.f9832k * this.a), paint);
            canvas.drawLine(this.f9829h - this.f9831j, this.f9834m - this.f9832k, (this.f9832k * this.a) + (this.f9829h - this.f9831j), (this.f9832k * this.a) + (this.f9834m - this.f9832k), paint);
        } else {
            canvas.drawLine(this.f9829h - ((this.f9831j * (this.a - 0.5f)) * 4.0f), this.f9834m - this.f9832k, this.f9833l - 20.0f, this.f9834m - this.f9832k, paint);
        }
        canvas.restore();
    }

    /* renamed from: c */
    private void m13666c(Paint paint, Canvas canvas) {
        this.f9831j = (float) (m13657b() / 10);
        this.f9832k = (float) (m13657b() / 15);
        this.f9831j *= this.f;
        this.f9832k *= this.f;
        this.f9829h = (float) (m13657b() / 2);
        this.f9830i = (float) (m13660c() / 2);
        this.f9833l = this.f9829h + ((this.f9831j * 2.0f) * this.f9837p);
        this.f9834m = this.f9830i + (((this.f9831j * 2.0f) * this.f9837p) - this.f9832k);
        this.f9835n.left = this.f9833l - this.f9832k;
        this.f9835n.right = this.f9833l + this.f9832k;
        this.f9835n.top = this.f9834m - this.f9832k;
        this.f9835n.bottom = this.f9834m + this.f9832k;
        this.f9836o.left = this.f9829h - this.f9831j;
        this.f9836o.right = this.f9829h + this.f9831j;
        this.f9836o.top = this.f9830i - this.f9831j;
        this.f9836o.bottom = this.f9830i + this.f9831j;
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Cap.ROUND);
        canvas.save();
        paint.setColor(this.c);
        paint.setStrokeWidth(this.e);
        paint.setStyle(Style.STROKE);
        canvas.drawCircle(this.f9829h, this.f9830i, this.f9831j, paint);
        canvas.drawLine((this.f9831j * this.f9837p) + this.f9829h, (this.f9831j * this.f9837p) + this.f9830i, this.f9833l, ((this.f9831j * 2.0f) * this.f9837p) + this.f9830i, paint);
        canvas.restore();
    }

    /* renamed from: a */
    public void mo3517a(Canvas canvas, Paint paint) {
        paint.setColor(this.c);
        switch (this.g) {
            case 0:
                m13666c(paint, canvas);
                return;
            case 1:
                m13665b(paint, canvas);
                return;
            case 2:
                m13664a(paint, canvas);
                return;
            default:
                return;
        }
    }

    /* renamed from: d */
    public void mo3518d() {
        if (this.g != 1) {
            this.g = 1;
            m13663f();
        }
    }

    /* renamed from: e */
    public void mo3519e() {
        if (this.g != 2) {
            this.g = 2;
            m13663f();
        }
    }
}
