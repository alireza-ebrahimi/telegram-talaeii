package io.fabric.sdk.android;

import io.fabric.sdk.android.services.common.TimingMetric;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;

class InitializationTask<Result> extends PriorityAsyncTask<Void, Void, Result> {
    private static final String TIMING_METRIC_TAG = "KitInitialization";
    final Kit<Result> kit;

    protected void onPreExecute() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x001a in list [B:19:0x003a]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r7 = this;
        r6 = 1;
        super.onPreExecute();
        r3 = "onPreExecute";
        r2 = r7.createAndStartTimingMetric(r3);
        r1 = 0;
        r3 = r7.kit;	 Catch:{ UnmetDependencyException -> 0x001b, Exception -> 0x0027, all -> 0x001d }
        r1 = r3.onPreExecute();	 Catch:{ UnmetDependencyException -> 0x001b, Exception -> 0x0027, all -> 0x001d }
        r2.stopMeasuring();
        if (r1 != 0) goto L_0x001a;
    L_0x0017:
        r7.cancel(r6);
    L_0x001a:
        return;
    L_0x001b:
        r0 = move-exception;
        throw r0;	 Catch:{ UnmetDependencyException -> 0x001b, Exception -> 0x0027, all -> 0x001d }
    L_0x001d:
        r3 = move-exception;
        r2.stopMeasuring();
        if (r1 != 0) goto L_0x0026;
    L_0x0023:
        r7.cancel(r6);
    L_0x0026:
        throw r3;
    L_0x0027:
        r0 = move-exception;
        r3 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ UnmetDependencyException -> 0x001b, Exception -> 0x0027, all -> 0x001d }
        r4 = "Fabric";	 Catch:{ UnmetDependencyException -> 0x001b, Exception -> 0x0027, all -> 0x001d }
        r5 = "Failure onPreExecute()";	 Catch:{ UnmetDependencyException -> 0x001b, Exception -> 0x0027, all -> 0x001d }
        r3.mo4384e(r4, r5, r0);	 Catch:{ UnmetDependencyException -> 0x001b, Exception -> 0x0027, all -> 0x001d }
        r2.stopMeasuring();
        if (r1 != 0) goto L_0x001a;
    L_0x003a:
        r7.cancel(r6);
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.InitializationTask.onPreExecute():void");
    }

    public InitializationTask(Kit<Result> kit) {
        this.kit = kit;
    }

    protected Result doInBackground(Void... voids) {
        TimingMetric timingMetric = createAndStartTimingMetric("doInBackground");
        Result result = null;
        if (!isCancelled()) {
            result = this.kit.doInBackground();
        }
        timingMetric.stopMeasuring();
        return result;
    }

    protected void onPostExecute(Result result) {
        this.kit.onPostExecute(result);
        this.kit.initializationCallback.success(result);
    }

    protected void onCancelled(Result result) {
        this.kit.onCancelled(result);
        this.kit.initializationCallback.failure(new InitializationException(this.kit.getIdentifier() + " Initialization was cancelled"));
    }

    public Priority getPriority() {
        return Priority.HIGH;
    }

    private TimingMetric createAndStartTimingMetric(String event) {
        TimingMetric timingMetric = new TimingMetric(this.kit.getIdentifier() + "." + event, TIMING_METRIC_TAG);
        timingMetric.startMeasuring();
        return timingMetric;
    }
}
