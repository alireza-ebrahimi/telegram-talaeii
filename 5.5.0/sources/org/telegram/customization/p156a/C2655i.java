package org.telegram.customization.p156a;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.C0926a;
import android.support.v7.widget.RecyclerView.C0955v;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Model.RequestLog;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

/* renamed from: org.telegram.customization.a.i */
public class C2655i extends C0926a<C2654a> {
    /* renamed from: a */
    ArrayList<RequestLog> f8866a = new ArrayList();
    /* renamed from: b */
    OnItemClickListener f8867b;
    /* renamed from: c */
    private RecyclerView f8868c;

    /* renamed from: org.telegram.customization.a.i$a */
    class C2654a extends C0955v {
        /* renamed from: a */
        View f8861a;
        /* renamed from: b */
        TextView f8862b;
        /* renamed from: c */
        TextView f8863c;
        /* renamed from: d */
        TextView f8864d;
        /* renamed from: e */
        final /* synthetic */ C2655i f8865e;

        public C2654a(final C2655i c2655i, View view) {
            this.f8865e = c2655i;
            super(view);
            this.f8861a = view.findViewById(R.id.root);
            this.f8862b = (TextView) view.findViewById(R.id.tv_host_name);
            this.f8863c = (TextView) view.findViewById(R.id.tv_service_name);
            this.f8864d = (TextView) view.findViewById(R.id.tv_status_code);
            view.setOnClickListener(new OnClickListener(this) {
                /* renamed from: b */
                final /* synthetic */ C2654a f8860b;

                public void onClick(View view) {
                    try {
                        this.f8860b.f8865e.f8867b.onItemClick(null, view, this.f8860b.f8865e.f8868c.m273f(view), System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    public C2655i(ArrayList<RequestLog> arrayList, OnItemClickListener onItemClickListener, RecyclerView recyclerView) {
        this.f8866a = arrayList;
        this.f8867b = onItemClickListener;
        this.f8868c = recyclerView;
    }

    /* renamed from: a */
    public ArrayList<RequestLog> m12500a() {
        return this.f8866a;
    }

    /* renamed from: a */
    public C2654a m12501a(ViewGroup viewGroup, int i) {
        return new C2654a(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_log, viewGroup, false));
    }

    /* renamed from: a */
    public void m12502a(ArrayList<RequestLog> arrayList) {
        this.f8866a = arrayList;
        notifyDataSetChanged();
    }

    /* renamed from: a */
    public void m12503a(C2654a c2654a, int i) {
        RequestLog requestLog = (RequestLog) m12500a().get(i);
        c2654a.f8862b.setText(requestLog.getHost());
        c2654a.f8863c.setText(requestLog.getServiceName());
        c2654a.f8864d.setText(String.valueOf(requestLog.getStatusCode()));
        if (requestLog.getStatusCode() == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
            c2654a.f8864d.setTextColor(ApplicationLoader.applicationContext.getResources().getColor(R.color.green));
        } else {
            c2654a.f8864d.setTextColor(ApplicationLoader.applicationContext.getResources().getColor(R.color.red));
        }
    }

    public int getItemCount() {
        return m12500a() == null ? 0 : m12500a().size();
    }

    public /* synthetic */ void onBindViewHolder(C0955v c0955v, int i) {
        m12503a((C2654a) c0955v, i);
    }

    public /* synthetic */ C0955v onCreateViewHolder(ViewGroup viewGroup, int i) {
        return m12501a(viewGroup, i);
    }
}
