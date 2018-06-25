package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;

public class AvailabilityException extends Exception {
    private final ArrayMap<zzh<?>, ConnectionResult> zzfse;

    @Hide
    public AvailabilityException(ArrayMap<zzh<?>, ConnectionResult> arrayMap) {
        this.zzfse = arrayMap;
    }

    public ConnectionResult getConnectionResult(GoogleApi<? extends ApiOptions> googleApi) {
        zzh zzahv = googleApi.zzahv();
        zzbq.checkArgument(this.zzfse.get(zzahv) != null, "The given API was not part of the availability request.");
        return (ConnectionResult) this.zzfse.get(zzahv);
    }

    public String getMessage() {
        Iterable arrayList = new ArrayList();
        Object obj = 1;
        for (zzh zzh : this.zzfse.keySet()) {
            ConnectionResult connectionResult = (ConnectionResult) this.zzfse.get(zzh);
            if (connectionResult.isSuccess()) {
                obj = null;
            }
            String zzaig = zzh.zzaig();
            String valueOf = String.valueOf(connectionResult);
            arrayList.add(new StringBuilder((String.valueOf(zzaig).length() + 2) + String.valueOf(valueOf).length()).append(zzaig).append(": ").append(valueOf).toString());
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

    @Hide
    public final ArrayMap<zzh<?>, ConnectionResult> zzahr() {
        return this.zzfse;
    }
}
