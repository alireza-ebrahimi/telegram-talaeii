package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.p027c.p028a.C0825b;
import android.util.AttributeSet;

public class bk {
    /* renamed from: a */
    private final Context f3044a;
    /* renamed from: b */
    private final TypedArray f3045b;

    private bk(Context context, TypedArray typedArray) {
        this.f3044a = context;
        this.f3045b = typedArray;
    }

    /* renamed from: a */
    public static bk m5652a(Context context, int i, int[] iArr) {
        return new bk(context, context.obtainStyledAttributes(i, iArr));
    }

    /* renamed from: a */
    public static bk m5653a(Context context, AttributeSet attributeSet, int[] iArr) {
        return new bk(context, context.obtainStyledAttributes(attributeSet, iArr));
    }

    /* renamed from: a */
    public static bk m5654a(Context context, AttributeSet attributeSet, int[] iArr, int i, int i2) {
        return new bk(context, context.obtainStyledAttributes(attributeSet, iArr, i, i2));
    }

    /* renamed from: a */
    public float m5655a(int i, float f) {
        return this.f3045b.getFloat(i, f);
    }

    /* renamed from: a */
    public int m5656a(int i, int i2) {
        return this.f3045b.getInt(i, i2);
    }

    /* renamed from: a */
    public Drawable m5657a(int i) {
        if (this.f3045b.hasValue(i)) {
            int resourceId = this.f3045b.getResourceId(i, 0);
            if (resourceId != 0) {
                return C0825b.m3939b(this.f3044a, resourceId);
            }
        }
        return this.f3045b.getDrawable(i);
    }

    /* renamed from: a */
    public void m5658a() {
        this.f3045b.recycle();
    }

    /* renamed from: a */
    public boolean m5659a(int i, boolean z) {
        return this.f3045b.getBoolean(i, z);
    }

    /* renamed from: b */
    public int m5660b(int i, int i2) {
        return this.f3045b.getColor(i, i2);
    }

    /* renamed from: b */
    public Drawable m5661b(int i) {
        if (this.f3045b.hasValue(i)) {
            int resourceId = this.f3045b.getResourceId(i, 0);
            if (resourceId != 0) {
                return C1069l.m5865a().m5884a(this.f3044a, resourceId, true);
            }
        }
        return null;
    }

    /* renamed from: c */
    public int m5662c(int i, int i2) {
        return this.f3045b.getInteger(i, i2);
    }

    /* renamed from: c */
    public CharSequence m5663c(int i) {
        return this.f3045b.getText(i);
    }

    /* renamed from: d */
    public int m5664d(int i, int i2) {
        return this.f3045b.getDimensionPixelOffset(i, i2);
    }

    /* renamed from: d */
    public String m5665d(int i) {
        return this.f3045b.getString(i);
    }

    /* renamed from: e */
    public int m5666e(int i, int i2) {
        return this.f3045b.getDimensionPixelSize(i, i2);
    }

    /* renamed from: e */
    public ColorStateList m5667e(int i) {
        if (this.f3045b.hasValue(i)) {
            int resourceId = this.f3045b.getResourceId(i, 0);
            if (resourceId != 0) {
                ColorStateList a = C0825b.m3936a(this.f3044a, resourceId);
                if (a != null) {
                    return a;
                }
            }
        }
        return this.f3045b.getColorStateList(i);
    }

    /* renamed from: f */
    public int m5668f(int i, int i2) {
        return this.f3045b.getLayoutDimension(i, i2);
    }

    /* renamed from: f */
    public CharSequence[] m5669f(int i) {
        return this.f3045b.getTextArray(i);
    }

    /* renamed from: g */
    public int m5670g(int i, int i2) {
        return this.f3045b.getResourceId(i, i2);
    }

    /* renamed from: g */
    public boolean m5671g(int i) {
        return this.f3045b.hasValue(i);
    }
}
