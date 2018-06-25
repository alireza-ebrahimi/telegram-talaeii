package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import com.mohamadamin.persianmaterialdatetimepicker.date.C2044e.C2041b;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2018b;
import java.util.HashMap;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.d */
public abstract class C2042d extends BaseAdapter implements C2041b {
    /* renamed from: b */
    protected static int f6013b = 7;
    /* renamed from: a */
    protected final C2031a f6014a;
    /* renamed from: c */
    private final Context f6015c;
    /* renamed from: d */
    private C2040a f6016d;

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.d$a */
    public static class C2040a {
        /* renamed from: a */
        int f6009a;
        /* renamed from: b */
        int f6010b;
        /* renamed from: c */
        int f6011c;
        /* renamed from: d */
        private C2018b f6012d;

        public C2040a() {
            m9168a(System.currentTimeMillis());
        }

        public C2040a(int i, int i2, int i3) {
            m9169a(i, i2, i3);
        }

        public C2040a(long j) {
            m9168a(j);
        }

        public C2040a(C2018b c2018b) {
            this.f6009a = c2018b.m9095b();
            this.f6010b = c2018b.m9096c();
            this.f6011c = c2018b.m9098e();
        }

        /* renamed from: a */
        private void m9168a(long j) {
            if (this.f6012d == null) {
                this.f6012d = new C2018b();
            }
            this.f6012d.setTimeInMillis(j);
            this.f6010b = this.f6012d.m9096c();
            this.f6009a = this.f6012d.m9095b();
            this.f6011c = this.f6012d.m9098e();
        }

        /* renamed from: a */
        public void m9169a(int i, int i2, int i3) {
            this.f6009a = i;
            this.f6010b = i2;
            this.f6011c = i3;
        }

        /* renamed from: a */
        public void m9170a(C2040a c2040a) {
            this.f6009a = c2040a.f6009a;
            this.f6010b = c2040a.f6010b;
            this.f6011c = c2040a.f6011c;
        }
    }

    public C2042d(Context context, C2031a c2031a) {
        this.f6015c = context;
        this.f6014a = c2031a;
        m9174a();
        m9175a(this.f6014a.mo3061a());
    }

    /* renamed from: a */
    private boolean m9172a(int i, int i2) {
        return this.f6016d.f6009a == i && this.f6016d.f6010b == i2;
    }

    /* renamed from: a */
    public abstract C2044e mo3085a(Context context);

    /* renamed from: a */
    protected void m9174a() {
        this.f6016d = new C2040a(System.currentTimeMillis());
    }

    /* renamed from: a */
    public void m9175a(C2040a c2040a) {
        this.f6016d = c2040a;
        notifyDataSetChanged();
    }

    /* renamed from: a */
    public void mo3075a(C2044e c2044e, C2040a c2040a) {
        if (c2040a != null) {
            m9177b(c2040a);
        }
    }

    /* renamed from: b */
    protected void m9177b(C2040a c2040a) {
        this.f6014a.mo3073j();
        this.f6014a.mo3063a(c2040a.f6009a, c2040a.f6010b, c2040a.f6011c);
        m9175a(c2040a);
    }

    public int getCount() {
        return ((this.f6014a.mo3070g() - this.f6014a.mo3069f()) + 1) * 12;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    @SuppressLint({"NewApi"})
    public View getView(int i, View view, ViewGroup viewGroup) {
        int i2 = -1;
        HashMap hashMap = null;
        if (view != null) {
            view = (C2044e) view;
            hashMap = (HashMap) view.getTag();
        } else {
            view = mo3085a(this.f6015c);
            view.setLayoutParams(new LayoutParams(-1, -1));
            view.setClickable(true);
            view.setOnDayClickListener(this);
        }
        if (hashMap == null) {
            hashMap = new HashMap();
        }
        hashMap.clear();
        int i3 = i % 12;
        int f = (i / 12) + this.f6014a.mo3069f();
        if (m9172a(f, i3)) {
            i2 = this.f6016d.f6011c;
        }
        view.m9201b();
        hashMap.put("selected_day", Integer.valueOf(i2));
        hashMap.put("year", Integer.valueOf(f));
        hashMap.put("month", Integer.valueOf(i3));
        hashMap.put("week_start", Integer.valueOf(this.f6014a.mo3068e()));
        view.setMonthParams(hashMap);
        view.invalidate();
        return view;
    }

    public boolean hasStableIds() {
        return true;
    }
}
