package com.google.android.gms.tasks;

public class TaskCompletionSource<TResult> {
    private final zzu<TResult> zzafh = new zzu();

    public TaskCompletionSource(CancellationToken cancellationToken) {
        cancellationToken.onCanceledRequested(new zzs(this));
    }

    public Task<TResult> getTask() {
        return this.zzafh;
    }

    public void setException(Exception exception) {
        this.zzafh.setException(exception);
    }

    public void setResult(TResult tResult) {
        this.zzafh.setResult(tResult);
    }

    public boolean trySetException(Exception exception) {
        return this.zzafh.trySetException(exception);
    }

    public boolean trySetResult(TResult tResult) {
        return this.zzafh.trySetResult(tResult);
    }
}
