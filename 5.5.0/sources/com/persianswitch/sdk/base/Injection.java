package com.persianswitch.sdk.base;

import android.content.Context;
import com.persianswitch.sdk.base.webservice.HttpEngine;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public interface Injection {

    public static final class Network {
        /* renamed from: a */
        public static HttpEngine m10482a(Context context, Config config) {
            return HttpEngine.m10820a(context, config);
        }
    }

    public static final class ThreadPool {
        /* renamed from: a */
        private static Executor f6996a;

        /* renamed from: a */
        public static Executor m10483a() {
            if (f6996a == null) {
                f6996a = Executors.newFixedThreadPool(2);
            }
            return f6996a;
        }
    }
}
