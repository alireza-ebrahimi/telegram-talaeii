package org.telegram.messenger.support.customtabs;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

class CustomTabsSessionToken$1 extends CustomTabsCallback {
    final /* synthetic */ CustomTabsSessionToken this$0;

    CustomTabsSessionToken$1(CustomTabsSessionToken this$0) {
        this.this$0 = this$0;
    }

    public void onNavigationEvent(int navigationEvent, Bundle extras) {
        try {
            CustomTabsSessionToken.access$000(this.this$0).onNavigationEvent(navigationEvent, extras);
        } catch (RemoteException e) {
            Log.e("CustomTabsSessionToken", "RemoteException during ICustomTabsCallback transaction");
        }
    }

    public void extraCallback(String callbackName, Bundle args) {
        try {
            CustomTabsSessionToken.access$000(this.this$0).extraCallback(callbackName, args);
        } catch (RemoteException e) {
            Log.e("CustomTabsSessionToken", "RemoteException during ICustomTabsCallback transaction");
        }
    }

    public void onMessageChannelReady(Bundle extras) {
        try {
            CustomTabsSessionToken.access$000(this.this$0).onMessageChannelReady(extras);
        } catch (RemoteException e) {
            Log.e("CustomTabsSessionToken", "RemoteException during ICustomTabsCallback transaction");
        }
    }

    public void onPostMessage(String message, Bundle extras) {
        try {
            CustomTabsSessionToken.access$000(this.this$0).onPostMessage(message, extras);
        } catch (RemoteException e) {
            Log.e("CustomTabsSessionToken", "RemoteException during ICustomTabsCallback transaction");
        }
    }
}
