package org.telegram.customization.p161d;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Model.FilterHelper;
import org.telegram.customization.Model.FilterItem;
import org.telegram.customization.p152f.C2620a;
import org.telegram.customization.p156a.C2640d;
import org.telegram.ui.ActionBar.Theme;

/* renamed from: org.telegram.customization.d.a */
public class C2677a extends Dialog {
    /* renamed from: a */
    Activity f8924a;
    /* renamed from: b */
    String f8925b;
    /* renamed from: c */
    int f8926c;
    /* renamed from: d */
    ArrayList<FilterItem> f8927d = new ArrayList();
    /* renamed from: e */
    C2620a f8928e;
    /* renamed from: f */
    C2640d f8929f;
    /* renamed from: g */
    FilterHelper f8930g = new FilterHelper();
    /* renamed from: h */
    TextView f8931h;
    /* renamed from: i */
    RecyclerView f8932i;
    /* renamed from: j */
    Button f8933j;
    /* renamed from: k */
    Button f8934k;
    /* renamed from: l */
    View f8935l;
    /* renamed from: m */
    View f8936m;
    /* renamed from: n */
    View f8937n;
    /* renamed from: o */
    View f8938o;

    public C2677a(Activity activity, String str, int i, ArrayList<FilterItem> arrayList, C2620a c2620a) {
        super(activity);
        this.f8924a = activity;
        this.f8925b = str;
        this.f8926c = i;
        this.f8927d = arrayList;
        this.f8928e = c2620a;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.dialog_filter);
        this.f8931h = (TextView) findViewById(R.id.tv_header);
        this.f8938o = findViewById(R.id.v_header);
        this.f8938o.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        this.f8932i = (RecyclerView) findViewById(R.id.recycler);
        this.f8937n = findViewById(R.id.ll_btn_container);
        this.f8933j = (Button) findViewById(R.id.btn_submit_filter);
        this.f8934k = (Button) findViewById(R.id.btn_cancel);
        this.f8935l = findViewById(R.id.pb_loading);
        this.f8936m = findViewById(R.id.v_close);
        this.f8929f = new C2640d(this, this.f8927d, this.f8928e);
        this.f8932i.setLayoutManager(new LinearLayoutManager(this.f8924a, 1, false));
        this.f8932i.setAdapter(this.f8929f);
        this.f8931h.setText(this.f8925b);
        this.f8935l.setVisibility(8);
        this.f8932i.setVisibility(0);
        if (this.f8926c == 3) {
            this.f8937n.setVisibility(8);
        }
    }
}
