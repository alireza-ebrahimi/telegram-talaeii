package org.telegram.messenger;

import java.io.File;

class UserConfig$1 implements Runnable {
    final /* synthetic */ File val$configFile;

    UserConfig$1(File file) {
        this.val$configFile = file;
    }

    public void run() {
        UserConfig.saveConfig(true, this.val$configFile);
    }
}
