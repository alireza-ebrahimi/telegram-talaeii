package android.support.v7.p027c.p028a;

import java.lang.reflect.Array;

/* renamed from: android.support.v7.c.a.c */
final class C0826c {
    /* renamed from: a */
    static final /* synthetic */ boolean f1940a = (!C0826c.class.desiredAssertionStatus());

    private C0826c() {
    }

    /* renamed from: a */
    public static int m3943a(int i) {
        return i <= 4 ? 8 : i * 2;
    }

    /* renamed from: a */
    public static int[] m3944a(int[] iArr, int i, int i2) {
        if (f1940a || i <= iArr.length) {
            if (i + 1 > iArr.length) {
                Object obj = new int[C0826c.m3943a(i)];
                System.arraycopy(iArr, 0, obj, 0, i);
                iArr = obj;
            }
            iArr[i] = i2;
            return iArr;
        }
        throw new AssertionError();
    }

    /* renamed from: a */
    public static <T> T[] m3945a(T[] tArr, int i, T t) {
        if (f1940a || i <= tArr.length) {
            T[] tArr2;
            if (i + 1 > tArr.length) {
                tArr2 = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), C0826c.m3943a(i));
                System.arraycopy(tArr, 0, tArr2, 0, i);
            } else {
                tArr2 = tArr;
            }
            tArr2[i] = t;
            return tArr2;
        }
        throw new AssertionError();
    }
}
