package com.google.android.gms.location;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzcfk;
import com.google.android.gms.internal.zzchh;

public class ActivityRecognition {
    public static final Api<NoOptions> API = new Api("ActivityRecognition.API", zzegv, zzegu);
    @Deprecated
    public static final ActivityRecognitionApi ActivityRecognitionApi = new zzcfk();
    public static final String CLIENT_NAME = "activity_recognition";
    private static final zzf<zzchh> zzegu = new zzf();
    private static final com.google.android.gms.common.api.Api.zza<zzchh, NoOptions> zzegv = new zza();

    @Hide
    public static abstract class zza<R extends Result> extends zzm<R, zzchh> {
        public zza(GoogleApiClient googleApiClient) {
            super(ActivityRecognition.API, googleApiClient);
        }

        @Hide
        public final /* bridge */ /* synthetic */ void setResult(Object obj) {
            super.setResult((Result) obj);
        }
    }

    private ActivityRecognition() {
    }

    public static ActivityRecognitionClient getClient(Activity activity) {
        return new ActivityRecognitionClient(activity);
    }

    public static ActivityRecognitionClient getClient(Context context) {
        return new ActivityRecognitionClient(context);
    }
}
