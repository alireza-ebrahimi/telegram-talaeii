package org.telegram.messenger;

class MediaController$10 implements Runnable {
    final /* synthetic */ MediaController this$0;

    MediaController$10(MediaController this$0) {
        this.this$0 = this$0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r14 = this;
        r13 = 1;
        r2 = r14.this$0;
        r3 = org.telegram.messenger.MediaController.access$4300(r2);
        monitor-enter(r3);
        r2 = r14.this$0;	 Catch:{ all -> 0x00ab }
        r2 = org.telegram.messenger.MediaController.access$2600(r2);	 Catch:{ all -> 0x00ab }
        if (r2 == 0) goto L_0x001d;
    L_0x0010:
        r2 = r14.this$0;	 Catch:{ all -> 0x00ab }
        r2 = org.telegram.messenger.MediaController.access$2600(r2);	 Catch:{ all -> 0x00ab }
        r2 = r2.getPlayState();	 Catch:{ all -> 0x00ab }
        r11 = 3;
        if (r2 == r11) goto L_0x001f;
    L_0x001d:
        monitor-exit(r3);	 Catch:{ all -> 0x00ab }
    L_0x001e:
        return;
    L_0x001f:
        monitor-exit(r3);	 Catch:{ all -> 0x00ab }
        r8 = 0;
        r2 = r14.this$0;
        r3 = org.telegram.messenger.MediaController.access$3800(r2);
        monitor-enter(r3);
        r2 = r14.this$0;	 Catch:{ all -> 0x00ae }
        r2 = org.telegram.messenger.MediaController.access$4000(r2);	 Catch:{ all -> 0x00ae }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x00ae }
        if (r2 != 0) goto L_0x004d;
    L_0x0034:
        r2 = r14.this$0;	 Catch:{ all -> 0x00ae }
        r2 = org.telegram.messenger.MediaController.access$4000(r2);	 Catch:{ all -> 0x00ae }
        r11 = 0;
        r2 = r2.get(r11);	 Catch:{ all -> 0x00ae }
        r0 = r2;
        r0 = (org.telegram.messenger.MediaController$AudioBuffer) r0;	 Catch:{ all -> 0x00ae }
        r8 = r0;
        r2 = r14.this$0;	 Catch:{ all -> 0x00ae }
        r2 = org.telegram.messenger.MediaController.access$4000(r2);	 Catch:{ all -> 0x00ae }
        r11 = 0;
        r2.remove(r11);	 Catch:{ all -> 0x00ae }
    L_0x004d:
        monitor-exit(r3);	 Catch:{ all -> 0x00ae }
        if (r8 == 0) goto L_0x0086;
    L_0x0050:
        r9 = 0;
        r2 = r14.this$0;	 Catch:{ Exception -> 0x00b1 }
        r2 = org.telegram.messenger.MediaController.access$2600(r2);	 Catch:{ Exception -> 0x00b1 }
        r3 = r8.bufferBytes;	 Catch:{ Exception -> 0x00b1 }
        r11 = 0;
        r12 = r8.size;	 Catch:{ Exception -> 0x00b1 }
        r9 = r2.write(r3, r11, r12);	 Catch:{ Exception -> 0x00b1 }
    L_0x0060:
        r2 = r14.this$0;
        org.telegram.messenger.MediaController.access$4408(r2);
        if (r9 <= 0) goto L_0x007d;
    L_0x0067:
        r4 = r8.pcmOffset;
        r2 = r8.finished;
        if (r2 != r13) goto L_0x00b6;
    L_0x006d:
        r6 = r9;
    L_0x006e:
        r2 = r14.this$0;
        r7 = org.telegram.messenger.MediaController.access$4400(r2);
        r2 = new org.telegram.messenger.MediaController$10$1;
        r3 = r14;
        r2.<init>(r4, r6, r7);
        org.telegram.messenger.AndroidUtilities.runOnUIThread(r2);
    L_0x007d:
        r2 = r8.finished;
        if (r2 == r13) goto L_0x0086;
    L_0x0081:
        r2 = r14.this$0;
        org.telegram.messenger.MediaController.access$3700(r2);
    L_0x0086:
        if (r8 == 0) goto L_0x008e;
    L_0x0088:
        if (r8 == 0) goto L_0x0093;
    L_0x008a:
        r2 = r8.finished;
        if (r2 == r13) goto L_0x0093;
    L_0x008e:
        r2 = r14.this$0;
        org.telegram.messenger.MediaController.access$4500(r2);
    L_0x0093:
        if (r8 == 0) goto L_0x001e;
    L_0x0095:
        r2 = r14.this$0;
        r3 = org.telegram.messenger.MediaController.access$3800(r2);
        monitor-enter(r3);
        r2 = r14.this$0;	 Catch:{ all -> 0x00a8 }
        r2 = org.telegram.messenger.MediaController.access$3900(r2);	 Catch:{ all -> 0x00a8 }
        r2.add(r8);	 Catch:{ all -> 0x00a8 }
        monitor-exit(r3);	 Catch:{ all -> 0x00a8 }
        goto L_0x001e;
    L_0x00a8:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x00a8 }
        throw r2;
    L_0x00ab:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x00ab }
        throw r2;
    L_0x00ae:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x00ae }
        throw r2;
    L_0x00b1:
        r10 = move-exception;
        org.telegram.messenger.FileLog.e(r10);
        goto L_0x0060;
    L_0x00b6:
        r6 = -1;
        goto L_0x006e;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController$10.run():void");
    }
}
