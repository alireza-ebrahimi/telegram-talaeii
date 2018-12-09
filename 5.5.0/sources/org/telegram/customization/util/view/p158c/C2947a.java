package org.telegram.customization.util.view.p158c;

import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.View;

/* renamed from: org.telegram.customization.util.view.c.a */
public class C2947a {
    /* renamed from: a */
    private View[] f9740a = new View[0];
    /* renamed from: b */
    private int[] f9741b = new int[0];
    /* renamed from: c */
    private SparseArray<View>[] f9742c;
    /* renamed from: d */
    private int f9743d;
    /* renamed from: e */
    private SparseArray<View> f9744e;

    /* renamed from: a */
    static View m13607a(SparseArray<View> sparseArray, int i) {
        int size = sparseArray.size();
        if (size <= 0) {
            return null;
        }
        int i2;
        View view;
        for (i2 = 0; i2 < size; i2++) {
            int keyAt = sparseArray.keyAt(i2);
            view = (View) sparseArray.get(keyAt);
            if (keyAt == i) {
                sparseArray.remove(keyAt);
                return view;
            }
        }
        i2 = size - 1;
        view = (View) sparseArray.valueAt(i2);
        sparseArray.remove(sparseArray.keyAt(i2));
        return view;
    }

    /* renamed from: b */
    private void m13608b() {
        int length = this.f9740a.length;
        int i = this.f9743d;
        SparseArray[] sparseArrayArr = this.f9742c;
        for (int i2 = 0; i2 < i; i2++) {
            SparseArray sparseArray = sparseArrayArr[i2];
            int size = sparseArray.size();
            int i3 = size - length;
            int i4 = size - 1;
            size = 0;
            while (size < i3) {
                int i5 = i4 - 1;
                sparseArray.remove(sparseArray.keyAt(i4));
                size++;
                i4 = i5;
            }
        }
    }

    /* renamed from: a */
    View m13609a(int i, int i2) {
        return this.f9743d == 1 ? C2947a.m13607a(this.f9744e, i) : (i2 < 0 || i2 >= this.f9742c.length) ? null : C2947a.m13607a(this.f9742c[i2], i);
    }

    /* renamed from: a */
    void m13610a() {
        Object obj = 1;
        View[] viewArr = this.f9740a;
        int[] iArr = this.f9741b;
        if (this.f9743d <= 1) {
            obj = null;
        }
        SparseArray sparseArray = this.f9744e;
        for (int length = viewArr.length - 1; length >= 0; length--) {
            View view = viewArr[length];
            if (view != null) {
                int i = iArr[length];
                viewArr[length] = null;
                iArr[length] = -1;
                if (m13613b(i)) {
                    if (obj != null) {
                        sparseArray = this.f9742c[i];
                    }
                    sparseArray.put(length, view);
                    if (VERSION.SDK_INT >= 14) {
                        view.setAccessibilityDelegate(null);
                    }
                }
            }
        }
        m13608b();
    }

    /* renamed from: a */
    public void m13611a(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
        }
        SparseArray[] sparseArrayArr = new SparseArray[i];
        for (int i2 = 0; i2 < i; i2++) {
            sparseArrayArr[i2] = new SparseArray();
        }
        this.f9743d = i;
        this.f9744e = sparseArrayArr[0];
        this.f9742c = sparseArrayArr;
    }

    /* renamed from: a */
    void m13612a(View view, int i, int i2) {
        if (this.f9743d == 1) {
            this.f9744e.put(i, view);
        } else {
            this.f9742c[i2].put(i, view);
        }
        if (VERSION.SDK_INT >= 14) {
            view.setAccessibilityDelegate(null);
        }
    }

    /* renamed from: b */
    protected boolean m13613b(int i) {
        return i >= 0;
    }
}
