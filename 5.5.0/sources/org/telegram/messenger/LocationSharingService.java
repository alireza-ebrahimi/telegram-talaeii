package org.telegram.messenger;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.al.C0266d;
import android.support.v4.app.au;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.LocationController.SharingLocationInfo;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkedTrackBlacklistUtil;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.ui.LaunchActivity;

public class LocationSharingService extends Service implements NotificationCenterDelegate {
    private C0266d builder;
    private Handler handler;
    private Runnable runnable;

    /* renamed from: org.telegram.messenger.LocationSharingService$1 */
    class C31291 implements Runnable {

        /* renamed from: org.telegram.messenger.LocationSharingService$1$1 */
        class C31281 implements Runnable {
            C31281() {
            }

            public void run() {
                LocationController.getInstance().update();
            }
        }

        C31291() {
        }

        public void run() {
            LocationSharingService.this.handler.postDelayed(LocationSharingService.this.runnable, ChunkedTrackBlacklistUtil.DEFAULT_TRACK_BLACKLIST_MS);
            Utilities.stageQueue.postRunnable(new C31281());
        }
    }

    /* renamed from: org.telegram.messenger.LocationSharingService$2 */
    class C31302 implements Runnable {
        C31302() {
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

    private void updateNotification() {
        if (this.builder != null) {
            String firstName;
            ArrayList arrayList = LocationController.getInstance().sharingLocationsUI;
            if (arrayList.size() == 1) {
                int dialogId = (int) ((SharingLocationInfo) arrayList.get(0)).messageObject.getDialogId();
                if (dialogId > 0) {
                    firstName = UserObject.getFirstName(MessagesController.getInstance().getUser(Integer.valueOf(dialogId)));
                } else {
                    Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-dialogId));
                    firstName = chat != null ? chat.title : TtmlNode.ANONYMOUS_REGION_ID;
                }
            } else {
                firstName = LocaleController.formatPluralString("Chats", LocationController.getInstance().sharingLocationsUI.size());
            }
            CharSequence format = String.format(LocaleController.getString("AttachLiveLocationIsSharing", R.string.AttachLiveLocationIsSharing), new Object[]{LocaleController.getString("AttachLiveLocation", R.string.AttachLiveLocation), firstName});
            this.builder.m1249c(format);
            this.builder.m1245b(format);
            au.m1378a(ApplicationLoader.applicationContext).m1383a(6, this.builder.m1242b());
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.liveLocationsChanged && this.handler != null) {
            this.handler.post(new C31302());
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.handler = new Handler();
        this.runnable = new C31291();
        this.handler.postDelayed(this.runnable, ChunkedTrackBlacklistUtil.DEFAULT_TRACK_BLACKLIST_MS);
    }

    public void onDestroy() {
        if (this.handler != null) {
            this.handler.removeCallbacks(this.runnable);
        }
        stopForeground(true);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.liveLocationsChanged);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (LocationController.getInstance().sharingLocationsUI.isEmpty()) {
            stopSelf();
        }
        if (this.builder == null) {
            Intent intent2 = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
            intent2.setAction("org.tmessages.openlocations");
            intent2.setFlags(TLRPC.MESSAGE_FLAG_EDITED);
            PendingIntent activity = PendingIntent.getActivity(ApplicationLoader.applicationContext, 0, intent2, 0);
            this.builder = new C0266d(ApplicationLoader.applicationContext);
            this.builder.m1231a(System.currentTimeMillis());
            this.builder.m1227a((int) R.drawable.notification);
            this.builder.m1232a(activity);
            this.builder.m1238a(LocaleController.getString("AppName", R.string.AppName));
            this.builder.m1230a(0, LocaleController.getString("StopLiveLocation", R.string.StopLiveLocation), PendingIntent.getBroadcast(ApplicationLoader.applicationContext, 2, new Intent(ApplicationLoader.applicationContext, StopLiveLocationReceiver.class), 134217728));
        }
        startForeground(6, this.builder.m1242b());
        updateNotification();
        return 2;
    }
}
