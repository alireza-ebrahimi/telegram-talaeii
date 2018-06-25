package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0743f;
import android.support.v7.p025a.C0748a.C0744g;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.view.menu.C0079p.C0077a;
import android.support.v7.widget.bk;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class ListMenuItemView extends LinearLayout implements C0077a {
    /* renamed from: a */
    private C0876j f2079a;
    /* renamed from: b */
    private ImageView f2080b;
    /* renamed from: c */
    private RadioButton f2081c;
    /* renamed from: d */
    private TextView f2082d;
    /* renamed from: e */
    private CheckBox f2083e;
    /* renamed from: f */
    private TextView f2084f;
    /* renamed from: g */
    private ImageView f2085g;
    /* renamed from: h */
    private Drawable f2086h;
    /* renamed from: i */
    private int f2087i;
    /* renamed from: j */
    private Context f2088j;
    /* renamed from: k */
    private boolean f2089k;
    /* renamed from: l */
    private Drawable f2090l;
    /* renamed from: m */
    private int f2091m;
    /* renamed from: n */
    private LayoutInflater f2092n;
    /* renamed from: o */
    private boolean f2093o;

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0738a.listMenuViewStyle);
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        bk a = bk.m5654a(getContext(), attributeSet, C0747j.MenuView, i, 0);
        this.f2086h = a.m5657a(C0747j.MenuView_android_itemBackground);
        this.f2087i = a.m5670g(C0747j.MenuView_android_itemTextAppearance, -1);
        this.f2089k = a.m5659a(C0747j.MenuView_preserveIconSpacing, false);
        this.f2088j = context;
        this.f2090l = a.m5657a(C0747j.MenuView_subMenuArrow);
        a.m5658a();
    }

    /* renamed from: b */
    private void m4098b() {
        this.f2080b = (ImageView) getInflater().inflate(C0744g.abc_list_menu_item_icon, this, false);
        addView(this.f2080b, 0);
    }

    /* renamed from: c */
    private void m4099c() {
        this.f2081c = (RadioButton) getInflater().inflate(C0744g.abc_list_menu_item_radio, this, false);
        addView(this.f2081c);
    }

    /* renamed from: d */
    private void m4100d() {
        this.f2083e = (CheckBox) getInflater().inflate(C0744g.abc_list_menu_item_checkbox, this, false);
        addView(this.f2083e);
    }

    private LayoutInflater getInflater() {
        if (this.f2092n == null) {
            this.f2092n = LayoutInflater.from(getContext());
        }
        return this.f2092n;
    }

    private void setSubMenuArrowVisible(boolean z) {
        if (this.f2085g != null) {
            this.f2085g.setVisibility(z ? 0 : 8);
        }
    }

    /* renamed from: a */
    public void mo43a(C0876j c0876j, int i) {
        this.f2079a = c0876j;
        this.f2091m = i;
        setVisibility(c0876j.isVisible() ? 0 : 8);
        setTitle(c0876j.m4272a((C0077a) this));
        setCheckable(c0876j.isCheckable());
        m4102a(c0876j.m4285f(), c0876j.m4281d());
        setIcon(c0876j.getIcon());
        setEnabled(c0876j.isEnabled());
        setSubMenuArrowVisible(c0876j.hasSubMenu());
    }

    /* renamed from: a */
    public void m4102a(boolean z, char c) {
        int i = (z && this.f2079a.m4285f()) ? 0 : 8;
        if (i == 0) {
            this.f2084f.setText(this.f2079a.m4283e());
        }
        if (this.f2084f.getVisibility() != i) {
            this.f2084f.setVisibility(i);
        }
    }

    /* renamed from: a */
    public boolean mo44a() {
        return false;
    }

    public C0876j getItemData() {
        return this.f2079a;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ah.m2781a((View) this, this.f2086h);
        this.f2082d = (TextView) findViewById(C0743f.title);
        if (this.f2087i != -1) {
            this.f2082d.setTextAppearance(this.f2088j, this.f2087i);
        }
        this.f2084f = (TextView) findViewById(C0743f.shortcut);
        this.f2085g = (ImageView) findViewById(C0743f.submenuarrow);
        if (this.f2085g != null) {
            this.f2085g.setImageDrawable(this.f2090l);
        }
    }

    protected void onMeasure(int i, int i2) {
        if (this.f2080b != null && this.f2089k) {
            LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.f2080b.getLayoutParams();
            if (layoutParams.height > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = layoutParams.height;
            }
        }
        super.onMeasure(i, i2);
    }

    public void setCheckable(boolean z) {
        if (z || this.f2081c != null || this.f2083e != null) {
            CompoundButton compoundButton;
            CompoundButton compoundButton2;
            if (this.f2079a.m4286g()) {
                if (this.f2081c == null) {
                    m4099c();
                }
                compoundButton = this.f2081c;
                compoundButton2 = this.f2083e;
            } else {
                if (this.f2083e == null) {
                    m4100d();
                }
                compoundButton = this.f2083e;
                compoundButton2 = this.f2081c;
            }
            if (z) {
                compoundButton.setChecked(this.f2079a.isChecked());
                int i = z ? 0 : 8;
                if (compoundButton.getVisibility() != i) {
                    compoundButton.setVisibility(i);
                }
                if (compoundButton2 != null && compoundButton2.getVisibility() != 8) {
                    compoundButton2.setVisibility(8);
                    return;
                }
                return;
            }
            if (this.f2083e != null) {
                this.f2083e.setVisibility(8);
            }
            if (this.f2081c != null) {
                this.f2081c.setVisibility(8);
            }
        }
    }

    public void setChecked(boolean z) {
        CompoundButton compoundButton;
        if (this.f2079a.m4286g()) {
            if (this.f2081c == null) {
                m4099c();
            }
            compoundButton = this.f2081c;
        } else {
            if (this.f2083e == null) {
                m4100d();
            }
            compoundButton = this.f2083e;
        }
        compoundButton.setChecked(z);
    }

    public void setForceShowIcon(boolean z) {
        this.f2093o = z;
        this.f2089k = z;
    }

    public void setIcon(Drawable drawable) {
        int i = (this.f2079a.m4288i() || this.f2093o) ? 1 : 0;
        if (i == 0 && !this.f2089k) {
            return;
        }
        if (this.f2080b != null || drawable != null || this.f2089k) {
            if (this.f2080b == null) {
                m4098b();
            }
            if (drawable != null || this.f2089k) {
                ImageView imageView = this.f2080b;
                if (i == 0) {
                    drawable = null;
                }
                imageView.setImageDrawable(drawable);
                if (this.f2080b.getVisibility() != 0) {
                    this.f2080b.setVisibility(0);
                    return;
                }
                return;
            }
            this.f2080b.setVisibility(8);
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (charSequence != null) {
            this.f2082d.setText(charSequence);
            if (this.f2082d.getVisibility() != 0) {
                this.f2082d.setVisibility(0);
            }
        } else if (this.f2082d.getVisibility() != 8) {
            this.f2082d.setVisibility(8);
        }
    }
}
