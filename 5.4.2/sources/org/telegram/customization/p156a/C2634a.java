package org.telegram.customization.p156a;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.C0926a;
import android.support.v7.widget.RecyclerView.C0955v;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.util.C2868b;

/* renamed from: org.telegram.customization.a.a */
public class C2634a extends C0926a<C2633a> {
    /* renamed from: a */
    ArrayList<Category> f8804a = new ArrayList();
    /* renamed from: b */
    OnItemClickListener f8805b;
    /* renamed from: c */
    private RecyclerView f8806c;

    /* renamed from: org.telegram.customization.a.a$a */
    class C2633a extends C0955v {
        /* renamed from: a */
        View f8798a;
        /* renamed from: b */
        ImageView f8799b;
        /* renamed from: c */
        ImageView f8800c;
        /* renamed from: d */
        TextView f8801d;
        /* renamed from: e */
        TextView f8802e;
        /* renamed from: f */
        final /* synthetic */ C2634a f8803f;

        public C2633a(final C2634a c2634a, View view) {
            this.f8803f = c2634a;
            super(view);
            this.f8798a = view.findViewById(R.id.root);
            this.f8799b = (ImageView) view.findViewById(R.id.iv_channel_item);
            this.f8800c = (ImageView) view.findViewById(R.id.iv_selected);
            this.f8801d = (TextView) view.findViewById(R.id.tv_channel_name);
            this.f8802e = (TextView) view.findViewById(R.id.tv_channel_desc);
            view.setOnClickListener(new OnClickListener(this) {
                /* renamed from: b */
                final /* synthetic */ C2633a f8797b;

                public void onClick(View view) {
                    try {
                        this.f8797b.f8803f.f8805b.onItemClick(null, view, this.f8797b.f8803f.f8806c.m273f(view), System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    public C2634a(ArrayList<Category> arrayList, OnItemClickListener onItemClickListener, RecyclerView recyclerView) {
        this.f8804a = arrayList;
        this.f8805b = onItemClickListener;
        this.f8806c = recyclerView;
    }

    /* renamed from: a */
    public ArrayList<Category> m12478a() {
        return this.f8804a;
    }

    /* renamed from: a */
    public C2633a m12479a(ViewGroup viewGroup, int i) {
        return new C2633a(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ads_channel, viewGroup, false));
    }

    /* renamed from: a */
    public void m12480a(C2633a c2633a, int i) {
        Category category = (Category) m12478a().get(i);
        c2633a.f8801d.setText(category.getTitle());
        c2633a.f8802e.setText(category.getDesc());
        C2868b.m13329a(c2633a.f8799b, category.getImage());
        if (category.getStatus() == 1) {
            c2633a.f8800c.setImageResource(R.drawable.check_circle_green);
        } else {
            c2633a.f8800c.setImageResource(R.drawable.check_circle_gray);
        }
    }

    public int getItemCount() {
        return m12478a() == null ? 0 : m12478a().size();
    }

    public /* synthetic */ void onBindViewHolder(C0955v c0955v, int i) {
        m12480a((C2633a) c0955v, i);
    }

    public /* synthetic */ C0955v onCreateViewHolder(ViewGroup viewGroup, int i) {
        return m12479a(viewGroup, i);
    }
}
