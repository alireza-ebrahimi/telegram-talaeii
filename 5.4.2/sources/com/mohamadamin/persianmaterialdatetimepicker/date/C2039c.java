package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import com.mohamadamin.persianmaterialdatetimepicker.C2030d;
import com.mohamadamin.persianmaterialdatetimepicker.date.C2036b.C2034a;
import com.mohamadamin.persianmaterialdatetimepicker.date.C2042d.C2040a;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2017a;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2018b;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.c */
public abstract class C2039c extends ListView implements OnScrollListener, C2034a {
    /* renamed from: a */
    public static int f5992a = -1;
    /* renamed from: b */
    protected int f5993b = 6;
    /* renamed from: c */
    protected boolean f5994c = false;
    /* renamed from: d */
    protected int f5995d = 7;
    /* renamed from: e */
    protected float f5996e = 1.0f;
    /* renamed from: f */
    protected Context f5997f;
    /* renamed from: g */
    protected Handler f5998g;
    /* renamed from: h */
    protected C2040a f5999h = new C2040a();
    /* renamed from: i */
    protected C2042d f6000i;
    /* renamed from: j */
    protected C2040a f6001j = new C2040a();
    /* renamed from: k */
    protected int f6002k;
    /* renamed from: l */
    protected long f6003l;
    /* renamed from: m */
    protected int f6004m = 0;
    /* renamed from: n */
    protected int f6005n = 0;
    /* renamed from: o */
    protected C2038a f6006o = new C2038a(this);
    /* renamed from: p */
    private C2031a f6007p;
    /* renamed from: q */
    private boolean f6008q;

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.c$a */
    protected class C2038a implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C2039c f5990a;
        /* renamed from: b */
        private int f5991b;

        protected C2038a(C2039c c2039c) {
            this.f5990a = c2039c;
        }

        /* renamed from: a */
        public void m9157a(AbsListView absListView, int i) {
            this.f5990a.f5998g.removeCallbacks(this);
            this.f5991b = i;
            this.f5990a.f5998g.postDelayed(this, 40);
        }

        public void run() {
            int i = 1;
            this.f5990a.f6005n = this.f5991b;
            if (Log.isLoggable("MonthFragment", 3)) {
                Log.d("MonthFragment", "new scroll state: " + this.f5991b + " old state: " + this.f5990a.f6004m);
            }
            if (this.f5991b != 0 || this.f5990a.f6004m == 0 || this.f5990a.f6004m == 1) {
                this.f5990a.f6004m = this.f5991b;
                return;
            }
            this.f5990a.f6004m = this.f5991b;
            View childAt = this.f5990a.getChildAt(0);
            int i2 = 0;
            while (childAt != null && childAt.getBottom() <= 0) {
                i2++;
                childAt = this.f5990a.getChildAt(i2);
            }
            if (childAt != null) {
                i2 = this.f5990a.getFirstVisiblePosition();
                int lastVisiblePosition = this.f5990a.getLastVisiblePosition();
                if (i2 == 0 || lastVisiblePosition == this.f5990a.getCount() - 1) {
                    i = 0;
                }
                int top = childAt.getTop();
                int bottom = childAt.getBottom();
                i2 = this.f5990a.getHeight() / 2;
                if (i != 0 && top < C2039c.f5992a) {
                    if (bottom > i2) {
                        this.f5990a.smoothScrollBy(top, Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
                    } else {
                        this.f5990a.smoothScrollBy(bottom, Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
                    }
                }
            }
        }
    }

    public C2039c(Context context, C2031a c2031a) {
        super(context);
        m9164a(context);
        setController(c2031a);
    }

    /* renamed from: a */
    private boolean m9158a(C2040a c2040a) {
        if (c2040a == null) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((childAt instanceof C2044e) && ((C2044e) childAt).m9199a(c2040a)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    private static String m9159b(C2040a c2040a) {
        C2018b c2018b = new C2018b();
        c2018b.m9094a(c2040a.f6009a, c2040a.f6010b, c2040a.f6011c);
        return ((TtmlNode.ANONYMOUS_REGION_ID + c2018b.m9097d()) + " ") + c2018b.m9095b();
    }

    /* renamed from: d */
    private C2040a m9160d() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof C2044e) {
                C2040a accessibilityFocus = ((C2044e) childAt).getAccessibilityFocus();
                if (accessibilityFocus != null) {
                    if (VERSION.SDK_INT != 17) {
                        return accessibilityFocus;
                    }
                    ((C2044e) childAt).m9206d();
                    return accessibilityFocus;
                }
            }
        }
        return null;
    }

    /* renamed from: a */
    public abstract C2042d mo3084a(Context context, C2031a c2031a);

    /* renamed from: a */
    public void mo3074a() {
        m9165a(this.f6007p.mo3061a(), false, true, true);
    }

    /* renamed from: a */
    public void m9163a(final int i) {
        clearFocus();
        post(new Runnable(this) {
            /* renamed from: b */
            final /* synthetic */ C2039c f5989b;

            public void run() {
                this.f5989b.setSelection(i);
            }
        });
        onScrollStateChanged(this, 0);
    }

    /* renamed from: a */
    public void m9164a(Context context) {
        this.f5998g = new Handler();
        setLayoutParams(new LayoutParams(-1, -1));
        setDrawSelectorOnTop(false);
        this.f5997f = context;
        m9167c();
    }

    /* renamed from: a */
    public boolean m9165a(C2040a c2040a, boolean z, boolean z2, boolean z3) {
        View childAt;
        if (z2) {
            this.f5999h.m9170a(c2040a);
        }
        this.f6001j.m9170a(c2040a);
        int f = ((c2040a.f6009a - this.f6007p.mo3069f()) * 12) + c2040a.f6010b;
        int i = 0;
        while (true) {
            int i2 = i + 1;
            childAt = getChildAt(i);
            if (childAt != null) {
                int top = childAt.getTop();
                if (Log.isLoggable("MonthFragment", 3)) {
                    Log.d("MonthFragment", "child at " + (i2 - 1) + " has top " + top);
                }
                if (top >= 0) {
                    break;
                }
                i = i2;
            } else {
                break;
            }
        }
        i = childAt != null ? getPositionForView(childAt) : 0;
        if (z2) {
            this.f6000i.m9175a(this.f5999h);
        }
        if (Log.isLoggable("MonthFragment", 3)) {
            Log.d("MonthFragment", "GoTo position " + f);
        }
        if (f != i || z3) {
            setMonthDisplayed(this.f6001j);
            this.f6004m = 2;
            if (z) {
                smoothScrollToPositionFromTop(f, f5992a, Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
                return true;
            }
            m9163a(f);
            return false;
        } else if (!z2) {
            return false;
        } else {
            setMonthDisplayed(this.f5999h);
            return false;
        }
    }

    /* renamed from: b */
    protected void m9166b() {
        if (this.f6000i == null) {
            this.f6000i = mo3084a(getContext(), this.f6007p);
        } else {
            this.f6000i.m9175a(this.f5999h);
        }
        setAdapter(this.f6000i);
    }

    /* renamed from: c */
    protected void m9167c() {
        setCacheColorHint(0);
        setDivider(null);
        setItemsCanFocus(true);
        setFastScrollEnabled(false);
        setVerticalScrollBarEnabled(false);
        setOnScrollListener(this);
        setFadingEdgeLength(0);
        setFriction(ViewConfiguration.getScrollFriction() * this.f5996e);
    }

    public int getMostVisiblePosition() {
        int firstVisiblePosition = getFirstVisiblePosition();
        int height = getHeight();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i < height) {
            View childAt = getChildAt(i2);
            if (childAt == null) {
                break;
            }
            int bottom = childAt.getBottom();
            i = Math.min(bottom, height) - Math.max(0, childAt.getTop());
            if (i > i4) {
                i3 = i2;
            } else {
                i = i4;
            }
            i2++;
            i4 = i;
            i = bottom;
        }
        return i3 + firstVisiblePosition;
    }

    protected void layoutChildren() {
        C2040a d = m9160d();
        super.layoutChildren();
        if (this.f6008q) {
            this.f6008q = false;
        } else {
            m9158a(d);
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setItemCount(-1);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (VERSION.SDK_INT >= 21) {
            accessibilityNodeInfo.addAction(AccessibilityAction.ACTION_SCROLL_BACKWARD);
            accessibilityNodeInfo.addAction(AccessibilityAction.ACTION_SCROLL_FORWARD);
            return;
        }
        accessibilityNodeInfo.addAction(4096);
        accessibilityNodeInfo.addAction(MessagesController.UPDATE_MASK_CHANNEL);
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        C2044e c2044e = (C2044e) absListView.getChildAt(0);
        if (c2044e != null) {
            this.f6003l = (long) ((absListView.getFirstVisiblePosition() * c2044e.getHeight()) - c2044e.getBottom());
            this.f6004m = this.f6005n;
        }
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
        this.f6006o.m9157a(absListView, i);
    }

    @SuppressLint({"NewApi"})
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        if (i != 4096 && i != MessagesController.UPDATE_MASK_CHANNEL) {
            return super.performAccessibilityAction(i, bundle);
        }
        int firstVisiblePosition = getFirstVisiblePosition();
        C2040a c2040a = new C2040a((firstVisiblePosition / 12) + this.f6007p.mo3069f(), firstVisiblePosition % 12, 1);
        if (i == 4096) {
            c2040a.f6010b++;
            if (c2040a.f6010b == 12) {
                c2040a.f6010b = 0;
                c2040a.f6009a++;
            }
        } else if (i == MessagesController.UPDATE_MASK_CHANNEL) {
            View childAt = getChildAt(0);
            if (childAt != null && childAt.getTop() >= -1) {
                c2040a.f6010b--;
                if (c2040a.f6010b == -1) {
                    c2040a.f6010b = 11;
                    c2040a.f6009a--;
                }
            }
        }
        C2030d.m9116a((View) this, C2017a.m9087a(C2039c.m9159b(c2040a)));
        m9165a(c2040a, true, false, true);
        this.f6008q = true;
        return true;
    }

    public void setController(C2031a c2031a) {
        this.f6007p = c2031a;
        this.f6007p.mo3064a((C2034a) this);
        m9166b();
        mo3074a();
    }

    protected void setMonthDisplayed(C2040a c2040a) {
        this.f6002k = c2040a.f6010b;
        invalidateViews();
    }
}
