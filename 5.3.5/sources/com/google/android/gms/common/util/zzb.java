package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

@Hide
public final class zzb {
    public static <T> ArrayList<T> zza(T[] tArr) {
        ArrayList<T> arrayList = new ArrayList(r1);
        for (Object add : tArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static void zza(StringBuilder stringBuilder, double[] dArr) {
        int length = dArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Double.toString(dArr[i]));
        }
    }

    public static void zza(StringBuilder stringBuilder, float[] fArr) {
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Float.toString(fArr[i]));
        }
    }

    public static void zza(StringBuilder stringBuilder, long[] jArr) {
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Long.toString(jArr[i]));
        }
    }

    public static <T> void zza(StringBuilder stringBuilder, T[] tArr) {
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(tArr[i].toString());
        }
    }

    public static void zza(StringBuilder stringBuilder, String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\"").append(strArr[i]).append("\"");
        }
    }

    public static void zza(StringBuilder stringBuilder, boolean[] zArr) {
        int length = zArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Boolean.toString(zArr[i]));
        }
    }

    public static byte[] zza(byte[]... bArr) {
        if (bArr.length == 0) {
            return new byte[0];
        }
        int i;
        int i2 = 0;
        for (byte[] length : bArr) {
            i2 += length.length;
        }
        Object copyOf = Arrays.copyOf(bArr[0], i2);
        i2 = bArr[0].length;
        for (i = 1; i < bArr.length; i++) {
            Object obj = bArr[i];
            System.arraycopy(obj, 0, copyOf, i2, obj.length);
            i2 += obj.length;
        }
        return copyOf;
    }

    public static <T> T[] zza(T[] tArr, T... tArr2) {
        if (tArr == null) {
            return null;
        }
        if (tArr2.length == 0) {
            return Arrays.copyOf(tArr, tArr.length);
        }
        int i;
        Object[] objArr = (Object[]) Array.newInstance(tArr2.getClass().getComponentType(), tArr.length);
        int length;
        int i2;
        if (tArr2.length == 1) {
            length = tArr.length;
            int i3 = 0;
            i = 0;
            while (i3 < length) {
                Object obj = tArr[i3];
                if (zzbg.equal(tArr2[0], obj)) {
                    i2 = i;
                } else {
                    i2 = i + 1;
                    objArr[i] = obj;
                }
                i3++;
                i = i2;
            }
        } else {
            int length2 = tArr.length;
            length = 0;
            i = 0;
            while (length < length2) {
                Object obj2 = tArr[length];
                int length3 = tArr2.length;
                i2 = 0;
                while (i2 < length3) {
                    if (zzbg.equal(tArr2[i2], obj2)) {
                        break;
                    }
                    i2++;
                }
                i2 = -1;
                if ((i2 >= 0 ? 1 : 0) == 0) {
                    i2 = i + 1;
                    objArr[i] = obj2;
                } else {
                    i2 = i;
                }
                length++;
                i = i2;
            }
        }
        return objArr == null ? null : i != objArr.length ? Arrays.copyOf(objArr, i) : objArr;
    }

    public static Integer[] zzb(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        Integer[] numArr = new Integer[length];
        for (int i = 0; i < length; i++) {
            numArr[i] = Integer.valueOf(iArr[i]);
        }
        return numArr;
    }
}
