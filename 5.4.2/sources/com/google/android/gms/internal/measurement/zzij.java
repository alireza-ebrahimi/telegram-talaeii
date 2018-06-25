package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkedTrackBlacklistUtil;

@VisibleForTesting
public final class zzij extends zzhi {
    private final zzix zzapg;
    private zzez zzaph;
    private volatile Boolean zzapi;
    private final zzeo zzapj;
    private final zzjn zzapk;
    private final List<Runnable> zzapl = new ArrayList();
    private final zzeo zzapm;

    protected zzij(zzgm zzgm) {
        super(zzgm);
        this.zzapk = new zzjn(zzgm.zzbt());
        this.zzapg = new zzix(this);
        this.zzapj = new zzik(this, zzgm);
        this.zzapm = new zzip(this, zzgm);
    }

    private final void onServiceDisconnected(ComponentName componentName) {
        zzab();
        if (this.zzaph != null) {
            this.zzaph = null;
            zzgf().zziz().zzg("Disconnected from device MeasurementService", componentName);
            zzab();
            zzdf();
        }
    }

    private final void zzcu() {
        zzab();
        this.zzapk.start();
        this.zzapj.zzh(((Long) zzey.zzahv.get()).longValue());
    }

    private final void zzcv() {
        zzab();
        if (isConnected()) {
            zzgf().zziz().log("Inactivity, disconnecting from the service");
            disconnect();
        }
    }

    private final void zzf(Runnable runnable) {
        zzab();
        if (isConnected()) {
            runnable.run();
        } else if (((long) this.zzapl.size()) >= 1000) {
            zzgf().zzis().log("Discarding data. Max runnable queue size reached");
        } else {
            this.zzapl.add(runnable);
            this.zzapm.zzh(ChunkedTrackBlacklistUtil.DEFAULT_TRACK_BLACKLIST_MS);
            zzdf();
        }
    }

    private final zzdz zzk(boolean z) {
        zzgi();
        return zzfw().zzbh(z ? zzgf().zzjb() : null);
    }

    private final boolean zzkn() {
        zzgi();
        return true;
    }

    private final void zzkp() {
        zzab();
        zzgf().zziz().zzg("Processing queued up service tasks", Integer.valueOf(this.zzapl.size()));
        for (Runnable run : this.zzapl) {
            try {
                run.run();
            } catch (Exception e) {
                zzgf().zzis().zzg("Task exception while flushing queue", e);
            }
        }
        this.zzapl.clear();
        this.zzapm.cancel();
    }

    public final void disconnect() {
        zzab();
        zzch();
        try {
            ConnectionTracker.getInstance().unbindService(getContext(), this.zzapg);
        } catch (IllegalStateException e) {
        } catch (IllegalArgumentException e2) {
        }
        this.zzaph = null;
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final boolean isConnected() {
        zzab();
        zzch();
        return this.zzaph != null;
    }

    protected final void resetAnalyticsData() {
        zzab();
        zzfs();
        zzch();
        zzdz zzk = zzk(false);
        if (zzkn()) {
            zzga().resetAnalyticsData();
        }
        zzf(new zzil(this, zzk));
    }

    @VisibleForTesting
    protected final void zza(zzez zzez) {
        zzab();
        Preconditions.checkNotNull(zzez);
        this.zzaph = zzez;
        zzcu();
        zzkp();
    }

    @VisibleForTesting
    final void zza(zzez zzez, AbstractSafeParcelable abstractSafeParcelable, zzdz zzdz) {
        zzab();
        zzfs();
        zzch();
        boolean zzkn = zzkn();
        int i = 100;
        for (int i2 = 0; i2 < 1001 && r4 == 100; i2++) {
            Object zzp;
            ArrayList arrayList;
            int size;
            int i3;
            AbstractSafeParcelable abstractSafeParcelable2;
            List arrayList2 = new ArrayList();
            if (zzkn) {
                zzp = zzga().zzp(100);
                if (zzp != null) {
                    arrayList2.addAll(zzp);
                    i = zzp.size();
                    if (abstractSafeParcelable != null && r4 < 100) {
                        arrayList2.add(abstractSafeParcelable);
                    }
                    arrayList = (ArrayList) arrayList2;
                    size = arrayList.size();
                    i3 = 0;
                    while (i3 < size) {
                        zzp = arrayList.get(i3);
                        i3++;
                        abstractSafeParcelable2 = (AbstractSafeParcelable) zzp;
                        if (abstractSafeParcelable2 instanceof zzew) {
                            try {
                                zzez.zza((zzew) abstractSafeParcelable2, zzdz);
                            } catch (RemoteException e) {
                                zzgf().zzis().zzg("Failed to send event to the service", e);
                            }
                        } else if (abstractSafeParcelable2 instanceof zzjz) {
                            try {
                                zzez.zza((zzjz) abstractSafeParcelable2, zzdz);
                            } catch (RemoteException e2) {
                                zzgf().zzis().zzg("Failed to send attribute to the service", e2);
                            }
                        } else if (abstractSafeParcelable2 instanceof zzee) {
                            zzgf().zzis().log("Discarding data. Unrecognized parcel type.");
                        } else {
                            try {
                                zzez.zza((zzee) abstractSafeParcelable2, zzdz);
                            } catch (RemoteException e22) {
                                zzgf().zzis().zzg("Failed to send conditional property to the service", e22);
                            }
                        }
                    }
                }
            }
            i = 0;
            arrayList2.add(abstractSafeParcelable);
            arrayList = (ArrayList) arrayList2;
            size = arrayList.size();
            i3 = 0;
            while (i3 < size) {
                zzp = arrayList.get(i3);
                i3++;
                abstractSafeParcelable2 = (AbstractSafeParcelable) zzp;
                if (abstractSafeParcelable2 instanceof zzew) {
                    zzez.zza((zzew) abstractSafeParcelable2, zzdz);
                } else if (abstractSafeParcelable2 instanceof zzjz) {
                    zzez.zza((zzjz) abstractSafeParcelable2, zzdz);
                } else if (abstractSafeParcelable2 instanceof zzee) {
                    zzgf().zzis().log("Discarding data. Unrecognized parcel type.");
                } else {
                    zzez.zza((zzee) abstractSafeParcelable2, zzdz);
                }
            }
        }
    }

    public final void zza(AtomicReference<String> atomicReference) {
        zzab();
        zzch();
        zzf(new zzim(this, atomicReference, zzk(false)));
    }

    protected final void zza(AtomicReference<List<zzee>> atomicReference, String str, String str2, String str3) {
        zzab();
        zzch();
        zzf(new zzit(this, atomicReference, str, str2, str3, zzk(false)));
    }

    protected final void zza(AtomicReference<List<zzjz>> atomicReference, String str, String str2, String str3, boolean z) {
        zzab();
        zzch();
        zzf(new zziu(this, atomicReference, str, str2, str3, z, zzk(false)));
    }

    protected final void zza(AtomicReference<List<zzjz>> atomicReference, boolean z) {
        zzab();
        zzch();
        zzf(new zziw(this, atomicReference, zzk(false), z));
    }

    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    protected final void zzb(zzew zzew, String str) {
        Preconditions.checkNotNull(zzew);
        zzab();
        zzch();
        boolean zzkn = zzkn();
        boolean z = zzkn && zzga().zza(zzew);
        zzf(new zzir(this, zzkn, z, zzew, zzk(true), str));
    }

    protected final void zzb(zzif zzif) {
        zzab();
        zzch();
        zzf(new zzio(this, zzif));
    }

    protected final void zzb(zzjz zzjz) {
        zzab();
        zzch();
        boolean z = zzkn() && zzga().zza(zzjz);
        zzf(new zziv(this, z, zzjz, zzk(true)));
    }

    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    protected final void zzd(zzee zzee) {
        Preconditions.checkNotNull(zzee);
        zzab();
        zzch();
        zzgi();
        zzf(new zzis(this, true, zzga().zzc(zzee), new zzee(zzee), zzk(true), zzee));
    }

    final void zzdf() {
        Object obj = 1;
        zzab();
        zzch();
        if (!isConnected()) {
            if (this.zzapi == null) {
                boolean z;
                zzab();
                zzch();
                Boolean zzji = zzgg().zzji();
                if (zzji == null || !zzji.booleanValue()) {
                    Object obj2;
                    zzgi();
                    if (zzfw().zziq() != 1) {
                        zzgf().zziz().log("Checking service availability");
                        int isGooglePlayServicesAvailable = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(zzgc().getContext(), 12451000);
                        int i;
                        switch (isGooglePlayServicesAvailable) {
                            case 0:
                                zzgf().zziz().log("Service available");
                                i = 1;
                                z = true;
                                break;
                            case 1:
                                zzgf().zziz().log("Service missing");
                                i = 1;
                                z = false;
                                break;
                            case 2:
                                zzgf().zziy().log("Service container out of date");
                                if (zzgc().zzlm() >= 12600) {
                                    zzji = zzgg().zzji();
                                    z = zzji == null || zzji.booleanValue();
                                    obj2 = null;
                                    break;
                                }
                                i = 1;
                                z = false;
                                break;
                                break;
                            case 3:
                                zzgf().zziv().log("Service disabled");
                                obj2 = null;
                                z = false;
                                break;
                            case 9:
                                zzgf().zziv().log("Service invalid");
                                obj2 = null;
                                z = false;
                                break;
                            case 18:
                                zzgf().zziv().log("Service updating");
                                i = 1;
                                z = true;
                                break;
                            default:
                                zzgf().zziv().zzg("Unexpected service status", Integer.valueOf(isGooglePlayServicesAvailable));
                                obj2 = null;
                                z = false;
                                break;
                        }
                    }
                    obj2 = 1;
                    z = true;
                    if (obj2 != null) {
                        zzgg().zzf(z);
                    }
                } else {
                    z = true;
                }
                this.zzapi = Boolean.valueOf(z);
            }
            if (this.zzapi.booleanValue()) {
                this.zzapg.zzkq();
                return;
            }
            zzgi();
            List queryIntentServices = getContext().getPackageManager().queryIntentServices(new Intent().setClassName(getContext(), "com.google.android.gms.measurement.AppMeasurementService"), C3446C.DEFAULT_BUFFER_SEGMENT_SIZE);
            if (queryIntentServices == null || queryIntentServices.size() <= 0) {
                obj = null;
            }
            if (obj != null) {
                Intent intent = new Intent("com.google.android.gms.measurement.START");
                Context context = getContext();
                zzgi();
                intent.setComponent(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementService"));
                this.zzapg.zzc(intent);
                return;
            }
            zzgf().zzis().log("Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest");
        }
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

    protected final boolean zzhh() {
        return false;
    }

    protected final void zzkj() {
        zzab();
        zzch();
        zzf(new zzin(this, zzk(true)));
    }

    protected final void zzkm() {
        zzab();
        zzch();
        zzf(new zziq(this, zzk(true)));
    }

    final Boolean zzko() {
        return this.zzapi;
    }
}
