package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

interface zzm<TResult> {
    void cancel();

    void onComplete(@NonNull Task<TResult> task);
}
