package com.google.android.gms.wearable;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.util.Preconditions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.zzd;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.wearable.internal.zzaa;
import com.google.android.gms.wearable.internal.zzaj;
import com.google.android.gms.wearable.internal.zzao;
import com.google.android.gms.wearable.internal.zzbv;
import com.google.android.gms.wearable.internal.zzbw;
import com.google.android.gms.wearable.internal.zzcj;
import com.google.android.gms.wearable.internal.zzeu;
import com.google.android.gms.wearable.internal.zzez;
import com.google.android.gms.wearable.internal.zzfg;
import com.google.android.gms.wearable.internal.zzfl;
import com.google.android.gms.wearable.internal.zzgi;
import com.google.android.gms.wearable.internal.zzh;
import com.google.android.gms.wearable.internal.zzhg;
import com.google.android.gms.wearable.internal.zzhq;
import com.google.android.gms.wearable.internal.zzk;
import com.google.android.gms.wearable.internal.zzo;

public class Wearable {
    @Deprecated
    public static final Api<WearableOptions> API = new Api("Wearable.API", zzegv, zzegu);
    @Deprecated
    public static final CapabilityApi CapabilityApi = new zzo();
    @Deprecated
    public static final ChannelApi ChannelApi = new zzaj();
    @Deprecated
    public static final DataApi DataApi = new zzbw();
    @Deprecated
    public static final MessageApi MessageApi = new zzeu();
    @Deprecated
    public static final NodeApi NodeApi = new zzfg();
    private static final zzf<zzhg> zzegu = new zzf();
    private static final zza<zzhg, WearableOptions> zzegv = new zzj();
    @Hide
    @Deprecated
    private static zzc zzlrb = new zzk();
    @Hide
    @Deprecated
    private static zza zzlrc = new zzh();
    @Hide
    @Deprecated
    private static zzf zzlrd = new zzbv();
    @Hide
    @Deprecated
    private static zzi zzlre = new zzgi();
    @Hide
    @Deprecated
    private static zzu zzlrf = new zzhq();

    public static final class WearableOptions implements Optional {
        private final Looper zzfst;

        public static class Builder {
            private Looper zzfst;

            public WearableOptions build() {
                return new WearableOptions();
            }

            public Builder setLooper(Looper looper) {
                this.zzfst = looper;
                return this;
            }
        }

        private WearableOptions(Builder builder) {
            this.zzfst = builder.zzfst;
        }

        private final GoogleApi.zza zzblw() {
            return this.zzfst != null ? new zzd().zza(this.zzfst).zzahy() : GoogleApi.zza.zzfsr;
        }
    }

    private Wearable() {
    }

    public static CapabilityClient getCapabilityClient(@NonNull Activity activity) {
        return new zzaa(activity, GoogleApi.zza.zzfsr);
    }

    public static CapabilityClient getCapabilityClient(@NonNull Activity activity, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzaa(activity, wearableOptions.zzblw());
    }

    public static CapabilityClient getCapabilityClient(@NonNull Context context) {
        return new zzaa(context, GoogleApi.zza.zzfsr);
    }

    public static CapabilityClient getCapabilityClient(@NonNull Context context, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzaa(context, wearableOptions.zzblw());
    }

    public static ChannelClient getChannelClient(@NonNull Activity activity) {
        return new zzao(activity, GoogleApi.zza.zzfsr);
    }

    public static ChannelClient getChannelClient(@NonNull Activity activity, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzao(activity, wearableOptions.zzblw());
    }

    public static ChannelClient getChannelClient(@NonNull Context context) {
        return new zzao(context, GoogleApi.zza.zzfsr);
    }

    public static ChannelClient getChannelClient(@NonNull Context context, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzao(context, wearableOptions.zzblw());
    }

    public static DataClient getDataClient(@NonNull Activity activity) {
        return new zzcj(activity, GoogleApi.zza.zzfsr);
    }

    public static DataClient getDataClient(@NonNull Activity activity, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzcj(activity, wearableOptions.zzblw());
    }

    public static DataClient getDataClient(@NonNull Context context) {
        return new zzcj(context, GoogleApi.zza.zzfsr);
    }

    public static DataClient getDataClient(@NonNull Context context, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzcj(context, wearableOptions.zzblw());
    }

    public static MessageClient getMessageClient(@NonNull Activity activity) {
        return new zzez(activity, GoogleApi.zza.zzfsr);
    }

    public static MessageClient getMessageClient(@NonNull Activity activity, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzez(activity, wearableOptions.zzblw());
    }

    public static MessageClient getMessageClient(@NonNull Context context) {
        return new zzez(context, GoogleApi.zza.zzfsr);
    }

    public static MessageClient getMessageClient(@NonNull Context context, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzez(context, wearableOptions.zzblw());
    }

    public static NodeClient getNodeClient(@NonNull Activity activity) {
        return new zzfl(activity, GoogleApi.zza.zzfsr);
    }

    public static NodeClient getNodeClient(@NonNull Activity activity, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzfl(activity, wearableOptions.zzblw());
    }

    public static NodeClient getNodeClient(@NonNull Context context) {
        return new zzfl(context, GoogleApi.zza.zzfsr);
    }

    public static NodeClient getNodeClient(@NonNull Context context, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzfl(context, wearableOptions.zzblw());
    }
}
