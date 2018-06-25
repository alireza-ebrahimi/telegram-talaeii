package com.google.android.gms.common.api;

import com.google.android.gms.common.api.internal.BasePendingResult;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends BasePendingResult<BatchResult> {
    private final Object mLock;
    private int zzcd;
    private boolean zzce;
    private boolean zzcf;
    private final PendingResult<?>[] zzcg;

    public static final class Builder {
        private List<PendingResult<?>> zzci = new ArrayList();
        private GoogleApiClient zzcj;

        public Builder(GoogleApiClient googleApiClient) {
            this.zzcj = googleApiClient;
        }

        public final <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken(this.zzci.size());
            this.zzci.add(pendingResult);
            return batchResultToken;
        }

        public final Batch build() {
            return new Batch(this.zzci, this.zzcj);
        }
    }

    private Batch(List<PendingResult<?>> list, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.mLock = new Object();
        this.zzcd = list.size();
        this.zzcg = new PendingResult[this.zzcd];
        if (list.isEmpty()) {
            setResult(new BatchResult(Status.RESULT_SUCCESS, this.zzcg));
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            PendingResult pendingResult = (PendingResult) list.get(i);
            this.zzcg[i] = pendingResult;
            pendingResult.addStatusListener(new zza(this));
        }
    }

    public final void cancel() {
        super.cancel();
        for (PendingResult cancel : this.zzcg) {
            cancel.cancel();
        }
    }

    public final BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.zzcg);
    }
}
