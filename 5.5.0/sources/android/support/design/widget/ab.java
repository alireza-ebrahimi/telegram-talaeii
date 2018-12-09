package android.support.design.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout.C0088a;
import android.util.AttributeSet;
import android.view.View;

class ab<V extends View> extends C0088a<V> {
    /* renamed from: a */
    private ac f219a;
    /* renamed from: b */
    private int f220b = 0;
    /* renamed from: c */
    private int f221c = 0;

    public ab(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* renamed from: a */
    public boolean mo73a(int i) {
        if (this.f219a != null) {
            return this.f219a.m653a(i);
        }
        this.f220b = i;
        return false;
    }

    /* renamed from: a */
    public boolean mo62a(CoordinatorLayout coordinatorLayout, V v, int i) {
        mo81b(coordinatorLayout, v, i);
        if (this.f219a == null) {
            this.f219a = new ac(v);
        }
        this.f219a.m652a();
        if (this.f220b != 0) {
            this.f219a.m653a(this.f220b);
            this.f220b = 0;
        }
        if (this.f221c != 0) {
            this.f219a.m655b(this.f221c);
            this.f221c = 0;
        }
        return true;
    }

    /* renamed from: b */
    public int mo77b() {
        return this.f219a != null ? this.f219a.m654b() : 0;
    }

    /* renamed from: b */
    protected void mo81b(CoordinatorLayout coordinatorLayout, V v, int i) {
        coordinatorLayout.m541a((View) v, i);
    }
}
