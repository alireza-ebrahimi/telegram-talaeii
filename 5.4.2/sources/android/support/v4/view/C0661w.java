package android.support.v4.view;

import android.view.View;
import android.view.ViewParent;

/* renamed from: android.support.v4.view.w */
public class C0661w {
    /* renamed from: a */
    private final View f1398a;
    /* renamed from: b */
    private ViewParent f1399b;
    /* renamed from: c */
    private boolean f1400c;
    /* renamed from: d */
    private int[] f1401d;

    public C0661w(View view) {
        this.f1398a = view;
    }

    /* renamed from: a */
    public void m3208a(boolean z) {
        if (this.f1400c) {
            ah.m2766F(this.f1398a);
        }
        this.f1400c = z;
    }

    /* renamed from: a */
    public boolean m3209a() {
        return this.f1400c;
    }

    /* renamed from: a */
    public boolean m3210a(float f, float f2) {
        return (!m3209a() || this.f1399b == null) ? false : au.m2963a(this.f1399b, this.f1398a, f, f2);
    }

    /* renamed from: a */
    public boolean m3211a(float f, float f2, boolean z) {
        return (!m3209a() || this.f1399b == null) ? false : au.m2964a(this.f1399b, this.f1398a, f, f2, z);
    }

    /* renamed from: a */
    public boolean m3212a(int i) {
        if (m3215b()) {
            return true;
        }
        if (m3209a()) {
            View view = this.f1398a;
            for (ViewParent parent = this.f1398a.getParent(); parent != null; parent = parent.getParent()) {
                if (au.m2965a(parent, view, this.f1398a, i)) {
                    this.f1399b = parent;
                    au.m2967b(parent, view, this.f1398a, i);
                    return true;
                }
                if (parent instanceof View) {
                    view = (View) parent;
                }
            }
        }
        return false;
    }

    /* renamed from: a */
    public boolean m3213a(int i, int i2, int i3, int i4, int[] iArr) {
        if (!m3209a() || this.f1399b == null) {
            return false;
        }
        if (i != 0 || i2 != 0 || i3 != 0 || i4 != 0) {
            int i5;
            int i6;
            if (iArr != null) {
                this.f1398a.getLocationInWindow(iArr);
                int i7 = iArr[0];
                i5 = iArr[1];
                i6 = i7;
            } else {
                i5 = 0;
                i6 = 0;
            }
            au.m2961a(this.f1399b, this.f1398a, i, i2, i3, i4);
            if (iArr != null) {
                this.f1398a.getLocationInWindow(iArr);
                iArr[0] = iArr[0] - i6;
                iArr[1] = iArr[1] - i5;
            }
            return true;
        } else if (iArr == null) {
            return false;
        } else {
            iArr[0] = 0;
            iArr[1] = 0;
            return false;
        }
    }

    /* renamed from: a */
    public boolean m3214a(int i, int i2, int[] iArr, int[] iArr2) {
        if (!m3209a() || this.f1399b == null) {
            return false;
        }
        if (i != 0 || i2 != 0) {
            int i3;
            int i4;
            if (iArr2 != null) {
                this.f1398a.getLocationInWindow(iArr2);
                i3 = iArr2[0];
                i4 = iArr2[1];
            } else {
                i4 = 0;
                i3 = 0;
            }
            if (iArr == null) {
                if (this.f1401d == null) {
                    this.f1401d = new int[2];
                }
                iArr = this.f1401d;
            }
            iArr[0] = 0;
            iArr[1] = 0;
            au.m2962a(this.f1399b, this.f1398a, i, i2, iArr);
            if (iArr2 != null) {
                this.f1398a.getLocationInWindow(iArr2);
                iArr2[0] = iArr2[0] - i3;
                iArr2[1] = iArr2[1] - i4;
            }
            return (iArr[0] == 0 && iArr[1] == 0) ? false : true;
        } else if (iArr2 == null) {
            return false;
        } else {
            iArr2[0] = 0;
            iArr2[1] = 0;
            return false;
        }
    }

    /* renamed from: b */
    public boolean m3215b() {
        return this.f1399b != null;
    }

    /* renamed from: c */
    public void m3216c() {
        if (this.f1399b != null) {
            au.m2960a(this.f1399b, this.f1398a);
            this.f1399b = null;
        }
    }
}
