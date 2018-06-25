package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.util.concurrent.NumberedThreadFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class zzbg {
    private static final ExecutorService zzji = Executors.newFixedThreadPool(2, new NumberedThreadFactory("GAC_Executor"));

    public static ExecutorService zzbe() {
        return zzji;
    }
}
