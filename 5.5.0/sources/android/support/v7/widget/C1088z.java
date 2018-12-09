package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;

@TargetApi(21)
/* renamed from: android.support.v7.widget.z */
class C1088z implements ac {
    C1088z() {
    }

    /* renamed from: j */
    private ay m5924j(aa aaVar) {
        return (ay) aaVar.mo797c();
    }

    /* renamed from: a */
    public float mo903a(aa aaVar) {
        return m5924j(aaVar).m5574a();
    }

    /* renamed from: a */
    public void mo904a() {
    }

    /* renamed from: a */
    public void mo905a(aa aaVar, float f) {
        m5924j(aaVar).m5575a(f);
    }

    /* renamed from: a */
    public void mo906a(aa aaVar, Context context, ColorStateList colorStateList, float f, float f2, float f3) {
        aaVar.mo794a(new ay(colorStateList, f));
        View d = aaVar.mo798d();
        d.setClipToOutline(true);
        d.setElevation(f2);
        mo909b(aaVar, f3);
    }

    /* renamed from: a */
    public void mo907a(aa aaVar, ColorStateList colorStateList) {
        m5924j(aaVar).m5577a(colorStateList);
    }

    /* renamed from: b */
    public float mo908b(aa aaVar) {
        return mo912d(aaVar) * 2.0f;
    }

    /* renamed from: b */
    public void mo909b(aa aaVar, float f) {
        m5924j(aaVar).m5576a(f, aaVar.mo795a(), aaVar.mo796b());
        m5936f(aaVar);
    }

    /* renamed from: c */
    public float mo910c(aa aaVar) {
        return mo912d(aaVar) * 2.0f;
    }

    /* renamed from: c */
    public void mo911c(aa aaVar, float f) {
        aaVar.mo798d().setElevation(f);
    }

    /* renamed from: d */
    public float mo912d(aa aaVar) {
        return m5924j(aaVar).m5578b();
    }

    /* renamed from: e */
    public float mo913e(aa aaVar) {
        return aaVar.mo798d().getElevation();
    }

    /* renamed from: f */
    public void m5936f(aa aaVar) {
        if (aaVar.mo795a()) {
            float a = mo903a(aaVar);
            float d = mo912d(aaVar);
            int ceil = (int) Math.ceil((double) az.m5582b(a, d, aaVar.mo796b()));
            int ceil2 = (int) Math.ceil((double) az.m5580a(a, d, aaVar.mo796b()));
            aaVar.mo793a(ceil, ceil2, ceil, ceil2);
            return;
        }
        aaVar.mo793a(0, 0, 0, 0);
    }

    /* renamed from: g */
    public void mo914g(aa aaVar) {
        mo909b(aaVar, mo903a(aaVar));
    }

    /* renamed from: h */
    public void mo915h(aa aaVar) {
        mo909b(aaVar, mo903a(aaVar));
    }

    /* renamed from: i */
    public ColorStateList mo916i(aa aaVar) {
        return m5924j(aaVar).m5579c();
    }
}
