package com.google.android.gms.internal.measurement;

import android.os.Process;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.BlockingQueue;

final class zzgl extends Thread {
    private final /* synthetic */ zzgh zzami;
    private final Object zzaml = new Object();
    private final BlockingQueue<zzgk<?>> zzamm;

    public zzgl(zzgh zzgh, String str, BlockingQueue<zzgk<?>> blockingQueue) {
        this.zzami = zzgh;
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(blockingQueue);
        this.zzamm = blockingQueue;
        setName(str);
    }

    private final void zza(InterruptedException interruptedException) {
        this.zzami.zzgf().zziv().zzg(String.valueOf(getName()).concat(" was interrupted"), interruptedException);
    }

    public final void run() {
        Object obj = null;
        while (obj == null) {
            try {
                this.zzami.zzame.acquire();
                obj = 1;
            } catch (InterruptedException e) {
                zza(e);
            }
        }
        int threadPriority = Process.getThreadPriority(Process.myTid());
        while (true) {
            zzgk zzgk = (zzgk) this.zzamm.poll();
            if (zzgk != null) {
                Process.setThreadPriority(zzgk.zzamk ? threadPriority : 10);
                zzgk.run();
            } else {
                try {
                    synchronized (this.zzaml) {
                        if (this.zzamm.peek() == null && !this.zzami.zzamf) {
                            try {
                                this.zzaml.wait(30000);
                            } catch (InterruptedException e2) {
                                zza(e2);
                            }
                        }
                    }
                    synchronized (this.zzami.zzamd) {
                        if (this.zzamm.peek() == null) {
                            break;
                        }
                    }
                } catch (Throwable th) {
                    synchronized (this.zzami.zzamd) {
                        this.zzami.zzame.release();
                        this.zzami.zzamd.notifyAll();
                        if (this == this.zzami.zzalx) {
                            this.zzami.zzalx = null;
                        } else if (this == this.zzami.zzaly) {
                            this.zzami.zzaly = null;
                        } else {
                            this.zzami.zzgf().zzis().log("Current scheduler thread is neither worker nor network");
                        }
                    }
                }
            }
        }
        synchronized (this.zzami.zzamd) {
            this.zzami.zzame.release();
            this.zzami.zzamd.notifyAll();
            if (this == this.zzami.zzalx) {
                this.zzami.zzalx = null;
            } else if (this == this.zzami.zzaly) {
                this.zzami.zzaly = null;
            } else {
                this.zzami.zzgf().zzis().log("Current scheduler thread is neither worker nor network");
            }
        }
    }

    public final void zzju() {
        synchronized (this.zzaml) {
            this.zzaml.notifyAll();
        }
    }
}
