package android.support.v4.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: android.support.v4.widget.w */
public abstract class C0728w extends C0694g {
    /* renamed from: j */
    private int f1625j;
    /* renamed from: k */
    private int f1626k;
    /* renamed from: l */
    private LayoutInflater f1627l;

    @Deprecated
    public C0728w(Context context, int i, Cursor cursor, boolean z) {
        super(context, cursor, z);
        this.f1626k = i;
        this.f1625j = i;
        this.f1627l = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    /* renamed from: a */
    public View mo593a(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.f1627l.inflate(this.f1625j, viewGroup, false);
    }

    /* renamed from: b */
    public View mo594b(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.f1627l.inflate(this.f1626k, viewGroup, false);
    }
}
