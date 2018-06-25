package com.google.android.gms.gcm;

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.CallSuper;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;

public class Task implements ReflectedParcelable {
    public static final int EXTRAS_LIMIT_BYTES = 10240;
    public static final int NETWORK_STATE_ANY = 2;
    public static final int NETWORK_STATE_CONNECTED = 0;
    public static final int NETWORK_STATE_UNMETERED = 1;
    protected static final long UNINITIALIZED = -1;
    private final Bundle mExtras;
    private final String mTag;
    private final String zziks;
    private final boolean zzikt;
    private final boolean zziku;
    private final int zzikv;
    private final boolean zzikw;
    private final boolean zzikx;
    private final zzi zziky;

    public static abstract class Builder {
        protected Bundle extras;
        protected String gcmTaskService;
        protected boolean isPersisted;
        protected int requiredNetworkState;
        protected boolean requiresCharging;
        protected String tag;
        protected boolean updateCurrent;
        @Hide
        protected zzi zzikz = zzi.zzikn;

        public abstract Task build();

        @CallSuper
        protected void checkConditions() {
            zzbq.checkArgument(this.gcmTaskService != null, "Must provide an endpoint for this task by calling setService(ComponentName).");
            GcmNetworkManager.zzid(this.tag);
            zzi zzi = this.zzikz;
            if (zzi != null) {
                int zzawi = zzi.zzawi();
                if (zzawi == 1 || zzawi == 0) {
                    int zzawj = zzi.zzawj();
                    int zzawk = zzi.zzawk();
                    if (zzawi == 0 && zzawj < 0) {
                        throw new IllegalArgumentException("InitialBackoffSeconds can't be negative: " + zzawj);
                    } else if (zzawi == 1 && zzawj < 10) {
                        throw new IllegalArgumentException("RETRY_POLICY_LINEAR must have an initial backoff at least 10 seconds.");
                    } else if (zzawk < zzawj) {
                        throw new IllegalArgumentException("MaximumBackoffSeconds must be greater than InitialBackoffSeconds: " + zzi.zzawk());
                    }
                }
                throw new IllegalArgumentException("Must provide a valid RetryPolicy: " + zzawi);
            }
            if (this.isPersisted) {
                Task.zzw(this.extras);
            }
        }

        public abstract Builder setExtras(Bundle bundle);

        @RequiresPermission("android.permission.RECEIVE_BOOT_COMPLETED")
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
        this.zziks = parcel.readString();
        this.mTag = parcel.readString();
        this.zzikt = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.zziku = z;
        this.zzikv = 2;
        this.zzikw = false;
        this.zzikx = false;
        this.zziky = zzi.zzikn;
        this.mExtras = null;
    }

    Task(Builder builder) {
        this.zziks = builder.gcmTaskService;
        this.mTag = builder.tag;
        this.zzikt = builder.updateCurrent;
        this.zziku = builder.isPersisted;
        this.zzikv = builder.requiredNetworkState;
        this.zzikw = builder.requiresCharging;
        this.zzikx = false;
        this.mExtras = builder.extras;
        this.zziky = builder.zzikz != null ? builder.zzikz : zzi.zzikn;
    }

    @Hide
    public static void zzw(Bundle bundle) {
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
                        zzw((Bundle) obj);
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
        return this.mExtras;
    }

    public int getRequiredNetwork() {
        return this.zzikv;
    }

    public boolean getRequiresCharging() {
        return this.zzikw;
    }

    public String getServiceName() {
        return this.zziks;
    }

    public String getTag() {
        return this.mTag;
    }

    public boolean isPersisted() {
        return this.zziku;
    }

    public boolean isUpdateCurrent() {
        return this.zzikt;
    }

    @Hide
    public void toBundle(Bundle bundle) {
        bundle.putString("tag", this.mTag);
        bundle.putBoolean("update_current", this.zzikt);
        bundle.putBoolean("persisted", this.zziku);
        bundle.putString("service", this.zziks);
        bundle.putInt("requiredNetwork", this.zzikv);
        bundle.putBoolean("requiresCharging", this.zzikw);
        bundle.putBoolean("requiresIdle", false);
        bundle.putBundle("retryStrategy", this.zziky.zzv(new Bundle()));
        bundle.putBundle("extras", this.mExtras);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeString(this.zziks);
        parcel.writeString(this.mTag);
        parcel.writeInt(this.zzikt ? 1 : 0);
        if (!this.zziku) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
