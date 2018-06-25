package org.telegram.customization.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0188f;
import android.util.Log;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import org.ir.talaeii.R;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p153c.C2516a;
import org.telegram.customization.p153c.C2671d;
import org.telegram.customization.p156a.C2651g;
import utils.view.VideoController.LightnessController;

public class SlsMediaViewActivity extends Activity implements C0188f, Observer, C2516a, C2497d {
    /* renamed from: a */
    C2651g f8437a;
    /* renamed from: b */
    ViewPager f8438b;
    /* renamed from: c */
    private long f8439c;
    /* renamed from: d */
    private boolean f8440d = false;
    /* renamed from: e */
    private int f8441e;
    /* renamed from: f */
    private int f8442f;

    /* renamed from: org.telegram.customization.Activities.SlsMediaViewActivity$1 */
    class C25151 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ SlsMediaViewActivity f8436a;

        C25151(SlsMediaViewActivity slsMediaViewActivity) {
            this.f8436a = slsMediaViewActivity;
        }

        public void run() {
            if (this.f8436a.f8442f == 1) {
                C2818c.m13087a(this.f8436a, this.f8436a).m13110a(this.f8436a.f8439c, C2671d.m12546b(this.f8436a.f8442f), true, C2671d.m12557i(this.f8436a.f8442f), C2671d.m12556h(this.f8436a.f8442f));
            } else {
                C2818c.m13087a(this.f8436a, this.f8436a).m13111a(this.f8436a.f8439c, C2671d.m12546b(this.f8436a.f8442f), true, C2671d.m12554f(this.f8436a.f8442f), C2671d.m12557i(this.f8436a.f8442f), C2671d.m12556h(this.f8436a.f8442f), C2671d.m12558j(this.f8436a.f8442f), C2671d.m12555g(this.f8436a.f8442f));
            }
        }
    }

    /* renamed from: a */
    private void m12271a(int i) {
        if (!C2671d.m12553e(this.f8442f) && i + 4 >= C2671d.m12552d(this.f8442f) && !this.f8440d) {
            this.f8440d = true;
            try {
                new Thread(new C25151(this)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: a */
    public void mo3423a() {
        if (this.f8437a != null) {
            this.f8437a.notifyDataSetChanged();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    protected void onCreate(Bundle bundle) {
        int intExtra;
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.f8439c = 0;
        if (intent != null) {
            this.f8439c = intent.getLongExtra("EXTRA_TAG_ID", 0);
            this.f8442f = intent.getIntExtra("EXTRA_POOL_ID", 0);
            intExtra = intent.getIntExtra("EXTRA_CURRENT_POSITION", 0);
        } else {
            intExtra = 0;
        }
        setContentView(R.layout.sls_media_viewr_activity);
        this.f8438b = (ViewPager) findViewById(R.id.view_pager);
        this.f8437a = new C2651g(this.f8438b, this, getApplicationContext(), this.f8442f);
        this.f8438b.setAdapter(this.f8437a);
        this.f8441e = LightnessController.a(this);
        this.f8438b.addOnPageChangeListener(this);
        this.f8438b.setCurrentItem(intExtra);
        C2671d.m12538a().addObserver(this);
        if (bundle != null) {
            this.f8437a.m12498a(bundle.getBundle("MEDIA_STATE_CHANGE_BUNDLE"));
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        C2671d.m12538a().deleteObserver(this);
    }

    public void onPageScrollStateChanged(int i) {
    }

    public void onPageScrolled(int i, float f, int i2) {
    }

    public void onPageSelected(int i) {
        Log.d("LEE", "poolID:2  " + this.f8442f);
        m12271a(i);
    }

    protected void onPause() {
        super.onPause();
        this.f8441e = LightnessController.a(this);
    }

    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case 1:
                ArrayList arrayList = (ArrayList) obj;
                this.f8440d = false;
                C2671d.m12543a(this.f8442f, arrayList);
                this.f8437a.notifyDataSetChanged();
                return;
            default:
                return;
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBundle("MEDIA_STATE_CHANGE_BUNDLE", this.f8437a.m12496a());
    }

    public void update(Observable observable, Object obj) {
        try {
            mo3423a();
        } catch (Exception e) {
        }
    }
}
