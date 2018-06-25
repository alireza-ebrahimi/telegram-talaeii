package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.zzf;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Hide
public final class zzcme extends zzcli {
    private final zzcms zzjrb;
    private zzcjb zzjrc;
    private volatile Boolean zzjrd;
    private final zzcip zzjre;
    private final zzcni zzjrf;
    private final List<Runnable> zzjrg = new ArrayList();
    private final zzcip zzjrh;

    protected zzcme(zzckj zzckj) {
        super(zzckj);
        this.zzjrf = new zzcni(zzckj.zzxx());
        this.zzjrb = new zzcms(this);
        this.zzjre = new zzcmf(this, zzckj);
        this.zzjrh = new zzcmk(this, zzckj);
    }

    @WorkerThread
    private final void onServiceDisconnected(ComponentName componentName) {
        zzwj();
        if (this.zzjrc != null) {
            this.zzjrc = null;
            zzayp().zzbba().zzj("Disconnected from device MeasurementService", componentName);
            zzwj();
            zzzh();
        }
    }

    @WorkerThread
    private final void zzbcl() {
        zzwj();
        zzayp().zzbba().zzj("Processing queued up service tasks", Integer.valueOf(this.zzjrg.size()));
        for (Runnable run : this.zzjrg) {
            try {
                run.run();
            } catch (Throwable th) {
                zzayp().zzbau().zzj("Task exception while flushing queue", th);
            }
        }
        this.zzjrg.clear();
        this.zzjrh.cancel();
    }

    @Nullable
    @WorkerThread
    private final zzcif zzbw(boolean z) {
        return zzaye().zzjo(z ? zzayp().zzbbc() : null);
    }

    @WorkerThread
    private final void zzk(Runnable runnable) throws IllegalStateException {
        zzwj();
        if (isConnected()) {
            runnable.run();
        } else if (((long) this.zzjrg.size()) >= 1000) {
            zzayp().zzbau().log("Discarding data. Max runnable queue size reached");
        } else {
            this.zzjrg.add(runnable);
            this.zzjrh.zzs(60000);
            zzzh();
        }
    }

    @WorkerThread
    private final void zzyw() {
        zzwj();
        this.zzjrf.start();
        this.zzjre.zzs(((Long) zzciz.zzjjt.get()).longValue());
    }

    @WorkerThread
    private final void zzyx() {
        zzwj();
        if (isConnected()) {
            zzayp().zzbba().log("Inactivity, disconnecting from the service");
            disconnect();
        }
    }

    @WorkerThread
    public final void disconnect() {
        zzwj();
        zzyk();
        try {
            zza.zzanm();
            getContext().unbindService(this.zzjrb);
        } catch (IllegalStateException e) {
        } catch (IllegalArgumentException e2) {
        }
        this.zzjrc = null;
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @WorkerThread
    public final boolean isConnected() {
        zzwj();
        zzyk();
        return this.zzjrc != null;
    }

    @WorkerThread
    protected final void resetAnalyticsData() {
        zzwj();
        zzyk();
        zzcif zzbw = zzbw(false);
        zzayi().resetAnalyticsData();
        zzk(new zzcmg(this, zzbw));
    }

    @WorkerThread
    protected final void zza(zzcjb zzcjb) {
        zzwj();
        zzbq.checkNotNull(zzcjb);
        this.zzjrc = zzcjb;
        zzyw();
        zzbcl();
    }

    @WorkerThread
    final void zza(zzcjb zzcjb, zzbgl zzbgl, zzcif zzcif) {
        zzwj();
        zzyk();
        int i = 100;
        for (int i2 = 0; i2 < 1001 && r4 == 100; i2++) {
            List arrayList = new ArrayList();
            Object zzep = zzayi().zzep(100);
            if (zzep != null) {
                arrayList.addAll(zzep);
                i = zzep.size();
            } else {
                i = 0;
            }
            if (zzbgl != null && r4 < 100) {
                arrayList.add(zzbgl);
            }
            ArrayList arrayList2 = (ArrayList) arrayList;
            int size = arrayList2.size();
            int i3 = 0;
            while (i3 < size) {
                zzep = arrayList2.get(i3);
                i3++;
                zzbgl zzbgl2 = (zzbgl) zzep;
                if (zzbgl2 instanceof zzcix) {
                    try {
                        zzcjb.zza((zzcix) zzbgl2, zzcif);
                    } catch (RemoteException e) {
                        zzayp().zzbau().zzj("Failed to send event to the service", e);
                    }
                } else if (zzbgl2 instanceof zzcnl) {
                    try {
                        zzcjb.zza((zzcnl) zzbgl2, zzcif);
                    } catch (RemoteException e2) {
                        zzayp().zzbau().zzj("Failed to send attribute to the service", e2);
                    }
                } else if (zzbgl2 instanceof zzcii) {
                    try {
                        zzcjb.zza((zzcii) zzbgl2, zzcif);
                    } catch (RemoteException e22) {
                        zzayp().zzbau().zzj("Failed to send conditional property to the service", e22);
                    }
                } else {
                    zzayp().zzbau().log("Discarding data. Unrecognized parcel type.");
                }
            }
        }
    }

    @WorkerThread
    protected final void zza(zzclz zzclz) {
        zzwj();
        zzyk();
        zzk(new zzcmj(this, zzclz));
    }

    @WorkerThread
    public final void zza(AtomicReference<String> atomicReference) {
        zzwj();
        zzyk();
        zzk(new zzcmh(this, atomicReference, zzbw(false)));
    }

    @WorkerThread
    protected final void zza(AtomicReference<List<zzcii>> atomicReference, String str, String str2, String str3) {
        zzwj();
        zzyk();
        zzk(new zzcmo(this, atomicReference, str, str2, str3, zzbw(false)));
    }

    @WorkerThread
    protected final void zza(AtomicReference<List<zzcnl>> atomicReference, String str, String str2, String str3, boolean z) {
        zzwj();
        zzyk();
        zzk(new zzcmp(this, atomicReference, str, str2, str3, z, zzbw(false)));
    }

    @WorkerThread
    protected final void zza(AtomicReference<List<zzcnl>> atomicReference, boolean z) {
        zzwj();
        zzyk();
        zzk(new zzcmr(this, atomicReference, zzbw(false), z));
    }

    public final /* bridge */ /* synthetic */ void zzaxz() {
        super.zzaxz();
    }

    public final /* bridge */ /* synthetic */ void zzaya() {
        super.zzaya();
    }

    public final /* bridge */ /* synthetic */ zzcia zzayb() {
        return super.zzayb();
    }

    public final /* bridge */ /* synthetic */ zzcih zzayc() {
        return super.zzayc();
    }

    public final /* bridge */ /* synthetic */ zzclk zzayd() {
        return super.zzayd();
    }

    public final /* bridge */ /* synthetic */ zzcje zzaye() {
        return super.zzaye();
    }

    public final /* bridge */ /* synthetic */ zzcir zzayf() {
        return super.zzayf();
    }

    public final /* bridge */ /* synthetic */ zzcme zzayg() {
        return super.zzayg();
    }

    public final /* bridge */ /* synthetic */ zzcma zzayh() {
        return super.zzayh();
    }

    public final /* bridge */ /* synthetic */ zzcjf zzayi() {
        return super.zzayi();
    }

    public final /* bridge */ /* synthetic */ zzcil zzayj() {
        return super.zzayj();
    }

    public final /* bridge */ /* synthetic */ zzcjh zzayk() {
        return super.zzayk();
    }

    public final /* bridge */ /* synthetic */ zzcno zzayl() {
        return super.zzayl();
    }

    public final /* bridge */ /* synthetic */ zzckd zzaym() {
        return super.zzaym();
    }

    public final /* bridge */ /* synthetic */ zzcnd zzayn() {
        return super.zzayn();
    }

    public final /* bridge */ /* synthetic */ zzcke zzayo() {
        return super.zzayo();
    }

    public final /* bridge */ /* synthetic */ zzcjj zzayp() {
        return super.zzayp();
    }

    public final /* bridge */ /* synthetic */ zzcju zzayq() {
        return super.zzayq();
    }

    public final /* bridge */ /* synthetic */ zzcik zzayr() {
        return super.zzayr();
    }

    protected final boolean zzazq() {
        return false;
    }

    @WorkerThread
    protected final void zzb(zzcnl zzcnl) {
        zzwj();
        zzyk();
        zzk(new zzcmq(this, zzayi().zza(zzcnl), zzcnl, zzbw(true)));
    }

    @WorkerThread
    protected final void zzbci() {
        zzwj();
        zzyk();
        zzk(new zzcml(this, zzbw(true)));
    }

    @WorkerThread
    protected final void zzbcj() {
        zzwj();
        zzyk();
        zzk(new zzcmi(this, zzbw(true)));
    }

    final Boolean zzbck() {
        return this.zzjrd;
    }

    @WorkerThread
    protected final void zzc(zzcix zzcix, String str) {
        zzbq.checkNotNull(zzcix);
        zzwj();
        zzyk();
        zzk(new zzcmm(this, true, zzayi().zza(zzcix), zzcix, zzbw(true), str));
    }

    @WorkerThread
    protected final void zzf(zzcii zzcii) {
        zzbq.checkNotNull(zzcii);
        zzwj();
        zzyk();
        zzk(new zzcmn(this, true, zzayi().zzc(zzcii), new zzcii(zzcii), zzbw(true), zzcii));
    }

    public final /* bridge */ /* synthetic */ void zzwj() {
        super.zzwj();
    }

    public final /* bridge */ /* synthetic */ zze zzxx() {
        return super.zzxx();
    }

    @WorkerThread
    final void zzzh() {
        Object obj = 1;
        zzwj();
        zzyk();
        if (!isConnected()) {
            if (this.zzjrd == null) {
                boolean z;
                zzwj();
                zzyk();
                Boolean zzbbg = zzayq().zzbbg();
                if (zzbbg == null || !zzbbg.booleanValue()) {
                    Object obj2;
                    if (zzaye().zzbas() != 1) {
                        zzayp().zzbba().log("Checking service availability");
                        int isGooglePlayServicesAvailable = zzf.zzahf().isGooglePlayServicesAvailable(zzayl().getContext());
                        int i;
                        switch (isGooglePlayServicesAvailable) {
                            case 0:
                                zzayp().zzbba().log("Service available");
                                i = 1;
                                z = true;
                                break;
                            case 1:
                                zzayp().zzbba().log("Service missing");
                                i = 1;
                                z = false;
                                break;
                            case 2:
                                zzayp().zzbaz().log("Service container out of date");
                                if (12211 >= 11800) {
                                    zzbbg = zzayq().zzbbg();
                                    z = zzbbg == null || zzbbg.booleanValue();
                                    obj2 = null;
                                    break;
                                }
                                i = 1;
                                z = false;
                                break;
                            case 3:
                                zzayp().zzbaw().log("Service disabled");
                                obj2 = null;
                                z = false;
                                break;
                            case 9:
                                zzayp().zzbaw().log("Service invalid");
                                obj2 = null;
                                z = false;
                                break;
                            case 18:
                                zzayp().zzbaw().log("Service updating");
                                i = 1;
                                z = true;
                                break;
                            default:
                                zzayp().zzbaw().zzj("Unexpected service status", Integer.valueOf(isGooglePlayServicesAvailable));
                                obj2 = null;
                                z = false;
                                break;
                        }
                    }
                    obj2 = 1;
                    z = true;
                    if (obj2 != null) {
                        zzayq().zzbr(z);
                    }
                } else {
                    z = true;
                }
                this.zzjrd = Boolean.valueOf(z);
            }
            if (this.zzjrd.booleanValue()) {
                this.zzjrb.zzbcm();
                return;
            }
            List queryIntentServices = getContext().getPackageManager().queryIntentServices(new Intent().setClassName(getContext(), "com.google.android.gms.measurement.AppMeasurementService"), 65536);
            if (queryIntentServices == null || queryIntentServices.size() <= 0) {
                obj = null;
            }
            if (obj != null) {
                Intent intent = new Intent("com.google.android.gms.measurement.START");
                intent.setComponent(new ComponentName(getContext(), "com.google.android.gms.measurement.AppMeasurementService"));
                this.zzjrb.zzm(intent);
                return;
            }
            zzayp().zzbau().log("Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest");
        }
    }
}
