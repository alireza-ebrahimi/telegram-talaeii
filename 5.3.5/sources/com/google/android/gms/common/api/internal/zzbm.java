package com.google.android.gms.common.api.internal;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArraySet;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;

@Hide
public final class zzbm implements Callback {
    private static final Object sLock = new Object();
    public static final Status zzfzg = new Status(4, "Sign-out occurred while this API call was in progress.");
    private static final Status zzfzh = new Status(4, "The user must be signed in to make this API call.");
    private static zzbm zzfzj;
    private final Context mContext;
    private final Handler mHandler;
    private final GoogleApiAvailability zzftg;
    private final Map<zzh<?>, zzbo<?>> zzfwg = new ConcurrentHashMap(5, 0.75f, 1);
    private long zzfyf = 120000;
    private long zzfyg = DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
    private long zzfzi = 10000;
    private int zzfzk = -1;
    private final AtomicInteger zzfzl = new AtomicInteger(1);
    private final AtomicInteger zzfzm = new AtomicInteger(0);
    private zzah zzfzn = null;
    private final Set<zzh<?>> zzfzo = new ArraySet();
    private final Set<zzh<?>> zzfzp = new ArraySet();

    private zzbm(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.mContext = context;
        this.mHandler = new Handler(looper, this);
        this.zzftg = googleApiAvailability;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(6));
    }

    public static zzbm zzajy() {
        zzbm zzbm;
        synchronized (sLock) {
            zzbq.checkNotNull(zzfzj, "Must guarantee manager is non-null before using getInstance");
            zzbm = zzfzj;
        }
        return zzbm;
    }

    public static void zzajz() {
        synchronized (sLock) {
            if (zzfzj != null) {
                zzbm zzbm = zzfzj;
                zzbm.zzfzm.incrementAndGet();
                zzbm.mHandler.sendMessageAtFrontOfQueue(zzbm.mHandler.obtainMessage(10));
            }
        }
    }

    @WorkerThread
    private final void zzakb() {
        for (zzh remove : this.zzfzp) {
            ((zzbo) this.zzfwg.remove(remove)).signOut();
        }
        this.zzfzp.clear();
    }

    @WorkerThread
    private final void zzb(GoogleApi<?> googleApi) {
        zzh zzahv = googleApi.zzahv();
        zzbo zzbo = (zzbo) this.zzfwg.get(zzahv);
        if (zzbo == null) {
            zzbo = new zzbo(this, googleApi);
            this.zzfwg.put(zzahv, zzbo);
        }
        if (zzbo.zzacc()) {
            this.zzfzp.add(zzahv);
        }
        zzbo.connect();
    }

    public static zzbm zzck(Context context) {
        zzbm zzbm;
        synchronized (sLock) {
            if (zzfzj == null) {
                HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
                handlerThread.start();
                zzfzj = new zzbm(context.getApplicationContext(), handlerThread.getLooper(), GoogleApiAvailability.getInstance());
            }
            zzbm = zzfzj;
        }
        return zzbm;
    }

    @WorkerThread
    public final boolean handleMessage(Message message) {
        zzbo zzbo;
        switch (message.what) {
            case 1:
                this.zzfzi = ((Boolean) message.obj).booleanValue() ? 10000 : 300000;
                this.mHandler.removeMessages(12);
                for (zzh obtainMessage : this.zzfwg.keySet()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(12, obtainMessage), this.zzfzi);
                }
                break;
            case 2:
                zzj zzj = (zzj) message.obj;
                for (zzh zzh : zzj.zzaii()) {
                    zzbo zzbo2 = (zzbo) this.zzfwg.get(zzh);
                    if (zzbo2 == null) {
                        zzj.zza(zzh, new ConnectionResult(13), null);
                        break;
                    } else if (zzbo2.isConnected()) {
                        zzj.zza(zzh, ConnectionResult.zzfqt, zzbo2.zzaix().zzahp());
                    } else if (zzbo2.zzakj() != null) {
                        zzj.zza(zzh, zzbo2.zzakj(), null);
                    } else {
                        zzbo2.zza(zzj);
                    }
                }
                break;
            case 3:
                for (zzbo zzbo3 : this.zzfwg.values()) {
                    zzbo3.zzaki();
                    zzbo3.connect();
                }
                break;
            case 4:
            case 8:
            case 13:
                zzcp zzcp = (zzcp) message.obj;
                zzbo = (zzbo) this.zzfwg.get(zzcp.zzgba.zzahv());
                if (zzbo == null) {
                    zzb(zzcp.zzgba);
                    zzbo = (zzbo) this.zzfwg.get(zzcp.zzgba.zzahv());
                }
                if (zzbo.zzacc() && this.zzfzm.get() != zzcp.zzgaz) {
                    zzcp.zzgay.zzs(zzfzg);
                    zzbo.signOut();
                    break;
                }
                zzbo.zza(zzcp.zzgay);
                break;
                break;
            case 5:
                String errorString;
                String errorMessage;
                int i = message.arg1;
                ConnectionResult connectionResult = (ConnectionResult) message.obj;
                for (zzbo zzbo4 : this.zzfwg.values()) {
                    if (zzbo4.getInstanceId() == i) {
                        if (zzbo4 != null) {
                            Log.wtf("GoogleApiManager", "Could not find API instance " + i + " while trying to fail enqueued calls.", new Exception());
                            break;
                        }
                        errorString = this.zzftg.getErrorString(connectionResult.getErrorCode());
                        errorMessage = connectionResult.getErrorMessage();
                        zzbo4.zzw(new Status(17, new StringBuilder((String.valueOf(errorString).length() + 69) + String.valueOf(errorMessage).length()).append("Error resolution was canceled by the user, original error message: ").append(errorString).append(": ").append(errorMessage).toString()));
                        break;
                    }
                }
                zzbo4 = null;
                if (zzbo4 != null) {
                    Log.wtf("GoogleApiManager", "Could not find API instance " + i + " while trying to fail enqueued calls.", new Exception());
                } else {
                    errorString = this.zzftg.getErrorString(connectionResult.getErrorCode());
                    errorMessage = connectionResult.getErrorMessage();
                    zzbo4.zzw(new Status(17, new StringBuilder((String.valueOf(errorString).length() + 69) + String.valueOf(errorMessage).length()).append("Error resolution was canceled by the user, original error message: ").append(errorString).append(": ").append(errorMessage).toString()));
                }
            case 6:
                if (this.mContext.getApplicationContext() instanceof Application) {
                    zzk.zza((Application) this.mContext.getApplicationContext());
                    zzk.zzaij().zza(new zzbn(this));
                    if (!zzk.zzaij().zzbi(true)) {
                        this.zzfzi = 300000;
                        break;
                    }
                }
                break;
            case 7:
                zzb((GoogleApi) message.obj);
                break;
            case 9:
                if (this.zzfwg.containsKey(message.obj)) {
                    ((zzbo) this.zzfwg.get(message.obj)).resume();
                    break;
                }
                break;
            case 10:
                zzakb();
                break;
            case 11:
                if (this.zzfwg.containsKey(message.obj)) {
                    ((zzbo) this.zzfwg.get(message.obj)).zzajr();
                    break;
                }
                break;
            case 12:
                if (this.zzfwg.containsKey(message.obj)) {
                    ((zzbo) this.zzfwg.get(message.obj)).zzakm();
                    break;
                }
                break;
            default:
                Log.w("GoogleApiManager", "Unknown message id: " + message.what);
                return false;
        }
        return true;
    }

    final PendingIntent zza(zzh<?> zzh, int i) {
        zzbo zzbo = (zzbo) this.zzfwg.get(zzh);
        if (zzbo == null) {
            return null;
        }
        zzcyj zzakn = zzbo.zzakn();
        return zzakn == null ? null : PendingIntent.getActivity(this.mContext, i, zzakn.getSignInIntent(), 134217728);
    }

    public final <O extends ApiOptions> Task<Boolean> zza(@NonNull GoogleApi<O> googleApi, @NonNull zzck<?> zzck) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.mHandler.sendMessage(this.mHandler.obtainMessage(13, new zzcp(new zzf(zzck, taskCompletionSource), this.zzfzm.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final <O extends ApiOptions> Task<Void> zza(@NonNull GoogleApi<O> googleApi, @NonNull zzcq<zzb, ?> zzcq, @NonNull zzdo<zzb, ?> zzdo) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.mHandler.sendMessage(this.mHandler.obtainMessage(8, new zzcp(new zzd(new zzcr(zzcq, zzdo), taskCompletionSource), this.zzfzm.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final Task<Map<zzh<?>, String>> zza(Iterable<? extends GoogleApi<?>> iterable) {
        zzj zzj = new zzj(iterable);
        for (GoogleApi googleApi : iterable) {
            zzbo zzbo = (zzbo) this.zzfwg.get(googleApi.zzahv());
            if (zzbo == null || !zzbo.isConnected()) {
                this.mHandler.sendMessage(this.mHandler.obtainMessage(2, zzj));
                return zzj.getTask();
            }
            zzj.zza(googleApi.zzahv(), ConnectionResult.zzfqt, zzbo.zzaix().zzahp());
        }
        return zzj.getTask();
    }

    public final void zza(ConnectionResult connectionResult, int i) {
        if (!zzc(connectionResult, i)) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(5, i, 0, connectionResult));
        }
    }

    public final void zza(GoogleApi<?> googleApi) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7, googleApi));
    }

    public final <O extends ApiOptions, TResult> void zza(GoogleApi<O> googleApi, int i, zzde<zzb, TResult> zzde, TaskCompletionSource<TResult> taskCompletionSource, zzda zzda) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, new zzcp(new zze(i, zzde, taskCompletionSource, zzda), this.zzfzm.get(), googleApi)));
    }

    public final <O extends ApiOptions> void zza(GoogleApi<O> googleApi, int i, zzm<? extends Result, zzb> zzm) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, new zzcp(new zzc(i, zzm), this.zzfzm.get(), googleApi)));
    }

    public final void zza(@NonNull zzah zzah) {
        synchronized (sLock) {
            if (this.zzfzn != zzah) {
                this.zzfzn = zzah;
                this.zzfzo.clear();
                this.zzfzo.addAll(zzah.zzajf());
            }
        }
    }

    final void zzaia() {
        this.zzfzm.incrementAndGet();
        this.mHandler.sendMessage(this.mHandler.obtainMessage(10));
    }

    public final void zzaih() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
    }

    public final int zzaka() {
        return this.zzfzl.getAndIncrement();
    }

    final void zzb(@NonNull zzah zzah) {
        synchronized (sLock) {
            if (this.zzfzn == zzah) {
                this.zzfzn = null;
                this.zzfzo.clear();
            }
        }
    }

    final boolean zzc(ConnectionResult connectionResult, int i) {
        return this.zzftg.zza(this.mContext, connectionResult, i);
    }
}
