package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement.ConditionalUserProperty;
import com.google.android.gms.measurement.AppMeasurement.Event;
import com.google.android.gms.measurement.AppMeasurement.EventInterceptor;
import com.google.android.gms.measurement.AppMeasurement.OnEventListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;

public final class zzclk extends zzcli {
    protected zzcly zzjpt;
    private EventInterceptor zzjpu;
    private final Set<OnEventListener> zzjpv = new CopyOnWriteArraySet();
    private boolean zzjpw;
    private final AtomicReference<String> zzjpx = new AtomicReference();

    protected zzclk(zzckj zzckj) {
        super(zzckj);
    }

    private final void zza(ConditionalUserProperty conditionalUserProperty) {
        long currentTimeMillis = zzxx().currentTimeMillis();
        zzbq.checkNotNull(conditionalUserProperty);
        zzbq.zzgv(conditionalUserProperty.mName);
        zzbq.zzgv(conditionalUserProperty.mOrigin);
        zzbq.checkNotNull(conditionalUserProperty.mValue);
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        String str = conditionalUserProperty.mName;
        Object obj = conditionalUserProperty.mValue;
        if (zzayl().zzkk(str) != 0) {
            zzayp().zzbau().zzj("Invalid conditional user property name", zzayk().zzjr(str));
        } else if (zzayl().zzl(str, obj) != 0) {
            zzayp().zzbau().zze("Invalid conditional user property value", zzayk().zzjr(str), obj);
        } else {
            Object zzm = zzayl().zzm(str, obj);
            if (zzm == null) {
                zzayp().zzbau().zze("Unable to normalize conditional user property value", zzayk().zzjr(str), obj);
                return;
            }
            conditionalUserProperty.mValue = zzm;
            long j = conditionalUserProperty.mTriggerTimeout;
            if (TextUtils.isEmpty(conditionalUserProperty.mTriggerEventName) || (j <= 15552000000L && j >= 1)) {
                j = conditionalUserProperty.mTimeToLive;
                if (j > 15552000000L || j < 1) {
                    zzayp().zzbau().zze("Invalid conditional user property time to live", zzayk().zzjr(str), Long.valueOf(j));
                    return;
                } else {
                    zzayo().zzh(new zzclm(this, conditionalUserProperty));
                    return;
                }
            }
            zzayp().zzbau().zze("Invalid conditional user property timeout", zzayk().zzjr(str), Long.valueOf(j));
        }
    }

    private final void zza(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        Bundle bundle2;
        if (bundle == null) {
            bundle2 = new Bundle();
        } else {
            bundle2 = new Bundle(bundle);
            for (String str4 : bundle2.keySet()) {
                Object obj = bundle2.get(str4);
                if (obj instanceof Bundle) {
                    bundle2.putBundle(str4, new Bundle((Bundle) obj));
                } else if (obj instanceof Parcelable[]) {
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    for (r4 = 0; r4 < parcelableArr.length; r4++) {
                        if (parcelableArr[r4] instanceof Bundle) {
                            parcelableArr[r4] = new Bundle((Bundle) parcelableArr[r4]);
                        }
                    }
                } else if (obj instanceof ArrayList) {
                    ArrayList arrayList = (ArrayList) obj;
                    for (r4 = 0; r4 < arrayList.size(); r4++) {
                        Object obj2 = arrayList.get(r4);
                        if (obj2 instanceof Bundle) {
                            arrayList.set(r4, new Bundle((Bundle) obj2));
                        }
                    }
                }
            }
        }
        zzayo().zzh(new zzcls(this, str, str2, j, bundle2, z, z2, z3, str3));
    }

    private final void zza(String str, String str2, long j, Object obj) {
        zzayo().zzh(new zzclt(this, str, str2, obj, j));
    }

    private final void zza(String str, String str2, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        zza(str, str2, zzxx().currentTimeMillis(), bundle, true, z2, z3, null);
    }

    @WorkerThread
    private final void zza(String str, String str2, Object obj, long j) {
        zzbq.zzgv(str);
        zzbq.zzgv(str2);
        zzwj();
        zzyk();
        if (!this.zzjev.isEnabled()) {
            zzayp().zzbaz().log("User property not set since app measurement is disabled");
        } else if (this.zzjev.zzbbn()) {
            zzayp().zzbaz().zze("Setting user property (FE)", zzayk().zzjp(str2), obj);
            zzayg().zzb(new zzcnl(str2, j, obj, str));
        }
    }

    private final void zza(String str, String str2, String str3, Bundle bundle) {
        long currentTimeMillis = zzxx().currentTimeMillis();
        zzbq.zzgv(str2);
        ConditionalUserProperty conditionalUserProperty = new ConditionalUserProperty();
        conditionalUserProperty.mAppId = str;
        conditionalUserProperty.mName = str2;
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        if (str3 != null) {
            conditionalUserProperty.mExpiredEventName = str3;
            conditionalUserProperty.mExpiredEventParams = bundle;
        }
        zzayo().zzh(new zzcln(this, conditionalUserProperty));
    }

    private final Map<String, Object> zzb(String str, String str2, String str3, boolean z) {
        if (zzayo().zzbbk()) {
            zzayp().zzbau().log("Cannot get user properties from analytics worker thread");
            return Collections.emptyMap();
        }
        zzayo();
        if (zzcke.zzas()) {
            zzayp().zzbau().log("Cannot get user properties from main thread");
            return Collections.emptyMap();
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zzjev.zzayo().zzh(new zzclp(this, atomicReference, str, str2, str3, z));
            try {
                atomicReference.wait(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            } catch (InterruptedException e) {
                zzayp().zzbaw().zzj("Interrupted waiting for get user properties", e);
            }
        }
        List<zzcnl> list = (List) atomicReference.get();
        if (list == null) {
            zzayp().zzbaw().log("Timed out waiting for get user properties");
            return Collections.emptyMap();
        }
        Map<String, Object> arrayMap = new ArrayMap(list.size());
        for (zzcnl zzcnl : list) {
            arrayMap.put(zzcnl.name, zzcnl.getValue());
        }
        return arrayMap;
    }

    @WorkerThread
    private final void zzb(ConditionalUserProperty conditionalUserProperty) {
        zzwj();
        zzyk();
        zzbq.checkNotNull(conditionalUserProperty);
        zzbq.zzgv(conditionalUserProperty.mName);
        zzbq.zzgv(conditionalUserProperty.mOrigin);
        zzbq.checkNotNull(conditionalUserProperty.mValue);
        if (this.zzjev.isEnabled()) {
            zzcnl zzcnl = new zzcnl(conditionalUserProperty.mName, conditionalUserProperty.mTriggeredTimestamp, conditionalUserProperty.mValue, conditionalUserProperty.mOrigin);
            try {
                zzcix zza = zzayl().zza(conditionalUserProperty.mTriggeredEventName, conditionalUserProperty.mTriggeredEventParams, conditionalUserProperty.mOrigin, 0, true, false);
                zzayg().zzf(new zzcii(conditionalUserProperty.mAppId, conditionalUserProperty.mOrigin, zzcnl, conditionalUserProperty.mCreationTimestamp, false, conditionalUserProperty.mTriggerEventName, zzayl().zza(conditionalUserProperty.mTimedOutEventName, conditionalUserProperty.mTimedOutEventParams, conditionalUserProperty.mOrigin, 0, true, false), conditionalUserProperty.mTriggerTimeout, zza, conditionalUserProperty.mTimeToLive, zzayl().zza(conditionalUserProperty.mExpiredEventName, conditionalUserProperty.mExpiredEventParams, conditionalUserProperty.mOrigin, 0, true, false)));
                return;
            } catch (IllegalArgumentException e) {
                return;
            }
        }
        zzayp().zzbaz().log("Conditional property not sent since Firebase Analytics is disabled");
    }

    @WorkerThread
    private final void zzb(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        zzbq.zzgv(str);
        zzbq.zzgv(str2);
        zzbq.checkNotNull(bundle);
        zzwj();
        zzyk();
        if (this.zzjev.isEnabled()) {
            if (!this.zzjpw) {
                this.zzjpw = true;
                try {
                    try {
                        Class.forName("com.google.android.gms.tagmanager.TagManagerService").getDeclaredMethod("initialize", new Class[]{Context.class}).invoke(null, new Object[]{getContext()});
                    } catch (Exception e) {
                        zzayp().zzbaw().zzj("Failed to invoke Tag Manager's initialize() method", e);
                    }
                } catch (ClassNotFoundException e2) {
                    zzayp().zzbay().log("Tag Manager is not found and thus will not be used");
                }
            }
            if (z3 && !"_iap".equals(str2)) {
                zzcno zzayl = this.zzjev.zzayl();
                int i = !zzayl.zzaq("event", str2) ? 2 : !zzayl.zza("event", Event.zzjew, str2) ? 13 : !zzayl.zzb("event", 40, str2) ? 2 : 0;
                if (i != 0) {
                    this.zzjev.zzayl();
                    this.zzjev.zzayl().zza(i, "_ev", zzcno.zza(str2, 40, true), str2 != null ? str2.length() : 0);
                    return;
                }
            }
            zzclz zzbcg = zzayh().zzbcg();
            if (zzbcg != null) {
                if (!bundle.containsKey("_sc")) {
                    zzbcg.zzjra = true;
                }
            }
            boolean z4 = z && z3;
            zzcma.zza(zzbcg, bundle, z4);
            boolean equals = "am".equals(str);
            z4 = zzcno.zzkp(str2);
            if (z && this.zzjpu != null && !z4 && !equals) {
                zzayp().zzbaz().zze("Passing event to registered event handler (FE)", zzayk().zzjp(str2), zzayk().zzac(bundle));
                this.zzjpu.interceptEvent(str, str2, bundle, j);
                return;
            } else if (this.zzjev.zzbbn()) {
                int zzki = zzayl().zzki(str2);
                if (zzki != 0) {
                    zzayl();
                    this.zzjev.zzayl().zza(str3, zzki, "_ev", zzcno.zza(str2, 40, true), str2 != null ? str2.length() : 0);
                    return;
                }
                Bundle zza;
                List unmodifiableList = Collections.unmodifiableList(Arrays.asList(new String[]{"_o", "_sn", "_sc", "_si"}));
                Bundle zza2 = zzayl().zza(str2, bundle, unmodifiableList, z3, true);
                zzclz zzcmd = (zza2 != null && zza2.containsKey("_sc") && zza2.containsKey("_si")) ? new zzcmd(zza2.getString("_sn"), zza2.getString("_sc"), Long.valueOf(zza2.getLong("_si")).longValue()) : null;
                zzclz zzclz = zzcmd == null ? zzbcg : zzcmd;
                List arrayList = new ArrayList();
                arrayList.add(zza2);
                long nextLong = zzayl().zzbcr().nextLong();
                int i2 = 0;
                String[] strArr = (String[]) zza2.keySet().toArray(new String[bundle.size()]);
                Arrays.sort(strArr);
                int length = strArr.length;
                int i3 = 0;
                while (i3 < length) {
                    int length2;
                    String str4 = strArr[i3];
                    Object obj = zza2.get(str4);
                    zzayl();
                    Bundle[] zzaf = zzcno.zzaf(obj);
                    if (zzaf != null) {
                        zza2.putInt(str4, zzaf.length);
                        for (int i4 = 0; i4 < zzaf.length; i4++) {
                            Bundle bundle2 = zzaf[i4];
                            zzcma.zza(zzclz, bundle2, true);
                            zza = zzayl().zza("_ep", bundle2, unmodifiableList, z3, false);
                            zza.putString("_en", str2);
                            zza.putLong("_eid", nextLong);
                            zza.putString("_gn", str4);
                            zza.putInt("_ll", zzaf.length);
                            zza.putInt("_i", i4);
                            arrayList.add(zza);
                        }
                        length2 = zzaf.length + i2;
                    } else {
                        length2 = i2;
                    }
                    i3++;
                    i2 = length2;
                }
                if (i2 != 0) {
                    zza2.putLong("_eid", nextLong);
                    zza2.putInt("_epc", i2);
                }
                int i5 = 0;
                while (i5 < arrayList.size()) {
                    zza = (Bundle) arrayList.get(i5);
                    String str5 = (i5 != 0 ? 1 : null) != null ? "_ep" : str2;
                    zza.putString("_o", str);
                    Bundle zzad = z2 ? zzayl().zzad(zza) : zza;
                    zzayp().zzbaz().zze("Logging event (FE)", zzayk().zzjp(str2), zzayk().zzac(zzad));
                    zzayg().zzc(new zzcix(str5, new zzciu(zzad), str, j), str3);
                    if (!equals) {
                        for (OnEventListener onEvent : this.zzjpv) {
                            onEvent.onEvent(str, str2, new Bundle(zzad), j);
                        }
                    }
                    i5++;
                }
                if (zzayh().zzbcg() != null && Event.APP_EXCEPTION.equals(str2)) {
                    zzayn().zzbx(true);
                    return;
                }
                return;
            } else {
                return;
            }
        }
        zzayp().zzbaz().log("Event not sent since app measurement is disabled");
    }

    @WorkerThread
    private final void zzbu(boolean z) {
        zzwj();
        zzyk();
        zzayp().zzbaz().zzj("Setting app measurement enabled (FE)", Boolean.valueOf(z));
        zzayq().setMeasurementEnabled(z);
        zzayg().zzbci();
    }

    @WorkerThread
    private final void zzc(ConditionalUserProperty conditionalUserProperty) {
        zzwj();
        zzyk();
        zzbq.checkNotNull(conditionalUserProperty);
        zzbq.zzgv(conditionalUserProperty.mName);
        if (this.zzjev.isEnabled()) {
            zzcnl zzcnl = new zzcnl(conditionalUserProperty.mName, 0, null, null);
            try {
                zzayg().zzf(new zzcii(conditionalUserProperty.mAppId, conditionalUserProperty.mOrigin, zzcnl, conditionalUserProperty.mCreationTimestamp, conditionalUserProperty.mActive, conditionalUserProperty.mTriggerEventName, null, conditionalUserProperty.mTriggerTimeout, null, conditionalUserProperty.mTimeToLive, zzayl().zza(conditionalUserProperty.mExpiredEventName, conditionalUserProperty.mExpiredEventParams, conditionalUserProperty.mOrigin, conditionalUserProperty.mCreationTimestamp, true, false)));
                return;
            } catch (IllegalArgumentException e) {
                return;
            }
        }
        zzayp().zzbaz().log("Conditional property not cleared since Firebase Analytics is disabled");
    }

    private final List<ConditionalUserProperty> zzl(String str, String str2, String str3) {
        if (zzayo().zzbbk()) {
            zzayp().zzbau().log("Cannot get conditional user properties from analytics worker thread");
            return Collections.emptyList();
        }
        zzayo();
        if (zzcke.zzas()) {
            zzayp().zzbau().log("Cannot get conditional user properties from main thread");
            return Collections.emptyList();
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zzjev.zzayo().zzh(new zzclo(this, atomicReference, str, str2, str3));
            try {
                atomicReference.wait(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            } catch (InterruptedException e) {
                zzayp().zzbaw().zze("Interrupted waiting for get conditional user properties", str, e);
            }
        }
        List<zzcii> list = (List) atomicReference.get();
        if (list == null) {
            zzayp().zzbaw().zzj("Timed out waiting for get conditional user properties", str);
            return Collections.emptyList();
        }
        List<ConditionalUserProperty> arrayList = new ArrayList(list.size());
        for (zzcii zzcii : list) {
            ConditionalUserProperty conditionalUserProperty = new ConditionalUserProperty();
            conditionalUserProperty.mAppId = str;
            conditionalUserProperty.mOrigin = str2;
            conditionalUserProperty.mCreationTimestamp = zzcii.zzjgo;
            conditionalUserProperty.mName = zzcii.zzjgn.name;
            conditionalUserProperty.mValue = zzcii.zzjgn.getValue();
            conditionalUserProperty.mActive = zzcii.zzjgp;
            conditionalUserProperty.mTriggerEventName = zzcii.zzjgq;
            if (zzcii.zzjgr != null) {
                conditionalUserProperty.mTimedOutEventName = zzcii.zzjgr.name;
                if (zzcii.zzjgr.zzjhr != null) {
                    conditionalUserProperty.mTimedOutEventParams = zzcii.zzjgr.zzjhr.zzbao();
                }
            }
            conditionalUserProperty.mTriggerTimeout = zzcii.zzjgs;
            if (zzcii.zzjgt != null) {
                conditionalUserProperty.mTriggeredEventName = zzcii.zzjgt.name;
                if (zzcii.zzjgt.zzjhr != null) {
                    conditionalUserProperty.mTriggeredEventParams = zzcii.zzjgt.zzjhr.zzbao();
                }
            }
            conditionalUserProperty.mTriggeredTimestamp = zzcii.zzjgn.zzjsi;
            conditionalUserProperty.mTimeToLive = zzcii.zzjgu;
            if (zzcii.zzjgv != null) {
                conditionalUserProperty.mExpiredEventName = zzcii.zzjgv.name;
                if (zzcii.zzjgv.zzjhr != null) {
                    conditionalUserProperty.mExpiredEventParams = zzcii.zzjgv.zzjhr.zzbao();
                }
            }
            arrayList.add(conditionalUserProperty);
        }
        return arrayList;
    }

    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        zza(null, str, str2, bundle);
    }

    public final void clearConditionalUserPropertyAs(String str, String str2, String str3, Bundle bundle) {
        zzbq.zzgv(str);
        zzaxz();
        zza(str, str2, str3, bundle);
    }

    public final Task<String> getAppInstanceId() {
        try {
            String zzbbf = zzayq().zzbbf();
            return zzbbf != null ? Tasks.forResult(zzbbf) : Tasks.call(zzayo().zzbbl(), new zzclv(this));
        } catch (Exception e) {
            zzayp().zzbaw().log("Failed to schedule task for getAppInstanceId");
            return Tasks.forException(e);
        }
    }

    public final List<ConditionalUserProperty> getConditionalUserProperties(String str, String str2) {
        return zzl(null, str, str2);
    }

    public final List<ConditionalUserProperty> getConditionalUserPropertiesAs(String str, String str2, String str3) {
        zzbq.zzgv(str);
        zzaxz();
        return zzl(str, str2, str3);
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        return zzb(null, str, str2, z);
    }

    public final Map<String, Object> getUserPropertiesAs(String str, String str2, String str3, boolean z) {
        zzbq.zzgv(str);
        zzaxz();
        return zzb(str, str2, str3, z);
    }

    @Hide
    public final void registerOnMeasurementEventListener(OnEventListener onEventListener) {
        zzyk();
        zzbq.checkNotNull(onEventListener);
        if (!this.zzjpv.add(onEventListener)) {
            zzayp().zzbaw().log("OnEventListener already registered");
        }
    }

    public final void resetAnalyticsData() {
        zzayo().zzh(new zzclx(this));
    }

    public final void setConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
        zzbq.checkNotNull(conditionalUserProperty);
        ConditionalUserProperty conditionalUserProperty2 = new ConditionalUserProperty(conditionalUserProperty);
        if (!TextUtils.isEmpty(conditionalUserProperty2.mAppId)) {
            zzayp().zzbaw().log("Package name should be null when calling setConditionalUserProperty");
        }
        conditionalUserProperty2.mAppId = null;
        zza(conditionalUserProperty2);
    }

    public final void setConditionalUserPropertyAs(ConditionalUserProperty conditionalUserProperty) {
        zzbq.checkNotNull(conditionalUserProperty);
        zzbq.zzgv(conditionalUserProperty.mAppId);
        zzaxz();
        zza(new ConditionalUserProperty(conditionalUserProperty));
    }

    @WorkerThread
    public final void setEventInterceptor(EventInterceptor eventInterceptor) {
        zzwj();
        zzyk();
        if (!(eventInterceptor == null || eventInterceptor == this.zzjpu)) {
            zzbq.zza(this.zzjpu == null, (Object) "EventInterceptor already set.");
        }
        this.zzjpu = eventInterceptor;
    }

    public final void setMeasurementEnabled(boolean z) {
        zzyk();
        zzayo().zzh(new zzcll(this, z));
    }

    public final void setMinimumSessionDuration(long j) {
        zzayo().zzh(new zzclq(this, j));
    }

    public final void setSessionTimeoutDuration(long j) {
        zzayo().zzh(new zzclr(this, j));
    }

    @Hide
    public final void unregisterOnMeasurementEventListener(OnEventListener onEventListener) {
        zzyk();
        zzbq.checkNotNull(onEventListener);
        if (!this.zzjpv.remove(onEventListener)) {
            zzayp().zzbaw().log("OnEventListener had not been registered");
        }
    }

    public final void zza(String str, String str2, Bundle bundle, long j) {
        zza(str, str2, j, bundle, false, true, true, null);
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z) {
        boolean z2 = this.zzjpu == null || zzcno.zzkp(str2);
        zza(str, str2, bundle, true, z2, true, null);
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

    public final void zzb(String str, String str2, Object obj) {
        int i = 0;
        zzbq.zzgv(str);
        long currentTimeMillis = zzxx().currentTimeMillis();
        int zzkk = zzayl().zzkk(str2);
        String zza;
        if (zzkk != 0) {
            zzayl();
            zza = zzcno.zza(str2, 24, true);
            if (str2 != null) {
                i = str2.length();
            }
            this.zzjev.zzayl().zza(zzkk, "_ev", zza, i);
        } else if (obj != null) {
            zzkk = zzayl().zzl(str2, obj);
            if (zzkk != 0) {
                zzayl();
                zza = zzcno.zza(str2, 24, true);
                if ((obj instanceof String) || (obj instanceof CharSequence)) {
                    i = String.valueOf(obj).length();
                }
                this.zzjev.zzayl().zza(zzkk, "_ev", zza, i);
                return;
            }
            Object zzm = zzayl().zzm(str2, obj);
            if (zzm != null) {
                zza(str, str2, currentTimeMillis, zzm);
            }
        } else {
            zza(str, str2, currentTimeMillis, null);
        }
    }

    @Nullable
    public final String zzbbf() {
        return (String) this.zzjpx.get();
    }

    @Nullable
    final String zzbd(long j) {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            zzayo().zzh(new zzclw(this, atomicReference));
            try {
                atomicReference.wait(j);
            } catch (InterruptedException e) {
                zzayp().zzbaw().log("Interrupted waiting for app instance id");
                return null;
            }
        }
        return (String) atomicReference.get();
    }

    public final List<zzcnl> zzbv(boolean z) {
        zzyk();
        zzayp().zzbaz().log("Fetching user attributes (FE)");
        if (zzayo().zzbbk()) {
            zzayp().zzbau().log("Cannot get all user properties from analytics worker thread");
            return Collections.emptyList();
        }
        zzayo();
        if (zzcke.zzas()) {
            zzayp().zzbau().log("Cannot get all user properties from main thread");
            return Collections.emptyList();
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zzjev.zzayo().zzh(new zzclu(this, atomicReference, z));
            try {
                atomicReference.wait(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            } catch (InterruptedException e) {
                zzayp().zzbaw().zzj("Interrupted waiting for get user properties", e);
            }
        }
        List<zzcnl> list = (List) atomicReference.get();
        if (list != null) {
            return list;
        }
        zzayp().zzbaw().log("Timed out waiting for get user properties");
        return Collections.emptyList();
    }

    public final void zzd(String str, String str2, Bundle bundle) {
        boolean z = this.zzjpu == null || zzcno.zzkp(str2);
        zza(str, str2, bundle, true, z, false, null);
    }

    final void zzjx(@Nullable String str) {
        this.zzjpx.set(str);
    }

    public final /* bridge */ /* synthetic */ void zzwj() {
        super.zzwj();
    }

    public final /* bridge */ /* synthetic */ zze zzxx() {
        return super.zzxx();
    }
}
