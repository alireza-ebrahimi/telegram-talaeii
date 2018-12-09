package org.telegram.customization.p156a;

import android.support.v7.widget.RecyclerView.C0926a;
import android.support.v7.widget.RecyclerView.C0955v;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Model.FilterItem;
import org.telegram.customization.p152f.C2620a;
import org.telegram.customization.p161d.C2677a;

/* renamed from: org.telegram.customization.a.d */
public class C2640d extends C0926a<C2639a> {
    /* renamed from: a */
    ArrayList<FilterItem> f8825a = new ArrayList();
    /* renamed from: b */
    C2620a f8826b;
    /* renamed from: c */
    C2677a f8827c;

    /* renamed from: org.telegram.customization.a.d$a */
    class C2639a extends C0955v {
        /* renamed from: a */
        View f8821a;
        /* renamed from: b */
        TextView f8822b;
        /* renamed from: c */
        ImageView f8823c;
        /* renamed from: d */
        final /* synthetic */ C2640d f8824d;

        public C2639a(C2640d c2640d, View view) {
            this.f8824d = c2640d;
            super(view);
            this.f8821a = view.findViewById(R.id.root);
            this.f8822b = (TextView) view.findViewById(R.id.tv_name);
            this.f8823c = (ImageView) view.findViewById(R.id.iv_select_status);
        }
    }

    public C2640d(C2677a c2677a, ArrayList<FilterItem> arrayList, C2620a c2620a) {
        this.f8825a = arrayList;
        this.f8826b = c2620a;
        this.f8827c = c2677a;
    }

    /* renamed from: a */
    public ArrayList<FilterItem> m12485a() {
        return this.f8825a;
    }

    /* renamed from: a */
    public C2639a m12486a(ViewGroup viewGroup, int i) {
        return new C2639a(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_filter, viewGroup, false));
    }

    /* renamed from: a */
    public void m12487a(C2639a c2639a, int i) {
        final FilterItem filterItem = (FilterItem) m12485a().get(i);
        c2639a.f8822b.setText(filterItem.getName());
        if (filterItem.isSelected()) {
            c2639a.f8823c.setVisibility(0);
        } else {
            c2639a.f8823c.setVisibility(8);
        }
        c2639a.f8821a.setOnClickListener(new OnClickListener(this) {
            /* renamed from: b */
            final /* synthetic */ C2640d f8820b;

            public void onClick(View view) {
                this.f8820b.f8826b.mo3453a(filterItem);
                this.f8820b.f8827c.dismiss();
            }
        });
    }

    public int getItemCount() {
        return m12485a() == null ? 0 : m12485a().size();
    }

    public /* synthetic */ void onBindViewHolder(C0955v c0955v, int i) {
        m12487a((C2639a) c0955v, i);
    }

    public /* synthetic */ C0955v onCreateViewHolder(ViewGroup viewGroup, int i) {
        return m12486a(viewGroup, i);
    }
}
