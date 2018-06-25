package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.animation.ObjectAnimator;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.mohamadamin.persianmaterialdatetimepicker.C2021a;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2022a;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2025d;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2026e;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2027f;
import com.mohamadamin.persianmaterialdatetimepicker.C2029c;
import com.mohamadamin.persianmaterialdatetimepicker.C2030d;
import com.mohamadamin.persianmaterialdatetimepicker.date.C2042d.C2040a;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2017a;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2018b;
import java.util.HashSet;
import java.util.Iterator;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.b */
public class C2036b extends DialogFragment implements OnClickListener, C2031a {
    /* renamed from: A */
    private String f5960A;
    /* renamed from: B */
    private String f5961B;
    /* renamed from: a */
    private final C2018b f5962a = new C2018b();
    /* renamed from: b */
    private C2035b f5963b;
    /* renamed from: c */
    private HashSet<C2034a> f5964c = new HashSet();
    /* renamed from: d */
    private OnCancelListener f5965d;
    /* renamed from: e */
    private OnDismissListener f5966e;
    /* renamed from: f */
    private AccessibleDateAnimator f5967f;
    /* renamed from: g */
    private TextView f5968g;
    /* renamed from: h */
    private LinearLayout f5969h;
    /* renamed from: i */
    private TextView f5970i;
    /* renamed from: j */
    private TextView f5971j;
    /* renamed from: k */
    private TextView f5972k;
    /* renamed from: l */
    private C2039c f5973l;
    /* renamed from: m */
    private C2050i f5974m;
    /* renamed from: n */
    private int f5975n = -1;
    /* renamed from: o */
    private int f5976o = 7;
    /* renamed from: p */
    private int f5977p = 1350;
    /* renamed from: q */
    private int f5978q = 1450;
    /* renamed from: r */
    private C2018b f5979r;
    /* renamed from: s */
    private C2018b f5980s;
    /* renamed from: t */
    private C2018b[] f5981t;
    /* renamed from: u */
    private C2018b[] f5982u;
    /* renamed from: v */
    private boolean f5983v;
    /* renamed from: w */
    private C2021a f5984w;
    /* renamed from: x */
    private boolean f5985x = true;
    /* renamed from: y */
    private String f5986y;
    /* renamed from: z */
    private String f5987z;

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.b$1 */
    class C20321 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2036b f5958a;

        C20321(C2036b c2036b) {
            this.f5958a = c2036b;
        }

        public void onClick(View view) {
            this.f5958a.mo3073j();
            if (this.f5958a.f5963b != null) {
                this.f5958a.f5963b.mo3443a(this.f5958a, this.f5958a.f5962a.m9095b(), this.f5958a.f5962a.m9096c(), this.f5958a.f5962a.m9098e());
            }
            this.f5958a.dismiss();
        }
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.b$2 */
    class C20332 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2036b f5959a;

        C20332(C2036b c2036b) {
            this.f5959a = c2036b;
        }

        public void onClick(View view) {
            this.f5959a.mo3073j();
            this.f5959a.getDialog().cancel();
        }
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.b$a */
    public interface C2034a {
        /* renamed from: a */
        void mo3074a();
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.b$b */
    public interface C2035b {
        /* renamed from: a */
        void mo3443a(C2036b c2036b, int i, int i2, int i3);
    }

    /* renamed from: a */
    public static C2036b m9136a(C2035b c2035b, int i, int i2, int i3) {
        C2036b c2036b = new C2036b();
        c2036b.m9147b(c2035b, i, i2, i3);
        return c2036b;
    }

    /* renamed from: a */
    private void m9137a(int i, int i2) {
    }

    /* renamed from: a */
    private void m9138a(boolean z) {
        if (this.f5968g != null) {
            this.f5968g.setText(this.f5962a.m9099f());
        }
        this.f5970i.setText(C2017a.m9087a(this.f5962a.m9097d()));
        this.f5971j.setText(C2017a.m9087a(String.valueOf(this.f5962a.m9098e())));
        this.f5972k.setText(C2017a.m9087a(String.valueOf(this.f5962a.m9095b())));
        this.f5967f.setDateMillis(this.f5962a.getTimeInMillis());
        this.f5969h.setContentDescription(C2017a.m9087a(this.f5962a.m9097d() + " " + this.f5962a.m9098e()));
        if (z) {
            C2030d.m9116a(this.f5967f, C2017a.m9087a(this.f5962a.m9100g()));
        }
    }

    /* renamed from: b */
    private void m9140b(int i) {
        ObjectAnimator a;
        switch (i) {
            case 0:
                a = C2030d.m9115a(this.f5969h, 0.9f, 1.05f);
                if (this.f5985x) {
                    a.setStartDelay(500);
                    this.f5985x = false;
                }
                this.f5973l.mo3074a();
                if (this.f5975n != i) {
                    this.f5969h.setSelected(true);
                    this.f5972k.setSelected(false);
                    this.f5967f.setDisplayedChild(0);
                    this.f5975n = i;
                }
                a.start();
                this.f5967f.setContentDescription(this.f5986y + ": " + C2017a.m9087a(this.f5962a.m9100g()));
                C2030d.m9116a(this.f5967f, this.f5987z);
                return;
            case 1:
                a = C2030d.m9115a(this.f5972k, 0.85f, 1.1f);
                if (this.f5985x) {
                    a.setStartDelay(500);
                    this.f5985x = false;
                }
                this.f5974m.mo3074a();
                if (this.f5975n != i) {
                    this.f5969h.setSelected(false);
                    this.f5972k.setSelected(true);
                    this.f5967f.setDisplayedChild(1);
                    this.f5975n = i;
                }
                a.start();
                this.f5967f.setContentDescription(this.f5960A + ": " + C2017a.m9087a(String.valueOf(this.f5962a.m9095b())));
                C2030d.m9116a(this.f5967f, this.f5961B);
                return;
            default:
                return;
        }
    }

    /* renamed from: k */
    private void m9141k() {
        Iterator it = this.f5964c.iterator();
        while (it.hasNext()) {
            ((C2034a) it.next()).mo3074a();
        }
    }

    /* renamed from: a */
    public C2040a mo3061a() {
        return new C2040a(this.f5962a);
    }

    /* renamed from: a */
    public void mo3062a(int i) {
        m9137a(this.f5962a.m9096c(), i);
        this.f5962a.m9094a(i, this.f5962a.m9096c(), this.f5962a.m9098e());
        m9141k();
        m9140b(0);
        m9138a(true);
    }

    /* renamed from: a */
    public void mo3063a(int i, int i2, int i3) {
        this.f5962a.m9094a(i, i2, i3);
        m9141k();
        m9138a(true);
    }

    /* renamed from: a */
    public void mo3064a(C2034a c2034a) {
        this.f5964c.add(c2034a);
    }

    /* renamed from: a */
    public void m9146a(C2035b c2035b) {
        this.f5963b = c2035b;
    }

    /* renamed from: b */
    public void m9147b(C2035b c2035b, int i, int i2, int i3) {
        this.f5963b = c2035b;
        this.f5962a.m9094a(i, i2, i3);
        this.f5983v = false;
    }

    /* renamed from: b */
    public boolean mo3065b() {
        return this.f5983v;
    }

    /* renamed from: c */
    public C2018b[] mo3066c() {
        return this.f5981t;
    }

    /* renamed from: d */
    public C2018b[] mo3067d() {
        return this.f5982u;
    }

    /* renamed from: e */
    public int mo3068e() {
        return this.f5976o;
    }

    /* renamed from: f */
    public int mo3069f() {
        return this.f5982u != null ? this.f5982u[0].m9095b() : (this.f5979r == null || this.f5979r.m9095b() <= this.f5977p) ? this.f5977p : this.f5979r.m9095b();
    }

    /* renamed from: g */
    public int mo3070g() {
        return this.f5982u != null ? this.f5982u[this.f5982u.length - 1].m9095b() : (this.f5980s == null || this.f5980s.m9095b() >= this.f5978q) ? this.f5978q : this.f5980s.m9095b();
    }

    /* renamed from: h */
    public C2018b mo3071h() {
        return this.f5979r;
    }

    /* renamed from: i */
    public C2018b mo3072i() {
        return this.f5980s;
    }

    /* renamed from: j */
    public void mo3073j() {
        this.f5984w.m9112c();
    }

    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        if (this.f5965d != null) {
            this.f5965d.onCancel(dialogInterface);
        }
    }

    public void onClick(View view) {
        mo3073j();
        if (view.getId() == C2025d.date_picker_year) {
            m9140b(1);
        } else if (view.getId() == C2025d.date_picker_month_and_day) {
            m9140b(0);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActivity().getWindow().setSoftInputMode(3);
        if (bundle != null) {
            this.f5962a.m9094a(bundle.getInt("year"), bundle.getInt("month"), bundle.getInt("day"));
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        int i2;
        int i3;
        Log.d("DatePickerDialog", "onCreateView: ");
        getDialog().getWindow().requestFeature(1);
        View inflate = layoutInflater.inflate(C2026e.mdtp_date_picker_dialog, null);
        this.f5968g = (TextView) inflate.findViewById(C2025d.date_picker_header);
        this.f5969h = (LinearLayout) inflate.findViewById(C2025d.date_picker_month_and_day);
        this.f5969h.setOnClickListener(this);
        this.f5970i = (TextView) inflate.findViewById(C2025d.date_picker_month);
        this.f5971j = (TextView) inflate.findViewById(C2025d.date_picker_day);
        this.f5972k = (TextView) inflate.findViewById(C2025d.date_picker_year);
        this.f5972k.setOnClickListener(this);
        if (bundle != null) {
            this.f5976o = bundle.getInt("week_start");
            this.f5977p = bundle.getInt("year_start");
            this.f5978q = bundle.getInt("year_end");
            i = bundle.getInt("current_view");
            i2 = bundle.getInt("list_position");
            i3 = bundle.getInt("list_position_offset");
            this.f5979r = (C2018b) bundle.getSerializable("min_date");
            this.f5980s = (C2018b) bundle.getSerializable("max_date");
            this.f5981t = (C2018b[]) bundle.getSerializable("highlighted_days");
            this.f5982u = (C2018b[]) bundle.getSerializable("selectable_days");
            this.f5983v = bundle.getBoolean("theme_dark");
        } else {
            i2 = -1;
            i3 = 0;
            i = 0;
        }
        Context activity = getActivity();
        this.f5973l = new C2045f(activity, this);
        this.f5974m = new C2050i(activity, this);
        Resources resources = getResources();
        this.f5986y = resources.getString(C2027f.mdtp_day_picker_description);
        this.f5987z = resources.getString(C2027f.mdtp_select_day);
        this.f5960A = resources.getString(C2027f.mdtp_year_picker_description);
        this.f5961B = resources.getString(C2027f.mdtp_select_year);
        inflate.setBackgroundColor(activity.getResources().getColor(this.f5983v ? C2022a.mdtp_date_picker_view_animator_dark_theme : C2022a.mdtp_date_picker_view_animator));
        this.f5967f = (AccessibleDateAnimator) inflate.findViewById(C2025d.animator);
        this.f5967f.addView(this.f5973l);
        this.f5967f.addView(this.f5974m);
        this.f5967f.setDateMillis(this.f5962a.getTimeInMillis());
        Animation alphaAnimation = new AlphaAnimation(BitmapDescriptorFactory.HUE_RED, 1.0f);
        alphaAnimation.setDuration(300);
        this.f5967f.setInAnimation(alphaAnimation);
        alphaAnimation = new AlphaAnimation(1.0f, BitmapDescriptorFactory.HUE_RED);
        alphaAnimation.setDuration(300);
        this.f5967f.setOutAnimation(alphaAnimation);
        Button button = (Button) inflate.findViewById(C2025d.ok);
        button.setOnClickListener(new C20321(this));
        button.setTypeface(C2029c.m9113a(activity, "Roboto-Medium"));
        button = (Button) inflate.findViewById(C2025d.cancel);
        button.setOnClickListener(new C20332(this));
        button.setTypeface(C2029c.m9113a(activity, "Roboto-Medium"));
        button.setVisibility(isCancelable() ? 0 : 8);
        m9138a(false);
        m9140b(i);
        if (i2 != -1) {
            if (i == 0) {
                this.f5973l.m9163a(i2);
            } else if (i == 1) {
                this.f5974m.m9217a(i2, i3);
            }
        }
        this.f5984w = new C2021a(activity);
        return inflate;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        if (this.f5966e != null) {
            this.f5966e.onDismiss(dialogInterface);
        }
    }

    public void onPause() {
        super.onPause();
        this.f5984w.m9111b();
    }

    public void onResume() {
        super.onResume();
        this.f5984w.m9110a();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("year", this.f5962a.m9095b());
        bundle.putInt("month", this.f5962a.m9096c());
        bundle.putInt("day", this.f5962a.m9098e());
        bundle.putInt("week_start", this.f5976o);
        bundle.putInt("year_start", this.f5977p);
        bundle.putInt("year_end", this.f5978q);
        bundle.putInt("current_view", this.f5975n);
        int i = -1;
        if (this.f5975n == 0) {
            i = this.f5973l.getMostVisiblePosition();
        } else if (this.f5975n == 1) {
            i = this.f5974m.getFirstVisiblePosition();
            bundle.putInt("list_position_offset", this.f5974m.getFirstPositionOffset());
        }
        bundle.putInt("list_position", i);
        bundle.putSerializable("min_date", this.f5979r);
        bundle.putSerializable("max_date", this.f5980s);
        bundle.putSerializable("highlighted_days", this.f5981t);
        bundle.putSerializable("selectable_days", this.f5982u);
        bundle.putBoolean("theme_dark", this.f5983v);
    }
}
