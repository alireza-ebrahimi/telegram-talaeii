package org.telegram.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.C0235a;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.C0675b;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.C0926a;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.util.view.observerRecyclerView.C2621b;
import org.telegram.customization.util.view.observerRecyclerView.C2952c;
import org.telegram.customization.util.view.observerRecyclerView.ObservableRecyclerView;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.news.p175c.C3727a;
import org.telegram.news.p175c.C3749b;
import org.telegram.news.p176a.C3740b;
import org.telegram.news.p177b.C3744b;
import org.telegram.ui.LaunchActivity;
import p046c.p047a.p048a.p049a.C1242b;
import utils.p178a.C3791b;
import utils.view.SimpleDividerItemDecoration;

/* renamed from: org.telegram.news.d */
public class C3752d extends FrameLayout implements C0675b, OnClickListener, OnItemClickListener, Observer, C2497d, C2621b, C3727a {
    /* renamed from: a */
    Activity f10019a;
    /* renamed from: b */
    boolean f10020b = false;
    /* renamed from: c */
    boolean f10021c = true;
    /* renamed from: d */
    boolean f10022d = false;
    /* renamed from: e */
    private View f10023e;
    /* renamed from: f */
    private View f10024f;
    /* renamed from: g */
    private SwipeRefreshLayout f10025g;
    /* renamed from: h */
    private ObservableRecyclerView f10026h;
    /* renamed from: i */
    private int f10027i = 0;
    /* renamed from: j */
    private C3740b f10028j;

    public C3752d(Activity activity) {
        super(activity);
        m13855a(activity);
    }

    /* renamed from: a */
    private void m13845a(int i) {
        this.f10026h.setVisibility(8);
        this.f10024f.setVisibility(0);
        TextView textView = (TextView) this.f10024f.findViewById(R.id.txt_error);
        ImageView imageView = (ImageView) this.f10024f.findViewById(R.id.iv_error);
        m13850d();
        switch (i) {
            case -3000:
                textView.setText(getResources().getString(R.string.err_empty));
                imageView.setImageDrawable(C0235a.m1066a(this.f10019a, (int) R.drawable.sad));
                break;
            case -2000:
                textView.setText(getResources().getString(R.string.err_get_data));
                imageView.setImageDrawable(C0235a.m1066a(this.f10019a, (int) R.drawable.sad));
                break;
            case C3446C.PRIORITY_DOWNLOAD /*-1000*/:
                textView.setText(getResources().getString(R.string.network_error));
                imageView.setImageDrawable(C0235a.m1066a(this.f10019a, (int) R.drawable.wifi_off));
                break;
        }
        this.f10025g.setEnabled(true);
    }

    /* renamed from: a */
    private void m13846a(String str) {
        m13849c();
        String str2 = "0";
        Object obj = -1;
        switch (str.hashCode()) {
            case 3377907:
                if (str.equals("next")) {
                    obj = null;
                    break;
                }
                break;
            case 3449395:
                if (str.equals("prev")) {
                    obj = 1;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                str2 = getFirstPostId();
                break;
            case 1:
                str2 = getLastPostId();
                break;
        }
        C2818c.m13087a(getContext(), (C2497d) this).m13113a((long) getTagId(), str2, str, 20);
    }

    /* renamed from: a */
    private void m13847a(ArrayList<C3744b> arrayList) {
        if (arrayList != null && arrayList.size() < 20) {
            this.f10021c = false;
        }
        C3749b.m13820a(getTagId(), this.f10019a, (ArrayList) arrayList);
    }

    /* renamed from: b */
    private void m13848b() {
        try {
            this.f10027i = Integer.parseInt(TtmlNode.ANONYMOUS_REGION_ID + C3791b.m14035r(getContext()));
        } catch (Exception e) {
        }
        if (this.f10027i != 0) {
            this.f10020b = true;
            this.f10024f = this.f10023e.findViewById(R.id.error_layout);
            this.f10025g = (SwipeRefreshLayout) this.f10023e.findViewById(R.id.refresher);
            this.f10025g.setOnRefreshListener(this);
            this.f10025g.setColorSchemeResources(R.color.flag_text_color, R.color.elv_btn_pressed, R.color.pink);
            this.f10025g.m3273a(true, Callback.DEFAULT_DRAG_ANIMATION_DURATION, 300);
            this.f10026h = (ObservableRecyclerView) this.f10023e.findViewById(R.id.recycler);
            this.f10026h.setScrollPositionChangesListener(this);
            this.f10026h.setLayoutManager(new LinearLayoutManager(this.f10019a, 1, false));
            this.f10026h.m239a(new SimpleDividerItemDecoration(this.f10019a));
            new ExtraData().setTagId((long) this.f10027i);
            this.f10028j = new C3740b(this.f10019a, this.f10027i, this.f10026h, this);
            C0926a c1242b = new C1242b(this.f10028j);
            c1242b.m6473a(300);
            this.f10026h.setAdapter(c1242b);
            m13851e();
            m13846a("next");
        }
    }

    /* renamed from: c */
    private void m13849c() {
        this.f10026h.setLoadingEnd(true);
    }

    /* renamed from: d */
    private void m13850d() {
        findViewById(R.id.loading).setVisibility(8);
        this.f10026h.setLoadingEnd(false);
        this.f10025g.setRefreshing(false);
        this.f10025g.setEnabled(false);
        this.f10026h.setLoadingEnd(false);
        this.f10026h.setLoadingStart(false);
        this.f10026h.setVisibility(0);
    }

    /* renamed from: e */
    private void m13851e() {
        ((LaunchActivity) this.f10019a).drawerLayoutContainer.setAllowOpenDrawer(false, false);
    }

    /* renamed from: f */
    private void m13852f() {
        this.f10025g.setRefreshing(false);
        this.f10025g.setEnabled(false);
        this.f10026h.setLoadingEnd(false);
        this.f10026h.setLoadingStart(false);
    }

    private String getFirstPostId() {
        return (this.f10028j == null || this.f10028j.m13749b().isEmpty()) ? "0" : ((C3744b) this.f10028j.m13749b().get(0)).m13787h();
    }

    private String getLastPostId() {
        if (this.f10028j == null || this.f10028j.m13749b().isEmpty()) {
            return "0";
        }
        C3744b c3744b = (C3744b) this.f10028j.m13749b().get((this.f10028j.getItemCount() - 1) - (this.f10028j.m13748a() ? 1 : 0));
        Log.d("LEE", "dddddd:" + c3744b.m13789i() + " " + c3744b.m13787h());
        return c3744b.m13787h();
    }

    /* renamed from: a */
    public View m13853a(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View linearLayout = new LinearLayout(this.f10019a);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-1, -1));
        linearLayout.setBackgroundColor(-1);
        this.f10023e = layoutInflater.inflate(R.layout.activity_news_list, linearLayout, false);
        linearLayout.addView(this.f10023e);
        m13848b();
        return linearLayout;
    }

    /* renamed from: a */
    public void mo4194a() {
        this.f10028j.notifyItemRangeChanged(this.f10028j.getItemCount(), 1);
    }

    /* renamed from: a */
    void m13855a(Activity activity) {
        this.f10019a = activity;
        addView(m13853a((LayoutInflater) this.f10019a.getSystemService("layout_inflater"), null, null), -1, -1);
    }

    /* renamed from: a */
    public void mo3451a(View view) {
        this.f10025g.setEnabled(false);
        m13846a("prev");
    }

    /* renamed from: a */
    public void mo3452a(View view, int i) {
        this.f10025g.setEnabled(false);
    }

    /* renamed from: a */
    public void mo3454a(C2952c c2952c) {
    }

    /* renamed from: b */
    public void mo3455b(View view) {
        this.f10026h.setLoadingEnd(false);
        this.f10026h.setLoadingStart(false);
        this.f10025g.setEnabled(true);
    }

    public int getTagId() {
        return this.f10027i;
    }

    public void j_() {
        m13852f();
        m13846a("next");
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        C3749b.m13819a().addObserver(this);
    }

    public void onClick(View view) {
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        C3749b.m13819a().deleteObserver(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Log.d("LEE", "Cliiiiick");
        Intent intent = new Intent(this.f10019a, NewsDescriptionActivity.class);
        intent.putExtra("EXTRA_TAG_ID", getTagId());
        intent.putExtra(TtmlNode.ATTR_ID, i);
        this.f10019a.startActivity(intent);
    }

    public void onResult(Object obj, int i) {
        m13850d();
        switch (i) {
            case -11:
                m13845a(-2000);
                return;
            case 11:
                m13847a((ArrayList) obj);
                return;
            default:
                return;
        }
    }

    public void setTagId(int i) {
        this.f10027i = i;
    }

    public void update(Observable observable, Object obj) {
        try {
            mo4194a();
        } catch (Exception e) {
        }
    }
}
