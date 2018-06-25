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

    public void onMessageReceived(String from, final Bundle bundle) {
        FileLog.d("GCM received bundle: " + bundle + " from: " + from);
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                ApplicationLoader.postInitApplication();
                try {
                    String key = bundle.getString("loc_key");
                    if ("DC_UPDATE".equals(key)) {
                        JSONObject object = new JSONObject(bundle.getString("custom"));
                        int dc = object.getInt("dc");
                        String[] parts = object.getString("addr").split(":");
                        if (parts.length == 2) {
                            ConnectionsManager.getInstance().applyDatacenterAddress(dc, parts[0], Integer.parseInt(parts[1]));
                        } else {
                            return;
                        }
                    } else if ("MESSAGE_ANNOUNCEMENT".equals(key)) {
                        obj = bundle.get("google.sent_time");
                        try {
                            if (obj instanceof String) {
                                time = Utilities.parseLong((String) obj).longValue();
                            } else if (obj instanceof Long) {
                                time = ((Long) obj).longValue();
                            } else {
                                time = System.currentTimeMillis();
                            }
                        } catch (Exception e) {
                            time = System.currentTimeMillis();
                        }
                        TLRPC$TL_updateServiceNotification update = new TLRPC$TL_updateServiceNotification();
                        update.popup = false;
                        update.flags = 2;
                        update.inbox_date = (int) (time / 1000);
                        update.message = bundle.getString("message");
                        update.type = "announcement";
                        update.media = new TLRPC$TL_messageMediaEmpty();
                        TLRPC$TL_updates updates = new TLRPC$TL_updates();
                        updates.updates.add(update);
                        final TLRPC$TL_updates tLRPC$TL_updates = updates;
                        Utilities.stageQueue.postRunnable(new Runnable() {
                            public void run() {
                                MessagesController.getInstance().processUpdates(tLRPC$TL_updates, false);
                            }
                        });
                    } else if (VERSION.SDK_INT >= 24 && ApplicationLoader.mainInterfacePaused && UserConfig.isClientActivated() && bundle.get("badge") == null) {
                        obj = bundle.get("google.sent_time");
                        if (obj instanceof String) {
                            time = Utilities.parseLong((String) obj).longValue();
                        } else if (obj instanceof Long) {
                            time = ((Long) obj).longValue();
                        } else {
                            time = -1;
                        }
                        if (time == -1 || UserConfig.lastAppPauseTime < time) {
                            ConnectivityManager connectivityManager = (ConnectivityManager) ApplicationLoader.applicationContext.getSystemService("connectivity");
                            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                            if (BuildVars.DEBUG_VERSION) {
                                FileLog.d("try show notification in background with time " + time + " with nework info " + netInfo + " and status " + connectivityManager.getRestrictBackgroundStatus());
                            }
                            if (connectivityManager.getRestrictBackgroundStatus() == 3 && netInfo.getType() == 0) {
                                NotificationsController.getInstance().showSingleBackgroundNotification();
                            }
                        }
                    }
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
                ConnectionsManager.onInternalPushReceived();
                ConnectionsManager.getInstance().resumeNetworkMaybe();
            }
        });
    }
}
