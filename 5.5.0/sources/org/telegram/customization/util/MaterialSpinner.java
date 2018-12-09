package org.telegram.customization.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.p072e.p073a.C1492l;
import com.p072e.p073a.C1492l.C1517b;
import com.p072e.p073a.C1493h;
import org.ir.talaeii.R;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.C3336R;
import utils.view.FarsiTextView;
import utils.view.TitleTextView;

public class MaterialSpinner extends Spinner implements C1517b {
    /* renamed from: b */
    private static final String f9404b = MaterialSpinner.class.getSimpleName();
    /* renamed from: A */
    private float f9405A;
    /* renamed from: B */
    private C1493h f9406B;
    /* renamed from: C */
    private boolean f9407C;
    /* renamed from: D */
    private boolean f9408D;
    /* renamed from: E */
    private int f9409E;
    /* renamed from: F */
    private int f9410F;
    /* renamed from: G */
    private int f9411G;
    /* renamed from: H */
    private int f9412H;
    /* renamed from: I */
    private int f9413I;
    /* renamed from: J */
    private CharSequence f9414J;
    /* renamed from: K */
    private CharSequence f9415K;
    /* renamed from: L */
    private int f9416L;
    /* renamed from: M */
    private CharSequence f9417M;
    /* renamed from: N */
    private int f9418N;
    /* renamed from: O */
    private boolean f9419O;
    /* renamed from: P */
    private Typeface f9420P;
    /* renamed from: Q */
    private boolean f9421Q;
    /* renamed from: R */
    private float f9422R;
    /* renamed from: S */
    private float f9423S;
    /* renamed from: T */
    private int f9424T;
    /* renamed from: U */
    private float f9425U;
    /* renamed from: V */
    private boolean f9426V;
    /* renamed from: W */
    private boolean f9427W;
    /* renamed from: a */
    int f9428a = -1;
    private boolean aa;
    private C2856a ab;
    /* renamed from: c */
    private Paint f9429c;
    /* renamed from: d */
    private TextPaint f9430d;
    /* renamed from: e */
    private StaticLayout f9431e;
    /* renamed from: f */
    private Path f9432f;
    /* renamed from: g */
    private Point[] f9433g;
    /* renamed from: h */
    private int f9434h;
    /* renamed from: i */
    private int f9435i;
    /* renamed from: j */
    private int f9436j;
    /* renamed from: k */
    private int f9437k;
    /* renamed from: l */
    private int f9438l;
    /* renamed from: m */
    private int f9439m;
    /* renamed from: n */
    private int f9440n;
    /* renamed from: o */
    private int f9441o;
    /* renamed from: p */
    private int f9442p;
    /* renamed from: q */
    private int f9443q;
    /* renamed from: r */
    private int f9444r;
    /* renamed from: s */
    private int f9445s;
    /* renamed from: t */
    private int f9446t;
    /* renamed from: u */
    private int f9447u;
    /* renamed from: v */
    private int f9448v;
    /* renamed from: w */
    private C1493h f9449w;
    /* renamed from: x */
    private int f9450x;
    /* renamed from: y */
    private int f9451y;
    /* renamed from: z */
    private float f9452z;

    /* renamed from: org.telegram.customization.util.MaterialSpinner$a */
    private class C2856a extends BaseAdapter {
        /* renamed from: a */
        final /* synthetic */ MaterialSpinner f9401a;
        /* renamed from: b */
        private SpinnerAdapter f9402b;
        /* renamed from: c */
        private Context f9403c;

        public C2856a(MaterialSpinner materialSpinner, SpinnerAdapter spinnerAdapter, Context context) {
            this.f9401a = materialSpinner;
            this.f9402b = spinnerAdapter;
            this.f9403c = context;
        }

        /* renamed from: a */
        private View m13273a(int i, View view, ViewGroup viewGroup, boolean z) {
            if (getItemViewType(i) == -1) {
                return m13274a(view, viewGroup, z);
            }
            if (view != null && (view.getTag() == null || !(view.getTag() instanceof Integer) || ((Integer) view.getTag()).intValue() == -1)) {
                view = null;
            }
            if (this.f9401a.f9415K != null) {
                i--;
            }
            return z ? this.f9402b.getDropDownView(i, view, viewGroup) : this.f9402b.getView(i, view, viewGroup);
        }

        /* renamed from: a */
        private View m13274a(View view, ViewGroup viewGroup, boolean z) {
            TextView textView = (TextView) LayoutInflater.from(this.f9403c).inflate(z ? 17367049 : 17367048, viewGroup, false);
            textView.setTypeface(TitleTextView.a(this.f9401a.getContext()));
            textView.setText(this.f9401a.f9415K);
            textView.setTextColor(this.f9401a.isEnabled() ? this.f9401a.f9416L : this.f9401a.f9413I);
            textView.setTag(Integer.valueOf(-1));
            return textView;
        }

        /* renamed from: a */
        private SpinnerAdapter m13275a() {
            return this.f9402b;
        }

        public int getCount() {
            int count = this.f9402b.getCount();
            return this.f9401a.f9415K != null ? count + 1 : count;
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return m13273a(i, view, viewGroup, true);
        }

        public Object getItem(int i) {
            if (this.f9401a.f9415K != null) {
                i--;
            }
            return i == -1 ? this.f9401a.f9415K : this.f9402b.getItem(i);
        }

        public long getItemId(int i) {
            if (this.f9401a.f9415K != null) {
                i--;
            }
            return i == -1 ? 0 : this.f9402b.getItemId(i);
        }

        public int getItemViewType(int i) {
            if (this.f9401a.f9415K != null) {
                i--;
            }
            return i == -1 ? -1 : this.f9402b.getItemViewType(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return m13273a(i, view, viewGroup, false);
        }

        public int getViewTypeCount() {
            return VERSION.SDK_INT >= 21 ? 1 : this.f9402b.getViewTypeCount();
        }
    }

    public MaterialSpinner(Context context) {
        super(context);
        m13280a(context, null);
    }

    public MaterialSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m13280a(context, attributeSet);
    }

    public MaterialSpinner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m13280a(context, attributeSet);
    }

    /* renamed from: a */
    private void m13278a() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.label_text_size);
        this.f9429c = new Paint(1);
        this.f9430d = new TextPaint(1);
        this.f9430d.setTextSize((float) dimensionPixelSize);
        if (this.f9420P != null) {
            this.f9430d.setTypeface(this.f9420P);
        }
        this.f9430d.setColor(this.f9410F);
        this.f9409E = this.f9430d.getAlpha();
        this.f9432f = new Path();
        this.f9432f.setFillType(FillType.EVEN_ODD);
        this.f9433g = new Point[3];
        for (dimensionPixelSize = 0; dimensionPixelSize < 3; dimensionPixelSize++) {
            this.f9433g[dimensionPixelSize] = new Point();
        }
    }

    /* renamed from: a */
    private void m13279a(float f) {
        if (this.f9449w == null) {
            this.f9449w = C1493h.m7412a((Object) this, "currentNbErrorLines", f);
        } else {
            this.f9449w.mo1194a(f);
        }
        this.f9449w.mo1188a();
    }

    /* renamed from: a */
    private void m13280a(Context context, AttributeSet attributeSet) {
        m13287b(context, attributeSet);
        m13278a();
        m13290d();
        m13286b();
        m13295f();
        m13292e();
        setMinimumHeight((getPaddingTop() + getPaddingBottom()) + this.f9447u);
        setBackgroundResource(R.drawable.my_background);
    }

    /* renamed from: a */
    private void m13281a(Canvas canvas, int i, int i2) {
        if (this.f9407C || hasFocus()) {
            this.f9429c.setColor(this.f9411G);
        } else {
            this.f9429c.setColor(isEnabled() ? this.f9424T : this.f9413I);
        }
        Point point = this.f9433g[0];
        Point point2 = this.f9433g[1];
        Point point3 = this.f9433g[2];
        point.set(i, i2);
        point2.set((int) (((float) i) - this.f9425U), i2);
        point3.set((int) (((float) i) - (this.f9425U / 2.0f)), (int) (((float) i2) + (this.f9425U / 2.0f)));
        this.f9432f.reset();
        this.f9432f.moveTo((float) point.x, (float) point.y);
        this.f9432f.lineTo((float) point2.x, (float) point2.y);
        this.f9432f.lineTo((float) point3.x, (float) point3.y);
        this.f9432f.close();
        canvas.drawPath(this.f9432f, this.f9429c);
    }

    /* renamed from: b */
    private int m13283b(float f) {
        return Math.round(TypedValue.applyDimension(1, f, getContext().getResources().getDisplayMetrics()));
    }

    /* renamed from: b */
    private void m13286b() {
        this.f9436j = getPaddingTop();
        this.f9434h = getPaddingLeft();
        this.f9435i = getPaddingRight();
        this.f9437k = getPaddingBottom();
        this.f9438l = this.f9427W ? (this.f9443q + this.f9445s) + this.f9444r : this.f9444r;
        m13288c();
    }

    /* renamed from: b */
    private void m13287b(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.colorControlNormal, R.attr.colorAccent});
        int color = obtainStyledAttributes.getColor(0, 0);
        int color2 = obtainStyledAttributes.getColor(1, 0);
        int color3 = context.getResources().getColor(R.color.error_color);
        obtainStyledAttributes.recycle();
        obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C3336R.styleable.MaterialSpinner);
        this.f9410F = obtainStyledAttributes.getColor(0, color);
        this.f9411G = obtainStyledAttributes.getColor(1, color2);
        this.f9412H = obtainStyledAttributes.getColor(2, color3);
        this.f9413I = context.getResources().getColor(R.color.disabled_color);
        this.f9414J = obtainStyledAttributes.getString(3);
        this.f9415K = obtainStyledAttributes.getString(4);
        this.f9428a = obtainStyledAttributes.getInt(15, -1);
        this.f9416L = obtainStyledAttributes.getColor(5, this.f9410F);
        this.f9417M = obtainStyledAttributes.getString(6);
        this.f9418N = obtainStyledAttributes.getColor(7, this.f9410F);
        this.f9419O = obtainStyledAttributes.getBoolean(8, true);
        this.f9451y = obtainStyledAttributes.getInt(9, 1);
        this.f9421Q = obtainStyledAttributes.getBoolean(11, true);
        this.f9422R = obtainStyledAttributes.getDimension(12, 1.0f);
        this.f9423S = obtainStyledAttributes.getDimension(13, 2.0f);
        this.f9424T = obtainStyledAttributes.getColor(16, this.f9410F);
        this.f9425U = obtainStyledAttributes.getDimension(14, (float) m13283b(12.0f));
        this.f9426V = obtainStyledAttributes.getBoolean(18, true);
        this.f9427W = obtainStyledAttributes.getBoolean(17, true);
        this.aa = obtainStyledAttributes.getBoolean(19, false);
        if (obtainStyledAttributes.getString(10) != null) {
            this.f9420P = FarsiTextView.a(getContext());
        }
        obtainStyledAttributes.recycle();
        this.f9405A = BitmapDescriptorFactory.HUE_RED;
        this.f9450x = 0;
        this.f9407C = false;
        this.f9408D = false;
        this.f9448v = -1;
        this.f9452z = (float) this.f9451y;
    }

    /* renamed from: c */
    private void m13288c() {
        FontMetrics fontMetrics = this.f9430d.getFontMetrics();
        this.f9439m = this.f9440n + this.f9441o;
        if (this.f9426V) {
            this.f9439m = ((int) ((fontMetrics.descent - fontMetrics.ascent) * this.f9452z)) + this.f9439m;
        }
        m13302j();
    }

    /* renamed from: d */
    private void m13290d() {
        this.f9440n = getResources().getDimensionPixelSize(R.dimen.underline_top_spacing);
        this.f9441o = getResources().getDimensionPixelSize(R.dimen.underline_bottom_spacing);
        this.f9443q = getResources().getDimensionPixelSize(R.dimen.floating_label_top_spacing);
        this.f9444r = getResources().getDimensionPixelSize(R.dimen.floating_label_bottom_spacing);
        this.f9446t = this.f9421Q ? getResources().getDimensionPixelSize(R.dimen.right_left_spinner_padding) : 0;
        this.f9445s = getResources().getDimensionPixelSize(R.dimen.floating_label_inside_spacing);
        this.f9442p = (int) getResources().getDimension(R.dimen.error_label_spacing);
        this.f9447u = (int) getResources().getDimension(R.dimen.min_content_height);
    }

    /* renamed from: e */
    private void m13292e() {
        setOnItemSelectedListener(null);
    }

    /* renamed from: f */
    private void m13295f() {
        if (this.f9406B == null) {
            this.f9406B = C1493h.m7412a((Object) this, "floatingLabelPercent", BitmapDescriptorFactory.HUE_RED, 1.0f);
            this.f9406B.m7397a((C1517b) this);
        }
    }

    /* renamed from: g */
    private void m13297g() {
        if (this.f9406B != null) {
            this.f9408D = true;
            if (this.f9406B.m7410h()) {
                this.f9406B.m7411i();
            } else {
                this.f9406B.mo1188a();
            }
        }
    }

    private float getCurrentNbErrorLines() {
        return this.f9452z;
    }

    private int getErrorLabelPosX() {
        return this.f9450x;
    }

    private float getFloatingLabelPercent() {
        return this.f9405A;
    }

    /* renamed from: h */
    private void m13299h() {
        if (this.f9406B != null) {
            this.f9408D = false;
            this.f9406B.m7411i();
        }
    }

    /* renamed from: i */
    private void m13301i() {
        int round = Math.round(this.f9430d.measureText(this.f9414J.toString()));
        if (this.f9449w == null) {
            this.f9449w = C1493h.m7413a((Object) this, "errorLabelPosX", 0, round + (getWidth() / 2));
            this.f9449w.m7406d(1000);
            this.f9449w.m7396a(new LinearInterpolator());
            this.f9449w.mo1192a((long) (this.f9414J.length() * BuildConfig.VERSION_CODE));
            this.f9449w.m7397a((C1517b) this);
            this.f9449w.m7395a(-1);
        } else {
            this.f9449w.mo1195a(0, round + (getWidth() / 2));
        }
        this.f9449w.mo1188a();
    }

    /* renamed from: j */
    private void m13302j() {
        int i = this.f9434h;
        int i2 = this.f9436j + this.f9438l;
        int i3 = this.f9435i;
        int i4 = this.f9437k + this.f9439m;
        if (this.f9428a >= 0) {
            i = this.f9428a;
            i4 = this.f9428a;
            i3 = this.f9428a;
            i2 = this.f9428a;
        }
        super.setPadding(i, i2, i3, i4);
        setMinimumHeight((i4 + i2) + this.f9447u);
    }

    /* renamed from: k */
    private boolean m13303k() {
        if (this.f9414J == null) {
            return false;
        }
        return this.f9430d.measureText(this.f9414J.toString(), 0, this.f9414J.length()) > ((float) (getWidth() - this.f9446t));
    }

    /* renamed from: l */
    private int m13304l() {
        int i = this.f9451y;
        if (this.f9414J == null) {
            return i;
        }
        this.f9431e = new StaticLayout(this.f9414J, this.f9430d, (getWidth() - getPaddingRight()) - getPaddingLeft(), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, true);
        return Math.max(this.f9451y, this.f9431e.getLineCount());
    }

    private void setCurrentNbErrorLines(float f) {
        this.f9452z = f;
        m13288c();
    }

    private void setErrorLabelPosX(int i) {
        this.f9450x = i;
    }

    private void setFloatingLabelPercent(float f) {
        this.f9405A = f;
    }

    /* renamed from: a */
    public void mo3496a(C1492l c1492l) {
        invalidate();
    }

    public SpinnerAdapter getAdapter() {
        return this.ab != null ? this.ab.m13275a() : null;
    }

    public int getBaseColor() {
        return this.f9410F;
    }

    public CharSequence getError() {
        return this.f9414J;
    }

    public int getErrorColor() {
        return this.f9412H;
    }

    public CharSequence getFloatingLabelText() {
        return this.f9417M;
    }

    public int getHighlightColor() {
        return this.f9411G;
    }

    public CharSequence getHint() {
        return this.f9415K;
    }

    public int getHintColor() {
        return this.f9416L;
    }

    public Object getItemAtPosition(int i) {
        if (this.f9415K != null) {
            i++;
        }
        return (this.ab == null || i < 0) ? null : this.ab.getItem(i);
    }

    public long getItemIdAtPosition(int i) {
        if (this.f9415K != null) {
            i++;
        }
        return (this.ab == null || i < 0) ? Long.MIN_VALUE : this.ab.getItemId(i);
    }

    public int getSelectedItemPosition() {
        return super.getSelectedItemPosition();
    }

    protected void onDraw(Canvas canvas) {
        int i;
        super.onDraw(canvas);
        int width = getWidth();
        int height = (getHeight() - getPaddingBottom()) + this.f9440n;
        int paddingTop = (int) (((float) getPaddingTop()) - (this.f9405A * ((float) this.f9444r)));
        int b;
        if (this.f9414J == null || !this.f9426V) {
            b = m13283b(this.f9422R);
            if (this.f9407C || hasFocus()) {
                this.f9429c.setColor(this.f9411G);
                i = b;
            } else {
                this.f9429c.setColor(isEnabled() ? this.f9410F : this.f9413I);
                i = b;
            }
        } else {
            i = m13283b(this.f9423S);
            b = (this.f9442p + height) + i;
            this.f9429c.setColor(this.f9412H);
            this.f9430d.setColor(this.f9412H);
            if (this.f9419O) {
                canvas.save();
                canvas.translate((float) (this.f9446t + 0), (float) (b - this.f9442p));
                this.f9431e.draw(canvas);
                canvas.restore();
            } else {
                canvas.drawText(this.f9414J.toString(), (float) ((this.f9446t + 0) - this.f9450x), (float) b, this.f9430d);
                if (this.f9450x > 0) {
                    canvas.save();
                    canvas.translate(this.f9430d.measureText(this.f9414J.toString()) + ((float) (getWidth() / 2)), BitmapDescriptorFactory.HUE_RED);
                    canvas.drawText(this.f9414J.toString(), (float) ((this.f9446t + 0) - this.f9450x), (float) b, this.f9430d);
                    canvas.restore();
                }
            }
        }
        canvas.drawRect((float) null, (float) height, (float) width, (float) (i + height), this.f9429c);
        if (!(this.f9415K == null && this.f9417M == null) && this.f9427W) {
            if (this.f9407C || hasFocus()) {
                this.f9430d.setColor(this.f9411G);
            } else {
                this.f9430d.setColor(isEnabled() ? this.f9418N : this.f9413I);
            }
            if (this.f9406B.m7410h() || !this.f9408D) {
                this.f9430d.setAlpha((int) ((((0.8d * ((double) this.f9405A)) + 0.2d) * ((double) this.f9409E)) * ((double) this.f9405A)));
            }
            String charSequence = this.f9417M != null ? this.f9417M.toString() : this.f9415K.toString();
            if (this.aa) {
                canvas.drawText(charSequence, ((float) (getWidth() - this.f9446t)) - this.f9430d.measureText(charSequence), (float) paddingTop, this.f9430d);
            } else {
                canvas.drawText(charSequence, (float) (this.f9446t + 0), (float) paddingTop, this.f9430d);
            }
        }
        m13281a(canvas, getWidth() - this.f9446t, getPaddingTop() + m13283b(8.0f));
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isEnabled()) {
            switch (motionEvent.getAction()) {
                case 0:
                    this.f9407C = true;
                    break;
                case 1:
                case 3:
                    this.f9407C = false;
                    break;
            }
            invalidate();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        this.ab = new C2856a(this, spinnerAdapter, getContext());
        super.setAdapter(spinnerAdapter);
    }

    public void setBaseColor(int i) {
        this.f9410F = i;
        this.f9430d.setColor(i);
        this.f9409E = this.f9430d.getAlpha();
        invalidate();
    }

    public void setEnabled(boolean z) {
        if (!z) {
            this.f9407C = false;
            invalidate();
        }
        super.setEnabled(z);
    }

    public void setError(int i) {
        setError(getResources().getString(i));
    }

    public void setError(CharSequence charSequence) {
        this.f9414J = charSequence;
        if (this.f9449w != null) {
            this.f9449w.mo1189b();
        }
        if (this.f9419O) {
            m13279a((float) m13304l());
        } else if (m13303k()) {
            m13301i();
        }
        requestLayout();
    }

    public void setErrorColor(int i) {
        this.f9412H = i;
        invalidate();
    }

    public void setFloatingLabelText(int i) {
        setFloatingLabelText(getResources().getString(i));
    }

    public void setFloatingLabelText(CharSequence charSequence) {
        this.f9417M = charSequence;
        invalidate();
    }

    public void setHighlightColor(int i) {
        this.f9411G = i;
        invalidate();
    }

    public void setHint(int i) {
        setHint(getResources().getString(i));
    }

    public void setHint(CharSequence charSequence) {
        this.f9415K = charSequence;
        invalidate();
    }

    public void setHintColor(int i) {
        this.f9416L = i;
        invalidate();
    }

    public void setOnItemSelectedListener(final OnItemSelectedListener onItemSelectedListener) {
        super.setOnItemSelectedListener(new OnItemSelectedListener(this) {
            /* renamed from: b */
            final /* synthetic */ MaterialSpinner f9400b;

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (!(this.f9400b.f9415K == null && this.f9400b.f9417M == null)) {
                    if (!this.f9400b.f9408D && i != 0) {
                        this.f9400b.m13297g();
                    } else if (this.f9400b.f9408D && i == 0) {
                        this.f9400b.m13299h();
                    }
                }
                if (!(i == this.f9400b.f9448v || this.f9400b.f9414J == null)) {
                    this.f9400b.setError(null);
                }
                this.f9400b.f9448v = i;
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(adapterView, view, this.f9400b.f9415K != null ? i - 1 : i, j);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onNothingSelected(adapterView);
                }
            }
        });
    }

    @Deprecated
    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i, i2, i3, i4);
    }

    public void setSelection(final int i) {
        post(new Runnable(this) {
            /* renamed from: b */
            final /* synthetic */ MaterialSpinner f9398b;

            public void run() {
                super.setSelection(i);
            }
        });
    }
}
