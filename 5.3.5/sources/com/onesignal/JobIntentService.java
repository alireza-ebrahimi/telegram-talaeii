package com.onesignal;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobInfo.Builder;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobServiceEngine;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.HashMap;

abstract class JobIntentService extends Service {
    static final boolean DEBUG = false;
    static final String TAG = "JobIntentService";
    static final HashMap<ComponentName, WorkEnqueuer> sClassWorkEnqueuer = new HashMap();
    static final Object sLock = new Object();
    final ArrayList<CompatWorkItem> mCompatQueue;
    WorkEnqueuer mCompatWorkEnqueuer;
    CommandProcessor mCurProcessor;
    boolean mInterruptIfStopped = false;
    CompatJobEngine mJobImpl;
    boolean mStopped = false;

    final class CommandProcessor extends AsyncTask<Void, Void, Void> {
        CommandProcessor() {
        }

        protected Void doInBackground(Void... params) {
            while (true) {
                GenericWorkItem work = JobIntentService.this.dequeueWork();
                if (work == null) {
                    return null;
                }
                JobIntentService.this.onHandleWork(work.getIntent());
                work.complete();
            }
        }

        protected void onCancelled(Void aVoid) {
            JobIntentService.this.processorFinished();
        }

        protected void onPostExecute(Void aVoid) {
            JobIntentService.this.processorFinished();
        }
    }

    interface CompatJobEngine {
        IBinder compatGetBinder();

        GenericWorkItem dequeueWork();
    }

    static abstract class WorkEnqueuer {
        final ComponentName mComponentName;
        boolean mHasJobId;
        int mJobId;

        abstract void enqueueWork(Intent intent);

        WorkEnqueuer(Context context, ComponentName cn) {
            this.mComponentName = cn;
        }

        void ensureJobId(int jobId) {
            if (!this.mHasJobId) {
                this.mHasJobId = true;
                this.mJobId = jobId;
            } else if (this.mJobId != jobId) {
                throw new IllegalArgumentException("Given job ID " + jobId + " is different than previous " + this.mJobId);
            }
        }

        public void serviceCreated() {
        }

        public void serviceStartReceived() {
        }

        public void serviceDestroyed() {
        }
    }

    static final class CompatWorkEnqueuer extends WorkEnqueuer {
        private final Context mContext;
        private final WakeLock mLaunchWakeLock;
        boolean mLaunchingService;
        private final WakeLock mRunWakeLock;
        boolean mServiceRunning;

        CompatWorkEnqueuer(Context context, ComponentName cn) {
            super(context, cn);
            this.mContext = context.getApplicationContext();
            PowerManager pm = (PowerManager) context.getSystemService("power");
            this.mLaunchWakeLock = pm.newWakeLock(1, cn.getClassName() + ":launch");
            this.mLaunchWakeLock.setReferenceCounted(false);
            this.mRunWakeLock = pm.newWakeLock(1, cn.getClassName() + ":run");
            this.mRunWakeLock.setReferenceCounted(false);
        }

        void enqueueWork(Intent work) {
            Intent intent = new Intent(work);
            intent.setComponent(this.mComponentName);
            if (this.mContext.startService(intent) != null) {
                synchronized (this) {
                    if (!this.mLaunchingService) {
                        this.mLaunchingService = true;
                        if (!this.mServiceRunning) {
                            this.mLaunchWakeLock.acquire(60000);
                        }
                    }
                }
            }
        }

        public void serviceCreated() {
            synchronized (this) {
                if (!this.mServiceRunning) {
                    this.mServiceRunning = true;
                    this.mRunWakeLock.acquire();
                    this.mLaunchWakeLock.release();
                }
            }
        }

        public void serviceStartReceived() {
            synchronized (this) {
                this.mLaunchingService = false;
            }
        }

        public void serviceDestroyed() {
            synchronized (this) {
                if (this.mLaunchingService) {
                    this.mLaunchWakeLock.acquire(60000);
                }
                this.mServiceRunning = false;
                this.mRunWakeLock.release();
            }
        }
    }

    interface GenericWorkItem {
        void complete();

        Intent getIntent();
    }

    final class CompatWorkItem implements GenericWorkItem {
        final Intent mIntent;
        final int mStartId;

        CompatWorkItem(Intent intent, int startId) {
            this.mIntent = intent;
            this.mStartId = startId;
        }

        public Intent getIntent() {
            return this.mIntent;
        }

        public void complete() {
            JobIntentService.this.stopSelf(this.mStartId);
        }
    }

    @RequiresApi(26)
    static final class JobServiceEngineImpl extends JobServiceEngine implements CompatJobEngine {
        static final boolean DEBUG = false;
        static final String TAG = "JobServiceEngineImpl";
        final Object mLock = new Object();
        JobParameters mParams;
        final JobIntentService mService;

        final class WrapperWorkItem implements GenericWorkItem {
            final JobWorkItem mJobWork;

            WrapperWorkItem(JobWorkItem jobWork) {
                this.mJobWork = jobWork;
            }

            public Intent getIntent() {
                return this.mJobWork.getIntent();
            }

            public void complete() {
                synchronized (JobServiceEngineImpl.this.mLock) {
                    if (JobServiceEngineImpl.this.mParams != null) {
                        JobServiceEngineImpl.this.mParams.completeWork(this.mJobWork);
                    }
                }
            }
        }

        JobServiceEngineImpl(JobIntentService service) {
            super(service);
            this.mService = service;
        }

        public IBinder compatGetBinder() {
            return getBinder();
        }

        public boolean onStartJob(JobParameters params) {
            this.mParams = params;
            this.mService.ensureProcessorRunningLocked();
            return true;
        }

        public boolean onStopJob(JobParameters params) {
            boolean result = this.mService.doStopCurrentWork();
            synchronized (this.mLock) {
                this.mParams = null;
            }
            return result;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.onesignal.JobIntentService.GenericWorkItem dequeueWork() {
            /*
            r4 = this;
            r1 = 0;
            r2 = r4.mLock;
            monitor-enter(r2);
            r3 = r4.mParams;	 Catch:{ all -> 0x0026 }
            if (r3 != 0) goto L_0x000a;
        L_0x0008:
            monitor-exit(r2);	 Catch:{ all -> 0x0026 }
        L_0x0009:
            return r1;
        L_0x000a:
            r3 = r4.mParams;	 Catch:{ all -> 0x0026 }
            r0 = r3.dequeueWork();	 Catch:{ all -> 0x0026 }
            monitor-exit(r2);	 Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x0009;
        L_0x0013:
            r1 = r0.getIntent();
            r2 = r4.mService;
            r2 = r2.getClassLoader();
            r1.setExtrasClassLoader(r2);
            r1 = new com.onesignal.JobIntentService$JobServiceEngineImpl$WrapperWorkItem;
            r1.<init>(r0);
            goto L_0x0009;
        L_0x0026:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0026 }
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.onesignal.JobIntentService.JobServiceEngineImpl.dequeueWork():com.onesignal.JobIntentService$GenericWorkItem");
        }
    }

    @RequiresApi(26)
    static final class JobWorkEnqueuer extends WorkEnqueuer {
        private final JobInfo mJobInfo;
        private final JobScheduler mJobScheduler;

        JobWorkEnqueuer(Context context, ComponentName cn, int jobId) {
            super(context, cn);
            ensureJobId(jobId);
            this.mJobInfo = new Builder(jobId, this.mComponentName).setOverrideDeadline(0).build();
            this.mJobScheduler = (JobScheduler) context.getApplicationContext().getSystemService("jobscheduler");
        }

        void enqueueWork(Intent work) {
            this.mJobScheduler.enqueue(this.mJobInfo, new JobWorkItem(work));
        }
    }

    protected abstract void onHandleWork(@NonNull Intent intent);

    public JobIntentService() {
        if (VERSION.SDK_INT >= 26) {
            this.mCompatQueue = null;
        } else {
            this.mCompatQueue = new ArrayList();
        }
    }

    public void onCreate() {
        super.onCreate();
        if (VERSION.SDK_INT >= 26) {
            this.mJobImpl = new JobServiceEngineImpl(this);
            this.mCompatWorkEnqueuer = null;
            return;
        }
        this.mJobImpl = null;
        this.mCompatWorkEnqueuer = getWorkEnqueuer(this, new ComponentName(this, getClass()), false, 0);
        this.mCompatWorkEnqueuer.serviceCreated();
    }

    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (this.mCompatQueue == null) {
            return 2;
        }
        this.mCompatWorkEnqueuer.serviceStartReceived();
        synchronized (this.mCompatQueue) {
            ArrayList arrayList = this.mCompatQueue;
            if (intent == null) {
                intent = new Intent();
            }
            arrayList.add(new CompatWorkItem(intent, startId));
            ensureProcessorRunningLocked();
        }
        return 3;
    }

    public IBinder onBind(@NonNull Intent intent) {
        if (this.mJobImpl != null) {
            return this.mJobImpl.compatGetBinder();
        }
        return null;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mCompatWorkEnqueuer != null) {
            this.mCompatWorkEnqueuer.serviceDestroyed();
        }
    }

    public static void enqueueWork(@NonNull Context context, @NonNull Class cls, int jobId, @NonNull Intent work) {
        enqueueWork(context, new ComponentName(context, cls), jobId, work);
    }

    public static void enqueueWork(@NonNull Context context, @NonNull ComponentName component, int jobId, @NonNull Intent work) {
        if (work == null) {
            throw new IllegalArgumentException("work must not be null");
        }
        synchronized (sLock) {
            WorkEnqueuer we = getWorkEnqueuer(context, component, true, jobId);
            we.ensureJobId(jobId);
            we.enqueueWork(work);
        }
    }

    static WorkEnqueuer getWorkEnqueuer(Context context, ComponentName cn, boolean hasJobId, int jobId) {
        WorkEnqueuer we = (WorkEnqueuer) sClassWorkEnqueuer.get(cn);
        if (we == null) {
            if (VERSION.SDK_INT < 26) {
                we = new CompatWorkEnqueuer(context, cn);
            } else if (hasJobId) {
                we = new JobWorkEnqueuer(context, cn, jobId);
            } else {
                throw new IllegalArgumentException("Can't be here without a job id");
            }
            sClassWorkEnqueuer.put(cn, we);
        }
        return we;
    }

    public void setInterruptIfStopped(boolean interruptIfStopped) {
        this.mInterruptIfStopped = interruptIfStopped;
    }

    public boolean isStopped() {
        return this.mStopped;
    }

    public boolean onStopCurrentWork() {
        return true;
    }

    boolean doStopCurrentWork() {
        if (this.mCurProcessor != null) {
            this.mCurProcessor.cancel(this.mInterruptIfStopped);
        }
        this.mStopped = true;
        return onStopCurrentWork();
    }

    void ensureProcessorRunningLocked() {
        if (this.mCurProcessor == null) {
            this.mCurProcessor = new CommandProcessor();
            this.mCurProcessor.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    void processorFinished() {
        if (this.mCompatQueue != null) {
            synchronized (this.mCompatQueue) {
                this.mCurProcessor = null;
                if (this.mCompatQueue != null && this.mCompatQueue.size() > 0) {
                    ensureProcessorRunningLocked();
                }
            }
        }
    }

    GenericWorkItem dequeueWork() {
        if (this.mJobImpl != null) {
            return this.mJobImpl.dequeueWork();
        }
        synchronized (this.mCompatQueue) {
            if (this.mCompatQueue.size() > 0) {
                GenericWorkItem genericWorkItem = (GenericWorkItem) this.mCompatQueue.remove(0);
                return genericWorkItem;
            }
            return null;
        }
    }
}
