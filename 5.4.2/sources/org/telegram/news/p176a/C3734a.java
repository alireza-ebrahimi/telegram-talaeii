package org.telegram.news.p176a;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.util.view.p158c.C2650b;
import org.telegram.news.C3733c;
import org.telegram.news.C3773e;
import org.telegram.news.p175c.C3727a;
import org.telegram.news.p175c.C3749b;
import org.telegram.news.p177b.C3744b;

/* renamed from: org.telegram.news.a.a */
public class C3734a extends C2650b implements C2497d, C3733c, C3727a {
    /* renamed from: a */
    public static Integer f9920a = Integer.valueOf(1);
    /* renamed from: g */
    private static LayoutInflater f9921g = null;
    /* renamed from: b */
    Activity f9922b;
    /* renamed from: c */
    int f9923c;
    /* renamed from: d */
    int f9924d;
    /* renamed from: e */
    boolean f9925e = false;
    /* renamed from: f */
    ViewPager f9926f;
    /* renamed from: h */
    private final int f9927h;
    /* renamed from: i */
    private ArrayList<C3733c> f9928i;
    /* renamed from: j */
    private int f9929j;

    /* renamed from: org.telegram.news.a.a$1 */
    class C37301 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C3734a f9916a;

        C37301(C3734a c3734a) {
            this.f9916a = c3734a;
        }

        public void run() {
            C2818c.m13087a(this.f9916a.f9922b, this.f9916a).m13113a((long) this.f9916a.f9927h, C3749b.m13824b(this.f9916a.f9927h), "prev", 20);
        }
    }

    /* renamed from: org.telegram.news.a.a$a */
    private static class C3732a {
        /* renamed from: a */
        final C3773e f9919a;

        public C3732a(View view) {
            this.f9919a = (C3773e) view;
        }
    }

    public C3734a(ViewPager viewPager, Activity activity, int i) {
        this.f9922b = activity;
        f9921g = (LayoutInflater) activity.getSystemService("layout_inflater");
        this.f9927h = i;
        this.f9926f = viewPager;
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        this.f9923c = Math.round(180.0f * (displayMetrics.xdpi / 160.0f));
        this.f9924d = Math.round((displayMetrics.xdpi / 160.0f) * 300.0f);
    }

    /* renamed from: c */
    private void m13738c(int i) {
        if (!C3749b.m13828d(this.f9927h) && i + 4 >= m13739d().size() && !this.f9925e) {
            Log.d("srdc", "less than 4 item reminded. fuck u anooshe");
            this.f9925e = true;
            try {
                new Thread(new C37301(this)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: d */
    private List<C3744b> m13739d() {
        return C3749b.m13818a(this.f9927h);
    }

    /* renamed from: a */
    public View mo3459a(int i, View view, ViewGroup viewGroup) {
        m13738c(i);
        C3744b c3744b = (C3744b) m13739d().get(i);
        Context context = this.f9922b;
        if (view != null) {
            ((C3732a) view.getTag()).f9919a.m13890a(c3744b, this.f9927h, i);
            return view;
        }
        view = new C3773e(context);
        C3732a c3732a = new C3732a(view);
        c3732a.f9919a.m13888a(this.f9922b, f9921g, c3744b, viewGroup, this.f9927h, this.f9929j);
        c3732a.f9919a.setPos(i);
        m13744c().add(c3732a.f9919a);
        view.setTag(c3732a);
        this.f9926f.addOnPageChangeListener(c3732a.f9919a);
        return view;
    }

    /* renamed from: a */
    public void mo4194a() {
        notifyDataSetChanged();
    }

    /* renamed from: a */
    public void mo4195a(float f) {
        Iterator it = m13744c().iterator();
        while (it.hasNext()) {
            C3733c c3733c = (C3733c) it.next();
            if (c3733c != null) {
                c3733c.mo4195a(f);
            }
        }
    }

    /* renamed from: b */
    public void m13743b(int i) {
        this.f9929j = i;
    }

    /* renamed from: c */
    public ArrayList<C3733c> m13744c() {
        if (this.f9928i == null) {
            this.f9928i = new ArrayList();
        }
        return this.f9928i;
    }

    public int getCount() {
        int size;
        synchronized (f9920a) {
            size = m13739d().size();
        }
        return size;
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case 11:
                this.f9925e = false;
                return;
            case 12:
                this.f9925e = false;
                final C3744b[] c3744bArr = (C3744b[]) obj;
                this.f9922b.runOnUiThread(new Runnable(this) {
                    /* renamed from: b */
                    final /* synthetic */ C3734a f9918b;

                    public void run() {
                        C3749b.m13822a(this.f9918b.f9927h, this.f9918b.f9922b, c3744bArr);
                    }
                });
                return;
            default:
                return;
        }
    }
}
