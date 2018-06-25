package tellh.com.stickyheaderview_rv.p179a;

import android.support.v4.p022f.C0482l;
import android.support.v7.widget.RecyclerView.C0926a;
import android.support.v7.widget.RecyclerView.C0955v;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/* renamed from: tellh.com.stickyheaderview_rv.a.e */
public class C5306e extends C0926a<C0955v> {
    /* renamed from: b */
    private static int f10211b = 10;
    /* renamed from: a */
    protected List<C5304a> f10212a = new ArrayList();
    /* renamed from: c */
    private C0482l<C5308f> f10213c = new C0482l(f10211b);
    /* renamed from: d */
    private C5301a f10214d;

    /* renamed from: tellh.com.stickyheaderview_rv.a.e$a */
    public interface C5301a {
    }

    public C5306e(List<? extends C5304a> list) {
        this.f10212a.addAll(list);
    }

    /* renamed from: a */
    public int m14110a() {
        return this.f10212a == null ? 0 : this.f10212a.size();
    }

    /* renamed from: a */
    public int m14111a(C5304a c5304a) {
        return this.f10212a.indexOf(c5304a);
    }

    /* renamed from: a */
    public C5304a m14112a(int i) {
        return i < m14110a() ? (C5304a) this.f10212a.get(i) : null;
    }

    /* renamed from: a */
    public C5306e m14113a(C5308f c5308f) {
        this.f10213c.b(c5308f.getItemLayoutId(this), c5308f);
        return this;
    }

    /* renamed from: a */
    public void m14114a(C5301a c5301a) {
        this.f10214d = c5301a;
    }

    /* renamed from: b */
    public C5308f m14115b(int i) {
        return (C5308f) this.f10213c.a(i);
    }

    public int getItemCount() {
        return this.f10212a == null ? 0 : this.f10212a.size();
    }

    public int getItemViewType(int i) {
        return ((C5304a) this.f10212a.get(i)).getItemLayoutId(this);
    }

    public void onBindViewHolder(C0955v c0955v, int i) {
        C5304a c5304a = (C5304a) this.f10212a.get(i);
        c5304a.provideViewBinder(this, this.f10213c, i).bindView(this, c0955v, i, c5304a);
    }

    public C0955v onCreateViewHolder(ViewGroup viewGroup, int i) {
        C5308f b = m14115b(i);
        return b.provideViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(b.getItemLayoutId(this), viewGroup, false));
    }
}
