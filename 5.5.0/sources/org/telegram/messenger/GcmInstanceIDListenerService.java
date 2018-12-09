package org.telegram.messenger;

import android.content.Intent;
import com.google.android.gms.iid.InstanceIDListenerService;

public class GcmInstanceIDListenerService extends InstanceIDListenerService {

    /* renamed from: org.telegram.messenger.GcmInstanceIDListenerService$1 */
    class C30561 implements Runnable {
        C30561() {
        }

        public void run() {
            ApplicationLoader.postInitApplication();
            GcmInstanceIDListenerService.this.startService(new Intent(ApplicationLoader.applicationContext, GcmRegistrationIntentService.class));
        }
    }

    public void onTokenRefresh() {
        AndroidUtilities.runOnUIThread(new C30561());
    }
}
