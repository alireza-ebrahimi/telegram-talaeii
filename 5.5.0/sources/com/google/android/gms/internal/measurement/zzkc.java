package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ParseException;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.measurement.AppMeasurement.Event;
import com.google.android.gms.measurement.AppMeasurement.UserProperty;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.security.auth.x500.X500Principal;

public final class zzkc extends zzhi {
    private static final String[] zzarp = new String[]{"firebase_", "google_", "ga_"};
    private SecureRandom zzarq;
    private final AtomicLong zzarr = new AtomicLong(0);
    private int zzars;
    private Integer zzart = null;

    zzkc(zzgm zzgm) {
        super(zzgm);
    }

    static MessageDigest getMessageDigest(String str) {
        int i = 0;
        while (i < 2) {
            try {
                MessageDigest instance = MessageDigest.getInstance(str);
                if (instance != null) {
                    return instance;
                }
                i++;
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return null;
    }

    private static Object zza(int i, Object obj, boolean z) {
        if (obj == null) {
            return null;
        }
        if ((obj instanceof Long) || (obj instanceof Double)) {
            return obj;
        }
        if (obj instanceof Integer) {
            return Long.valueOf((long) ((Integer) obj).intValue());
        }
        if (obj instanceof Byte) {
            return Long.valueOf((long) ((Byte) obj).byteValue());
        }
        if (obj instanceof Short) {
            return Long.valueOf((long) ((Short) obj).shortValue());
        }
        if (!(obj instanceof Boolean)) {
            return obj instanceof Float ? Double.valueOf(((Float) obj).doubleValue()) : ((obj instanceof String) || (obj instanceof Character) || (obj instanceof CharSequence)) ? zza(String.valueOf(obj), i, z) : null;
        } else {
            return Long.valueOf(((Boolean) obj).booleanValue() ? 1 : 0);
        }
    }

    public static String zza(String str, int i, boolean z) {
        return str.codePointCount(0, str.length()) > i ? z ? String.valueOf(str.substring(0, str.offsetByCodePoints(0, i))).concat("...") : null : str;
    }

    public static String zza(String str, String[] strArr, String[] strArr2) {
        Preconditions.checkNotNull(strArr);
        Preconditions.checkNotNull(strArr2);
        int min = Math.min(strArr.length, strArr2.length);
        for (int i = 0; i < min; i++) {
            if (zzs(str, strArr[i])) {
                return strArr2[i];
            }
        }
        return null;
    }

    private static void zza(Bundle bundle, Object obj) {
        Preconditions.checkNotNull(bundle);
        if (obj == null) {
            return;
        }
        if ((obj instanceof String) || (obj instanceof CharSequence)) {
            bundle.putLong("_el", (long) String.valueOf(obj).length());
        }
    }

    public static boolean zza(Context context, boolean z) {
        Preconditions.checkNotNull(context);
        return VERSION.SDK_INT >= 24 ? zzc(context, "com.google.android.gms.measurement.AppMeasurementJobService") : zzc(context, "com.google.android.gms.measurement.AppMeasurementService");
    }

    private static boolean zza(Bundle bundle, int i) {
        if (bundle.getLong("_err") != 0) {
            return false;
        }
        bundle.putLong("_err", (long) i);
        return true;
    }

    private final boolean zza(String str, String str2, int i, Object obj, boolean z) {
        if (obj == null || (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Integer) || (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Boolean) || (obj instanceof Double)) {
            return true;
        }
        if ((obj instanceof String) || (obj instanceof Character) || (obj instanceof CharSequence)) {
            String valueOf = String.valueOf(obj);
            if (valueOf.codePointCount(0, valueOf.length()) <= i) {
                return true;
            }
            zzgf().zziv().zzd("Value is too long; discarded. Value kind, name, value length", str, str2, Integer.valueOf(valueOf.length()));
            return false;
        } else if ((obj instanceof Bundle) && z) {
            return true;
        } else {
            int length;
            int i2;
            Object obj2;
            if ((obj instanceof Parcelable[]) && z) {
                Parcelable[] parcelableArr = (Parcelable[]) obj;
                length = parcelableArr.length;
                i2 = 0;
                while (i2 < length) {
                    obj2 = parcelableArr[i2];
                    if (obj2 instanceof Bundle) {
                        i2++;
                    } else {
                        zzgf().zziv().zze("All Parcelable[] elements must be of type Bundle. Value type, name", obj2.getClass(), str2);
                        return false;
                    }
                }
                return true;
            } else if (!(obj instanceof ArrayList) || !z) {
                return false;
            } else {
                ArrayList arrayList = (ArrayList) obj;
                length = arrayList.size();
                i2 = 0;
                while (i2 < length) {
                    obj2 = arrayList.get(i2);
                    i2++;
                    if (!(obj2 instanceof Bundle)) {
                        zzgf().zziv().zze("All ArrayList elements must be of type Bundle. Value type, name", obj2.getClass(), str2);
                        return false;
                    }
                }
                return true;
            }
        }
    }

    public static boolean zza(long[] jArr, int i) {
        return i < (jArr.length << 6) && (jArr[i / 64] & (1 << (i % 64))) != 0;
    }

    static byte[] zza(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        try {
            parcelable.writeToParcel(obtain, 0);
            byte[] marshall = obtain.marshall();
            return marshall;
        } finally {
            obtain.recycle();
        }
    }

    public static long[] zza(BitSet bitSet) {
        int length = (bitSet.length() + 63) / 64;
        long[] jArr = new long[length];
        int i = 0;
        while (i < length) {
            jArr[i] = 0;
            int i2 = 0;
            while (i2 < 64 && (i << 6) + i2 < bitSet.length()) {
                if (bitSet.get((i << 6) + i2)) {
                    jArr[i] = jArr[i] | (1 << i2);
                }
                i2++;
            }
            i++;
        }
        return jArr;
    }

    @VisibleForTesting
    static long zzc(byte[] bArr) {
        long j = null;
        Preconditions.checkNotNull(bArr);
        Preconditions.checkState(bArr.length > 0);
        long j2 = 0;
        int length = bArr.length - 1;
        while (length >= 0 && length >= bArr.length - 8) {
            j2 += (((long) bArr[length]) & 255) << j;
            j += 8;
            length--;
        }
        return j2;
    }

    private static boolean zzc(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return false;
            }
            ServiceInfo serviceInfo = packageManager.getServiceInfo(new ComponentName(context, str), 0);
            return serviceInfo != null && serviceInfo.enabled;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    static boolean zzcb(String str) {
        Preconditions.checkNotEmpty(str);
        return str.charAt(0) != '_' || str.equals("_ep");
    }

    private static int zzcg(String str) {
        return "_ldl".equals(str) ? 2048 : "_id".equals(str) ? 256 : 36;
    }

    public static boolean zzch(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith("_");
    }

    static boolean zzcj(String str) {
        return str != null && str.matches("(\\+|-)?([0-9]+\\.?[0-9]*|[0-9]*\\.?[0-9]+)") && str.length() <= 310;
    }

    static boolean zzck(String str) {
        Preconditions.checkNotEmpty(str);
        boolean z = true;
        switch (str.hashCode()) {
            case 94660:
                if (str.equals("_in")) {
                    z = false;
                    break;
                }
                break;
            case 95025:
                if (str.equals("_ug")) {
                    z = true;
                    break;
                }
                break;
            case 95027:
                if (str.equals("_ui")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
            case true:
            case true:
                return true;
            default:
                return false;
        }
    }

    public static boolean zzd(Intent intent) {
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        return "android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(stringExtra) || "https://www.google.com".equals(stringExtra) || "android-app://com.google.appcrawler".equals(stringExtra);
    }

    @VisibleForTesting
    private final boolean zze(Context context, String str) {
        X500Principal x500Principal = new X500Principal("CN=Android Debug,O=Android,C=US");
        try {
            PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(str, 64);
            if (!(packageInfo == null || packageInfo.signatures == null || packageInfo.signatures.length <= 0)) {
                return ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(packageInfo.signatures[0].toByteArray()))).getSubjectX500Principal().equals(x500Principal);
            }
        } catch (CertificateException e) {
            zzgf().zzis().zzg("Error obtaining certificate", e);
        } catch (NameNotFoundException e2) {
            zzgf().zzis().zzg("Package name not found", e2);
        }
        return true;
    }

    public static Bundle[] zze(Object obj) {
        if (obj instanceof Bundle) {
            return new Bundle[]{(Bundle) obj};
        } else if (obj instanceof Parcelable[]) {
            return (Bundle[]) Arrays.copyOf((Parcelable[]) obj, ((Parcelable[]) obj).length, Bundle[].class);
        } else {
            if (!(obj instanceof ArrayList)) {
                return null;
            }
            ArrayList arrayList = (ArrayList) obj;
            return (Bundle[]) arrayList.toArray(new Bundle[arrayList.size()]);
        }
    }

    public static Object zzf(Object obj) {
        Throwable th;
        if (obj == null) {
            return null;
        }
        ObjectOutputStream objectOutputStream;
        ObjectInputStream objectInputStream;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream.writeObject(obj);
                objectOutputStream.flush();
                objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            } catch (Throwable th2) {
                th = th2;
                objectInputStream = null;
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                throw th;
            }
            try {
                Object readObject = objectInputStream.readObject();
                try {
                    objectOutputStream.close();
                    objectInputStream.close();
                    return readObject;
                } catch (IOException e) {
                    return null;
                } catch (ClassNotFoundException e2) {
                    return null;
                }
            } catch (Throwable th3) {
                th = th3;
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            objectInputStream = null;
            objectOutputStream = null;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            throw th;
        }
    }

    private final boolean zzr(String str, String str2) {
        if (str2 == null) {
            zzgf().zzis().zzg("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.length() == 0) {
            zzgf().zzis().zzg("Name is required and can't be empty. Type", str);
            return false;
        } else {
            int codePointAt = str2.codePointAt(0);
            if (Character.isLetter(codePointAt) || codePointAt == 95) {
                int length = str2.length();
                codePointAt = Character.charCount(codePointAt);
                while (codePointAt < length) {
                    int codePointAt2 = str2.codePointAt(codePointAt);
                    if (codePointAt2 == 95 || Character.isLetterOrDigit(codePointAt2)) {
                        codePointAt += Character.charCount(codePointAt2);
                    } else {
                        zzgf().zzis().zze("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                        return false;
                    }
                }
                return true;
            }
            zzgf().zzis().zze("Name must start with a letter or _ (underscore). Type, name", str, str2);
            return false;
        }
    }

    public static boolean zzs(String str, String str2) {
        return (str == null && str2 == null) ? true : str == null ? false : str.equals(str2);
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final Bundle zza(Uri uri) {
        Bundle bundle = null;
        if (uri != null) {
            try {
                Object queryParameter;
                Object queryParameter2;
                Object queryParameter3;
                Object queryParameter4;
                if (uri.isHierarchical()) {
                    queryParameter = uri.getQueryParameter("utm_campaign");
                    queryParameter2 = uri.getQueryParameter("utm_source");
                    queryParameter3 = uri.getQueryParameter("utm_medium");
                    queryParameter4 = uri.getQueryParameter("gclid");
                } else {
                    queryParameter4 = null;
                    queryParameter3 = null;
                    queryParameter2 = null;
                    queryParameter = null;
                }
                if (!(TextUtils.isEmpty(queryParameter) && TextUtils.isEmpty(queryParameter2) && TextUtils.isEmpty(queryParameter3) && TextUtils.isEmpty(queryParameter4))) {
                    bundle = new Bundle();
                    if (!TextUtils.isEmpty(queryParameter)) {
                        bundle.putString(C1797b.CAMPAIGN, queryParameter);
                    }
                    if (!TextUtils.isEmpty(queryParameter2)) {
                        bundle.putString(C1797b.SOURCE, queryParameter2);
                    }
                    if (!TextUtils.isEmpty(queryParameter3)) {
                        bundle.putString(C1797b.MEDIUM, queryParameter3);
                    }
                    if (!TextUtils.isEmpty(queryParameter4)) {
                        bundle.putString("gclid", queryParameter4);
                    }
                    queryParameter4 = uri.getQueryParameter("utm_term");
                    if (!TextUtils.isEmpty(queryParameter4)) {
                        bundle.putString(C1797b.TERM, queryParameter4);
                    }
                    queryParameter4 = uri.getQueryParameter("utm_content");
                    if (!TextUtils.isEmpty(queryParameter4)) {
                        bundle.putString(C1797b.CONTENT, queryParameter4);
                    }
                    queryParameter4 = uri.getQueryParameter(C1797b.ACLID);
                    if (!TextUtils.isEmpty(queryParameter4)) {
                        bundle.putString(C1797b.ACLID, queryParameter4);
                    }
                    queryParameter4 = uri.getQueryParameter(C1797b.CP1);
                    if (!TextUtils.isEmpty(queryParameter4)) {
                        bundle.putString(C1797b.CP1, queryParameter4);
                    }
                    queryParameter4 = uri.getQueryParameter("anid");
                    if (!TextUtils.isEmpty(queryParameter4)) {
                        bundle.putString("anid", queryParameter4);
                    }
                }
            } catch (UnsupportedOperationException e) {
                zzgf().zziv().zzg("Install referrer url isn't a hierarchical URI", e);
            }
        }
        return bundle;
    }

    public final Bundle zza(String str, Bundle bundle, List<String> list, boolean z, boolean z2) {
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = new Bundle(bundle);
        int i = 0;
        for (String str2 : bundle.keySet()) {
            int i2 = 0;
            if (list == null || !list.contains(str2)) {
                if (z) {
                    i2 = !zzq("event param", str2) ? 3 : !zza("event param", null, str2) ? 14 : !zza("event param", 40, str2) ? 3 : 0;
                }
                if (i2 == 0) {
                    i2 = !zzr("event param", str2) ? 3 : !zza("event param", null, str2) ? 14 : !zza("event param", 40, str2) ? 3 : 0;
                }
            }
            if (i2 != 0) {
                if (zza(bundle2, i2)) {
                    bundle2.putString("_ev", zza(str2, 40, true));
                    if (i2 == 3) {
                        zza(bundle2, (Object) str2);
                    }
                }
                bundle2.remove(str2);
            } else {
                Object obj = bundle.get(str2);
                zzab();
                if (z2) {
                    Object obj2;
                    String str3 = "param";
                    if (obj instanceof Parcelable[]) {
                        i2 = ((Parcelable[]) obj).length;
                    } else if (obj instanceof ArrayList) {
                        i2 = ((ArrayList) obj).size();
                    } else {
                        obj2 = 1;
                        if (obj2 == null) {
                            i2 = 17;
                            if (i2 != 0 || "_ev".equals(str2)) {
                                if (zzcb(str2)) {
                                    i2 = i + 1;
                                    if (i2 > 25) {
                                        zzgf().zzis().zze("Event can't contain more than 25 params", zzgb().zzbi(str), zzgb().zzb(bundle));
                                        zza(bundle2, 5);
                                        bundle2.remove(str2);
                                        i = i2;
                                    }
                                } else {
                                    i2 = i;
                                }
                                i = i2;
                            } else {
                                if (zza(bundle2, i2)) {
                                    bundle2.putString("_ev", zza(str2, 40, true));
                                    zza(bundle2, bundle.get(str2));
                                }
                                bundle2.remove(str2);
                            }
                        }
                    }
                    if (i2 > 1000) {
                        zzgf().zziv().zzd("Parameter array is too long; discarded. Value kind, name, array length", str3, str2, Integer.valueOf(i2));
                        obj2 = null;
                    } else {
                        obj2 = 1;
                    }
                    if (obj2 == null) {
                        i2 = 17;
                        if (i2 != 0) {
                        }
                        if (zzcb(str2)) {
                            i2 = i;
                        } else {
                            i2 = i + 1;
                            if (i2 > 25) {
                                zzgf().zzis().zze("Event can't contain more than 25 params", zzgb().zzbi(str), zzgb().zzb(bundle));
                                zza(bundle2, 5);
                                bundle2.remove(str2);
                                i = i2;
                            }
                        }
                        i = i2;
                    }
                }
                boolean zza = ((zzgh().zzav(zzfw().zzah()) && zzch(str)) || zzch(str2)) ? zza("param", str2, 256, obj, z2) : zza("param", str2, 100, obj, z2);
                i2 = zza ? 0 : 4;
                if (i2 != 0) {
                }
                if (zzcb(str2)) {
                    i2 = i + 1;
                    if (i2 > 25) {
                        zzgf().zzis().zze("Event can't contain more than 25 params", zzgb().zzbi(str), zzgb().zzb(bundle));
                        zza(bundle2, 5);
                        bundle2.remove(str2);
                        i = i2;
                    }
                } else {
                    i2 = i;
                }
                i = i2;
            }
        }
        return bundle2;
    }

    final <T extends Parcelable> T zza(byte[] bArr, Creator<T> creator) {
        if (bArr == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        T t;
        try {
            obtain.unmarshall(bArr, 0, bArr.length);
            obtain.setDataPosition(0);
            t = (Parcelable) creator.createFromParcel(obtain);
            return t;
        } catch (ParseException e) {
            t = zzgf().zzis();
            t.log("Failed to load parcelable from buffer");
            return null;
        } finally {
            obtain.recycle();
        }
    }

    final zzew zza(String str, Bundle bundle, String str2, long j, boolean z, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (zzcc(str) != 0) {
            zzgf().zzis().zzg("Invalid conditional property event name", zzgb().zzbk(str));
            throw new IllegalArgumentException();
        }
        Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
        bundle2.putString("_o", str2);
        return new zzew(str, new zzet(zzd(zza(str, bundle2, CollectionUtils.listOf((Object) "_o"), false, false))), str2, j);
    }

    public final void zza(int i, String str, String str2, int i2) {
        zza(null, i, str, str2, i2);
    }

    public final void zza(Bundle bundle, String str, Object obj) {
        if (bundle != null) {
            if (obj instanceof Long) {
                bundle.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof String) {
                bundle.putString(str, String.valueOf(obj));
            } else if (obj instanceof Double) {
                bundle.putDouble(str, ((Double) obj).doubleValue());
            } else if (str != null) {
                zzgf().zziw().zze("Not putting event parameter. Invalid value type. name, type", zzgb().zzbj(str), obj != null ? obj.getClass().getSimpleName() : null);
            }
        }
    }

    public final void zza(String str, int i, String str2, String str3, int i2) {
        Bundle bundle = new Bundle();
        zza(bundle, i);
        if (!TextUtils.isEmpty(str2)) {
            bundle.putString(str2, str3);
        }
        if (i == 6 || i == 7 || i == 2) {
            bundle.putLong("_el", (long) i2);
        }
        this.zzacw.zzgi();
        this.zzacw.zzfv().logEvent("auto", "_err", bundle);
    }

    public final boolean zza(long j, long j2) {
        return j == 0 || j2 <= 0 || Math.abs(zzbt().currentTimeMillis() - j) > j2;
    }

    final boolean zza(String str, int i, String str2) {
        if (str2 == null) {
            zzgf().zzis().zzg("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.codePointCount(0, str2.length()) <= i) {
            return true;
        } else {
            zzgf().zzis().zzd("Name is too long. Type, maximum supported length, name", str, Integer.valueOf(i), str2);
            return false;
        }
    }

    final boolean zza(String str, String[] strArr, String str2) {
        if (str2 == null) {
            zzgf().zzis().zzg("Name is required and can't be null. Type", str);
            return false;
        }
        boolean z;
        Preconditions.checkNotNull(str2);
        for (String startsWith : zzarp) {
            if (str2.startsWith(startsWith)) {
                z = true;
                break;
            }
        }
        z = false;
        if (z) {
            zzgf().zzis().zze("Name starts with reserved prefix. Type, name", str, str2);
            return false;
        }
        if (strArr != null) {
            Preconditions.checkNotNull(strArr);
            for (String startsWith2 : strArr) {
                if (zzs(str2, startsWith2)) {
                    z = true;
                    break;
                }
            }
            z = false;
            if (z) {
                zzgf().zzis().zze("Name is reserved. Type, name", str, str2);
                return false;
            }
        }
        return true;
    }

    public final byte[] zza(byte[] bArr) {
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            zzgf().zzis().zzg("Failed to gzip content", e);
            throw e;
        }
    }

    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    public final byte[] zzb(byte[] bArr) {
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[1024];
            while (true) {
                int read = gZIPInputStream.read(bArr2);
                if (read > 0) {
                    byteArrayOutputStream.write(bArr2, 0, read);
                } else {
                    gZIPInputStream.close();
                    byteArrayInputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e) {
            zzgf().zzis().zzg("Failed to ungzip content", e);
            throw e;
        }
    }

    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    public final int zzcc(String str) {
        return !zzr("event", str) ? 2 : !zza("event", Event.zzacx, str) ? 13 : zza("event", 40, str) ? 0 : 2;
    }

    public final int zzcd(String str) {
        return !zzq("user property", str) ? 6 : !zza("user property", UserProperty.zzadb, str) ? 15 : zza("user property", 24, str) ? 0 : 6;
    }

    public final int zzce(String str) {
        return !zzr("user property", str) ? 6 : !zza("user property", UserProperty.zzadb, str) ? 15 : zza("user property", 24, str) ? 0 : 6;
    }

    public final boolean zzcf(String str) {
        if (TextUtils.isEmpty(str)) {
            zzgf().zzis().log("Missing google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI");
            return false;
        }
        Preconditions.checkNotNull(str);
        if (str.matches("^(1:\\d+:android:[a-f0-9]+|ca-app-pub-.*)$")) {
            return true;
        }
        zzgf().zzis().zzg("Invalid google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI. provided id", str);
        return false;
    }

    public final boolean zzci(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String zzhn = zzgh().zzhn();
        zzgi();
        return zzhn.equals(str);
    }

    final long zzd(Context context, String str) {
        zzab();
        Preconditions.checkNotNull(context);
        Preconditions.checkNotEmpty(str);
        PackageManager packageManager = context.getPackageManager();
        MessageDigest messageDigest = getMessageDigest("MD5");
        if (messageDigest == null) {
            zzgf().zzis().log("Could not get MD5 instance");
            return -1;
        }
        if (packageManager != null) {
            try {
                if (!zze(context, str)) {
                    PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(getContext().getPackageName(), 64);
                    if (packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                        return zzc(messageDigest.digest(packageInfo.signatures[0].toByteArray()));
                    }
                    zzgf().zziv().log("Could not get signatures");
                    return -1;
                }
            } catch (NameNotFoundException e) {
                zzgf().zzis().zzg("Package name not found", e);
            }
        }
        return 0;
    }

    final Bundle zzd(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                Object zzh = zzh(str, bundle.get(str));
                if (zzh == null) {
                    zzgf().zziv().zzg("Param value can't be null", zzgb().zzbj(str));
                } else {
                    zza(bundle2, str, zzh);
                }
            }
        }
        return bundle2;
    }

    final boolean zzd(zzew zzew, zzdz zzdz) {
        Preconditions.checkNotNull(zzew);
        Preconditions.checkNotNull(zzdz);
        if (!TextUtils.isEmpty(zzdz.zzadm)) {
            return true;
        }
        zzgi();
        return false;
    }

    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    public final /* bridge */ /* synthetic */ void zzfs() {
        super.zzfs();
    }

    public final /* bridge */ /* synthetic */ void zzft() {
        super.zzft();
    }

    public final /* bridge */ /* synthetic */ zzdu zzfu() {
        return super.zzfu();
    }

    public final /* bridge */ /* synthetic */ zzhl zzfv() {
        return super.zzfv();
    }

    public final /* bridge */ /* synthetic */ zzfc zzfw() {
        return super.zzfw();
    }

    public final /* bridge */ /* synthetic */ zzeq zzfx() {
        return super.zzfx();
    }

    public final /* bridge */ /* synthetic */ zzij zzfy() {
        return super.zzfy();
    }

    public final /* bridge */ /* synthetic */ zzig zzfz() {
        return super.zzfz();
    }

    public final /* bridge */ /* synthetic */ zzfd zzga() {
        return super.zzga();
    }

    public final /* bridge */ /* synthetic */ zzff zzgb() {
        return super.zzgb();
    }

    public final /* bridge */ /* synthetic */ zzkc zzgc() {
        return super.zzgc();
    }

    public final /* bridge */ /* synthetic */ zzji zzgd() {
        return super.zzgd();
    }

    public final /* bridge */ /* synthetic */ zzgh zzge() {
        return super.zzge();
    }

    public final /* bridge */ /* synthetic */ zzfh zzgf() {
        return super.zzgf();
    }

    public final /* bridge */ /* synthetic */ zzfs zzgg() {
        return super.zzgg();
    }

    public final /* bridge */ /* synthetic */ zzeg zzgh() {
        return super.zzgh();
    }

    public final /* bridge */ /* synthetic */ zzec zzgi() {
        return super.zzgi();
    }

    public final Object zzh(String str, Object obj) {
        int i = 256;
        if ("_ev".equals(str)) {
            return zza(256, obj, true);
        }
        if (!zzch(str)) {
            i = 100;
        }
        return zza(i, obj, false);
    }

    protected final boolean zzhh() {
        return true;
    }

    public final int zzi(String str, Object obj) {
        return "_ldl".equals(str) ? zza("user property referrer", str, zzcg(str), obj, false) : zza("user property", str, zzcg(str), obj, false) ? 0 : 7;
    }

    protected final void zzin() {
        zzab();
        SecureRandom secureRandom = new SecureRandom();
        long nextLong = secureRandom.nextLong();
        if (nextLong == 0) {
            nextLong = secureRandom.nextLong();
            if (nextLong == 0) {
                zzgf().zziv().log("Utils falling back to Random for random id");
            }
        }
        this.zzarr.set(nextLong);
    }

    public final Object zzj(String str, Object obj) {
        return "_ldl".equals(str) ? zza(zzcg(str), obj, true) : zza(zzcg(str), obj, false);
    }

    public final long zzlk() {
        long nextLong;
        if (this.zzarr.get() == 0) {
            synchronized (this.zzarr) {
                nextLong = new Random(System.nanoTime() ^ zzbt().currentTimeMillis()).nextLong();
                int i = this.zzars + 1;
                this.zzars = i;
                nextLong += (long) i;
            }
        } else {
            synchronized (this.zzarr) {
                this.zzarr.compareAndSet(-1, 1);
                nextLong = this.zzarr.getAndIncrement();
            }
        }
        return nextLong;
    }

    final SecureRandom zzll() {
        zzab();
        if (this.zzarq == null) {
            this.zzarq = new SecureRandom();
        }
        return this.zzarq;
    }

    public final int zzlm() {
        if (this.zzart == null) {
            this.zzart = Integer.valueOf(GoogleApiAvailabilityLight.getInstance().getApkVersion(getContext()) / 1000);
        }
        return this.zzart.intValue();
    }

    final boolean zzq(String str, String str2) {
        if (str2 == null) {
            zzgf().zzis().zzg("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.length() == 0) {
            zzgf().zzis().zzg("Name is required and can't be empty. Type", str);
            return false;
        } else {
            int codePointAt = str2.codePointAt(0);
            if (Character.isLetter(codePointAt)) {
                int length = str2.length();
                codePointAt = Character.charCount(codePointAt);
                while (codePointAt < length) {
                    int codePointAt2 = str2.codePointAt(codePointAt);
                    if (codePointAt2 == 95 || Character.isLetterOrDigit(codePointAt2)) {
                        codePointAt += Character.charCount(codePointAt2);
                    } else {
                        zzgf().zzis().zze("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                        return false;
                    }
                }
                return true;
            }
            zzgf().zzis().zze("Name must start with a letter. Type, name", str, str2);
            return false;
        }
    }

    public final boolean zzw(String str) {
        zzab();
        if (Wrappers.packageManager(getContext()).checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        zzgf().zziy().zzg("Permission not granted", str);
        return false;
    }
}
