package org.telegram.customization.Activities;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ProgressBar;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.util.C2872c;
import org.telegram.customization.util.view.observerRecyclerView.ObservableRecyclerView;
import org.telegram.messenger.ApplicationLoader;
import utils.view.ToastUtil;

/* renamed from: org.telegram.customization.Activities.n */
public class C2625n extends Activity implements OnClickListener, C2497d {
    /* renamed from: a */
    ObservableRecyclerView f8788a;
    /* renamed from: b */
    DynamicAdapter f8789b;
    /* renamed from: c */
    ProgressBar f8790c;
    /* renamed from: d */
    View f8791d;
    /* renamed from: e */
    View f8792e;
    /* renamed from: f */
    Toolbar f8793f;

    /* renamed from: a */
    private void m12475a() {
        this.f8788a = (ObservableRecyclerView) findViewById(R.id.recycler);
        this.f8788a.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        this.f8788a.setAdapter(this.f8789b);
        this.f8790c = (ProgressBar) findViewById(R.id.pb_loading);
        this.f8791d = findViewById(R.id.ftv_empty_list);
        this.f8792e = findViewById(R.id.iv_back);
        this.f8792e.setOnClickListener(this);
        this.f8793f = (Toolbar) findViewById(R.id.toolbar);
        int i = ApplicationLoader.applicationContext.getSharedPreferences("theme", 0).getInt("themeColor", C2872c.f9484b);
        int a = C2872c.m13332a(i, 21);
        this.f8793f.setBackgroundColor(i);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(a);
        }
    }

    /* renamed from: b */
    private void m12476b() {
        C2818c.m13087a(getApplicationContext(), (C2497d) this).m13129c();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.user_tag_list_activity);
        m12475a();
        m12476b();
    }

    public void onResult(Object obj, int i) {
        this.f8790c.setVisibility(8);
        switch (i) {
            case -9:
                ToastUtil.a(getApplicationContext(), getString(R.string.err_get_data)).show();
                finish();
                return;
            case 9:
                ArrayList arrayList = (ArrayList) obj;
                if (arrayList.size() > 0) {
                    this.f8789b.setItems(arrayList);
                    this.f8789b.notifyDataSetChanged();
                    return;
                }
                this.f8791d.setVisibility(0);
                return;
            default:
                return;
        }
    }
}
