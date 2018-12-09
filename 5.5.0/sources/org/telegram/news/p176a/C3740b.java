package org.telegram.news.p176a;

import android.app.Activity;
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
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import org.ir.talaeii.R;
import org.telegram.customization.util.C2868b;
import org.telegram.news.p175c.C3749b;
import org.telegram.news.p177b.C3744b;
import utils.view.CircularProgress;

/* renamed from: org.telegram.news.a.b */
public class C3740b extends C0926a<C3736a> implements Observer {
    /* renamed from: a */
    boolean f9949a = true;
    /* renamed from: b */
    OnItemClickListener f9950b;
    /* renamed from: c */
    RecyclerView f9951c;
    /* renamed from: d */
    ArrayList<C3744b> f9952d = new ArrayList();
    /* renamed from: e */
    int f9953e;
    /* renamed from: f */
    private Activity f9954f;
    /* renamed from: g */
    private LayoutInflater f9955g = null;

    /* renamed from: org.telegram.news.a.b$a */
    class C3736a extends C0955v {
        /* renamed from: a */
        View f9932a;
        /* renamed from: b */
        final /* synthetic */ C3740b f9933b;

        public C3736a(final C3740b c3740b, View view) {
            this.f9933b = c3740b;
            super(view);
            this.f9932a = view;
            this.f9932a.setOnClickListener(new OnClickListener(this) {
                /* renamed from: b */
                final /* synthetic */ C3736a f9931b;

                public void onClick(View view) {
                    try {
                        this.f9931b.f9933b.f9950b.onItemClick(null, view, this.f9931b.f9933b.f9951c.m273f(view), System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    /* renamed from: org.telegram.news.a.b$b */
    class C3737b extends C3736a {
        /* renamed from: c */
        TextView f9934c;
        /* renamed from: d */
        TextView f9935d;
        /* renamed from: e */
        TextView f9936e;
        /* renamed from: f */
        ImageView f9937f;
        /* renamed from: g */
        ImageView f9938g;
        /* renamed from: h */
        final /* synthetic */ C3740b f9939h;

        public C3737b(C3740b c3740b, View view) {
            this.f9939h = c3740b;
            super(c3740b, view);
            this.f9934c = (TextView) view.findViewById(R.id.txt_title);
            this.f9935d = (TextView) view.findViewById(R.id.txt_time);
            this.f9936e = (TextView) view.findViewById(R.id.txt_source);
            this.f9937f = (ImageView) view.findViewById(R.id.iv_agency);
            this.f9938g = (ImageView) view.findViewById(R.id.iv_news_image);
        }
    }

    /* renamed from: org.telegram.news.a.b$c */
    class C3738c extends C3736a {
        /* renamed from: c */
        CircularProgress f9940c;
        /* renamed from: d */
        final /* synthetic */ C3740b f9941d;

        public C3738c(C3740b c3740b, View view) {
            this.f9941d = c3740b;
            super(c3740b, view);
            this.f9940c = (CircularProgress) view.findViewById(R.id.loading);
        }
    }

    /* renamed from: org.telegram.news.a.b$d */
    class C3739d extends C3736a {
        /* renamed from: c */
        TextView f9942c;
        /* renamed from: d */
        TextView f9943d;
        /* renamed from: e */
        TextView f9944e;
        /* renamed from: f */
        ImageView f9945f;
        /* renamed from: g */
        ImageView f9946g;
        /* renamed from: h */
        View f9947h;
        /* renamed from: i */
        final /* synthetic */ C3740b f9948i;

        public C3739d(C3740b c3740b, View view) {
            this.f9948i = c3740b;
            super(c3740b, view);
            this.f9942c = (TextView) view.findViewById(R.id.txt_title);
            this.f9943d = (TextView) view.findViewById(R.id.txt_time);
            this.f9944e = (TextView) view.findViewById(R.id.txt_source);
            this.f9945f = (ImageView) view.findViewById(R.id.iv_agency);
            this.f9946g = (ImageView) view.findViewById(R.id.iv_news_image);
            this.f9947h = view.findViewById(R.id.iv_news_image2);
        }
    }

    public C3740b(Activity activity, int i, RecyclerView recyclerView, OnItemClickListener onItemClickListener) {
        this.f9954f = activity;
        this.f9953e = i;
        this.f9950b = onItemClickListener;
        this.f9951c = recyclerView;
        this.f9955g = (LayoutInflater) this.f9954f.getSystemService("layout_inflater");
    }

    /* renamed from: a */
    public C3736a m13745a(ViewGroup viewGroup, int i) {
        this.f9955g = LayoutInflater.from(viewGroup.getContext());
        switch (i) {
            case 0:
                return new C3739d(this, this.f9955g.inflate(R.layout.small_news_item_new, viewGroup, false));
            case 1:
                return new C3737b(this, this.f9955g.inflate(R.layout.larg_news_item, viewGroup, false));
            case 3:
                return new C3738c(this, this.f9955g.inflate(R.layout.loading, viewGroup, false));
            default:
                return new C3739d(this, this.f9955g.inflate(R.layout.small_news_item_new, viewGroup, false));
        }
    }

    /* renamed from: a */
    public C3744b m13746a(int i) {
        try {
            return (C3744b) m13749b().get(i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    public void m13747a(C3736a c3736a, int i) {
        C3744b a;
        switch (getItemViewType(i)) {
            case 0:
                C3739d c3739d = (C3739d) c3736a;
                a = m13746a(i);
                c3739d.f9942c.setText(a.m13789i() + TtmlNode.ANONYMOUS_REGION_ID);
                C2868b.m13329a(c3739d.f9946g, a.m13800q());
                c3739d.f9944e.setText(a.m13793k() + TtmlNode.ANONYMOUS_REGION_ID);
                c3739d.f9943d.setText(a.m13806w() + TtmlNode.ANONYMOUS_REGION_ID);
                C2868b.m13329a(c3739d.f9945f, a.m13785g());
                return;
            case 1:
                C3737b c3737b = (C3737b) c3736a;
                a = m13746a(i);
                c3737b.f9934c.setText(a.m13789i());
                c3737b.f9936e.setText(a.m13793k());
                c3737b.f9935d.setText(a.m13806w());
                C2868b.m13329a(c3737b.f9938g, a.m13800q());
                C2868b.m13329a(c3737b.f9937f, a.m13785g());
                return;
            default:
                return;
        }
    }

    /* renamed from: a */
    public boolean m13748a() {
        return this.f9949a;
    }

    /* renamed from: b */
    public List<C3744b> m13749b() {
        if (this.f9952d == null) {
            this.f9952d = new ArrayList();
        }
        this.f9952d = C3749b.m13818a(this.f9953e);
        return this.f9952d;
    }

    public int getItemCount() {
        int i = 0;
        if (m13749b().size() == 0) {
            return 0;
        }
        int size = m13749b().size();
        if (m13748a()) {
            i = 1;
        }
        return i + size;
    }

    public int getItemViewType(int i) {
        if (i == m13749b().size()) {
            return 3;
        }
        switch (i % 5) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return 0;
            case 6:
                return 1;
            default:
                return 0;
        }
    }

    public /* synthetic */ void onBindViewHolder(C0955v c0955v, int i) {
        m13747a((C3736a) c0955v, i);
    }

    public /* synthetic */ C0955v onCreateViewHolder(ViewGroup viewGroup, int i) {
        return m13745a(viewGroup, i);
    }

    public void update(Observable observable, Object obj) {
        notifyDataSetChanged();
    }
}
