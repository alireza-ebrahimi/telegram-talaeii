package com.mohamadamin.persianmaterialdatetimepicker.time;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2022a;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2027f;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2017a;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.d */
public class C2061d extends View {
    /* renamed from: A */
    private float[] f6164A;
    /* renamed from: B */
    private float[] f6165B;
    /* renamed from: C */
    private float[] f6166C;
    /* renamed from: D */
    private float f6167D;
    /* renamed from: E */
    private float f6168E;
    /* renamed from: F */
    private float f6169F;
    /* renamed from: G */
    private C2060a f6170G;
    /* renamed from: a */
    ObjectAnimator f6171a;
    /* renamed from: b */
    ObjectAnimator f6172b;
    /* renamed from: c */
    private final Paint f6173c = new Paint();
    /* renamed from: d */
    private final Paint f6174d = new Paint();
    /* renamed from: e */
    private boolean f6175e;
    /* renamed from: f */
    private boolean f6176f = false;
    /* renamed from: g */
    private int f6177g = -1;
    /* renamed from: h */
    private Typeface f6178h;
    /* renamed from: i */
    private Typeface f6179i;
    /* renamed from: j */
    private String[] f6180j;
    /* renamed from: k */
    private String[] f6181k;
    /* renamed from: l */
    private boolean f6182l;
    /* renamed from: m */
    private boolean f6183m;
    /* renamed from: n */
    private float f6184n;
    /* renamed from: o */
    private float f6185o;
    /* renamed from: p */
    private float f6186p;
    /* renamed from: q */
    private float f6187q;
    /* renamed from: r */
    private float f6188r;
    /* renamed from: s */
    private float f6189s;
    /* renamed from: t */
    private int f6190t;
    /* renamed from: u */
    private int f6191u;
    /* renamed from: v */
    private float f6192v;
    /* renamed from: w */
    private boolean f6193w;
    /* renamed from: x */
    private float f6194x;
    /* renamed from: y */
    private float f6195y;
    /* renamed from: z */
    private float[] f6196z;

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.d$a */
    private class C2060a implements AnimatorUpdateListener {
        /* renamed from: a */
        final /* synthetic */ C2061d f6163a;

        private C2060a(C2061d c2061d) {
            this.f6163a = c2061d;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.f6163a.invalidate();
        }
    }

    public C2061d(Context context) {
        super(context);
    }

    /* renamed from: a */
    private void m9248a() {
        Keyframe ofFloat = Keyframe.ofFloat(BitmapDescriptorFactory.HUE_RED, 1.0f);
        Keyframe ofFloat2 = Keyframe.ofFloat(0.2f, this.f6168E);
        Keyframe ofFloat3 = Keyframe.ofFloat(1.0f, this.f6169F);
        PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe("animationRadiusMultiplier", new Keyframe[]{ofFloat, ofFloat2, ofFloat3});
        ofFloat2 = Keyframe.ofFloat(BitmapDescriptorFactory.HUE_RED, 1.0f);
        ofFloat3 = Keyframe.ofFloat(1.0f, BitmapDescriptorFactory.HUE_RED);
        PropertyValuesHolder ofKeyframe2 = PropertyValuesHolder.ofKeyframe("alpha", new Keyframe[]{ofFloat2, ofFloat3});
        this.f6171a = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofKeyframe, ofKeyframe2}).setDuration((long) 500);
        this.f6171a.addUpdateListener(this.f6170G);
        int i = (int) ((1.0f + 0.25f) * ((float) 500));
        float f = (((float) 500) * 0.25f) / ((float) i);
        float f2 = 1.0f - (0.2f * (1.0f - f));
        ofFloat = Keyframe.ofFloat(BitmapDescriptorFactory.HUE_RED, this.f6169F);
        ofFloat3 = Keyframe.ofFloat(f, this.f6169F);
        Keyframe ofFloat4 = Keyframe.ofFloat(f2, this.f6168E);
        Keyframe ofFloat5 = Keyframe.ofFloat(1.0f, 1.0f);
        PropertyValuesHolder ofKeyframe3 = PropertyValuesHolder.ofKeyframe("animationRadiusMultiplier", new Keyframe[]{ofFloat, ofFloat3, ofFloat4, ofFloat5});
        ofFloat = Keyframe.ofFloat(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        Keyframe ofFloat6 = Keyframe.ofFloat(f, BitmapDescriptorFactory.HUE_RED);
        ofFloat3 = Keyframe.ofFloat(1.0f, 1.0f);
        PropertyValuesHolder ofKeyframe4 = PropertyValuesHolder.ofKeyframe("alpha", new Keyframe[]{ofFloat, ofFloat6, ofFloat3});
        this.f6172b = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofKeyframe3, ofKeyframe4}).setDuration((long) i);
        this.f6172b.addUpdateListener(this.f6170G);
    }

    /* renamed from: a */
    private void m9249a(float f, float f2, float f3, float f4, float[] fArr, float[] fArr2) {
        float sqrt = (((float) Math.sqrt(3.0d)) * f) / 2.0f;
        float f5 = f / 2.0f;
        this.f6173c.setTextSize(f4);
        this.f6174d.setTextSize(f4);
        float descent = f3 - ((this.f6173c.descent() + this.f6173c.ascent()) / 2.0f);
        fArr[0] = descent - f;
        fArr2[0] = f2 - f;
        fArr[1] = descent - sqrt;
        fArr2[1] = f2 - sqrt;
        fArr[2] = descent - f5;
        fArr2[2] = f2 - f5;
        fArr[3] = descent;
        fArr2[3] = f2;
        fArr[4] = descent + f5;
        fArr2[4] = f5 + f2;
        fArr[5] = descent + sqrt;
        fArr2[5] = sqrt + f2;
        fArr[6] = descent + f;
        fArr2[6] = f2 + f;
    }

    /* renamed from: a */
    private void m9250a(Canvas canvas, float f, Typeface typeface, String[] strArr, float[] fArr, float[] fArr2) {
        this.f6173c.setTextSize(f);
        this.f6173c.setTypeface(typeface);
        C2017a.m9089a(strArr);
        canvas.drawText(strArr[0], fArr[3], fArr2[0], Integer.parseInt(strArr[0]) == this.f6177g ? this.f6174d : this.f6173c);
        canvas.drawText(strArr[1], fArr[4], fArr2[1], Integer.parseInt(strArr[1]) == this.f6177g ? this.f6174d : this.f6173c);
        canvas.drawText(strArr[2], fArr[5], fArr2[2], Integer.parseInt(strArr[2]) == this.f6177g ? this.f6174d : this.f6173c);
        canvas.drawText(strArr[3], fArr[6], fArr2[3], Integer.parseInt(strArr[3]) == this.f6177g ? this.f6174d : this.f6173c);
        canvas.drawText(strArr[4], fArr[5], fArr2[4], Integer.parseInt(strArr[4]) == this.f6177g ? this.f6174d : this.f6173c);
        canvas.drawText(strArr[5], fArr[4], fArr2[5], Integer.parseInt(strArr[5]) == this.f6177g ? this.f6174d : this.f6173c);
        canvas.drawText(strArr[6], fArr[3], fArr2[6], Integer.parseInt(strArr[6]) == this.f6177g ? this.f6174d : this.f6173c);
        canvas.drawText(strArr[7], fArr[2], fArr2[5], Integer.parseInt(strArr[7]) == this.f6177g ? this.f6174d : this.f6173c);
        canvas.drawText(strArr[8], fArr[1], fArr2[4], Integer.parseInt(strArr[8]) == this.f6177g ? this.f6174d : this.f6173c);
        canvas.drawText(strArr[9], fArr[0], fArr2[3], Integer.parseInt(strArr[9]) == this.f6177g ? this.f6174d : this.f6173c);
        canvas.drawText(strArr[10], fArr[1], fArr2[2], Integer.parseInt(strArr[10]) == this.f6177g ? this.f6174d : this.f6173c);
        canvas.drawText(strArr[11], fArr[2], fArr2[1], Integer.parseInt(strArr[11]) == this.f6177g ? this.f6174d : this.f6173c);
    }

    /* renamed from: a */
    void m9251a(Context context, boolean z) {
        Resources resources = context.getResources();
        this.f6173c.setColor(z ? resources.getColor(C2022a.mdtp_white) : resources.getColor(C2022a.mdtp_numbers_text_color));
    }

    /* renamed from: a */
    public void m9252a(Resources resources, String[] strArr, String[] strArr2, boolean z, boolean z2) {
        int i = -1;
        boolean z3 = false;
        if (this.f6176f) {
            Log.e("RadialTextsView", "This RadialTextsView may only be initialized once.");
            return;
        }
        this.f6173c.setColor(resources.getColor(C2022a.mdtp_numbers_text_color));
        this.f6178h = Typeface.create(resources.getString(C2027f.mdtp_radial_numbers_typeface), 0);
        this.f6179i = Typeface.create(resources.getString(C2027f.mdtp_sans_serif), 0);
        this.f6173c.setAntiAlias(true);
        this.f6173c.setTextAlign(Align.CENTER);
        this.f6174d.setColor(resources.getColor(C2022a.mdtp_white));
        this.f6174d.setAntiAlias(true);
        this.f6174d.setTextAlign(Align.CENTER);
        this.f6180j = strArr;
        this.f6181k = strArr2;
        this.f6182l = z;
        if (strArr2 != null) {
            z3 = true;
        }
        this.f6183m = z3;
        if (z) {
            this.f6184n = Float.parseFloat(resources.getString(C2027f.mdtp_circle_radius_multiplier_24HourMode));
        } else {
            this.f6184n = Float.parseFloat(resources.getString(C2027f.mdtp_circle_radius_multiplier));
            this.f6185o = Float.parseFloat(resources.getString(C2027f.mdtp_ampm_circle_radius_multiplier));
        }
        this.f6196z = new float[7];
        this.f6164A = new float[7];
        if (this.f6183m) {
            this.f6186p = Float.parseFloat(resources.getString(C2027f.mdtp_numbers_radius_multiplier_outer));
            this.f6188r = Float.parseFloat(resources.getString(C2027f.mdtp_text_size_multiplier_outer));
            this.f6187q = Float.parseFloat(resources.getString(C2027f.mdtp_numbers_radius_multiplier_inner));
            this.f6189s = Float.parseFloat(resources.getString(C2027f.mdtp_text_size_multiplier_inner));
            this.f6165B = new float[7];
            this.f6166C = new float[7];
        } else {
            this.f6186p = Float.parseFloat(resources.getString(C2027f.mdtp_numbers_radius_multiplier_normal));
            this.f6188r = Float.parseFloat(resources.getString(C2027f.mdtp_text_size_multiplier_normal));
        }
        this.f6167D = 1.0f;
        this.f6168E = (((float) (z2 ? -1 : 1)) * 0.05f) + 1.0f;
        if (z2) {
            i = 1;
        }
        this.f6169F = (0.3f * ((float) i)) + 1.0f;
        this.f6170G = new C2060a();
        this.f6193w = true;
        this.f6176f = true;
    }

    public ObjectAnimator getDisappearAnimator() {
        if (this.f6176f && this.f6175e && this.f6171a != null) {
            return this.f6171a;
        }
        Log.e("RadialTextsView", "RadialTextView was not ready for animation.");
        return null;
    }

    public ObjectAnimator getReappearAnimator() {
        if (this.f6176f && this.f6175e && this.f6172b != null) {
            return this.f6172b;
        }
        Log.e("RadialTextsView", "RadialTextView was not ready for animation.");
        return null;
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public void onDraw(Canvas canvas) {
        if (getWidth() != 0 && this.f6176f) {
            if (!this.f6175e) {
                this.f6190t = getWidth() / 2;
                this.f6191u = getHeight() / 2;
                this.f6192v = ((float) Math.min(this.f6190t, this.f6191u)) * this.f6184n;
                if (!this.f6182l) {
                    this.f6191u = (int) (((double) this.f6191u) - (((double) (this.f6192v * this.f6185o)) * 0.75d));
                }
                this.f6194x = this.f6192v * this.f6188r;
                if (this.f6183m) {
                    this.f6195y = this.f6192v * this.f6189s;
                }
                m9248a();
                this.f6193w = true;
                this.f6175e = true;
            }
            if (this.f6193w) {
                m9249a(this.f6167D * (this.f6192v * this.f6186p), (float) this.f6190t, (float) this.f6191u, this.f6194x, this.f6196z, this.f6164A);
                if (this.f6183m) {
                    m9249a(this.f6167D * (this.f6192v * this.f6187q), (float) this.f6190t, (float) this.f6191u, this.f6195y, this.f6165B, this.f6166C);
                }
                this.f6193w = false;
            }
            m9250a(canvas, this.f6194x, this.f6178h, this.f6180j, this.f6164A, this.f6196z);
            if (this.f6183m) {
                m9250a(canvas, this.f6195y, this.f6179i, this.f6181k, this.f6166C, this.f6165B);
            }
        }
    }

    public void setAnimationRadiusMultiplier(float f) {
        this.f6167D = f;
        this.f6193w = true;
    }

    protected void setSelection(int i) {
        this.f6177g = i;
    }
}
