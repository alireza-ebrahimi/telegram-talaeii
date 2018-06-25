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
import android.util.Log;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2022a;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2027f;
import org.telegram.ui.ChatActivity;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.c */
public class C2058c extends View {
    /* renamed from: a */
    private final Paint f6139a = new Paint();
    /* renamed from: b */
    private boolean f6140b = false;
    /* renamed from: c */
    private boolean f6141c;
    /* renamed from: d */
    private float f6142d;
    /* renamed from: e */
    private float f6143e;
    /* renamed from: f */
    private float f6144f;
    /* renamed from: g */
    private float f6145g;
    /* renamed from: h */
    private float f6146h;
    /* renamed from: i */
    private float f6147i;
    /* renamed from: j */
    private float f6148j;
    /* renamed from: k */
    private boolean f6149k;
    /* renamed from: l */
    private boolean f6150l;
    /* renamed from: m */
    private int f6151m;
    /* renamed from: n */
    private int f6152n;
    /* renamed from: o */
    private int f6153o;
    /* renamed from: p */
    private int f6154p;
    /* renamed from: q */
    private float f6155q;
    /* renamed from: r */
    private float f6156r;
    /* renamed from: s */
    private int f6157s;
    /* renamed from: t */
    private int f6158t;
    /* renamed from: u */
    private C2057a f6159u;
    /* renamed from: v */
    private int f6160v;
    /* renamed from: w */
    private double f6161w;
    /* renamed from: x */
    private boolean f6162x;

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.c$a */
    private class C2057a implements AnimatorUpdateListener {
        /* renamed from: a */
        final /* synthetic */ C2058c f6138a;

        private C2057a(C2058c c2058c) {
            this.f6138a = c2058c;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.f6138a.invalidate();
        }
    }

    public C2058c(Context context) {
        super(context);
    }

    /* renamed from: a */
    public int m9244a(float f, float f2, boolean z, Boolean[] boolArr) {
        boolean z2 = true;
        if (!this.f6141c) {
            return -1;
        }
        double sqrt = Math.sqrt((double) (((f2 - ((float) this.f6153o)) * (f2 - ((float) this.f6153o))) + ((f - ((float) this.f6152n)) * (f - ((float) this.f6152n)))));
        if (this.f6150l) {
            if (z) {
                boolArr[0] = Boolean.valueOf(((int) Math.abs(sqrt - ((double) ((int) (((float) this.f6154p) * this.f6144f))))) <= ((int) Math.abs(sqrt - ((double) ((int) (((float) this.f6154p) * this.f6145g))))));
            } else {
                int i = ((int) (((float) this.f6154p) * this.f6145g)) + this.f6158t;
                int i2 = (int) (((float) this.f6154p) * ((this.f6145g + this.f6144f) / 2.0f));
                if (sqrt >= ((double) (((int) (((float) this.f6154p) * this.f6144f)) - this.f6158t)) && sqrt <= ((double) i2)) {
                    boolArr[0] = Boolean.valueOf(true);
                } else if (sqrt > ((double) i) || sqrt < ((double) i2)) {
                    return -1;
                } else {
                    boolArr[0] = Boolean.valueOf(false);
                }
            }
        } else if (!z && ((int) Math.abs(sqrt - ((double) this.f6157s))) > ((int) (((float) this.f6154p) * (1.0f - this.f6146h)))) {
            return -1;
        }
        int asin = (int) ((Math.asin(((double) Math.abs(f2 - ((float) this.f6153o))) / sqrt) * 180.0d) / 3.141592653589793d);
        boolean z3 = f > ((float) this.f6152n);
        if (f2 >= ((float) this.f6153o)) {
            z2 = false;
        }
        return (z3 && z2) ? 90 - asin : (!z3 || z2) ? (z3 || z2) ? (z3 || !z2) ? asin : asin + 270 : 270 - asin : asin + 90;
    }

    /* renamed from: a */
    public void m9245a(int i, boolean z, boolean z2) {
        this.f6160v = i;
        this.f6161w = (((double) i) * 3.141592653589793d) / 180.0d;
        this.f6162x = z2;
        if (!this.f6150l) {
            return;
        }
        if (z) {
            this.f6146h = this.f6144f;
        } else {
            this.f6146h = this.f6145g;
        }
    }

    /* renamed from: a */
    void m9246a(Context context, boolean z) {
        int color;
        Resources resources = context.getResources();
        if (z) {
            color = resources.getColor(C2022a.mdtp_red);
            this.f6151m = 255;
        } else {
            color = resources.getColor(C2022a.mdtp_accent_color);
            this.f6151m = 255;
        }
        this.f6139a.setColor(color);
    }

    /* renamed from: a */
    public void m9247a(Context context, boolean z, boolean z2, boolean z3, int i, boolean z4) {
        int i2 = -1;
        if (this.f6140b) {
            Log.e("RadialSelectorView", "This RadialSelectorView may only be initialized once.");
            return;
        }
        Resources resources = context.getResources();
        this.f6139a.setColor(resources.getColor(C2022a.mdtp_accent_color));
        this.f6139a.setAntiAlias(true);
        this.f6151m = 255;
        this.f6149k = z;
        if (z) {
            this.f6142d = Float.parseFloat(resources.getString(C2027f.mdtp_circle_radius_multiplier_24HourMode));
        } else {
            this.f6142d = Float.parseFloat(resources.getString(C2027f.mdtp_circle_radius_multiplier));
            this.f6143e = Float.parseFloat(resources.getString(C2027f.mdtp_ampm_circle_radius_multiplier));
        }
        this.f6150l = z2;
        if (z2) {
            this.f6144f = Float.parseFloat(resources.getString(C2027f.mdtp_numbers_radius_multiplier_inner));
            this.f6145g = Float.parseFloat(resources.getString(C2027f.mdtp_numbers_radius_multiplier_outer));
        } else {
            this.f6146h = Float.parseFloat(resources.getString(C2027f.mdtp_numbers_radius_multiplier_normal));
        }
        this.f6147i = Float.parseFloat(resources.getString(C2027f.mdtp_selection_radius_multiplier));
        this.f6148j = 1.0f;
        this.f6155q = (((float) (z3 ? -1 : 1)) * 0.05f) + 1.0f;
        if (z3) {
            i2 = 1;
        }
        this.f6156r = (0.3f * ((float) i2)) + 1.0f;
        this.f6159u = new C2057a();
        m9245a(i, z4, false);
        this.f6140b = true;
    }

    public ObjectAnimator getDisappearAnimator() {
        if (this.f6140b && this.f6141c) {
            Keyframe ofFloat = Keyframe.ofFloat(BitmapDescriptorFactory.HUE_RED, 1.0f);
            Keyframe ofFloat2 = Keyframe.ofFloat(0.2f, this.f6155q);
            Keyframe ofFloat3 = Keyframe.ofFloat(1.0f, this.f6156r);
            PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe("animationRadiusMultiplier", new Keyframe[]{ofFloat, ofFloat2, ofFloat3});
            ofFloat = Keyframe.ofFloat(BitmapDescriptorFactory.HUE_RED, 1.0f);
            ofFloat3 = Keyframe.ofFloat(1.0f, BitmapDescriptorFactory.HUE_RED);
            PropertyValuesHolder ofKeyframe2 = PropertyValuesHolder.ofKeyframe("alpha", new Keyframe[]{ofFloat, ofFloat3});
            ObjectAnimator duration = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofKeyframe, ofKeyframe2}).setDuration((long) ChatActivity.startAllServices);
            duration.addUpdateListener(this.f6159u);
            return duration;
        }
        Log.e("RadialSelectorView", "RadialSelectorView was not ready for animation.");
        return null;
    }

    public ObjectAnimator getReappearAnimator() {
        if (this.f6140b && this.f6141c) {
            int i = (int) ((1.0f + 0.25f) * ((float) 500));
            float f = (((float) 500) * 0.25f) / ((float) i);
            float f2 = 1.0f - (0.2f * (1.0f - f));
            Keyframe ofFloat = Keyframe.ofFloat(BitmapDescriptorFactory.HUE_RED, this.f6156r);
            Keyframe ofFloat2 = Keyframe.ofFloat(f, this.f6156r);
            Keyframe ofFloat3 = Keyframe.ofFloat(f2, this.f6155q);
            Keyframe ofFloat4 = Keyframe.ofFloat(1.0f, 1.0f);
            PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe("animationRadiusMultiplier", new Keyframe[]{ofFloat, ofFloat2, ofFloat3, ofFloat4});
            ofFloat = Keyframe.ofFloat(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
            Keyframe ofFloat5 = Keyframe.ofFloat(f, BitmapDescriptorFactory.HUE_RED);
            ofFloat2 = Keyframe.ofFloat(1.0f, 1.0f);
            PropertyValuesHolder ofKeyframe2 = PropertyValuesHolder.ofKeyframe("alpha", new Keyframe[]{ofFloat, ofFloat5, ofFloat2});
            ObjectAnimator duration = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofKeyframe, ofKeyframe2}).setDuration((long) i);
            duration.addUpdateListener(this.f6159u);
            return duration;
        }
        Log.e("RadialSelectorView", "RadialSelectorView was not ready for animation.");
        return null;
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public void onDraw(Canvas canvas) {
        int i = 1;
        if (getWidth() != 0 && this.f6140b) {
            int i2;
            if (!this.f6141c) {
                this.f6152n = getWidth() / 2;
                this.f6153o = getHeight() / 2;
                this.f6154p = (int) (((float) Math.min(this.f6152n, this.f6153o)) * this.f6142d);
                if (!this.f6149k) {
                    this.f6153o = (int) (((double) this.f6153o) - (((double) ((int) (((float) this.f6154p) * this.f6143e))) * 0.75d));
                }
                this.f6158t = (int) (((float) this.f6154p) * this.f6147i);
                this.f6141c = true;
            }
            this.f6157s = (int) ((((float) this.f6154p) * this.f6146h) * this.f6148j);
            int sin = ((int) (((double) this.f6157s) * Math.sin(this.f6161w))) + this.f6152n;
            int cos = this.f6153o - ((int) (((double) this.f6157s) * Math.cos(this.f6161w)));
            this.f6139a.setAlpha(this.f6151m);
            canvas.drawCircle((float) sin, (float) cos, (float) this.f6158t, this.f6139a);
            boolean z = this.f6162x;
            if (this.f6160v % 30 == 0) {
                i = 0;
            }
            if ((i | z) != 0) {
                this.f6139a.setAlpha(255);
                canvas.drawCircle((float) sin, (float) cos, (float) ((this.f6158t * 2) / 7), this.f6139a);
                i2 = sin;
            } else {
                cos = this.f6157s - this.f6158t;
                sin = this.f6152n + ((int) (((double) cos) * Math.sin(this.f6161w)));
                cos = this.f6153o - ((int) (((double) cos) * Math.cos(this.f6161w)));
                i2 = sin;
            }
            this.f6139a.setAlpha(255);
            this.f6139a.setStrokeWidth(1.0f);
            canvas.drawLine((float) this.f6152n, (float) this.f6153o, (float) i2, (float) cos, this.f6139a);
        }
    }

    public void setAnimationRadiusMultiplier(float f) {
        this.f6148j = f;
    }
}
