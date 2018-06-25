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
import org.telegram.customization.Model.HotgramTheme;
import org.telegram.customization.util.C2868b;

/* renamed from: org.telegram.customization.a.j */
public class C2658j extends C0926a<C2657a> {
    /* renamed from: a */
    ArrayList<HotgramTheme> f8876a = new ArrayList();
    /* renamed from: b */
    OnItemClickListener f8877b;
    /* renamed from: c */
    private RecyclerView f8878c;

    /* renamed from: org.telegram.customization.a.j$a */
    class C2657a extends C0955v {
        /* renamed from: a */
        View f8871a;
        /* renamed from: b */
        ImageView f8872b;
        /* renamed from: c */
        ImageView f8873c;
        /* renamed from: d */
        TextView f8874d;
        /* renamed from: e */
        final /* synthetic */ C2658j f8875e;

        public C2657a(final C2658j c2658j, View view) {
            this.f8875e = c2658j;
            super(view);
            this.f8871a = view.findViewById(R.id.root);
            this.f8872b = (ImageView) view.findViewById(R.id.theme_image);
            this.f8873c = (ImageView) view.findViewById(R.id.iv_check);
            this.f8874d = (TextView) view.findViewById(R.id.theme_name);
            view.setOnClickListener(new OnClickListener(this) {
                /* renamed from: b */
                final /* synthetic */ C2657a f8870b;

                public void onClick(View view) {
                    try {
                        this.f8870b.f8875e.f8877b.onItemClick(null, view, this.f8870b.f8875e.f8878c.m273f(view), System.currentTimeMillis());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public C2658j(ArrayList<HotgramTheme> arrayList, OnItemClickListener onItemClickListener, RecyclerView recyclerView) {
        this.f8876a = arrayList;
        this.f8877b = onItemClickListener;
        this.f8878c = recyclerView;
    }

    /* renamed from: a */
    public ArrayList<HotgramTheme> m12505a() {
        return this.f8876a;
    }

    /* renamed from: a */
    public C2657a m12506a(ViewGroup viewGroup, int i) {
        return new C2657a(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_theme, viewGroup, false));
    }

    /* renamed from: a */
    public void m12507a(C2657a c2657a, int i) {
        HotgramTheme hotgramTheme = (HotgramTheme) m12505a().get(i);
        c2657a.f8874d.setText(hotgramTheme.getName());
        C2868b.m13329a(c2657a.f8872b, hotgramTheme.getPreviewUrl());
        if (hotgramTheme.isSelected()) {
            c2657a.f8873c.setImageResource(R.drawable.check_circle_green);
        } else {
            c2657a.f8873c.setImageResource(R.drawable.check_circle_gray);
        }
    }

    public int getItemCount() {
        return m12505a() == null ? 0 : m12505a().size();
    }

    public /* synthetic */ void onBindViewHolder(C0955v c0955v, int i) {
        m12507a((C2657a) c0955v, i);
    }

    public /* synthetic */ C0955v onCreateViewHolder(ViewGroup viewGroup, int i) {
        return m12506a(viewGroup, i);
    }
}
