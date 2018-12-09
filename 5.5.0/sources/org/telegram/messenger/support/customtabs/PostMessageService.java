package org.telegram.messenger.support.customtabs;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import org.telegram.messenger.support.customtabs.IPostMessageService.Stub;

public class PostMessageService extends Service {
    private Stub mBinder = new C36481();

    /* renamed from: org.telegram.messenger.support.customtabs.PostMessageService$1 */
    class C36481 extends Stub {
        C36481() {
        }

        public void onMessageChannelReady(ICustomTabsCallback iCustomTabsCallback, Bundle bundle) {
            iCustomTabsCallback.onMessageChannelReady(bundle);
        }

        public void onPostMessage(ICustomTabsCallback iCustomTabsCallback, String str, Bundle bundle) {
            iCustomTabsCallback.onPostMessage(str, bundle);
        }
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}
