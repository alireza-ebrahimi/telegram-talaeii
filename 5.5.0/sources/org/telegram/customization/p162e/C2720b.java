package org.telegram.customization.p162e;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Model.HotgramTheme;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p156a.C2658j;
import org.telegram.messenger.ApplicationLoader;

/* renamed from: org.telegram.customization.e.b */
public class C2720b extends Fragment implements OnItemClickListener, C2497d {
    /* renamed from: a */
    RecyclerView f8945a;
    /* renamed from: b */
    C2658j f8946b;
    /* renamed from: c */
    ArrayList<HotgramTheme> f8947c = new ArrayList();
    /* renamed from: d */
    ProgressBar f8948d;
    /* renamed from: e */
    String f8949e;
    /* renamed from: f */
    View f8950f;
    /* renamed from: g */
    boolean f8951g = false;

    /* renamed from: org.telegram.customization.e.b$1 */
    class C27191 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2720b f8944a;

        C27191(C2720b c2720b) {
            this.f8944a = c2720b;
        }

        public void onClick(View view) {
            this.f8944a.f8948d.setVisibility(0);
            this.f8944a.f8950f.setVisibility(8);
            C2818c.m13087a(ApplicationLoader.applicationContext, this.f8944a).m13138h();
        }
    }

    /* renamed from: a */
    public String m12594a() {
        return this.f8949e;
    }

    /* renamed from: a */
    public void m12595a(String str) {
        this.f8949e = str;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_choose_theme, viewGroup, false);
        this.f8945a = (RecyclerView) inflate.findViewById(R.id.recycler);
        this.f8945a.setLayoutManager(new GridLayoutManager(ApplicationLoader.applicationContext, 2));
        this.f8948d = (ProgressBar) inflate.findViewById(R.id.pb_loading);
        this.f8950f = inflate.findViewById(R.id.btn_retry);
        C2818c.m13087a(ApplicationLoader.applicationContext, (C2497d) this).m13138h();
        this.f8950f.setOnClickListener(new C27191(this));
        return inflate;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        for (int i2 = 0; i2 < this.f8947c.size(); i2++) {
            if (((HotgramTheme) this.f8947c.get(i2)).isSelected()) {
                ((HotgramTheme) this.f8947c.get(i2)).setSelected(false);
                this.f8946b.notifyItemChanged(i2);
                break;
            }
        }
        if (!((HotgramTheme) this.f8947c.get(i)).isSelected()) {
            ((HotgramTheme) this.f8947c.get(i)).setSelected(true);
            m12595a(((HotgramTheme) this.f8947c.get(i)).getName());
        }
        this.f8946b.notifyItemChanged(i);
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case -26:
                if (!this.f8951g) {
                    this.f8948d.setVisibility(8);
                    this.f8950f.setVisibility(0);
                    return;
                }
                return;
            case 26:
                this.f8948d.setVisibility(8);
                this.f8947c = (ArrayList) obj;
                if (this.f8947c != null && this.f8947c.size() > 0) {
                    this.f8946b = new C2658j(this.f8947c, this, this.f8945a);
                    this.f8945a.setAdapter(this.f8946b);
                    this.f8951g = true;
                    return;
                }
                return;
            default:
                return;
        }
    }
}
