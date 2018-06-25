package com.google.android.gms.iid;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.support.v4.p022f.C0464a;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.tasks.Tasks;
import java.io.IOException;
import java.security.KeyPair;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class zzaf {
    private static int zzck = 0;
    private static final zzaj<Boolean> zzco = zzai.zzw().zzd("gcm_iid_use_messenger_ipc", true);
    private static String zzcp = null;
    private static boolean zzcq = false;
    private static int zzcr = 0;
    private static int zzcs = 0;
    private static BroadcastReceiver zzct = null;
    private PendingIntent zzad;
    private Messenger zzah;
    private Map<String, Object> zzcu = new C0464a();
    private Messenger zzcv;
    private MessengerCompat zzcw;
    private Context zzk;

    public zzaf(Context context) {
        this.zzk = context;
    }

    private static void zzd(Object obj, Object obj2) {
        if (obj instanceof ConditionVariable) {
            ((ConditionVariable) obj).open();
        }
        if (obj instanceof Messenger) {
            Messenger messenger = (Messenger) obj;
            Message obtain = Message.obtain();
            obtain.obj = obj2;
            try {
                messenger.send(obtain);
            } catch (RemoteException e) {
                String valueOf = String.valueOf(e);
                Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 24).append("Failed to send response ").append(valueOf).toString());
            }
        }
    }

    private final void zzd(String str, Object obj) {
        synchronized (getClass()) {
            Object obj2 = this.zzcu.get(str);
            this.zzcu.put(str, obj);
            zzd(obj2, obj);
        }
    }

    private static boolean zzd(PackageManager packageManager, String str) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            zzcp = applicationInfo.packageName;
            zzcs = applicationInfo.uid;
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    private static boolean zzd(PackageManager packageManager, String str, String str2) {
        if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", str) == 0) {
            return zzd(packageManager, str);
        }
        Log.w("InstanceID", new StringBuilder((String.valueOf(str).length() + 56) + String.valueOf(str2).length()).append("Possible malicious package ").append(str).append(" declares ").append(str2).append(" without permission").toString());
        return false;
    }

    private final synchronized void zzg(Intent intent) {
        if (this.zzad == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.zzad = PendingIntent.getBroadcast(this.zzk, 0, intent2, 0);
        }
        intent.putExtra("app", this.zzad);
    }

    static String zzi(Bundle bundle) {
        if (bundle == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String string = bundle.getString("registration_id");
        if (string == null) {
            string = bundle.getString("unregistered");
        }
        if (string != null) {
            return string;
        }
        string = bundle.getString("error");
        if (string != null) {
            throw new IOException(string);
        }
        String valueOf = String.valueOf(bundle);
        Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 29).append("Unexpected response from GCM ").append(valueOf).toString(), new Throwable());
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    private final Bundle zzj(Bundle bundle) {
        Bundle zzk = zzk(bundle);
        if (zzk == null || !zzk.containsKey("google.messenger")) {
            return zzk;
        }
        zzk = zzk(bundle);
        return (zzk == null || !zzk.containsKey("google.messenger")) ? zzk : null;
    }

    private final Bundle zzk(Bundle bundle) {
        ConditionVariable conditionVariable = new ConditionVariable();
        String zzv = zzv();
        synchronized (getClass()) {
            this.zzcu.put(zzv, conditionVariable);
        }
        if (this.zzah == null) {
            zzl(this.zzk);
            this.zzah = new Messenger(new zzag(this, Looper.getMainLooper()));
        }
        if (zzcp == null) {
            throw new IOException(InstanceID.ERROR_MISSING_INSTANCEID_SERVICE);
        }
        Object remove;
        Bundle bundle2;
        Intent intent = new Intent(zzcq ? "com.google.iid.TOKEN_REQUEST" : "com.google.android.c2dm.intent.REGISTER");
        intent.setPackage(zzcp);
        intent.putExtras(bundle);
        zzg(intent);
        intent.putExtra("kid", new StringBuilder(String.valueOf(zzv).length() + 5).append("|ID|").append(zzv).append("|").toString());
        intent.putExtra("X-kid", new StringBuilder(String.valueOf(zzv).length() + 5).append("|ID|").append(zzv).append("|").toString());
        boolean equals = "com.google.android.gsf".equals(zzcp);
        String stringExtra = intent.getStringExtra("useGsf");
        if (stringExtra != null) {
            equals = "1".equals(stringExtra);
        }
        if (Log.isLoggable("InstanceID", 3)) {
            String valueOf = String.valueOf(intent.getExtras());
            Log.d("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 8).append("Sending ").append(valueOf).toString());
        }
        if (this.zzcv != null) {
            intent.putExtra("google.messenger", this.zzah);
            Message obtain = Message.obtain();
            obtain.obj = intent;
            try {
                this.zzcv.send(obtain);
            } catch (RemoteException e) {
                if (Log.isLoggable("InstanceID", 3)) {
                    Log.d("InstanceID", "Messenger failed, fallback to startService");
                }
            }
            conditionVariable.block(30000);
            synchronized (getClass()) {
                remove = this.zzcu.remove(zzv);
                if (remove instanceof Bundle) {
                    bundle2 = (Bundle) remove;
                } else if (remove instanceof String) {
                    String valueOf2 = String.valueOf(remove);
                    Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf2).length() + 12).append("No response ").append(valueOf2).toString());
                    throw new IOException(InstanceID.ERROR_TIMEOUT);
                } else {
                    throw new IOException((String) remove);
                }
            }
            return bundle2;
        }
        if (equals) {
            synchronized (this) {
                if (zzct == null) {
                    zzct = new zzah(this);
                    if (Log.isLoggable("InstanceID", 3)) {
                        Log.d("InstanceID", "Registered GSF callback receiver");
                    }
                    IntentFilter intentFilter = new IntentFilter("com.google.android.c2dm.intent.REGISTRATION");
                    intentFilter.addCategory(this.zzk.getPackageName());
                    this.zzk.registerReceiver(zzct, intentFilter, "com.google.android.c2dm.permission.SEND", null);
                }
            }
            this.zzk.sendBroadcast(intent);
        } else {
            intent.putExtra("google.messenger", this.zzah);
            intent.putExtra("messenger2", "1");
            if (this.zzcw != null) {
                Message obtain2 = Message.obtain();
                obtain2.obj = intent;
                try {
                    this.zzcw.send(obtain2);
                } catch (RemoteException e2) {
                    if (Log.isLoggable("InstanceID", 3)) {
                        Log.d("InstanceID", "Messenger failed, fallback to startService");
                    }
                }
            }
            if (zzcq) {
                this.zzk.sendBroadcast(intent);
            } else {
                this.zzk.startService(intent);
            }
        }
        conditionVariable.block(30000);
        synchronized (getClass()) {
            remove = this.zzcu.remove(zzv);
            if (remove instanceof Bundle) {
                bundle2 = (Bundle) remove;
            } else if (remove instanceof String) {
                String valueOf22 = String.valueOf(remove);
                Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf22).length() + 12).append("No response ").append(valueOf22).toString());
                throw new IOException(InstanceID.ERROR_TIMEOUT);
            } else {
                throw new IOException((String) remove);
            }
        }
        return bundle2;
    }

    public static boolean zzk(Context context) {
        if (zzcp != null) {
            zzl(context);
        }
        return zzcq;
    }

    public static String zzl(Context context) {
        if (zzcp != null) {
            return zzcp;
        }
        boolean z;
        zzcr = Process.myUid();
        PackageManager packageManager = context.getPackageManager();
        if (!PlatformVersion.isAtLeastO()) {
            for (ResolveInfo resolveInfo : packageManager.queryIntentServices(new Intent("com.google.android.c2dm.intent.REGISTER"), 0)) {
                if (zzd(packageManager, resolveInfo.serviceInfo.packageName, "com.google.android.c2dm.intent.REGISTER")) {
                    zzcq = false;
                    z = true;
                    break;
                }
            }
            z = false;
            if (z) {
                return zzcp;
            }
        }
        for (ResolveInfo resolveInfo2 : packageManager.queryBroadcastReceivers(new Intent("com.google.iid.TOKEN_REQUEST"), 0)) {
            if (zzd(packageManager, resolveInfo2.activityInfo.packageName, "com.google.iid.TOKEN_REQUEST")) {
                zzcq = true;
                z = true;
                break;
            }
        }
        z = false;
        if (z) {
            return zzcp;
        }
        Log.w("InstanceID", "Failed to resolve IID implementation package, falling back");
        if (zzd(packageManager, "com.google.android.gms")) {
            zzcq = PlatformVersion.isAtLeastO();
            return zzcp;
        } else if (PlatformVersion.isAtLeastLollipop() || !zzd(packageManager, "com.google.android.gsf")) {
            Log.w("InstanceID", "Google Play services is missing, unable to get tokens");
            return null;
        } else {
            zzcq = false;
            return zzcp;
        }
    }

    private static int zzm(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(zzl(context), 0).versionCode;
        } catch (NameNotFoundException e) {
            return -1;
        }
    }

    private static synchronized String zzv() {
        String num;
        synchronized (zzaf.class) {
            int i = zzck;
            zzck = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    final Bundle zzd(Bundle bundle, KeyPair keyPair) {
        Exception e;
        int zzm = zzm(this.zzk);
        bundle.putString("gmsv", Integer.toString(zzm));
        bundle.putString("osv", Integer.toString(VERSION.SDK_INT));
        bundle.putString("app_ver", Integer.toString(InstanceID.zzg(this.zzk)));
        bundle.putString("app_ver_name", InstanceID.zzh(this.zzk));
        bundle.putString("cliv", "iid-12451000");
        bundle.putString("appid", InstanceID.zzd(keyPair));
        if (zzm < 12000000 || !((Boolean) zzco.get()).booleanValue()) {
            return zzj(bundle);
        }
        try {
            return (Bundle) Tasks.await(new zzr(this.zzk).zzd(1, bundle));
        } catch (InterruptedException e2) {
            e = e2;
        } catch (ExecutionException e3) {
            e = e3;
        }
        if (Log.isLoggable("InstanceID", 3)) {
            String valueOf = String.valueOf(e);
            Log.d("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 22).append("Error making request: ").append(valueOf).toString());
        }
        return ((e.getCause() instanceof zzaa) && ((zzaa) e.getCause()).getErrorCode() == 4) ? zzj(bundle) : null;
    }

    public final void zze(Message message) {
        if (message != null) {
            if (message.obj instanceof Intent) {
                Intent intent = (Intent) message.obj;
                intent.setExtrasClassLoader(MessengerCompat.class.getClassLoader());
                if (intent.hasExtra("google.messenger")) {
                    Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
                    if (parcelableExtra instanceof MessengerCompat) {
                        this.zzcw = (MessengerCompat) parcelableExtra;
                    }
                    if (parcelableExtra instanceof Messenger) {
                        this.zzcv = (Messenger) parcelableExtra;
                    }
                }
                zzh((Intent) message.obj);
                return;
            }
            Log.w("InstanceID", "Dropping invalid message");
        }
    }

    public final void zzh(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            String stringExtra;
            String valueOf;
            if ("com.google.android.c2dm.intent.REGISTRATION".equals(action) || "com.google.android.gms.iid.InstanceID".equals(action)) {
                CharSequence stringExtra2 = intent.getStringExtra("registration_id");
                if (stringExtra2 == null) {
                    stringExtra2 = intent.getStringExtra("unregistered");
                }
                if (stringExtra2 == null) {
                    stringExtra = intent.getStringExtra("error");
                    if (stringExtra == null) {
                        valueOf = String.valueOf(intent.getExtras());
                        Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 49).append("Unexpected response, no error or registration id ").append(valueOf).toString());
                        return;
                    }
                    Object obj;
                    if (Log.isLoggable("InstanceID", 3)) {
                        valueOf = "InstanceID";
                        String str = "Received InstanceID error ";
                        action = String.valueOf(stringExtra);
                        Log.d(valueOf, action.length() != 0 ? str.concat(action) : new String(str));
                    }
                    if (stringExtra.startsWith("|")) {
                        String[] split = stringExtra.split("\\|");
                        if (!"ID".equals(split[1])) {
                            String str2 = "InstanceID";
                            String str3 = "Unexpected structured response ";
                            action = String.valueOf(stringExtra);
                            Log.w(str2, action.length() != 0 ? str3.concat(action) : new String(str3));
                        }
                        if (split.length > 2) {
                            action = split[2];
                            obj = split[3];
                            if (obj.startsWith(":")) {
                                obj = obj.substring(1);
                            }
                        } else {
                            valueOf = "UNKNOWN";
                            action = null;
                        }
                        intent.putExtra("error", obj);
                    } else {
                        action = null;
                        valueOf = stringExtra;
                    }
                    if (action == null) {
                        synchronized (getClass()) {
                            for (String action2 : this.zzcu.keySet()) {
                                Object obj2 = this.zzcu.get(action2);
                                this.zzcu.put(action2, obj);
                                zzd(obj2, obj);
                            }
                        }
                        return;
                    }
                    zzd(action2, obj);
                    return;
                }
                Matcher matcher = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.*)").matcher(stringExtra2);
                if (matcher.matches()) {
                    action2 = matcher.group(1);
                    valueOf = matcher.group(2);
                    Object extras = intent.getExtras();
                    extras.putString("registration_id", valueOf);
                    zzd(action2, extras);
                } else if (Log.isLoggable("InstanceID", 3)) {
                    valueOf = "InstanceID";
                    stringExtra = "Unexpected response string: ";
                    action2 = String.valueOf(stringExtra2);
                    Log.d(valueOf, action2.length() != 0 ? stringExtra.concat(action2) : new String(stringExtra));
                }
            } else if (Log.isLoggable("InstanceID", 3)) {
                valueOf = "InstanceID";
                stringExtra = "Unexpected response ";
                action2 = String.valueOf(intent.getAction());
                Log.d(valueOf, action2.length() != 0 ? stringExtra.concat(action2) : new String(stringExtra));
            }
        } else if (Log.isLoggable("InstanceID", 3)) {
            Log.d("InstanceID", "Unexpected response: null");
        }
    }
}
