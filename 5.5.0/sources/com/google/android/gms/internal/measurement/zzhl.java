package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.p022f.C0464a;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.util.VisibleForTesting;
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

public final class zzhl extends zzhi {
    @VisibleForTesting
    protected zzie zzanz;
    private EventInterceptor zzaoa;
    private final Set<OnEventListener> zzaob = new CopyOnWriteArraySet();
    private boolean zzaoc;
    private final AtomicReference<String> zzaod = new AtomicReference();
    @VisibleForTesting
    protected boolean zzaoe = true;

    protected zzhl(zzgm zzgm) {
        super(zzgm);
    }

    private final void zza(ConditionalUserProperty conditionalUserProperty) {
        long currentTimeMillis = zzbt().currentTimeMillis();
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty.mName);
        Preconditions.checkNotEmpty(conditionalUserProperty.mOrigin);
        Preconditions.checkNotNull(conditionalUserProperty.mValue);
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        String str = conditionalUserProperty.mName;
        Object obj = conditionalUserProperty.mValue;
        if (zzgc().zzce(str) != 0) {
            zzgf().zzis().zzg("Invalid conditional user property name", zzgb().zzbk(str));
        } else if (zzgc().zzi(str, obj) != 0) {
            zzgf().zzis().zze("Invalid conditional user property value", zzgb().zzbk(str), obj);
        } else {
            Object zzj = zzgc().zzj(str, obj);
            if (zzj == null) {
                zzgf().zzis().zze("Unable to normalize conditional user property value", zzgb().zzbk(str), obj);
                return;
            }
            conditionalUserProperty.mValue = zzj;
            long j = conditionalUserProperty.mTriggerTimeout;
            if (TextUtils.isEmpty(conditionalUserProperty.mTriggerEventName) || (j <= 15552000000L && j >= 1)) {
                j = conditionalUserProperty.mTimeToLive;
                if (j > 15552000000L || j < 1) {
                    zzgf().zzis().zze("Invalid conditional user property time to live", zzgb().zzbk(str), Long.valueOf(j));
                    return;
                } else {
                    zzge().zzc(new zzhs(this, conditionalUserProperty));
                    return;
                }
            }
            zzgf().zzis().zze("Invalid conditional user property timeout", zzgb().zzbk(str), Long.valueOf(j));
        }
    }

    private final void zza(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(bundle);
        zzab();
        zzch();
        if (this.zzacw.isEnabled()) {
            if (!this.zzaoc) {
                this.zzaoc = true;
                try {
                    try {
                        Class.forName("com.google.android.gms.tagmanager.TagManagerService").getDeclaredMethod("initialize", new Class[]{Context.class}).invoke(null, new Object[]{getContext()});
                    } catch (Exception e) {
                        zzgf().zziv().zzg("Failed to invoke Tag Manager's initialize() method", e);
                    }
                } catch (ClassNotFoundException e2) {
                    zzgf().zzix().log("Tag Manager is not found and thus will not be used");
                }
            }
            if (z3) {
                zzgi();
                if (!"_iap".equals(str2)) {
                    zzkc zzgc = this.zzacw.zzgc();
                    int i = !zzgc.zzq("event", str2) ? 2 : !zzgc.zza("event", Event.zzacx, str2) ? 13 : !zzgc.zza("event", 40, str2) ? 2 : 0;
                    if (i != 0) {
                        zzgf().zziu().zzg("Invalid public event name. Event will not be logged (FE)", zzgb().zzbi(str2));
                        this.zzacw.zzgc();
                        this.zzacw.zzgc().zza(i, "_ev", zzkc.zza(str2, 40, true), str2 != null ? str2.length() : 0);
                        return;
                    }
                }
            }
            zzgi();
            zzif zzkk = zzfz().zzkk();
            if (zzkk != null) {
                if (!bundle.containsKey("_sc")) {
                    zzkk.zzaou = true;
                }
            }
            boolean z4 = z && z3;
            zzig.zza(zzkk, bundle, z4);
            boolean equals = "am".equals(str);
            z4 = zzkc.zzch(str2);
            if (z && this.zzaoa != null && !z4 && !equals) {
                zzgf().zziy().zze("Passing event to registered event handler (FE)", zzgb().zzbi(str2), zzgb().zzb(bundle));
                this.zzaoa.interceptEvent(str, str2, bundle, j);
                return;
            } else if (this.zzacw.zzkd()) {
                int zzcc = zzgc().zzcc(str2);
                if (zzcc != 0) {
                    zzgf().zziu().zzg("Invalid event name. Event will not be logged (FE)", zzgb().zzbi(str2));
                    zzgc();
                    this.zzacw.zzgc().zza(str3, zzcc, "_ev", zzkc.zza(str2, 40, true), str2 != null ? str2.length() : 0);
                    return;
                }
                Bundle zza;
                List listOf = CollectionUtils.listOf("_o", "_sn", "_sc", "_si");
                Bundle zza2 = zzgc().zza(str2, bundle, listOf, z3, true);
                zzif zzif = (zza2 != null && zza2.containsKey("_sc") && zza2.containsKey("_si")) ? new zzif(zza2.getString("_sn"), zza2.getString("_sc"), Long.valueOf(zza2.getLong("_si")).longValue()) : null;
                zzif zzif2 = zzif == null ? zzkk : zzif;
                List arrayList = new ArrayList();
                arrayList.add(zza2);
                long nextLong = zzgc().zzll().nextLong();
                int i2 = 0;
                String[] strArr = (String[]) zza2.keySet().toArray(new String[bundle.size()]);
                Arrays.sort(strArr);
                int length = strArr.length;
                int i3 = 0;
                while (i3 < length) {
                    int length2;
                    String str4 = strArr[i3];
                    Object obj = zza2.get(str4);
                    zzgc();
                    Bundle[] zze = zzkc.zze(obj);
                    if (zze != null) {
                        zza2.putInt(str4, zze.length);
                        for (int i4 = 0; i4 < zze.length; i4++) {
                            Bundle bundle2 = zze[i4];
                            zzig.zza(zzif2, bundle2, true);
                            zza = zzgc().zza("_ep", bundle2, listOf, z3, false);
                            zza.putString("_en", str2);
                            zza.putLong("_eid", nextLong);
                            zza.putString("_gn", str4);
                            zza.putInt("_ll", zze.length);
                            zza.putInt("_i", i4);
                            arrayList.add(zza);
                        }
                        length2 = zze.length + i2;
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
                    Bundle zzd = z2 ? zzgc().zzd(zza) : zza;
                    zzgf().zziy().zze("Logging event (FE)", zzgb().zzbi(str2), zzgb().zzb(zzd));
                    zzfy().zzb(new zzew(str5, new zzet(zzd), str, j), str3);
                    if (!equals) {
                        for (OnEventListener onEvent : this.zzaob) {
                            onEvent.onEvent(str, str2, new Bundle(zzd), j);
                        }
                    }
                    i5++;
                }
                zzgi();
                if (zzfz().zzkk() != null && Event.APP_EXCEPTION.equals(str2)) {
                    zzgd().zzl(true);
                    return;
                }
                return;
            } else {
                return;
            }
        }
        zzgf().zziy().log("Event not sent since app measurement is disabled");
    }

    private final void zza(String str, String str2, long j, Object obj) {
        zzge().zzc(new zzhn(this, str, str2, obj, j));
    }

    private final void zza(String str, String str2, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        zzb(str, str2, zzbt().currentTimeMillis(), bundle, z, z2, z3, str3);
    }

    private final void zza(String str, String str2, Object obj, long j) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzfs();
        zzch();
        if (!this.zzacw.isEnabled()) {
            zzgf().zziy().log("User property not set since app measurement is disabled");
        } else if (this.zzacw.zzkd()) {
            zzgf().zziy().zze("Setting user property (FE)", zzgb().zzbi(str2), obj);
            zzfy().zzb(new zzjz(str2, j, obj, str));
        }
    }

    private final void zza(String str, String str2, String str3, Bundle bundle) {
        long currentTimeMillis = zzbt().currentTimeMillis();
        Preconditions.checkNotEmpty(str2);
        ConditionalUserProperty conditionalUserProperty = new ConditionalUserProperty();
        conditionalUserProperty.mAppId = str;
        conditionalUserProperty.mName = str2;
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        if (str3 != null) {
            conditionalUserProperty.mExpiredEventName = str3;
            conditionalUserProperty.mExpiredEventParams = bundle;
        }
        zzge().zzc(new zzht(this, conditionalUserProperty));
    }

    @VisibleForTesting
    private final Map<String, Object> zzb(String str, String str2, String str3, boolean z) {
        if (zzge().zzjr()) {
            zzgf().zzis().log("Cannot get user properties from analytics worker thread");
            return Collections.emptyMap();
        } else if (zzec.isMainThread()) {
            zzgf().zzis().log("Cannot get user properties from main thread");
            return Collections.emptyMap();
        } else {
            AtomicReference atomicReference = new AtomicReference();
            synchronized (atomicReference) {
                this.zzacw.zzge().zzc(new zzhv(this, atomicReference, str, str2, str3, z));
                try {
                    atomicReference.wait(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                } catch (InterruptedException e) {
                    zzgf().zziv().zzg("Interrupted waiting for get user properties", e);
                }
            }
            List<zzjz> list = (List) atomicReference.get();
            if (list == null) {
                zzgf().zziv().log("Timed out waiting for get user properties");
                return Collections.emptyMap();
            }
            Map<String, Object> c0464a = new C0464a(list.size());
            for (zzjz zzjz : list) {
                c0464a.put(zzjz.name, zzjz.getValue());
            }
            return c0464a;
        }
    }

    private final void zzb(ConditionalUserProperty conditionalUserProperty) {
        zzab();
        zzch();
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty.mName);
        Preconditions.checkNotEmpty(conditionalUserProperty.mOrigin);
        Preconditions.checkNotNull(conditionalUserProperty.mValue);
        if (this.zzacw.isEnabled()) {
            zzjz zzjz = new zzjz(conditionalUserProperty.mName, conditionalUserProperty.mTriggeredTimestamp, conditionalUserProperty.mValue, conditionalUserProperty.mOrigin);
            try {
                zzew zza = zzgc().zza(conditionalUserProperty.mTriggeredEventName, conditionalUserProperty.mTriggeredEventParams, conditionalUserProperty.mOrigin, 0, true, false);
                zzfy().zzd(new zzee(conditionalUserProperty.mAppId, conditionalUserProperty.mOrigin, zzjz, conditionalUserProperty.mCreationTimestamp, false, conditionalUserProperty.mTriggerEventName, zzgc().zza(conditionalUserProperty.mTimedOutEventName, conditionalUserProperty.mTimedOutEventParams, conditionalUserProperty.mOrigin, 0, true, false), conditionalUserProperty.mTriggerTimeout, zza, conditionalUserProperty.mTimeToLive, zzgc().zza(conditionalUserProperty.mExpiredEventName, conditionalUserProperty.mExpiredEventParams, conditionalUserProperty.mOrigin, 0, true, false)));
                return;
            } catch (IllegalArgumentException e) {
                return;
            }
        }
        zzgf().zziy().log("Conditional property not sent since Firebase Analytics is disabled");
    }

    private final void zzb(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
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
        zzge().zzc(new zzid(this, str, str2, j, bundle2, z, z2, z3, str3));
    }

    private final void zzc(ConditionalUserProperty conditionalUserProperty) {
        zzab();
        zzch();
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty.mName);
        if (this.zzacw.isEnabled()) {
            zzjz zzjz = new zzjz(conditionalUserProperty.mName, 0, null, null);
            try {
                zzfy().zzd(new zzee(conditionalUserProperty.mAppId, conditionalUserProperty.mOrigin, zzjz, conditionalUserProperty.mCreationTimestamp, conditionalUserProperty.mActive, conditionalUserProperty.mTriggerEventName, null, conditionalUserProperty.mTriggerTimeout, null, conditionalUserProperty.mTimeToLive, zzgc().zza(conditionalUserProperty.mExpiredEventName, conditionalUserProperty.mExpiredEventParams, conditionalUserProperty.mOrigin, conditionalUserProperty.mCreationTimestamp, true, false)));
                return;
            } catch (IllegalArgumentException e) {
                return;
            }
        }
        zzgf().zziy().log("Conditional property not cleared since Firebase Analytics is disabled");
    }

    @VisibleForTesting
    private final List<ConditionalUserProperty> zzf(String str, String str2, String str3) {
        if (zzge().zzjr()) {
            zzgf().zzis().log("Cannot get conditional user properties from analytics worker thread");
            return Collections.emptyList();
        } else if (zzec.isMainThread()) {
            zzgf().zzis().log("Cannot get conditional user properties from main thread");
            return Collections.emptyList();
        } else {
            AtomicReference atomicReference = new AtomicReference();
            synchronized (atomicReference) {
                this.zzacw.zzge().zzc(new zzhu(this, atomicReference, str, str2, str3));
                try {
                    atomicReference.wait(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                } catch (InterruptedException e) {
                    zzgf().zziv().zze("Interrupted waiting for get conditional user properties", str, e);
                }
            }
            List<zzee> list = (List) atomicReference.get();
            if (list == null) {
                zzgf().zziv().zzg("Timed out waiting for get conditional user properties", str);
                return Collections.emptyList();
            }
            List<ConditionalUserProperty> arrayList = new ArrayList(list.size());
            for (zzee zzee : list) {
                ConditionalUserProperty conditionalUserProperty = new ConditionalUserProperty();
                conditionalUserProperty.mAppId = zzee.packageName;
                conditionalUserProperty.mOrigin = zzee.origin;
                conditionalUserProperty.mCreationTimestamp = zzee.creationTimestamp;
                conditionalUserProperty.mName = zzee.zzaeq.name;
                conditionalUserProperty.mValue = zzee.zzaeq.getValue();
                conditionalUserProperty.mActive = zzee.active;
                conditionalUserProperty.mTriggerEventName = zzee.triggerEventName;
                if (zzee.zzaer != null) {
                    conditionalUserProperty.mTimedOutEventName = zzee.zzaer.name;
                    if (zzee.zzaer.zzafr != null) {
                        conditionalUserProperty.mTimedOutEventParams = zzee.zzaer.zzafr.zzij();
                    }
                }
                conditionalUserProperty.mTriggerTimeout = zzee.triggerTimeout;
                if (zzee.zzaes != null) {
                    conditionalUserProperty.mTriggeredEventName = zzee.zzaes.name;
                    if (zzee.zzaes.zzafr != null) {
                        conditionalUserProperty.mTriggeredEventParams = zzee.zzaes.zzafr.zzij();
                    }
                }
                conditionalUserProperty.mTriggeredTimestamp = zzee.zzaeq.zzarl;
                conditionalUserProperty.mTimeToLive = zzee.timeToLive;
                if (zzee.zzaet != null) {
                    conditionalUserProperty.mExpiredEventName = zzee.zzaet.name;
                    if (zzee.zzaet.zzafr != null) {
                        conditionalUserProperty.mExpiredEventParams = zzee.zzaet.zzafr.zzij();
                    }
                }
                arrayList.add(conditionalUserProperty);
            }
            return arrayList;
        }
    }

    private final void zzi(boolean z) {
        zzab();
        zzfs();
        zzch();
        zzgf().zziy().zzg("Setting app measurement enabled (FE)", Boolean.valueOf(z));
        zzgg().setMeasurementEnabled(z);
        if (!zzgh().zzay(zzfw().zzah())) {
            zzfy().zzkm();
        } else if (this.zzacw.isEnabled() && this.zzaoe) {
            zzgf().zziy().log("Recording app launch after enabling measurement for the first time (FE)");
            zzkj();
        } else {
            zzfy().zzkm();
        }
    }

    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        zzfs();
        zza(null, str, str2, bundle);
    }

    public final void clearConditionalUserPropertyAs(String str, String str2, String str3, Bundle bundle) {
        Preconditions.checkNotEmpty(str);
        zzfr();
        zza(str, str2, str3, bundle);
    }

    public final Task<String> getAppInstanceId() {
        try {
            String zzjh = zzgg().zzjh();
            return zzjh != null ? Tasks.forResult(zzjh) : Tasks.call(zzge().zzjs(), new zzhp(this));
        } catch (Exception e) {
            zzgf().zziv().log("Failed to schedule task for getAppInstanceId");
            return Tasks.forException(e);
        }
    }

    public final List<ConditionalUserProperty> getConditionalUserProperties(String str, String str2) {
        zzfs();
        return zzf(null, str, str2);
    }

    public final List<ConditionalUserProperty> getConditionalUserPropertiesAs(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzfr();
        return zzf(str, str2, str3);
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        zzfs();
        return zzb(null, str, str2, z);
    }

    public final Map<String, Object> getUserPropertiesAs(String str, String str2, String str3, boolean z) {
        Preconditions.checkNotEmpty(str);
        zzfr();
        return zzb(str, str2, str3, z);
    }

    public final void logEvent(String str, String str2, Bundle bundle) {
        zzfs();
        boolean z = this.zzaoa == null || zzkc.zzch(str2);
        zza(str, str2, bundle, true, z, false, null);
    }

    public final void registerOnMeasurementEventListener(OnEventListener onEventListener) {
        zzfs();
        zzch();
        Preconditions.checkNotNull(onEventListener);
        if (!this.zzaob.add(onEventListener)) {
            zzgf().zziv().log("OnEventListener already registered");
        }
    }

    public final void resetAnalyticsData() {
        zzge().zzc(new zzhr(this, zzbt().currentTimeMillis()));
    }

    public final void setConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
        Preconditions.checkNotNull(conditionalUserProperty);
        zzfs();
        ConditionalUserProperty conditionalUserProperty2 = new ConditionalUserProperty(conditionalUserProperty);
        if (!TextUtils.isEmpty(conditionalUserProperty2.mAppId)) {
            zzgf().zziv().log("Package name should be null when calling setConditionalUserProperty");
        }
        conditionalUserProperty2.mAppId = null;
        zza(conditionalUserProperty2);
    }

    public final void setConditionalUserPropertyAs(ConditionalUserProperty conditionalUserProperty) {
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty.mAppId);
        zzfr();
        zza(new ConditionalUserProperty(conditionalUserProperty));
    }

    public final void setEventInterceptor(EventInterceptor eventInterceptor) {
        zzab();
        zzfs();
        zzch();
        if (!(eventInterceptor == null || eventInterceptor == this.zzaoa)) {
            Preconditions.checkState(this.zzaoa == null, "EventInterceptor already set.");
        }
        this.zzaoa = eventInterceptor;
    }

    public final void setMeasurementEnabled(boolean z) {
        zzch();
        zzfs();
        zzge().zzc(new zzia(this, z));
    }

    public final void setMinimumSessionDuration(long j) {
        zzfs();
        zzge().zzc(new zzib(this, j));
    }

    public final void setSessionTimeoutDuration(long j) {
        zzfs();
        zzge().zzc(new zzic(this, j));
    }

    public final void setUserProperty(String str, String str2, Object obj) {
        int i = 0;
        Preconditions.checkNotEmpty(str);
        long currentTimeMillis = zzbt().currentTimeMillis();
        int zzce = zzgc().zzce(str2);
        String zza;
        if (zzce != 0) {
            zzgc();
            zza = zzkc.zza(str2, 24, true);
            if (str2 != null) {
                i = str2.length();
            }
            this.zzacw.zzgc().zza(zzce, "_ev", zza, i);
        } else if (obj != null) {
            zzce = zzgc().zzi(str2, obj);
            if (zzce != 0) {
                zzgc();
                zza = zzkc.zza(str2, 24, true);
                if ((obj instanceof String) || (obj instanceof CharSequence)) {
                    i = String.valueOf(obj).length();
                }
                this.zzacw.zzgc().zza(zzce, "_ev", zza, i);
                return;
            }
            Object zzj = zzgc().zzj(str2, obj);
            if (zzj != null) {
                zza(str, str2, currentTimeMillis, zzj);
            }
        } else {
            zza(str, str2, currentTimeMillis, null);
        }
    }

    public final void unregisterOnMeasurementEventListener(OnEventListener onEventListener) {
        zzfs();
        zzch();
        Preconditions.checkNotNull(onEventListener);
        if (!this.zzaob.remove(onEventListener)) {
            zzgf().zziv().log("OnEventListener had not been registered");
        }
    }

    final void zza(String str, String str2, Bundle bundle) {
        zzfs();
        zzab();
        boolean z = this.zzaoa == null || zzkc.zzch(str2);
        zza(str, str2, zzbt().currentTimeMillis(), bundle, true, z, false, null);
    }

    public final void zza(String str, String str2, Bundle bundle, long j) {
        zzfs();
        zzb(str, str2, j, bundle, false, true, true, null);
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z) {
        zzfs();
        boolean z2 = this.zzaoa == null || zzkc.zzch(str2);
        zza(str, str2, bundle, true, z2, true, null);
    }

    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    final String zzae(long j) {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            zzge().zzc(new zzhq(this, atomicReference));
            try {
                atomicReference.wait(j);
            } catch (InterruptedException e) {
                zzgf().zziv().log("Interrupted waiting for app instance id");
                return null;
            }
        }
        return (String) atomicReference.get();
    }

    final void zzbq(String str) {
        this.zzaod.set(str);
    }

    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
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

    public final String zzhq() {
        AtomicReference atomicReference = new AtomicReference();
        return (String) zzge().zza(atomicReference, 15000, "String test flag value", new zzhw(this, atomicReference));
    }

    public final List<zzjz> zzj(boolean z) {
        zzfs();
        zzch();
        zzgf().zziy().log("Fetching user attributes (FE)");
        if (zzge().zzjr()) {
            zzgf().zzis().log("Cannot get all user properties from analytics worker thread");
            return Collections.emptyList();
        } else if (zzec.isMainThread()) {
            zzgf().zzis().log("Cannot get all user properties from main thread");
            return Collections.emptyList();
        } else {
            AtomicReference atomicReference = new AtomicReference();
            synchronized (atomicReference) {
                this.zzacw.zzge().zzc(new zzho(this, atomicReference, z));
                try {
                    atomicReference.wait(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                } catch (InterruptedException e) {
                    zzgf().zziv().zzg("Interrupted waiting for get user properties", e);
                }
            }
            List<zzjz> list = (List) atomicReference.get();
            if (list != null) {
                return list;
            }
            zzgf().zziv().log("Timed out waiting for get user properties");
            return Collections.emptyList();
        }
    }

    public final String zzjh() {
        zzfs();
        return (String) this.zzaod.get();
    }

    public final Boolean zzkf() {
        AtomicReference atomicReference = new AtomicReference();
        return (Boolean) zzge().zza(atomicReference, 15000, "boolean test flag value", new zzhm(this, atomicReference));
    }

    public final Long zzkg() {
        AtomicReference atomicReference = new AtomicReference();
        return (Long) zzge().zza(atomicReference, 15000, "long test flag value", new zzhx(this, atomicReference));
    }

    public final Integer zzkh() {
        AtomicReference atomicReference = new AtomicReference();
        return (Integer) zzge().zza(atomicReference, 15000, "int test flag value", new zzhy(this, atomicReference));
    }

    public final Double zzki() {
        AtomicReference atomicReference = new AtomicReference();
        return (Double) zzge().zza(atomicReference, 15000, "double test flag value", new zzhz(this, atomicReference));
    }

    public final void zzkj() {
        zzab();
        zzfs();
        zzch();
        if (this.zzacw.zzkd()) {
            zzfy().zzkj();
            this.zzaoe = false;
            String zzjk = zzgg().zzjk();
            if (!TextUtils.isEmpty(zzjk)) {
                zzfx().zzch();
                if (!zzjk.equals(VERSION.RELEASE)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("_po", zzjk);
                    logEvent("auto", "_ou", bundle);
                }
            }
        }
    }
}
