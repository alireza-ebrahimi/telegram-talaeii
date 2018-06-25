package com.google.android.gms.wearable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.internal.zzdng;
import com.google.android.gms.internal.zzdnh;
import com.google.android.gms.internal.zzdni;
import com.google.android.gms.internal.zzfls;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class DataMap {
    public static final String TAG = "DataMap";
    private final HashMap<String, Object> zzall = new HashMap();

    public static ArrayList<DataMap> arrayListFromBundleArrayList(@NonNull ArrayList<Bundle> arrayList) {
        ArrayList<DataMap> arrayList2 = new ArrayList();
        ArrayList arrayList3 = arrayList;
        int size = arrayList3.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList3.get(i);
            i++;
            arrayList2.add(fromBundle((Bundle) obj));
        }
        return arrayList2;
    }

    public static DataMap fromBundle(@NonNull Bundle bundle) {
        bundle.setClassLoader(Asset.class.getClassLoader());
        DataMap dataMap = new DataMap();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj instanceof String) {
                dataMap.putString(str, (String) obj);
            } else if (obj instanceof Integer) {
                dataMap.putInt(str, ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                dataMap.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof Double) {
                dataMap.putDouble(str, ((Double) obj).doubleValue());
            } else if (obj instanceof Float) {
                dataMap.putFloat(str, ((Float) obj).floatValue());
            } else if (obj instanceof Boolean) {
                dataMap.putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Byte) {
                dataMap.putByte(str, ((Byte) obj).byteValue());
            } else if (obj instanceof byte[]) {
                dataMap.putByteArray(str, (byte[]) obj);
            } else if (obj instanceof String[]) {
                dataMap.putStringArray(str, (String[]) obj);
            } else if (obj instanceof long[]) {
                dataMap.putLongArray(str, (long[]) obj);
            } else if (obj instanceof float[]) {
                dataMap.putFloatArray(str, (float[]) obj);
            } else if (obj instanceof Asset) {
                dataMap.putAsset(str, (Asset) obj);
            } else if (obj instanceof Bundle) {
                dataMap.putDataMap(str, fromBundle((Bundle) obj));
            } else if (obj instanceof ArrayList) {
                switch (zze((ArrayList) obj)) {
                    case 0:
                        dataMap.putStringArrayList(str, (ArrayList) obj);
                        break;
                    case 1:
                        dataMap.putStringArrayList(str, (ArrayList) obj);
                        break;
                    case 2:
                        dataMap.putIntegerArrayList(str, (ArrayList) obj);
                        break;
                    case 3:
                        dataMap.putStringArrayList(str, (ArrayList) obj);
                        break;
                    case 5:
                        dataMap.putDataMapArrayList(str, arrayListFromBundleArrayList((ArrayList) obj));
                        break;
                    default:
                        break;
                }
            }
        }
        return dataMap;
    }

    public static DataMap fromByteArray(@NonNull byte[] bArr) {
        try {
            return zzdng.zza(new zzdnh(zzdni.zzad(bArr), new ArrayList()));
        } catch (Throwable e) {
            throw new IllegalArgumentException("Unable to convert data", e);
        }
    }

    private static void zza(String str, Object obj, String str2, ClassCastException classCastException) {
        zza(str, obj, str2, "<null>", classCastException);
    }

    private static void zza(String str, Object obj, String str2, Object obj2, ClassCastException classCastException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Key ");
        stringBuilder.append(str);
        stringBuilder.append(" expected ");
        stringBuilder.append(str2);
        stringBuilder.append(" but value was a ");
        stringBuilder.append(obj.getClass().getName());
        stringBuilder.append(".  The default value ");
        stringBuilder.append(obj2);
        stringBuilder.append(" was returned.");
        Log.w(TAG, stringBuilder.toString());
        Log.w(TAG, "Attempt to cast generated internal exception:", classCastException);
    }

    private static void zzb(Bundle bundle, String str, Object obj) {
        if (obj instanceof String) {
            bundle.putString(str, (String) obj);
        } else if (obj instanceof Integer) {
            bundle.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            bundle.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof Double) {
            bundle.putDouble(str, ((Double) obj).doubleValue());
        } else if (obj instanceof Float) {
            bundle.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof Boolean) {
            bundle.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Byte) {
            bundle.putByte(str, ((Byte) obj).byteValue());
        } else if (obj instanceof byte[]) {
            bundle.putByteArray(str, (byte[]) obj);
        } else if (obj instanceof String[]) {
            bundle.putStringArray(str, (String[]) obj);
        } else if (obj instanceof long[]) {
            bundle.putLongArray(str, (long[]) obj);
        } else if (obj instanceof float[]) {
            bundle.putFloatArray(str, (float[]) obj);
        } else if (obj instanceof Asset) {
            bundle.putParcelable(str, (Asset) obj);
        } else if (obj instanceof DataMap) {
            bundle.putParcelable(str, ((DataMap) obj).toBundle());
        } else if (obj instanceof ArrayList) {
            switch (zze((ArrayList) obj)) {
                case 0:
                    bundle.putStringArrayList(str, (ArrayList) obj);
                    return;
                case 1:
                    bundle.putStringArrayList(str, (ArrayList) obj);
                    return;
                case 2:
                    bundle.putIntegerArrayList(str, (ArrayList) obj);
                    return;
                case 3:
                    bundle.putStringArrayList(str, (ArrayList) obj);
                    return;
                case 4:
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = (ArrayList) obj;
                    int size = arrayList2.size();
                    int i = 0;
                    while (i < size) {
                        Object obj2 = arrayList2.get(i);
                        i++;
                        arrayList.add(((DataMap) obj2).toBundle());
                    }
                    bundle.putParcelableArrayList(str, arrayList);
                    return;
                default:
                    return;
            }
        }
    }

    private static int zze(ArrayList<?> arrayList) {
        int i = 0;
        if (arrayList.isEmpty()) {
            return 0;
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            if (obj != null) {
                if (obj instanceof Integer) {
                    return 2;
                }
                if (obj instanceof String) {
                    return 3;
                }
                if (obj instanceof DataMap) {
                    return 4;
                }
                if (obj instanceof Bundle) {
                    return 5;
                }
            }
        }
        return 1;
    }

    public void clear() {
        this.zzall.clear();
    }

    public boolean containsKey(@NonNull String str) {
        return this.zzall.containsKey(str);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DataMap)) {
            return false;
        }
        DataMap dataMap = (DataMap) obj;
        if (size() != dataMap.size()) {
            return false;
        }
        for (String str : keySet()) {
            Object obj2 = get(str);
            Object obj3 = dataMap.get(str);
            if (obj2 instanceof Asset) {
                if (!(obj3 instanceof Asset)) {
                    return false;
                }
                Asset asset = (Asset) obj2;
                Asset asset2 = (Asset) obj3;
                boolean equals = (asset == null || asset2 == null) ? asset == asset2 : !TextUtils.isEmpty(asset.getDigest()) ? asset.getDigest().equals(asset2.getDigest()) : Arrays.equals(asset.getData(), asset2.getData());
                if (!equals) {
                    return false;
                }
            } else if (obj2 instanceof String[]) {
                if (!(obj3 instanceof String[])) {
                    return false;
                }
                if (!Arrays.equals((String[]) obj2, (String[]) obj3)) {
                    return false;
                }
            } else if (obj2 instanceof long[]) {
                if (!(obj3 instanceof long[])) {
                    return false;
                }
                if (!Arrays.equals((long[]) obj2, (long[]) obj3)) {
                    return false;
                }
            } else if (obj2 instanceof float[]) {
                if (!(obj3 instanceof float[])) {
                    return false;
                }
                if (!Arrays.equals((float[]) obj2, (float[]) obj3)) {
                    return false;
                }
            } else if (obj2 instanceof byte[]) {
                if (!(obj3 instanceof byte[])) {
                    return false;
                }
                if (!Arrays.equals((byte[]) obj2, (byte[]) obj3)) {
                    return false;
                }
            } else if (obj2 == null || obj3 == null) {
                if (obj2 != obj3) {
                    return false;
                }
                return true;
            } else if (!obj2.equals(obj3)) {
                return false;
            }
        }
        return true;
    }

    public <T> T get(@NonNull String str) {
        return this.zzall.get(str);
    }

    public Asset getAsset(@NonNull String str) {
        Object obj = this.zzall.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (Asset) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "Asset", e);
            return null;
        }
    }

    public boolean getBoolean(@NonNull String str) {
        return getBoolean(str, false);
    }

    public boolean getBoolean(@NonNull String str, boolean z) {
        Object obj = this.zzall.get(str);
        if (obj != null) {
            try {
                z = ((Boolean) obj).booleanValue();
            } catch (ClassCastException e) {
                zza(str, obj, "Boolean", Boolean.valueOf(z), e);
            }
        }
        return z;
    }

    public byte getByte(@NonNull String str) {
        return getByte(str, (byte) 0);
    }

    public byte getByte(@NonNull String str, byte b) {
        Object obj = this.zzall.get(str);
        if (obj != null) {
            try {
                b = ((Byte) obj).byteValue();
            } catch (ClassCastException e) {
                zza(str, obj, "Byte", Byte.valueOf(b), e);
            }
        }
        return b;
    }

    public byte[] getByteArray(@NonNull String str) {
        Object obj = this.zzall.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (byte[]) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "byte[]", e);
            return null;
        }
    }

    public DataMap getDataMap(@NonNull String str) {
        Object obj = this.zzall.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (DataMap) obj;
        } catch (ClassCastException e) {
            zza(str, obj, TAG, e);
            return null;
        }
    }

    public ArrayList<DataMap> getDataMapArrayList(@NonNull String str) {
        Object obj = this.zzall.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (ArrayList) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "ArrayList<DataMap>", e);
            return null;
        }
    }

    public double getDouble(@NonNull String str) {
        return getDouble(str, 0.0d);
    }

    public double getDouble(@NonNull String str, double d) {
        Object obj = this.zzall.get(str);
        if (obj != null) {
            try {
                d = ((Double) obj).doubleValue();
            } catch (ClassCastException e) {
                zza(str, obj, "Double", Double.valueOf(d), e);
            }
        }
        return d;
    }

    public float getFloat(@NonNull String str) {
        return getFloat(str, 0.0f);
    }

    public float getFloat(@NonNull String str, float f) {
        Object obj = this.zzall.get(str);
        if (obj != null) {
            try {
                f = ((Float) obj).floatValue();
            } catch (ClassCastException e) {
                zza(str, obj, "Float", Float.valueOf(f), e);
            }
        }
        return f;
    }

    public float[] getFloatArray(@NonNull String str) {
        Object obj = this.zzall.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (float[]) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "float[]", e);
            return null;
        }
    }

    public int getInt(@NonNull String str) {
        return getInt(str, 0);
    }

    public int getInt(@NonNull String str, int i) {
        Object obj = this.zzall.get(str);
        if (obj != null) {
            try {
                i = ((Integer) obj).intValue();
            } catch (ClassCastException e) {
                zza(str, obj, "Integer", e);
            }
        }
        return i;
    }

    public ArrayList<Integer> getIntegerArrayList(@NonNull String str) {
        Object obj = this.zzall.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (ArrayList) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "ArrayList<Integer>", e);
            return null;
        }
    }

    public long getLong(@NonNull String str) {
        return getLong(str, 0);
    }

    public long getLong(@NonNull String str, long j) {
        Object obj = this.zzall.get(str);
        if (obj != null) {
            try {
                j = ((Long) obj).longValue();
            } catch (ClassCastException e) {
                zza(str, obj, "long", e);
            }
        }
        return j;
    }

    public long[] getLongArray(@NonNull String str) {
        Object obj = this.zzall.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (long[]) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "long[]", e);
            return null;
        }
    }

    public String getString(@NonNull String str) {
        Object obj = this.zzall.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (String) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "String", e);
            return null;
        }
    }

    public String getString(@NonNull String str, String str2) {
        String string = getString(str);
        return string == null ? str2 : string;
    }

    public String[] getStringArray(@NonNull String str) {
        Object obj = this.zzall.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (String[]) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "String[]", e);
            return null;
        }
    }

    public ArrayList<String> getStringArrayList(@NonNull String str) {
        Object obj = this.zzall.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (ArrayList) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "ArrayList<String>", e);
            return null;
        }
    }

    public int hashCode() {
        return this.zzall.hashCode() * 29;
    }

    public boolean isEmpty() {
        return this.zzall.isEmpty();
    }

    public Set<String> keySet() {
        return this.zzall.keySet();
    }

    public void putAll(@NonNull DataMap dataMap) {
        for (String str : dataMap.keySet()) {
            this.zzall.put(str, dataMap.get(str));
        }
    }

    public void putAsset(@NonNull String str, Asset asset) {
        this.zzall.put(str, asset);
    }

    public void putBoolean(@NonNull String str, boolean z) {
        this.zzall.put(str, Boolean.valueOf(z));
    }

    public void putByte(@NonNull String str, byte b) {
        this.zzall.put(str, Byte.valueOf(b));
    }

    public void putByteArray(@NonNull String str, byte[] bArr) {
        this.zzall.put(str, bArr);
    }

    public void putDataMap(@NonNull String str, DataMap dataMap) {
        this.zzall.put(str, dataMap);
    }

    public void putDataMapArrayList(@NonNull String str, ArrayList<DataMap> arrayList) {
        this.zzall.put(str, arrayList);
    }

    public void putDouble(@NonNull String str, double d) {
        this.zzall.put(str, Double.valueOf(d));
    }

    public void putFloat(@NonNull String str, float f) {
        this.zzall.put(str, Float.valueOf(f));
    }

    public void putFloatArray(@NonNull String str, float[] fArr) {
        this.zzall.put(str, fArr);
    }

    public void putInt(@NonNull String str, int i) {
        this.zzall.put(str, Integer.valueOf(i));
    }

    public void putIntegerArrayList(@NonNull String str, ArrayList<Integer> arrayList) {
        this.zzall.put(str, arrayList);
    }

    public void putLong(@NonNull String str, long j) {
        this.zzall.put(str, Long.valueOf(j));
    }

    public void putLongArray(@NonNull String str, long[] jArr) {
        this.zzall.put(str, jArr);
    }

    public void putString(@NonNull String str, String str2) {
        this.zzall.put(str, str2);
    }

    public void putStringArray(@NonNull String str, String[] strArr) {
        this.zzall.put(str, strArr);
    }

    public void putStringArrayList(@NonNull String str, ArrayList<String> arrayList) {
        this.zzall.put(str, arrayList);
    }

    public Object remove(@NonNull String str) {
        return this.zzall.remove(str);
    }

    public int size() {
        return this.zzall.size();
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        for (String str : this.zzall.keySet()) {
            zzb(bundle, str, this.zzall.get(str));
        }
        return bundle;
    }

    public byte[] toByteArray() {
        return zzfls.zzc(zzdng.zza(this).zzlwk);
    }

    public String toString() {
        return this.zzall.toString();
    }
}
