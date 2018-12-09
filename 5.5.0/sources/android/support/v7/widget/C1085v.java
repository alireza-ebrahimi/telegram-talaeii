package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ae;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0744g;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.p027c.p028a.C0825b;
import android.support.v7.view.C0844d;
import android.support.v7.view.menu.C0867s;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;

/* renamed from: android.support.v7.widget.v */
public class C1085v extends Spinner implements ae {
    /* renamed from: d */
    private static final int[] f3214d = new int[]{16843505};
    /* renamed from: a */
    C1084b f3215a;
    /* renamed from: b */
    int f3216b;
    /* renamed from: c */
    final Rect f3217c;
    /* renamed from: e */
    private C1061h f3218e;
    /* renamed from: f */
    private Context f3219f;
    /* renamed from: g */
    private al f3220g;
    /* renamed from: h */
    private SpinnerAdapter f3221h;
    /* renamed from: i */
    private boolean f3222i;

    /* renamed from: android.support.v7.widget.v$a */
    private static class C1080a implements ListAdapter, SpinnerAdapter {
        /* renamed from: a */
        private SpinnerAdapter f3203a;
        /* renamed from: b */
        private ListAdapter f3204b;

        public C1080a(SpinnerAdapter spinnerAdapter, Theme theme) {
            this.f3203a = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                this.f3204b = (ListAdapter) spinnerAdapter;
            }
            if (theme == null) {
                return;
            }
            if (VERSION.SDK_INT >= 23 && (spinnerAdapter instanceof ThemedSpinnerAdapter)) {
                ThemedSpinnerAdapter themedSpinnerAdapter = (ThemedSpinnerAdapter) spinnerAdapter;
                if (themedSpinnerAdapter.getDropDownViewTheme() != theme) {
                    themedSpinnerAdapter.setDropDownViewTheme(theme);
                }
            } else if (spinnerAdapter instanceof bg) {
                bg bgVar = (bg) spinnerAdapter;
                if (bgVar.m5647a() == null) {
                    bgVar.m5648a(theme);
                }
            }
        }

        public boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.f3204b;
            return listAdapter != null ? listAdapter.areAllItemsEnabled() : true;
        }

        public int getCount() {
            return this.f3203a == null ? 0 : this.f3203a.getCount();
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return this.f3203a == null ? null : this.f3203a.getDropDownView(i, view, viewGroup);
        }

        public Object getItem(int i) {
            return this.f3203a == null ? null : this.f3203a.getItem(i);
        }

        public long getItemId(int i) {
            return this.f3203a == null ? -1 : this.f3203a.getItemId(i);
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getDropDownView(i, view, viewGroup);
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean hasStableIds() {
            return this.f3203a != null && this.f3203a.hasStableIds();
        }

        public boolean isEmpty() {
            return getCount() == 0;
        }

        public boolean isEnabled(int i) {
            ListAdapter listAdapter = this.f3204b;
            return listAdapter != null ? listAdapter.isEnabled(i) : true;
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            if (this.f3203a != null) {
                this.f3203a.registerDataSetObserver(dataSetObserver);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            if (this.f3203a != null) {
                this.f3203a.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    /* renamed from: android.support.v7.widget.v$b */
    private class C1084b extends ap {
        /* renamed from: a */
        ListAdapter f3210a;
        /* renamed from: b */
        final /* synthetic */ C1085v f3211b;
        /* renamed from: h */
        private CharSequence f3212h;
        /* renamed from: i */
        private final Rect f3213i = new Rect();

        /* renamed from: android.support.v7.widget.v$b$2 */
        class C10822 implements OnGlobalLayoutListener {
            /* renamed from: a */
            final /* synthetic */ C1084b f3207a;

            C10822(C1084b c1084b) {
                this.f3207a = c1084b;
            }

            public void onGlobalLayout() {
                if (this.f3207a.m5911a(this.f3207a.f3211b)) {
                    this.f3207a.mo1014f();
                    super.mo729a();
                    return;
                }
                this.f3207a.mo736c();
            }
        }

        public C1084b(final C1085v c1085v, Context context, AttributeSet attributeSet, int i) {
            this.f3211b = c1085v;
            super(context, attributeSet, i);
            m5480b((View) c1085v);
            m5478a(true);
            m5472a(0);
            m5475a(new OnItemClickListener(this) {
                /* renamed from: b */
                final /* synthetic */ C1084b f3206b;

                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    this.f3206b.f3211b.setSelection(i);
                    if (this.f3206b.f3211b.getOnItemClickListener() != null) {
                        this.f3206b.f3211b.performItemClick(view, i, this.f3206b.f3210a.getItemId(i));
                    }
                    this.f3206b.mo736c();
                }
            });
        }

        /* renamed from: a */
        public void mo729a() {
            boolean d = mo739d();
            mo1014f();
            m5491h(2);
            super.mo729a();
            mo740e().setChoiceMode(1);
            m5493i(this.f3211b.getSelectedItemPosition());
            if (!d) {
                ViewTreeObserver viewTreeObserver = this.f3211b.getViewTreeObserver();
                if (viewTreeObserver != null) {
                    final OnGlobalLayoutListener c10822 = new C10822(this);
                    viewTreeObserver.addOnGlobalLayoutListener(c10822);
                    m5477a(new OnDismissListener(this) {
                        /* renamed from: b */
                        final /* synthetic */ C1084b f3209b;

                        public void onDismiss() {
                            ViewTreeObserver viewTreeObserver = this.f3209b.f3211b.getViewTreeObserver();
                            if (viewTreeObserver != null) {
                                viewTreeObserver.removeGlobalOnLayoutListener(c10822);
                            }
                        }
                    });
                }
            }
        }

        /* renamed from: a */
        public void mo1012a(ListAdapter listAdapter) {
            super.mo1012a(listAdapter);
            this.f3210a = listAdapter;
        }

        /* renamed from: a */
        public void m5910a(CharSequence charSequence) {
            this.f3212h = charSequence;
        }

        /* renamed from: a */
        boolean m5911a(View view) {
            return ah.m2769I(view) && view.getGlobalVisibleRect(this.f3213i);
        }

        /* renamed from: b */
        public CharSequence mo1013b() {
            return this.f3212h;
        }

        /* renamed from: f */
        void mo1014f() {
            int i;
            Drawable h = m5490h();
            if (h != null) {
                h.getPadding(this.f3211b.f3217c);
                i = bp.m5747a(this.f3211b) ? this.f3211b.f3217c.right : -this.f3211b.f3217c.left;
            } else {
                Rect rect = this.f3211b.f3217c;
                this.f3211b.f3217c.right = 0;
                rect.left = 0;
                i = 0;
            }
            int paddingLeft = this.f3211b.getPaddingLeft();
            int paddingRight = this.f3211b.getPaddingRight();
            int width = this.f3211b.getWidth();
            if (this.f3211b.f3216b == -2) {
                int a = this.f3211b.m5914a((SpinnerAdapter) this.f3210a, m5490h());
                int i2 = (this.f3211b.getContext().getResources().getDisplayMetrics().widthPixels - this.f3211b.f3217c.left) - this.f3211b.f3217c.right;
                if (a <= i2) {
                    i2 = a;
                }
                m5488g(Math.max(i2, (width - paddingLeft) - paddingRight));
            } else if (this.f3211b.f3216b == -1) {
                m5488g((width - paddingLeft) - paddingRight);
            } else {
                m5488g(this.f3211b.f3216b);
            }
            m5482c(bp.m5747a(this.f3211b) ? ((width - paddingRight) - m5496l()) + i : i + paddingLeft);
        }
    }

    public C1085v(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0738a.spinnerStyle);
    }

    public C1085v(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, -1);
    }

    public C1085v(Context context, AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i, i2, null);
    }

    public C1085v(Context context, AttributeSet attributeSet, int i, int i2, Theme theme) {
        Throwable e;
        CharSequence[] f;
        SpinnerAdapter arrayAdapter;
        super(context, attributeSet, i);
        this.f3217c = new Rect();
        bk a = bk.m5654a(context, attributeSet, C0747j.Spinner, i, 0);
        this.f3218e = new C1061h(this);
        if (theme != null) {
            this.f3219f = new C0844d(context, theme);
        } else {
            int g = a.m5670g(C0747j.Spinner_popupTheme, 0);
            if (g != 0) {
                this.f3219f = new C0844d(context, g);
            } else {
                this.f3219f = VERSION.SDK_INT < 23 ? context : null;
            }
        }
        if (this.f3219f != null) {
            final C1084b c1084b;
            bk a2;
            if (i2 == -1) {
                if (VERSION.SDK_INT >= 11) {
                    TypedArray obtainStyledAttributes;
                    try {
                        obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f3214d, i, 0);
                        try {
                            if (obtainStyledAttributes.hasValue(0)) {
                                i2 = obtainStyledAttributes.getInt(0, 0);
                            }
                            if (obtainStyledAttributes != null) {
                                obtainStyledAttributes.recycle();
                            }
                        } catch (Exception e2) {
                            e = e2;
                            try {
                                Log.i("AppCompatSpinner", "Could not read android:spinnerMode", e);
                                if (obtainStyledAttributes != null) {
                                    obtainStyledAttributes.recycle();
                                }
                                if (i2 == 1) {
                                    c1084b = new C1084b(this, this.f3219f, attributeSet, i);
                                    a2 = bk.m5654a(this.f3219f, attributeSet, C0747j.Spinner, i, 0);
                                    this.f3216b = a2.m5668f(C0747j.Spinner_android_dropDownWidth, -2);
                                    c1084b.m5474a(a2.m5657a(C0747j.Spinner_android_popupBackground));
                                    c1084b.m5910a(a.m5665d(C0747j.Spinner_android_prompt));
                                    a2.m5658a();
                                    this.f3215a = c1084b;
                                    this.f3220g = new al(this, this) {
                                        /* renamed from: b */
                                        final /* synthetic */ C1085v f3202b;

                                        /* renamed from: a */
                                        public C0867s mo703a() {
                                            return c1084b;
                                        }

                                        /* renamed from: b */
                                        public boolean mo704b() {
                                            if (!this.f3202b.f3215a.mo739d()) {
                                                this.f3202b.f3215a.mo729a();
                                            }
                                            return true;
                                        }
                                    };
                                }
                                f = a.m5669f(C0747j.Spinner_android_entries);
                                if (f != null) {
                                    arrayAdapter = new ArrayAdapter(context, 17367048, f);
                                    arrayAdapter.setDropDownViewResource(C0744g.support_simple_spinner_dropdown_item);
                                    setAdapter(arrayAdapter);
                                }
                                a.m5658a();
                                this.f3222i = true;
                                if (this.f3221h != null) {
                                    setAdapter(this.f3221h);
                                    this.f3221h = null;
                                }
                                this.f3218e.m5841a(attributeSet, i);
                            } catch (Throwable th) {
                                e = th;
                                if (obtainStyledAttributes != null) {
                                    obtainStyledAttributes.recycle();
                                }
                                throw e;
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        obtainStyledAttributes = null;
                        Log.i("AppCompatSpinner", "Could not read android:spinnerMode", e);
                        if (obtainStyledAttributes != null) {
                            obtainStyledAttributes.recycle();
                        }
                        if (i2 == 1) {
                            c1084b = new C1084b(this, this.f3219f, attributeSet, i);
                            a2 = bk.m5654a(this.f3219f, attributeSet, C0747j.Spinner, i, 0);
                            this.f3216b = a2.m5668f(C0747j.Spinner_android_dropDownWidth, -2);
                            c1084b.m5474a(a2.m5657a(C0747j.Spinner_android_popupBackground));
                            c1084b.m5910a(a.m5665d(C0747j.Spinner_android_prompt));
                            a2.m5658a();
                            this.f3215a = c1084b;
                            this.f3220g = /* anonymous class already generated */;
                        }
                        f = a.m5669f(C0747j.Spinner_android_entries);
                        if (f != null) {
                            arrayAdapter = new ArrayAdapter(context, 17367048, f);
                            arrayAdapter.setDropDownViewResource(C0744g.support_simple_spinner_dropdown_item);
                            setAdapter(arrayAdapter);
                        }
                        a.m5658a();
                        this.f3222i = true;
                        if (this.f3221h != null) {
                            setAdapter(this.f3221h);
                            this.f3221h = null;
                        }
                        this.f3218e.m5841a(attributeSet, i);
                    } catch (Throwable th2) {
                        e = th2;
                        obtainStyledAttributes = null;
                        if (obtainStyledAttributes != null) {
                            obtainStyledAttributes.recycle();
                        }
                        throw e;
                    }
                }
                i2 = 1;
            }
            if (i2 == 1) {
                c1084b = new C1084b(this, this.f3219f, attributeSet, i);
                a2 = bk.m5654a(this.f3219f, attributeSet, C0747j.Spinner, i, 0);
                this.f3216b = a2.m5668f(C0747j.Spinner_android_dropDownWidth, -2);
                c1084b.m5474a(a2.m5657a(C0747j.Spinner_android_popupBackground));
                c1084b.m5910a(a.m5665d(C0747j.Spinner_android_prompt));
                a2.m5658a();
                this.f3215a = c1084b;
                this.f3220g = /* anonymous class already generated */;
            }
        }
        f = a.m5669f(C0747j.Spinner_android_entries);
        if (f != null) {
            arrayAdapter = new ArrayAdapter(context, 17367048, f);
            arrayAdapter.setDropDownViewResource(C0744g.support_simple_spinner_dropdown_item);
            setAdapter(arrayAdapter);
        }
        a.m5658a();
        this.f3222i = true;
        if (this.f3221h != null) {
            setAdapter(this.f3221h);
            this.f3221h = null;
        }
        this.f3218e.m5841a(attributeSet, i);
    }

    /* renamed from: a */
    int m5914a(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        if (spinnerAdapter == null) {
            return 0;
        }
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int max = Math.max(0, getSelectedItemPosition());
        int min = Math.min(spinnerAdapter.getCount(), max + 15);
        int max2 = Math.max(0, max - (15 - (min - max)));
        View view = null;
        int i = 0;
        max = 0;
        while (max2 < min) {
            View view2;
            int itemViewType = spinnerAdapter.getItemViewType(max2);
            if (itemViewType != max) {
                view2 = null;
            } else {
                itemViewType = max;
                view2 = view;
            }
            view = spinnerAdapter.getView(max2, view2, this);
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new LayoutParams(-2, -2));
            }
            view.measure(makeMeasureSpec, makeMeasureSpec2);
            i = Math.max(i, view.getMeasuredWidth());
            max2++;
            max = itemViewType;
        }
        if (drawable == null) {
            return i;
        }
        drawable.getPadding(this.f3217c);
        return (this.f3217c.left + this.f3217c.right) + i;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.f3218e != null) {
            this.f3218e.m5844c();
        }
    }

    public int getDropDownHorizontalOffset() {
        return this.f3215a != null ? this.f3215a.m5494j() : VERSION.SDK_INT >= 16 ? super.getDropDownHorizontalOffset() : 0;
    }

    public int getDropDownVerticalOffset() {
        return this.f3215a != null ? this.f3215a.m5495k() : VERSION.SDK_INT >= 16 ? super.getDropDownVerticalOffset() : 0;
    }

    public int getDropDownWidth() {
        return this.f3215a != null ? this.f3216b : VERSION.SDK_INT >= 16 ? super.getDropDownWidth() : 0;
    }

    public Drawable getPopupBackground() {
        return this.f3215a != null ? this.f3215a.m5490h() : VERSION.SDK_INT >= 16 ? super.getPopupBackground() : null;
    }

    public Context getPopupContext() {
        return this.f3215a != null ? this.f3219f : VERSION.SDK_INT >= 23 ? super.getPopupContext() : null;
    }

    public CharSequence getPrompt() {
        return this.f3215a != null ? this.f3215a.mo1013b() : super.getPrompt();
    }

    public ColorStateList getSupportBackgroundTintList() {
        return this.f3218e != null ? this.f3218e.m5836a() : null;
    }

    public Mode getSupportBackgroundTintMode() {
        return this.f3218e != null ? this.f3218e.m5842b() : null;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.f3215a != null && this.f3215a.mo739d()) {
            this.f3215a.mo736c();
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.f3215a != null && MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
            setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), m5914a(getAdapter(), getBackground())), MeasureSpec.getSize(i)), getMeasuredHeight());
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return (this.f3220g == null || !this.f3220g.onTouch(this, motionEvent)) ? super.onTouchEvent(motionEvent) : true;
    }

    public boolean performClick() {
        if (this.f3215a == null) {
            return super.performClick();
        }
        if (!this.f3215a.mo739d()) {
            this.f3215a.mo729a();
        }
        return true;
    }

    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        if (this.f3222i) {
            super.setAdapter(spinnerAdapter);
            if (this.f3215a != null) {
                this.f3215a.mo1012a(new C1080a(spinnerAdapter, (this.f3219f == null ? getContext() : this.f3219f).getTheme()));
                return;
            }
            return;
        }
        this.f3221h = spinnerAdapter;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.f3218e != null) {
            this.f3218e.m5840a(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (this.f3218e != null) {
            this.f3218e.m5837a(i);
        }
    }

    public void setDropDownHorizontalOffset(int i) {
        if (this.f3215a != null) {
            this.f3215a.m5482c(i);
        } else if (VERSION.SDK_INT >= 16) {
            super.setDropDownHorizontalOffset(i);
        }
    }

    public void setDropDownVerticalOffset(int i) {
        if (this.f3215a != null) {
            this.f3215a.m5483d(i);
        } else if (VERSION.SDK_INT >= 16) {
            super.setDropDownVerticalOffset(i);
        }
    }

    public void setDropDownWidth(int i) {
        if (this.f3215a != null) {
            this.f3216b = i;
        } else if (VERSION.SDK_INT >= 16) {
            super.setDropDownWidth(i);
        }
    }

    public void setPopupBackgroundDrawable(Drawable drawable) {
        if (this.f3215a != null) {
            this.f3215a.m5474a(drawable);
        } else if (VERSION.SDK_INT >= 16) {
            super.setPopupBackgroundDrawable(drawable);
        }
    }

    public void setPopupBackgroundResource(int i) {
        setPopupBackgroundDrawable(C0825b.m3939b(getPopupContext(), i));
    }

    public void setPrompt(CharSequence charSequence) {
        if (this.f3215a != null) {
            this.f3215a.m5910a(charSequence);
        } else {
            super.setPrompt(charSequence);
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.f3218e != null) {
            this.f3218e.m5838a(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(Mode mode) {
        if (this.f3218e != null) {
            this.f3218e.m5839a(mode);
        }
    }
}
