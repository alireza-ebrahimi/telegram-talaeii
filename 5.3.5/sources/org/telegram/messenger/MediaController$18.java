package org.telegram.messenger;

import java.io.File;
import java.util.concurrent.Semaphore;

class MediaController$18 implements Runnable {
    final /* synthetic */ MediaController this$0;
    final /* synthetic */ File val$cacheFile;
    final /* synthetic */ Boolean[] val$result;
    final /* synthetic */ Semaphore val$semaphore;

    MediaController$18(MediaController this$0, Boolean[] boolArr, File file, Semaphore semaphore) {
        this.this$0 = this$0;
        this.val$result = boolArr;
        this.val$cacheFile = file;
        this.val$semaphore = semaphore;
    }

    public void run() {
        boolean z;
        Boolean[] boolArr = this.val$result;
        if (MediaController.access$6200(this.this$0, this.val$cacheFile.getAbsolutePath()) != 0) {
            z = true;
        } else {
            z = false;
        }
        boolArr[0] = Boolean.valueOf(z);
        this.val$semaphore.release();
    }
}
