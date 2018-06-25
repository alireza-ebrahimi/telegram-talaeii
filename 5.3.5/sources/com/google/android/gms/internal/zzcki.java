package com.google.android.gms.internal;

import android.os.Process;
import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.BlockingQueue;

final class zzcki extends Thread {
    private /* synthetic */ zzcke zzjnm;
    private final Object zzjnp = new Object();
    private final BlockingQueue<zzckh<?>> zzjnq;

    public zzcki(zzcke zzcke, String str, BlockingQueue<zzckh<?>> blockingQueue) {
        this.zzjnm = zzcke;
        zzbq.checkNotNull(str);
        zzbq.checkNotNull(blockingQueue);
        this.zzjnq = blockingQueue;
        setName(str);
    }

    private final void zza(InterruptedException interruptedException) {
        this.zzjnm.zzayp().zzbaw().zzj(String.valueOf(getName()).concat(" was interrupted"), interruptedException);
    }

    public final void run() {
        Object obj = null;
        while (obj == null) {
            try {
                this.zzjnm.zzjni.acquire();
                obj = 1;
            } catch (InterruptedException e) {
                zza(e);
            }
        }
        int threadPriority = Process.getThreadPriority(Process.myTid());
        while (true) {
            zzckh zzckh = (zzckh) this.zzjnq.poll();
            if (zzckh != null) {
                Process.setThreadPriority(zzckh.zzjno ? threadPriority : 10);
                zzckh.run();
            } else {
                try {
                    synchronized (this.zzjnp) {
                        if (this.zzjnq.peek() == null && !this.zzjnm.zzjnj) {
                            try {
                                this.zzjnp.wait(30000);
                            } catch (InterruptedException e2) {
                                zza(e2);
                            }
                        }
                    }
                    synchronized (this.zzjnm.zzjnh) {
                        if (this.zzjnq.peek() == null) {
                            break;
                        }
                    }
                } catch (Throwable th) {
                    synchronized (this.zzjnm.zzjnh) {
                        this.zzjnm.zzjni.release();
                        this.zzjnm.zzjnh.notifyAll();
                        if (this == this.zzjnm.zzjnb) {
                            this.zzjnm.zzjnb = null;
                        } else if (this == this.zzjnm.zzjnc) {
                            this.zzjnm.zzjnc = null;
                        } else {
                            this.zzjnm.zzayp().zzbau().log("Current scheduler thread is neither worker nor network");
                        }
                    }
                }
            }
        }
        synchronized (this.zzjnm.zzjnh) {
            this.zzjnm.zzjni.release();
            this.zzjnm.zzjnh.notifyAll();
            if (this == this.zzjnm.zzjnb) {
                this.zzjnm.zzjnb = null;
            } else if (this == this.zzjnm.zzjnc) {
                this.zzjnm.zzjnc = null;
            } else {
                this.zzjnm.zzayp().zzbau().log("Current scheduler thread is neither worker nor network");
            }
        }
    }

    public final void zzsl() {
        synchronized (this.zzjnp) {
            this.zzjnp.notifyAll();
        }
    }
}
