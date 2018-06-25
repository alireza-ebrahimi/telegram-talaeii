package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class AutoResizeTextView extends TextView {
    /* renamed from: a */
    private final RectF f7355a;
    /* renamed from: b */
    private final SparseIntArray f7356b;
    /* renamed from: c */
    private final SizeTester f7357c;
    /* renamed from: d */
    private float f7358d;
    /* renamed from: e */
    private float f7359e;
    /* renamed from: f */
    private float f7360f;
    /* renamed from: g */
    private float f7361g;
    /* renamed from: h */
    private int f7362h;
    /* renamed from: i */
    private int f7363i;
    /* renamed from: j */
    private boolean f7364j;
    /* renamed from: k */
    private boolean f7365k;
    /* renamed from: l */
    private TextPaint f7366l;

    private interface SizeTester {
        /* renamed from: a */
        int mo3290a(int i, RectF rectF);
    }

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.AutoResizeTextView$1 */
    class C22811 implements SizeTester {
        /* renamed from: a */
        final RectF f7353a = new RectF();
        /* renamed from: b */
        final /* synthetic */ AutoResizeTextView f7354b;

        C22811(AutoResizeTextView autoResizeTextView) {
            this.f7354b = autoResizeTextView;
        }

        /* renamed from: a */
        public int mo3290a(int i, RectF rectF) {
            this.f7354b.f7366l.setTextSize((float) i);
            CharSequence charSequence = this.f7354b.getText().toString();
            if (this.f7354b.getSupportedMaxLines() == 1) {
                this.f7353a.bottom = this.f7354b.f7366l.getFontSpacing();
                this.f7353a.right = this.f7354b.f7366l.measureText(charSequence);
            } else {
                StaticLayout staticLayout = new StaticLayout(charSequence, this.f7354b.f7366l, this.f7354b.f7362h, Alignment.ALIGN_NORMAL, this.f7354b.f7359e, this.f7354b.f7360f, true);
                if (this.f7354b.getSupportedMaxLines() != -1 && staticLayout.getLineCount() > this.f7354b.getSupportedMaxLines()) {
                    return 1;
                }
                this.f7353a.bottom = (float) staticLayout.getHeight();
                int i2 = -1;
                for (int i3 = 0; i3 < staticLayout.getLineCount(); i3++) {
                    if (((float) i2) < staticLayout.getLineRight(i3) - staticLayout.getLineLeft(i3)) {
                        i2 = ((int) staticLayout.getLineRight(i3)) - ((int) staticLayout.getLineLeft(i3));
                    }
                }
                this.f7353a.right = (float) i2;
            }
            this.f7353a.offsetTo(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
            return rectF.contains(this.f7353a) ? -1 : 1;
        }
    }

    public AutoResizeTextView(Context context) {
        this(context, null, 0);
    }

    public AutoResizeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AutoResizeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f7355a = new RectF();
        this.f7356b = new SparseIntArray();
        this.f7359e = 1.0f;
        this.f7360f = BitmapDescriptorFactory.HUE_RED;
        this.f7364j = true;
        this.f7365k = false;
        this.f7361g = TypedValue.applyDimension(2, 12.0f, getResources().getDisplayMetrics());
        this.f7358d = getTextSize();
        if (this.f7363i == 0) {
            this.f7363i = -1;
        }
        this.f7357c = new C22811(this);
        this.f7365k = true;
    }

    /* renamed from: a */
    private int m11018a(int i, int i2, SizeTester sizeTester, RectF rectF) {
        if (!this.f7364j) {
            return m11022b(i, i2, sizeTester, rectF);
        }
        String charSequence = getText().toString();
        int length = charSequence == null ? 0 : charSequence.length();
        int i3 = this.f7356b.get(length);
        if (i3 != 0) {
            return i3;
        }
        i3 = m11022b(i, i2, sizeTester, rectF);
        this.f7356b.put(length, i3);
        return i3;
    }

    /* renamed from: a */
    private void m11020a() {
        m11024b();
    }

    /* renamed from: a */
    private void m11021a(int i) {
        super.setTextSize(0, (float) m11018a(i, (int) this.f7358d, this.f7357c, this.f7355a));
    }

    /* renamed from: b */
    private int m11022b(int i, int i2, SizeTester sizeTester, RectF rectF) {
        int i3 = i2 - 1;
        int i4 = i;
        int i5 = i;
        while (i4 <= i3) {
            i5 = (i4 + i3) >>> 1;
            int a = sizeTester.mo3290a(i5, rectF);
            if (a < 0) {
                i5++;
            } else if (a <= 0) {
                return i5;
            } else {
                i3 = i5 - 1;
                i5 = i4;
                i4 = i3;
            }
            int i6 = i5;
            i5 = i4;
            i4 = i6;
        }
        return i5;
    }

    /* renamed from: b */
    private void m11024b() {
        if (this.f7365k) {
            int i = (int) this.f7361g;
            int measuredHeight = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
            this.f7362h = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            if (this.f7362h > 0) {
                this.f7355a.right = (float) this.f7362h;
                this.f7355a.bottom = (float) measuredHeight;
                m11021a(i);
            }
        }
    }

    private int getSupportedMaxLines() {
        return this.f7363i;
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        this.f7356b.clear();
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            m11020a();
        }
    }

    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        m11020a();
    }

    public void setEnableSizeCache(boolean z) {
        this.f7364j = z;
        this.f7356b.clear();
        m11024b();
    }

    public void setLineSpacing(float f, float f2) {
        super.setLineSpacing(f, f2);
        this.f7359e = f2;
        this.f7360f = f;
    }

    public void setLines(int i) {
        super.setLines(i);
        this.f7363i = i;
        m11020a();
    }

    public void setMaxLines(int i) {
        super.setMaxLines(i);
        this.f7363i = i;
        m11020a();
    }

    public void setMinTextSize(float f) {
        this.f7361g = f;
        m11020a();
    }

    public void setSingleLine() {
        super.setSingleLine();
        this.f7363i = 1;
        m11020a();
    }

    public void setSingleLine(boolean z) {
        super.setSingleLine(z);
        if (z) {
            this.f7363i = 1;
        } else {
            this.f7363i = -1;
        }
        m11020a();
    }

    public void setTextSize(float f) {
        this.f7358d = f;
        this.f7356b.clear();
        m11024b();
    }

    public void setTextSize(int i, float f) {
        Context context = getContext();
        this.f7358d = TypedValue.applyDimension(i, f, (context == null ? Resources.getSystem() : context.getResources()).getDisplayMetrics());
        this.f7356b.clear();
        m11024b();
    }

    public void setTypeface(Typeface typeface) {
        if (this.f7366l == null) {
            this.f7366l = new TextPaint(getPaint());
        }
        this.f7366l.setTypeface(typeface);
        m11024b();
        super.setTypeface(typeface);
    }
}
