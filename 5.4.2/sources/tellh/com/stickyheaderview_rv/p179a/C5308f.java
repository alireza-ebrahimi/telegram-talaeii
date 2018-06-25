package tellh.com.stickyheaderview_rv.p179a;

import android.support.v7.widget.RecyclerView.C0955v;
import android.view.View;

/* renamed from: tellh.com.stickyheaderview_rv.a.f */
public abstract class C5308f<T extends C5302c, VH extends C0955v> implements C5305b<T, VH> {

    /* renamed from: tellh.com.stickyheaderview_rv.a.f$a */
    public static class C5307a extends C0955v {
        public C5307a(View view) {
            super(view);
        }

        protected <T extends View> T findViewById(int i) {
            return this.itemView.findViewById(i);
        }
    }

    public abstract void bindView(C5306e c5306e, VH vh, int i, T t);

    public abstract VH provideViewHolder(View view);
}
