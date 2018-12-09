package utils.view.bottombar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ah;
import android.support.v4.view.ax;
import android.support.v7.widget.AppCompatImageView;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;

public class BottomBarTab extends LinearLayout {
    /* renamed from: a */
    BottomBarBadge f10416a;
    /* renamed from: b */
    private final int f10417b;
    /* renamed from: c */
    private final int f10418c;
    /* renamed from: d */
    private final int f10419d;
    /* renamed from: e */
    private Type f10420e = Type.FIXED;
    /* renamed from: f */
    private int f10421f;
    /* renamed from: g */
    private String f10422g;
    /* renamed from: h */
    private float f10423h;
    /* renamed from: i */
    private float f10424i;
    /* renamed from: j */
    private int f10425j;
    /* renamed from: k */
    private int f10426k;
    /* renamed from: l */
    private int f10427l;
    /* renamed from: m */
    private int f10428m;
    /* renamed from: n */
    private AppCompatImageView f10429n;
    /* renamed from: o */
    private TextView f10430o;
    /* renamed from: p */
    private boolean f10431p;
    /* renamed from: q */
    private int f10432q;
    /* renamed from: r */
    private int f10433r;
    /* renamed from: s */
    private Typeface f10434s;

    /* renamed from: utils.view.bottombar.BottomBarTab$1 */
    class C53401 implements AnimatorUpdateListener {
        /* renamed from: a */
        final /* synthetic */ BottomBarTab f10391a;

        C53401(BottomBarTab bottomBarTab) {
            this.f10391a = bottomBarTab;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.f10391a.setColors(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
    }

    /* renamed from: utils.view.bottombar.BottomBarTab$2 */
    class C53412 implements AnimatorUpdateListener {
        /* renamed from: a */
        final /* synthetic */ BottomBarTab f10392a;

        C53412(BottomBarTab bottomBarTab) {
            this.f10392a = bottomBarTab;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            LayoutParams layoutParams = this.f10392a.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = Math.round(((Float) valueAnimator.getAnimatedValue()).floatValue());
                this.f10392a.setLayoutParams(layoutParams);
            }
        }
    }

    /* renamed from: utils.view.bottombar.BottomBarTab$3 */
    class C53423 extends AnimatorListenerAdapter {
        /* renamed from: a */
        final /* synthetic */ BottomBarTab f10393a;

        C53423(BottomBarTab bottomBarTab) {
            this.f10393a = bottomBarTab;
        }

        public void onAnimationEnd(Animator animator) {
            if (!this.f10393a.f10431p && this.f10393a.f10416a != null) {
                this.f10393a.f10416a.m14270b(this.f10393a);
                this.f10393a.f10416a.m14268b();
            }
        }
    }

    /* renamed from: utils.view.bottombar.BottomBarTab$4 */
    class C53434 implements AnimatorUpdateListener {
        /* renamed from: a */
        final /* synthetic */ BottomBarTab f10394a;

        C53434(BottomBarTab bottomBarTab) {
            this.f10394a = bottomBarTab;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.f10394a.f10429n.setPadding(this.f10394a.f10429n.getPaddingLeft(), ((Integer) valueAnimator.getAnimatedValue()).intValue(), this.f10394a.f10429n.getPaddingRight(), this.f10394a.f10429n.getPaddingBottom());
        }
    }

    public static class Config {
        /* renamed from: a */
        private final float f10404a;
        /* renamed from: b */
        private final float f10405b;
        /* renamed from: c */
        private final int f10406c;
        /* renamed from: d */
        private final int f10407d;
        /* renamed from: e */
        private final int f10408e;
        /* renamed from: f */
        private final int f10409f;
        /* renamed from: g */
        private final int f10410g;
        /* renamed from: h */
        private final Typeface f10411h;

        public static class Builder {
            /* renamed from: a */
            private float f10396a;
            /* renamed from: b */
            private float f10397b;
            /* renamed from: c */
            private int f10398c;
            /* renamed from: d */
            private int f10399d;
            /* renamed from: e */
            private int f10400e;
            /* renamed from: f */
            private int f10401f;
            /* renamed from: g */
            private int f10402g;
            /* renamed from: h */
            private Typeface f10403h;

            /* renamed from: a */
            public Builder m14280a(float f) {
                this.f10396a = f;
                return this;
            }

            /* renamed from: a */
            public Builder m14281a(int i) {
                this.f10398c = i;
                return this;
            }

            /* renamed from: a */
            public Builder m14282a(Typeface typeface) {
                this.f10403h = typeface;
                return this;
            }

            /* renamed from: a */
            public Config m14283a() {
                return new Config();
            }

            /* renamed from: b */
            public Builder m14284b(float f) {
                this.f10397b = f;
                return this;
            }

            /* renamed from: b */
            public Builder m14285b(int i) {
                this.f10399d = i;
                return this;
            }

            /* renamed from: c */
            public Builder m14286c(int i) {
                this.f10400e = i;
                return this;
            }

            /* renamed from: d */
            public Builder m14287d(int i) {
                this.f10401f = i;
                return this;
            }

            /* renamed from: e */
            public Builder m14288e(int i) {
                this.f10402g = i;
                return this;
            }
        }

        private Config(Builder builder) {
            this.f10404a = builder.f10396a;
            this.f10405b = builder.f10397b;
            this.f10406c = builder.f10398c;
            this.f10407d = builder.f10399d;
            this.f10408e = builder.f10400e;
            this.f10409f = builder.f10401f;
            this.f10410g = builder.f10402g;
            this.f10411h = builder.f10403h;
        }
    }

    enum Type {
        FIXED,
        SHIFTING,
        TABLET
    }

    BottomBarTab(Context context) {
        super(context);
        this.f10417b = MiscUtils.m14346a(context, 6.0f);
        this.f10418c = MiscUtils.m14346a(context, 8.0f);
        this.f10419d = MiscUtils.m14346a(context, 16.0f);
    }

    /* renamed from: a */
    private void m14297a(float f) {
        ah.q(this.f10429n).a(150).a(f).c();
    }

    /* renamed from: a */
    private void m14298a(float f, float f2) {
        if (this.f10420e != Type.TABLET) {
            ax e = ah.q(this.f10430o).a(150).d(f).e(f);
            e.a(f2);
            e.c();
        }
    }

    /* renamed from: a */
    private void m14299a(int i, int i2) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(new int[]{i, i2});
        valueAnimator.setEvaluator(new ArgbEvaluator());
        valueAnimator.addUpdateListener(new C53401(this));
        valueAnimator.setDuration(150);
        valueAnimator.start();
    }

    /* renamed from: b */
    private void m14303b(int i, int i2) {
        if (this.f10420e != Type.TABLET) {
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{i, i2});
            ofInt.addUpdateListener(new C53434(this));
            ofInt.setDuration(150);
            ofInt.start();
        }
    }

    /* renamed from: e */
    private void m14304e() {
        if (this.f10430o != null) {
            this.f10430o.setText(this.f10422g);
        }
    }

    /* renamed from: f */
    private void m14305f() {
        if (this.f10430o != null && this.f10433r != 0) {
            if (VERSION.SDK_INT >= 23) {
                this.f10430o.setTextAppearance(this.f10433r);
            } else {
                this.f10430o.setTextAppearance(getContext(), this.f10433r);
            }
            this.f10430o.setTag(Integer.valueOf(this.f10433r));
        }
    }

    /* renamed from: g */
    private void m14306g() {
        if (this.f10434s != null && this.f10430o != null) {
            this.f10430o.setTypeface(this.f10434s);
        }
    }

    private void setAlphas(float f) {
        if (this.f10429n != null) {
            ah.c(this.f10429n, f);
        }
        if (this.f10430o != null) {
            ah.c(this.f10430o, f);
        }
    }

    private void setColors(int i) {
        if (this.f10429n != null) {
            this.f10429n.setColorFilter(i);
            this.f10429n.setTag(Integer.valueOf(i));
        }
        if (this.f10430o != null) {
            this.f10430o.setTextColor(i);
        }
    }

    private void setTitleScale(float f) {
        if (this.f10420e != Type.TABLET) {
            ah.g(this.f10430o, f);
            ah.h(this.f10430o, f);
        }
    }

    private void setTopPadding(int i) {
        if (this.f10420e != Type.TABLET) {
            this.f10429n.setPadding(this.f10429n.getPaddingLeft(), i, this.f10429n.getPaddingRight(), this.f10429n.getPaddingBottom());
        }
    }

    /* renamed from: a */
    void m14307a() {
        inflate(getContext(), getLayoutResource(), this);
        setOrientation(1);
        setGravity(17);
        setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.f10429n = (AppCompatImageView) findViewById(R.id.bb_bottom_bar_icon);
        this.f10429n.setImageResource(this.f10421f);
        if (this.f10420e != Type.TABLET) {
            this.f10430o = (TextView) findViewById(R.id.bb_bottom_bar_title);
            m14304e();
        }
        m14305f();
        m14306g();
    }

    /* renamed from: a */
    void m14308a(float f, boolean z) {
        if (z) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{(float) getWidth(), f});
            ofFloat.setDuration(150);
            ofFloat.addUpdateListener(new C53412(this));
            ofFloat.addListener(new C53423(this));
            ofFloat.start();
            return;
        }
        getLayoutParams().width = (int) f;
        if (!this.f10431p && this.f10416a != null) {
            this.f10416a.m14270b(this);
            this.f10416a.m14268b();
        }
    }

    /* renamed from: a */
    void m14309a(Bundle bundle) {
        setBadgeCount(bundle.getInt("STATE_BADGE_COUNT_FOR_TAB_" + getIndexInTabContainer()));
    }

    /* renamed from: a */
    void m14310a(boolean z) {
        this.f10431p = true;
        if (z) {
            m14303b(this.f10429n.getPaddingTop(), this.f10417b);
            m14297a(this.f10424i);
            m14298a(1.0f, this.f10424i);
            m14299a(this.f10425j, this.f10426k);
        } else {
            setTitleScale(1.0f);
            setTopPadding(this.f10417b);
            setColors(this.f10426k);
            setAlphas(this.f10424i);
        }
        if (this.f10416a != null) {
            this.f10416a.m14271c();
        }
    }

    /* renamed from: b */
    void m14311b(boolean z) {
        this.f10431p = false;
        boolean z2 = this.f10420e == Type.SHIFTING;
        float f = z2 ? BitmapDescriptorFactory.HUE_RED : 0.86f;
        int i = z2 ? this.f10419d : this.f10418c;
        if (z) {
            m14303b(this.f10429n.getPaddingTop(), i);
            m14298a(f, this.f10423h);
            m14297a(this.f10423h);
            m14299a(this.f10426k, this.f10425j);
        } else {
            setTitleScale(f);
            setTopPadding(i);
            setColors(this.f10425j);
            setAlphas(this.f10423h);
        }
        if (!z2 && this.f10416a != null) {
            this.f10416a.m14268b();
        }
    }

    /* renamed from: b */
    boolean m14312b() {
        return this.f10431p;
    }

    /* renamed from: c */
    boolean m14313c() {
        return this.f10416a != null;
    }

    /* renamed from: d */
    Bundle m14314d() {
        Bundle bundle = new Bundle();
        bundle.putInt("STATE_BADGE_COUNT_FOR_TAB_" + getIndexInTabContainer(), this.f10416a.m14264a());
        return bundle;
    }

    public float getActiveAlpha() {
        return this.f10424i;
    }

    public int getActiveColor() {
        return this.f10426k;
    }

    public int getBadgeBackgroundColor() {
        return this.f10428m;
    }

    public int getBarColorWhenSelected() {
        return this.f10427l;
    }

    int getCurrentDisplayedIconColor() {
        return this.f10429n.getTag() instanceof Integer ? ((Integer) this.f10429n.getTag()).intValue() : 0;
    }

    int getCurrentDisplayedTextAppearance() {
        return (this.f10430o == null || !(this.f10430o.getTag() instanceof Integer)) ? 0 : ((Integer) this.f10430o.getTag()).intValue();
    }

    int getCurrentDisplayedTitleColor() {
        return this.f10430o != null ? this.f10430o.getCurrentTextColor() : 0;
    }

    int getIconResId() {
        return this.f10421f;
    }

    AppCompatImageView getIconView() {
        return this.f10429n;
    }

    public float getInActiveAlpha() {
        return this.f10423h;
    }

    public int getInActiveColor() {
        return this.f10425j;
    }

    int getIndexInTabContainer() {
        return this.f10432q;
    }

    int getLayoutResource() {
        switch (this.f10420e) {
            case FIXED:
                return R.layout.bb_bottom_bar_item_fixed;
            case SHIFTING:
                return R.layout.bb_bottom_bar_item_shifting;
            case TABLET:
                return R.layout.bb_bottom_bar_item_fixed_tablet;
            default:
                throw new RuntimeException("Unknown BottomBarTab type.");
        }
    }

    public ViewGroup getOuterView() {
        return (ViewGroup) getParent();
    }

    public String getTitle() {
        return this.f10422g;
    }

    public int getTitleTextAppearance() {
        return this.f10433r;
    }

    public Typeface getTitleTypeFace() {
        return this.f10434s;
    }

    TextView getTitleView() {
        return this.f10430o;
    }

    Type getType() {
        return this.f10420e;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            m14309a(bundle);
            parcelable = bundle.getParcelable("superstate");
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        if (this.f10416a == null) {
            return super.onSaveInstanceState();
        }
        Parcelable d = m14314d();
        d.putParcelable("superstate", super.onSaveInstanceState());
        return d;
    }

    public void setActiveAlpha(float f) {
        this.f10424i = f;
        if (this.f10431p) {
            setAlphas(f);
        }
    }

    public void setActiveColor(int i) {
        this.f10426k = i;
        if (this.f10431p) {
            setColors(this.f10426k);
        }
    }

    public void setBadgeBackgroundColor(int i) {
        this.f10428m = i;
        if (this.f10416a != null) {
            this.f10416a.m14269b(i);
        }
    }

    public void setBadgeCount(int i) {
        if (i > 0) {
            if (this.f10416a == null) {
                this.f10416a = new BottomBarBadge(getContext());
                this.f10416a.m14267a(this, this.f10428m);
            }
            this.f10416a.m14265a(i);
        } else if (this.f10416a != null) {
            this.f10416a.m14266a(this);
            this.f10416a = null;
        }
    }

    public void setBarColorWhenSelected(int i) {
        this.f10427l = i;
    }

    void setConfig(Config config) {
        setInActiveAlpha(config.f10404a);
        setActiveAlpha(config.f10405b);
        setInActiveColor(config.f10406c);
        setActiveColor(config.f10407d);
        setBarColorWhenSelected(config.f10408e);
        setBadgeBackgroundColor(config.f10409f);
        setTitleTextAppearance(config.f10410g);
        setTitleTypeface(config.f10411h);
    }

    void setIconResId(int i) {
        this.f10421f = i;
    }

    void setIconTint(int i) {
        this.f10429n.setColorFilter(i);
    }

    public void setInActiveAlpha(float f) {
        this.f10423h = f;
        if (!this.f10431p) {
            setAlphas(f);
        }
    }

    public void setInActiveColor(int i) {
        this.f10425j = i;
        if (!this.f10431p) {
            setColors(i);
        }
    }

    void setIndexInContainer(int i) {
        this.f10432q = i;
    }

    public void setTitle(String str) {
        this.f10422g = str;
        m14304e();
    }

    void setTitleTextAppearance(int i) {
        this.f10433r = i;
        m14305f();
    }

    public void setTitleTypeface(Typeface typeface) {
        this.f10434s = typeface;
        m14306g();
    }

    void setType(Type type) {
        this.f10420e = type;
    }
}
