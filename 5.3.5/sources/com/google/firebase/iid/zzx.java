package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.iid.zzi.zza;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class zzx {
    private static PendingIntent zzikb;
    private static int zzino = 0;
    private final Context zzaiq;
    private Messenger zzikf;
    private Messenger zziny;
    private final zzw zzokq;
    private final SimpleArrayMap<String, TaskCompletionSource<Bundle>> zzolg = new SimpleArrayMap();
    private zzi zzolh;

    public zzx(Context context, zzw zzw) {
        this.zzaiq = context;
        this.zzokq = zzw;
        this.zzikf = new Messenger(new zzy(this, Looper.getMainLooper()));
    }

    private final Bundle zzaa(Bundle bundle) throws IOException {
        String zzawx = zzawx();
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        synchronized (this.zzolg) {
            this.zzolg.put(zzawx, taskCompletionSource);
        }
        if (this.zzokq.zzcll() == 0) {
            throw new IOException(InstanceID.ERROR_MISSING_INSTANCEID_SERVICE);
        }
        Intent intent = new Intent();
        intent.setPackage("com.google.android.gms");
        if (this.zzokq.zzcll() == 2) {
            intent.setAction("com.google.iid.TOKEN_REQUEST");
        } else {
            intent.setAction("com.google.android.c2dm.intent.REGISTER");
        }
        intent.putExtras(bundle);
        zzd(this.zzaiq, intent);
        intent.putExtra("kid", new StringBuilder(String.valueOf(zzawx).length() + 5).append("|ID|").append(zzawx).append("|").toString());
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String valueOf = String.valueOf(intent.getExtras());
            Log.d("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 8).append("Sending ").append(valueOf).toString());
        }
        intent.putExtra("google.messenger", this.zzikf);
        if (!(this.zziny == null && this.zzolh == null)) {
            Message obtain = Message.obtain();
            obtain.obj = intent;
            try {
                if (this.zziny != null) {
                    this.zziny.send(obtain);
                } else {
                    this.zzolh.send(obtain);
                }
            } catch (RemoteException e) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    Log.d("FirebaseInstanceId", "Messenger failed, fallback to startService");
                }
            }
            Bundle bundle2 = (Bundle) Tasks.await(taskCompletionSource.getTask(), 30000, TimeUnit.MILLISECONDS);
            synchronized (this.zzolg) {
                this.zzolg.remove(zzawx);
            }
            return bundle2;
        }
        if (this.zzokq.zzcll() == 2) {
            this.zzaiq.sendBroadcast(intent);
        } else {
            this.zzaiq.startService(intent);
        }
        try {
            Bundle bundle22 = (Bundle) Tasks.await(taskCompletionSource.getTask(), 30000, TimeUnit.MILLISECONDS);
            synchronized (this.zzolg) {
                this.zzolg.remove(zzawx);
            }
            return bundle22;
        } catch (InterruptedException e2) {
            Log.w("FirebaseInstanceId", "No response");
            throw new IOException("TIMEOUT");
        } catch (TimeoutException e3) {
            Log.w("FirebaseInstanceId", "No response");
            throw new IOException("TIMEOUT");
        } catch (Throwable e4) {
            throw new IOException(e4);
        } catch (Throwable th) {
            synchronized (this.zzolg) {
                this.zzolg.remove(zzawx);
            }
        }
    }

    private static synchronized String zzawx() {
        String num;
        synchronized (zzx.class) {
            int i = zzino;
            zzino = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    @Hide
    private static synchronized void zzd(Context context, Intent intent) {
        synchronized (zzx.class) {
            if (zzikb == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                zzikb = PendingIntent.getBroadcast(context, 0, intent2, 0);
            }
            intent.putExtra(SettingsJsonConstants.APP_KEY, zzikb);
        }
    }

    private final void zze(Message message) {
        if (message == null || !(message.obj instanceof Intent)) {
            Log.w("FirebaseInstanceId", "Dropping invalid message");
            return;
        }
        Intent intent = (Intent) message.obj;
        intent.setExtrasClassLoader(new zza());
        if (intent.hasExtra("google.messenger")) {
            Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
            if (parcelableExtra instanceof zzi) {
                this.zzolh = (zzi) parcelableExtra;
            }
            if (parcelableExtra instanceof Messenger) {
                this.zziny = (Messenger) parcelableExtra;
            }
        }
        intent = (Intent) message.obj;
        String action = intent.getAction();
        String group;
        String str;
        String valueOf;
        if ("com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
            CharSequence stringExtra = intent.getStringExtra("registration_id");
            if (stringExtra == null) {
                stringExtra = intent.getStringExtra("unregistered");
            }
            if (stringExtra == null) {
                zzr(intent);
                return;
            }
            Matcher matcher = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.*)").matcher(stringExtra);
            if (matcher.matches()) {
                action = matcher.group(1);
                group = matcher.group(2);
                Bundle extras = intent.getExtras();
                extras.putString("registration_id", group);
                zzh(action, extras);
            } else if (Log.isLoggable("FirebaseInstanceId", 3)) {
                group = "FirebaseInstanceId";
                str = "Unexpected response string: ";
                valueOf = String.valueOf(stringExtra);
                Log.d(group, valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        } else if (Log.isLoggable("FirebaseInstanceId", 3)) {
            group = "FirebaseInstanceId";
            str = "Unexpected response action: ";
            valueOf = String.valueOf(action);
            Log.d(group, valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    private final void zzh(String str, Bundle bundle) {
        synchronized (this.zzolg) {
            TaskCompletionSource taskCompletionSource = (TaskCompletionSource) this.zzolg.remove(str);
            if (taskCompletionSource == null) {
                String str2 = "FirebaseInstanceId";
                String str3 = "Missing callback for ";
                String valueOf = String.valueOf(str);
                Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                return;
            }
            taskCompletionSource.setResult(bundle);
        }
    }

    private final void zzr(Intent intent) {
        String stringExtra = intent.getStringExtra("error");
        if (stringExtra == null) {
            stringExtra = String.valueOf(intent.getExtras());
            Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(stringExtra).length() + 49).append("Unexpected response, no error or registration id ").append(stringExtra).toString());
            return;
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String str = "FirebaseInstanceId";
            String str2 = "Received InstanceID error ";
            String valueOf = String.valueOf(stringExtra);
            Log.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
        if (stringExtra.startsWith("|")) {
            String[] split = stringExtra.split("\\|");
            if (split.length <= 2 || !"ID".equals(split[1])) {
                str = "FirebaseInstanceId";
                str2 = "Unexpected structured response ";
                valueOf = String.valueOf(stringExtra);
                Log.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                return;
            }
            stringExtra = split[2];
            valueOf = split[3];
            if (valueOf.startsWith(":")) {
                valueOf = valueOf.substring(1);
            }
            zzh(stringExtra, intent.putExtra("error", valueOf).getExtras());
            return;
        }
        synchronized (this.zzolg) {
            for (int i = 0; i < this.zzolg.size(); i++) {
                zzh((String) this.zzolg.keyAt(i), intent.getExtras());
            }
        }
    }

    private final Bundle zzz(Bundle bundle) throws IOException {
        Bundle zzaa = zzaa(bundle);
        if (zzaa == null || !zzaa.containsKey("google.messenger")) {
            return zzaa;
        }
        zzaa = zzaa(bundle);
        return (zzaa == null || !zzaa.containsKey("google.messenger")) ? zzaa : null;
    }

    final Bundle zzah(Bundle bundle) throws IOException {
        Exception e;
        if (this.zzokq.zzclo() < 12000000) {
            return zzz(bundle);
        }
        try {
            return (Bundle) Tasks.await(zzk.zzfa(this.zzaiq).zzj(1, bundle));
        } catch (InterruptedException e2) {
            e = e2;
        } catch (ExecutionException e3) {
            e = e3;
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String valueOf = String.valueOf(e);
            Log.d("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 22).append("Error making request: ").append(valueOf).toString());
        }
        return ((e.getCause() instanceof zzu) && ((zzu) e.getCause()).getErrorCode() == 4) ? zzz(bundle) : null;
    }
}
