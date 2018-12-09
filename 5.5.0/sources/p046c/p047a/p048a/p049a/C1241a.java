package p046c.p047a.p048a.p049a;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView.C0926a;
import android.support.v7.widget.RecyclerView.C0928c;
import android.support.v7.widget.RecyclerView.C0955v;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import p046c.p047a.p048a.p050b.C1243a;

/* renamed from: c.a.a.a.a */
public abstract class C1241a extends C0926a<C0955v> {
    /* renamed from: a */
    private C0926a<C0955v> f3597a;
    /* renamed from: b */
    private int f3598b = 300;
    /* renamed from: c */
    private Interpolator f3599c = new LinearInterpolator();
    /* renamed from: d */
    private int f3600d = -1;
    /* renamed from: e */
    private boolean f3601e = true;

    public C1241a(C0926a<C0955v> c0926a) {
        this.f3597a = c0926a;
    }

    /* renamed from: a */
    public void m6473a(int i) {
        this.f3598b = i;
    }

    /* renamed from: a */
    protected abstract Animator[] mo1092a(View view);

    public int getItemCount() {
        return this.f3597a.getItemCount();
    }

    public long getItemId(int i) {
        return this.f3597a.getItemId(i);
    }

    public int getItemViewType(int i) {
        return this.f3597a.getItemViewType(i);
    }

    public void onBindViewHolder(C0955v c0955v, int i) {
        this.f3597a.onBindViewHolder(c0955v, i);
        int adapterPosition = c0955v.getAdapterPosition();
        if (!this.f3601e || adapterPosition > this.f3600d) {
            for (Animator animator : mo1092a(c0955v.itemView)) {
                animator.setDuration((long) this.f3598b).start();
                animator.setInterpolator(this.f3599c);
            }
            this.f3600d = adapterPosition;
            return;
        }
        C1243a.m6476a(c0955v.itemView);
    }

    public C0955v onCreateViewHolder(ViewGroup viewGroup, int i) {
        return this.f3597a.onCreateViewHolder(viewGroup, i);
    }

    public void onViewRecycled(C0955v c0955v) {
        this.f3597a.onViewRecycled(c0955v);
        super.onViewRecycled(c0955v);
    }

    public void registerAdapterDataObserver(C0928c c0928c) {
        super.registerAdapterDataObserver(c0928c);
        this.f3597a.registerAdapterDataObserver(c0928c);
    }

    public void unregisterAdapterDataObserver(C0928c c0928c) {
        super.unregisterAdapterDataObserver(c0928c);
        this.f3597a.unregisterAdapterDataObserver(c0928c);
    }
}
