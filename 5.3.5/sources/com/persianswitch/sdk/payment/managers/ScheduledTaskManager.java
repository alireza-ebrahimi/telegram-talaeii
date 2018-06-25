package com.persianswitch.sdk.payment.managers;

import android.content.Context;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.payment.model.ClientConfig;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class ScheduledTaskManager {
    public static final Task SyncCardTask = new C07941();
    private static ScheduledTaskManager instance;
    private final Context mContext;
    private final List<Task> mScheduledTasks = Collections.emptyList();

    public static abstract class Task {
        public abstract boolean checkForRun(Context context, long j);

        public abstract void run(Context context);
    }

    /* renamed from: com.persianswitch.sdk.payment.managers.ScheduledTaskManager$1 */
    static class C07941 extends Task {
        private static final String TAG = "SyncCardTask";

        C07941() {
        }

        public boolean checkForRun(Context context, long currentTimeMillis) {
            boolean needSyncCards = currentTimeMillis - BaseSetting.getLastTimeCardsSynced(context) > TimeUnit.MILLISECONDS.convert(ClientConfig.getInstance(context).getCardSyncPeriodTime(), TimeUnit.SECONDS);
            if (needSyncCards) {
                SDKLog.m39i(TAG, "passedFromLastSync(millis) : %d , threshold(millis) : %d", Long.valueOf(passedFromLastSyncMillis), Long.valueOf(lastTimeCardSyncedThreshold));
            }
            return needSyncCards;
        }

        public void run(Context context) {
            new CardManager(context).sync();
        }
    }

    public static ScheduledTaskManager getInstance(Context context) {
        if (instance == null) {
            instance = new ScheduledTaskManager(context);
        }
        return instance;
    }

    private ScheduledTaskManager(Context context) {
        this.mContext = context;
    }

    public void checkTasks() {
        long nowMillis = System.currentTimeMillis();
        for (Task task : this.mScheduledTasks) {
            if (task.checkForRun(this.mContext, nowMillis)) {
                task.run(this.mContext);
            }
        }
    }
}
