package android.support.v4.widget;

import android.database.Cursor;
import android.widget.Filter;
import android.widget.Filter.FilterResults;

/* renamed from: android.support.v4.widget.h */
class C0695h extends Filter {
    /* renamed from: a */
    C0693a f1560a;

    /* renamed from: android.support.v4.widget.h$a */
    interface C0693a {
        /* renamed from: a */
        Cursor mo570a();

        /* renamed from: a */
        Cursor mo571a(CharSequence charSequence);

        /* renamed from: a */
        void mo572a(Cursor cursor);

        /* renamed from: c */
        CharSequence mo573c(Cursor cursor);
    }

    C0695h(C0693a c0693a) {
        this.f1560a = c0693a;
    }

    public CharSequence convertResultToString(Object obj) {
        return this.f1560a.mo573c((Cursor) obj);
    }

    protected FilterResults performFiltering(CharSequence charSequence) {
        Cursor a = this.f1560a.mo571a(charSequence);
        FilterResults filterResults = new FilterResults();
        if (a != null) {
            filterResults.count = a.getCount();
            filterResults.values = a;
        } else {
            filterResults.count = 0;
            filterResults.values = null;
        }
        return filterResults;
    }

    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        Cursor a = this.f1560a.mo570a();
        if (filterResults.values != null && filterResults.values != a) {
            this.f1560a.mo572a((Cursor) filterResults.values);
        }
    }
}
