package com.mohamadamin.persianmaterialdatetimepicker.time;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2022a;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2027f;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.a */
public class C2054a extends View {
    /* renamed from: a */
    private final Paint f6108a = new Paint();
    /* renamed from: b */
    private int f6109b;
    /* renamed from: c */
    private int f6110c;
    /* renamed from: d */
    private int f6111d;
    /* renamed from: e */
    private int f6112e;
    /* renamed from: f */
    private int f6113f;
    /* renamed from: g */
    private int f6114g;
    /* renamed from: h */
    private float f6115h;
    /* renamed from: i */
    private float f6116i;
    /* renamed from: j */
    private String f6117j;
    /* renamed from: k */
    private String f6118k;
    /* renamed from: l */
    private boolean f6119l = false;
    /* renamed from: m */
    private boolean f6120m;
    /* renamed from: n */
    private int f6121n;
    /* renamed from: o */
    private int f6122o;
    /* renamed from: p */
    private int f6123p;
    /* renamed from: q */
    private int f6124q;
    /* renamed from: r */
    private int f6125r;
    /* renamed from: s */
    private int f6126s;

    public C2054a(Context context) {
        super(context);
    }

    /* renamed from: a */
    public int m9239a(float f, float f2) {
        if (!this.f6120m) {
            return -1;
        }
        int i = (int) ((f2 - ((float) this.f6124q)) * (f2 - ((float) this.f6124q)));
        if (((int) Math.sqrt((double) (((f - ((float) this.f6122o)) * (f - ((float) this.f6122o))) + ((float) i)))) <= this.f6121n) {
            return 0;
        }
        return ((int) Math.sqrt((double) (((float) i) + ((f - ((float) this.f6123p)) * (f - ((float) this.f6123p)))))) <= this.f6121n ? 1 : -1;
    }

    /* renamed from: a */
    public void m9240a(Context context, int i) {
        if (this.f6119l) {
            Log.e("AmPmCirclesView", "AmPmCirclesView may only be initialized once.");
            return;
        }
        Resources resources = context.getResources();
        this.f6111d = resources.getColor(C2022a.mdtp_white);
        this.f6114g = resources.getColor(C2022a.mdtp_accent_color);
        this.f6110c = resources.getColor(C2022a.mdtp_accent_color_dark);
        this.f6112e = resources.getColor(C2022a.mdtp_ampm_text_color);
        this.f6113f = resources.getColor(C2022a.mdtp_white);
        this.f6109b = 255;
        this.f6108a.setTypeface(Typeface.create(resources.getString(C2027f.mdtp_sans_serif), 0));
        this.f6108a.setAntiAlias(true);
        this.f6108a.setTextAlign(Align.CENTER);
        this.f6115h = Float.parseFloat(resources.getString(C2027f.mdtp_circle_radius_multiplier));
        this.f6116i = Float.parseFloat(resources.getString(C2027f.mdtp_ampm_circle_radius_multiplier));
        this.f6117j = "قبل‌ازظهر";
        this.f6118k = "بعدازظهر";
        setAmOrPm(i);
        this.f6126s = -1;
        this.f6119l = true;
    }

    /* renamed from: a */
    void m9241a(Context context, boolean z) {
        Resources resources = context.getResources();
        if (z) {
            this.f6111d = resources.getColor(C2022a.mdtp_circle_background_dark_theme);
            this.f6114g = resources.getColor(C2022a.mdtp_red);
            this.f6112e = resources.getColor(C2022a.mdtp_white);
            this.f6109b = 255;
            return;
        }
        this.f6111d = resources.getColor(C2022a.mdtp_white);
        this.f6114g = resources.getColor(C2022a.mdtp_accent_color);
        this.f6112e = resources.getColor(C2022a.mdtp_ampm_text_color);
        this.f6109b = 255;
    }

    public void onDraw(Canvas canvas) {
        int i = 255;
        if (getWidth() != 0 && this.f6119l) {
            int width;
            int height;
            int min;
            if (!this.f6120m) {
                width = getWidth() / 2;
                height = getHeight() / 2;
                min = (int) (((float) Math.min(width, height)) * this.f6115h);
                this.f6121n = (int) (((float) min) * this.f6116i);
                height = (int) (((double) height) + (((double) this.f6121n) * 0.75d));
                this.f6108a.setTextSize((float) ((this.f6121n * 3) / 4));
                this.f6124q = (height - (this.f6121n / 2)) + min;
                this.f6122o = (width - min) + this.f6121n;
                this.f6123p = (width + min) - this.f6121n;
                this.f6120m = true;
            }
            int i2 = this.f6111d;
            int i3 = this.f6112e;
            height = this.f6111d;
            width = this.f6112e;
            if (this.f6125r == 0) {
                i2 = this.f6114g;
                i3 = this.f6109b;
                min = this.f6113f;
            } else if (this.f6125r == 1) {
                min = this.f6114g;
                height = this.f6109b;
                width = this.f6113f;
                int i4 = height;
                height = min;
                min = i3;
                i3 = 255;
                i = i4;
            } else {
                min = i3;
                i3 = 255;
            }
            if (this.f6126s == 0) {
                i2 = this.f6110c;
                i3 = this.f6109b;
            } else if (this.f6126s == 1) {
                height = this.f6110c;
                i = this.f6109b;
            }
            this.f6108a.setColor(i2);
            this.f6108a.setAlpha(i3);
            canvas.drawCircle((float) this.f6122o, (float) this.f6124q, (float) this.f6121n, this.f6108a);
            this.f6108a.setColor(height);
            this.f6108a.setAlpha(i);
            canvas.drawCircle((float) this.f6123p, (float) this.f6124q, (float) this.f6121n, this.f6108a);
            this.f6108a.setColor(min);
            i = this.f6124q - (((int) (this.f6108a.descent() + this.f6108a.ascent())) / 2);
            canvas.drawText(this.f6117j, (float) this.f6122o, (float) i, this.f6108a);
            this.f6108a.setColor(width);
            canvas.drawText(this.f6118k, (float) this.f6123p, (float) i, this.f6108a);
        }
    }

    public void setAmOrPm(int i) {
        this.f6125r = i;
    }

    public void setAmOrPmPressed(int i) {
        this.f6126s = i;
    }
}
