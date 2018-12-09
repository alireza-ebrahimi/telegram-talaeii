package org.telegram.messenger.support.customtabsclient.shared;

import android.content.ComponentName;
import java.lang.ref.WeakReference;
import org.telegram.messenger.support.customtabs.CustomTabsClient;
import org.telegram.messenger.support.customtabs.CustomTabsServiceConnection;

public class ServiceConnection extends CustomTabsServiceConnection {
    private WeakReference<ServiceConnectionCallback> mConnectionCallback;

    public ServiceConnection(ServiceConnectionCallback serviceConnectionCallback) {
        this.mConnectionCallback = new WeakReference(serviceConnectionCallback);
    }

    public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
        ServiceConnectionCallback serviceConnectionCallback = (ServiceConnectionCallback) this.mConnectionCallback.get();
        if (serviceConnectionCallback != null) {
            serviceConnectionCallback.onServiceConnected(customTabsClient);
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        ServiceConnectionCallback serviceConnectionCallback = (ServiceConnectionCallback) this.mConnectionCallback.get();
        if (serviceConnectionCallback != null) {
            serviceConnectionCallback.onServiceDisconnected();
        }
    }
}
