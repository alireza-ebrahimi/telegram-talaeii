package org.telegram.customization.service;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;
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
import org.telegram.tgnet.TLRPC.InputEncryptedFile;
import org.telegram.tgnet.TLRPC.InputFile;
import utils.p178a.C3791b;

public class DownloadManagerService extends C2827a implements NotificationCenterDelegate {
    /* renamed from: a */
    FileLoaderDelegate f9307a;
    /* renamed from: b */
    ArrayList<MessageObject> f9308b = new ArrayList();
    /* renamed from: c */
    int f9309c;
    /* renamed from: d */
    boolean f9310d;
    /* renamed from: e */
    private int f9311e = 0;
    /* renamed from: f */
    private int f9312f;

    /* renamed from: b */
    public static void m13182b(Context context) {
        Intent intent = new Intent(context, DownloadManagerService.class);
        String[] split = C3791b.m13973f(context).split(":");
        Calendar instance = Calendar.getInstance();
        instance.set(11, Integer.parseInt(split[0]));
        instance.set(12, Integer.parseInt(split[1]));
        instance.set(13, 0);
        C2827a.m13164a(context, intent, instance, 0);
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.messagesDidLoaded) {
            int i2;
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDidLoaded);
            this.f9308b = (ArrayList) objArr[2];
            Iterator it = this.f9308b.iterator();
            while (it.hasNext()) {
                MessageObject messageObject = (MessageObject) it.next();
                if (!messageObject.mediaExists) {
                    FileLoader.getInstance().loadFile(messageObject.getDocument(), true, 0);
                    if (!(messageObject == null || messageObject.getDocument() == null)) {
                        this.f9312f = messageObject.getDocument().dc_id;
                    }
                    i2 = 0;
                    if (i2 != 0) {
                        stopSelf();
                    }
                }
            }
            boolean z = true;
            if (i2 != 0) {
                stopSelf();
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        Iterator it = this.f9308b.iterator();
        while (it.hasNext()) {
            FileLoader.getInstance().cancelLoadFile(((MessageObject) it.next()).getDocument());
        }
        if (!this.f9310d && C3791b.m13920X(getApplicationContext())) {
            ((WifiManager) getSystemService("wifi")).setWifiEnabled(false);
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.DownloadServiceStop, Integer.valueOf(10));
        C3791b.m14021n(getApplicationContext(), false);
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        final boolean booleanExtra = (intent == null || intent.getExtras() == null) ? false : intent.getBooleanExtra("EXTRA_IS_FORCE", false);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.DownloadServiceStart, Integer.valueOf(10));
        if (C3791b.m13938a((Context) this)) {
            this.f9309c = ConnectionsManager.getInstance().generateClassGuid();
            if (!booleanExtra && C3791b.m13919W(getApplicationContext())) {
                ((WifiManager) getSystemService("wifi")).setWifiEnabled(true);
            }
            String[] split = C3791b.m13980g(getApplicationContext()).split(":");
            final int parseInt = Integer.parseInt(split[0]);
            final int parseInt2 = Integer.parseInt(split[1]);
            this.f9307a = new FileLoaderDelegate(this) {
                /* renamed from: d */
                final /* synthetic */ DownloadManagerService f9306d;

                public void fileDidFailedLoad(String str, int i) {
                }

                public void fileDidFailedUpload(String str, boolean z) {
                }

                public void fileDidLoaded(String str, File file, int i) {
                    if (file.getName().contains(String.valueOf(this.f9306d.f9312f))) {
                        this.f9306d.f9311e = this.f9306d.f9311e + 1;
                        if (this.f9306d.f9311e >= this.f9306d.f9308b.size()) {
                            this.f9306d.stopSelf();
                        }
                        FileLoader.getInstance().loadFile(((MessageObject) this.f9306d.f9308b.get(this.f9306d.f9311e)).getDocument(), true, 0);
                        this.f9306d.f9312f = ((MessageObject) this.f9306d.f9308b.get(this.f9306d.f9311e)).getDocument().dc_id;
                    }
                }

                public void fileDidUploaded(String str, InputFile inputFile, InputEncryptedFile inputEncryptedFile, byte[] bArr, byte[] bArr2, long j) {
                }

                public void fileLoadProgressChanged(String str, float f) {
                    if (!booleanExtra) {
                        int hours = new Time(System.currentTimeMillis()).getHours();
                        int minutes = new Time(System.currentTimeMillis()).getMinutes();
                        if (hours >= parseInt && minutes >= parseInt2) {
                            this.f9306d.stopSelf();
                        }
                    }
                }

                public void fileUploadProgressChanged(String str, float f, boolean z) {
                }
            };
            FileLoader.getInstance().addDelegate(this.f9307a);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDidLoaded);
            MessagesController.getInstance().loadMessages(111444999, 1000, 0, 0, true, 0, this.f9309c, 0, 0, false, 0);
            return super.onStartCommand(intent, i, i2);
        }
        stopSelf();
        return super.onStartCommand(intent, i, i2);
    }
}
