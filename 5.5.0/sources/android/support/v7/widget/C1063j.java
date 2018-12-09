package android.support.v7.widget;

import android.content.Context;
import android.support.v7.p027c.p028a.C0825b;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.TextView;

/* renamed from: android.support.v7.widget.j */
public class C1063j extends CheckedTextView {
    /* renamed from: a */
    private static final int[] f3156a = new int[]{16843016};
    /* renamed from: b */
    private C1086w f3157b;

    public C1063j(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843720);
    }

    public C1063j(Context context, AttributeSet attributeSet, int i) {
        super(bh.m5649a(context), attributeSet, i);
        this.f3157b = C1086w.m5916a((TextView) this);
        this.f3157b.mo1016a(attributeSet, i);
        this.f3157b.mo1015a();
        bk a = bk.m5654a(getContext(), attributeSet, f3156a, i, 0);
        setCheckMarkDrawable(a.m5657a(0));
        a.m5658a();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.f3157b != null) {
            this.f3157b.mo1015a();
        }
    }

    public void setCheckMarkDrawable(int i) {
        setCheckMarkDrawable(C0825b.m3939b(getContext(), i));
    }

    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        if (this.f3157b != null) {
            this.f3157b.m5918a(context, i);
        }
    }
}
