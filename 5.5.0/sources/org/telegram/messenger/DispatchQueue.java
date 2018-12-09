package org.telegram.messenger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.CountDownLatch;

public class DispatchQueue extends Thread {
    private volatile Handler handler = null;
    private CountDownLatch syncLatch = new CountDownLatch(1);

    /* renamed from: org.telegram.messenger.DispatchQueue$1 */
    class C30201 extends Handler {
        C30201() {
        }

        public void handleMessage(Message message) {
            DispatchQueue.this.handleMessage(message);
        }
    }

    public DispatchQueue(String str) {
        setName(str);
        start();
    }

    public void cancelRunnable(Runnable runnable) {
        try {
            this.syncLatch.await();
            this.handler.removeCallbacks(runnable);
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public void cleanupQueue() {
        try {
            this.syncLatch.await();
            this.handler.removeCallbacksAndMessages(null);
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public void handleMessage(Message message) {
    }

    public void postRunnable(Runnable runnable) {
        postRunnable(runnable, 0);
    }

    public void postRunnable(Runnable runnable, long j) {
        try {
            this.syncLatch.await();
            if (j <= 0) {
                this.handler.post(runnable);
            } else {
                this.handler.postDelayed(runnable, j);
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public void run() {
        Looper.prepare();
        this.handler = new C30201();
        this.syncLatch.countDown();
        Looper.loop();
    }

    public void sendMessage(Message message, int i) {
        try {
            this.syncLatch.await();
            if (i <= 0) {
                this.handler.sendMessage(message);
            } else {
                this.handler.sendMessageDelayed(message, (long) i);
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }
}
