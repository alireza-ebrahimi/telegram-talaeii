package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

public class TaskCompletionSource<TResult> {
    private final zzp<TResult> zzlel = new zzp();

    @NonNull
    public Task<TResult> getTask() {
        return this.zzlel;
    }

    public void setException(@NonNull Exception exception) {
        this.zzlel.setException(exception);
    }

    public void setResult(TResult tResult) {
        this.zzlel.setResult(tResult);
    }

    public boolean trySetException(@NonNull Exception exception) {
        return this.zzlel.trySetException(exception);
    }

    public boolean trySetResult(TResult tResult) {
        return this.zzlel.trySetResult(tResult);
    }
}
