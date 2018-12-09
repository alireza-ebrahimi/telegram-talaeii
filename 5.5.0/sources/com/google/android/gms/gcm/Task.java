package com.google.android.gms.gcm;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class Task implements ReflectedParcelable {
    public static final int EXTRAS_LIMIT_BYTES = 10240;
    public static final int NETWORK_STATE_ANY = 2;
    public static final int NETWORK_STATE_CONNECTED = 0;
    public static final int NETWORK_STATE_UNMETERED = 1;
    protected static final long UNINITIALIZED = -1;
    private final Bundle extras;
    private final String gcmTaskService;
    private final boolean isPersisted;
    private final int requiredNetworkState;
    private final boolean requiresCharging;
    private final String tag;
    private final boolean updateCurrent;
    private final Set<Uri> zzau;
    private final boolean zzav;
    private final zzl zzaw;

    public static abstract class Builder {
        protected Bundle extras;
        protected String gcmTaskService;
        protected boolean isPersisted;
        protected int requiredNetworkState;
        protected boolean requiresCharging;
        protected String tag;
        protected boolean updateCurrent;
        protected Set<Uri> zzau = Collections.emptySet();
        protected zzl zzaw = zzl.zzao;

        public abstract Task build();

        protected void checkConditions() {
            Preconditions.checkArgument(this.gcmTaskService != null, "Must provide an endpoint for this task by calling setService(ComponentName).");
            GcmNetworkManager.zzd(this.tag);
            zzl zzl = this.zzaw;
            if (zzl != null) {
                int zzh = zzl.zzh();
                if (zzh == 1 || zzh == 0) {
                    int zzi = zzl.zzi();
                    int zzj = zzl.zzj();
                    if (zzh == 0 && zzi < 0) {
                        throw new IllegalArgumentException("InitialBackoffSeconds can't be negative: " + zzi);
                    } else if (zzh == 1 && zzi < 10) {
                        throw new IllegalArgumentException("RETRY_POLICY_LINEAR must have an initial backoff at least 10 seconds.");
                    } else if (zzj < zzi) {
                        throw new IllegalArgumentException("MaximumBackoffSeconds must be greater than InitialBackoffSeconds: " + zzl.zzj());
                    }
                }
                throw new IllegalArgumentException("Must provide a valid RetryPolicy: " + zzh);
            }
            if (this.isPersisted) {
                Task.zzg(this.extras);
            }
            if (this.zzau.isEmpty() || this.requiredNetworkState != 2) {
                for (Uri zze : this.zzau) {
                    Task.zzd(zze);
                }
                return;
            }
            throw new IllegalArgumentException("Required URIs may not be used with NETWORK_STATE_ANY");
        }

        public abstract Builder setExtras(Bundle bundle);

        public abstract Builder setPersisted(boolean z);

        public abstract Builder setRequiredNetwork(int i);

        public abstract Builder setRequiresCharging(boolean z);

        public abstract Builder setService(Class<? extends GcmTaskService> cls);

        public abstract Builder setTag(String str);

        public abstract Builder setUpdateCurrent(boolean z);
    }

    @Deprecated
    Task(Parcel parcel) {
        boolean z = true;
        Log.e("Task", "Constructing a Task object using a parcel.");
        this.gcmTaskService = parcel.readString();
        this.tag = parcel.readString();
        this.updateCurrent = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.isPersisted = z;
        this.requiredNetworkState = 2;
        this.zzau = Collections.emptySet();
        this.requiresCharging = false;
        this.zzav = false;
        this.zzaw = zzl.zzao;
        this.extras = null;
    }

    Task(Builder builder) {
        this.gcmTaskService = builder.gcmTaskService;
        this.tag = builder.tag;
        this.updateCurrent = builder.updateCurrent;
        this.isPersisted = builder.isPersisted;
        this.requiredNetworkState = builder.requiredNetworkState;
        this.zzau = builder.zzau;
        this.requiresCharging = builder.requiresCharging;
        this.zzav = false;
        this.extras = builder.extras;
        this.zzaw = builder.zzaw != null ? builder.zzaw : zzl.zzao;
    }

    private static void zzd(Uri uri) {
        String str;
        if (uri == null) {
            throw new IllegalArgumentException("Null URI");
        }
        String scheme = uri.getScheme();
        CharSequence host = uri.getHost();
        if (TextUtils.isEmpty(host) || "null".equals(host)) {
            throw new IllegalArgumentException("URI hostname is required");
        }
        try {
            int port = uri.getPort();
            if ("tcp".equals(scheme)) {
                if (port <= 0 || port > 65535) {
                    throw new IllegalArgumentException("Invalid required URI port: " + uri.getPort());
                }
            } else if (!"ping".equals(scheme)) {
                str = "Unsupported required URI scheme: ";
                scheme = String.valueOf(scheme);
                throw new IllegalArgumentException(scheme.length() != 0 ? str.concat(scheme) : new String(str));
            } else if (port != -1) {
                throw new IllegalArgumentException("Ping does not support port numbers");
            }
        } catch (NumberFormatException e) {
            str = "Invalid port number: ";
            scheme = String.valueOf(e.getMessage());
            throw new IllegalArgumentException(scheme.length() != 0 ? str.concat(scheme) : new String(str));
        }
    }

    public static void zzg(Bundle bundle) {
        if (bundle != null) {
            Parcel obtain = Parcel.obtain();
            bundle.writeToParcel(obtain, 0);
            int dataSize = obtain.dataSize();
            obtain.recycle();
            if (dataSize > EXTRAS_LIMIT_BYTES) {
                throw new IllegalArgumentException("Extras exceeding maximum size(10240 bytes): " + dataSize);
            }
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                dataSize = ((obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Double) || (obj instanceof String) || (obj instanceof Boolean)) ? 1 : 0;
                if (dataSize == 0) {
                    if (obj instanceof Bundle) {
                        zzg((Bundle) obj);
                    } else {
                        throw new IllegalArgumentException("Only the following extra parameter types are supported: Integer, Long, Double, String, Boolean, and nested Bundles with the same restrictions.");
                    }
                }
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public Bundle getExtras() {
        return this.extras;
    }

    public int getRequiredNetwork() {
        return this.requiredNetworkState;
    }

    public boolean getRequiresCharging() {
        return this.requiresCharging;
    }

    public String getServiceName() {
        return this.gcmTaskService;
    }

    public String getTag() {
        return this.tag;
    }

    public boolean isPersisted() {
        return this.isPersisted;
    }

    public boolean isUpdateCurrent() {
        return this.updateCurrent;
    }

    public void toBundle(Bundle bundle) {
        bundle.putString("tag", this.tag);
        bundle.putBoolean("update_current", this.updateCurrent);
        bundle.putBoolean("persisted", this.isPersisted);
        bundle.putString("service", this.gcmTaskService);
        bundle.putInt("requiredNetwork", this.requiredNetworkState);
        if (!this.zzau.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            for (Uri uri : this.zzau) {
                arrayList.add(uri.toString());
            }
            bundle.putStringArrayList("reachabilityUris", arrayList);
        }
        bundle.putBoolean("requiresCharging", this.requiresCharging);
        bundle.putBoolean("requiresIdle", false);
        bundle.putBundle("retryStrategy", this.zzaw.zzf(new Bundle()));
        bundle.putBundle("extras", this.extras);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeString(this.gcmTaskService);
        parcel.writeString(this.tag);
        parcel.writeInt(this.updateCurrent ? 1 : 0);
        if (!this.isPersisted) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
