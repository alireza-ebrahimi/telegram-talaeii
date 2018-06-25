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
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.tasks.Tasks;
import com.thin.downloadmanager.BuildConfig;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Hide
public final class zzaa {
    private static int zzino = 0;
    private static zzae<Boolean> zzinr = zzad.zzawy().zzf("gcm_iid_use_messenger_ipc", true);
    private static String zzins = null;
    private static boolean zzint = false;
    private static int zzinu = 0;
    private static int zzinv = 0;
    private static BroadcastReceiver zzinw = null;
    private Context zzaiq;
    private PendingIntent zzikb;
    private Messenger zzikf;
    private Map<String, Object> zzinx = new ArrayMap();
    private Messenger zziny;
    private MessengerCompat zzinz;

    public zzaa(Context context) {
        this.zzaiq = context;
    }

    private static String zza(KeyPair keyPair, String... strArr) {
        String str = null;
        try {
            byte[] bytes = TextUtils.join(LogCollector.LINE_SEPARATOR, strArr).getBytes("UTF-8");
            try {
                PrivateKey privateKey = keyPair.getPrivate();
                Signature instance = Signature.getInstance(privateKey instanceof RSAPrivateKey ? "SHA256withRSA" : "SHA256withECDSA");
                instance.initSign(privateKey);
                instance.update(bytes);
                str = InstanceID.zzp(instance.sign());
            } catch (Throwable e) {
                Log.e("InstanceID/Rpc", "Unable to sign registration request", e);
            }
        } catch (Throwable e2) {
            Log.e("InstanceID/Rpc", "Unable to encode string", e2);
        }
        return str;
    }

    private static boolean zza(PackageManager packageManager) {
        for (ResolveInfo resolveInfo : packageManager.queryBroadcastReceivers(new Intent("com.google.iid.TOKEN_REQUEST"), 0)) {
            if (zza(packageManager, resolveInfo.activityInfo.packageName, "com.google.iid.TOKEN_REQUEST")) {
                zzint = true;
                return true;
            }
        }
        return false;
    }

    private static boolean zza(PackageManager packageManager, String str, String str2) {
        if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", str) == 0) {
            return zzb(packageManager, str);
        }
        Log.w("InstanceID/Rpc", new StringBuilder((String.valueOf(str).length() + 56) + String.valueOf(str2).length()).append("Possible malicious package ").append(str).append(" declares ").append(str2).append(" without permission").toString());
        return false;
    }

    private final Bundle zzaa(Bundle bundle) throws IOException {
        Bundle bundle2;
        ConditionVariable conditionVariable = new ConditionVariable();
        String zzawx = zzawx();
        synchronized (getClass()) {
            this.zzinx.put(zzawx, conditionVariable);
        }
        zzf(bundle, zzawx);
        conditionVariable.block(30000);
        synchronized (getClass()) {
            Object remove = this.zzinx.remove(zzawx);
            if (remove instanceof Bundle) {
                bundle2 = (Bundle) remove;
            } else if (remove instanceof String) {
                throw new IOException((String) remove);
            } else {
                String valueOf = String.valueOf(remove);
                Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 12).append("No response ").append(valueOf).toString());
                throw new IOException("TIMEOUT");
            }
        }
        return bundle2;
    }

    private final void zzae(Object obj) {
        synchronized (getClass()) {
            for (String str : this.zzinx.keySet()) {
                Object obj2 = this.zzinx.get(str);
                this.zzinx.put(str, obj);
                zze(obj2, obj);
            }
        }
    }

    private static synchronized String zzawx() {
        String num;
        synchronized (zzaa.class) {
            int i = zzino;
            zzino = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    private static boolean zzb(PackageManager packageManager, String str) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            zzins = applicationInfo.packageName;
            zzinv = applicationInfo.uid;
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean zzdq(Context context) {
        if (zzins != null) {
            zzdr(context);
        }
        return zzint;
    }

    @Hide
    public static String zzdr(Context context) {
        if (zzins != null) {
            return zzins;
        }
        zzinu = Process.myUid();
        PackageManager packageManager = context.getPackageManager();
        if (!zzs.isAtLeastO()) {
            boolean z;
            for (ResolveInfo resolveInfo : packageManager.queryIntentServices(new Intent("com.google.android.c2dm.intent.REGISTER"), 0)) {
                if (zza(packageManager, resolveInfo.serviceInfo.packageName, "com.google.android.c2dm.intent.REGISTER")) {
                    zzint = false;
                    z = true;
                    break;
                }
            }
            z = false;
            if (z) {
                return zzins;
            }
        }
        if (zza(packageManager)) {
            return zzins;
        }
        Log.w("InstanceID/Rpc", "Failed to resolve IID implementation package, falling back");
        if (zzb(packageManager, "com.google.android.gms")) {
            zzint = zzs.isAtLeastO();
            return zzins;
        } else if (zzs.zzanx() || !zzb(packageManager, "com.google.android.gsf")) {
            Log.w("InstanceID/Rpc", "Google Play services is missing, unable to get tokens");
            return null;
        } else {
            zzint = false;
            return zzins;
        }
    }

    private static int zzds(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(zzdr(context), 0).versionCode;
        } catch (NameNotFoundException e) {
            return -1;
        }
    }

    private static void zze(Object obj, Object obj2) {
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
                Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 24).append("Failed to send response ").append(valueOf).toString());
            }
        }
    }

    private final void zzf(Bundle bundle, String str) throws IOException {
        if (this.zzikf == null) {
            zzdr(this.zzaiq);
            this.zzikf = new Messenger(new zzab(this, Looper.getMainLooper()));
        }
        if (zzins == null) {
            throw new IOException(InstanceID.ERROR_MISSING_INSTANCEID_SERVICE);
        }
        Intent intent = new Intent(zzint ? "com.google.iid.TOKEN_REQUEST" : "com.google.android.c2dm.intent.REGISTER");
        intent.setPackage(zzins);
        intent.putExtras(bundle);
        zzi(intent);
        intent.putExtra("kid", new StringBuilder(String.valueOf(str).length() + 5).append("|ID|").append(str).append("|").toString());
        intent.putExtra("X-kid", new StringBuilder(String.valueOf(str).length() + 5).append("|ID|").append(str).append("|").toString());
        boolean equals = "com.google.android.gsf".equals(zzins);
        String stringExtra = intent.getStringExtra("useGsf");
        if (stringExtra != null) {
            equals = BuildConfig.VERSION_NAME.equals(stringExtra);
        }
        if (Log.isLoggable("InstanceID/Rpc", 3)) {
            String valueOf = String.valueOf(intent.getExtras());
            Log.d("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 8).append("Sending ").append(valueOf).toString());
        }
        if (this.zziny != null) {
            intent.putExtra("google.messenger", this.zzikf);
            Message obtain = Message.obtain();
            obtain.obj = intent;
            try {
                this.zziny.send(obtain);
                return;
            } catch (RemoteException e) {
                if (Log.isLoggable("InstanceID/Rpc", 3)) {
                    Log.d("InstanceID/Rpc", "Messenger failed, fallback to startService");
                }
            }
        }
        if (equals) {
            synchronized (this) {
                if (zzinw == null) {
                    zzinw = new zzac(this);
                    if (Log.isLoggable("InstanceID/Rpc", 3)) {
                        Log.d("InstanceID/Rpc", "Registered GSF callback receiver");
                    }
                    IntentFilter intentFilter = new IntentFilter("com.google.android.c2dm.intent.REGISTRATION");
                    intentFilter.addCategory(this.zzaiq.getPackageName());
                    this.zzaiq.registerReceiver(zzinw, intentFilter, "com.google.android.c2dm.permission.SEND", null);
                }
            }
            this.zzaiq.sendBroadcast(intent);
            return;
        }
        intent.putExtra("google.messenger", this.zzikf);
        intent.putExtra("messenger2", BuildConfig.VERSION_NAME);
        if (this.zzinz != null) {
            Message obtain2 = Message.obtain();
            obtain2.obj = intent;
            try {
                this.zzinz.send(obtain2);
                return;
            } catch (RemoteException e2) {
                if (Log.isLoggable("InstanceID/Rpc", 3)) {
                    Log.d("InstanceID/Rpc", "Messenger failed, fallback to startService");
                }
            }
        }
        if (zzint) {
            this.zzaiq.sendBroadcast(intent);
        } else {
            this.zzaiq.startService(intent);
        }
    }

    private final synchronized void zzi(Intent intent) {
        if (this.zzikb == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.zzikb = PendingIntent.getBroadcast(this.zzaiq, 0, intent2, 0);
        }
        intent.putExtra(SettingsJsonConstants.APP_KEY, this.zzikb);
    }

    private final void zzi(String str, Object obj) {
        synchronized (getClass()) {
            Object obj2 = this.zzinx.get(str);
            this.zzinx.put(str, obj);
            zze(obj2, obj);
        }
    }

    static String zzy(Bundle bundle) throws IOException {
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
        Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 29).append("Unexpected response from GCM ").append(valueOf).toString(), new Throwable());
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    private final Bundle zzz(Bundle bundle) throws IOException {
        Bundle zzaa = zzaa(bundle);
        if (zzaa == null || !zzaa.containsKey("google.messenger")) {
            return zzaa;
        }
        zzaa = zzaa(bundle);
        return (zzaa == null || !zzaa.containsKey("google.messenger")) ? zzaa : null;
    }

    final Bundle zza(Bundle bundle, KeyPair keyPair) throws IOException {
        Exception e;
        int zzds = zzds(this.zzaiq);
        bundle.putString("gmsv", Integer.toString(zzds));
        bundle.putString("osv", Integer.toString(VERSION.SDK_INT));
        bundle.putString("app_ver", Integer.toString(InstanceID.zzdo(this.zzaiq)));
        bundle.putString("app_ver_name", InstanceID.zzdp(this.zzaiq));
        bundle.putString("cliv", "iid-12211000");
        bundle.putString("appid", InstanceID.zza(keyPair));
        bundle.putString("pub2", InstanceID.zzp(keyPair.getPublic().getEncoded()));
        bundle.putString("sig", zza(keyPair, this.zzaiq.getPackageName(), r1));
        if (zzds < 12000000 || !((Boolean) zzinr.get()).booleanValue()) {
            return zzz(bundle);
        }
        try {
            return (Bundle) Tasks.await(new zzm(this.zzaiq).zzj(1, bundle));
        } catch (InterruptedException e2) {
            e = e2;
        } catch (ExecutionException e3) {
            e = e3;
        }
        if (Log.isLoggable("InstanceID/Rpc", 3)) {
            String valueOf = String.valueOf(e);
            Log.d("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 22).append("Error making request: ").append(valueOf).toString());
        }
        return ((e.getCause() instanceof zzv) && ((zzv) e.getCause()).getErrorCode() == 4) ? zzz(bundle) : null;
    }

    public final void zzd(Message message) {
        if (message != null) {
            if (message.obj instanceof Intent) {
                Intent intent = (Intent) message.obj;
                intent.setExtrasClassLoader(MessengerCompat.class.getClassLoader());
                if (intent.hasExtra("google.messenger")) {
                    Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
                    if (parcelableExtra instanceof MessengerCompat) {
                        this.zzinz = (MessengerCompat) parcelableExtra;
                    }
                    if (parcelableExtra instanceof Messenger) {
                        this.zziny = (Messenger) parcelableExtra;
                    }
                }
                zzj((Intent) message.obj);
                return;
            }
            Log.w("InstanceID/Rpc", "Dropping invalid message");
        }
    }

    @Hide
    public final void zzj(Intent intent) {
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
                        Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 49).append("Unexpected response, no error or registration id ").append(valueOf).toString());
                        return;
                    }
                    Object obj;
                    if (Log.isLoggable("InstanceID/Rpc", 3)) {
                        valueOf = "InstanceID/Rpc";
                        String str = "Received InstanceID error ";
                        action = String.valueOf(stringExtra);
                        Log.d(valueOf, action.length() != 0 ? str.concat(action) : new String(str));
                    }
                    if (stringExtra.startsWith("|")) {
                        String[] split = stringExtra.split("\\|");
                        if (!"ID".equals(split[1])) {
                            String str2 = "InstanceID/Rpc";
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
                        zzae(obj);
                        return;
                    } else {
                        zzi(action, obj);
                        return;
                    }
                }
                Matcher matcher = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.+)").matcher(stringExtra2);
                if (matcher.matches()) {
                    action = matcher.group(1);
                    valueOf = matcher.group(2);
                    Bundle extras = intent.getExtras();
                    extras.putString("registration_id", valueOf);
                    zzi(action, extras);
                } else if (Log.isLoggable("InstanceID/Rpc", 3)) {
                    valueOf = "InstanceID/Rpc";
                    stringExtra = "Unexpected response string: ";
                    action = String.valueOf(stringExtra2);
                    Log.d(valueOf, action.length() != 0 ? stringExtra.concat(action) : new String(stringExtra));
                }
            } else if (Log.isLoggable("InstanceID/Rpc", 3)) {
                valueOf = "InstanceID/Rpc";
                stringExtra = "Unexpected response ";
                action = String.valueOf(intent.getAction());
                Log.d(valueOf, action.length() != 0 ? stringExtra.concat(action) : new String(stringExtra));
            }
        } else if (Log.isLoggable("InstanceID/Rpc", 3)) {
            Log.d("InstanceID/Rpc", "Unexpected response: null");
        }
    }
}
