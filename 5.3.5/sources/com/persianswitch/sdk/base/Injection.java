package com.persianswitch.sdk.base;

import android.content.Context;
import com.persianswitch.sdk.base.security.SecurityManager;
import com.persianswitch.sdk.base.webservice.HttpEngine;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public interface Injection {

    public static final class Network {
        private static final String TAG = "Injection/Network";

        public static HttpEngine engine(Context context, Config config) {
            return HttpEngine.getInstance(context, config);
        }

        public static SecurityManager security(Context context) {
            return SecurityManager.getInstance(context);
        }
    }

    public static final class ThreadPool {
        private static Executor wsExecutor;

        public static Executor ws() {
            if (wsExecutor == null) {
                wsExecutor = Executors.newFixedThreadPool(2);
            }
            return wsExecutor;
        }
    }
}
