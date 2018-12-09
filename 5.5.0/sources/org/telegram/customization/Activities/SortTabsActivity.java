package org.telegram.customization.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.C0910h;
import android.support.v7.widget.RecyclerView.C0955v;
import android.support.v7.widget.p032a.C0989a;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.p098a.C1768f;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Model.DialogTab;
import org.telegram.customization.util.view.p154a.C2920a;
import org.telegram.customization.util.view.p154a.p155a.C2517a;
import org.telegram.customization.util.view.p154a.p155a.C2518b;
import org.telegram.customization.util.view.p154a.p155a.C2519c;
import org.telegram.customization.util.view.p154a.p170b.C2921c;
import org.telegram.messenger.NotificationCenter;
import utils.p178a.C3791b;

public class SortTabsActivity extends Activity implements OnClickListener, C2517a, C2518b, C2519c {
    /* renamed from: a */
    private RecyclerView f8443a;
    /* renamed from: b */
    private C2920a f8444b;
    /* renamed from: c */
    private C0910h f8445c;
    /* renamed from: d */
    private C0989a f8446d;
    /* renamed from: e */
    private List<DialogTab> f8447e;

    /* renamed from: a */
    private void m12277a() {
        this.f8443a = (RecyclerView) findViewById(R.id.note_recycler_view);
        this.f8443a.setHasFixedSize(true);
        this.f8445c = new LinearLayoutManager(this);
        this.f8443a.setLayoutManager(this.f8445c);
        this.f8447e = C3791b.m14049u(getApplicationContext());
        this.f8444b = new C2920a(this.f8447e, this, this, this, this);
        this.f8446d = new C0989a(new C2921c(this.f8444b));
        this.f8446d.m5263a(this.f8443a);
        this.f8443a.setAdapter(this.f8444b);
    }

    /* renamed from: a */
    public void mo3424a(C0955v c0955v) {
        this.f8446d.m5271b(c0955v);
    }

    /* renamed from: a */
    public void mo3425a(List<DialogTab> list) {
        C3791b.m13996i(getApplicationContext(), new C1768f().m8395a((Object) list));
    }

    /* renamed from: b */
    public void mo3426b(C0955v c0955v) {
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
            case R.id.cancel:
                finish();
                return;
            case R.id.download:
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, Integer.valueOf(0));
                finish();
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sort_tabs);
        findViewById(R.id.download).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.iv_close).setOnClickListener(this);
        m12277a();
    }
}
