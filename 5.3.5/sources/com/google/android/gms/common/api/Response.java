package com.google.android.gms.common.api;

import android.support.annotation.NonNull;

public class Response<T extends Result> {
    private T zzftm;

    protected Response(@NonNull T t) {
        this.zzftm = t;
    }

    @NonNull
    protected T getResult() {
        return this.zzftm;
    }

    public void setResult(@NonNull T t) {
        this.zzftm = t;
    }
}
