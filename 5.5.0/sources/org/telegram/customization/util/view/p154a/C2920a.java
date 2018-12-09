package org.telegram.customization.util.view.p154a;

import android.content.Context;
import android.support.v4.view.C0659t;
import android.support.v7.widget.RecyclerView.C0926a;
import android.support.v7.widget.RecyclerView.C0955v;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Model.DialogTab;
import org.telegram.customization.util.view.p154a.p155a.C2517a;
import org.telegram.customization.util.view.p154a.p155a.C2518b;
import org.telegram.customization.util.view.p154a.p155a.C2519c;
import org.telegram.customization.util.view.p154a.p170b.C2917b;
import org.telegram.customization.util.view.p154a.p170b.C2919a;

/* renamed from: org.telegram.customization.util.view.a.a */
public class C2920a extends C0926a<C2918a> implements C2919a {
    /* renamed from: a */
    private List<DialogTab> f9608a;
    /* renamed from: b */
    private Context f9609b;
    /* renamed from: c */
    private C2519c f9610c;
    /* renamed from: d */
    private C2518b f9611d;
    /* renamed from: e */
    private C2517a f9612e;

    /* renamed from: org.telegram.customization.util.view.a.a$a */
    public static class C2918a extends C0955v implements C2917b {
        /* renamed from: a */
        public final TextView f9606a;
        /* renamed from: b */
        public final ImageView f9607b;

        public C2918a(View view) {
            super(view);
            this.f9606a = (TextView) view.findViewById(R.id.text_view_customer_name);
            this.f9607b = (ImageView) view.findViewById(R.id.handle);
        }

        /* renamed from: a */
        public void mo3500a() {
            this.itemView.setBackgroundColor(-3355444);
        }

        /* renamed from: b */
        public void mo3501b() {
            this.itemView.setBackgroundColor(0);
        }
    }

    public C2920a(List<DialogTab> list, Context context, C2519c c2519c, C2518b c2518b, C2517a c2517a) {
        this.f9608a = list;
        this.f9609b = context;
        this.f9610c = c2519c;
        this.f9612e = c2517a;
        this.f9611d = c2518b;
    }

    /* renamed from: a */
    public C2918a m13476a(ViewGroup viewGroup, int i) {
        return new C2918a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_dialog_tab_list, viewGroup, false));
    }

    /* renamed from: a */
    public void mo3502a(int i) {
    }

    /* renamed from: a */
    public void mo3503a(int i, int i2) {
        Collections.swap(this.f9608a, i, i2);
        this.f9612e.mo3425a(this.f9608a);
        notifyItemMoved(i, i2);
    }

    /* renamed from: a */
    public void m13479a(final C2918a c2918a, int i) {
        c2918a.f9606a.setText(((DialogTab) this.f9608a.get(i)).getShowName());
        c2918a.f9607b.setOnTouchListener(new OnTouchListener(this) {
            /* renamed from: b */
            final /* synthetic */ C2920a f9605b;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("LEE", "Actttion:" + C0659t.m3205a(motionEvent));
                if (C0659t.m3205a(motionEvent) == 0) {
                    this.f9605b.f9610c.mo3424a(c2918a);
                } else if (C0659t.m3205a(motionEvent) == 1) {
                    this.f9605b.f9611d.mo3426b(c2918a);
                }
                return false;
            }
        });
    }

    public int getItemCount() {
        return this.f9608a.size();
    }

    public /* synthetic */ void onBindViewHolder(C0955v c0955v, int i) {
        m13479a((C2918a) c0955v, i);
    }

    public /* synthetic */ C0955v onCreateViewHolder(ViewGroup viewGroup, int i) {
        return m13476a(viewGroup, i);
    }
}
