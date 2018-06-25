package com.google.android.gms.internal.measurement;

import android.os.Binder;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.telegram.messenger.exoplayer2.C3446C;

public final class zzgo extends zzfa {
    private final zzjs zzajy;
    private Boolean zzanm;
    private String zzann;

    public zzgo(zzjs zzjs) {
        this(zzjs, null);
    }

    private zzgo(zzjs zzjs, String str) {
        Preconditions.checkNotNull(zzjs);
        this.zzajy = zzjs;
        this.zzann = null;
    }

    private final void zzb(zzdz zzdz, boolean z) {
        Preconditions.checkNotNull(zzdz);
        zzc(zzdz.packageName, false);
        this.zzajy.zzgc().zzcf(zzdz.zzadm);
    }

    private final void zzc(String str, boolean z) {
        boolean z2 = false;
        if (TextUtils.isEmpty(str)) {
            this.zzajy.zzgf().zzis().log("Measurement Service called without app package");
            throw new SecurityException("Measurement Service called without app package");
        }
        if (z) {
            try {
                if (this.zzanm == null) {
                    if ("com.google.android.gms".equals(this.zzann) || UidVerifier.isGooglePlayServicesUid(this.zzajy.getContext(), Binder.getCallingUid()) || GoogleSignatureVerifier.getInstance(this.zzajy.getContext()).isUidGoogleSigned(Binder.getCallingUid())) {
                        z2 = true;
                    }
                    this.zzanm = Boolean.valueOf(z2);
                }
                if (this.zzanm.booleanValue()) {
                    return;
                }
            } catch (SecurityException e) {
                this.zzajy.zzgf().zzis().zzg("Measurement Service called with invalid calling package. appId", zzfh.zzbl(str));
                throw e;
            }
        }
        if (this.zzann == null && GooglePlayServicesUtilLight.uidHasPackageName(this.zzajy.getContext(), Binder.getCallingUid(), str)) {
            this.zzann = str;
        }
        if (!str.equals(this.zzann)) {
            throw new SecurityException(String.format("Unknown calling package name '%s'.", new Object[]{str}));
        }
    }

    @VisibleForTesting
    private final void zze(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        if (((Boolean) zzey.zzaih.get()).booleanValue() && this.zzajy.zzge().zzjr()) {
            runnable.run();
        } else {
            this.zzajy.zzge().zzc(runnable);
        }
    }

    public final List<zzjz> zza(zzdz zzdz, boolean z) {
        Object e;
        zzb(zzdz, false);
        try {
            List<zzkb> list = (List) this.zzajy.zzge().zzb(new zzhe(this, zzdz)).get();
            List<zzjz> arrayList = new ArrayList(list.size());
            for (zzkb zzkb : list) {
                if (z || !zzkc.zzch(zzkb.name)) {
                    arrayList.add(new zzjz(zzkb));
                }
            }
            return arrayList;
        } catch (InterruptedException e2) {
            e = e2;
            this.zzajy.zzgf().zzis().zze("Failed to get user attributes. appId", zzfh.zzbl(zzdz.packageName), e);
            return null;
        } catch (ExecutionException e3) {
            e = e3;
            this.zzajy.zzgf().zzis().zze("Failed to get user attributes. appId", zzfh.zzbl(zzdz.packageName), e);
            return null;
        }
    }

    public final List<zzee> zza(String str, String str2, zzdz zzdz) {
        Object e;
        zzb(zzdz, false);
        try {
            return (List) this.zzajy.zzge().zzb(new zzgw(this, zzdz, str, str2)).get();
        } catch (InterruptedException e2) {
            e = e2;
        } catch (ExecutionException e3) {
            e = e3;
        }
        this.zzajy.zzgf().zzis().zzg("Failed to get conditional user properties", e);
        return Collections.emptyList();
    }

    public final List<zzjz> zza(String str, String str2, String str3, boolean z) {
        Object e;
        zzc(str, true);
        try {
            List<zzkb> list = (List) this.zzajy.zzge().zzb(new zzgv(this, str, str2, str3)).get();
            List<zzjz> arrayList = new ArrayList(list.size());
            for (zzkb zzkb : list) {
                if (z || !zzkc.zzch(zzkb.name)) {
                    arrayList.add(new zzjz(zzkb));
                }
            }
            return arrayList;
        } catch (InterruptedException e2) {
            e = e2;
            this.zzajy.zzgf().zzis().zze("Failed to get user attributes. appId", zzfh.zzbl(str), e);
            return Collections.emptyList();
        } catch (ExecutionException e3) {
            e = e3;
            this.zzajy.zzgf().zzis().zze("Failed to get user attributes. appId", zzfh.zzbl(str), e);
            return Collections.emptyList();
        }
    }

    public final List<zzjz> zza(String str, String str2, boolean z, zzdz zzdz) {
        Object e;
        zzb(zzdz, false);
        try {
            List<zzkb> list = (List) this.zzajy.zzge().zzb(new zzgu(this, zzdz, str, str2)).get();
            List<zzjz> arrayList = new ArrayList(list.size());
            for (zzkb zzkb : list) {
                if (z || !zzkc.zzch(zzkb.name)) {
                    arrayList.add(new zzjz(zzkb));
                }
            }
            return arrayList;
        } catch (InterruptedException e2) {
            e = e2;
            this.zzajy.zzgf().zzis().zze("Failed to get user attributes. appId", zzfh.zzbl(zzdz.packageName), e);
            return Collections.emptyList();
        } catch (ExecutionException e3) {
            e = e3;
            this.zzajy.zzgf().zzis().zze("Failed to get user attributes. appId", zzfh.zzbl(zzdz.packageName), e);
            return Collections.emptyList();
        }
    }

    public final void zza(long j, String str, String str2, String str3) {
        zze(new zzhg(this, str2, str3, str, j));
    }

    public final void zza(zzdz zzdz) {
        zzb(zzdz, false);
        zze(new zzhf(this, zzdz));
    }

    public final void zza(zzee zzee, zzdz zzdz) {
        Preconditions.checkNotNull(zzee);
        Preconditions.checkNotNull(zzee.zzaeq);
        zzb(zzdz, false);
        zzee zzee2 = new zzee(zzee);
        zzee2.packageName = zzdz.packageName;
        if (zzee.zzaeq.getValue() == null) {
            zze(new zzgq(this, zzee2, zzdz));
        } else {
            zze(new zzgr(this, zzee2, zzdz));
        }
    }

    public final void zza(zzew zzew, zzdz zzdz) {
        Preconditions.checkNotNull(zzew);
        zzb(zzdz, false);
        zze(new zzgz(this, zzew, zzdz));
    }

    public final void zza(zzew zzew, String str, String str2) {
        Preconditions.checkNotNull(zzew);
        Preconditions.checkNotEmpty(str);
        zzc(str, true);
        zze(new zzha(this, zzew, str));
    }

    public final void zza(zzjz zzjz, zzdz zzdz) {
        Preconditions.checkNotNull(zzjz);
        zzb(zzdz, false);
        if (zzjz.getValue() == null) {
            zze(new zzhc(this, zzjz, zzdz));
        } else {
            zze(new zzhd(this, zzjz, zzdz));
        }
    }

    public final byte[] zza(zzew zzew, String str) {
        Object e;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzew);
        zzc(str, true);
        this.zzajy.zzgf().zziy().zzg("Log and bundle. event", this.zzajy.zzgb().zzbi(zzew.name));
        long nanoTime = this.zzajy.zzbt().nanoTime() / C3446C.MICROS_PER_SECOND;
        try {
            byte[] bArr = (byte[]) this.zzajy.zzge().zzc(new zzhb(this, zzew, str)).get();
            if (bArr == null) {
                this.zzajy.zzgf().zzis().zzg("Log and bundle returned null. appId", zzfh.zzbl(str));
                bArr = new byte[0];
            }
            this.zzajy.zzgf().zziy().zzd("Log and bundle processed. event, size, time_ms", this.zzajy.zzgb().zzbi(zzew.name), Integer.valueOf(bArr.length), Long.valueOf((this.zzajy.zzbt().nanoTime() / C3446C.MICROS_PER_SECOND) - nanoTime));
            return bArr;
        } catch (InterruptedException e2) {
            e = e2;
            this.zzajy.zzgf().zzis().zzd("Failed to log and bundle. appId, event, error", zzfh.zzbl(str), this.zzajy.zzgb().zzbi(zzew.name), e);
            return null;
        } catch (ExecutionException e3) {
            e = e3;
            this.zzajy.zzgf().zzis().zzd("Failed to log and bundle. appId, event, error", zzfh.zzbl(str), this.zzajy.zzgb().zzbi(zzew.name), e);
            return null;
        }
    }

    public final void zzb(zzdz zzdz) {
        zzb(zzdz, false);
        zze(new zzgp(this, zzdz));
    }

    public final void zzb(zzee zzee) {
        Preconditions.checkNotNull(zzee);
        Preconditions.checkNotNull(zzee.zzaeq);
        zzc(zzee.packageName, true);
        zzee zzee2 = new zzee(zzee);
        if (zzee.zzaeq.getValue() == null) {
            zze(new zzgs(this, zzee2));
        } else {
            zze(new zzgt(this, zzee2));
        }
    }

    public final String zzc(zzdz zzdz) {
        zzb(zzdz, false);
        return this.zzajy.zzh(zzdz);
    }

    public final void zzd(zzdz zzdz) {
        zzc(zzdz.packageName, false);
        zze(new zzgy(this, zzdz));
    }

    public final List<zzee> zze(String str, String str2, String str3) {
        Object e;
        zzc(str, true);
        try {
            return (List) this.zzajy.zzge().zzb(new zzgx(this, str, str2, str3)).get();
        } catch (InterruptedException e2) {
            e = e2;
        } catch (ExecutionException e3) {
            e = e3;
        }
        this.zzajy.zzgf().zzis().zzg("Failed to get conditional user properties", e);
        return Collections.emptyList();
    }
}
