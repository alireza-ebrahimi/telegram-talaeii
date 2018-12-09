package com.google.android.gms.common.api;

import android.support.v4.p022f.C0464a;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;

public class AvailabilityException extends Exception {
    private final C0464a<zzh<?>, ConnectionResult> zzcc;

    public AvailabilityException(C0464a<zzh<?>, ConnectionResult> c0464a) {
        this.zzcc = c0464a;
    }

    public ConnectionResult getConnectionResult(GoogleApi<? extends ApiOptions> googleApi) {
        zzh zzm = googleApi.zzm();
        Preconditions.checkArgument(this.zzcc.get(zzm) != null, "The given API was not part of the availability request.");
        return (ConnectionResult) this.zzcc.get(zzm);
    }

    public String getMessage() {
        Iterable arrayList = new ArrayList();
        Object obj = 1;
        for (zzh zzh : this.zzcc.keySet()) {
            ConnectionResult connectionResult = (ConnectionResult) this.zzcc.get(zzh);
            if (connectionResult.isSuccess()) {
                obj = null;
            }
            String zzq = zzh.zzq();
            String valueOf = String.valueOf(connectionResult);
            arrayList.add(new StringBuilder((String.valueOf(zzq).length() + 2) + String.valueOf(valueOf).length()).append(zzq).append(": ").append(valueOf).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (obj != null) {
            stringBuilder.append("None of the queried APIs are available. ");
        } else {
            stringBuilder.append("Some of the queried APIs are unavailable. ");
        }
        stringBuilder.append(TextUtils.join("; ", arrayList));
        return stringBuilder.toString();
    }

    public final C0464a<zzh<?>, ConnectionResult> zzl() {
        return this.zzcc;
    }
}
