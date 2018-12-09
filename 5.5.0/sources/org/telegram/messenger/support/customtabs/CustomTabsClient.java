package org.telegram.messenger.support.customtabs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.support.customtabs.ICustomTabsCallback.Stub;

public class CustomTabsClient {
    private final ICustomTabsService mService;
    private final ComponentName mServiceComponentName;

    CustomTabsClient(ICustomTabsService iCustomTabsService, ComponentName componentName) {
        this.mService = iCustomTabsService;
        this.mServiceComponentName = componentName;
    }

    public static boolean bindCustomTabsService(Context context, String str, CustomTabsServiceConnection customTabsServiceConnection) {
        Intent intent = new Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
        if (!TextUtils.isEmpty(str)) {
            intent.setPackage(str);
        }
        return context.bindService(intent, customTabsServiceConnection, 33);
    }

    public static boolean connectAndInitialize(Context context, String str) {
        boolean z = false;
        if (str != null) {
            final Context applicationContext = context.getApplicationContext();
            try {
                z = bindCustomTabsService(applicationContext, str, new CustomTabsServiceConnection() {
                    public final void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                        customTabsClient.warmup(0);
                        applicationContext.unbindService(this);
                    }

                    public final void onServiceDisconnected(ComponentName componentName) {
                    }
                });
            } catch (SecurityException e) {
            }
        }
        return z;
    }

    public static String getPackageName(Context context, List<String> list) {
        return getPackageName(context, list, false);
    }

    public static String getPackageName(Context context, List<String> list, boolean z) {
        PackageManager packageManager = context.getPackageManager();
        if (list == null) {
            List arrayList = new ArrayList();
        } else {
            List<String> list2 = list;
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://"));
        if (!z) {
            ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
            if (resolveActivity != null) {
                String str = resolveActivity.activityInfo.packageName;
                List arrayList2 = new ArrayList(arrayList.size() + 1);
                arrayList2.add(str);
                if (list != null) {
                    arrayList2.addAll(list);
                }
                arrayList = arrayList2;
            }
        }
        intent = new Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
        for (String str2 : r0) {
            intent.setPackage(str2);
            if (packageManager.resolveService(intent, 0) != null) {
                return str2;
            }
        }
        return null;
    }

    public Bundle extraCommand(String str, Bundle bundle) {
        try {
            return this.mService.extraCommand(str, bundle);
        } catch (RemoteException e) {
            return null;
        }
    }

    public CustomTabsSession newSession(final CustomTabsCallback customTabsCallback) {
        ICustomTabsCallback c36422 = new Stub() {
            private Handler mHandler = new Handler(Looper.getMainLooper());

            public void extraCallback(final String str, final Bundle bundle) {
                if (customTabsCallback != null) {
                    this.mHandler.post(new Runnable() {
                        public void run() {
                            customTabsCallback.extraCallback(str, bundle);
                        }
                    });
                }
            }

            public void onMessageChannelReady(final Bundle bundle) {
                if (customTabsCallback != null) {
                    this.mHandler.post(new Runnable() {
                        public void run() {
                            customTabsCallback.onMessageChannelReady(bundle);
                        }
                    });
                }
            }

            public void onNavigationEvent(final int i, final Bundle bundle) {
                if (customTabsCallback != null) {
                    this.mHandler.post(new Runnable() {
                        public void run() {
                            customTabsCallback.onNavigationEvent(i, bundle);
                        }
                    });
                }
            }

            public void onPostMessage(final String str, final Bundle bundle) {
                if (customTabsCallback != null) {
                    this.mHandler.post(new Runnable() {
                        public void run() {
                            customTabsCallback.onPostMessage(str, bundle);
                        }
                    });
                }
            }
        };
        try {
            return !this.mService.newSession(c36422) ? null : new CustomTabsSession(this.mService, c36422, this.mServiceComponentName);
        } catch (RemoteException e) {
            return null;
        }
    }

    public boolean warmup(long j) {
        try {
            return this.mService.warmup(j);
        } catch (RemoteException e) {
            return false;
        }
    }
}
