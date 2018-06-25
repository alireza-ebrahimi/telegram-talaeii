package org.telegram.customization.service;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLoader.FileLoaderDelegate;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$InputEncryptedFile;
import org.telegram.tgnet.TLRPC$InputFile;
import utils.Utilities;
import utils.app.AppPreferences;
import utils.view.Constants;

public class DownloadManagerService extends BaseService implements NotificationCenterDelegate {
    int classGuid;
    boolean isForce;
    private int loadedIndex = 0;
    FileLoaderDelegate loaderDelegate;
    private int localDocId;
    ArrayList<MessageObject> messArr = new ArrayList();

    public static Calendar getCalender() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 11);
        calendar.set(12, Utilities.getRandomMinute());
        calendar.set(13, 0);
        return calendar;
    }

    public static void registerService(Context context) {
        Intent intent = new Intent(context, DownloadManagerService.class);
        String[] i = AppPreferences.getStartDownloadTime(context).split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, Integer.parseInt(i[0]));
        calendar.set(12, Integer.parseInt(i[1]));
        calendar.set(13, 0);
        registerService(context, intent, calendar, 0);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isForce = false;
        if (!(intent == null || intent.getExtras() == null)) {
            isForce = intent.getBooleanExtra(Constants.EXTRA_IS_FORCE, false);
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.DownloadServiceStart, new Object[]{Integer.valueOf(10)});
        if (AppPreferences.isRegistered(this)) {
            this.classGuid = ConnectionsManager.getInstance().generateClassGuid();
            if (!isForce && AppPreferences.isTurnWifiOn(getApplicationContext())) {
                ((WifiManager) getSystemService("wifi")).setWifiEnabled(true);
            }
            String[] i2 = AppPreferences.getEndDownloadTime(getApplicationContext()).split(":");
            final boolean z = isForce;
            final int parseInt = Integer.parseInt(i2[0]);
            final int parseInt2 = Integer.parseInt(i2[1]);
            this.loaderDelegate = new FileLoaderDelegate() {
                public void fileDidLoaded(String location, File finalFile, int type) {
                    if (finalFile.getName().contains(String.valueOf(DownloadManagerService.this.localDocId))) {
                        DownloadManagerService.this.loadedIndex = DownloadManagerService.this.loadedIndex + 1;
                        if (DownloadManagerService.this.loadedIndex >= DownloadManagerService.this.messArr.size()) {
                            DownloadManagerService.this.stopSelf();
                        }
                        FileLoader.getInstance().loadFile(((MessageObject) DownloadManagerService.this.messArr.get(DownloadManagerService.this.loadedIndex)).getDocument(), true, 0);
                        DownloadManagerService.this.localDocId = ((MessageObject) DownloadManagerService.this.messArr.get(DownloadManagerService.this.loadedIndex)).getDocument().dc_id;
                    }
                }

                public void fileUploadProgressChanged(String location, float progress, boolean isEncrypted) {
                }

                public void fileDidUploaded(String location, TLRPC$InputFile inputFile, TLRPC$InputEncryptedFile inputEncryptedFile, byte[] key, byte[] iv, long totalFileSize) {
                }

                public void fileDidFailedUpload(String location, boolean isEncrypted) {
                }

                public void fileDidFailedLoad(String location, int state) {
                }

                public void fileLoadProgressChanged(String location, float progress) {
                    if (!z) {
                        int hours = new Time(System.currentTimeMillis()).getHours();
                        int minutes = new Time(System.currentTimeMillis()).getMinutes();
                        if (hours >= parseInt && minutes >= parseInt2) {
                            DownloadManagerService.this.stopSelf();
                        }
                    }
                }
            };
            FileLoader.getInstance().addDelegate(this.loaderDelegate);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDidLoaded);
            MessagesController.getInstance().loadMessages(111444999, 1000, 0, 0, true, 0, this.classGuid, 0, 0, false, 0);
            return super.onStartCommand(intent, flags, startId);
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.messagesDidLoaded) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDidLoaded);
            this.messArr = (ArrayList) args[2];
            boolean isAllDownloaded = true;
            Iterator it = this.messArr.iterator();
            while (it.hasNext()) {
                MessageObject msg = (MessageObject) it.next();
                if (!msg.mediaExists) {
                    FileLoader.getInstance().loadFile(msg.getDocument(), true, 0);
                    if (!(msg == null || msg.getDocument() == null)) {
                        this.localDocId = msg.getDocument().dc_id;
                    }
                    isAllDownloaded = false;
                    if (isAllDownloaded) {
                        stopSelf();
                    }
                }
            }
            if (isAllDownloaded) {
                stopSelf();
            }
        }
    }

    public void onDestroy() {
        Iterator it = this.messArr.iterator();
        while (it.hasNext()) {
            FileLoader.getInstance().cancelLoadFile(((MessageObject) it.next()).getDocument());
        }
        if (!this.isForce && AppPreferences.isTurnWifiOff(getApplicationContext())) {
            ((WifiManager) getSystemService("wifi")).setWifiEnabled(false);
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.DownloadServiceStop, new Object[]{Integer.valueOf(10)});
        AppPreferences.setDownloadScheduled(getApplicationContext(), false);
        super.onDestroy();
    }
}
