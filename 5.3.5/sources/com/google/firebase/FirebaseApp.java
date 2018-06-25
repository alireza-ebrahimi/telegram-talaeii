package com.google.firebase;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.ArraySet;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.zzk;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzu;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.internal.InternalTokenProvider;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FirebaseApp {
    public static final String DEFAULT_APP_NAME = "[DEFAULT]";
    private static final Object sLock = new Object();
    static final Map<String, FirebaseApp> zzimu = new ArrayMap();
    private static final List<String> zzmmm = Arrays.asList(new String[]{"com.google.firebase.auth.FirebaseAuth", "com.google.firebase.iid.FirebaseInstanceId"});
    private static final List<String> zzmmn = Collections.singletonList("com.google.firebase.crash.FirebaseCrash");
    private static final List<String> zzmmo = Arrays.asList(new String[]{"com.google.android.gms.measurement.AppMeasurement"});
    private static final List<String> zzmmp = Arrays.asList(new String[0]);
    private static final Set<String> zzmmq = Collections.emptySet();
    private final Context mApplicationContext;
    private final String mName;
    private final FirebaseOptions zzmmr;
    private final AtomicBoolean zzmms = new AtomicBoolean(false);
    private final AtomicBoolean zzmmt = new AtomicBoolean();
    private final List<IdTokenListener> zzmmu = new CopyOnWriteArrayList();
    private final List<zza> zzmmv = new CopyOnWriteArrayList();
    private final List<Object> zzmmw = new CopyOnWriteArrayList();
    private InternalTokenProvider zzmmx;
    private zzb zzmmy;

    @Hide
    @KeepForSdk
    public interface IdTokenListener {
        void zzb(@NonNull com.google.firebase.internal.zzc zzc);
    }

    @Hide
    public interface zza {
        void zzbj(boolean z);
    }

    @Hide
    public interface zzb {
        void zzha(int i);
    }

    @Hide
    @TargetApi(24)
    static class zzc extends BroadcastReceiver {
        private static AtomicReference<zzc> zzmmz = new AtomicReference();
        private final Context mApplicationContext;

        private zzc(Context context) {
            this.mApplicationContext = context;
        }

        private static void zzew(Context context) {
            if (zzmmz.get() == null) {
                BroadcastReceiver zzc = new zzc(context);
                if (zzmmz.compareAndSet(null, zzc)) {
                    context.registerReceiver(zzc, new IntentFilter("android.intent.action.USER_UNLOCKED"));
                }
            }
        }

        public final void onReceive(Context context, Intent intent) {
            synchronized (FirebaseApp.sLock) {
                for (FirebaseApp zza : FirebaseApp.zzimu.values()) {
                    zza.zzbsx();
                }
            }
            this.mApplicationContext.unregisterReceiver(this);
        }
    }

    @Hide
    private FirebaseApp(Context context, String str, FirebaseOptions firebaseOptions) {
        this.mApplicationContext = (Context) zzbq.checkNotNull(context);
        this.mName = zzbq.zzgv(str);
        this.zzmmr = (FirebaseOptions) zzbq.checkNotNull(firebaseOptions);
        this.zzmmy = new com.google.firebase.internal.zza();
    }

    public static List<FirebaseApp> getApps(Context context) {
        List<FirebaseApp> arrayList;
        com.google.firebase.internal.zzb.zzfb(context);
        synchronized (sLock) {
            arrayList = new ArrayList(zzimu.values());
            com.google.firebase.internal.zzb.zzclx();
            Set<String> zzcly = com.google.firebase.internal.zzb.zzcly();
            zzcly.removeAll(zzimu.keySet());
            for (String str : zzcly) {
                com.google.firebase.internal.zzb.zzrw(str);
                arrayList.add(initializeApp(context, null, str));
            }
        }
        return arrayList;
    }

    @Nullable
    public static FirebaseApp getInstance() {
        FirebaseApp firebaseApp;
        synchronized (sLock) {
            firebaseApp = (FirebaseApp) zzimu.get(DEFAULT_APP_NAME);
            if (firebaseApp == null) {
                String zzany = zzu.zzany();
                throw new IllegalStateException(new StringBuilder(String.valueOf(zzany).length() + 116).append("Default FirebaseApp is not initialized in this process ").append(zzany).append(". Make sure to call FirebaseApp.initializeApp(Context) first.").toString());
            }
        }
        return firebaseApp;
    }

    public static FirebaseApp getInstance(@NonNull String str) {
        FirebaseApp firebaseApp;
        synchronized (sLock) {
            firebaseApp = (FirebaseApp) zzimu.get(str.trim());
            if (firebaseApp != null) {
            } else {
                String str2;
                Iterable zzbsw = zzbsw();
                if (zzbsw.isEmpty()) {
                    str2 = "";
                } else {
                    String str3 = "Available app names: ";
                    str2 = String.valueOf(TextUtils.join(", ", zzbsw));
                    str2 = str2.length() != 0 ? str3.concat(str2) : new String(str3);
                }
                throw new IllegalStateException(String.format("FirebaseApp with name %s doesn't exist. %s", new Object[]{str, str2}));
            }
        }
        return firebaseApp;
    }

    @Nullable
    public static FirebaseApp initializeApp(Context context) {
        FirebaseApp instance;
        synchronized (sLock) {
            if (zzimu.containsKey(DEFAULT_APP_NAME)) {
                instance = getInstance();
            } else {
                FirebaseOptions fromResource = FirebaseOptions.fromResource(context);
                if (fromResource == null) {
                    instance = null;
                } else {
                    instance = initializeApp(context, fromResource);
                }
            }
        }
        return instance;
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions firebaseOptions) {
        return initializeApp(context, firebaseOptions, DEFAULT_APP_NAME);
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions firebaseOptions, String str) {
        FirebaseApp firebaseApp;
        com.google.firebase.internal.zzb.zzfb(context);
        if (context.getApplicationContext() instanceof Application) {
            zzk.zza((Application) context.getApplicationContext());
            zzk.zzaij().zza(new zza());
        }
        String trim = str.trim();
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        synchronized (sLock) {
            zzbq.zza(!zzimu.containsKey(trim), new StringBuilder(String.valueOf(trim).length() + 33).append("FirebaseApp name ").append(trim).append(" already exists!").toString());
            zzbq.checkNotNull(context, "Application context cannot be null.");
            firebaseApp = new FirebaseApp(context, trim, firebaseOptions);
            zzimu.put(trim, firebaseApp);
        }
        com.google.firebase.internal.zzb.zzg(firebaseApp);
        firebaseApp.zza(FirebaseApp.class, firebaseApp, zzmmm);
        if (firebaseApp.zzbsu()) {
            firebaseApp.zza(FirebaseApp.class, firebaseApp, zzmmn);
            firebaseApp.zza(Context.class, firebaseApp.getApplicationContext(), zzmmo);
        }
        return firebaseApp;
    }

    private final <T> void zza(Class<T> cls, T t, Iterable<String> iterable) {
        String valueOf;
        boolean isDeviceProtectedStorage = ContextCompat.isDeviceProtectedStorage(this.mApplicationContext);
        if (isDeviceProtectedStorage) {
            zzc.zzew(this.mApplicationContext);
        }
        for (String valueOf2 : iterable) {
            if (isDeviceProtectedStorage) {
                try {
                    if (!zzmmp.contains(valueOf2)) {
                    }
                } catch (ClassNotFoundException e) {
                    if (zzmmq.contains(valueOf2)) {
                        throw new IllegalStateException(String.valueOf(valueOf2).concat(" is missing, but is required. Check if it has been removed by Proguard."));
                    }
                    Log.d("FirebaseApp", String.valueOf(valueOf2).concat(" is not linked. Skipping initialization."));
                } catch (NoSuchMethodException e2) {
                    throw new IllegalStateException(String.valueOf(valueOf2).concat("#getInstance has been removed by Proguard. Add keep rule to prevent it."));
                } catch (Throwable e3) {
                    Log.wtf("FirebaseApp", "Firebase API initialization failure.", e3);
                } catch (Throwable e4) {
                    String str = "FirebaseApp";
                    String str2 = "Failed to initialize ";
                    valueOf2 = String.valueOf(valueOf2);
                    Log.wtf(str, valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2), e4);
                }
            }
            Method method = Class.forName(valueOf2).getMethod("getInstance", new Class[]{cls});
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                method.invoke(null, new Object[]{t});
            }
        }
    }

    @Hide
    public static void zzbj(boolean z) {
        synchronized (sLock) {
            ArrayList arrayList = new ArrayList(zzimu.values());
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                FirebaseApp firebaseApp = (FirebaseApp) obj;
                if (firebaseApp.zzmms.get()) {
                    firebaseApp.zzci(z);
                }
            }
        }
    }

    private final void zzbst() {
        zzbq.zza(!this.zzmmt.get(), (Object) "FirebaseApp was deleted");
    }

    private static List<String> zzbsw() {
        Collection arraySet = new ArraySet();
        synchronized (sLock) {
            for (FirebaseApp name : zzimu.values()) {
                arraySet.add(name.getName());
            }
            if (com.google.firebase.internal.zzb.zzclx() != null) {
                arraySet.addAll(com.google.firebase.internal.zzb.zzcly());
            }
        }
        List<String> arrayList = new ArrayList(arraySet);
        Collections.sort(arrayList);
        return arrayList;
    }

    private final void zzbsx() {
        zza(FirebaseApp.class, this, zzmmm);
        if (zzbsu()) {
            zza(FirebaseApp.class, this, zzmmn);
            zza(Context.class, this.mApplicationContext, zzmmo);
        }
    }

    private final void zzci(boolean z) {
        Log.d("FirebaseApp", "Notifying background state change listeners.");
        for (zza zzbj : this.zzmmv) {
            zzbj.zzbj(z);
        }
    }

    public boolean equals(Object obj) {
        return !(obj instanceof FirebaseApp) ? false : this.mName.equals(((FirebaseApp) obj).getName());
    }

    @NonNull
    public Context getApplicationContext() {
        zzbst();
        return this.mApplicationContext;
    }

    @NonNull
    public String getName() {
        zzbst();
        return this.mName;
    }

    @NonNull
    public FirebaseOptions getOptions() {
        zzbst();
        return this.zzmmr;
    }

    @Hide
    @KeepForSdk
    public Task<GetTokenResult> getToken(boolean z) {
        zzbst();
        return this.zzmmx == null ? Tasks.forException(new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode.")) : this.zzmmx.zzcj(z);
    }

    @Nullable
    @Hide
    public final String getUid() throws FirebaseApiNotAvailableException {
        zzbst();
        if (this.zzmmx != null) {
            return this.zzmmx.getUid();
        }
        throw new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode.");
    }

    public int hashCode() {
        return this.mName.hashCode();
    }

    public void setAutomaticResourceManagementEnabled(boolean z) {
        zzbst();
        if (this.zzmms.compareAndSet(!z, z)) {
            boolean zzaik = zzk.zzaij().zzaik();
            if (z && zzaik) {
                zzci(true);
            } else if (!z && zzaik) {
                zzci(false);
            }
        }
    }

    public String toString() {
        return zzbg.zzx(this).zzg("name", this.mName).zzg("options", this.zzmmr).toString();
    }

    @Hide
    public final void zza(@NonNull IdTokenListener idTokenListener) {
        zzbst();
        zzbq.checkNotNull(idTokenListener);
        this.zzmmu.add(idTokenListener);
        this.zzmmy.zzha(this.zzmmu.size());
    }

    @Hide
    public final void zza(zza zza) {
        zzbst();
        if (this.zzmms.get() && zzk.zzaij().zzaik()) {
            zza.zzbj(true);
        }
        this.zzmmv.add(zza);
    }

    @Hide
    public final void zza(@NonNull zzb zzb) {
        this.zzmmy = (zzb) zzbq.checkNotNull(zzb);
        this.zzmmy.zzha(this.zzmmu.size());
    }

    @Hide
    public final void zza(@NonNull InternalTokenProvider internalTokenProvider) {
        this.zzmmx = (InternalTokenProvider) zzbq.checkNotNull(internalTokenProvider);
    }

    @Hide
    @UiThread
    public final void zza(@NonNull com.google.firebase.internal.zzc zzc) {
        Log.d("FirebaseApp", "Notifying auth state listeners.");
        int i = 0;
        for (IdTokenListener zzb : this.zzmmu) {
            zzb.zzb(zzc);
            i++;
        }
        Log.d("FirebaseApp", String.format("Notified %d auth state listeners.", new Object[]{Integer.valueOf(i)}));
    }

    @Hide
    public final void zzb(@NonNull IdTokenListener idTokenListener) {
        zzbst();
        zzbq.checkNotNull(idTokenListener);
        this.zzmmu.remove(idTokenListener);
        this.zzmmy.zzha(this.zzmmu.size());
    }

    @Hide
    public final boolean zzbsu() {
        return DEFAULT_APP_NAME.equals(getName());
    }

    @Hide
    public final String zzbsv() {
        String zzl = com.google.android.gms.common.util.zzc.zzl(getName().getBytes());
        String zzl2 = com.google.android.gms.common.util.zzc.zzl(getOptions().getApplicationId().getBytes());
        return new StringBuilder((String.valueOf(zzl).length() + 1) + String.valueOf(zzl2).length()).append(zzl).append("+").append(zzl2).toString();
    }
}
