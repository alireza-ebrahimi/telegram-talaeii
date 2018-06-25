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
import android.support.v4.p022f.C0464a;
import android.util.Log;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.zzaf;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Deprecated
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
    private static GoogleCloudMessaging zzac;
    private static final AtomicInteger zzaf = new AtomicInteger(1);
    private PendingIntent zzad;
    private final Map<String, Handler> zzae = Collections.synchronizedMap(new C0464a());
    private final BlockingQueue<Intent> zzag = new LinkedBlockingQueue();
    private final Messenger zzah = new Messenger(new zzf(this, Looper.getMainLooper()));
    private Context zzk;

    @Deprecated
    public static synchronized GoogleCloudMessaging getInstance(Context context) {
        GoogleCloudMessaging googleCloudMessaging;
        synchronized (GoogleCloudMessaging.class) {
            if (zzac == null) {
                zze(context);
                googleCloudMessaging = new GoogleCloudMessaging();
                zzac = googleCloudMessaging;
                googleCloudMessaging.zzk = context.getApplicationContext();
            }
            googleCloudMessaging = zzac;
        }
        return googleCloudMessaging;
    }

    @Deprecated
    private final Intent zzd(Bundle bundle, boolean z) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        } else if (zzf(this.zzk) < 0) {
            throw new IOException("Google Play Services missing");
        } else {
            Intent intent = new Intent(z ? "com.google.iid.TOKEN_REQUEST" : "com.google.android.c2dm.intent.REGISTER");
            intent.setPackage(zzaf.zzl(this.zzk));
            zze(intent);
            intent.putExtra("google.message_id", "google.rpc" + zzaf.getAndIncrement());
            intent.putExtras(bundle);
            intent.putExtra("google.messenger", this.zzah);
            if (z) {
                this.zzk.sendBroadcast(intent);
            } else {
                this.zzk.startService(intent);
            }
            try {
                return (Intent) this.zzag.poll(30000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    @Deprecated
    @VisibleForTesting
    private final synchronized String zzd(boolean z, String... strArr) {
        String stringBuilder;
        String zzl = zzaf.zzl(this.zzk);
        if (zzl == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        if (strArr != null) {
            if (strArr.length != 0) {
                StringBuilder stringBuilder2 = new StringBuilder(strArr[0]);
                for (int i = 1; i < strArr.length; i++) {
                    stringBuilder2.append(',').append(strArr[i]);
                }
                stringBuilder = stringBuilder2.toString();
                Bundle bundle = new Bundle();
                if (zzl.contains(".gsf")) {
                    bundle.putString("legacy.sender", stringBuilder);
                    stringBuilder = InstanceID.getInstance(this.zzk).getToken(stringBuilder, "GCM", bundle);
                } else {
                    bundle.putString("sender", stringBuilder);
                    Intent zzd = zzd(bundle, z);
                    stringBuilder = "registration_id";
                    if (zzd == null) {
                        throw new IOException("SERVICE_NOT_AVAILABLE");
                    }
                    stringBuilder = zzd.getStringExtra(stringBuilder);
                    if (stringBuilder == null) {
                        stringBuilder = zzd.getStringExtra("error");
                        if (stringBuilder != null) {
                            throw new IOException(stringBuilder);
                        }
                        throw new IOException("SERVICE_NOT_AVAILABLE");
                    }
                }
            }
        }
        throw new IllegalArgumentException("No senderIds");
        return stringBuilder;
    }

    private final boolean zzd(Intent intent) {
        Object stringExtra = intent.getStringExtra("In-Reply-To");
        if (stringExtra == null && intent.hasExtra("error")) {
            stringExtra = intent.getStringExtra("google.message_id");
        }
        if (stringExtra != null) {
            Handler handler = (Handler) this.zzae.remove(stringExtra);
            if (handler != null) {
                Message obtain = Message.obtain();
                obtain.obj = intent;
                return handler.sendMessage(obtain);
            }
        }
        return false;
    }

    static void zze(Context context) {
        String packageName = context.getPackageName();
        Log.w("GCM", new StringBuilder(String.valueOf(packageName).length() + 48).append("GCM SDK is deprecated, ").append(packageName).append(" should update to use FCM").toString());
    }

    private final synchronized void zze(Intent intent) {
        if (this.zzad == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.zzad = PendingIntent.getBroadcast(this.zzk, 0, intent2, 0);
        }
        intent.putExtra("app", this.zzad);
    }

    public static int zzf(Context context) {
        String zzl = zzaf.zzl(context);
        if (zzl != null) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(zzl, 0);
                if (packageInfo != null) {
                    return packageInfo.versionCode;
                }
            } catch (NameNotFoundException e) {
            }
        }
        return -1;
    }

    private final synchronized void zzg() {
        if (this.zzad != null) {
            this.zzad.cancel();
            this.zzad = null;
        }
    }

    @Deprecated
    public void close() {
        zzac = null;
        zzd.zzj = null;
        zzg();
    }

    @Deprecated
    public String getMessageType(Intent intent) {
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            return null;
        }
        String stringExtra = intent.getStringExtra("message_type");
        return stringExtra == null ? MESSAGE_TYPE_MESSAGE : stringExtra;
    }

    @Deprecated
    public synchronized String register(String... strArr) {
        return zzd(zzaf.zzk(this.zzk), strArr);
    }

    @Deprecated
    public void send(String str, String str2, long j, Bundle bundle) {
        if (str == null) {
            throw new IllegalArgumentException("Missing 'to'");
        }
        String zzl = zzaf.zzl(this.zzk);
        if (zzl == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        Intent intent = new Intent("com.google.android.gcm.intent.SEND");
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        zze(intent);
        intent.setPackage(zzl);
        intent.putExtra("google.to", str);
        intent.putExtra("google.message_id", str2);
        intent.putExtra("google.ttl", Long.toString(j));
        String str3 = "google.from";
        int indexOf = str.indexOf(64);
        String substring = indexOf > 0 ? str.substring(0, indexOf) : str;
        InstanceID.getInstance(this.zzk);
        intent.putExtra(str3, InstanceID.zzn().zze(TtmlNode.ANONYMOUS_REGION_ID, substring, "GCM"));
        if (zzl.contains(".gsf")) {
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
            InstanceID.getInstance(this.zzk).zze("GCM", "upstream", bundle2);
            return;
        }
        this.zzk.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
    }

    @Deprecated
    public void send(String str, String str2, Bundle bundle) {
        send(str, str2, -1, bundle);
    }

    @Deprecated
    public synchronized void unregister() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        InstanceID.getInstance(this.zzk).deleteInstanceID();
    }
}
