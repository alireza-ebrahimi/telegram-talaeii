package org.telegram.customization.util.view.p154a.p170b;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.C0955v;
import android.support.v7.widget.p032a.C0989a.C0986a;
import android.util.Log;

/* renamed from: org.telegram.customization.util.view.a.b.c */
public class C2921c extends C0986a {
    /* renamed from: a */
    private final C2919a f9613a;

    public C2921c(C2919a c2919a) {
        this.f9613a = c2919a;
    }

    /* renamed from: a */
    public int mo3504a(RecyclerView recyclerView, C0955v c0955v) {
        Log.d("alireza", "alireza + 3");
        return C0986a.m5219b(3, 48);
    }

    /* renamed from: a */
    public void mo3505a(C0955v c0955v, int i) {
        this.f9613a.mo3502a(c0955v.getAdapterPosition());
    }

    /* renamed from: a */
    public boolean mo3506a() {
        return true;
    }

    /* renamed from: b */
    public void mo3507b(C0955v c0955v, int i) {
        if (i != 0) {
            ((C2917b) c0955v).mo3500a();
        }
        super.mo3507b(c0955v, i);
    }

    /* renamed from: b */
    public boolean mo3508b() {
        return false;
    }

    /* renamed from: b */
    public boolean mo3509b(RecyclerView recyclerView, C0955v c0955v, C0955v c0955v2) {
        this.f9613a.mo3503a(c0955v.getAdapterPosition(), c0955v2.getAdapterPosition());
        return true;
    }

    /* renamed from: d */
    public void mo3510d(RecyclerView recyclerView, C0955v c0955v) {
        super.mo3510d(recyclerView, c0955v);
        ((C2917b) c0955v).mo3501b();
    }
}
