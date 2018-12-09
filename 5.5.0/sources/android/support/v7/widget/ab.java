package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.az.C0995a;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

@TargetApi(9)
class ab implements ac {
    /* renamed from: a */
    final RectF f2783a = new RectF();

    /* renamed from: android.support.v7.widget.ab$1 */
    class C09961 implements C0995a {
        /* renamed from: a */
        final /* synthetic */ ab f2782a;

        C09961(ab abVar) {
            this.f2782a = abVar;
        }

        /* renamed from: a */
        public void mo902a(Canvas canvas, RectF rectF, float f, Paint paint) {
            float f2 = 2.0f * f;
            float width = (rectF.width() - f2) - 1.0f;
            float height = (rectF.height() - f2) - 1.0f;
            if (f >= 1.0f) {
                float f3 = f + 0.5f;
                this.f2782a.f2783a.set(-f3, -f3, f3, f3);
                int save = canvas.save();
                canvas.translate(rectF.left + f3, rectF.top + f3);
                canvas.drawArc(this.f2782a.f2783a, 180.0f, 90.0f, true, paint);
                canvas.translate(width, BitmapDescriptorFactory.HUE_RED);
                canvas.rotate(90.0f);
                canvas.drawArc(this.f2782a.f2783a, 180.0f, 90.0f, true, paint);
                canvas.translate(height, BitmapDescriptorFactory.HUE_RED);
                canvas.rotate(90.0f);
                canvas.drawArc(this.f2782a.f2783a, 180.0f, 90.0f, true, paint);
                canvas.translate(width, BitmapDescriptorFactory.HUE_RED);
                canvas.rotate(90.0f);
                canvas.drawArc(this.f2782a.f2783a, 180.0f, 90.0f, true, paint);
                canvas.restoreToCount(save);
                canvas.drawRect((rectF.left + f3) - 1.0f, rectF.top, 1.0f + (rectF.right - f3), rectF.top + f3, paint);
                canvas.drawRect((rectF.left + f3) - 1.0f, rectF.bottom - f3, 1.0f + (rectF.right - f3), rectF.bottom, paint);
            }
            canvas.drawRect(rectF.left, rectF.top + f, rectF.right, rectF.bottom - f, paint);
        }
    }

    ab() {
    }

    /* renamed from: a */
    private az m5308a(Context context, ColorStateList colorStateList, float f, float f2, float f3) {
        return new az(context.getResources(), colorStateList, f, f2, f3);
    }

    /* renamed from: j */
    private az m5309j(aa aaVar) {
        return (az) aaVar.mo797c();
    }

    /* renamed from: a */
    public float mo903a(aa aaVar) {
        return m5309j(aaVar).m5595c();
    }

    /* renamed from: a */
    public void mo904a() {
        az.f2958c = new C09961(this);
    }

    /* renamed from: a */
    public void mo905a(aa aaVar, float f) {
        m5309j(aaVar).m5588a(f);
        m5321f(aaVar);
    }

    /* renamed from: a */
    public void mo906a(aa aaVar, Context context, ColorStateList colorStateList, float f, float f2, float f3) {
        Drawable a = m5308a(context, colorStateList, f, f2, f3);
        a.m5592a(aaVar.mo796b());
        aaVar.mo794a(a);
        m5321f(aaVar);
    }

    /* renamed from: a */
    public void mo907a(aa aaVar, ColorStateList colorStateList) {
        m5309j(aaVar).m5590a(colorStateList);
    }

    /* renamed from: b */
    public float mo908b(aa aaVar) {
        return m5309j(aaVar).m5597d();
    }

    /* renamed from: b */
    public void mo909b(aa aaVar, float f) {
        m5309j(aaVar).m5596c(f);
        m5321f(aaVar);
    }

    /* renamed from: c */
    public float mo910c(aa aaVar) {
        return m5309j(aaVar).m5598e();
    }

    /* renamed from: c */
    public void mo911c(aa aaVar, float f) {
        m5309j(aaVar).m5594b(f);
    }

    /* renamed from: d */
    public float mo912d(aa aaVar) {
        return m5309j(aaVar).m5587a();
    }

    /* renamed from: e */
    public float mo913e(aa aaVar) {
        return m5309j(aaVar).m5593b();
    }

    /* renamed from: f */
    public void m5321f(aa aaVar) {
        Rect rect = new Rect();
        m5309j(aaVar).m5591a(rect);
        aaVar.mo792a((int) Math.ceil((double) mo908b(aaVar)), (int) Math.ceil((double) mo910c(aaVar)));
        aaVar.mo793a(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* renamed from: g */
    public void mo914g(aa aaVar) {
    }

    /* renamed from: h */
    public void mo915h(aa aaVar) {
        m5309j(aaVar).m5592a(aaVar.mo796b());
        m5321f(aaVar);
    }

    /* renamed from: i */
    public ColorStateList mo916i(aa aaVar) {
        return m5309j(aaVar).m5599f();
    }
}
