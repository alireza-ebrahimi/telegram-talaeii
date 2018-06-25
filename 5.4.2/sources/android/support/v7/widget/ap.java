package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.view.ah;
import android.support.v4.widget.C0724s;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.view.menu.C0867s;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import java.lang.reflect.Method;

public class ap implements C0867s {
    /* renamed from: a */
    private static Method f2889a;
    /* renamed from: b */
    private static Method f2890b;
    /* renamed from: h */
    private static Method f2891h;
    /* renamed from: A */
    private OnItemSelectedListener f2892A;
    /* renamed from: B */
    private final C1022d f2893B;
    /* renamed from: C */
    private final C1021c f2894C;
    /* renamed from: D */
    private final C1019a f2895D;
    /* renamed from: E */
    private Runnable f2896E;
    /* renamed from: F */
    private final Rect f2897F;
    /* renamed from: G */
    private Rect f2898G;
    /* renamed from: H */
    private boolean f2899H;
    /* renamed from: c */
    aj f2900c;
    /* renamed from: d */
    int f2901d;
    /* renamed from: e */
    final C1023e f2902e;
    /* renamed from: f */
    final Handler f2903f;
    /* renamed from: g */
    PopupWindow f2904g;
    /* renamed from: i */
    private Context f2905i;
    /* renamed from: j */
    private ListAdapter f2906j;
    /* renamed from: k */
    private int f2907k;
    /* renamed from: l */
    private int f2908l;
    /* renamed from: m */
    private int f2909m;
    /* renamed from: n */
    private int f2910n;
    /* renamed from: o */
    private int f2911o;
    /* renamed from: p */
    private boolean f2912p;
    /* renamed from: q */
    private boolean f2913q;
    /* renamed from: r */
    private int f2914r;
    /* renamed from: s */
    private boolean f2915s;
    /* renamed from: t */
    private boolean f2916t;
    /* renamed from: u */
    private View f2917u;
    /* renamed from: v */
    private int f2918v;
    /* renamed from: w */
    private DataSetObserver f2919w;
    /* renamed from: x */
    private View f2920x;
    /* renamed from: y */
    private Drawable f2921y;
    /* renamed from: z */
    private OnItemClickListener f2922z;

    /* renamed from: android.support.v7.widget.ap$1 */
    class C10171 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ ap f2882a;

        C10171(ap apVar) {
            this.f2882a = apVar;
        }

        public void run() {
            View i = this.f2882a.m5492i();
            if (i != null && i.getWindowToken() != null) {
                this.f2882a.mo729a();
            }
        }
    }

    /* renamed from: android.support.v7.widget.ap$2 */
    class C10182 implements OnItemSelectedListener {
        /* renamed from: a */
        final /* synthetic */ ap f2883a;

        C10182(ap apVar) {
            this.f2883a = apVar;
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            if (i != -1) {
                aj ajVar = this.f2883a.f2900c;
                if (ajVar != null) {
                    ajVar.setListSelectionHidden(false);
                }
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: android.support.v7.widget.ap$a */
    private class C1019a implements Runnable {
        /* renamed from: a */
        final /* synthetic */ ap f2884a;

        C1019a(ap apVar) {
            this.f2884a = apVar;
        }

        public void run() {
            this.f2884a.m5497m();
        }
    }

    /* renamed from: android.support.v7.widget.ap$b */
    private class C1020b extends DataSetObserver {
        /* renamed from: a */
        final /* synthetic */ ap f2885a;

        C1020b(ap apVar) {
            this.f2885a = apVar;
        }

        public void onChanged() {
            if (this.f2885a.mo739d()) {
                this.f2885a.mo729a();
            }
        }

        public void onInvalidated() {
            this.f2885a.mo736c();
        }
    }

    /* renamed from: android.support.v7.widget.ap$c */
    private class C1021c implements OnScrollListener {
        /* renamed from: a */
        final /* synthetic */ ap f2886a;

        C1021c(ap apVar) {
            this.f2886a = apVar;
        }

        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        }

        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (i == 1 && !this.f2886a.m5498n() && this.f2886a.f2904g.getContentView() != null) {
                this.f2886a.f2903f.removeCallbacks(this.f2886a.f2902e);
                this.f2886a.f2902e.run();
            }
        }
    }

    /* renamed from: android.support.v7.widget.ap$d */
    private class C1022d implements OnTouchListener {
        /* renamed from: a */
        final /* synthetic */ ap f2887a;

        C1022d(ap apVar) {
            this.f2887a = apVar;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 0 && this.f2887a.f2904g != null && this.f2887a.f2904g.isShowing() && x >= 0 && x < this.f2887a.f2904g.getWidth() && y >= 0 && y < this.f2887a.f2904g.getHeight()) {
                this.f2887a.f2903f.postDelayed(this.f2887a.f2902e, 250);
            } else if (action == 1) {
                this.f2887a.f2903f.removeCallbacks(this.f2887a.f2902e);
            }
            return false;
        }
    }

    /* renamed from: android.support.v7.widget.ap$e */
    private class C1023e implements Runnable {
        /* renamed from: a */
        final /* synthetic */ ap f2888a;

        C1023e(ap apVar) {
            this.f2888a = apVar;
        }

        public void run() {
            if (this.f2888a.f2900c != null && ah.m2769I(this.f2888a.f2900c) && this.f2888a.f2900c.getCount() > this.f2888a.f2900c.getChildCount() && this.f2888a.f2900c.getChildCount() <= this.f2888a.f2901d) {
                this.f2888a.f2904g.setInputMethodMode(2);
                this.f2888a.mo729a();
            }
        }
    }

    static {
        try {
            f2889a = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", new Class[]{Boolean.TYPE});
        } catch (NoSuchMethodException e) {
            Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
        }
        try {
            f2890b = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", new Class[]{View.class, Integer.TYPE, Boolean.TYPE});
        } catch (NoSuchMethodException e2) {
            Log.i("ListPopupWindow", "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
        try {
            f2891h = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", new Class[]{Rect.class});
        } catch (NoSuchMethodException e3) {
            Log.i("ListPopupWindow", "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
        }
    }

    public ap(Context context) {
        this(context, null, C0738a.listPopupWindowStyle);
    }

    public ap(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ap(Context context, AttributeSet attributeSet, int i, int i2) {
        this.f2907k = -2;
        this.f2908l = -2;
        this.f2911o = 1002;
        this.f2913q = true;
        this.f2914r = 0;
        this.f2915s = false;
        this.f2916t = false;
        this.f2901d = Integer.MAX_VALUE;
        this.f2918v = 0;
        this.f2902e = new C1023e(this);
        this.f2893B = new C1022d(this);
        this.f2894C = new C1021c(this);
        this.f2895D = new C1019a(this);
        this.f2897F = new Rect();
        this.f2905i = context;
        this.f2903f = new Handler(context.getMainLooper());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0747j.ListPopupWindow, i, i2);
        this.f2909m = obtainStyledAttributes.getDimensionPixelOffset(C0747j.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        this.f2910n = obtainStyledAttributes.getDimensionPixelOffset(C0747j.ListPopupWindow_android_dropDownVerticalOffset, 0);
        if (this.f2910n != 0) {
            this.f2912p = true;
        }
        obtainStyledAttributes.recycle();
        if (VERSION.SDK_INT >= 11) {
            this.f2904g = new C1073p(context, attributeSet, i, i2);
        } else {
            this.f2904g = new C1073p(context, attributeSet, i);
        }
        this.f2904g.setInputMethodMode(1);
    }

    /* renamed from: a */
    private int m5466a(View view, int i, boolean z) {
        if (f2890b != null) {
            try {
                return ((Integer) f2890b.invoke(this.f2904g, new Object[]{view, Integer.valueOf(i), Boolean.valueOf(z)})).intValue();
            } catch (Exception e) {
                Log.i("ListPopupWindow", "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
        }
        return this.f2904g.getMaxAvailableHeight(view, i);
    }

    /* renamed from: b */
    private void mo1013b() {
        if (this.f2917u != null) {
            ViewParent parent = this.f2917u.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.f2917u);
            }
        }
    }

    /* renamed from: b */
    private void mo943b(boolean z) {
        if (f2889a != null) {
            try {
                f2889a.invoke(this.f2904g, new Object[]{Boolean.valueOf(z)});
            } catch (Exception e) {
                Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        }
    }

    /* renamed from: f */
    private int mo1014f() {
        int i;
        int i2;
        int i3;
        int i4;
        boolean z = true;
        LayoutParams layoutParams;
        View view;
        if (this.f2900c == null) {
            Context context = this.f2905i;
            this.f2896E = new C10171(this);
            this.f2900c = mo942a(context, !this.f2899H);
            if (this.f2921y != null) {
                this.f2900c.setSelector(this.f2921y);
            }
            this.f2900c.setAdapter(this.f2906j);
            this.f2900c.setOnItemClickListener(this.f2922z);
            this.f2900c.setFocusable(true);
            this.f2900c.setFocusableInTouchMode(true);
            this.f2900c.setOnItemSelectedListener(new C10182(this));
            this.f2900c.setOnScrollListener(this.f2894C);
            if (this.f2892A != null) {
                this.f2900c.setOnItemSelectedListener(this.f2892A);
            }
            View view2 = this.f2900c;
            View view3 = this.f2917u;
            if (view3 != null) {
                View linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(1);
                ViewGroup.LayoutParams layoutParams2 = new LayoutParams(-1, 0, 1.0f);
                switch (this.f2918v) {
                    case 0:
                        linearLayout.addView(view3);
                        linearLayout.addView(view2, layoutParams2);
                        break;
                    case 1:
                        linearLayout.addView(view2, layoutParams2);
                        linearLayout.addView(view3);
                        break;
                    default:
                        Log.e("ListPopupWindow", "Invalid hint position " + this.f2918v);
                        break;
                }
                if (this.f2908l >= 0) {
                    i = this.f2908l;
                    i2 = Integer.MIN_VALUE;
                } else {
                    i2 = 0;
                    i = 0;
                }
                view3.measure(MeasureSpec.makeMeasureSpec(i, i2), 0);
                layoutParams = (LayoutParams) view3.getLayoutParams();
                i2 = layoutParams.bottomMargin + (view3.getMeasuredHeight() + layoutParams.topMargin);
                view = linearLayout;
            } else {
                view = view2;
                i2 = 0;
            }
            this.f2904g.setContentView(view);
            i3 = i2;
        } else {
            ViewGroup viewGroup = (ViewGroup) this.f2904g.getContentView();
            view = this.f2917u;
            if (view != null) {
                layoutParams = (LayoutParams) view.getLayoutParams();
                i3 = layoutParams.bottomMargin + (view.getMeasuredHeight() + layoutParams.topMargin);
            } else {
                i3 = 0;
            }
        }
        Drawable background = this.f2904g.getBackground();
        if (background != null) {
            background.getPadding(this.f2897F);
            i2 = this.f2897F.top + this.f2897F.bottom;
            if (this.f2912p) {
                i4 = i2;
            } else {
                this.f2910n = -this.f2897F.top;
                i4 = i2;
            }
        } else {
            this.f2897F.setEmpty();
            i4 = 0;
        }
        if (this.f2904g.getInputMethodMode() != 2) {
            z = false;
        }
        i = m5466a(m5492i(), this.f2910n, z);
        if (this.f2915s || this.f2907k == -1) {
            return i + i4;
        }
        int makeMeasureSpec;
        switch (this.f2908l) {
            case -2:
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.f2905i.getResources().getDisplayMetrics().widthPixels - (this.f2897F.left + this.f2897F.right), Integer.MIN_VALUE);
                break;
            case -1:
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.f2905i.getResources().getDisplayMetrics().widthPixels - (this.f2897F.left + this.f2897F.right), 1073741824);
                break;
            default:
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.f2908l, 1073741824);
                break;
        }
        i2 = this.f2900c.m5434a(makeMeasureSpec, 0, -1, i - i3, -1);
        if (i2 > 0) {
            i3 += (this.f2900c.getPaddingTop() + this.f2900c.getPaddingBottom()) + i4;
        }
        return i2 + i3;
    }

    /* renamed from: a */
    aj mo942a(Context context, boolean z) {
        return new aj(context, z);
    }

    /* renamed from: a */
    public void mo729a() {
        boolean z = true;
        boolean z2 = false;
        int i = -1;
        int f = mo1014f();
        boolean n = m5498n();
        C0724s.m3532a(this.f2904g, this.f2911o);
        if (this.f2904g.isShowing()) {
            int i2;
            int width = this.f2908l == -1 ? -1 : this.f2908l == -2 ? m5492i().getWidth() : this.f2908l;
            if (this.f2907k == -1) {
                if (!n) {
                    f = -1;
                }
                if (n) {
                    this.f2904g.setWidth(this.f2908l == -1 ? -1 : 0);
                    this.f2904g.setHeight(0);
                    i2 = f;
                } else {
                    this.f2904g.setWidth(this.f2908l == -1 ? -1 : 0);
                    this.f2904g.setHeight(-1);
                    i2 = f;
                }
            } else {
                i2 = this.f2907k == -2 ? f : this.f2907k;
            }
            PopupWindow popupWindow = this.f2904g;
            if (!(this.f2916t || this.f2915s)) {
                z2 = true;
            }
            popupWindow.setOutsideTouchable(z2);
            popupWindow = this.f2904g;
            View i3 = m5492i();
            f = this.f2909m;
            int i4 = this.f2910n;
            if (width < 0) {
                width = -1;
            }
            if (i2 >= 0) {
                i = i2;
            }
            popupWindow.update(i3, f, i4, width, i);
            return;
        }
        int width2 = this.f2908l == -1 ? -1 : this.f2908l == -2 ? m5492i().getWidth() : this.f2908l;
        if (this.f2907k == -1) {
            f = -1;
        } else if (this.f2907k != -2) {
            f = this.f2907k;
        }
        this.f2904g.setWidth(width2);
        this.f2904g.setHeight(f);
        mo943b(true);
        popupWindow = this.f2904g;
        if (this.f2916t || this.f2915s) {
            z = false;
        }
        popupWindow.setOutsideTouchable(z);
        this.f2904g.setTouchInterceptor(this.f2893B);
        if (f2891h != null) {
            try {
                f2891h.invoke(this.f2904g, new Object[]{this.f2898G});
            } catch (Throwable e) {
                Log.e("ListPopupWindow", "Could not invoke setEpicenterBounds on PopupWindow", e);
            }
        }
        C0724s.m3533a(this.f2904g, m5492i(), this.f2909m, this.f2910n, this.f2914r);
        this.f2900c.setSelection(-1);
        if (!this.f2899H || this.f2900c.isInTouchMode()) {
            m5497m();
        }
        if (!this.f2899H) {
            this.f2903f.post(this.f2895D);
        }
    }

    /* renamed from: a */
    public void m5472a(int i) {
        this.f2918v = i;
    }

    /* renamed from: a */
    public void m5473a(Rect rect) {
        this.f2898G = rect;
    }

    /* renamed from: a */
    public void m5474a(Drawable drawable) {
        this.f2904g.setBackgroundDrawable(drawable);
    }

    /* renamed from: a */
    public void m5475a(OnItemClickListener onItemClickListener) {
        this.f2922z = onItemClickListener;
    }

    /* renamed from: a */
    public void mo1012a(ListAdapter listAdapter) {
        if (this.f2919w == null) {
            this.f2919w = new C1020b(this);
        } else if (this.f2906j != null) {
            this.f2906j.unregisterDataSetObserver(this.f2919w);
        }
        this.f2906j = listAdapter;
        if (this.f2906j != null) {
            listAdapter.registerDataSetObserver(this.f2919w);
        }
        if (this.f2900c != null) {
            this.f2900c.setAdapter(this.f2906j);
        }
    }

    /* renamed from: a */
    public void m5477a(OnDismissListener onDismissListener) {
        this.f2904g.setOnDismissListener(onDismissListener);
    }

    /* renamed from: a */
    public void m5478a(boolean z) {
        this.f2899H = z;
        this.f2904g.setFocusable(z);
    }

    /* renamed from: b */
    public void m5479b(int i) {
        this.f2904g.setAnimationStyle(i);
    }

    /* renamed from: b */
    public void m5480b(View view) {
        this.f2920x = view;
    }

    /* renamed from: c */
    public void mo736c() {
        this.f2904g.dismiss();
        mo1013b();
        this.f2904g.setContentView(null);
        this.f2900c = null;
        this.f2903f.removeCallbacks(this.f2902e);
    }

    /* renamed from: c */
    public void m5482c(int i) {
        this.f2909m = i;
    }

    /* renamed from: d */
    public void m5483d(int i) {
        this.f2910n = i;
        this.f2912p = true;
    }

    /* renamed from: d */
    public boolean mo739d() {
        return this.f2904g.isShowing();
    }

    /* renamed from: e */
    public ListView mo740e() {
        return this.f2900c;
    }

    /* renamed from: e */
    public void m5486e(int i) {
        this.f2914r = i;
    }

    /* renamed from: f */
    public void m5487f(int i) {
        this.f2908l = i;
    }

    /* renamed from: g */
    public void m5488g(int i) {
        Drawable background = this.f2904g.getBackground();
        if (background != null) {
            background.getPadding(this.f2897F);
            this.f2908l = (this.f2897F.left + this.f2897F.right) + i;
            return;
        }
        m5487f(i);
    }

    /* renamed from: g */
    public boolean m5489g() {
        return this.f2899H;
    }

    /* renamed from: h */
    public Drawable m5490h() {
        return this.f2904g.getBackground();
    }

    /* renamed from: h */
    public void m5491h(int i) {
        this.f2904g.setInputMethodMode(i);
    }

    /* renamed from: i */
    public View m5492i() {
        return this.f2920x;
    }

    /* renamed from: i */
    public void m5493i(int i) {
        aj ajVar = this.f2900c;
        if (mo739d() && ajVar != null) {
            ajVar.setListSelectionHidden(false);
            ajVar.setSelection(i);
            if (VERSION.SDK_INT >= 11 && ajVar.getChoiceMode() != 0) {
                ajVar.setItemChecked(i, true);
            }
        }
    }

    /* renamed from: j */
    public int m5494j() {
        return this.f2909m;
    }

    /* renamed from: k */
    public int m5495k() {
        return !this.f2912p ? 0 : this.f2910n;
    }

    /* renamed from: l */
    public int m5496l() {
        return this.f2908l;
    }

    /* renamed from: m */
    public void m5497m() {
        aj ajVar = this.f2900c;
        if (ajVar != null) {
            ajVar.setListSelectionHidden(true);
            ajVar.requestLayout();
        }
    }

    /* renamed from: n */
    public boolean m5498n() {
        return this.f2904g.getInputMethodMode() == 2;
    }
}
