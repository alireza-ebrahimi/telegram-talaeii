package org.telegram.messenger.exoplayer2.util;

public final class ConditionVariable {
    private boolean isOpen;

    public synchronized void block() {
        while (!this.isOpen) {
            wait();
        }
    }

    public synchronized boolean close() {
        boolean z;
        z = this.isOpen;
        this.isOpen = false;
        return z;
    }

    public synchronized boolean open() {
        boolean z = true;
        synchronized (this) {
            if (this.isOpen) {
                z = false;
            } else {
                this.isOpen = true;
                notifyAll();
            }
        }
        return z;
    }
}
