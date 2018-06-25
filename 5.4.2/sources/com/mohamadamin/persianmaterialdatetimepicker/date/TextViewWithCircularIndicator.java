package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2022a;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2023b;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2027f;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2017a;

public class TextViewWithCircularIndicator extends TextView {
    /* renamed from: a */
    Paint f5953a = new Paint();
    /* renamed from: b */
    private final int f5954b;
    /* renamed from: c */
    private final int f5955c;
    /* renamed from: d */
    private final String f5956d;
    /* renamed from: e */
    private boolean f5957e;

    public TextViewWithCircularIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Resources resources = context.getResources();
        this.f5955c = resources.getColor(C2022a.mdtp_accent_color);
        this.f5954b = resources.getDimensionPixelOffset(C2023b.mdtp_month_select_circle_radius);
        this.f5956d = context.getResources().getString(C2027f.mdtp_item_is_selected);
        m9118a();
    }

    /* renamed from: a */
    private void m9118a() {
        this.f5953a.setFakeBoldText(true);
        this.f5953a.setAntiAlias(true);
        this.f5953a.setColor(this.f5955c);
        this.f5953a.setTextAlign(Align.CENTER);
        this.f5953a.setStyle(Style.FILL);
        this.f5953a.setAlpha(255);
    }

    /* renamed from: a */
    public void m9119a(boolean z) {
        this.f5957e = z;
    }

    public CharSequence getContentDescription() {
        CharSequence a = C2017a.m9087a(getText().toString());
        if (!this.f5957e) {
            return a;
        }
        return String.format(this.f5956d, new Object[]{a});
    }

    public void onDraw(Canvas canvas) {
        if (this.f5957e) {
            int width = getWidth();
            int height = getHeight();
            canvas.drawCircle((float) (width / 2), (float) (height / 2), (float) (Math.min(width, height) / 2), this.f5953a);
        }
        setSelected(this.f5957e);
        super.onDraw(canvas);
    }
}
