package android.support.v4.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.support.v4.widget.C0695h.C0693a;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;

/* renamed from: android.support.v4.widget.g */
public abstract class C0694g extends BaseAdapter implements C0693a, Filterable {
    /* renamed from: a */
    protected boolean f1551a;
    /* renamed from: b */
    protected boolean f1552b;
    /* renamed from: c */
    protected Cursor f1553c;
    /* renamed from: d */
    protected Context f1554d;
    /* renamed from: e */
    protected int f1555e;
    /* renamed from: f */
    protected C0691a f1556f;
    /* renamed from: g */
    protected DataSetObserver f1557g;
    /* renamed from: h */
    protected C0695h f1558h;
    /* renamed from: i */
    protected FilterQueryProvider f1559i;

    /* renamed from: android.support.v4.widget.g$a */
    private class C0691a extends ContentObserver {
        /* renamed from: a */
        final /* synthetic */ C0694g f1549a;

        C0691a(C0694g c0694g) {
            this.f1549a = c0694g;
            super(new Handler());
        }

        public boolean deliverSelfNotifications() {
            return true;
        }

        public void onChange(boolean z) {
            this.f1549a.m3389b();
        }
    }

    /* renamed from: android.support.v4.widget.g$b */
    private class C0692b extends DataSetObserver {
        /* renamed from: a */
        final /* synthetic */ C0694g f1550a;

        C0692b(C0694g c0694g) {
            this.f1550a = c0694g;
        }

        public void onChanged() {
            this.f1550a.f1551a = true;
            this.f1550a.notifyDataSetChanged();
        }

        public void onInvalidated() {
            this.f1550a.f1551a = false;
            this.f1550a.notifyDataSetInvalidated();
        }
    }

    public C0694g(Context context, Cursor cursor, boolean z) {
        m3384a(context, cursor, z ? 1 : 2);
    }

    /* renamed from: a */
    public Cursor mo570a() {
        return this.f1553c;
    }

    /* renamed from: a */
    public Cursor mo571a(CharSequence charSequence) {
        return this.f1559i != null ? this.f1559i.runQuery(charSequence) : this.f1553c;
    }

    /* renamed from: a */
    public abstract View mo593a(Context context, Cursor cursor, ViewGroup viewGroup);

    /* renamed from: a */
    void m3384a(Context context, Cursor cursor, int i) {
        boolean z = true;
        if ((i & 1) == 1) {
            i |= 2;
            this.f1552b = true;
        } else {
            this.f1552b = false;
        }
        if (cursor == null) {
            z = false;
        }
        this.f1553c = cursor;
        this.f1551a = z;
        this.f1554d = context;
        this.f1555e = z ? cursor.getColumnIndexOrThrow("_id") : -1;
        if ((i & 2) == 2) {
            this.f1556f = new C0691a(this);
            this.f1557g = new C0692b(this);
        } else {
            this.f1556f = null;
            this.f1557g = null;
        }
        if (z) {
            if (this.f1556f != null) {
                cursor.registerContentObserver(this.f1556f);
            }
            if (this.f1557g != null) {
                cursor.registerDataSetObserver(this.f1557g);
            }
        }
    }

    /* renamed from: a */
    public void mo572a(Cursor cursor) {
        Cursor b = m3387b(cursor);
        if (b != null) {
            b.close();
        }
    }

    /* renamed from: a */
    public abstract void mo960a(View view, Context context, Cursor cursor);

    /* renamed from: b */
    public Cursor m3387b(Cursor cursor) {
        if (cursor == this.f1553c) {
            return null;
        }
        Cursor cursor2 = this.f1553c;
        if (cursor2 != null) {
            if (this.f1556f != null) {
                cursor2.unregisterContentObserver(this.f1556f);
            }
            if (this.f1557g != null) {
                cursor2.unregisterDataSetObserver(this.f1557g);
            }
        }
        this.f1553c = cursor;
        if (cursor != null) {
            if (this.f1556f != null) {
                cursor.registerContentObserver(this.f1556f);
            }
            if (this.f1557g != null) {
                cursor.registerDataSetObserver(this.f1557g);
            }
            this.f1555e = cursor.getColumnIndexOrThrow("_id");
            this.f1551a = true;
            notifyDataSetChanged();
            return cursor2;
        }
        this.f1555e = -1;
        this.f1551a = false;
        notifyDataSetInvalidated();
        return cursor2;
    }

    /* renamed from: b */
    public View mo594b(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mo593a(context, cursor, viewGroup);
    }

    /* renamed from: b */
    protected void m3389b() {
        if (this.f1552b && this.f1553c != null && !this.f1553c.isClosed()) {
            this.f1551a = this.f1553c.requery();
        }
    }

    /* renamed from: c */
    public CharSequence mo573c(Cursor cursor) {
        return cursor == null ? TtmlNode.ANONYMOUS_REGION_ID : cursor.toString();
    }

    public int getCount() {
        return (!this.f1551a || this.f1553c == null) ? 0 : this.f1553c.getCount();
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        if (!this.f1551a) {
            return null;
        }
        this.f1553c.moveToPosition(i);
        if (view == null) {
            view = mo594b(this.f1554d, this.f1553c, viewGroup);
        }
        mo960a(view, this.f1554d, this.f1553c);
        return view;
    }

    public Filter getFilter() {
        if (this.f1558h == null) {
            this.f1558h = new C0695h(this);
        }
        return this.f1558h;
    }

    public Object getItem(int i) {
        if (!this.f1551a || this.f1553c == null) {
            return null;
        }
        this.f1553c.moveToPosition(i);
        return this.f1553c;
    }

    public long getItemId(int i) {
        return (this.f1551a && this.f1553c != null && this.f1553c.moveToPosition(i)) ? this.f1553c.getLong(this.f1555e) : 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (!this.f1551a) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        } else if (this.f1553c.moveToPosition(i)) {
            if (view == null) {
                view = mo593a(this.f1554d, this.f1553c, viewGroup);
            }
            mo960a(view, this.f1554d, this.f1553c);
            return view;
        } else {
            throw new IllegalStateException("couldn't move cursor to position " + i);
        }
    }

    public boolean hasStableIds() {
        return true;
    }
}
