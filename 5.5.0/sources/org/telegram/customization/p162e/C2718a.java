package org.telegram.customization.p162e;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import org.ir.talaeii.R;

/* renamed from: org.telegram.customization.e.a */
public class C2718a extends Fragment implements OnClickListener {
    /* renamed from: a */
    RelativeLayout f8939a;
    /* renamed from: b */
    RelativeLayout f8940b;
    /* renamed from: c */
    ImageView f8941c;
    /* renamed from: d */
    ImageView f8942d;
    /* renamed from: e */
    boolean f8943e;

    /* renamed from: a */
    public void m12592a(boolean z) {
        this.f8943e = z;
    }

    /* renamed from: a */
    public boolean m12593a() {
        return this.f8943e;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_with_tab:
                this.f8941c.setImageResource(R.drawable.check_circle_green);
                this.f8942d.setImageResource(R.drawable.check_circle_gray);
                m12592a(false);
                return;
            case R.id.rl_no_tab:
                this.f8941c.setImageResource(R.drawable.check_circle_gray);
                this.f8942d.setImageResource(R.drawable.check_circle_green);
                m12592a(true);
                return;
            default:
                return;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_choose_tab_style, viewGroup, false);
        this.f8939a = (RelativeLayout) inflate.findViewById(R.id.rl_with_tab);
        this.f8940b = (RelativeLayout) inflate.findViewById(R.id.rl_no_tab);
        this.f8941c = (ImageView) inflate.findViewById(R.id.iv_checked);
        this.f8942d = (ImageView) inflate.findViewById(R.id.iv_checked1);
        this.f8939a.setOnClickListener(this);
        this.f8940b.setOnClickListener(this);
        return inflate;
    }
}
