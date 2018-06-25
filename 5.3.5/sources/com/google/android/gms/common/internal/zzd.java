package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zzf;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;

public abstract class zzd<T extends IInterface> {
    @Hide
    private static String[] zzgfi = new String[]{"service_esmobile", "service_googleme"};
    private final Context mContext;
    final Handler mHandler;
    private final Object mLock;
    private final Looper zzalj;
    private final zzf zzfwk;
    private int zzgen;
    private long zzgeo;
    private long zzgep;
    private int zzgeq;
    private long zzger;
    private zzam zzges;
    private final zzag zzget;
    private final Object zzgeu;
    private zzay zzgev;
    protected zzj zzgew;
    private T zzgex;
    private final ArrayList<zzi<?>> zzgey;
    private zzl zzgez;
    private int zzgfa;
    private final zzf zzgfb;
    private final zzg zzgfc;
    private final int zzgfd;
    private final String zzgfe;
    private ConnectionResult zzgff;
    private boolean zzgfg;
    protected AtomicInteger zzgfh;

    protected zzd(Context context, Looper looper, int i, zzf zzf, zzg zzg, String str) {
        this(context, looper, zzag.zzcp(context), zzf.zzahf(), i, (zzf) zzbq.checkNotNull(zzf), (zzg) zzbq.checkNotNull(zzg), null);
    }

    protected zzd(Context context, Looper looper, zzag zzag, zzf zzf, int i, zzf zzf2, zzg zzg, String str) {
        this.mLock = new Object();
        this.zzgeu = new Object();
        this.zzgey = new ArrayList();
        this.zzgfa = 1;
        this.zzgff = null;
        this.zzgfg = false;
        this.zzgfh = new AtomicInteger(0);
        this.mContext = (Context) zzbq.checkNotNull(context, "Context must not be null");
        this.zzalj = (Looper) zzbq.checkNotNull(looper, "Looper must not be null");
        this.zzget = (zzag) zzbq.checkNotNull(zzag, "Supervisor must not be null");
        this.zzfwk = (zzf) zzbq.checkNotNull(zzf, "API availability must not be null");
        this.mHandler = new zzh(this, looper);
        this.zzgfd = i;
        this.zzgfb = zzf2;
        this.zzgfc = zzg;
        this.zzgfe = str;
    }

    private final void zza(int i, T t) {
        boolean z = true;
        if ((i == 4) != (t != null)) {
            z = false;
        }
        zzbq.checkArgument(z);
        synchronized (this.mLock) {
            this.zzgfa = i;
            this.zzgex = t;
            zzb(i, t);
            switch (i) {
                case 1:
                    if (this.zzgez != null) {
                        this.zzget.zza(zzhm(), zzalq(), TsExtractor.TS_STREAM_TYPE_AC3, this.zzgez, zzalr());
                        this.zzgez = null;
                        break;
                    }
                    break;
                case 2:
                case 3:
                    String zzamx;
                    String packageName;
                    if (!(this.zzgez == null || this.zzges == null)) {
                        zzamx = this.zzges.zzamx();
                        packageName = this.zzges.getPackageName();
                        Log.e("GmsClient", new StringBuilder((String.valueOf(zzamx).length() + 70) + String.valueOf(packageName).length()).append("Calling connect() while still connected, missing disconnect() for ").append(zzamx).append(" on ").append(packageName).toString());
                        this.zzget.zza(this.zzges.zzamx(), this.zzges.getPackageName(), this.zzges.zzamu(), this.zzgez, zzalr());
                        this.zzgfh.incrementAndGet();
                    }
                    this.zzgez = new zzl(this, this.zzgfh.get());
                    this.zzges = new zzam(zzalq(), zzhm(), false, TsExtractor.TS_STREAM_TYPE_AC3);
                    if (!this.zzget.zza(new zzah(this.zzges.zzamx(), this.zzges.getPackageName(), this.zzges.zzamu()), this.zzgez, zzalr())) {
                        zzamx = this.zzges.zzamx();
                        packageName = this.zzges.getPackageName();
                        Log.e("GmsClient", new StringBuilder((String.valueOf(zzamx).length() + 34) + String.valueOf(packageName).length()).append("unable to connect to service: ").append(zzamx).append(" on ").append(packageName).toString());
                        zza(16, null, this.zzgfh.get());
                        break;
                    }
                    break;
                case 4:
                    zza((IInterface) t);
                    break;
            }
        }
    }

    private final boolean zza(int i, int i2, T t) {
        boolean z;
        synchronized (this.mLock) {
            if (this.zzgfa != i) {
                z = false;
            } else {
                zza(i2, (IInterface) t);
                z = true;
            }
        }
        return z;
    }

    @Nullable
    @Hide
    private final String zzalr() {
        return this.zzgfe == null ? this.mContext.getClass().getName() : this.zzgfe;
    }

    @Hide
    private final boolean zzalt() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzgfa == 3;
        }
        return z;
    }

    private final boolean zzalz() {
        if (this.zzgfg || TextUtils.isEmpty(zzhn()) || TextUtils.isEmpty(null)) {
            return false;
        }
        try {
            Class.forName(zzhn());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Hide
    private final void zzce(int i) {
        int i2;
        if (zzalt()) {
            i2 = 5;
            this.zzgfg = true;
        } else {
            i2 = 4;
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(i2, this.zzgfh.get(), 16));
    }

    public void disconnect() {
        this.zzgfh.incrementAndGet();
        synchronized (this.zzgey) {
            int size = this.zzgey.size();
            for (int i = 0; i < size; i++) {
                ((zzi) this.zzgey.get(i)).removeListener();
            }
            this.zzgey.clear();
        }
        synchronized (this.zzgeu) {
            this.zzgev = null;
        }
        zza(1, null);
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (this.mLock) {
            int i = this.zzgfa;
            IInterface iInterface = this.zzgex;
        }
        synchronized (this.zzgeu) {
            zzay zzay = this.zzgev;
        }
        printWriter.append(str).append("mConnectState=");
        switch (i) {
            case 1:
                printWriter.print("DISCONNECTED");
                break;
            case 2:
                printWriter.print("REMOTE_CONNECTING");
                break;
            case 3:
                printWriter.print("LOCAL_CONNECTING");
                break;
            case 4:
                printWriter.print("CONNECTED");
                break;
            case 5:
                printWriter.print("DISCONNECTING");
                break;
            default:
                printWriter.print("UNKNOWN");
                break;
        }
        printWriter.append(" mService=");
        if (iInterface == null) {
            printWriter.append("null");
        } else {
            printWriter.append(zzhn()).append("@").append(Integer.toHexString(System.identityHashCode(iInterface.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (zzay == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(zzay.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzgep > 0) {
            PrintWriter append = printWriter.append(str).append("lastConnectedTime=");
            long j = this.zzgep;
            String format = simpleDateFormat.format(new Date(this.zzgep));
            append.println(new StringBuilder(String.valueOf(format).length() + 21).append(j).append(" ").append(format).toString());
        }
        if (this.zzgeo > 0) {
            printWriter.append(str).append("lastSuspendedCause=");
            switch (this.zzgen) {
                case 1:
                    printWriter.append("CAUSE_SERVICE_DISCONNECTED");
                    break;
                case 2:
                    printWriter.append("CAUSE_NETWORK_LOST");
                    break;
                default:
                    printWriter.append(String.valueOf(this.zzgen));
                    break;
            }
            append = printWriter.append(" lastSuspendedTime=");
            j = this.zzgeo;
            format = simpleDateFormat.format(new Date(this.zzgeo));
            append.println(new StringBuilder(String.valueOf(format).length() + 21).append(j).append(" ").append(format).toString());
        }
        if (this.zzger > 0) {
            printWriter.append(str).append("lastFailedStatus=").append(CommonStatusCodes.getStatusCodeString(this.zzgeq));
            append = printWriter.append(" lastFailedTime=");
            j = this.zzger;
            String format2 = simpleDateFormat.format(new Date(this.zzger));
            append.println(new StringBuilder(String.valueOf(format2).length() + 21).append(j).append(" ").append(format2).toString());
        }
    }

    public Account getAccount() {
        return null;
    }

    @Hide
    public final Context getContext() {
        return this.mContext;
    }

    @Hide
    public final Looper getLooper() {
        return this.zzalj;
    }

    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    public final boolean isConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzgfa == 4;
        }
        return z;
    }

    public final boolean isConnecting() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzgfa == 2 || this.zzgfa == 3;
        }
        return z;
    }

    @CallSuper
    protected void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzgeq = connectionResult.getErrorCode();
        this.zzger = System.currentTimeMillis();
    }

    @CallSuper
    protected void onConnectionSuspended(int i) {
        this.zzgen = i;
        this.zzgeo = System.currentTimeMillis();
    }

    @Hide
    protected final void zza(int i, @Nullable Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7, i2, -1, new zzo(this, i, null)));
    }

    protected void zza(int i, IBinder iBinder, Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, i2, -1, new zzn(this, i, iBinder, bundle)));
    }

    @CallSuper
    protected void zza(@NonNull T t) {
        this.zzgep = System.currentTimeMillis();
    }

    @WorkerThread
    @Hide
    public final void zza(zzan zzan, Set<Scope> set) {
        Throwable e;
        Bundle zzabt = zzabt();
        zzz zzz = new zzz(this.zzgfd);
        zzz.zzggd = this.mContext.getPackageName();
        zzz.zzggg = zzabt;
        if (set != null) {
            zzz.zzggf = (Scope[]) set.toArray(new Scope[set.size()]);
        }
        if (zzacc()) {
            zzz.zzggh = getAccount() != null ? getAccount() : new Account("<<default account>>", "com.google");
            if (zzan != null) {
                zzz.zzgge = zzan.asBinder();
            }
        } else if (zzalx()) {
            zzz.zzggh = getAccount();
        }
        zzz.zzggi = zzalu();
        try {
            synchronized (this.zzgeu) {
                if (this.zzgev != null) {
                    this.zzgev.zza(new zzk(this, this.zzgfh.get()), zzz);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (Throwable e2) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e2);
            zzcd(1);
        } catch (SecurityException e3) {
            throw e3;
        } catch (RemoteException e4) {
            e2 = e4;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e2);
            zza(8, null, null, this.zzgfh.get());
        } catch (RuntimeException e5) {
            e2 = e5;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e2);
            zza(8, null, null, this.zzgfh.get());
        }
    }

    public void zza(@NonNull zzj zzj) {
        this.zzgew = (zzj) zzbq.checkNotNull(zzj, "Connection progress callbacks cannot be null.");
        zza(2, null);
    }

    protected final void zza(@NonNull zzj zzj, int i, @Nullable PendingIntent pendingIntent) {
        this.zzgew = (zzj) zzbq.checkNotNull(zzj, "Connection progress callbacks cannot be null.");
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzgfh.get(), i, pendingIntent));
    }

    public void zza(@NonNull zzp zzp) {
        zzp.zzako();
    }

    @Hide
    protected Bundle zzabt() {
        return new Bundle();
    }

    public boolean zzacc() {
        return false;
    }

    public boolean zzacn() {
        return false;
    }

    public Bundle zzagp() {
        return null;
    }

    public boolean zzahn() {
        return true;
    }

    @Nullable
    public final IBinder zzaho() {
        IBinder iBinder;
        synchronized (this.zzgeu) {
            if (this.zzgev == null) {
                iBinder = null;
            } else {
                iBinder = this.zzgev.asBinder();
            }
        }
        return iBinder;
    }

    @Hide
    public final String zzahp() {
        if (isConnected() && this.zzges != null) {
            return this.zzges.getPackageName();
        }
        throw new RuntimeException("Failed to connect when checking package");
    }

    @Hide
    protected String zzalq() {
        return "com.google.android.gms";
    }

    public final void zzals() {
        int isGooglePlayServicesAvailable = this.zzfwk.isGooglePlayServicesAvailable(this.mContext);
        if (isGooglePlayServicesAvailable != 0) {
            zza(1, null);
            zza(new zzm(this), isGooglePlayServicesAvailable, null);
            return;
        }
        zza(new zzm(this));
    }

    public zzc[] zzalu() {
        return new zzc[0];
    }

    @Hide
    protected final void zzalv() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    @Hide
    public final T zzalw() throws DeadObjectException {
        T t;
        synchronized (this.mLock) {
            if (this.zzgfa == 5) {
                throw new DeadObjectException();
            }
            zzalv();
            zzbq.zza(this.zzgex != null, (Object) "Client is connected but service is null");
            t = this.zzgex;
        }
        return t;
    }

    public boolean zzalx() {
        return false;
    }

    protected Set<Scope> zzaly() {
        return Collections.EMPTY_SET;
    }

    void zzb(int i, T t) {
    }

    @Hide
    public final void zzcd(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(6, this.zzgfh.get(), i));
    }

    @Nullable
    @Hide
    protected abstract T zzd(IBinder iBinder);

    @Hide
    @NonNull
    protected abstract String zzhm();

    @Hide
    @NonNull
    protected abstract String zzhn();
}
