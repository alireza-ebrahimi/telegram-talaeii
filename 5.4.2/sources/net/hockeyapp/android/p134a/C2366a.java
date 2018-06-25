package net.hockeyapp.android.p134a;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import net.hockeyapp.android.p135c.C2373d;
import net.hockeyapp.android.views.C2425b;

/* renamed from: net.hockeyapp.android.a.a */
public class C2366a extends BaseAdapter {
    /* renamed from: a */
    private Context f7953a;
    /* renamed from: b */
    private ArrayList<C2373d> f7954b;

    public C2366a(Context context, ArrayList<C2373d> arrayList) {
        this.f7953a = context;
        this.f7954b = arrayList;
    }

    /* renamed from: a */
    public void m11714a() {
        if (this.f7954b != null) {
            this.f7954b.clear();
        }
    }

    /* renamed from: a */
    public void m11715a(C2373d c2373d) {
        if (c2373d != null && this.f7954b != null) {
            this.f7954b.add(c2373d);
        }
    }

    public int getCount() {
        return this.f7954b.size();
    }

    public Object getItem(int i) {
        return this.f7954b.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        C2373d c2373d = (C2373d) this.f7954b.get(i);
        if (view == null) {
            view = new C2425b(this.f7953a, null);
        } else {
            C2425b c2425b = (C2425b) view;
        }
        if (c2373d != null) {
            view.setFeedbackMessage(c2373d);
        }
        view.setIndex(i);
        return view;
    }
}
