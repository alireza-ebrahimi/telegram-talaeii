package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.StateListDrawable;
import android.support.design.C0073a.C0065d;
import android.support.design.C0073a.C0066e;
import android.support.design.C0073a.C0067f;
import android.support.design.C0073a.C0069h;
import android.support.v4.content.p020a.C0405d;
import android.support.v4.p007b.p008a.C0375a;
import android.support.v4.view.C0074a;
import android.support.v4.view.ah;
import android.support.v4.view.p023a.C0531e;
import android.support.v4.widget.C0737z;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.view.menu.C0079p.C0077a;
import android.support.v7.view.menu.C0876j;
import android.support.v7.widget.ao.C0899a;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;

public class NavigationMenuItemView extends C0076a implements C0077a {
    /* renamed from: d */
    private static final int[] f148d = new int[]{16842912};
    /* renamed from: c */
    boolean f149c;
    /* renamed from: e */
    private final int f150e;
    /* renamed from: f */
    private boolean f151f;
    /* renamed from: g */
    private final CheckedTextView f152g;
    /* renamed from: h */
    private FrameLayout f153h;
    /* renamed from: i */
    private C0876j f154i;
    /* renamed from: j */
    private ColorStateList f155j;
    /* renamed from: k */
    private boolean f156k;
    /* renamed from: l */
    private Drawable f157l;
    /* renamed from: m */
    private final C0074a f158m;

    /* renamed from: android.support.design.internal.NavigationMenuItemView$1 */
    class C00751 extends C0074a {
        /* renamed from: a */
        final /* synthetic */ NavigationMenuItemView f126a;

        C00751(NavigationMenuItemView navigationMenuItemView) {
            this.f126a = navigationMenuItemView;
        }

        public void onInitializeAccessibilityNodeInfo(View view, C0531e c0531e) {
            super.onInitializeAccessibilityNodeInfo(view, c0531e);
            c0531e.m2309a(this.f126a.f149c);
        }
    }

    public NavigationMenuItemView(Context context) {
        this(context, null);
    }

    public NavigationMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NavigationMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f158m = new C00751(this);
        setOrientation(0);
        LayoutInflater.from(context).inflate(C0069h.design_navigation_menu_item, this, true);
        this.f150e = context.getResources().getDimensionPixelSize(C0065d.design_navigation_icon_size);
        this.f152g = (CheckedTextView) findViewById(C0067f.design_menu_item_text);
        this.f152g.setDuplicateParentStateEnabled(true);
        ah.m2783a(this.f152g, this.f158m);
    }

    /* renamed from: b */
    private boolean m183b() {
        return this.f154i.getTitle() == null && this.f154i.getIcon() == null && this.f154i.getActionView() != null;
    }

    /* renamed from: c */
    private void m184c() {
        if (m183b()) {
            this.f152g.setVisibility(8);
            if (this.f153h != null) {
                C0899a c0899a = (C0899a) this.f153h.getLayoutParams();
                c0899a.width = -1;
                this.f153h.setLayoutParams(c0899a);
                return;
            }
            return;
        }
        this.f152g.setVisibility(0);
        if (this.f153h != null) {
            c0899a = (C0899a) this.f153h.getLayoutParams();
            c0899a.width = -2;
            this.f153h.setLayoutParams(c0899a);
        }
    }

    /* renamed from: d */
    private StateListDrawable m185d() {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(C0738a.colorControlHighlight, typedValue, true)) {
            return null;
        }
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(f148d, new ColorDrawable(typedValue.data));
        stateListDrawable.addState(EMPTY_STATE_SET, new ColorDrawable(0));
        return stateListDrawable;
    }

    private void setActionView(View view) {
        if (view != null) {
            if (this.f153h == null) {
                this.f153h = (FrameLayout) ((ViewStub) findViewById(C0067f.design_menu_item_action_area_stub)).inflate();
            }
            this.f153h.removeAllViews();
            this.f153h.addView(view);
        }
    }

    /* renamed from: a */
    public void mo43a(C0876j c0876j, int i) {
        this.f154i = c0876j;
        setVisibility(c0876j.isVisible() ? 0 : 8);
        if (getBackground() == null) {
            ah.m2781a((View) this, m185d());
        }
        setCheckable(c0876j.isCheckable());
        setChecked(c0876j.isChecked());
        setEnabled(c0876j.isEnabled());
        setTitle(c0876j.getTitle());
        setIcon(c0876j.getIcon());
        setActionView(c0876j.getActionView());
        m184c();
    }

    /* renamed from: a */
    public boolean mo44a() {
        return false;
    }

    public C0876j getItemData() {
        return this.f154i;
    }

    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (this.f154i != null && this.f154i.isCheckable() && this.f154i.isChecked()) {
            mergeDrawableStates(onCreateDrawableState, f148d);
        }
        return onCreateDrawableState;
    }

    public void setCheckable(boolean z) {
        refreshDrawableState();
        if (this.f149c != z) {
            this.f149c = z;
            this.f158m.sendAccessibilityEvent(this.f152g, 2048);
        }
    }

    public void setChecked(boolean z) {
        refreshDrawableState();
        this.f152g.setChecked(z);
    }

    public void setIcon(Drawable drawable) {
        if (drawable != null) {
            if (this.f156k) {
                ConstantState constantState = drawable.getConstantState();
                if (constantState != null) {
                    drawable = constantState.newDrawable();
                }
                drawable = C0375a.m1784g(drawable).mutate();
                C0375a.m1773a(drawable, this.f155j);
            }
            drawable.setBounds(0, 0, this.f150e, this.f150e);
        } else if (this.f151f) {
            if (this.f157l == null) {
                this.f157l = C0405d.m1869a(getResources(), C0066e.navigation_empty_icon, getContext().getTheme());
                if (this.f157l != null) {
                    this.f157l.setBounds(0, 0, this.f150e, this.f150e);
                }
            }
            drawable = this.f157l;
        }
        C0737z.m3573a(this.f152g, drawable, null, null, null);
    }

    void setIconTintList(ColorStateList colorStateList) {
        this.f155j = colorStateList;
        this.f156k = this.f155j != null;
        if (this.f154i != null) {
            setIcon(this.f154i.getIcon());
        }
    }

    public void setNeedsEmptyIcon(boolean z) {
        this.f151f = z;
    }

    public void setTextAppearance(int i) {
        C0737z.m3572a(this.f152g, i);
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.f152g.setTextColor(colorStateList);
    }

    public void setTitle(CharSequence charSequence) {
        this.f152g.setText(charSequence);
    }
}
