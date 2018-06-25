package com.thin.downloadmanager;

import android.os.Process;
import com.persianswitch.okhttp3.internal.http.StatusLine;
import com.thin.downloadmanager.util.Log;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

class DownloadDispatcher extends Thread {
    private final int BUFFER_SIZE = 4096;
    private final int HTTP_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    private final int HTTP_TEMP_REDIRECT = StatusLine.HTTP_TEMP_REDIRECT;
    private final int MAX_REDIRECTS = 5;
    private long mContentLength;
    private long mCurrentBytes;
    private CallBackDelivery mDelivery;
    private final BlockingQueue<DownloadRequest> mQueue;
    private volatile boolean mQuit = false;
    private int mRedirectionCount = 0;
    private Timer mTimer;
    private boolean shouldAllowRedirects = true;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void executeDownload(com.thin.downloadmanager.DownloadRequest r13, java.lang.String r14) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0059 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r12 = this;
        r11 = 5;
        r7 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x005a }
        r7.<init>(r14);	 Catch:{ MalformedURLException -> 0x005a }
        r1 = 0;
        r8 = r7.openConnection();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r0 = r8;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r1 = r0;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = 0;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r1.setInstanceFollowRedirects(r8);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = r13.getRetryPolicy();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = r8.getCurrentTimeout();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r1.setConnectTimeout(r8);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = r13.getRetryPolicy();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = r8.getCurrentTimeout();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r1.setReadTimeout(r8);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r2 = r13.getCustomHeaders();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        if (r2 == 0) goto L_0x0064;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
    L_0x002f:
        r8 = r2.keySet();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = r8.iterator();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
    L_0x0037:
        r8 = r9.hasNext();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        if (r8 == 0) goto L_0x0064;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
    L_0x003d:
        r4 = r9.next();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r4 = (java.lang.String) r4;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = r2.get(r4);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = (java.lang.String) r8;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r1.addRequestProperty(r4, r8);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        goto L_0x0037;
    L_0x004d:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r12.attemptRetryOnTimeOutException(r13);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        if (r1 == 0) goto L_0x0059;
    L_0x0056:
        r1.disconnect();
    L_0x0059:
        return;
    L_0x005a:
        r3 = move-exception;
        r8 = 1007; // 0x3ef float:1.411E-42 double:4.975E-321;
        r9 = "MalformedURLException: URI passed is malformed.";
        r12.updateDownloadFailed(r13, r8, r9);
        goto L_0x0059;
    L_0x0064:
        r8 = 4;
        r12.updateDownloadState(r13, r8);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r6 = r1.getResponseCode();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = new java.lang.StringBuilder;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8.<init>();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = "Response code obtained for downloaded Id ";	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = r8.append(r9);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = r13.getDownloadId();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = r8.append(r9);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = " : httpResponse Code ";	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = r8.append(r9);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = r8.append(r6);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = r8.toString();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        com.thin.downloadmanager.util.Log.m53v(r8);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        switch(r6) {
            case 200: goto L_0x00c3;
            case 206: goto L_0x00c3;
            case 301: goto L_0x00ed;
            case 302: goto L_0x00ed;
            case 303: goto L_0x00ed;
            case 307: goto L_0x00ed;
            case 416: goto L_0x014e;
            case 500: goto L_0x016b;
            case 503: goto L_0x0160;
            default: goto L_0x0095;
        };	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
    L_0x0095:
        r8 = 1002; // 0x3ea float:1.404E-42 double:4.95E-321;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = new java.lang.StringBuilder;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9.<init>();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r10 = "Unhandled HTTP response:";	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = r9.append(r10);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = r9.append(r6);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r10 = " message:";	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = r9.append(r10);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r10 = r1.getResponseMessage();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = r9.append(r10);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = r9.toString();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r12.updateDownloadFailed(r13, r8, r9);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
    L_0x00bd:
        if (r1 == 0) goto L_0x0059;
    L_0x00bf:
        r1.disconnect();
        goto L_0x0059;
    L_0x00c3:
        r8 = 0;
        r12.shouldAllowRedirects = r8;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = r12.readResponseHeaders(r13, r1);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = 1;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        if (r8 != r9) goto L_0x00d6;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
    L_0x00cd:
        r12.transferData(r13, r1);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
    L_0x00d0:
        if (r1 == 0) goto L_0x0059;
    L_0x00d2:
        r1.disconnect();
        goto L_0x0059;
    L_0x00d6:
        r8 = 1006; // 0x3ee float:1.41E-42 double:4.97E-321;
        r9 = "Transfer-Encoding not found as well as can't know size of download, giving up";	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r12.updateDownloadFailed(r13, r8, r9);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        goto L_0x00d0;
    L_0x00df:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r12.attemptRetryOnTimeOutException(r13);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        if (r1 == 0) goto L_0x0059;
    L_0x00e8:
        r1.disconnect();
        goto L_0x0059;
    L_0x00ed:
        r8 = r12.mRedirectionCount;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        if (r8 >= r11) goto L_0x0137;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
    L_0x00f1:
        r8 = r12.shouldAllowRedirects;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        if (r8 == 0) goto L_0x0137;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
    L_0x00f5:
        r8 = r12.mRedirectionCount;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = r8 + 1;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r12.mRedirectionCount = r8;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = "ContentValues";	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = new java.lang.StringBuilder;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9.<init>();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r10 = "Redirect for downloaded Id ";	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = r9.append(r10);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r10 = r13.getDownloadId();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = r9.append(r10);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = r9.toString();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        com.thin.downloadmanager.util.Log.m54v(r8, r9);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = "Location";	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r5 = r1.getHeaderField(r8);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r12.executeDownload(r13, r5);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        goto L_0x00ed;
    L_0x0124:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r8 = 1004; // 0x3ec float:1.407E-42 double:4.96E-321;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = "Trouble with low-level sockets";	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r12.updateDownloadFailed(r13, r8, r9);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        if (r1 == 0) goto L_0x0059;
    L_0x0132:
        r1.disconnect();
        goto L_0x0059;
    L_0x0137:
        r8 = r12.mRedirectionCount;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        if (r8 <= r11) goto L_0x00bd;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
    L_0x013b:
        r8 = r12.shouldAllowRedirects;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        if (r8 == 0) goto L_0x00bd;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
    L_0x013f:
        r8 = 1005; // 0x3ed float:1.408E-42 double:4.965E-321;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = "Too many redirects, giving up";	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r12.updateDownloadFailed(r13, r8, r9);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        if (r1 == 0) goto L_0x0059;
    L_0x0149:
        r1.disconnect();
        goto L_0x0059;
    L_0x014e:
        r8 = 416; // 0x1a0 float:5.83E-43 double:2.055E-321;
        r9 = r1.getResponseMessage();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r12.updateDownloadFailed(r13, r8, r9);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        goto L_0x00bd;
    L_0x0159:
        r8 = move-exception;
        if (r1 == 0) goto L_0x015f;
    L_0x015c:
        r1.disconnect();
    L_0x015f:
        throw r8;
    L_0x0160:
        r8 = 503; // 0x1f7 float:7.05E-43 double:2.485E-321;
        r9 = r1.getResponseMessage();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r12.updateDownloadFailed(r13, r8, r9);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        goto L_0x00bd;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
    L_0x016b:
        r8 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r9 = r1.getResponseMessage();	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        r12.updateDownloadFailed(r13, r8, r9);	 Catch:{ SocketTimeoutException -> 0x004d, ConnectTimeoutException -> 0x00df, IOException -> 0x0124, all -> 0x0159 }
        goto L_0x00bd;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thin.downloadmanager.DownloadDispatcher.executeDownload(com.thin.downloadmanager.DownloadRequest, java.lang.String):void");
    }

    DownloadDispatcher(BlockingQueue<DownloadRequest> queue, CallBackDelivery delivery) {
        this.mQueue = queue;
        this.mDelivery = delivery;
    }

    public void run() {
        Process.setThreadPriority(10);
        this.mTimer = new Timer();
        while (true) {
            DownloadRequest request = null;
            try {
                request = (DownloadRequest) this.mQueue.take();
                this.mRedirectionCount = 0;
                this.shouldAllowRedirects = true;
                Log.m53v("Download initiated for " + request.getDownloadId());
                updateDownloadState(request, 2);
                executeDownload(request, request.getUri().toString());
            } catch (InterruptedException e) {
                if (this.mQuit) {
                    if (request != null) {
                        request.finish();
                        if (request.getDownloadState() != 16) {
                            updateDownloadFailed(request, 1008, "Download cancelled");
                        }
                    }
                    this.mTimer.cancel();
                }
            }
        }
        if (request != null) {
            request.finish();
            if (request.getDownloadState() != 16) {
                updateDownloadFailed(request, 1008, "Download cancelled");
            }
        }
        this.mTimer.cancel();
    }

    void quit() {
        this.mQuit = true;
        interrupt();
    }

    private void transferData(DownloadRequest request, HttpURLConnection conn) {
        IOException e;
        Throwable th;
        InputStream in = null;
        OutputStream out = null;
        FileDescriptor outFd = null;
        cleanupDestination(request);
        try {
            in = conn.getInputStream();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (Throwable th2) {
            th = th2;
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.flush();
                } catch (IOException e222) {
                    e222.printStackTrace();
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e2222) {
                            e2222.printStackTrace();
                        }
                    }
                } catch (Throwable th3) {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e22222) {
                            e22222.printStackTrace();
                        }
                    }
                }
            }
            if (outFd != null) {
                outFd.sync();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e222222) {
                    e222222.printStackTrace();
                }
            }
            throw th;
        }
        File destinationFile = new File(request.getDestinationURI().getPath());
        boolean errorCreatingDestinationFile = false;
        if (!destinationFile.exists()) {
            try {
                if (!destinationFile.createNewFile()) {
                    errorCreatingDestinationFile = true;
                    updateDownloadFailed(request, 1001, "Error in creating destination file");
                }
            } catch (IOException e2222222) {
                e2222222.printStackTrace();
                errorCreatingDestinationFile = true;
                updateDownloadFailed(request, 1001, "Error in creating destination file");
            }
        }
        if (!errorCreatingDestinationFile) {
            try {
                OutputStream out2 = new FileOutputStream(destinationFile, true);
                try {
                    outFd = ((FileOutputStream) out2).getFD();
                    out = out2;
                } catch (IOException e3) {
                    e2222222 = e3;
                    out = out2;
                    e2222222.printStackTrace();
                    if (in != null) {
                        updateDownloadFailed(request, 1001, "Error in creating input stream");
                    } else if (out == null) {
                        transferData(request, in, out);
                    } else {
                        updateDownloadFailed(request, 1001, "Error in writing download contents to the destination file");
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e22222222) {
                            e22222222.printStackTrace();
                        }
                    }
                    if (out != null) {
                        try {
                            out.flush();
                        } catch (IOException e222222222) {
                            e222222222.printStackTrace();
                            if (out != null) {
                                try {
                                    out.close();
                                    return;
                                } catch (IOException e2222222222) {
                                    e2222222222.printStackTrace();
                                    return;
                                }
                            }
                            return;
                        } catch (Throwable th4) {
                            if (out != null) {
                                try {
                                    out.close();
                                } catch (IOException e22222222222) {
                                    e22222222222.printStackTrace();
                                }
                            }
                        }
                    }
                    if (outFd != null) {
                        outFd.sync();
                    }
                    if (out == null) {
                        try {
                            out.close();
                        } catch (IOException e222222222222) {
                            e222222222222.printStackTrace();
                            return;
                        }
                    }
                } catch (Throwable th5) {
                    th = th5;
                    out = out2;
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.flush();
                    }
                    if (outFd != null) {
                        outFd.sync();
                    }
                    if (out != null) {
                        out.close();
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e222222222222 = e4;
                e222222222222.printStackTrace();
                if (in != null) {
                    updateDownloadFailed(request, 1001, "Error in creating input stream");
                } else if (out == null) {
                    updateDownloadFailed(request, 1001, "Error in writing download contents to the destination file");
                } else {
                    transferData(request, in, out);
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.flush();
                }
                if (outFd != null) {
                    outFd.sync();
                }
                if (out == null) {
                    out.close();
                }
            }
            if (in != null) {
                updateDownloadFailed(request, 1001, "Error in creating input stream");
            } else if (out == null) {
                updateDownloadFailed(request, 1001, "Error in writing download contents to the destination file");
            } else {
                transferData(request, in, out);
            }
        }
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.flush();
        }
        if (outFd != null) {
            outFd.sync();
        }
        if (out == null) {
            out.close();
        }
    }

    private void transferData(DownloadRequest request, InputStream in, OutputStream out) {
        byte[] data = new byte[4096];
        this.mCurrentBytes = 0;
        request.setDownloadState(8);
        Log.m53v("Content Length: " + this.mContentLength + " for Download Id " + request.getDownloadId());
        while (!request.isCancelled()) {
            int bytesRead = readFromResponse(request, data, in);
            if (this.mContentLength != -1 && this.mContentLength > 0) {
                updateDownloadProgress(request, (int) ((this.mCurrentBytes * 100) / this.mContentLength), this.mCurrentBytes);
            }
            if (bytesRead == -1) {
                updateDownloadComplete(request);
                return;
            } else if (bytesRead == Integer.MIN_VALUE) {
                return;
            } else {
                if (writeDataToDestination(request, data, bytesRead, out)) {
                    this.mCurrentBytes += (long) bytesRead;
                } else {
                    request.finish();
                    updateDownloadFailed(request, 1001, "Failed writing file");
                    return;
                }
            }
        }
        Log.m53v("Stopping the download as Download Request is cancelled for Downloaded Id " + request.getDownloadId());
        request.finish();
        updateDownloadFailed(request, 1008, "Download cancelled");
    }

    private int readFromResponse(DownloadRequest request, byte[] data, InputStream entityStream) {
        try {
            return entityStream.read(data);
        } catch (IOException ex) {
            if ("unexpected end of stream".equals(ex.getMessage())) {
                return -1;
            }
            updateDownloadFailed(request, 1004, "IOException: Failed reading response");
            return Integer.MIN_VALUE;
        }
    }

    private boolean writeDataToDestination(DownloadRequest request, byte[] data, int bytesRead, OutputStream out) {
        try {
            out.write(data, 0, bytesRead);
            return true;
        } catch (IOException e) {
            updateDownloadFailed(request, 1001, "IOException when writing download contents to the destination file");
            return false;
        } catch (Exception e2) {
            updateDownloadFailed(request, 1001, "Exception when writing download contents to the destination file");
            return false;
        }
    }

    private int readResponseHeaders(DownloadRequest request, HttpURLConnection conn) {
        String transferEncoding = conn.getHeaderField("Transfer-Encoding");
        this.mContentLength = -1;
        if (transferEncoding == null) {
            this.mContentLength = getHeaderFieldLong(conn, HttpRequest.HEADER_CONTENT_LENGTH, -1);
        } else {
            Log.m53v("Ignoring Content-Length since Transfer-Encoding is also defined for Downloaded Id " + request.getDownloadId());
        }
        if (this.mContentLength != -1) {
            return 1;
        }
        if (transferEncoding == null || !transferEncoding.equalsIgnoreCase("chunked")) {
            return -1;
        }
        return 1;
    }

    private long getHeaderFieldLong(URLConnection conn, String field, long defaultValue) {
        try {
            defaultValue = Long.parseLong(conn.getHeaderField(field));
        } catch (NumberFormatException e) {
        }
        return defaultValue;
    }

    private void attemptRetryOnTimeOutException(final DownloadRequest request) {
        updateDownloadState(request, 128);
        RetryPolicy retryPolicy = request.getRetryPolicy();
        try {
            retryPolicy.retry();
            this.mTimer.schedule(new TimerTask() {
                public void run() {
                    DownloadDispatcher.this.executeDownload(request, request.getUri().toString());
                }
            }, (long) retryPolicy.getCurrentTimeout());
        } catch (RetryError e) {
            updateDownloadFailed(request, 1009, "Connection time out after maximum retires attempted");
        }
    }

    private void cleanupDestination(DownloadRequest request) {
        Log.m40d("cleanupDestination() deleting " + request.getDestinationURI().getPath());
        File destinationFile = new File(request.getDestinationURI().getPath());
        if (destinationFile.exists()) {
            destinationFile.delete();
        }
    }

    private void updateDownloadState(DownloadRequest request, int state) {
        request.setDownloadState(state);
    }

    private void updateDownloadComplete(DownloadRequest request) {
        this.mDelivery.postDownloadComplete(request);
        request.setDownloadState(16);
        request.finish();
    }

    private void updateDownloadFailed(DownloadRequest request, int errorCode, String errorMsg) {
        this.shouldAllowRedirects = false;
        request.setDownloadState(32);
        if (request.getDeleteDestinationFileOnFailure()) {
            cleanupDestination(request);
        }
        this.mDelivery.postDownloadFailed(request, errorCode, errorMsg);
        request.finish();
    }

    private void updateDownloadProgress(DownloadRequest request, int progress, long downloadedBytes) {
        this.mDelivery.postProgressUpdate(request, this.mContentLength, downloadedBytes, progress);
    }
}
