package utils.view.bottombar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.C0104d;
import android.support.v4.content.C0235a;
import android.support.v4.view.ah;
import android.support.v4.view.bc;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.messenger.C3336R;
import org.telegram.ui.ActionBar.Theme;
import utils.view.bottombar.BottomBarTab.Config;
import utils.view.bottombar.BottomBarTab.Config.Builder;

public class BottomBar extends LinearLayout implements OnClickListener, OnLongClickListener {
    /* renamed from: A */
    private OnTabReselectListener f10354A;
    /* renamed from: B */
    private boolean f10355B;
    /* renamed from: C */
    private boolean f10356C;
    /* renamed from: D */
    private boolean f10357D;
    /* renamed from: E */
    private boolean f10358E;
    /* renamed from: F */
    private BottomBarTab[] f10359F;
    /* renamed from: a */
    private BatchTabPropertyApplier f10360a;
    /* renamed from: b */
    private int f10361b;
    /* renamed from: c */
    private int f10362c;
    /* renamed from: d */
    private int f10363d;
    /* renamed from: e */
    private int f10364e;
    /* renamed from: f */
    private int f10365f;
    /* renamed from: g */
    private boolean f10366g;
    /* renamed from: h */
    private int f10367h;
    /* renamed from: i */
    private float f10368i;
    /* renamed from: j */
    private float f10369j;
    /* renamed from: k */
    private int f10370k;
    /* renamed from: l */
    private int f10371l;
    /* renamed from: m */
    private int f10372m;
    /* renamed from: n */
    private int f10373n;
    /* renamed from: o */
    private Typeface f10374o;
    /* renamed from: p */
    private boolean f10375p;
    /* renamed from: q */
    private View f10376q;
    /* renamed from: r */
    private ViewGroup f10377r;
    /* renamed from: s */
    private ViewGroup f10378s;
    /* renamed from: t */
    private View f10379t;
    /* renamed from: u */
    private int f10380u = Theme.getColor(Theme.key_actionBarDefault);
    /* renamed from: v */
    private int f10381v;
    /* renamed from: w */
    private int f10382w;
    /* renamed from: x */
    private int f10383x;
    /* renamed from: y */
    private int f10384y;
    /* renamed from: z */
    private OnTabSelectListener f10385z;

    /* renamed from: utils.view.bottombar.BottomBar$1 */
    class C53301 implements TabPropertyUpdater {
        /* renamed from: a */
        final /* synthetic */ BottomBar f10343a;

        C53301(BottomBar bottomBar) {
            this.f10343a = bottomBar;
        }

        /* renamed from: a */
        public void mo4648a(BottomBarTab bottomBarTab) {
            bottomBarTab.setInActiveAlpha(this.f10343a.f10368i);
        }
    }

    /* renamed from: utils.view.bottombar.BottomBar$2 */
    class C53312 implements TabPropertyUpdater {
        /* renamed from: a */
        final /* synthetic */ BottomBar f10344a;

        C53312(BottomBar bottomBar) {
            this.f10344a = bottomBar;
        }

        /* renamed from: a */
        public void mo4648a(BottomBarTab bottomBarTab) {
            bottomBarTab.setActiveAlpha(this.f10344a.f10369j);
        }
    }

    /* renamed from: utils.view.bottombar.BottomBar$3 */
    class C53323 implements TabPropertyUpdater {
        /* renamed from: a */
        final /* synthetic */ BottomBar f10345a;

        C53323(BottomBar bottomBar) {
            this.f10345a = bottomBar;
        }

        /* renamed from: a */
        public void mo4648a(BottomBarTab bottomBarTab) {
            bottomBarTab.setInActiveColor(this.f10345a.f10370k);
        }
    }

    /* renamed from: utils.view.bottombar.BottomBar$4 */
    class C53334 implements TabPropertyUpdater {
        /* renamed from: a */
        final /* synthetic */ BottomBar f10346a;

        C53334(BottomBar bottomBar) {
            this.f10346a = bottomBar;
        }

        /* renamed from: a */
        public void mo4648a(BottomBarTab bottomBarTab) {
            bottomBarTab.setActiveColor(this.f10346a.f10371l);
        }
    }

    /* renamed from: utils.view.bottombar.BottomBar$5 */
    class C53345 implements TabPropertyUpdater {
        /* renamed from: a */
        final /* synthetic */ BottomBar f10347a;

        C53345(BottomBar bottomBar) {
            this.f10347a = bottomBar;
        }

        /* renamed from: a */
        public void mo4648a(BottomBarTab bottomBarTab) {
            bottomBarTab.setBadgeBackgroundColor(this.f10347a.f10372m);
        }
    }

    /* renamed from: utils.view.bottombar.BottomBar$6 */
    class C53356 implements TabPropertyUpdater {
        /* renamed from: a */
        final /* synthetic */ BottomBar f10348a;

        C53356(BottomBar bottomBar) {
            this.f10348a = bottomBar;
        }

        /* renamed from: a */
        public void mo4648a(BottomBarTab bottomBarTab) {
            bottomBarTab.setTitleTextAppearance(this.f10348a.f10373n);
        }
    }

    /* renamed from: utils.view.bottombar.BottomBar$7 */
    class C53367 implements TabPropertyUpdater {
        /* renamed from: a */
        final /* synthetic */ BottomBar f10349a;

        C53367(BottomBar bottomBar) {
            this.f10349a = bottomBar;
        }

        /* renamed from: a */
        public void mo4648a(BottomBarTab bottomBarTab) {
            bottomBarTab.setTitleTypeface(this.f10349a.f10374o);
        }
    }

    public BottomBar(Context context) {
        super(context);
        m14220a(context, null);
    }

    public BottomBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m14220a(context, attributeSet);
    }

    /* renamed from: a */
    private Typeface m14218a(String str) {
        return str != null ? Typeface.createFromAsset(getContext().getAssets(), str) : null;
    }

    /* renamed from: a */
    private BottomBarTab m14219a(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof BottomBarTab) {
                return (BottomBarTab) childAt;
            }
        }
        return null;
    }

    /* renamed from: a */
    private void m14220a(Context context, AttributeSet attributeSet) {
        this.f10360a = new BatchTabPropertyApplier(this);
        m14228b(context, attributeSet);
        m14237e();
        m14240f();
        if (this.f10365f != 0) {
            setItems(this.f10365f);
        }
    }

    /* renamed from: a */
    private void m14221a(View view) {
        BottomBarTab currentTab = getCurrentTab();
        BottomBarTab bottomBarTab = (BottomBarTab) view;
        currentTab.m14309b(true);
        bottomBarTab.m14308a(true);
        m14224a(currentTab, bottomBarTab, true);
        m14225a(bottomBarTab, true);
        m14244g(bottomBarTab.getIndexInTabContainer());
    }

    /* renamed from: a */
    private void m14222a(View view, int i) {
        m14247h(i);
        if (VERSION.SDK_INT < 21) {
            m14250i(i);
        } else if (this.f10377r.isAttachedToWindow()) {
            m14229b(view, i);
        }
    }

    /* renamed from: a */
    private void m14223a(List<BottomBarTab> list) {
        this.f10378s.removeAllViews();
        BottomBarTab[] bottomBarTabArr = new BottomBarTab[list.size()];
        int i = 0;
        int i2 = 0;
        for (BottomBarTab bottomBarTab : list) {
            Type type = m14230b() ? Type.SHIFTING : this.f10366g ? Type.TABLET : Type.FIXED;
            bottomBarTab.setType(type);
            bottomBarTab.m14305a();
            if (i2 == this.f10382w) {
                bottomBarTab.m14308a(true);
                m14225a(bottomBarTab, false);
            } else {
                bottomBarTab.m14309b(false);
            }
            if (this.f10366g) {
                this.f10378s.addView(bottomBarTab);
            } else {
                if (bottomBarTab.getWidth() > i) {
                    i = bottomBarTab.getWidth();
                }
                bottomBarTabArr[i2] = bottomBarTab;
            }
            bottomBarTab.setOnClickListener(this);
            bottomBarTab.setOnLongClickListener(this);
            i2++;
        }
        this.f10359F = bottomBarTabArr;
        if (!this.f10366g) {
            m14226a(bottomBarTabArr);
        }
    }

    /* renamed from: a */
    private void m14224a(BottomBarTab bottomBarTab, BottomBarTab bottomBarTab2, boolean z) {
        if (m14230b()) {
            bottomBarTab.m14306a((float) this.f10383x, z);
            bottomBarTab2.m14306a((float) this.f10384y, z);
        }
    }

    /* renamed from: a */
    private void m14225a(BottomBarTab bottomBarTab, boolean z) {
        int barColorWhenSelected = bottomBarTab.getBarColorWhenSelected();
        if (this.f10381v != barColorWhenSelected) {
            if (z) {
                View outerView;
                if (bottomBarTab.m14311c()) {
                    outerView = bottomBarTab.getOuterView();
                }
                m14222a(outerView, barColorWhenSelected);
                this.f10381v = barColorWhenSelected;
                return;
            }
            this.f10377r.setBackgroundColor(barColorWhenSelected);
        }
    }

    /* renamed from: a */
    private void m14226a(BottomBarTab[] bottomBarTabArr) {
        int a = MiscUtils.m14345a(getContext(), getWidth());
        if (a <= 0 || a > this.f10362c) {
            a = this.f10362c;
        }
        int min = Math.min(MiscUtils.m14344a(getContext(), (float) (a / bottomBarTabArr.length)), this.f10364e);
        this.f10383x = (int) (((double) min) * 0.9d);
        this.f10384y = (int) (((double) min) + (((double) min) * (((double) bottomBarTabArr.length) * 0.1d)));
        int round = Math.round(getContext().getResources().getDimension(R.dimen.bb_height));
        for (View view : bottomBarTabArr) {
            LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = round;
            if (!m14230b()) {
                layoutParams.width = min;
            } else if (view.m14310b()) {
                layoutParams.width = this.f10384y;
            } else {
                layoutParams.width = this.f10383x;
            }
            if (view.getParent() == null) {
                this.f10378s.addView(view);
            }
            view.requestLayout();
        }
    }

    /* renamed from: b */
    private void m14228b(Context context, AttributeSet attributeSet) {
        int i = -1;
        float f = 1.0f;
        this.f10361b = Theme.getColor(Theme.key_actionBarDefault);
        this.f10362c = MiscUtils.m14343a(getContext());
        this.f10363d = MiscUtils.m14344a(getContext(), 10.0f);
        this.f10364e = MiscUtils.m14344a(getContext(), 168.0f);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C3336R.styleable.BottomBar, 0, 0);
        try {
            this.f10365f = obtainStyledAttributes.getResourceId(0, 0);
            this.f10366g = obtainStyledAttributes.getBoolean(1, false);
            this.f10367h = obtainStyledAttributes.getInteger(2, 0);
            if (m14230b()) {
                f = 0.6f;
            }
            this.f10368i = obtainStyledAttributes.getFloat(3, f);
            this.f10369j = obtainStyledAttributes.getFloat(4, 1.0f);
            int c = m14230b() ? -1 : C0235a.c(context, R.color.bb_inActiveBottomBarItemColor);
            if (!m14230b()) {
                i = this.f10361b;
            }
            this.f10370k = obtainStyledAttributes.getColor(5, c);
            this.f10371l = obtainStyledAttributes.getColor(6, i);
            this.f10372m = obtainStyledAttributes.getColor(7, -65536);
            this.f10373n = obtainStyledAttributes.getResourceId(8, 0);
            this.f10374o = m14218a(obtainStyledAttributes.getString(9));
            this.f10375p = obtainStyledAttributes.getBoolean(10, true);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    @TargetApi(21)
    /* renamed from: b */
    private void m14229b(View view, final int i) {
        Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(this.f10376q, (int) (ah.s(view) + ((float) (view.getMeasuredWidth() / 2))), (view.getMeasuredHeight() / 2) + (this.f10366g ? (int) ah.t(view) : 0), (float) null, (float) (this.f10366g ? this.f10377r.getHeight() : this.f10377r.getWidth()));
        if (this.f10366g) {
            createCircularReveal.setDuration(500);
        }
        createCircularReveal.addListener(new AnimatorListenerAdapter(this) {
            /* renamed from: b */
            final /* synthetic */ BottomBar f10351b;

            /* renamed from: a */
            private void m14215a() {
                this.f10351b.f10377r.setBackgroundColor(i);
                this.f10351b.f10376q.setVisibility(4);
                ah.c(this.f10351b.f10376q, 1.0f);
            }

            public void onAnimationCancel(Animator animator) {
                m14215a();
            }

            public void onAnimationEnd(Animator animator) {
                m14215a();
            }
        });
        createCircularReveal.start();
    }

    /* renamed from: b */
    private boolean m14230b() {
        return !this.f10366g && m14238e(1);
    }

    /* renamed from: b */
    private boolean m14231b(View view) {
        if (view instanceof BottomBarTab) {
            BottomBarTab bottomBarTab = (BottomBarTab) view;
            if ((m14230b() || this.f10366g) && !bottomBarTab.m14310b()) {
                Toast.makeText(getContext(), bottomBarTab.getTitle(), 0).show();
            }
        }
        return true;
    }

    /* renamed from: c */
    private boolean m14233c() {
        return !this.f10366g && m14238e(4) && NavbarUtils.m14348b(getContext());
    }

    /* renamed from: d */
    private boolean m14235d() {
        return !this.f10366g && m14238e(2);
    }

    /* renamed from: e */
    private void m14237e() {
        int i = -1;
        int i2 = this.f10366g ? -2 : -1;
        if (!this.f10366g) {
            i = -2;
        }
        LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, i);
        setLayoutParams(layoutParams);
        setOrientation(this.f10366g ? 0 : 1);
        ah.k(this, (float) MiscUtils.m14344a(getContext(), 8.0f));
        View inflate = inflate(getContext(), this.f10366g ? R.layout.bb_bottom_bar_item_container_tablet : R.layout.bb_bottom_bar_item_container, this);
        inflate.setLayoutParams(layoutParams);
        this.f10376q = inflate.findViewById(R.id.bb_bottom_bar_background_overlay);
        this.f10377r = (ViewGroup) inflate.findViewById(R.id.bb_bottom_bar_outer_container);
        this.f10378s = (ViewGroup) inflate.findViewById(R.id.bb_bottom_bar_item_container);
        this.f10379t = inflate.findViewById(R.id.bb_bottom_bar_shadow);
        if (!this.f10375p) {
            this.f10379t.setVisibility(8);
        }
    }

    /* renamed from: e */
    private boolean m14238e(int i) {
        return (this.f10367h | i) == this.f10367h;
    }

    /* renamed from: f */
    private void m14240f() {
        if (m14230b()) {
            this.f10380u = this.f10361b;
        }
        Drawable background = getBackground();
        int i = (background == null || !(background instanceof ColorDrawable)) ? 0 : 1;
        if (i != 0) {
            this.f10380u = ((ColorDrawable) background).getColor();
            setBackgroundColor(0);
        }
    }

    /* renamed from: f */
    private void m14241f(int i) {
        ((C0104d) getLayoutParams()).a(new BottomNavigationBehavior(i, 0, false));
    }

    /* renamed from: g */
    private void m14243g() {
        int tabCount = getTabCount();
        if (this.f10378s != null && tabCount != 0 && m14230b()) {
            for (int i = 0; i < tabCount; i++) {
                TextView titleView = m14257b(i).getTitleView();
                if (titleView != null) {
                    int height = this.f10363d - (titleView.getHeight() - titleView.getBaseline());
                    if (height > 0) {
                        titleView.setPadding(titleView.getPaddingLeft(), titleView.getPaddingTop(), titleView.getPaddingRight(), height + titleView.getPaddingBottom());
                    }
                }
            }
        }
    }

    /* renamed from: g */
    private void m14244g(int i) {
        int id = m14257b(i).getId();
        if (i != this.f10382w) {
            if (this.f10385z != null) {
                this.f10385z.onTabSelected(id);
            }
        } else if (!(this.f10354A == null || this.f10356C)) {
            this.f10354A.m14351a(id);
        }
        this.f10382w = i;
        if (this.f10356C) {
            this.f10356C = false;
        }
    }

    private Config getTabConfig() {
        return new Builder().m14278a(this.f10368i).m14282b(this.f10369j).m14279a(this.f10370k).m14283b(this.f10371l).m14284c(this.f10380u).m14285d(this.f10372m).m14286e(this.f10373n).m14280a(this.f10374o).m14281a();
    }

    /* renamed from: h */
    private void m14246h() {
        ViewParent parent = getParent();
        boolean z = parent != null && (parent instanceof CoordinatorLayout);
        if (!z) {
            throw new RuntimeException("In order to have shy behavior, the BottomBar must be a direct child of a CoordinatorLayout.");
        } else if (!this.f10357D) {
            int height = getHeight();
            if (height != 0) {
                m14241f(height);
                this.f10357D = true;
            }
        }
    }

    /* renamed from: h */
    private void m14247h(int i) {
        this.f10377r.clearAnimation();
        this.f10376q.clearAnimation();
        this.f10376q.setBackgroundColor(i);
        this.f10376q.setVisibility(0);
    }

    /* renamed from: i */
    private void m14249i() {
        if (VERSION.SDK_INT >= 19) {
            int height = getHeight();
            if (height != 0 && !this.f10358E) {
                this.f10358E = true;
                this.f10378s.getLayoutParams().height = height;
                height += NavbarUtils.m14347a(getContext());
                getLayoutParams().height = height;
                if (m14235d()) {
                    m14241f(height);
                }
            }
        }
    }

    /* renamed from: i */
    private void m14250i(final int i) {
        ah.c(this.f10376q, BitmapDescriptorFactory.HUE_RED);
        ah.q(this.f10376q).a(1.0f).a(new bc(this) {
            /* renamed from: b */
            final /* synthetic */ BottomBar f10353b;

            /* renamed from: a */
            private void m14216a() {
                this.f10353b.f10377r.setBackgroundColor(i);
                this.f10353b.f10376q.setVisibility(4);
                ah.c(this.f10353b.f10376q, 1.0f);
            }

            public void onAnimationCancel(View view) {
                m14216a();
            }

            public void onAnimationEnd(View view) {
                m14216a();
            }
        }).c();
    }

    /* renamed from: a */
    Bundle m14251a() {
        Bundle bundle = new Bundle();
        bundle.putInt("STATE_CURRENT_SELECTED_TAB", this.f10382w);
        return bundle;
    }

    /* renamed from: a */
    public void m14252a(int i) {
        m14254a(i, false);
    }

    /* renamed from: a */
    public void m14253a(int i, Config config) {
        if (i == 0) {
            throw new RuntimeException("No items specified for the BottomBar!");
        }
        if (config == null) {
            config = getTabConfig();
        }
        m14223a(new TabParser(getContext(), config, i).m14357a());
    }

    /* renamed from: a */
    public void m14254a(int i, boolean z) {
        if (i > getTabCount() - 1 || i < 0) {
            throw new IndexOutOfBoundsException("Can't select tab at position " + i + ". This BottomBar has no items at that position.");
        }
        BottomBarTab currentTab = getCurrentTab();
        BottomBarTab b = m14257b(i);
        currentTab.m14309b(z);
        b.m14308a(z);
        m14244g(i);
        m14224a(currentTab, b, z);
        m14225a(b, z);
    }

    /* renamed from: a */
    void m14255a(Bundle bundle) {
        if (bundle != null) {
            this.f10355B = true;
            this.f10356C = true;
            m14254a(bundle.getInt("STATE_CURRENT_SELECTED_TAB", this.f10382w), false);
        }
    }

    /* renamed from: a */
    public void m14256a(OnTabSelectListener onTabSelectListener, boolean z) {
        this.f10385z = onTabSelectListener;
        if (z && onTabSelectListener != null && getTabCount() > 0) {
            onTabSelectListener.onTabSelected(getCurrentTabId());
        }
    }

    /* renamed from: b */
    public BottomBarTab m14257b(int i) {
        View childAt = this.f10378s.getChildAt(i);
        return childAt instanceof BadgeContainer ? m14219a((BadgeContainer) childAt) : (BottomBarTab) childAt;
    }

    /* renamed from: c */
    public int m14258c(int i) {
        return m14259d(i).getIndexInTabContainer();
    }

    /* renamed from: d */
    public BottomBarTab m14259d(int i) {
        return (BottomBarTab) this.f10378s.findViewById(i);
    }

    public BottomBarTab getCurrentTab() {
        return m14257b(getCurrentTabPosition());
    }

    public int getCurrentTabId() {
        return getCurrentTab().getId();
    }

    public int getCurrentTabPosition() {
        return this.f10382w;
    }

    public OnTabSelectListener getOnTabSelectListener() {
        return this.f10385z;
    }

    public int getTabCount() {
        return this.f10378s.getChildCount();
    }

    public void onClick(View view) {
        m14221a(view);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            if (!this.f10366g) {
                m14226a(this.f10359F);
            }
            m14243g();
            if (m14235d()) {
                m14246h();
            }
            if (m14233c()) {
                m14249i();
            }
        }
    }

    public boolean onLongClick(View view) {
        return m14231b(view);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            m14255a(bundle);
            parcelable = bundle.getParcelable("superstate");
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        Parcelable a = m14251a();
        a.putParcelable("superstate", super.onSaveInstanceState());
        return a;
    }

    public void setActiveTabAlpha(float f) {
        this.f10369j = f;
        this.f10360a.m14207a(new C53312(this));
    }

    public void setActiveTabColor(int i) {
        this.f10371l = i;
        this.f10360a.m14207a(new C53334(this));
    }

    public void setBadgeBackgroundColor(int i) {
        this.f10372m = i;
        this.f10360a.m14207a(new C53345(this));
    }

    public void setDefaultTab(int i) {
        setDefaultTabPosition(m14258c(i));
    }

    public void setDefaultTabPosition(int i) {
        if (!this.f10355B) {
            m14252a(i);
        }
    }

    public void setInActiveTabAlpha(float f) {
        this.f10368i = f;
        this.f10360a.m14207a(new C53301(this));
    }

    public void setInActiveTabColor(int i) {
        this.f10370k = i;
        this.f10360a.m14207a(new C53323(this));
    }

    public void setItems(int i) {
        m14253a(i, null);
    }

    public void setOnTabReselectListener(OnTabReselectListener onTabReselectListener) {
        this.f10354A = onTabReselectListener;
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        m14256a(onTabSelectListener, true);
    }

    public void setTabTitleTextAppearance(int i) {
        this.f10373n = i;
        this.f10360a.m14207a(new C53356(this));
    }

    public void setTabTitleTypeface(Typeface typeface) {
        this.f10374o = typeface;
        this.f10360a.m14207a(new C53367(this));
    }

    public void setTabTitleTypeface(String str) {
        setTabTitleTypeface(m14218a(str));
    }
}
