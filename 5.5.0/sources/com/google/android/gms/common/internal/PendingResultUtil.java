package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class PendingResultUtil {
    private static final StatusConverter zzun = new zzk();

    public interface ResultConverter<R extends Result, T> {
        T convert(R r);
    }

    public interface StatusConverter {
        ApiException convert(Status status);
    }

    public static <R extends Result, T extends Response<R>> Task<T> toResponseTask(PendingResult<R> pendingResult, T t) {
        return toTask(pendingResult, new zzm(t));
    }

    public static <R extends Result, T> Task<T> toTask(PendingResult<R> pendingResult, ResultConverter<R, T> resultConverter) {
        return toTask(pendingResult, resultConverter, zzun);
    }

    public static <R extends Result, T> Task<T> toTask(PendingResult<R> pendingResult, ResultConverter<R, T> resultConverter, StatusConverter statusConverter) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        pendingResult.addStatusListener(new zzl(pendingResult, taskCompletionSource, resultConverter, statusConverter));
        return taskCompletionSource.getTask();
    }

    public static <R extends Result> Task<Void> toVoidTask(PendingResult<R> pendingResult) {
        return toTask(pendingResult, new zzn());
    }
}
