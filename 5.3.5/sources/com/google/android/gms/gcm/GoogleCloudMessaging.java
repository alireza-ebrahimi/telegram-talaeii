package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.RequiresPermission;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.zzaa;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GoogleCloudMessaging {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String INSTANCE_ID_SCOPE = "GCM";
    @Deprecated
    public static final String MESSAGE_TYPE_DELETED = "deleted_messages";
    @Deprecated
    public static final String MESSAGE_TYPE_MESSAGE = "gcm";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_ERROR = "send_error";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_EVENT = "send_event";
    private static GoogleCloudMessaging zzika;
    private static final AtomicInteger zzikd = new AtomicInteger(1);
    private Context zzaiq;
    private PendingIntent zzikb;
    private final Map<String, Handler> zzikc = Collections.synchronizedMap(new ArrayMap());
    private final BlockingQueue<Intent> zzike = new LinkedBlockingQueue();
    private Messenger zzikf = new Messenger(new zzc(this, Looper.getMainLooper()));

    public static synchronized GoogleCloudMessaging getInstance(Context context) {
        GoogleCloudMessaging googleCloudMessaging;
        synchronized (GoogleCloudMessaging.class) {
            if (zzika == null) {
                googleCloudMessaging = new GoogleCloudMessaging();
                zzika = googleCloudMessaging;
                googleCloudMessaging.zzaiq = context.getApplicationContext();
            }
            googleCloudMessaging = zzika;
        }
        return googleCloudMessaging;
    }

    @Deprecated
    private final Intent zza(Bundle bundle, boolean z) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        } else if (zzdn(this.zzaiq) < 0) {
            throw new IOException("Google Play Services missing");
        } else {
            Intent intent = new Intent(z ? "com.google.iid.TOKEN_REQUEST" : "com.google.android.c2dm.intent.REGISTER");
            intent.setPackage(zzaa.zzdr(this.zzaiq));
            zzg(intent);
            intent.putExtra("google.message_id", "google.rpc" + zzikd.getAndIncrement());
            intent.putExtras(bundle);
            intent.putExtra("google.messenger", this.zzikf);
            if (z) {
                this.zzaiq.sendBroadcast(intent);
            } else {
                this.zzaiq.startService(intent);
            }
            try {
                return (Intent) this.zzike.poll(30000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    @Hide
    @Deprecated
    private final synchronized String zza(boolean z, String... strArr) throws IOException {
        String zzdr;
        zzdr = zzaa.zzdr(this.zzaiq);
        if (zzdr == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String zzd = zzd(strArr);
        Bundle bundle = new Bundle();
        if (zzdr.contains(".gsf")) {
            bundle.putString("legacy.sender", zzd);
            zzdr = InstanceID.getInstance(this.zzaiq).getToken(zzd, INSTANCE_ID_SCOPE, bundle);
        } else {
            bundle.putString("sender", zzd);
            Intent zza = zza(bundle, z);
            zzdr = "registration_id";
            if (zza == null) {
                throw new IOException("SERVICE_NOT_AVAILABLE");
            }
            zzdr = zza.getStringExtra(zzdr);
            if (zzdr == null) {
                zzdr = zza.getStringExtra("error");
                if (zzdr != null) {
                    throw new IOException(zzdr);
                }
                throw new IOException("SERVICE_NOT_AVAILABLE");
            }
        }
        return zzdr;
    }

    private final synchronized void zzawh() {
        if (this.zzikb != null) {
            this.zzikb.cancel();
            this.zzikb = null;
        }
    }

    private static String zzd(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException("No senderIds");
        }
        StringBuilder stringBuilder = new StringBuilder(strArr[0]);
        for (int i = 1; i < strArr.length; i++) {
            stringBuilder.append(',').append(strArr[i]);
        }
        return stringBuilder.toString();
    }

    @Hide
    public static int zzdn(Context context) {
        String zzdr = zzaa.zzdr(context);
        if (zzdr != null) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(zzdr, 0);
                if (packageInfo != null) {
                    return packageInfo.versionCode;
                }
            } catch (NameNotFoundException e) {
            }
        }
        return -1;
    }

    private final boolean zzf(Intent intent) {
        Object stringExtra = intent.getStringExtra("In-Reply-To");
        if (stringExtra == null && intent.hasExtra("error")) {
            stringExtra = intent.getStringExtra("google.message_id");
        }
        if (stringExtra != null) {
            Handler handler = (Handler) this.zzikc.remove(stringExtra);
            if (handler != null) {
                Message obtain = Message.obtain();
                obtain.obj = intent;
                return handler.sendMessage(obtain);
            }
        }
        return false;
    }

    private final synchronized void zzg(Intent intent) {
        if (this.zzikb == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.zzikb = PendingIntent.getBroadcast(this.zzaiq, 0, intent2, 0);
        }
        intent.putExtra(SettingsJsonConstants.APP_KEY, this.zzikb);
    }

    public void close() {
        zzika = null;
        zza.zzijk = null;
        zzawh();
    }

    public String getMessageType(Intent intent) {
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            return null;
        }
        String stringExtra = intent.getStringExtra("message_type");
        return stringExtra == null ? MESSAGE_TYPE_MESSAGE : stringExtra;
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    @Deprecated
    public synchronized String register(String... strArr) throws IOException {
        return zza(zzaa.zzdq(this.zzaiq), strArr);
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void send(String str, String str2, long j, Bundle bundle) throws IOException {
        if (str == null) {
            throw new IllegalArgumentException("Missing 'to'");
        }
        String zzdr = zzaa.zzdr(this.zzaiq);
        if (zzdr == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        Intent intent = new Intent("com.google.android.gcm.intent.SEND");
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        zzg(intent);
        intent.setPackage(zzdr);
        intent.putExtra("google.to", str);
        intent.putExtra("google.message_id", str2);
        intent.putExtra("google.ttl", Long.toString(j));
        String str3 = "google.from";
        int indexOf = str.indexOf(64);
        String substring = indexOf > 0 ? str.substring(0, indexOf) : str;
        InstanceID.getInstance(this.zzaiq);
        intent.putExtra(str3, InstanceID.zzawr().zzf("", substring, INSTANCE_ID_SCOPE));
        if (zzdr.contains(".gsf")) {
            Bundle bundle2 = new Bundle();
            for (String substring2 : bundle.keySet()) {
                Object obj = bundle.get(substring2);
                if (obj instanceof String) {
                    String str4 = "gcm.";
                    substring2 = String.valueOf(substring2);
                    bundle2.putString(substring2.length() != 0 ? str4.concat(substring2) : new String(str4), (String) obj);
                }
            }
            bundle2.putString("google.to", str);
            bundle2.putString("google.message_id", str2);
            InstanceID.getInstance(this.zzaiq).zzb(INSTANCE_ID_SCOPE, "upstream", bundle2);
            return;
        }
        this.zzaiq.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void send(String str, String str2, Bundle bundle) throws IOException {
        send(str, str2, -1, bundle);
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    @Deprecated
    public synchronized void unregister() throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        InstanceID.getInstance(this.zzaiq).deleteInstanceID();
    }
}
