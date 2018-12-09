package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ah;
import android.support.v4.view.p023a.C0531e;
import android.support.v4.widget.C0708l;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2022a;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2023b;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2027f;
import com.mohamadamin.persianmaterialdatetimepicker.C2029c;
import com.mohamadamin.persianmaterialdatetimepicker.C2030d;
import com.mohamadamin.persianmaterialdatetimepicker.date.C2042d.C2040a;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2017a;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2018b;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.e */
public abstract class C2044e extends View {
    /* renamed from: a */
    protected static int f6020a = 32;
    /* renamed from: b */
    protected static int f6021b = 10;
    /* renamed from: c */
    protected static int f6022c = 1;
    /* renamed from: d */
    protected static int f6023d;
    /* renamed from: e */
    protected static int f6024e;
    /* renamed from: f */
    protected static int f6025f;
    /* renamed from: g */
    protected static int f6026g;
    /* renamed from: h */
    protected static int f6027h;
    /* renamed from: i */
    protected static float f6028i = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: A */
    protected int f6029A = 7;
    /* renamed from: B */
    protected int f6030B = this.f6029A;
    /* renamed from: C */
    protected int f6031C = -1;
    /* renamed from: D */
    protected int f6032D = -1;
    /* renamed from: E */
    protected final C2018b f6033E;
    /* renamed from: F */
    protected int f6034F = 6;
    /* renamed from: G */
    protected C2041b f6035G;
    /* renamed from: H */
    protected int f6036H;
    /* renamed from: I */
    protected int f6037I;
    /* renamed from: J */
    protected int f6038J;
    /* renamed from: K */
    protected int f6039K;
    /* renamed from: L */
    protected int f6040L;
    /* renamed from: M */
    protected int f6041M;
    /* renamed from: N */
    protected int f6042N;
    /* renamed from: O */
    private String f6043O;
    /* renamed from: P */
    private String f6044P;
    /* renamed from: Q */
    private final StringBuilder f6045Q;
    /* renamed from: R */
    private final C2018b f6046R;
    /* renamed from: S */
    private final C2043a f6047S;
    /* renamed from: T */
    private boolean f6048T;
    /* renamed from: U */
    private int f6049U = 0;
    /* renamed from: j */
    protected C2031a f6050j;
    /* renamed from: k */
    protected int f6051k = 0;
    /* renamed from: l */
    protected Paint f6052l;
    /* renamed from: m */
    protected Paint f6053m;
    /* renamed from: n */
    protected Paint f6054n;
    /* renamed from: o */
    protected Paint f6055o;
    /* renamed from: p */
    protected int f6056p = -1;
    /* renamed from: q */
    protected int f6057q = -1;
    /* renamed from: r */
    protected int f6058r = -1;
    /* renamed from: s */
    protected int f6059s;
    /* renamed from: t */
    protected int f6060t;
    /* renamed from: u */
    protected int f6061u;
    /* renamed from: v */
    protected int f6062v = f6020a;
    /* renamed from: w */
    protected boolean f6063w = false;
    /* renamed from: x */
    protected int f6064x = -1;
    /* renamed from: y */
    protected int f6065y = -1;
    /* renamed from: z */
    protected int f6066z = 7;

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.e$b */
    public interface C2041b {
        /* renamed from: a */
        void mo3075a(C2044e c2044e, C2040a c2040a);
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.e$a */
    protected class C2043a extends C0708l {
        /* renamed from: a */
        final /* synthetic */ C2044e f6017a;
        /* renamed from: b */
        private final Rect f6018b = new Rect();
        /* renamed from: c */
        private final C2018b f6019c = new C2018b();

        public C2043a(C2044e c2044e, View view) {
            this.f6017a = c2044e;
            super(view);
        }

        /* renamed from: a */
        protected int mo3076a(float f, float f2) {
            int a = this.f6017a.m9194a(f, f2);
            return a >= 0 ? a : Integer.MIN_VALUE;
        }

        /* renamed from: a */
        protected void m9179a(int i, Rect rect) {
            int i2 = this.f6017a.f6051k;
            int monthHeaderSize = this.f6017a.getMonthHeaderSize();
            int i3 = this.f6017a.f6062v;
            int i4 = (this.f6017a.f6061u - (this.f6017a.f6051k * 2)) / this.f6017a.f6029A;
            int c = (i - 1) + this.f6017a.m9204c();
            i2 += (c % this.f6017a.f6029A) * i4;
            monthHeaderSize += (c / this.f6017a.f6029A) * i3;
            rect.set(i2, monthHeaderSize, i4 + i2, i3 + monthHeaderSize);
        }

        /* renamed from: a */
        protected void mo3077a(int i, C0531e c0531e) {
            m9179a(i, this.f6018b);
            c0531e.m2325d(mo3083e(i));
            c0531e.m2310b(this.f6018b);
            c0531e.m2305a(16);
            if (i == this.f6017a.f6064x) {
                c0531e.m2332g(true);
            }
        }

        /* renamed from: a */
        protected void mo3078a(int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(mo3083e(i));
        }

        /* renamed from: a */
        protected void mo3079a(List<Integer> list) {
            for (int i = 1; i <= this.f6017a.f6030B; i++) {
                list.add(Integer.valueOf(i));
            }
        }

        /* renamed from: b */
        protected boolean mo3080b(int i, int i2, Bundle bundle) {
            switch (i2) {
                case 16:
                    this.f6017a.m9187a(i);
                    return true;
                default:
                    return false;
            }
        }

        /* renamed from: d */
        public void mo3081d() {
            int c = m3468c();
            if (c != Integer.MIN_VALUE) {
                getAccessibilityNodeProvider(this.f6017a).mo584a(c, 128, null);
            }
        }

        /* renamed from: d */
        public void mo3082d(int i) {
            getAccessibilityNodeProvider(this.f6017a).mo584a(i, 64, null);
        }

        /* renamed from: e */
        protected CharSequence mo3083e(int i) {
            this.f6019c.m9094a(this.f6017a.f6060t, this.f6017a.f6059s, i);
            CharSequence a = C2017a.m9087a(this.f6019c.m9100g());
            if (i != this.f6017a.f6064x) {
                return a;
            }
            return this.f6017a.getContext().getString(C2027f.mdtp_item_is_selected, new Object[]{a});
        }
    }

    public C2044e(Context context, AttributeSet attributeSet, C2031a c2031a) {
        boolean z = false;
        super(context, attributeSet);
        this.f6050j = c2031a;
        Resources resources = context.getResources();
        this.f6033E = new C2018b();
        this.f6046R = new C2018b();
        this.f6043O = resources.getString(C2027f.mdtp_day_of_week_label_typeface);
        this.f6044P = resources.getString(C2027f.mdtp_sans_serif);
        if (this.f6050j != null && this.f6050j.mo3065b()) {
            z = true;
        }
        if (z) {
            this.f6036H = resources.getColor(C2022a.mdtp_date_picker_text_normal_dark_theme);
            this.f6038J = resources.getColor(C2022a.mdtp_date_picker_month_day_dark_theme);
            this.f6041M = resources.getColor(C2022a.mdtp_date_picker_text_disabled_dark_theme);
            this.f6040L = resources.getColor(C2022a.mdtp_date_picker_text_highlighted_dark_theme);
        } else {
            this.f6036H = resources.getColor(C2022a.mdtp_date_picker_text_normal);
            this.f6038J = resources.getColor(C2022a.mdtp_date_picker_month_day);
            this.f6041M = resources.getColor(C2022a.mdtp_date_picker_text_disabled);
            this.f6040L = resources.getColor(C2022a.mdtp_date_picker_text_highlighted);
        }
        this.f6037I = resources.getColor(C2022a.mdtp_white);
        this.f6039K = resources.getColor(C2022a.mdtp_accent_color);
        this.f6042N = resources.getColor(C2022a.mdtp_white);
        this.f6045Q = new StringBuilder(50);
        f6023d = resources.getDimensionPixelSize(C2023b.mdtp_day_number_size);
        f6024e = resources.getDimensionPixelSize(C2023b.mdtp_month_label_size);
        f6025f = resources.getDimensionPixelSize(C2023b.mdtp_month_day_label_text_size);
        f6026g = resources.getDimensionPixelOffset(C2023b.mdtp_month_list_item_header_height);
        f6027h = resources.getDimensionPixelSize(C2023b.mdtp_day_number_select_circle_radius);
        this.f6062v = (resources.getDimensionPixelOffset(C2023b.mdtp_date_picker_view_animator_height) - getMonthHeaderSize()) / 6;
        this.f6047S = getMonthViewTouchHelper();
        ah.m2783a((View) this, this.f6047S);
        ah.m2801c((View) this, 1);
        this.f6048T = true;
        m9195a();
    }

    /* renamed from: a */
    private void m9187a(int i) {
        if (!m9198a(this.f6060t, this.f6059s, i)) {
            if (this.f6035G != null) {
                this.f6035G.mo3075a(this, new C2040a(this.f6060t, this.f6059s, i));
            }
            this.f6047S.m3461a(i, 1);
        }
    }

    /* renamed from: a */
    private boolean m9189a(int i, C2018b c2018b) {
        return this.f6060t == c2018b.m9095b() && this.f6059s == c2018b.m9096c() && i == c2018b.m9098e();
    }

    /* renamed from: c */
    private boolean m9190c(int i, int i2, int i3) {
        for (C2018b c2018b : this.f6050j.mo3067d()) {
            if (i < c2018b.m9095b()) {
                return false;
            }
            if (i <= c2018b.m9095b()) {
                if (i2 < c2018b.m9096c()) {
                    return false;
                }
                if (i2 > c2018b.m9096c()) {
                    continue;
                } else if (i3 < c2018b.m9098e()) {
                    return false;
                } else {
                    if (i3 <= c2018b.m9098e()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: d */
    private boolean m9191d(int i, int i2, int i3) {
        if (this.f6050j == null) {
            return false;
        }
        C2018b h = this.f6050j.mo3071h();
        return h != null ? i < h.m9095b() ? true : i <= h.m9095b() ? i2 < h.m9096c() ? true : i2 <= h.m9096c() && i3 < h.m9098e() : false : false;
    }

    /* renamed from: e */
    private int m9192e() {
        int c = m9204c();
        return ((c + this.f6030B) % this.f6029A > 0 ? 1 : 0) + ((this.f6030B + c) / this.f6029A);
    }

    /* renamed from: e */
    private boolean m9193e(int i, int i2, int i3) {
        if (this.f6050j == null) {
            return false;
        }
        C2018b i4 = this.f6050j.mo3072i();
        return i4 != null ? i > i4.m9095b() ? true : i >= i4.m9095b() ? i2 > i4.m9096c() ? true : i2 >= i4.m9096c() && i3 > i4.m9096c() : false : false;
    }

    private String getMonthAndYearString() {
        this.f6045Q.setLength(0);
        return C2017a.m9087a(this.f6046R.m9097d() + " " + this.f6046R.m9095b());
    }

    /* renamed from: a */
    public int m9194a(float f, float f2) {
        int b = m9200b(f, f2);
        return (b < 1 || b > this.f6030B) ? -1 : b;
    }

    /* renamed from: a */
    protected void m9195a() {
        this.f6053m = new Paint();
        this.f6053m.setFakeBoldText(true);
        this.f6053m.setAntiAlias(true);
        this.f6053m.setTextSize((float) f6024e);
        this.f6053m.setTypeface(Typeface.create(this.f6044P, 1));
        this.f6053m.setColor(this.f6036H);
        this.f6053m.setTextAlign(Align.CENTER);
        this.f6053m.setStyle(Style.FILL);
        this.f6054n = new Paint();
        this.f6054n.setFakeBoldText(true);
        this.f6054n.setAntiAlias(true);
        this.f6054n.setColor(this.f6039K);
        this.f6054n.setTextAlign(Align.CENTER);
        this.f6054n.setStyle(Style.FILL);
        this.f6054n.setAlpha(255);
        this.f6055o = new Paint();
        this.f6055o.setAntiAlias(true);
        this.f6055o.setTextSize((float) f6025f);
        this.f6055o.setColor(this.f6038J);
        this.f6055o.setTypeface(C2029c.m9113a(getContext(), "Roboto-Medium"));
        this.f6055o.setStyle(Style.FILL);
        this.f6055o.setTextAlign(Align.CENTER);
        this.f6055o.setFakeBoldText(true);
        this.f6052l = new Paint();
        this.f6052l.setAntiAlias(true);
        this.f6052l.setTextSize((float) f6023d);
        this.f6052l.setStyle(Style.FILL);
        this.f6052l.setTextAlign(Align.CENTER);
        this.f6052l.setFakeBoldText(false);
    }

    /* renamed from: a */
    protected void m9196a(Canvas canvas) {
        canvas.drawText(getMonthAndYearString(), (float) ((this.f6061u + (this.f6051k * 2)) / 2), (float) ((getMonthHeaderSize() - f6025f) / 2), this.f6053m);
    }

    /* renamed from: a */
    public abstract void mo3086a(Canvas canvas, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9);

    /* renamed from: a */
    protected boolean m9198a(int i, int i2, int i3) {
        return this.f6050j.mo3067d() != null ? !m9190c(i, i2, i3) : m9191d(i, i2, i3) || m9193e(i, i2, i3);
    }

    /* renamed from: a */
    public boolean m9199a(C2040a c2040a) {
        if (c2040a.f6009a != this.f6060t || c2040a.f6010b != this.f6059s || c2040a.f6011c > this.f6030B) {
            return false;
        }
        this.f6047S.mo3082d(c2040a.f6011c);
        return true;
    }

    /* renamed from: b */
    protected int m9200b(float f, float f2) {
        int i = this.f6051k;
        if (f < ((float) i) || f > ((float) (this.f6061u - this.f6051k))) {
            return -1;
        }
        return ((((int) (((f - ((float) i)) * ((float) this.f6029A)) / ((float) ((this.f6061u - i) - this.f6051k)))) - m9204c()) + 1) + ((((int) (f2 - ((float) getMonthHeaderSize()))) / this.f6062v) * this.f6029A);
    }

    /* renamed from: b */
    public void m9201b() {
        this.f6034F = 6;
        requestLayout();
    }

    /* renamed from: b */
    protected void m9202b(Canvas canvas) {
        int monthHeaderSize = getMonthHeaderSize() - (f6025f / 2);
        int i = (this.f6061u - (this.f6051k * 2)) / (this.f6029A * 2);
        for (int i2 = 0; i2 < this.f6029A; i2++) {
            int i3 = (((i2 * 2) + 1) * i) + this.f6051k;
            this.f6033E.set(7, (this.f6066z + i2) % this.f6029A);
            canvas.drawText(this.f6033E.m9099f().substring(0, 1), (float) i3, (float) monthHeaderSize, this.f6055o);
        }
    }

    /* renamed from: b */
    protected boolean m9203b(int i, int i2, int i3) {
        C2018b[] c = this.f6050j.mo3066c();
        if (c == null) {
            return false;
        }
        for (C2018b c2018b : c) {
            if (i < c2018b.m9095b()) {
                return false;
            }
            if (i <= c2018b.m9095b()) {
                if (i2 < c2018b.m9096c()) {
                    return false;
                }
                if (i2 > c2018b.m9096c()) {
                    continue;
                } else if (i3 < c2018b.m9098e()) {
                    return false;
                } else {
                    if (i3 <= c2018b.m9098e()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: c */
    protected int m9204c() {
        return (this.f6049U < this.f6066z ? this.f6049U + this.f6029A : this.f6049U) - this.f6066z;
    }

    /* renamed from: c */
    protected void m9205c(Canvas canvas) {
        int monthHeaderSize = (((this.f6062v + f6023d) / 2) - f6022c) + getMonthHeaderSize();
        float f = ((float) (this.f6061u - (this.f6051k * 2))) / (((float) this.f6029A) * 2.0f);
        int i = 1;
        int c = m9204c();
        while (i <= this.f6030B) {
            int i2 = (int) ((((float) ((c * 2) + 1)) * f) + ((float) this.f6051k));
            int i3 = monthHeaderSize - (((this.f6062v + f6023d) / 2) - f6022c);
            Canvas canvas2 = canvas;
            mo3086a(canvas2, this.f6060t, this.f6059s, i, i2, monthHeaderSize, (int) (((float) i2) - f), (int) (((float) i2) + f), i3, i3 + this.f6062v);
            int i4 = c + 1;
            if (i4 == this.f6029A) {
                i4 = 0;
                monthHeaderSize += this.f6062v;
            }
            i++;
            c = i4;
        }
    }

    /* renamed from: d */
    public void m9206d() {
        this.f6047S.mo3081d();
    }

    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.f6047S.m3463a(motionEvent) ? true : super.dispatchHoverEvent(motionEvent);
    }

    public C2040a getAccessibilityFocus() {
        int c = this.f6047S.m3468c();
        return c >= 0 ? new C2040a(this.f6060t, this.f6059s, c) : null;
    }

    public int getMonth() {
        return this.f6059s;
    }

    protected int getMonthHeaderSize() {
        return f6026g;
    }

    protected C2043a getMonthViewTouchHelper() {
        return new C2043a(this, this);
    }

    public int getYear() {
        return this.f6060t;
    }

    protected void onDraw(Canvas canvas) {
        m9196a(canvas);
        m9202b(canvas);
        m9205c(canvas);
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(MeasureSpec.getSize(i), ((this.f6062v * this.f6034F) + getMonthHeaderSize()) + 5);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        this.f6061u = i;
        this.f6047S.m3464b();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 1:
                int a = m9194a(motionEvent.getX(), motionEvent.getY());
                if (a >= 0) {
                    m9187a(a);
                    break;
                }
                break;
        }
        return true;
    }

    public void setAccessibilityDelegate(AccessibilityDelegate accessibilityDelegate) {
        if (!this.f6048T) {
            super.setAccessibilityDelegate(accessibilityDelegate);
        }
    }

    public void setDatePickerController(C2031a c2031a) {
        this.f6050j = c2031a;
    }

    public void setMonthParams(HashMap<String, Integer> hashMap) {
        if (hashMap.containsKey("month") || hashMap.containsKey("year")) {
            setTag(hashMap);
            if (hashMap.containsKey("height")) {
                this.f6062v = ((Integer) hashMap.get("height")).intValue();
                if (this.f6062v < f6021b) {
                    this.f6062v = f6021b;
                }
            }
            if (hashMap.containsKey("selected_day")) {
                this.f6064x = ((Integer) hashMap.get("selected_day")).intValue();
            }
            this.f6059s = ((Integer) hashMap.get("month")).intValue();
            this.f6060t = ((Integer) hashMap.get("year")).intValue();
            C2018b c2018b = new C2018b();
            this.f6063w = false;
            this.f6065y = -1;
            this.f6046R.m9094a(this.f6060t, this.f6059s, 1);
            this.f6049U = this.f6046R.get(7);
            if (hashMap.containsKey("week_start")) {
                this.f6066z = ((Integer) hashMap.get("week_start")).intValue();
            } else {
                this.f6066z = 7;
            }
            this.f6030B = C2030d.m9114a(this.f6059s, this.f6060t);
            for (int i = 0; i < this.f6030B; i++) {
                int i2 = i + 1;
                if (m9189a(i2, c2018b)) {
                    this.f6063w = true;
                    this.f6065y = i2;
                }
            }
            this.f6034F = m9192e();
            this.f6047S.m3464b();
            return;
        }
        throw new InvalidParameterException("You must specify month and year for this view");
    }

    public void setOnDayClickListener(C2041b c2041b) {
        this.f6035G = c2041b;
    }

    public void setSelectedDay(int i) {
        this.f6064x = i;
    }
}
