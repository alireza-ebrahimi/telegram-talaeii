package com.google.android.gms.gcm;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.iid.InstanceID;
import java.util.regex.Pattern;

@Deprecated
public class GcmPubSub {
    private static GcmPubSub zzn;
    private static final Pattern zzp = Pattern.compile("/topics/[a-zA-Z0-9-_.~%]{1,900}");
    private InstanceID zzo;

    private GcmPubSub(Context context) {
        this.zzo = InstanceID.getInstance(context);
    }

    @Deprecated
    public static synchronized GcmPubSub getInstance(Context context) {
        GcmPubSub gcmPubSub;
        synchronized (GcmPubSub.class) {
            if (zzn == null) {
                GoogleCloudMessaging.zze(context);
                zzn = new GcmPubSub(context);
            }
            gcmPubSub = zzn;
        }
        return gcmPubSub;
    }

    @Deprecated
    public void subscribe(String str, String str2, Bundle bundle) {
        String str3;
        String valueOf;
        if (str == null || str.isEmpty()) {
            str3 = "Invalid appInstanceToken: ";
            valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        } else if (str2 == null || !zzp.matcher(str2).matches()) {
            str3 = "Invalid topic name: ";
            valueOf = String.valueOf(str2);
            throw new IllegalArgumentException(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        } else {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("gcm.topic", str2);
            this.zzo.getToken(str, str2, bundle);
        }
    }

    @Deprecated
    public void unsubscribe(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("gcm.topic", str2);
        this.zzo.zzd(str, str2, bundle);
    }
}
