package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.Objects;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

@VisibleForTesting
public final class ArrayUtils {

    private static class zza {
        HashMap<Object, zza> zzzb;

        zza(int i) {
            this.zzzb = new HashMap(i);
        }

        private final zza zzd(Object obj) {
            zza zza = (zza) this.zzzb.get(obj);
            if (zza != null) {
                return zza;
            }
            zza = new zza();
            this.zzzb.put(obj, zza);
            return zza;
        }

        final void zzb(Object obj) {
            zza zzd = zzd(obj);
            zzd.count++;
        }

        final void zzc(Object obj) {
            zza zzd = zzd(obj);
            zzd.count--;
        }
    }

    private ArrayUtils() {
    }

    public static int[] appendToArray(int[] iArr, int i) {
        int[] copyOf = (iArr == null || iArr.length == 0) ? new int[1] : Arrays.copyOf(iArr, iArr.length + 1);
        copyOf[copyOf.length - 1] = i;
        return copyOf;
    }

    public static <T> T[] appendToArray(T[] tArr, T t) {
        if (tArr == null && t == null) {
            throw new IllegalArgumentException("Cannot generate array of generic type w/o class info");
        }
        T[] copyOf = tArr == null ? (Object[]) Array.newInstance(t.getClass(), 1) : Arrays.copyOf(tArr, tArr.length + 1);
        copyOf[copyOf.length - 1] = t;
        return copyOf;
    }

    public static <T> T[] concat(T[]... tArr) {
        if (tArr.length == 0) {
            return (Object[]) Array.newInstance(tArr.getClass(), 0);
        }
        int i;
        int i2 = 0;
        for (T[] length : tArr) {
            i2 += length.length;
        }
        Object copyOf = Arrays.copyOf(tArr[0], i2);
        i2 = tArr[0].length;
        for (i = 1; i < tArr.length; i++) {
            Object obj = tArr[i];
            System.arraycopy(obj, 0, copyOf, i2, obj.length);
            i2 += obj.length;
        }
        return copyOf;
    }

    public static byte[] concatByteArrays(byte[]... bArr) {
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

    public static boolean contains(byte[] bArr, byte b) {
        if (bArr == null) {
            return false;
        }
        for (byte b2 : bArr) {
            if (b2 == b) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(char[] cArr, char c) {
        if (cArr == null) {
            return false;
        }
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(double[] dArr, double d) {
        if (dArr == null) {
            return false;
        }
        for (double d2 : dArr) {
            if (d2 == d) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(float[] fArr, float f, float f2) {
        if (fArr == null) {
            return false;
        }
        float f3 = f - f2;
        float f4 = f + f2;
        for (float f5 : fArr) {
            if (f3 <= f5 && f5 <= f4) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(int[] iArr, int i) {
        if (iArr == null) {
            return false;
        }
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean contains(T[] tArr, T t) {
        return indexOf(tArr, t) >= 0;
    }

    public static boolean contains(short[] sArr, short s) {
        if (sArr == null) {
            return false;
        }
        for (short s2 : sArr) {
            if (s2 == s) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(boolean[] zArr, boolean z) {
        if (zArr == null) {
            return false;
        }
        for (boolean z2 : zArr) {
            if (z2 == z) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsIgnoreCase(String[] strArr, String str) {
        if (strArr == null) {
            return false;
        }
        for (String str2 : strArr) {
            if (str2 == str) {
                return true;
            }
            if (str2 != null && str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalsAnyOrder(Object[] objArr, Object[] objArr2) {
        if (objArr == objArr2) {
            return true;
        }
        int length = objArr == null ? 0 : objArr.length;
        int length2 = objArr2 == null ? 0 : objArr2.length;
        if (length == 0 && length2 == 0) {
            return true;
        }
        if (length != length2) {
            return false;
        }
        zza zza = new zza(length);
        for (Object zzb : objArr) {
            zza.zzb(zzb);
        }
        for (Object zzb2 : objArr2) {
            zza.zzc(zzb2);
        }
        for (zza zza2 : zza.zzzb.values()) {
            if (zza2.count != 0) {
                return false;
            }
        }
        return true;
    }

    public static <T> int indexOf(T[] tArr, T t) {
        int i = 0;
        int length = tArr != null ? tArr.length : 0;
        while (i < length) {
            if (Objects.equal(tArr[i], t)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList();
    }

    public static <T> int rearrange(T[] tArr, Predicate<T> predicate) {
        int i = 0;
        if (!(tArr == null || tArr.length == 0)) {
            int length = tArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (predicate.apply(tArr[i2])) {
                    if (i != i2) {
                        T t = tArr[i];
                        tArr[i] = tArr[i2];
                        tArr[i2] = t;
                    }
                    i++;
                }
            }
        }
        return i;
    }

    public static int[] removeAll(int[] iArr, int... iArr2) {
        int i = 0;
        if (iArr == null) {
            return null;
        }
        if (iArr2 == null || iArr2.length == 0) {
            return Arrays.copyOf(iArr, iArr.length);
        }
        int i2;
        int[] iArr3 = new int[iArr.length];
        int length;
        int i3;
        int i4;
        if (iArr2.length == 1) {
            length = iArr.length;
            i3 = 0;
            i2 = 0;
            while (i3 < length) {
                int i5 = iArr[i3];
                if (iArr2[0] != i5) {
                    i4 = i2 + 1;
                    iArr3[i2] = i5;
                } else {
                    i4 = i2;
                }
                i3++;
                i2 = i4;
            }
        } else {
            i3 = iArr.length;
            i2 = 0;
            while (i < i3) {
                length = iArr[i];
                if (contains(iArr2, length)) {
                    i4 = i2;
                } else {
                    i4 = i2 + 1;
                    iArr3[i2] = length;
                }
                i++;
                i2 = i4;
            }
        }
        return resize(iArr3, i2);
    }

    public static <T> T[] removeAll(T[] tArr, T... tArr2) {
        int i = 0;
        if (tArr == null) {
            return null;
        }
        if (tArr2 == null || tArr2.length == 0) {
            return Arrays.copyOf(tArr, tArr.length);
        }
        int i2;
        Object[] objArr = (Object[]) Array.newInstance(tArr2.getClass().getComponentType(), tArr.length);
        int i3;
        int i4;
        if (tArr2.length == 1) {
            int length = tArr.length;
            i3 = 0;
            i2 = 0;
            while (i3 < length) {
                Object obj = tArr[i3];
                if (Objects.equal(tArr2[0], obj)) {
                    i4 = i2;
                } else {
                    i4 = i2 + 1;
                    objArr[i2] = obj;
                }
                i3++;
                i2 = i4;
            }
        } else {
            i3 = tArr.length;
            i2 = 0;
            while (i < i3) {
                Object obj2 = tArr[i];
                if (contains((Object[]) tArr2, obj2)) {
                    i4 = i2;
                } else {
                    i4 = i2 + 1;
                    objArr[i2] = obj2;
                }
                i++;
                i2 = i4;
            }
        }
        return resize(objArr, i2);
    }

    public static int[] resize(int[] iArr, int i) {
        return iArr == null ? null : i != iArr.length ? Arrays.copyOf(iArr, i) : iArr;
    }

    public static <T> T[] resize(T[] tArr, int i) {
        return tArr == null ? null : i != tArr.length ? Arrays.copyOf(tArr, i) : tArr;
    }

    public static <T> ArrayList<T> toArrayList(Collection<T> collection) {
        return collection == null ? null : new ArrayList(collection);
    }

    public static <T> ArrayList<T> toArrayList(T[] tArr) {
        ArrayList<T> arrayList = new ArrayList(r1);
        for (Object add : tArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static long[] toLongArray(Collection<Long> collection) {
        if (collection == null || collection.size() == 0) {
            return new long[0];
        }
        long[] jArr = new long[collection.size()];
        int i = 0;
        for (Long longValue : collection) {
            int i2 = i + 1;
            jArr[i] = longValue.longValue();
            i = i2;
        }
        return jArr;
    }

    public static long[] toLongArray(Long[] lArr) {
        int i = 0;
        if (lArr == null) {
            return new long[0];
        }
        long[] jArr = new long[lArr.length];
        while (i < lArr.length) {
            jArr[i] = lArr[i].longValue();
            i++;
        }
        return jArr;
    }

    public static int[] toPrimitiveArray(Collection<Integer> collection) {
        if (collection == null || collection.size() == 0) {
            return new int[0];
        }
        int[] iArr = new int[collection.size()];
        int i = 0;
        for (Integer intValue : collection) {
            int i2 = i + 1;
            iArr[i] = intValue.intValue();
            i = i2;
        }
        return iArr;
    }

    public static int[] toPrimitiveArray(Integer[] numArr) {
        int i = 0;
        if (numArr == null) {
            return new int[0];
        }
        int[] iArr = new int[numArr.length];
        while (i < numArr.length) {
            iArr[i] = numArr[i].intValue();
            i++;
        }
        return iArr;
    }

    public static String[] toStringArray(Collection<String> collection) {
        return (collection == null || collection.size() == 0) ? new String[0] : (String[]) collection.toArray(new String[collection.size()]);
    }

    public static Boolean[] toWrapperArray(boolean[] zArr) {
        if (zArr == null) {
            return null;
        }
        int length = zArr.length;
        Boolean[] boolArr = new Boolean[length];
        for (int i = 0; i < length; i++) {
            boolArr[i] = Boolean.valueOf(zArr[i]);
        }
        return boolArr;
    }

    public static Byte[] toWrapperArray(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        Byte[] bArr2 = new Byte[length];
        for (int i = 0; i < length; i++) {
            bArr2[i] = Byte.valueOf(bArr[i]);
        }
        return bArr2;
    }

    public static Character[] toWrapperArray(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        int length = cArr.length;
        Character[] chArr = new Character[length];
        for (int i = 0; i < length; i++) {
            chArr[i] = Character.valueOf(cArr[i]);
        }
        return chArr;
    }

    public static Double[] toWrapperArray(double[] dArr) {
        if (dArr == null) {
            return null;
        }
        int length = dArr.length;
        Double[] dArr2 = new Double[length];
        for (int i = 0; i < length; i++) {
            dArr2[i] = Double.valueOf(dArr[i]);
        }
        return dArr2;
    }

    public static Float[] toWrapperArray(float[] fArr) {
        if (fArr == null) {
            return null;
        }
        int length = fArr.length;
        Float[] fArr2 = new Float[length];
        for (int i = 0; i < length; i++) {
            fArr2[i] = Float.valueOf(fArr[i]);
        }
        return fArr2;
    }

    public static Integer[] toWrapperArray(int[] iArr) {
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

    public static Long[] toWrapperArray(long[] jArr) {
        if (jArr == null) {
            return null;
        }
        int length = jArr.length;
        Long[] lArr = new Long[length];
        for (int i = 0; i < length; i++) {
            lArr[i] = Long.valueOf(jArr[i]);
        }
        return lArr;
    }

    public static Short[] toWrapperArray(short[] sArr) {
        if (sArr == null) {
            return null;
        }
        int length = sArr.length;
        Short[] shArr = new Short[length];
        for (int i = 0; i < length; i++) {
            shArr[i] = Short.valueOf(sArr[i]);
        }
        return shArr;
    }

    public static void writeArray(StringBuilder stringBuilder, double[] dArr) {
        int length = dArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Double.toString(dArr[i]));
        }
    }

    public static void writeArray(StringBuilder stringBuilder, float[] fArr) {
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Float.toString(fArr[i]));
        }
    }

    public static void writeArray(StringBuilder stringBuilder, int[] iArr) {
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Integer.toString(iArr[i]));
        }
    }

    public static void writeArray(StringBuilder stringBuilder, long[] jArr) {
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Long.toString(jArr[i]));
        }
    }

    public static <T> void writeArray(StringBuilder stringBuilder, T[] tArr) {
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(tArr[i].toString());
        }
    }

    public static void writeArray(StringBuilder stringBuilder, boolean[] zArr) {
        int length = zArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Boolean.toString(zArr[i]));
        }
    }

    public static void writeStringArray(StringBuilder stringBuilder, String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\"").append(strArr[i]).append("\"");
        }
    }
}
