package com.squareup.picasso;

import android.net.NetworkInfo;
import com.squareup.picasso.Picasso.Priority;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class PicassoExecutorService extends ThreadPoolExecutor {
    private static final int DEFAULT_THREAD_COUNT = 3;

    private static final class PicassoFutureTask extends FutureTask<BitmapHunter> implements Comparable<PicassoFutureTask> {
        private final BitmapHunter hunter;

        public PicassoFutureTask(BitmapHunter hunter) {
            super(hunter, null);
            this.hunter = hunter;
        }

        public int compareTo(PicassoFutureTask other) {
            Priority p1 = this.hunter.getPriority();
            Priority p2 = other.hunter.getPriority();
            return p1 == p2 ? this.hunter.sequence - other.hunter.sequence : p2.ordinal() - p1.ordinal();
        }
    }

    PicassoExecutorService() {
        super(3, 3, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new PicassoThreadFactory());
    }

    void adjustThreadCount(NetworkInfo info) {
        if (info == null || !info.isConnectedOrConnecting()) {
            setThreadCount(3);
            return;
        }
        switch (info.getType()) {
            case 0:
                switch (info.getSubtype()) {
                    case 1:
                    case 2:
                        setThreadCount(1);
                        return;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 12:
                        setThreadCount(2);
                        return;
                    case 13:
                    case 14:
                    case 15:
                        setThreadCount(3);
                        return;
                    default:
                        setThreadCount(3);
                        return;
                }
            case 1:
            case 6:
            case 9:
                setThreadCount(4);
                return;
            default:
                setThreadCount(3);
                return;
        }
    }

    private void setThreadCount(int threadCount) {
        setCorePoolSize(threadCount);
        setMaximumPoolSize(threadCount);
    }

    public Future<?> submit(Runnable task) {
        PicassoFutureTask ftask = new PicassoFutureTask((BitmapHunter) task);
        execute(ftask);
        return ftask;
    }
}
