package org.telegram.messenger.browser;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import java.lang.ref.WeakReference;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.CustomTabsCopyReceiver;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.ShareBroadcastReceiver;
import org.telegram.messenger.support.customtabs.CustomTabsCallback;
import org.telegram.messenger.support.customtabs.CustomTabsClient;
import org.telegram.messenger.support.customtabs.CustomTabsIntent.Builder;
import org.telegram.messenger.support.customtabs.CustomTabsServiceConnection;
import org.telegram.messenger.support.customtabs.CustomTabsSession;
import org.telegram.messenger.support.customtabsclient.shared.CustomTabsHelper;
import org.telegram.messenger.support.customtabsclient.shared.ServiceConnection;
import org.telegram.messenger.support.customtabsclient.shared.ServiceConnectionCallback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_messages_getWebPagePreview;
import org.telegram.tgnet.TLRPC$TL_webPage;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.LaunchActivity;

public class Browser {
    private static WeakReference<Activity> currentCustomTabsActivity;
    private static CustomTabsClient customTabsClient;
    private static WeakReference<CustomTabsSession> customTabsCurrentSession;
    private static String customTabsPackageToBind;
    private static CustomTabsServiceConnection customTabsServiceConnection;
    private static CustomTabsSession customTabsSession;

    /* renamed from: org.telegram.messenger.browser.Browser$1 */
    static class C34231 implements ServiceConnectionCallback {
        C34231() {
        }

        public void onServiceConnected(CustomTabsClient customTabsClient) {
            Browser.customTabsClient = customTabsClient;
            if (MediaController.getInstance().canCustomTabs() && Browser.customTabsClient != null) {
                try {
                    Browser.customTabsClient.warmup(0);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        }

        public void onServiceDisconnected() {
            Browser.customTabsClient = null;
        }
    }

    private static class NavigationCallback extends CustomTabsCallback {
        private NavigationCallback() {
        }

        public void onNavigationEvent(int i, Bundle bundle) {
        }
    }

    public static void bindCustomTabsService(Activity activity) {
        Activity activity2 = null;
        if (currentCustomTabsActivity != null) {
            activity2 = (Activity) currentCustomTabsActivity.get();
        }
        if (!(activity2 == null || activity2 == activity)) {
            unbindCustomTabsService(activity2);
        }
        if (customTabsClient == null) {
            currentCustomTabsActivity = new WeakReference(activity);
            try {
                if (TextUtils.isEmpty(customTabsPackageToBind)) {
                    customTabsPackageToBind = CustomTabsHelper.getPackageNameToUse(activity);
                    if (customTabsPackageToBind == null) {
                        return;
                    }
                }
                customTabsServiceConnection = new ServiceConnection(new C34231());
                if (!CustomTabsClient.bindCustomTabsService(activity, customTabsPackageToBind, customTabsServiceConnection)) {
                    customTabsServiceConnection = null;
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    private static CustomTabsSession getCurrentSession() {
        return customTabsCurrentSession == null ? null : (CustomTabsSession) customTabsCurrentSession.get();
    }

    private static CustomTabsSession getSession() {
        if (customTabsClient == null) {
            customTabsSession = null;
        } else if (customTabsSession == null) {
            customTabsSession = customTabsClient.newSession(new NavigationCallback());
            setCurrentSession(customTabsSession);
        }
        return customTabsSession;
    }

    public static boolean isInternalUri(Uri uri, boolean[] zArr) {
        String host = uri.getHost();
        Object toLowerCase = host != null ? host.toLowerCase() : TtmlNode.ANONYMOUS_REGION_ID;
        if ("tg".equals(uri.getScheme())) {
            return true;
        }
        if ("telegram.dog".equals(toLowerCase)) {
            host = uri.getPath();
            if (host != null && host.length() > 1) {
                host = host.substring(1).toLowerCase();
                if (!host.startsWith("blog") && !host.equals("iv") && !host.startsWith("faq") && !host.equals("apps")) {
                    return true;
                }
                if (zArr != null) {
                    zArr[0] = true;
                }
                return false;
            }
        } else if ("telegram.me".equals(toLowerCase) || "t.me".equals(toLowerCase) || "telesco.pe".equals(toLowerCase)) {
            host = uri.getPath();
            if (host != null && host.length() > 1) {
                if (!host.substring(1).toLowerCase().equals("iv")) {
                    return true;
                }
                if (zArr != null) {
                    zArr[0] = true;
                }
                return false;
            }
        }
        return false;
    }

    public static boolean isInternalUrl(String str, boolean[] zArr) {
        return isInternalUri(Uri.parse(str), zArr);
    }

    public static void openUrl(Context context, Uri uri) {
        openUrl(context, uri, true);
    }

    public static void openUrl(Context context, Uri uri, boolean z) {
        openUrl(context, uri, z, true);
    }

    public static void openUrl(final Context context, final Uri uri, final boolean z, boolean z2) {
        final int sendRequest;
        List queryIntentActivities;
        int i;
        Intent intent;
        List list = null;
        if (context != null && uri != null) {
            boolean[] zArr = new boolean[]{false};
            boolean isInternalUri = isInternalUri(uri, zArr);
            if (z2) {
                try {
                    if (uri.getHost().toLowerCase().equals("telegra.ph") || uri.toString().contains("telegram.org/faq")) {
                        final AlertDialog[] alertDialogArr = new AlertDialog[]{new AlertDialog(context, 1)};
                        TLObject tLRPC$TL_messages_getWebPagePreview = new TLRPC$TL_messages_getWebPagePreview();
                        tLRPC$TL_messages_getWebPagePreview.message = uri.toString();
                        sendRequest = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getWebPagePreview, new RequestDelegate() {
                            public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        int i;
                                        try {
                                            alertDialogArr[0].dismiss();
                                        } catch (Throwable th) {
                                        }
                                        alertDialogArr[0] = null;
                                        if (tLObject instanceof TLRPC$TL_messageMediaWebPage) {
                                            TLRPC$TL_messageMediaWebPage tLRPC$TL_messageMediaWebPage = (TLRPC$TL_messageMediaWebPage) tLObject;
                                            if ((tLRPC$TL_messageMediaWebPage.webpage instanceof TLRPC$TL_webPage) && tLRPC$TL_messageMediaWebPage.webpage.cached_page != null) {
                                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.openArticle, tLRPC$TL_messageMediaWebPage.webpage, uri.toString());
                                                i = 1;
                                                if (i == 0) {
                                                    Browser.openUrl(context, uri, z, false);
                                                }
                                            }
                                        }
                                        boolean z = false;
                                        if (i == 0) {
                                            Browser.openUrl(context, uri, z, false);
                                        }
                                    }
                                });
                            }
                        });
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.messenger.browser.Browser$3$1 */
                            class C34261 implements OnClickListener {
                                C34261() {
                                }

                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ConnectionsManager.getInstance().cancelRequest(sendRequest, true);
                                    try {
                                        dialogInterface.dismiss();
                                    } catch (Throwable e) {
                                        FileLog.m13728e(e);
                                    }
                                }
                            }

                            public void run() {
                                if (alertDialogArr[0] != null) {
                                    try {
                                        alertDialogArr[0].setMessage(LocaleController.getString("Loading", R.string.Loading));
                                        alertDialogArr[0].setCanceledOnTouchOutside(false);
                                        alertDialogArr[0].setCancelable(false);
                                        alertDialogArr[0].setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C34261());
                                        alertDialogArr[0].show();
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }, 1000);
                        return;
                    }
                } catch (Exception e) {
                }
            }
            try {
                String toLowerCase = uri.getScheme() != null ? uri.getScheme().toLowerCase() : TtmlNode.ANONYMOUS_REGION_ID;
                if (z && MediaController.getInstance().canCustomTabs() && !isInternalUri && !toLowerCase.equals("tel")) {
                    PendingIntent broadcast;
                    Builder builder;
                    String[] strArr;
                    String[] strArr2;
                    int i2;
                    try {
                        List queryIntentActivities2 = context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("http://www.google.com")), 0);
                        if (queryIntentActivities2 == null || queryIntentActivities2.isEmpty()) {
                            strArr = null;
                        } else {
                            strArr2 = new String[queryIntentActivities2.size()];
                            int i3 = 0;
                            while (i3 < queryIntentActivities2.size()) {
                                try {
                                    strArr2[i3] = ((ResolveInfo) queryIntentActivities2.get(i3)).activityInfo.packageName;
                                    FileLog.m13725d("default browser name = " + strArr2[i3]);
                                    i3++;
                                } catch (Exception e2) {
                                    strArr = strArr2;
                                }
                            }
                            strArr = strArr2;
                        }
                        strArr2 = strArr;
                        try {
                            queryIntentActivities = context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", uri), 0);
                            if (strArr2 == null) {
                                i2 = 0;
                                while (i2 < queryIntentActivities.size()) {
                                    try {
                                        for (String equals : strArr2) {
                                            if (equals.equals(((ResolveInfo) queryIntentActivities.get(i2)).activityInfo.packageName)) {
                                                queryIntentActivities.remove(i2);
                                                i = i2 - 1;
                                                break;
                                            }
                                        }
                                        i = i2;
                                        i2 = i + 1;
                                    } catch (Exception e3) {
                                        list = queryIntentActivities;
                                    }
                                }
                            } else {
                                sendRequest = 0;
                                while (sendRequest < queryIntentActivities.size()) {
                                    if (!((ResolveInfo) queryIntentActivities.get(sendRequest)).activityInfo.packageName.toLowerCase().contains("browser") || ((ResolveInfo) queryIntentActivities.get(sendRequest)).activityInfo.packageName.toLowerCase().contains("chrome")) {
                                        queryIntentActivities.remove(sendRequest);
                                        i = sendRequest - 1;
                                    } else {
                                        i = sendRequest;
                                    }
                                    sendRequest = i + 1;
                                }
                            }
                            if (BuildVars.DEBUG_VERSION) {
                                for (sendRequest = 0; sendRequest < queryIntentActivities.size(); sendRequest++) {
                                    FileLog.m13725d("device has " + ((ResolveInfo) queryIntentActivities.get(sendRequest)).activityInfo.packageName + " to open " + uri.toString());
                                }
                            }
                        } catch (Exception e4) {
                            queryIntentActivities = list;
                            intent = new Intent(ApplicationLoader.applicationContext, ShareBroadcastReceiver.class);
                            intent.setAction("android.intent.action.SEND");
                            broadcast = PendingIntent.getBroadcast(ApplicationLoader.applicationContext, 0, new Intent(ApplicationLoader.applicationContext, CustomTabsCopyReceiver.class), 134217728);
                            builder = new Builder(getSession());
                            builder.addMenuItem(LocaleController.getString("CopyLink", R.string.CopyLink), broadcast);
                            builder.setToolbarColor(Theme.getColor(Theme.key_actionBarDefault));
                            builder.setShowTitle(true);
                            builder.setActionButton(BitmapFactory.decodeResource(context.getResources(), R.drawable.abc_ic_menu_share_mtrl_alpha), LocaleController.getString("ShareFile", R.string.ShareFile), PendingIntent.getBroadcast(ApplicationLoader.applicationContext, 0, intent, 0), false);
                            builder.build().launchUrl(context, uri);
                            return;
                        }
                    } catch (Exception e5) {
                        strArr = null;
                        strArr2 = strArr;
                        queryIntentActivities = context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", uri), 0);
                        if (strArr2 == null) {
                            sendRequest = 0;
                            while (sendRequest < queryIntentActivities.size()) {
                                if (((ResolveInfo) queryIntentActivities.get(sendRequest)).activityInfo.packageName.toLowerCase().contains("browser")) {
                                }
                                queryIntentActivities.remove(sendRequest);
                                i = sendRequest - 1;
                                sendRequest = i + 1;
                            }
                        } else {
                            i2 = 0;
                            while (i2 < queryIntentActivities.size()) {
                                while (r5 < strArr2.length) {
                                    if (equals.equals(((ResolveInfo) queryIntentActivities.get(i2)).activityInfo.packageName)) {
                                        queryIntentActivities.remove(i2);
                                        i = i2 - 1;
                                        break;
                                    }
                                }
                                i = i2;
                                i2 = i + 1;
                            }
                        }
                        if (BuildVars.DEBUG_VERSION) {
                            for (sendRequest = 0; sendRequest < queryIntentActivities.size(); sendRequest++) {
                                FileLog.m13725d("device has " + ((ResolveInfo) queryIntentActivities.get(sendRequest)).activityInfo.packageName + " to open " + uri.toString());
                            }
                        }
                        intent = new Intent(ApplicationLoader.applicationContext, ShareBroadcastReceiver.class);
                        intent.setAction("android.intent.action.SEND");
                        broadcast = PendingIntent.getBroadcast(ApplicationLoader.applicationContext, 0, new Intent(ApplicationLoader.applicationContext, CustomTabsCopyReceiver.class), 134217728);
                        builder = new Builder(getSession());
                        builder.addMenuItem(LocaleController.getString("CopyLink", R.string.CopyLink), broadcast);
                        builder.setToolbarColor(Theme.getColor(Theme.key_actionBarDefault));
                        builder.setShowTitle(true);
                        builder.setActionButton(BitmapFactory.decodeResource(context.getResources(), R.drawable.abc_ic_menu_share_mtrl_alpha), LocaleController.getString("ShareFile", R.string.ShareFile), PendingIntent.getBroadcast(ApplicationLoader.applicationContext, 0, intent, 0), false);
                        builder.build().launchUrl(context, uri);
                        return;
                    }
                    if (zArr[0] || r3 == null || r3.isEmpty()) {
                        intent = new Intent(ApplicationLoader.applicationContext, ShareBroadcastReceiver.class);
                        intent.setAction("android.intent.action.SEND");
                        broadcast = PendingIntent.getBroadcast(ApplicationLoader.applicationContext, 0, new Intent(ApplicationLoader.applicationContext, CustomTabsCopyReceiver.class), 134217728);
                        builder = new Builder(getSession());
                        builder.addMenuItem(LocaleController.getString("CopyLink", R.string.CopyLink), broadcast);
                        builder.setToolbarColor(Theme.getColor(Theme.key_actionBarDefault));
                        builder.setShowTitle(true);
                        builder.setActionButton(BitmapFactory.decodeResource(context.getResources(), R.drawable.abc_ic_menu_share_mtrl_alpha), LocaleController.getString("ShareFile", R.string.ShareFile), PendingIntent.getBroadcast(ApplicationLoader.applicationContext, 0, intent, 0), false);
                        builder.build().launchUrl(context, uri);
                        return;
                    }
                }
            } catch (Throwable e6) {
                FileLog.m13728e(e6);
            }
            try {
                intent = new Intent("android.intent.action.VIEW", uri);
                if (isInternalUri) {
                    intent.setComponent(new ComponentName(context.getPackageName(), LaunchActivity.class.getName()));
                }
                intent.putExtra("create_new_tab", true);
                intent.putExtra("com.android.browser.application_id", context.getPackageName());
                context.startActivity(intent);
            } catch (Throwable e62) {
                FileLog.m13728e(e62);
            }
        }
    }

    public static void openUrl(Context context, String str) {
        if (str != null) {
            openUrl(context, Uri.parse(str), true);
        }
    }

    public static void openUrl(Context context, String str, boolean z) {
        if (context != null && str != null) {
            openUrl(context, Uri.parse(str), z);
        }
    }

    public static void openUrlSls(Context context, Uri uri, boolean z) {
        if (context != null && uri != null) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", uri);
                intent.addFlags(ErrorDialogData.BINDER_CRASH);
                intent.putExtra("com.android.browser.application_id", context.getPackageName());
                context.startActivity(intent);
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    private static void setCurrentSession(CustomTabsSession customTabsSession) {
        customTabsCurrentSession = new WeakReference(customTabsSession);
    }

    public static void unbindCustomTabsService(Activity activity) {
        if (customTabsServiceConnection != null) {
            if ((currentCustomTabsActivity == null ? null : (Activity) currentCustomTabsActivity.get()) == activity) {
                currentCustomTabsActivity.clear();
            }
            try {
                activity.unbindService(customTabsServiceConnection);
            } catch (Exception e) {
            }
            customTabsClient = null;
            customTabsSession = null;
        }
    }
}
