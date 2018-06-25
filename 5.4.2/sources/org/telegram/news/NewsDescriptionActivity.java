package org.telegram.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0188f;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import java.util.Observable;
import java.util.Observer;
import org.ir.talaeii.R;
import org.telegram.news.p175c.C3727a;
import org.telegram.news.p175c.C3749b;
import org.telegram.news.p176a.C3734a;
import org.telegram.news.p177b.C3744b;

public class NewsDescriptionActivity extends Activity implements Observer, C3727a {
    /* renamed from: a */
    int f9904a;
    /* renamed from: b */
    C3734a f9905b;
    /* renamed from: c */
    ViewPager f9906c;
    /* renamed from: d */
    ScaleGestureDetector f9907d;
    /* renamed from: e */
    private int f9908e;
    /* renamed from: f */
    private boolean f9909f;
    /* renamed from: g */
    private boolean f9910g;

    /* renamed from: org.telegram.news.NewsDescriptionActivity$1 */
    class C37261 implements C0188f {
        /* renamed from: a */
        final /* synthetic */ NewsDescriptionActivity f9903a;

        C37261(NewsDescriptionActivity newsDescriptionActivity) {
            this.f9903a = newsDescriptionActivity;
        }

        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
            this.f9903a.f9904a = i;
        }
    }

    /* renamed from: b */
    private void m13731b() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        this.f9904a = intent.getIntExtra(TtmlNode.ATTR_ID, 0);
        this.f9908e = intent.getIntExtra("EXTRA_TAG_ID", 0);
        String stringExtra = intent.getStringExtra("SPECIAL_NEWS");
        if (!TextUtils.isEmpty(stringExtra)) {
            this.f9908e = -1001;
            m13733a(true);
            m13734b(intent.getBooleanExtra("EXTRA_BACK_TO_HOME", true));
            C3749b.m13827c(this.f9908e);
            C3749b.m13823a(this.f9908e, true);
            C3744b c3744b = new C3744b();
            c3744b.m13775c(stringExtra);
            C3749b.m13821a(this.f9908e, (Context) this, c3744b);
        }
        if (this.f9904a < 0) {
            this.f9904a = 0;
        }
        this.f9906c = (ViewPager) findViewById(R.id.view_pager);
        this.f9906c.setVisibility(0);
        this.f9905b = new C3734a(this.f9906c, this, this.f9908e);
        this.f9905b.m13743b(this.f9904a);
        this.f9906c.setAdapter(this.f9905b);
        this.f9907d = new ScaleGestureDetector(this, new C3776h(this, this.f9905b));
        this.f9906c.setCurrentItem(this.f9904a);
        this.f9906c.setOffscreenPageLimit(1);
        this.f9906c.setOnPageChangeListener(new C37261(this));
    }

    /* renamed from: a */
    public void mo4194a() {
        if (this.f9905b != null) {
            this.f9905b.notifyDataSetChanged();
        }
    }

    /* renamed from: a */
    public void m13733a(boolean z) {
        this.f9909f = z;
    }

    /* renamed from: b */
    public void m13734b(boolean z) {
        this.f9910g = z;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.f9907d.onTouchEvent(motionEvent);
        return super.dispatchTouchEvent(motionEvent);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.news_description_activity);
        C3749b.m13819a().addObserver(this);
        m13731b();
    }

    protected void onDestroy() {
        super.onDestroy();
        C3749b.m13819a().deleteObserver(this);
    }

    protected void onResume() {
        super.onResume();
        this.f9905b.notifyDataSetChanged();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.f9907d.onTouchEvent(motionEvent);
        Log.d("TYPE", "TouchEvent");
        return super.onTouchEvent(motionEvent);
    }

    public void update(Observable observable, Object obj) {
        try {
            mo4194a();
        } catch (Exception e) {
        }
    }
}
