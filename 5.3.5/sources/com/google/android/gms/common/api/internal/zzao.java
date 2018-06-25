package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzbt;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;
import com.google.android.gms.internal.zzcyw;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

public final class zzao implements zzbh {
    private final Context mContext;
    private final zza<? extends zzcyj, zzcyk> zzfth;
    private final Lock zzfwa;
    private final zzr zzfwf;
    private final Map<Api<?>, Boolean> zzfwi;
    private final zzf zzfwk;
    private ConnectionResult zzfwt;
    private final zzbi zzfxd;
    private int zzfxg;
    private int zzfxh = 0;
    private int zzfxi;
    private final Bundle zzfxj = new Bundle();
    private final Set<zzc> zzfxk = new HashSet();
    private zzcyj zzfxl;
    private boolean zzfxm;
    private boolean zzfxn;
    private boolean zzfxo;
    private zzan zzfxp;
    private boolean zzfxq;
    private boolean zzfxr;
    private ArrayList<Future<?>> zzfxs = new ArrayList();

    public zzao(zzbi zzbi, zzr zzr, Map<Api<?>, Boolean> map, zzf zzf, zza<? extends zzcyj, zzcyk> zza, Lock lock, Context context) {
        this.zzfxd = zzbi;
        this.zzfwf = zzr;
        this.zzfwi = map;
        this.zzfwk = zzf;
        this.zzfth = zza;
        this.zzfwa = lock;
        this.mContext = context;
    }

    private final void zza(zzcyw zzcyw) {
        if (zzbs(0)) {
            ConnectionResult zzain = zzcyw.zzain();
            if (zzain.isSuccess()) {
                zzbt zzbfa = zzcyw.zzbfa();
                ConnectionResult zzain2 = zzbfa.zzain();
                if (zzain2.isSuccess()) {
                    this.zzfxo = true;
                    this.zzfxp = zzbfa.zzamy();
                    this.zzfxq = zzbfa.zzamz();
                    this.zzfxr = zzbfa.zzana();
                    zzajl();
                    return;
                }
                String valueOf = String.valueOf(zzain2);
                Log.wtf("GoogleApiClientConnecting", new StringBuilder(String.valueOf(valueOf).length() + 48).append("Sign-in succeeded with resolve account failure: ").append(valueOf).toString(), new Exception());
                zze(zzain2);
            } else if (zzd(zzain)) {
                zzajn();
                zzajl();
            } else {
                zze(zzain);
            }
        }
    }

    private final boolean zzajk() {
        this.zzfxi--;
        if (this.zzfxi > 0) {
            return false;
        }
        if (this.zzfxi < 0) {
            Log.w("GoogleApiClientConnecting", this.zzfxd.zzfvq.zzaju());
            Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            zze(new ConnectionResult(8, null));
            return false;
        } else if (this.zzfwt == null) {
            return true;
        } else {
            this.zzfxd.zzfzb = this.zzfxg;
            zze(this.zzfwt);
            return false;
        }
    }

    private final void zzajl() {
        if (this.zzfxi == 0) {
            if (!this.zzfxn || this.zzfxo) {
                ArrayList arrayList = new ArrayList();
                this.zzfxh = 1;
                this.zzfxi = this.zzfxd.zzfyj.size();
                for (zzc zzc : this.zzfxd.zzfyj.keySet()) {
                    if (!this.zzfxd.zzfyy.containsKey(zzc)) {
                        arrayList.add((zze) this.zzfxd.zzfyj.get(zzc));
                    } else if (zzajk()) {
                        zzajm();
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.zzfxs.add(zzbl.zzajx().submit(new zzau(this, arrayList)));
                }
            }
        }
    }

    private final void zzajm() {
        this.zzfxd.zzajw();
        zzbl.zzajx().execute(new zzap(this));
        if (this.zzfxl != null) {
            if (this.zzfxq) {
                this.zzfxl.zza(this.zzfxp, this.zzfxr);
            }
            zzbk(false);
        }
        for (zzc zzc : this.zzfxd.zzfyy.keySet()) {
            ((zze) this.zzfxd.zzfyj.get(zzc)).disconnect();
        }
        this.zzfxd.zzfzc.zzk(this.zzfxj.isEmpty() ? null : this.zzfxj);
    }

    private final void zzajn() {
        this.zzfxn = false;
        this.zzfxd.zzfvq.zzfyk = Collections.emptySet();
        for (zzc zzc : this.zzfxk) {
            if (!this.zzfxd.zzfyy.containsKey(zzc)) {
                this.zzfxd.zzfyy.put(zzc, new ConnectionResult(17, null));
            }
        }
    }

    private final void zzajo() {
        ArrayList arrayList = this.zzfxs;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Future) obj).cancel(true);
        }
        this.zzfxs.clear();
    }

    private final Set<Scope> zzajp() {
        if (this.zzfwf == null) {
            return Collections.emptySet();
        }
        Set<Scope> hashSet = new HashSet(this.zzfwf.zzamf());
        Map zzamh = this.zzfwf.zzamh();
        for (Api api : zzamh.keySet()) {
            if (!this.zzfxd.zzfyy.containsKey(api.zzahm())) {
                hashSet.addAll(((zzt) zzamh.get(api)).zzenh);
            }
        }
        return hashSet;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzb(com.google.android.gms.common.ConnectionResult r6, com.google.android.gms.common.api.Api<?> r7, boolean r8) {
        /*
        r5 = this;
        r1 = 0;
        r0 = 1;
        r2 = r7.zzahk();
        r3 = r2.getPriority();
        if (r8 == 0) goto L_0x0015;
    L_0x000c:
        r2 = r6.hasResolution();
        if (r2 == 0) goto L_0x002f;
    L_0x0012:
        r2 = r0;
    L_0x0013:
        if (r2 == 0) goto L_0x003f;
    L_0x0015:
        r2 = r5.zzfwt;
        if (r2 == 0) goto L_0x001d;
    L_0x0019:
        r2 = r5.zzfxg;
        if (r3 >= r2) goto L_0x003f;
    L_0x001d:
        if (r0 == 0) goto L_0x0023;
    L_0x001f:
        r5.zzfwt = r6;
        r5.zzfxg = r3;
    L_0x0023:
        r0 = r5.zzfxd;
        r0 = r0.zzfyy;
        r1 = r7.zzahm();
        r0.put(r1, r6);
        return;
    L_0x002f:
        r2 = r5.zzfwk;
        r4 = r6.getErrorCode();
        r2 = r2.zzbo(r4);
        if (r2 == 0) goto L_0x003d;
    L_0x003b:
        r2 = r0;
        goto L_0x0013;
    L_0x003d:
        r2 = r1;
        goto L_0x0013;
    L_0x003f:
        r0 = r1;
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzao.zzb(com.google.android.gms.common.ConnectionResult, com.google.android.gms.common.api.Api, boolean):void");
    }

    private final void zzbk(boolean z) {
        if (this.zzfxl != null) {
            if (this.zzfxl.isConnected() && z) {
                this.zzfxl.zzbet();
            }
            this.zzfxl.disconnect();
            this.zzfxp = null;
        }
    }

    private final boolean zzbs(int i) {
        if (this.zzfxh == i) {
            return true;
        }
        Log.w("GoogleApiClientConnecting", this.zzfxd.zzfvq.zzaju());
        String valueOf = String.valueOf(this);
        Log.w("GoogleApiClientConnecting", new StringBuilder(String.valueOf(valueOf).length() + 23).append("Unexpected callback in ").append(valueOf).toString());
        Log.w("GoogleApiClientConnecting", "mRemainingConnections=" + this.zzfxi);
        valueOf = zzbt(this.zzfxh);
        String zzbt = zzbt(i);
        Log.wtf("GoogleApiClientConnecting", new StringBuilder((String.valueOf(valueOf).length() + 70) + String.valueOf(zzbt).length()).append("GoogleApiClient connecting is in step ").append(valueOf).append(" but received callback for step ").append(zzbt).toString(), new Exception());
        zze(new ConnectionResult(8, null));
        return false;
    }

    private static String zzbt(int i) {
        switch (i) {
            case 0:
                return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
            case 1:
                return "STEP_GETTING_REMOTE_SERVICE";
            default:
                return "UNKNOWN";
        }
    }

    private final boolean zzd(ConnectionResult connectionResult) {
        return this.zzfxm && !connectionResult.hasResolution();
    }

    private final void zze(ConnectionResult connectionResult) {
        zzajo();
        zzbk(!connectionResult.hasResolution());
        this.zzfxd.zzg(connectionResult);
        this.zzfxd.zzfzc.zzc(connectionResult);
    }

    public final void begin() {
        this.zzfxd.zzfyy.clear();
        this.zzfxn = false;
        this.zzfwt = null;
        this.zzfxh = 0;
        this.zzfxm = true;
        this.zzfxo = false;
        this.zzfxq = false;
        Map hashMap = new HashMap();
        int i = 0;
        for (Api api : this.zzfwi.keySet()) {
            zze zze = (zze) this.zzfxd.zzfyj.get(api.zzahm());
            int i2 = (api.zzahk().getPriority() == 1 ? 1 : 0) | i;
            boolean booleanValue = ((Boolean) this.zzfwi.get(api)).booleanValue();
            if (zze.zzacc()) {
                this.zzfxn = true;
                if (booleanValue) {
                    this.zzfxk.add(api.zzahm());
                } else {
                    this.zzfxm = false;
                }
            }
            hashMap.put(zze, new zzaq(this, api, booleanValue));
            i = i2;
        }
        if (i != 0) {
            this.zzfxn = false;
        }
        if (this.zzfxn) {
            this.zzfwf.zzc(Integer.valueOf(System.identityHashCode(this.zzfxd.zzfvq)));
            ConnectionCallbacks zzax = new zzax();
            this.zzfxl = (zzcyj) this.zzfth.zza(this.mContext, this.zzfxd.zzfvq.getLooper(), this.zzfwf, this.zzfwf.zzaml(), zzax, zzax);
        }
        this.zzfxi = this.zzfxd.zzfyj.size();
        this.zzfxs.add(zzbl.zzajx().submit(new zzar(this, hashMap)));
    }

    public final void connect() {
    }

    public final boolean disconnect() {
        zzajo();
        zzbk(true);
        this.zzfxd.zzg(null);
        return true;
    }

    public final void onConnected(Bundle bundle) {
        if (zzbs(1)) {
            if (bundle != null) {
                this.zzfxj.putAll(bundle);
            }
            if (zzajk()) {
                zzajm();
            }
        }
    }

    public final void onConnectionSuspended(int i) {
        zze(new ConnectionResult(8, null));
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (zzbs(1)) {
            zzb(connectionResult, api, z);
            if (zzajk()) {
                zzajm();
            }
        }
    }

    public final <A extends zzb, R extends Result, T extends zzm<R, A>> T zzd(T t) {
        this.zzfxd.zzfvq.zzfwo.add(t);
        return t;
    }

    public final <A extends zzb, T extends zzm<? extends Result, A>> T zze(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}
