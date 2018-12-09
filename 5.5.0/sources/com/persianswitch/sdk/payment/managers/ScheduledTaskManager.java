package com.persianswitch.sdk.payment.managers;

import android.content.Context;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.payment.model.ClientConfig;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class ScheduledTaskManager {
    /* renamed from: a */
    public static final Task f7399a = new C22861();
    /* renamed from: d */
    private static ScheduledTaskManager f7400d;
    /* renamed from: b */
    private final Context f7401b;
    /* renamed from: c */
    private final List<Task> f7402c = Collections.emptyList();

    public static abstract class Task {
        /* renamed from: a */
        public abstract void mo3297a(Context context);

        /* renamed from: a */
        public abstract boolean mo3298a(Context context, long j);
    }

    /* renamed from: com.persianswitch.sdk.payment.managers.ScheduledTaskManager$1 */
    static class C22861 extends Task {
        C22861() {
        }

        /* renamed from: a */
        public void mo3297a(Context context) {
            new CardManager(context).m11078b();
        }

        /* renamed from: a */
        public boolean mo3298a(Context context, long j) {
            boolean z = j - BaseSetting.m10473j(context) > TimeUnit.MILLISECONDS.convert(ClientConfig.m11126a(context).m11140f(), TimeUnit.SECONDS);
            if (z) {
                SDKLog.m10657a("SyncCardTask", "passedFromLastSync(millis) : %d , threshold(millis) : %d", Long.valueOf(r4), Long.valueOf(r6));
            }
            return z;
        }
    }

    private ScheduledTaskManager(Context context) {
        this.f7401b = context;
    }

    /* renamed from: a */
    public static ScheduledTaskManager m11098a(Context context) {
        if (f7400d == null) {
            f7400d = new ScheduledTaskManager(context);
        }
        return f7400d;
    }

    /* renamed from: a */
    public void m11099a() {
        long currentTimeMillis = System.currentTimeMillis();
        for (Task task : this.f7402c) {
            if (task.mo3298a(this.f7401b, currentTimeMillis)) {
                task.mo3297a(this.f7401b);
            }
        }
    }
}
