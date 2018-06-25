package org.telegram.customization.p156a;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0188f;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.telegram.customization.dynamicadapter.data.SlsBaseMessage;
import org.telegram.customization.p153c.C2671d;
import org.telegram.customization.util.view.C2922a;
import org.telegram.customization.util.view.p158c.C2650b;

/* renamed from: org.telegram.customization.a.g */
public class C2651g extends C2650b {
    /* renamed from: e */
    private static LayoutInflater f8845e = null;
    /* renamed from: a */
    Activity f8846a;
    /* renamed from: b */
    Context f8847b;
    /* renamed from: c */
    ViewPager f8848c;
    /* renamed from: d */
    private final int f8849d;
    /* renamed from: f */
    private boolean f8850f = true;
    /* renamed from: g */
    private int f8851g = 0;

    /* renamed from: org.telegram.customization.a.g$1 */
    class C26481 implements C0188f {
        /* renamed from: a */
        final /* synthetic */ C2651g f8842a;

        C26481(C2651g c2651g) {
            this.f8842a = c2651g;
        }

        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
        }
    }

    /* renamed from: org.telegram.customization.a.g$a */
    private static class C2649a {
        /* renamed from: a */
        final C2922a f8843a;

        public C2649a(View view) {
            this.f8843a = (C2922a) view;
        }
    }

    public C2651g(ViewPager viewPager, Activity activity, Context context, int i) {
        this.f8848c = viewPager;
        this.f8846a = activity;
        this.f8847b = context;
        this.f8849d = i;
        f8845e = (LayoutInflater) this.f8847b.getSystemService("layout_inflater");
        this.f8848c.addOnPageChangeListener(new C26481(this));
    }

    /* renamed from: a */
    public Bundle m12496a() {
        int currentPosition;
        boolean z;
        boolean z2;
        Exception exception;
        Bundle bundle = new Bundle();
        try {
            C2649a c2649a = (C2649a) this.f8848c.getFocusedChild().getTag();
            boolean isPlaying = c2649a.f8843a.getVideoView().isPlaying();
            try {
                currentPosition = c2649a.f8843a.getVideoView().getCurrentPosition();
                z = isPlaying;
            } catch (Exception e) {
                Exception exception2 = e;
                z2 = isPlaying;
                exception = exception2;
                exception.printStackTrace();
                z = z2;
                currentPosition = 0;
                bundle.putBoolean("MEDIA_PLAYING", z);
                bundle.putInt("MEDIA_POSITION", currentPosition);
                return bundle;
            }
        } catch (Exception e2) {
            exception = e2;
            z2 = false;
            exception.printStackTrace();
            z = z2;
            currentPosition = 0;
            bundle.putBoolean("MEDIA_PLAYING", z);
            bundle.putInt("MEDIA_POSITION", currentPosition);
            return bundle;
        }
        bundle.putBoolean("MEDIA_PLAYING", z);
        bundle.putInt("MEDIA_POSITION", currentPosition);
        return bundle;
    }

    /* renamed from: a */
    public View mo3459a(int i, View view, ViewGroup viewGroup) {
        C2649a c2649a;
        SlsBaseMessage a = C2671d.m12539a(this.f8849d, i);
        if (view == null) {
            view = new C2922a(this.f8847b);
            C2649a c2649a2 = new C2649a(view);
            view.setTag(c2649a2);
            c2649a = c2649a2;
        } else {
            c2649a = (C2649a) view.getTag();
        }
        c2649a.f8843a.m13529a(this.f8847b, this.f8846a, f8845e, a, C2671d.m12552d(this.f8849d), i);
        this.f8848c.addOnPageChangeListener(c2649a.f8843a);
        if (a.getMessage().getMediaType() == 8 || a.getMessage().getMediaType() == 9 || a.getMessage().getMediaType() == 6) {
            try {
                if (this.f8848c.getCurrentItem() == i) {
                    if (this.f8850f) {
                        this.f8850f = false;
                        c2649a.f8843a.m13530a(a.getMessage().getFileUrl());
                    }
                    c2649a.f8843a.setPlayTime(this.f8851g);
                    this.f8851g = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    /* renamed from: a */
    public void m12498a(Bundle bundle) {
        try {
            this.f8850f = bundle.getBoolean("MEDIA_PLAYING", false);
            this.f8851g = bundle.getInt("MEDIA_POSITION", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return C2671d.m12552d(this.f8849d);
    }
}
