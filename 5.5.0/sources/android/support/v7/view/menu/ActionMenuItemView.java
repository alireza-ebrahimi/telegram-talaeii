package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.content.p020a.C0402a;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.view.menu.C0079p.C0077a;
import android.support.v7.view.menu.C0873h.C0857b;
import android.support.v7.widget.ActionMenuView.C0856a;
import android.support.v7.widget.C0855y;
import android.support.v7.widget.al;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Toast;

public class ActionMenuItemView extends C0855y implements C0077a, C0856a, OnClickListener, OnLongClickListener {
    /* renamed from: a */
    C0876j f2065a;
    /* renamed from: b */
    C0857b f2066b;
    /* renamed from: c */
    C0854b f2067c;
    /* renamed from: d */
    private CharSequence f2068d;
    /* renamed from: e */
    private Drawable f2069e;
    /* renamed from: f */
    private al f2070f;
    /* renamed from: g */
    private boolean f2071g;
    /* renamed from: h */
    private boolean f2072h;
    /* renamed from: i */
    private int f2073i;
    /* renamed from: j */
    private int f2074j;
    /* renamed from: k */
    private int f2075k;

    /* renamed from: android.support.v7.view.menu.ActionMenuItemView$a */
    private class C0853a extends al {
        /* renamed from: a */
        final /* synthetic */ ActionMenuItemView f2062a;

        public C0853a(ActionMenuItemView actionMenuItemView) {
            this.f2062a = actionMenuItemView;
            super(actionMenuItemView);
        }

        /* renamed from: a */
        public C0867s mo703a() {
            return this.f2062a.f2067c != null ? this.f2062a.f2067c.mo996a() : null;
        }

        /* renamed from: b */
        protected boolean mo704b() {
            if (this.f2062a.f2066b == null || !this.f2062a.f2066b.mo707a(this.f2062a.f2065a)) {
                return false;
            }
            C0867s a = mo703a();
            return a != null && a.mo739d();
        }
    }

    /* renamed from: android.support.v7.view.menu.ActionMenuItemView$b */
    public static abstract class C0854b {
        /* renamed from: a */
        public abstract C0867s mo996a();
    }

    public ActionMenuItemView(Context context) {
        this(context, null);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Resources resources = context.getResources();
        this.f2071g = m4088e();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0747j.ActionMenuItemView, i, 0);
        this.f2073i = obtainStyledAttributes.getDimensionPixelSize(C0747j.ActionMenuItemView_android_minWidth, 0);
        obtainStyledAttributes.recycle();
        this.f2075k = (int) ((resources.getDisplayMetrics().density * 32.0f) + 0.5f);
        setOnClickListener(this);
        setOnLongClickListener(this);
        this.f2074j = -1;
        setSaveEnabled(false);
    }

    /* renamed from: e */
    private boolean m4088e() {
        Configuration configuration = getContext().getResources().getConfiguration();
        int b = C0402a.m1861b(getResources());
        return b >= 480 || ((b >= 640 && C0402a.m1860a(getResources()) >= 480) || configuration.orientation == 2);
    }

    /* renamed from: f */
    private void m4089f() {
        int i = 0;
        int i2 = !TextUtils.isEmpty(this.f2068d) ? 1 : 0;
        if (this.f2069e == null || (this.f2065a.m4292m() && (this.f2071g || this.f2072h))) {
            i = 1;
        }
        setText((i2 & i) != 0 ? this.f2068d : null);
    }

    /* renamed from: a */
    public void mo43a(C0876j c0876j, int i) {
        this.f2065a = c0876j;
        setIcon(c0876j.getIcon());
        setTitle(c0876j.m4272a((C0077a) this));
        setId(c0876j.getItemId());
        setVisibility(c0876j.isVisible() ? 0 : 8);
        setEnabled(c0876j.isEnabled());
        if (c0876j.hasSubMenu() && this.f2070f == null) {
            this.f2070f = new C0853a(this);
        }
    }

    /* renamed from: a */
    public boolean mo44a() {
        return true;
    }

    /* renamed from: b */
    public boolean m4092b() {
        return !TextUtils.isEmpty(getText());
    }

    /* renamed from: c */
    public boolean mo705c() {
        return m4092b() && this.f2065a.getIcon() == null;
    }

    /* renamed from: d */
    public boolean mo706d() {
        return m4092b();
    }

    public C0876j getItemData() {
        return this.f2065a;
    }

    public void onClick(View view) {
        if (this.f2066b != null) {
            this.f2066b.mo707a(this.f2065a);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.f2071g = m4088e();
        m4089f();
    }

    public boolean onLongClick(View view) {
        if (m4092b()) {
            return false;
        }
        int[] iArr = new int[2];
        Rect rect = new Rect();
        getLocationOnScreen(iArr);
        getWindowVisibleDisplayFrame(rect);
        Context context = getContext();
        int width = getWidth();
        int height = getHeight();
        int i = iArr[1] + (height / 2);
        width = (width / 2) + iArr[0];
        if (ah.m2812g(view) == 0) {
            width = context.getResources().getDisplayMetrics().widthPixels - width;
        }
        Toast makeText = Toast.makeText(context, this.f2065a.getTitle(), 0);
        if (i < rect.height()) {
            makeText.setGravity(8388661, width, (iArr[1] + height) - rect.top);
        } else {
            makeText.setGravity(81, 0, height);
        }
        makeText.show();
        return true;
    }

    protected void onMeasure(int i, int i2) {
        boolean b = m4092b();
        if (b && this.f2074j >= 0) {
            super.setPadding(this.f2074j, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(i, i2);
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int measuredWidth = getMeasuredWidth();
        size = mode == Integer.MIN_VALUE ? Math.min(size, this.f2073i) : this.f2073i;
        if (mode != 1073741824 && this.f2073i > 0 && measuredWidth < size) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(size, 1073741824), i2);
        }
        if (!b && this.f2069e != null) {
            super.setPadding((getMeasuredWidth() - this.f2069e.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return (this.f2065a.hasSubMenu() && this.f2070f != null && this.f2070f.onTouch(this, motionEvent)) ? true : super.onTouchEvent(motionEvent);
    }

    public void setCheckable(boolean z) {
    }

    public void setChecked(boolean z) {
    }

    public void setExpandedFormat(boolean z) {
        if (this.f2072h != z) {
            this.f2072h = z;
            if (this.f2065a != null) {
                this.f2065a.m4287h();
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.f2069e = drawable;
        if (drawable != null) {
            float f;
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > this.f2075k) {
                f = ((float) this.f2075k) / ((float) intrinsicWidth);
                intrinsicWidth = this.f2075k;
                intrinsicHeight = (int) (((float) intrinsicHeight) * f);
            }
            if (intrinsicHeight > this.f2075k) {
                f = ((float) this.f2075k) / ((float) intrinsicHeight);
                intrinsicHeight = this.f2075k;
                intrinsicWidth = (int) (((float) intrinsicWidth) * f);
            }
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        }
        setCompoundDrawables(drawable, null, null, null);
        m4089f();
    }

    public void setItemInvoker(C0857b c0857b) {
        this.f2066b = c0857b;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.f2074j = i;
        super.setPadding(i, i2, i3, i4);
    }

    public void setPopupCallback(C0854b c0854b) {
        this.f2067c = c0854b;
    }

    public void setTitle(CharSequence charSequence) {
        this.f2068d = charSequence;
        setContentDescription(this.f2068d);
        m4089f();
    }
}
