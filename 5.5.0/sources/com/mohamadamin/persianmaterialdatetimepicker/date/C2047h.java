package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2017a;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.h */
public class C2047h extends C2044e {
    public C2047h(Context context, AttributeSet attributeSet, C2031a c2031a) {
        super(context, attributeSet, c2031a);
    }

    /* renamed from: a */
    public void mo3086a(Canvas canvas, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        if (this.x == i3) {
            canvas.drawCircle((float) i4, (float) (i5 - (d / 3)), (float) h, this.n);
        }
        if (m9203b(i, i2, i3)) {
            this.l.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
        } else {
            this.l.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
        }
        if (m9198a(i, i2, i3)) {
            this.l.setColor(this.M);
        } else if (this.x == i3) {
            this.l.setColor(this.I);
        } else if (this.w && this.y == i3) {
            this.l.setColor(this.K);
        } else {
            this.l.setColor(m9203b(i, i2, i3) ? this.L : this.H);
        }
        canvas.drawText(C2017a.m9087a(String.format("%d", new Object[]{Integer.valueOf(i3)})), (float) i4, (float) i5, this.l);
    }
}
