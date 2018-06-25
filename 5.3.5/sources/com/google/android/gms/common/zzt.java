package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbih;

@Hide
public class zzt {
    private static zzt zzfrx;
    private final Context mContext;

    private zzt(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Hide
    private static zzh zza(PackageInfo packageInfo, zzh... zzhArr) {
        int i = 0;
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        zzi zzi = new zzi(packageInfo.signatures[0].toByteArray());
        while (i < zzhArr.length) {
            if (zzhArr[i].equals(zzi)) {
                return zzhArr[i];
            }
            i++;
        }
        return null;
    }

    @Hide
    public static boolean zza(PackageInfo packageInfo, boolean z) {
        if (!(packageInfo == null || packageInfo.signatures == null)) {
            zzh zza;
            if (z) {
                zza = zza(packageInfo, zzk.zzfrh);
            } else {
                zza = zza(packageInfo, zzk.zzfrh[0]);
            }
            if (zza != null) {
                return true;
            }
        }
        return false;
    }

    public static zzt zzcj(Context context) {
        zzbq.checkNotNull(context);
        synchronized (zzt.class) {
            if (zzfrx == null) {
                zzg.zzch(context);
                zzfrx = new zzt(context);
            }
        }
        return zzfrx;
    }

    private final zzp zzgh(String str) {
        try {
            PackageInfo packageInfo = zzbih.zzdd(this.mContext).getPackageInfo(str, 64);
            boolean zzci = zzs.zzci(this.mContext);
            if (packageInfo == null) {
                return zzp.zzgg("null pkg");
            }
            if (packageInfo.signatures.length != 1) {
                return zzp.zzgg("single cert required");
            }
            zzh zzi = new zzi(packageInfo.signatures[0].toByteArray());
            String str2 = packageInfo.packageName;
            zzp zza = zzg.zza(str2, zzi, zzci);
            return (!zza.zzfrm || packageInfo.applicationInfo == null || (packageInfo.applicationInfo.flags & 2) == 0) ? zza : (!zzci || zzg.zza(str2, zzi, false).zzfrm) ? zzp.zzgg("debuggable release cert app rejected") : zza;
        } catch (NameNotFoundException e) {
            String str3 = "no pkg ";
            String valueOf = String.valueOf(str);
            return zzp.zzgg(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        }
    }

    @Hide
    public final boolean zza(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zza(packageInfo, false)) {
            return true;
        }
        if (!zza(packageInfo, true)) {
            return false;
        }
        if (zzs.zzci(this.mContext)) {
            return true;
        }
        Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        return false;
    }

    @Hide
    public final boolean zzbp(int i) {
        zzp zzp;
        String[] packagesForUid = zzbih.zzdd(this.mContext).getPackagesForUid(i);
        if (packagesForUid != null && packagesForUid.length != 0) {
            zzp = null;
            for (String zzgh : packagesForUid) {
                zzp = zzgh(zzgh);
                if (zzp.zzfrm) {
                    break;
                }
            }
        } else {
            zzp = zzp.zzgg("no pkgs");
        }
        if (!zzp.zzfrm) {
            if (zzp.cause != null) {
                Log.d("GoogleCertificatesRslt", zzp.getErrorMessage(), zzp.cause);
            } else {
                Log.d("GoogleCertificatesRslt", zzp.getErrorMessage());
            }
        }
        return zzp.zzfrm;
    }
}
