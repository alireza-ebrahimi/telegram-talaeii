package org.telegram.customization.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import org.ir.talaeii.R;
import org.telegram.customization.p156a.C2655i;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import utils.C5313a;

/* renamed from: org.telegram.customization.Activities.c */
public class C2539c extends FrameLayout implements OnClickListener, OnItemClickListener, NotificationCenterDelegate {
    /* renamed from: a */
    C2655i f8489a;
    /* renamed from: b */
    private View f8490b;
    /* renamed from: c */
    private Activity f8491c;
    /* renamed from: d */
    private RecyclerView f8492d;

    public C2539c(Activity activity) {
        super(activity);
        m12305a(activity);
    }

    /* renamed from: a */
    private void m12303a() {
        this.f8492d = (RecyclerView) this.f8490b.findViewById(R.id.recyclerView);
        this.f8492d.setLayoutManager(new LinearLayoutManager(this.f8491c, 1, false));
        this.f8489a = new C2655i(C5313a.a(), this, this.f8492d);
        this.f8492d.setAdapter(this.f8489a);
    }

    /* renamed from: a */
    public View m12304a(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateLog);
        View linearLayout = new LinearLayout(this.f8491c);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-1, -1));
        linearLayout.setBackgroundColor(-1);
        this.f8490b = layoutInflater.inflate(R.layout.sls_fragment_debug, linearLayout, false);
        linearLayout.addView(this.f8490b);
        this.f8490b.findViewById(R.id.ll_container).setBackgroundResource(R.drawable.background_hd);
        m12303a();
        return linearLayout;
    }

    /* renamed from: a */
    void m12305a(Activity activity) {
        this.f8491c = activity;
        addView(m12304a((LayoutInflater) this.f8491c.getSystemService("layout_inflater"), null, null), -1, -1);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateLog);
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.updateLog) {
            this.f8489a.m12502a(C5313a.a());
        }
    }

    public void onClick(View view) {
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }
}
