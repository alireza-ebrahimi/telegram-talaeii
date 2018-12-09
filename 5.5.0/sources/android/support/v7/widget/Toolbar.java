package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.p014d.C0085g;
import android.support.v4.p014d.C0437f;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.C0625f;
import android.support.v4.view.C0645o;
import android.support.v4.view.C0652q;
import android.support.v4.view.C0659t;
import android.support.v4.view.ah;
import android.support.v7.app.C0765a.C0762a;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.p027c.p028a.C0825b;
import android.support.v7.view.C0843c;
import android.support.v7.view.C0850g;
import android.support.v7.view.menu.C0859o;
import android.support.v7.view.menu.C0859o.C0794a;
import android.support.v7.view.menu.C0873h;
import android.support.v7.view.menu.C0873h.C0777a;
import android.support.v7.view.menu.C0876j;
import android.support.v7.view.menu.C0890u;
import android.support.v7.widget.ActionMenuView.C0902e;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import org.telegram.ui.ActionBar.Theme;

public class Toolbar extends ViewGroup {
    /* renamed from: A */
    private int f2673A;
    /* renamed from: B */
    private int f2674B;
    /* renamed from: C */
    private boolean f2675C;
    /* renamed from: D */
    private boolean f2676D;
    /* renamed from: E */
    private final ArrayList<View> f2677E;
    /* renamed from: F */
    private final ArrayList<View> f2678F;
    /* renamed from: G */
    private final int[] f2679G;
    /* renamed from: H */
    private final C0902e f2680H;
    /* renamed from: I */
    private bl f2681I;
    /* renamed from: J */
    private C1052d f2682J;
    /* renamed from: K */
    private C0973a f2683K;
    /* renamed from: L */
    private C0794a f2684L;
    /* renamed from: M */
    private C0777a f2685M;
    /* renamed from: N */
    private boolean f2686N;
    /* renamed from: O */
    private final Runnable f2687O;
    /* renamed from: a */
    ImageButton f2688a;
    /* renamed from: b */
    View f2689b;
    /* renamed from: c */
    int f2690c;
    /* renamed from: d */
    C0975c f2691d;
    /* renamed from: e */
    private ActionMenuView f2692e;
    /* renamed from: f */
    private TextView f2693f;
    /* renamed from: g */
    private TextView f2694g;
    /* renamed from: h */
    private ImageButton f2695h;
    /* renamed from: i */
    private ImageView f2696i;
    /* renamed from: j */
    private Drawable f2697j;
    /* renamed from: k */
    private CharSequence f2698k;
    /* renamed from: l */
    private Context f2699l;
    /* renamed from: m */
    private int f2700m;
    /* renamed from: n */
    private int f2701n;
    /* renamed from: o */
    private int f2702o;
    /* renamed from: p */
    private int f2703p;
    /* renamed from: q */
    private int f2704q;
    /* renamed from: r */
    private int f2705r;
    /* renamed from: s */
    private int f2706s;
    /* renamed from: t */
    private int f2707t;
    /* renamed from: u */
    private ba f2708u;
    /* renamed from: v */
    private int f2709v;
    /* renamed from: w */
    private int f2710w;
    /* renamed from: x */
    private int f2711x;
    /* renamed from: y */
    private CharSequence f2712y;
    /* renamed from: z */
    private CharSequence f2713z;

    /* renamed from: android.support.v7.widget.Toolbar$1 */
    class C09691 implements C0902e {
        /* renamed from: a */
        final /* synthetic */ Toolbar f2664a;

        C09691(Toolbar toolbar) {
            this.f2664a = toolbar;
        }

        /* renamed from: a */
        public boolean mo883a(MenuItem menuItem) {
            return this.f2664a.f2691d != null ? this.f2664a.f2691d.m5164a(menuItem) : false;
        }
    }

    /* renamed from: android.support.v7.widget.Toolbar$2 */
    class C09702 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ Toolbar f2665a;

        C09702(Toolbar toolbar) {
            this.f2665a = toolbar;
        }

        public void run() {
            this.f2665a.m5196d();
        }
    }

    /* renamed from: android.support.v7.widget.Toolbar$3 */
    class C09713 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ Toolbar f2666a;

        C09713(Toolbar toolbar) {
            this.f2666a = toolbar;
        }

        public void onClick(View view) {
            this.f2666a.m5200h();
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = C0437f.m1919a(new C09721());
        /* renamed from: a */
        int f2667a;
        /* renamed from: b */
        boolean f2668b;

        /* renamed from: android.support.v7.widget.Toolbar$SavedState$1 */
        static class C09721 implements C0085g<SavedState> {
            C09721() {
            }

            /* renamed from: a */
            public SavedState m5153a(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState[] m5154a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return m5153a(parcel, classLoader);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m5154a(i);
            }
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f2667a = parcel.readInt();
            this.f2668b = parcel.readInt() != 0;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.f2667a);
            parcel.writeInt(this.f2668b ? 1 : 0);
        }
    }

    /* renamed from: android.support.v7.widget.Toolbar$a */
    private class C0973a implements C0859o {
        /* renamed from: a */
        C0873h f2669a;
        /* renamed from: b */
        C0876j f2670b;
        /* renamed from: c */
        final /* synthetic */ Toolbar f2671c;

        C0973a(Toolbar toolbar) {
            this.f2671c = toolbar;
        }

        /* renamed from: a */
        public void mo719a(Context context, C0873h c0873h) {
            if (!(this.f2669a == null || this.f2670b == null)) {
                this.f2669a.mo762d(this.f2670b);
            }
            this.f2669a = c0873h;
        }

        /* renamed from: a */
        public void mo720a(C0873h c0873h, boolean z) {
        }

        /* renamed from: a */
        public void mo721a(C0794a c0794a) {
        }

        /* renamed from: a */
        public boolean mo722a(C0873h c0873h, C0876j c0876j) {
            this.f2671c.m5201i();
            if (this.f2671c.f2688a.getParent() != this.f2671c) {
                this.f2671c.addView(this.f2671c.f2688a);
            }
            this.f2671c.f2689b = c0876j.getActionView();
            this.f2670b = c0876j;
            if (this.f2671c.f2689b.getParent() != this.f2671c) {
                LayoutParams j = this.f2671c.m5202j();
                j.a = 8388611 | (this.f2671c.f2690c & 112);
                j.f2672b = 2;
                this.f2671c.f2689b.setLayoutParams(j);
                this.f2671c.addView(this.f2671c.f2689b);
            }
            this.f2671c.m5203k();
            this.f2671c.requestLayout();
            c0876j.m4284e(true);
            if (this.f2671c.f2689b instanceof C0843c) {
                ((C0843c) this.f2671c.f2689b).mo747a();
            }
            return true;
        }

        /* renamed from: a */
        public boolean mo723a(C0890u c0890u) {
            return false;
        }

        /* renamed from: b */
        public void mo724b(boolean z) {
            Object obj = null;
            if (this.f2670b != null) {
                if (this.f2669a != null) {
                    int size = this.f2669a.size();
                    for (int i = 0; i < size; i++) {
                        if (this.f2669a.getItem(i) == this.f2670b) {
                            obj = 1;
                            break;
                        }
                    }
                }
                if (obj == null) {
                    mo726b(this.f2669a, this.f2670b);
                }
            }
        }

        /* renamed from: b */
        public boolean mo725b() {
            return false;
        }

        /* renamed from: b */
        public boolean mo726b(C0873h c0873h, C0876j c0876j) {
            if (this.f2671c.f2689b instanceof C0843c) {
                ((C0843c) this.f2671c.f2689b).mo748b();
            }
            this.f2671c.removeView(this.f2671c.f2689b);
            this.f2671c.removeView(this.f2671c.f2688a);
            this.f2671c.f2689b = null;
            this.f2671c.m5204l();
            this.f2670b = null;
            this.f2671c.requestLayout();
            c0876j.m4284e(false);
            return true;
        }
    }

    /* renamed from: android.support.v7.widget.Toolbar$b */
    public static class C0974b extends C0762a {
        /* renamed from: b */
        int f2672b;

        public C0974b(int i, int i2) {
            super(i, i2);
            this.f2672b = 0;
            this.a = 8388627;
        }

        public C0974b(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f2672b = 0;
        }

        public C0974b(C0762a c0762a) {
            super(c0762a);
            this.f2672b = 0;
        }

        public C0974b(C0974b c0974b) {
            super((C0762a) c0974b);
            this.f2672b = 0;
            this.f2672b = c0974b.f2672b;
        }

        public C0974b(LayoutParams layoutParams) {
            super(layoutParams);
            this.f2672b = 0;
        }

        public C0974b(MarginLayoutParams marginLayoutParams) {
            super((LayoutParams) marginLayoutParams);
            this.f2672b = 0;
            m5163a(marginLayoutParams);
        }

        /* renamed from: a */
        void m5163a(MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
        }
    }

    /* renamed from: android.support.v7.widget.Toolbar$c */
    public interface C0975c {
        /* renamed from: a */
        boolean m5164a(MenuItem menuItem);
    }

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0738a.toolbarStyle);
    }

    public Toolbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f2711x = 8388627;
        this.f2677E = new ArrayList();
        this.f2678F = new ArrayList();
        this.f2679G = new int[2];
        this.f2680H = new C09691(this);
        this.f2687O = new C09702(this);
        bk a = bk.m5654a(getContext(), attributeSet, C0747j.Toolbar, i, 0);
        this.f2701n = a.m5670g(C0747j.Toolbar_titleTextAppearance, 0);
        this.f2702o = a.m5670g(C0747j.Toolbar_subtitleTextAppearance, 0);
        this.f2711x = a.m5662c(C0747j.Toolbar_android_gravity, this.f2711x);
        this.f2690c = a.m5662c(C0747j.Toolbar_buttonGravity, 48);
        int d = a.m5664d(C0747j.Toolbar_titleMargin, 0);
        if (a.m5671g(C0747j.Toolbar_titleMargins)) {
            d = a.m5664d(C0747j.Toolbar_titleMargins, d);
        }
        this.f2707t = d;
        this.f2706s = d;
        this.f2705r = d;
        this.f2704q = d;
        d = a.m5664d(C0747j.Toolbar_titleMarginStart, -1);
        if (d >= 0) {
            this.f2704q = d;
        }
        d = a.m5664d(C0747j.Toolbar_titleMarginEnd, -1);
        if (d >= 0) {
            this.f2705r = d;
        }
        d = a.m5664d(C0747j.Toolbar_titleMarginTop, -1);
        if (d >= 0) {
            this.f2706s = d;
        }
        d = a.m5664d(C0747j.Toolbar_titleMarginBottom, -1);
        if (d >= 0) {
            this.f2707t = d;
        }
        this.f2703p = a.m5666e(C0747j.Toolbar_maxButtonHeight, -1);
        d = a.m5664d(C0747j.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        int d2 = a.m5664d(C0747j.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int e = a.m5666e(C0747j.Toolbar_contentInsetLeft, 0);
        int e2 = a.m5666e(C0747j.Toolbar_contentInsetRight, 0);
        m5185s();
        this.f2708u.m5604b(e, e2);
        if (!(d == Integer.MIN_VALUE && d2 == Integer.MIN_VALUE)) {
            this.f2708u.m5601a(d, d2);
        }
        this.f2709v = a.m5664d(C0747j.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.f2710w = a.m5664d(C0747j.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.f2697j = a.m5657a(C0747j.Toolbar_collapseIcon);
        this.f2698k = a.m5663c(C0747j.Toolbar_collapseContentDescription);
        CharSequence c = a.m5663c(C0747j.Toolbar_title);
        if (!TextUtils.isEmpty(c)) {
            setTitle(c);
        }
        c = a.m5663c(C0747j.Toolbar_subtitle);
        if (!TextUtils.isEmpty(c)) {
            setSubtitle(c);
        }
        this.f2699l = getContext();
        setPopupTheme(a.m5670g(C0747j.Toolbar_popupTheme, 0));
        Drawable a2 = a.m5657a(C0747j.Toolbar_navigationIcon);
        if (a2 != null) {
            setNavigationIcon(a2);
        }
        c = a.m5663c(C0747j.Toolbar_navigationContentDescription);
        if (!TextUtils.isEmpty(c)) {
            setNavigationContentDescription(c);
        }
        a2 = a.m5657a(C0747j.Toolbar_logo);
        if (a2 != null) {
            setLogo(a2);
        }
        c = a.m5663c(C0747j.Toolbar_logoDescription);
        if (!TextUtils.isEmpty(c)) {
            setLogoDescription(c);
        }
        if (a.m5671g(C0747j.Toolbar_titleTextColor)) {
            setTitleTextColor(a.m5660b(C0747j.Toolbar_titleTextColor, -1));
        }
        if (a.m5671g(C0747j.Toolbar_subtitleTextColor)) {
            setSubtitleTextColor(a.m5660b(C0747j.Toolbar_subtitleTextColor, -1));
        }
        a.m5658a();
    }

    /* renamed from: a */
    private int m5165a(int i) {
        int i2 = i & 112;
        switch (i2) {
            case 16:
            case 48:
            case 80:
                return i2;
            default:
                return this.f2711x & 112;
        }
    }

    /* renamed from: a */
    private int m5166a(View view, int i) {
        C0974b c0974b = (C0974b) view.getLayoutParams();
        int measuredHeight = view.getMeasuredHeight();
        int i2 = i > 0 ? (measuredHeight - i) / 2 : 0;
        switch (m5165a(c0974b.a)) {
            case 48:
                return getPaddingTop() - i2;
            case 80:
                return (((getHeight() - getPaddingBottom()) - measuredHeight) - c0974b.bottomMargin) - i2;
            default:
                int i3;
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                int height = getHeight();
                i2 = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
                if (i2 < c0974b.topMargin) {
                    i3 = c0974b.topMargin;
                } else {
                    measuredHeight = (((height - paddingBottom) - measuredHeight) - i2) - paddingTop;
                    i3 = measuredHeight < c0974b.bottomMargin ? Math.max(0, i2 - (c0974b.bottomMargin - measuredHeight)) : i2;
                }
                return i3 + paddingTop;
        }
    }

    /* renamed from: a */
    private int m5167a(View view, int i, int i2, int i3, int i4, int[] iArr) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        int i5 = marginLayoutParams.leftMargin - iArr[0];
        int i6 = marginLayoutParams.rightMargin - iArr[1];
        int max = Math.max(0, i5) + Math.max(0, i6);
        iArr[0] = Math.max(0, -i5);
        iArr[1] = Math.max(0, -i6);
        view.measure(getChildMeasureSpec(i, ((getPaddingLeft() + getPaddingRight()) + max) + i2, marginLayoutParams.width), getChildMeasureSpec(i3, (((getPaddingTop() + getPaddingBottom()) + marginLayoutParams.topMargin) + marginLayoutParams.bottomMargin) + i4, marginLayoutParams.height));
        return view.getMeasuredWidth() + max;
    }

    /* renamed from: a */
    private int m5168a(View view, int i, int[] iArr, int i2) {
        C0974b c0974b = (C0974b) view.getLayoutParams();
        int i3 = c0974b.leftMargin - iArr[0];
        int max = Math.max(0, i3) + i;
        iArr[0] = Math.max(0, -i3);
        i3 = m5166a(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(max, i3, max + measuredWidth, view.getMeasuredHeight() + i3);
        return (c0974b.rightMargin + measuredWidth) + max;
    }

    /* renamed from: a */
    private int m5169a(List<View> list, int[] iArr) {
        int i = iArr[0];
        int i2 = iArr[1];
        int size = list.size();
        int i3 = 0;
        int i4 = 0;
        int i5 = i2;
        int i6 = i;
        while (i3 < size) {
            View view = (View) list.get(i3);
            C0974b c0974b = (C0974b) view.getLayoutParams();
            i6 = c0974b.leftMargin - i6;
            i = c0974b.rightMargin - i5;
            int max = Math.max(0, i6);
            int max2 = Math.max(0, i);
            i6 = Math.max(0, -i6);
            i5 = Math.max(0, -i);
            i3++;
            i4 += (view.getMeasuredWidth() + max) + max2;
        }
        return i4;
    }

    /* renamed from: a */
    private void m5170a(View view, int i, int i2, int i3, int i4, int i5) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        int childMeasureSpec = getChildMeasureSpec(i, (((getPaddingLeft() + getPaddingRight()) + marginLayoutParams.leftMargin) + marginLayoutParams.rightMargin) + i2, marginLayoutParams.width);
        int childMeasureSpec2 = getChildMeasureSpec(i3, (((getPaddingTop() + getPaddingBottom()) + marginLayoutParams.topMargin) + marginLayoutParams.bottomMargin) + i4, marginLayoutParams.height);
        int mode = MeasureSpec.getMode(childMeasureSpec2);
        if (mode != 1073741824 && i5 >= 0) {
            if (mode != 0) {
                i5 = Math.min(MeasureSpec.getSize(childMeasureSpec2), i5);
            }
            childMeasureSpec2 = MeasureSpec.makeMeasureSpec(i5, 1073741824);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    /* renamed from: a */
    private void m5171a(View view, boolean z) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = m5202j();
        } else if (checkLayoutParams(layoutParams)) {
            C0974b c0974b = (C0974b) layoutParams;
        } else {
            layoutParams = m5187a(layoutParams);
        }
        layoutParams.f2672b = 1;
        if (!z || this.f2689b == null) {
            addView(view, layoutParams);
            return;
        }
        view.setLayoutParams(layoutParams);
        this.f2678F.add(view);
    }

    /* renamed from: a */
    private void m5172a(List<View> list, int i) {
        int i2 = 1;
        int i3 = 0;
        if (ah.m2812g(this) != 1) {
            i2 = 0;
        }
        int childCount = getChildCount();
        int a = C0625f.m3120a(i, ah.m2812g(this));
        list.clear();
        C0974b c0974b;
        if (i2 != 0) {
            for (i3 = childCount - 1; i3 >= 0; i3--) {
                View childAt = getChildAt(i3);
                c0974b = (C0974b) childAt.getLayoutParams();
                if (c0974b.f2672b == 0 && m5173a(childAt) && m5174b(c0974b.a) == a) {
                    list.add(childAt);
                }
            }
            return;
        }
        while (i3 < childCount) {
            View childAt2 = getChildAt(i3);
            c0974b = (C0974b) childAt2.getLayoutParams();
            if (c0974b.f2672b == 0 && m5173a(childAt2) && m5174b(c0974b.a) == a) {
                list.add(childAt2);
            }
            i3++;
        }
    }

    /* renamed from: a */
    private boolean m5173a(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    /* renamed from: b */
    private int m5174b(int i) {
        int g = ah.m2812g(this);
        int a = C0625f.m3120a(i, g) & 7;
        switch (a) {
            case 1:
            case 3:
            case 5:
                return a;
            default:
                return g == 1 ? 5 : 3;
        }
    }

    /* renamed from: b */
    private int m5175b(View view) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        return C0645o.m3163b(marginLayoutParams) + C0645o.m3162a(marginLayoutParams);
    }

    /* renamed from: b */
    private int m5176b(View view, int i, int[] iArr, int i2) {
        C0974b c0974b = (C0974b) view.getLayoutParams();
        int i3 = c0974b.rightMargin - iArr[1];
        int max = i - Math.max(0, i3);
        iArr[1] = Math.max(0, -i3);
        i3 = m5166a(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(max - measuredWidth, i3, max, view.getMeasuredHeight() + i3);
        return max - (c0974b.leftMargin + measuredWidth);
    }

    /* renamed from: c */
    private int m5177c(View view) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.bottomMargin + marginLayoutParams.topMargin;
    }

    /* renamed from: d */
    private boolean m5178d(View view) {
        return view.getParent() == this || this.f2678F.contains(view);
    }

    private MenuInflater getMenuInflater() {
        return new C0850g(getContext());
    }

    /* renamed from: m */
    private void m5179m() {
        if (this.f2696i == null) {
            this.f2696i = new AppCompatImageView(getContext());
        }
    }

    /* renamed from: n */
    private void m5180n() {
        m5181o();
        if (this.f2692e.m4420d() == null) {
            C0873h c0873h = (C0873h) this.f2692e.getMenu();
            if (this.f2683K == null) {
                this.f2683K = new C0973a(this);
            }
            this.f2692e.setExpandedActionViewsExclusive(true);
            c0873h.m4227a(this.f2683K, this.f2699l);
        }
    }

    /* renamed from: o */
    private void m5181o() {
        if (this.f2692e == null) {
            this.f2692e = new ActionMenuView(getContext());
            this.f2692e.setPopupTheme(this.f2700m);
            this.f2692e.setOnMenuItemClickListener(this.f2680H);
            this.f2692e.m4412a(this.f2684L, this.f2685M);
            LayoutParams j = m5202j();
            j.a = 8388613 | (this.f2690c & 112);
            this.f2692e.setLayoutParams(j);
            m5171a(this.f2692e, false);
        }
    }

    /* renamed from: p */
    private void m5182p() {
        if (this.f2695h == null) {
            this.f2695h = new AppCompatImageButton(getContext(), null, C0738a.toolbarNavigationButtonStyle);
            LayoutParams j = m5202j();
            j.a = 8388611 | (this.f2690c & 112);
            this.f2695h.setLayoutParams(j);
        }
    }

    /* renamed from: q */
    private void m5183q() {
        removeCallbacks(this.f2687O);
        post(this.f2687O);
    }

    /* renamed from: r */
    private boolean m5184r() {
        if (!this.f2686N) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (m5173a(childAt) && childAt.getMeasuredWidth() > 0 && childAt.getMeasuredHeight() > 0) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: s */
    private void m5185s() {
        if (this.f2708u == null) {
            this.f2708u = new ba();
        }
    }

    /* renamed from: a */
    public C0974b m5186a(AttributeSet attributeSet) {
        return new C0974b(getContext(), attributeSet);
    }

    /* renamed from: a */
    protected C0974b m5187a(LayoutParams layoutParams) {
        return layoutParams instanceof C0974b ? new C0974b((C0974b) layoutParams) : layoutParams instanceof C0762a ? new C0974b((C0762a) layoutParams) : layoutParams instanceof MarginLayoutParams ? new C0974b((MarginLayoutParams) layoutParams) : new C0974b(layoutParams);
    }

    /* renamed from: a */
    public void m5188a(int i, int i2) {
        m5185s();
        this.f2708u.m5601a(i, i2);
    }

    /* renamed from: a */
    public void m5189a(Context context, int i) {
        this.f2701n = i;
        if (this.f2693f != null) {
            this.f2693f.setTextAppearance(context, i);
        }
    }

    /* renamed from: a */
    public void m5190a(C0873h c0873h, C1052d c1052d) {
        if (c0873h != null || this.f2692e != null) {
            m5181o();
            C0873h d = this.f2692e.m4420d();
            if (d != c0873h) {
                if (d != null) {
                    d.m4237b(this.f2682J);
                    d.m4237b(this.f2683K);
                }
                if (this.f2683K == null) {
                    this.f2683K = new C0973a(this);
                }
                c1052d.m5782d(true);
                if (c0873h != null) {
                    c0873h.m4227a((C0859o) c1052d, this.f2699l);
                    c0873h.m4227a(this.f2683K, this.f2699l);
                } else {
                    c1052d.mo719a(this.f2699l, null);
                    this.f2683K.mo719a(this.f2699l, null);
                    c1052d.mo724b(true);
                    this.f2683K.mo724b(true);
                }
                this.f2692e.setPopupTheme(this.f2700m);
                this.f2692e.setPresenter(c1052d);
                this.f2682J = c1052d;
            }
        }
    }

    /* renamed from: a */
    public void m5191a(C0794a c0794a, C0777a c0777a) {
        this.f2684L = c0794a;
        this.f2685M = c0777a;
        if (this.f2692e != null) {
            this.f2692e.m4412a(c0794a, c0777a);
        }
    }

    /* renamed from: a */
    public boolean m5192a() {
        return getVisibility() == 0 && this.f2692e != null && this.f2692e.m4413a();
    }

    /* renamed from: b */
    public void m5193b(Context context, int i) {
        this.f2702o = i;
        if (this.f2694g != null) {
            this.f2694g.setTextAppearance(context, i);
        }
    }

    /* renamed from: b */
    public boolean m5194b() {
        return this.f2692e != null && this.f2692e.m4423g();
    }

    /* renamed from: c */
    public boolean m5195c() {
        return this.f2692e != null && this.f2692e.m4424h();
    }

    protected boolean checkLayoutParams(LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof C0974b);
    }

    /* renamed from: d */
    public boolean m5196d() {
        return this.f2692e != null && this.f2692e.m4421e();
    }

    /* renamed from: e */
    public boolean m5197e() {
        return this.f2692e != null && this.f2692e.m4422f();
    }

    /* renamed from: f */
    public void m5198f() {
        if (this.f2692e != null) {
            this.f2692e.m4425i();
        }
    }

    /* renamed from: g */
    public boolean m5199g() {
        return (this.f2683K == null || this.f2683K.f2670b == null) ? false : true;
    }

    protected /* synthetic */ LayoutParams generateDefaultLayoutParams() {
        return m5202j();
    }

    public /* synthetic */ LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return m5186a(attributeSet);
    }

    protected /* synthetic */ LayoutParams generateLayoutParams(LayoutParams layoutParams) {
        return m5187a(layoutParams);
    }

    public int getContentInsetEnd() {
        return this.f2708u != null ? this.f2708u.m5606d() : 0;
    }

    public int getContentInsetEndWithActions() {
        return this.f2710w != Integer.MIN_VALUE ? this.f2710w : getContentInsetEnd();
    }

    public int getContentInsetLeft() {
        return this.f2708u != null ? this.f2708u.m5600a() : 0;
    }

    public int getContentInsetRight() {
        return this.f2708u != null ? this.f2708u.m5603b() : 0;
    }

    public int getContentInsetStart() {
        return this.f2708u != null ? this.f2708u.m5605c() : 0;
    }

    public int getContentInsetStartWithNavigation() {
        return this.f2709v != Integer.MIN_VALUE ? this.f2709v : getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        int i;
        if (this.f2692e != null) {
            C0873h d = this.f2692e.m4420d();
            i = (d == null || !d.hasVisibleItems()) ? 0 : 1;
        } else {
            i = 0;
        }
        return i != 0 ? Math.max(getContentInsetEnd(), Math.max(this.f2710w, 0)) : getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        return ah.m2812g(this) == 1 ? getCurrentContentInsetEnd() : getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        return ah.m2812g(this) == 1 ? getCurrentContentInsetStart() : getCurrentContentInsetEnd();
    }

    public int getCurrentContentInsetStart() {
        return getNavigationIcon() != null ? Math.max(getContentInsetStart(), Math.max(this.f2709v, 0)) : getContentInsetStart();
    }

    public Drawable getLogo() {
        return this.f2696i != null ? this.f2696i.getDrawable() : null;
    }

    public CharSequence getLogoDescription() {
        return this.f2696i != null ? this.f2696i.getContentDescription() : null;
    }

    public Menu getMenu() {
        m5180n();
        return this.f2692e.getMenu();
    }

    public CharSequence getNavigationContentDescription() {
        return this.f2695h != null ? this.f2695h.getContentDescription() : null;
    }

    public Drawable getNavigationIcon() {
        return this.f2695h != null ? this.f2695h.getDrawable() : null;
    }

    public Drawable getOverflowIcon() {
        m5180n();
        return this.f2692e.getOverflowIcon();
    }

    public int getPopupTheme() {
        return this.f2700m;
    }

    public CharSequence getSubtitle() {
        return this.f2713z;
    }

    public CharSequence getTitle() {
        return this.f2712y;
    }

    public int getTitleMarginBottom() {
        return this.f2707t;
    }

    public int getTitleMarginEnd() {
        return this.f2705r;
    }

    public int getTitleMarginStart() {
        return this.f2704q;
    }

    public int getTitleMarginTop() {
        return this.f2706s;
    }

    public ag getWrapper() {
        if (this.f2681I == null) {
            this.f2681I = new bl(this, true);
        }
        return this.f2681I;
    }

    /* renamed from: h */
    public void m5200h() {
        C0876j c0876j = this.f2683K == null ? null : this.f2683K.f2670b;
        if (c0876j != null) {
            c0876j.collapseActionView();
        }
    }

    /* renamed from: i */
    void m5201i() {
        if (this.f2688a == null) {
            this.f2688a = new AppCompatImageButton(getContext(), null, C0738a.toolbarNavigationButtonStyle);
            this.f2688a.setImageDrawable(this.f2697j);
            this.f2688a.setContentDescription(this.f2698k);
            LayoutParams j = m5202j();
            j.a = 8388611 | (this.f2690c & 112);
            j.f2672b = 2;
            this.f2688a.setLayoutParams(j);
            this.f2688a.setOnClickListener(new C09713(this));
        }
    }

    /* renamed from: j */
    protected C0974b m5202j() {
        return new C0974b(-2, -2);
    }

    /* renamed from: k */
    void m5203k() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (!(((C0974b) childAt.getLayoutParams()).f2672b == 2 || childAt == this.f2692e)) {
                removeViewAt(childCount);
                this.f2678F.add(childAt);
            }
        }
    }

    /* renamed from: l */
    void m5204l() {
        for (int size = this.f2678F.size() - 1; size >= 0; size--) {
            addView((View) this.f2678F.get(size));
        }
        this.f2678F.clear();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.f2687O);
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int a = C0659t.m3205a(motionEvent);
        if (a == 9) {
            this.f2676D = false;
        }
        if (!this.f2676D) {
            boolean onHoverEvent = super.onHoverEvent(motionEvent);
            if (a == 9 && !onHoverEvent) {
                this.f2676D = true;
            }
        }
        if (a == 10 || a == 3) {
            this.f2676D = false;
        }
        return true;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredHeight;
        int measuredWidth;
        Object obj = ah.m2812g(this) == 1 ? 1 : null;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i5 = width - paddingRight;
        int[] iArr = this.f2679G;
        iArr[1] = 0;
        iArr[0] = 0;
        int p = ah.m2826p(this);
        int min = p >= 0 ? Math.min(p, i4 - i2) : 0;
        if (!m5173a(this.f2695h)) {
            p = i5;
            i5 = paddingLeft;
        } else if (obj != null) {
            p = m5176b(this.f2695h, i5, iArr, min);
            i5 = paddingLeft;
        } else {
            int i6 = i5;
            i5 = m5168a(this.f2695h, paddingLeft, iArr, min);
            p = i6;
        }
        if (m5173a(this.f2688a)) {
            if (obj != null) {
                p = m5176b(this.f2688a, p, iArr, min);
            } else {
                i5 = m5168a(this.f2688a, i5, iArr, min);
            }
        }
        if (m5173a(this.f2692e)) {
            if (obj != null) {
                i5 = m5168a(this.f2692e, i5, iArr, min);
            } else {
                p = m5176b(this.f2692e, p, iArr, min);
            }
        }
        int currentContentInsetLeft = getCurrentContentInsetLeft();
        int currentContentInsetRight = getCurrentContentInsetRight();
        iArr[0] = Math.max(0, currentContentInsetLeft - i5);
        iArr[1] = Math.max(0, currentContentInsetRight - ((width - paddingRight) - p));
        i5 = Math.max(i5, currentContentInsetLeft);
        p = Math.min(p, (width - paddingRight) - currentContentInsetRight);
        if (m5173a(this.f2689b)) {
            if (obj != null) {
                p = m5176b(this.f2689b, p, iArr, min);
            } else {
                i5 = m5168a(this.f2689b, i5, iArr, min);
            }
        }
        if (!m5173a(this.f2696i)) {
            currentContentInsetLeft = p;
            currentContentInsetRight = i5;
        } else if (obj != null) {
            currentContentInsetLeft = m5176b(this.f2696i, p, iArr, min);
            currentContentInsetRight = i5;
        } else {
            currentContentInsetLeft = p;
            currentContentInsetRight = m5168a(this.f2696i, i5, iArr, min);
        }
        boolean a = m5173a(this.f2693f);
        boolean a2 = m5173a(this.f2694g);
        i5 = 0;
        if (a) {
            C0974b c0974b = (C0974b) this.f2693f.getLayoutParams();
            i5 = 0 + (c0974b.bottomMargin + (c0974b.topMargin + this.f2693f.getMeasuredHeight()));
        }
        if (a2) {
            c0974b = (C0974b) this.f2694g.getLayoutParams();
            measuredHeight = (c0974b.bottomMargin + (c0974b.topMargin + this.f2694g.getMeasuredHeight())) + i5;
        } else {
            measuredHeight = i5;
        }
        if (a || a2) {
            int paddingTop2;
            c0974b = (C0974b) (a ? this.f2693f : this.f2694g).getLayoutParams();
            C0974b c0974b2 = (C0974b) (a2 ? this.f2694g : this.f2693f).getLayoutParams();
            Object obj2 = ((!a || this.f2693f.getMeasuredWidth() <= 0) && (!a2 || this.f2694g.getMeasuredWidth() <= 0)) ? null : 1;
            switch (this.f2711x & 112) {
                case 48:
                    paddingTop2 = (c0974b.topMargin + getPaddingTop()) + this.f2706s;
                    break;
                case 80:
                    paddingTop2 = (((height - paddingBottom) - c0974b2.bottomMargin) - this.f2707t) - measuredHeight;
                    break;
                default:
                    paddingTop2 = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
                    if (paddingTop2 < c0974b.topMargin + this.f2706s) {
                        p = c0974b.topMargin + this.f2706s;
                    } else {
                        measuredHeight = (((height - paddingBottom) - measuredHeight) - paddingTop2) - paddingTop;
                        p = measuredHeight < c0974b.bottomMargin + this.f2707t ? Math.max(0, paddingTop2 - ((c0974b2.bottomMargin + this.f2707t) - measuredHeight)) : paddingTop2;
                    }
                    paddingTop2 = paddingTop + p;
                    break;
            }
            if (obj != null) {
                p = (obj2 != null ? this.f2704q : 0) - iArr[1];
                i5 = currentContentInsetLeft - Math.max(0, p);
                iArr[1] = Math.max(0, -p);
                if (a) {
                    c0974b = (C0974b) this.f2693f.getLayoutParams();
                    measuredWidth = i5 - this.f2693f.getMeasuredWidth();
                    currentContentInsetLeft = this.f2693f.getMeasuredHeight() + paddingTop2;
                    this.f2693f.layout(measuredWidth, paddingTop2, i5, currentContentInsetLeft);
                    paddingTop2 = currentContentInsetLeft + c0974b.bottomMargin;
                    currentContentInsetLeft = measuredWidth - this.f2705r;
                } else {
                    currentContentInsetLeft = i5;
                }
                if (a2) {
                    c0974b = (C0974b) this.f2694g.getLayoutParams();
                    measuredWidth = c0974b.topMargin + paddingTop2;
                    measuredHeight = this.f2694g.getMeasuredHeight() + measuredWidth;
                    this.f2694g.layout(i5 - this.f2694g.getMeasuredWidth(), measuredWidth, i5, measuredHeight);
                    p = c0974b.bottomMargin + measuredHeight;
                    p = i5 - this.f2705r;
                } else {
                    p = i5;
                }
                currentContentInsetLeft = obj2 != null ? Math.min(currentContentInsetLeft, p) : i5;
            } else {
                p = (obj2 != null ? this.f2704q : 0) - iArr[0];
                currentContentInsetRight += Math.max(0, p);
                iArr[0] = Math.max(0, -p);
                if (a) {
                    c0974b = (C0974b) this.f2693f.getLayoutParams();
                    i5 = this.f2693f.getMeasuredWidth() + currentContentInsetRight;
                    measuredWidth = this.f2693f.getMeasuredHeight() + paddingTop2;
                    this.f2693f.layout(currentContentInsetRight, paddingTop2, i5, measuredWidth);
                    p = c0974b.bottomMargin + measuredWidth;
                    measuredWidth = i5 + this.f2705r;
                    i5 = p;
                } else {
                    measuredWidth = currentContentInsetRight;
                    i5 = paddingTop2;
                }
                if (a2) {
                    c0974b = (C0974b) this.f2694g.getLayoutParams();
                    i5 += c0974b.topMargin;
                    paddingTop2 = this.f2694g.getMeasuredWidth() + currentContentInsetRight;
                    measuredHeight = this.f2694g.getMeasuredHeight() + i5;
                    this.f2694g.layout(currentContentInsetRight, i5, paddingTop2, measuredHeight);
                    p = c0974b.bottomMargin + measuredHeight;
                    p = this.f2705r + paddingTop2;
                } else {
                    p = currentContentInsetRight;
                }
                if (obj2 != null) {
                    currentContentInsetRight = Math.max(measuredWidth, p);
                }
            }
        }
        m5172a(this.f2677E, 3);
        int size = this.f2677E.size();
        i5 = currentContentInsetRight;
        for (measuredWidth = 0; measuredWidth < size; measuredWidth++) {
            i5 = m5168a((View) this.f2677E.get(measuredWidth), i5, iArr, min);
        }
        m5172a(this.f2677E, 5);
        currentContentInsetRight = this.f2677E.size();
        for (measuredWidth = 0; measuredWidth < currentContentInsetRight; measuredWidth++) {
            currentContentInsetLeft = m5176b((View) this.f2677E.get(measuredWidth), currentContentInsetLeft, iArr, min);
        }
        m5172a(this.f2677E, 1);
        measuredWidth = m5169a(this.f2677E, iArr);
        p = ((((width - paddingLeft) - paddingRight) / 2) + paddingLeft) - (measuredWidth / 2);
        measuredWidth += p;
        if (p < i5) {
            p = i5;
        } else if (measuredWidth > currentContentInsetLeft) {
            p -= measuredWidth - currentContentInsetLeft;
        }
        paddingLeft = this.f2677E.size();
        measuredWidth = p;
        for (i5 = 0; i5 < paddingLeft; i5++) {
            measuredWidth = m5168a((View) this.f2677E.get(i5), measuredWidth, iArr, min);
        }
        this.f2677E.clear();
    }

    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int max;
        int i5 = 0;
        int i6 = 0;
        int[] iArr = this.f2679G;
        if (bp.m5747a(this)) {
            i3 = 0;
            i4 = 1;
        } else {
            i3 = 1;
            i4 = 0;
        }
        int i7 = 0;
        if (m5173a(this.f2695h)) {
            m5170a(this.f2695h, i, 0, i2, 0, this.f2703p);
            i7 = this.f2695h.getMeasuredWidth() + m5175b(this.f2695h);
            max = Math.max(0, this.f2695h.getMeasuredHeight() + m5177c(this.f2695h));
            i6 = bp.m5745a(0, ah.m2816i(this.f2695h));
            i5 = max;
        }
        if (m5173a(this.f2688a)) {
            m5170a(this.f2688a, i, 0, i2, 0, this.f2703p);
            i7 = this.f2688a.getMeasuredWidth() + m5175b(this.f2688a);
            i5 = Math.max(i5, this.f2688a.getMeasuredHeight() + m5177c(this.f2688a));
            i6 = bp.m5745a(i6, ah.m2816i(this.f2688a));
        }
        int currentContentInsetStart = getCurrentContentInsetStart();
        int max2 = 0 + Math.max(currentContentInsetStart, i7);
        iArr[i4] = Math.max(0, currentContentInsetStart - i7);
        i7 = 0;
        if (m5173a(this.f2692e)) {
            m5170a(this.f2692e, i, max2, i2, 0, this.f2703p);
            i7 = this.f2692e.getMeasuredWidth() + m5175b(this.f2692e);
            i5 = Math.max(i5, this.f2692e.getMeasuredHeight() + m5177c(this.f2692e));
            i6 = bp.m5745a(i6, ah.m2816i(this.f2692e));
        }
        currentContentInsetStart = getCurrentContentInsetEnd();
        max2 += Math.max(currentContentInsetStart, i7);
        iArr[i3] = Math.max(0, currentContentInsetStart - i7);
        if (m5173a(this.f2689b)) {
            max2 += m5167a(this.f2689b, i, max2, i2, 0, iArr);
            i5 = Math.max(i5, this.f2689b.getMeasuredHeight() + m5177c(this.f2689b));
            i6 = bp.m5745a(i6, ah.m2816i(this.f2689b));
        }
        if (m5173a(this.f2696i)) {
            max2 += m5167a(this.f2696i, i, max2, i2, 0, iArr);
            i5 = Math.max(i5, this.f2696i.getMeasuredHeight() + m5177c(this.f2696i));
            i6 = bp.m5745a(i6, ah.m2816i(this.f2696i));
        }
        i4 = getChildCount();
        i3 = 0;
        int i8 = i5;
        i5 = i6;
        while (i3 < i4) {
            View childAt = getChildAt(i3);
            if (((C0974b) childAt.getLayoutParams()).f2672b != 0) {
                i7 = i5;
                currentContentInsetStart = i8;
            } else if (m5173a(childAt)) {
                max2 += m5167a(childAt, i, max2, i2, 0, iArr);
                max = Math.max(i8, childAt.getMeasuredHeight() + m5177c(childAt));
                i7 = bp.m5745a(i5, ah.m2816i(childAt));
                currentContentInsetStart = max;
            } else {
                i7 = i5;
                currentContentInsetStart = i8;
            }
            i3++;
            i5 = i7;
            i8 = currentContentInsetStart;
        }
        currentContentInsetStart = 0;
        i7 = 0;
        i6 = this.f2706s + this.f2707t;
        max = this.f2704q + this.f2705r;
        if (m5173a(this.f2693f)) {
            m5167a(this.f2693f, i, max2 + max, i2, i6, iArr);
            currentContentInsetStart = m5175b(this.f2693f) + this.f2693f.getMeasuredWidth();
            i7 = this.f2693f.getMeasuredHeight() + m5177c(this.f2693f);
            i5 = bp.m5745a(i5, ah.m2816i(this.f2693f));
        }
        if (m5173a(this.f2694g)) {
            currentContentInsetStart = Math.max(currentContentInsetStart, m5167a(this.f2694g, i, max2 + max, i2, i6 + i7, iArr));
            i7 += this.f2694g.getMeasuredHeight() + m5177c(this.f2694g);
            i5 = bp.m5745a(i5, ah.m2816i(this.f2694g));
        }
        currentContentInsetStart += max2;
        i7 = Math.max(i8, i7) + (getPaddingTop() + getPaddingBottom());
        currentContentInsetStart = ah.m2773a(Math.max(currentContentInsetStart + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), i, Theme.ACTION_BAR_VIDEO_EDIT_COLOR & i5);
        i7 = ah.m2773a(Math.max(i7, getSuggestedMinimumHeight()), i2, i5 << 16);
        if (m5184r()) {
            i7 = 0;
        }
        setMeasuredDimension(currentContentInsetStart, i7);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            Menu d = this.f2692e != null ? this.f2692e.m4420d() : null;
            if (!(savedState.f2667a == 0 || this.f2683K == null || d == null)) {
                MenuItem findItem = d.findItem(savedState.f2667a);
                if (findItem != null) {
                    C0652q.m3193b(findItem);
                }
            }
            if (savedState.f2668b) {
                m5183q();
                return;
            }
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public void onRtlPropertiesChanged(int i) {
        boolean z = true;
        if (VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(i);
        }
        m5185s();
        ba baVar = this.f2708u;
        if (i != 1) {
            z = false;
        }
        baVar.m5602a(z);
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        if (!(this.f2683K == null || this.f2683K.f2670b == null)) {
            savedState.f2667a = this.f2683K.f2670b.getItemId();
        }
        savedState.f2668b = m5194b();
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int a = C0659t.m3205a(motionEvent);
        if (a == 0) {
            this.f2675C = false;
        }
        if (!this.f2675C) {
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            if (a == 0 && !onTouchEvent) {
                this.f2675C = true;
            }
        }
        if (a == 1 || a == 3) {
            this.f2675C = false;
        }
        return true;
    }

    public void setCollapsible(boolean z) {
        this.f2686N = z;
        requestLayout();
    }

    public void setContentInsetEndWithActions(int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE;
        }
        if (i != this.f2710w) {
            this.f2710w = i;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setContentInsetStartWithNavigation(int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE;
        }
        if (i != this.f2709v) {
            this.f2709v = i;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setLogo(int i) {
        setLogo(C0825b.m3939b(getContext(), i));
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            m5179m();
            if (!m5178d(this.f2696i)) {
                m5171a(this.f2696i, true);
            }
        } else if (this.f2696i != null && m5178d(this.f2696i)) {
            removeView(this.f2696i);
            this.f2678F.remove(this.f2696i);
        }
        if (this.f2696i != null) {
            this.f2696i.setImageDrawable(drawable);
        }
    }

    public void setLogoDescription(int i) {
        setLogoDescription(getContext().getText(i));
    }

    public void setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            m5179m();
        }
        if (this.f2696i != null) {
            this.f2696i.setContentDescription(charSequence);
        }
    }

    public void setNavigationContentDescription(int i) {
        setNavigationContentDescription(i != 0 ? getContext().getText(i) : null);
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            m5182p();
        }
        if (this.f2695h != null) {
            this.f2695h.setContentDescription(charSequence);
        }
    }

    public void setNavigationIcon(int i) {
        setNavigationIcon(C0825b.m3939b(getContext(), i));
    }

    public void setNavigationIcon(Drawable drawable) {
        if (drawable != null) {
            m5182p();
            if (!m5178d(this.f2695h)) {
                m5171a(this.f2695h, true);
            }
        } else if (this.f2695h != null && m5178d(this.f2695h)) {
            removeView(this.f2695h);
            this.f2678F.remove(this.f2695h);
        }
        if (this.f2695h != null) {
            this.f2695h.setImageDrawable(drawable);
        }
    }

    public void setNavigationOnClickListener(OnClickListener onClickListener) {
        m5182p();
        this.f2695h.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(C0975c c0975c) {
        this.f2691d = c0975c;
    }

    public void setOverflowIcon(Drawable drawable) {
        m5180n();
        this.f2692e.setOverflowIcon(drawable);
    }

    public void setPopupTheme(int i) {
        if (this.f2700m != i) {
            this.f2700m = i;
            if (i == 0) {
                this.f2699l = getContext();
            } else {
                this.f2699l = new ContextThemeWrapper(getContext(), i);
            }
        }
    }

    public void setSubtitle(int i) {
        setSubtitle(getContext().getText(i));
    }

    public void setSubtitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.f2694g == null) {
                Context context = getContext();
                this.f2694g = new C0855y(context);
                this.f2694g.setSingleLine();
                this.f2694g.setEllipsize(TruncateAt.END);
                if (this.f2702o != 0) {
                    this.f2694g.setTextAppearance(context, this.f2702o);
                }
                if (this.f2674B != 0) {
                    this.f2694g.setTextColor(this.f2674B);
                }
            }
            if (!m5178d(this.f2694g)) {
                m5171a(this.f2694g, true);
            }
        } else if (this.f2694g != null && m5178d(this.f2694g)) {
            removeView(this.f2694g);
            this.f2678F.remove(this.f2694g);
        }
        if (this.f2694g != null) {
            this.f2694g.setText(charSequence);
        }
        this.f2713z = charSequence;
    }

    public void setSubtitleTextColor(int i) {
        this.f2674B = i;
        if (this.f2694g != null) {
            this.f2694g.setTextColor(i);
        }
    }

    public void setTitle(int i) {
        setTitle(getContext().getText(i));
    }

    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.f2693f == null) {
                Context context = getContext();
                this.f2693f = new C0855y(context);
                this.f2693f.setSingleLine();
                this.f2693f.setEllipsize(TruncateAt.END);
                if (this.f2701n != 0) {
                    this.f2693f.setTextAppearance(context, this.f2701n);
                }
                if (this.f2673A != 0) {
                    this.f2693f.setTextColor(this.f2673A);
                }
            }
            if (!m5178d(this.f2693f)) {
                m5171a(this.f2693f, true);
            }
        } else if (this.f2693f != null && m5178d(this.f2693f)) {
            removeView(this.f2693f);
            this.f2678F.remove(this.f2693f);
        }
        if (this.f2693f != null) {
            this.f2693f.setText(charSequence);
        }
        this.f2712y = charSequence;
    }

    public void setTitleMarginBottom(int i) {
        this.f2707t = i;
        requestLayout();
    }

    public void setTitleMarginEnd(int i) {
        this.f2705r = i;
        requestLayout();
    }

    public void setTitleMarginStart(int i) {
        this.f2704q = i;
        requestLayout();
    }

    public void setTitleMarginTop(int i) {
        this.f2706s = i;
        requestLayout();
    }

    public void setTitleTextColor(int i) {
        this.f2673A = i;
        if (this.f2693f != null) {
            this.f2693f.setTextColor(i);
        }
    }
}
