package org.telegram.messenger;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationManagerCompat;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.LocationController.SharingLocationInfo;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.ui.LaunchActivity;

public class LocationSharingService extends Service implements NotificationCenterDelegate {
    private Builder builder;
    private Handler handler;
    private Runnable runnable;

    /* renamed from: org.telegram.messenger.LocationSharingService$1 */
    class C14111 implements Runnable {

        /* renamed from: org.telegram.messenger.LocationSharingService$1$1 */
        class C14101 implements Runnable {
            C14101() {
            }

            public void run() {
                LocationController.getInstance().update();
            }
        }

        C14111() {
        }

        public void run() {
            LocationSharingService.this.handler.postDelayed(LocationSharingService.this.runnable, 60000);
            Utilities.stageQueue.postRunnable(new C14101());
        }
    }

    /* renamed from: org.telegram.messenger.LocationSharingService$2 */
    class C14122 implements Runnable {
        C14122() {
        }

        public void run() {
            if (LocationController.getInstance().sharingLocationsUI.isEmpty()) {
                LocationSharingService.this.stopSelf();
            } else {
                LocationSharingService.this.updateNotification();
            }
        }
    }

    public LocationSharingService() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.liveLocationsChanged);
    }

    public void onCreate() {
        super.onCreate();
        this.handler = new Handler();
        this.runnable = new C14111();
        this.handler.postDelayed(this.runnable, 60000);
    }

    public IBinder onBind(Intent arg2) {
        return null;
    }

    public void onDestroy() {
        if (this.handler != null) {
            this.handler.removeCallbacks(this.runnable);
        }
        stopForeground(true);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.liveLocationsChanged);
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.liveLocationsChanged && this.handler != null) {
            this.handler.post(new C14122());
        }
    }

    private void updateNotification() {
        if (this.builder != null) {
            String param;
            ArrayList<SharingLocationInfo> infos = LocationController.getInstance().sharingLocationsUI;
            if (infos.size() == 1) {
                int lower_id = (int) ((SharingLocationInfo) infos.get(0)).messageObject.getDialogId();
                if (lower_id > 0) {
                    param = UserObject.getFirstName(MessagesController.getInstance().getUser(Integer.valueOf(lower_id)));
                } else {
                    TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
                    if (chat != null) {
                        param = chat.title;
                    } else {
                        param = "";
                    }
                }
            } else {
                param = LocaleController.formatPluralString("Chats", LocationController.getInstance().sharingLocationsUI.size());
            }
            String str = String.format(LocaleController.getString("AttachLiveLocationIsSharing", R.string.AttachLiveLocationIsSharing), new Object[]{LocaleController.getString("AttachLiveLocation", R.string.AttachLiveLocation), param});
            this.builder.setTicker(str);
            this.builder.setContentText(str);
            NotificationManagerCompat.from(ApplicationLoader.applicationContext).notify(6, this.builder.build());
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (LocationController.getInstance().sharingLocationsUI.isEmpty()) {
            stopSelf();
        }
        if (this.builder == null) {
            Intent intent2 = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
            intent2.setAction("org.tmessages.openlocations");
            intent2.setFlags(32768);
            PendingIntent contentIntent = PendingIntent.getActivity(ApplicationLoader.applicationContext, 0, intent2, 0);
            this.builder = new Builder(ApplicationLoader.applicationContext);
            this.builder.setWhen(System.currentTimeMillis());
            this.builder.setSmallIcon(R.drawable.notification);
            this.builder.setContentIntent(contentIntent);
            this.builder.setContentTitle(LocaleController.getString("AppName", R.string.AppName));
            this.builder.addAction(0, LocaleController.getString("StopLiveLocation", R.string.StopLiveLocation), PendingIntent.getBroadcast(ApplicationLoader.applicationContext, 2, new Intent(ApplicationLoader.applicationContext, StopLiveLocationReceiver.class), 134217728));
        }
        startForeground(6, this.builder.build());
        updateNotification();
        return 2;
    }
}
