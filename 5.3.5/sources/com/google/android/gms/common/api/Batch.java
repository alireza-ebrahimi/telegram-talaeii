package com.google.android.gms.common.api;

import com.google.android.gms.common.api.internal.BasePendingResult;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends BasePendingResult<BatchResult> {
    private final Object mLock;
    private int zzfsf;
    private boolean zzfsg;
    private boolean zzfsh;
    private final PendingResult<?>[] zzfsi;

    public static final class Builder {
        private GoogleApiClient zzfap;
        private List<PendingResult<?>> zzfsk = new ArrayList();

        public Builder(GoogleApiClient googleApiClient) {
            this.zzfap = googleApiClient;
        }

        public final <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken(this.zzfsk.size());
            this.zzfsk.add(pendingResult);
            return batchResultToken;
        }

        public final Batch build() {
            return new Batch(this.zzfsk, this.zzfap);
        }
    }

    private Batch(List<PendingResult<?>> list, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.mLock = new Object();
        this.zzfsf = list.size();
        this.zzfsi = new PendingResult[this.zzfsf];
        if (list.isEmpty()) {
            setResult(new BatchResult(Status.zzftq, this.zzfsi));
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            PendingResult pendingResult = (PendingResult) list.get(i);
            this.zzfsi[i] = pendingResult;
            pendingResult.zza(new zza(this));
        }
    }

    public final void cancel() {
        super.cancel();
        for (PendingResult cancel : this.zzfsi) {
            cancel.cancel();
        }
    }

    public final BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.zzfsi);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return createFailedResult(status);
    }
}
