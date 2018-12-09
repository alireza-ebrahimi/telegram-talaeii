package org.telegram.messenger;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.google.android.gms.gcm.GcmListenerService;
import org.json.JSONObject;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$TL_messageMediaEmpty;
import org.telegram.tgnet.TLRPC$TL_updateServiceNotification;
import org.telegram.tgnet.TLRPC$TL_updates;

public class GcmPushListenerService extends GcmListenerService {
    public static final int NOTIFICATION_ID = 1;

    public void onMessageReceived(String str, final Bundle bundle) {
        FileLog.m13725d("GCM received bundle: " + bundle + " from: " + str);
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                ApplicationLoader.postInitApplication();
                try {
                    String string = bundle.getString("loc_key");
                    if ("DC_UPDATE".equals(string)) {
                        JSONObject jSONObject = new JSONObject(bundle.getString("custom"));
                        int i = jSONObject.getInt("dc");
                        String[] split = jSONObject.getString("addr").split(":");
                        if (split.length == 2) {
                            ConnectionsManager.getInstance().applyDatacenterAddress(i, split[0], Integer.parseInt(split[1]));
                        } else {
                            return;
                        }
                    } else if ("MESSAGE_ANNOUNCEMENT".equals(string)) {
                        long longValue;
                        r0 = bundle.get("google.sent_time");
                        try {
                            longValue = r0 instanceof String ? Utilities.parseLong((String) r0).longValue() : r0 instanceof Long ? ((Long) r0).longValue() : System.currentTimeMillis();
                        } catch (Exception e) {
                            longValue = System.currentTimeMillis();
                        }
                        TLRPC$TL_updateServiceNotification tLRPC$TL_updateServiceNotification = new TLRPC$TL_updateServiceNotification();
                        tLRPC$TL_updateServiceNotification.popup = false;
                        tLRPC$TL_updateServiceNotification.flags = 2;
                        tLRPC$TL_updateServiceNotification.inbox_date = (int) (longValue / 1000);
                        tLRPC$TL_updateServiceNotification.message = bundle.getString("message");
                        tLRPC$TL_updateServiceNotification.type = "announcement";
                        tLRPC$TL_updateServiceNotification.media = new TLRPC$TL_messageMediaEmpty();
                        final TLRPC$TL_updates tLRPC$TL_updates = new TLRPC$TL_updates();
                        tLRPC$TL_updates.updates.add(tLRPC$TL_updateServiceNotification);
                        Utilities.stageQueue.postRunnable(new Runnable() {
                            public void run() {
                                MessagesController.getInstance().processUpdates(tLRPC$TL_updates, false);
                            }
                        });
                    } else if (VERSION.SDK_INT >= 24 && ApplicationLoader.mainInterfacePaused && UserConfig.isClientActivated() && bundle.get("badge") == null) {
                        r0 = bundle.get("google.sent_time");
                        long longValue2 = r0 instanceof String ? Utilities.parseLong((String) r0).longValue() : r0 instanceof Long ? ((Long) r0).longValue() : -1;
                        if (longValue2 == -1 || UserConfig.lastAppPauseTime < longValue2) {
                            ConnectivityManager connectivityManager = (ConnectivityManager) ApplicationLoader.applicationContext.getSystemService("connectivity");
                            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                            if (BuildVars.DEBUG_VERSION) {
                                FileLog.m13725d("try show notification in background with time " + longValue2 + " with nework info " + activeNetworkInfo + " and status " + connectivityManager.getRestrictBackgroundStatus());
                            }
                            if (connectivityManager.getRestrictBackgroundStatus() == 3 && activeNetworkInfo.getType() == 0) {
                                NotificationsController.getInstance().showSingleBackgroundNotification();
                            }
                        }
                    }
                } catch (Throwable e2) {
                    FileLog.m13728e(e2);
                }
                ConnectionsManager.onInternalPushReceived();
                ConnectionsManager.getInstance().resumeNetworkMaybe();
            }
        });
    }
}
