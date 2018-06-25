package com.mohamadamin.persianmaterialdatetimepicker.time;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2022a;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2027f;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.b */
public class C2055b extends View {
    /* renamed from: a */
    private final Paint f6127a = new Paint();
    /* renamed from: b */
    private boolean f6128b;
    /* renamed from: c */
    private int f6129c;
    /* renamed from: d */
    private int f6130d;
    /* renamed from: e */
    private float f6131e;
    /* renamed from: f */
    private float f6132f;
    /* renamed from: g */
    private boolean f6133g;
    /* renamed from: h */
    private boolean f6134h;
    /* renamed from: i */
    private int f6135i;
    /* renamed from: j */
    private int f6136j;
    /* renamed from: k */
    private int f6137k;

    public C2055b(Context context) {
        super(context);
        Resources resources = context.getResources();
        this.f6129c = resources.getColor(C2022a.mdtp_circle_color);
        this.f6130d = resources.getColor(C2022a.mdtp_numbers_text_color);
        this.f6127a.setAntiAlias(true);
        this.f6133g = false;
    }

    /* renamed from: a */
    public void m9242a(Context context, boolean z) {
        if (this.f6133g) {
            Log.e("CircleView", "CircleView may only be initialized once.");
            return;
        }
        Resources resources = context.getResources();
        this.f6128b = z;
        if (z) {
            this.f6131e = Float.parseFloat(resources.getString(C2027f.mdtp_circle_radius_multiplier_24HourMode));
        } else {
            this.f6131e = Float.parseFloat(resources.getString(C2027f.mdtp_circle_radius_multiplier));
            this.f6132f = Float.parseFloat(resources.getString(C2027f.mdtp_ampm_circle_radius_multiplier));
        }
        this.f6133g = true;
    }

    /* renamed from: b */
    void m9243b(Context context, boolean z) {
        Resources resources = context.getResources();
        if (z) {
            this.f6129c = resources.getColor(C2022a.mdtp_circle_background_dark_theme);
            this.f6130d = resources.getColor(C2022a.mdtp_white);
            return;
        }
        this.f6129c = resources.getColor(C2022a.mdtp_circle_color);
        this.f6130d = resources.getColor(C2022a.mdtp_numbers_text_color);
    }

    public void onDraw(Canvas canvas) {
        if (getWidth() != 0 && this.f6133g) {
            if (!this.f6134h) {
                this.f6135i = getWidth() / 2;
                this.f6136j = getHeight() / 2;
                this.f6137k = (int) (((float) Math.min(this.f6135i, this.f6136j)) * this.f6131e);
                if (!this.f6128b) {
                    this.f6136j = (int) (((double) this.f6136j) - (((double) ((int) (((float) this.f6137k) * this.f6132f))) * 0.75d));
                }
                this.f6134h = true;
            }
            this.f6127a.setColor(this.f6129c);
            canvas.drawCircle((float) this.f6135i, (float) this.f6136j, (float) this.f6137k, this.f6127a);
            this.f6127a.setColor(this.f6130d);
            canvas.drawCircle((float) this.f6135i, (float) this.f6136j, 4.0f, this.f6127a);
        }
    }
}
