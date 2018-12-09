package org.telegram.customization.util.view.slideshow;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.C0659t;
import android.support.v4.view.ViewPager;
import android.support.v4.view.aa;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class AutoScrollViewPager extends ViewPager {
    /* renamed from: a */
    private long f9777a = 1500;
    /* renamed from: b */
    private int f9778b = 1;
    /* renamed from: c */
    private int f9779c = 0;
    /* renamed from: d */
    private boolean f9780d = true;
    /* renamed from: e */
    private boolean f9781e = true;
    /* renamed from: f */
    private boolean f9782f = true;
    /* renamed from: g */
    private double f9783g = 1.0d;
    /* renamed from: h */
    private double f9784h = 1.0d;
    /* renamed from: i */
    private Handler f9785i;
    /* renamed from: j */
    private boolean f9786j = false;
    /* renamed from: k */
    private boolean f9787k = false;
    /* renamed from: l */
    private float f9788l = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: m */
    private float f9789m = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: n */
    private float f9790n = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: o */
    private float f9791o = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: p */
    private float f9792p = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: q */
    private C2956a f9793q = null;

    /* renamed from: org.telegram.customization.util.view.slideshow.AutoScrollViewPager$a */
    private static class C2953a extends Handler {
        /* renamed from: a */
        private final WeakReference<AutoScrollViewPager> f9776a;

        public C2953a(AutoScrollViewPager autoScrollViewPager) {
            this.f9776a = new WeakReference(autoScrollViewPager);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 0:
                    AutoScrollViewPager autoScrollViewPager = (AutoScrollViewPager) this.f9776a.get();
                    if (autoScrollViewPager != null) {
                        autoScrollViewPager.f9793q.m13640a(autoScrollViewPager.f9783g);
                        autoScrollViewPager.m13633c();
                        autoScrollViewPager.f9793q.m13640a(autoScrollViewPager.f9784h);
                        autoScrollViewPager.m13624a(autoScrollViewPager.f9777a + ((long) autoScrollViewPager.f9793q.getDuration()));
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public AutoScrollViewPager(Context context) {
        super(context);
        m13629d();
    }

    public AutoScrollViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m13629d();
    }

    /* renamed from: a */
    private void m13624a(long j) {
        this.f9785i.removeMessages(0);
        this.f9785i.sendEmptyMessageDelayed(0, j);
    }

    /* renamed from: d */
    private void m13629d() {
        this.f9785i = new C2953a(this);
        m13630e();
    }

    /* renamed from: e */
    private void m13630e() {
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            Field declaredField2 = ViewPager.class.getDeclaredField("sInterpolator");
            declaredField2.setAccessible(true);
            this.f9793q = new C2956a(getContext(), (Interpolator) declaredField2.get(null));
            declaredField.set(this, this.f9793q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public void m13631a() {
        this.f9786j = true;
        m13624a((long) (((double) this.f9777a) + ((((double) this.f9793q.getDuration()) / this.f9783g) * this.f9784h)));
    }

    /* renamed from: b */
    public void m13632b() {
        this.f9786j = false;
        this.f9785i.removeMessages(0);
    }

    /* renamed from: c */
    public void m13633c() {
        aa adapter = getAdapter();
        int currentItem = getCurrentItem();
        if (adapter != null) {
            int count = adapter.getCount();
            if (count > 1) {
                int i = this.f9778b == 0 ? currentItem - 1 : currentItem + 1;
                if (i < 0) {
                    if (this.f9780d) {
                        setCurrentItem(count - 1, this.f9782f);
                    }
                } else if (i != count) {
                    setCurrentItem(i, true);
                } else if (this.f9780d) {
                    setCurrentItem(0, this.f9782f);
                }
            }
        }
    }

    public boolean canScrollVertically(int i) {
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int a = C0659t.m3205a(motionEvent);
        this.f9791o = motionEvent.getY();
        if (this.f9781e) {
            if (a == 0 && this.f9786j) {
                this.f9787k = true;
                m13632b();
            } else if (motionEvent.getAction() == 1 && this.f9787k && this.f9786j) {
                m13631a();
            }
        }
        if (motionEvent.getAction() == 0) {
            this.f9792p = this.f9791o;
            this.f9790n = motionEvent.getX();
        } else if (this.f9787k && (motionEvent.getAction() == 1 || (motionEvent.getAction() == 3 && this.f9786j))) {
            m13631a();
        }
        if (Math.abs(this.f9792p - this.f9791o) > Math.abs(this.f9790n - motionEvent.getX())) {
            getParent().requestDisallowInterceptTouchEvent(false);
            return super.dispatchTouchEvent(motionEvent);
        }
        if (Math.abs(this.f9792p - this.f9791o) > 5.0f && 5.0f < Math.abs(this.f9790n - motionEvent.getX())) {
            this.f9790n = motionEvent.getX();
            this.f9792p = this.f9791o;
        }
        if (this.f9779c == 2 || this.f9779c == 1) {
            this.f9788l = motionEvent.getX();
            if (motionEvent.getAction() == 0) {
                this.f9789m = this.f9788l;
            }
            int currentItem = getCurrentItem();
            aa adapter = getAdapter();
            a = adapter == null ? 0 : adapter.getCount();
            if ((currentItem == 0 && this.f9789m <= this.f9788l) || (currentItem == a - 1 && this.f9789m >= this.f9788l)) {
                if (this.f9779c == 2) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (a > 1) {
                        setCurrentItem((a - currentItem) - 1, this.f9782f);
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.dispatchTouchEvent(motionEvent);
            }
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(motionEvent);
    }

    public int getDirection() {
        return this.f9778b == 0 ? 0 : 1;
    }

    public long getInterval() {
        return this.f9777a;
    }

    public int getSlideBorderMode() {
        return this.f9779c;
    }

    protected void onMeasure(int i, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            View childAt = getChildAt(i4);
            childAt.measure(i, MeasureSpec.makeMeasureSpec(0, 0));
            int measuredHeight = childAt.getMeasuredHeight();
            if (measuredHeight > i3) {
                i3 = measuredHeight;
            }
        }
        super.onMeasure(i, MeasureSpec.makeMeasureSpec(i3, 1073741824));
    }

    public void setAutoScrollDurationFactor(double d) {
        this.f9783g = d;
    }

    public void setBorderAnimation(boolean z) {
        this.f9782f = z;
    }

    public void setCycle(boolean z) {
        this.f9780d = z;
    }

    public void setDirection(int i) {
        this.f9778b = i;
    }

    public void setInterval(long j) {
        this.f9777a = j;
    }

    public void setSlideBorderMode(int i) {
        this.f9779c = i;
    }

    public void setStopScrollWhenTouch(boolean z) {
        this.f9781e = z;
    }

    public void setSwipeScrollDurationFactor(double d) {
        this.f9784h = d;
    }
}
