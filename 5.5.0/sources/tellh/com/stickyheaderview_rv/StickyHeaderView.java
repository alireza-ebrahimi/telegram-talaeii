package tellh.com.stickyheaderview_rv;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.C0926a;
import android.support.v7.widget.RecyclerView.C0944m;
import android.support.v7.widget.RecyclerView.C0955v;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import tellh.com.stickyheaderview_rv.p179a.C5302c;
import tellh.com.stickyheaderview_rv.p179a.C5304a;
import tellh.com.stickyheaderview_rv.p179a.C5306e;
import tellh.com.stickyheaderview_rv.p179a.C5306e.C5301a;
import tellh.com.stickyheaderview_rv.p179a.C5308f;

public class StickyHeaderView extends FrameLayout implements C5301a {
    /* renamed from: a */
    private boolean f10203a = false;
    /* renamed from: b */
    private FrameLayout f10204b;
    /* renamed from: c */
    private RecyclerView f10205c;
    /* renamed from: d */
    private int f10206d = -1;
    /* renamed from: e */
    private C5306e f10207e;
    /* renamed from: f */
    private LinearLayoutManager f10208f;
    /* renamed from: g */
    private C5309a<C5304a> f10209g = new C5309a();
    /* renamed from: h */
    private SparseArray<C0955v> f10210h;

    /* renamed from: tellh.com.stickyheaderview_rv.StickyHeaderView$1 */
    class C53001 extends C0944m {
        /* renamed from: a */
        final /* synthetic */ StickyHeaderView f10202a;

        C53001(StickyHeaderView stickyHeaderView) {
            this.f10202a = stickyHeaderView;
        }

        /* renamed from: a */
        public void m14094a(RecyclerView recyclerView, int i) {
            super.a(recyclerView, i);
            if (this.f10202a.f10206d == -1 || this.f10202a.f10207e == null || this.f10202a.f10208f == null) {
                this.f10202a.f10206d = this.f10202a.f10204b.getHeight();
                C0926a adapter = this.f10202a.f10205c.getAdapter();
                if (adapter instanceof C5306e) {
                    this.f10202a.f10207e = (C5306e) adapter;
                    this.f10202a.f10207e.m14116a(this.f10202a);
                    this.f10202a.f10208f = (LinearLayoutManager) this.f10202a.f10205c.getLayoutManager();
                    this.f10202a.f10210h = new SparseArray();
                    return;
                }
                throw new RuntimeException("Your RecyclerView.Adapter should be the type of StickyHeaderViewAdapter.");
            }
        }

        /* renamed from: a */
        public void m14095a(RecyclerView recyclerView, int i, int i2) {
            super.a(recyclerView, i, i2);
            if (this.f10202a.f10206d != -1 && this.f10202a.f10207e != null && this.f10202a.f10208f != null) {
                if (this.f10202a.f10209g.m14121c() && this.f10202a.f10207e.m14112a() != 0) {
                    this.f10202a.f10209g.m14119a(this.f10202a.f10207e.m14114a(this.f10202a.m14096a(0)));
                }
                int l = this.f10202a.f10208f.l();
                if (l != -1) {
                    int b = this.f10202a.m14096a(l);
                    int a = this.f10202a.f10207e.m14113a((C5304a) this.f10202a.f10209g.m14120b());
                    if (b - l <= 1) {
                        C5304a a2 = this.f10202a.f10207e.m14114a(b + 1);
                        if (a2 != null && a2.shouldSticky()) {
                            b++;
                        }
                        View c = this.f10202a.f10208f.c(b);
                        if (c != null) {
                            int top = c.getTop();
                            if (top > 0 && top <= this.f10202a.f10206d) {
                                this.f10202a.f10204b.setY((float) (-(this.f10202a.f10206d - top)));
                                if (b == a) {
                                    this.f10202a.f10209g.m14118a();
                                    if (!this.f10202a.f10209g.m14121c()) {
                                        this.f10202a.m14104a((C5304a) this.f10202a.f10209g.m14120b());
                                    }
                                }
                            } else if (top <= 0) {
                                this.f10202a.f10204b.setY(BitmapDescriptorFactory.HUE_RED);
                                this.f10202a.m14104a(this.f10202a.f10207e.m14114a(l));
                            }
                            if (b > a) {
                                this.f10202a.f10209g.m14119a(this.f10202a.f10207e.m14114a(b));
                            } else if (b < a) {
                                this.f10202a.f10209g.m14118a();
                            }
                        }
                    }
                }
            }
        }
    }

    public StickyHeaderView(Context context) {
        super(context);
    }

    public StickyHeaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public StickyHeaderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* renamed from: a */
    private int m14096a(int i) {
        while (i < this.f10207e.m14112a() && !this.f10207e.m14114a(i).shouldSticky()) {
            i++;
        }
        return i;
    }

    /* renamed from: a */
    private void m14102a() {
        if (!this.f10203a) {
            this.f10203a = true;
            View childAt = getChildAt(0);
            if (childAt instanceof RecyclerView) {
                this.f10205c = (RecyclerView) childAt;
                this.f10204b = new FrameLayout(getContext());
                this.f10204b.setBackgroundColor(-1);
                this.f10204b.setLayoutParams(new LayoutParams(-1, -2));
                addView(this.f10204b);
                this.f10205c.a(new C53001(this));
                return;
            }
            throw new RuntimeException("RecyclerView should be the first child view.");
        }
    }

    /* renamed from: a */
    private void m14104a(C5304a c5304a) {
        int itemLayoutId = c5304a.getItemLayoutId(this.f10207e);
        m14107b();
        C0955v c0955v = (C0955v) this.f10210h.get(itemLayoutId);
        C5308f b = this.f10207e.m14117b(itemLayoutId);
        if (c0955v == null) {
            c0955v = b.provideViewHolder(LayoutInflater.from(this.f10204b.getContext()).inflate(itemLayoutId, this.f10204b, false));
            this.f10210h.put(itemLayoutId, c0955v);
        }
        this.f10204b.addView(c0955v.itemView);
        this.f10206d = this.f10204b.getHeight();
        b.bindView(this.f10207e, c0955v, this.f10207e.m14113a(c5304a), (C5302c) c5304a);
    }

    /* renamed from: b */
    private void m14107b() {
        this.f10204b.removeAllViews();
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        m14102a();
        super.onLayout(z, i, i2, i3, i4);
    }
}
