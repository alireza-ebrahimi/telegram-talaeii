package org.telegram.customization.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.customization.Model.Favourite;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;

/* renamed from: org.telegram.customization.service.b */
public class C2840b extends Service {
    /* renamed from: a */
    NotificationCenterDelegate f9322a = new C28391(this);
    /* renamed from: b */
    private ArrayList<Favourite> f9323b;
    /* renamed from: c */
    private int f9324c = 0;

    /* renamed from: org.telegram.customization.service.b$1 */
    class C28391 implements NotificationCenterDelegate {
        /* renamed from: a */
        final /* synthetic */ C2840b f9321a;

        C28391(C2840b c2840b) {
            this.f9321a = c2840b;
        }

        public void didReceivedNotification(int i, Object... objArr) {
            FileLog.m13725d("ForwardFavMessage 4");
            ArrayList arrayList = (ArrayList) objArr[2];
            synchronized (this) {
                FileLog.m13725d("ForwardFavMessage 5");
                if (arrayList != null && arrayList.size() > 0) {
                    FileLog.m13725d("ForwardFavMessage 6");
                    int i2 = 0;
                    while (i2 < arrayList.size()) {
                        int i3 = 0;
                        while (i3 < this.f9321a.f9323b.size()) {
                            FileLog.m13725d("ForwardFavMessage 7");
                            if (((long) ((MessageObject) arrayList.get(i2)).getId()) == ((Favourite) this.f9321a.f9323b.get(i3)).getMsg_id() && ((MessageObject) arrayList.get(i2)).getDialogId() == ((Favourite) this.f9321a.f9323b.get(i3)).getChatID()) {
                                FileLog.m13725d("ForwardFavMessage 8");
                                ArrayList arrayList2 = new ArrayList();
                                arrayList2.add(arrayList.get(i2));
                                SendMessagesHelper.getInstance().sendMessage(arrayList2, (long) UserConfig.getClientUserId());
                                this.f9321a.f9324c = this.f9321a.f9324c + 1;
                            }
                            i3++;
                        }
                        i2++;
                    }
                }
                this.f9321a.m13200a();
            }
        }
    }

    /* renamed from: a */
    private void m13200a() {
        FileLog.m13725d("ForwardFavMessage 9 " + (this.f9323b == null) + " - " + (this.f9323b == null ? "0" : Integer.valueOf(this.f9323b.size())) + " - " + this.f9324c);
        if (this.f9323b == null || this.f9323b.isEmpty() || this.f9324c >= this.f9323b.size()) {
            FileLog.m13725d("ForwardFavMessage 10 ");
            NotificationCenter.getInstance().removeObserver(this.f9322a, NotificationCenter.messagesDidLoaded);
            stopSelf();
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        FileLog.m13725d("ForwardFavMessage 1");
        this.f9323b = Favourite.getFavorites();
        if (this.f9323b == null || this.f9323b.isEmpty()) {
            FileLog.m13725d("ForwardFavMessage 3");
            stopSelf();
            return;
        }
        FileLog.m13725d("ForwardFavMessage 2");
        NotificationCenter.getInstance().addObserver(this.f9322a, NotificationCenter.messagesDidLoaded);
        Iterator it = this.f9323b.iterator();
        while (it.hasNext()) {
            Favourite favourite = (Favourite) it.next();
            MessagesController.getInstance().loadMessages(favourite.getChatID(), 1, (int) (favourite.getMsg_id() + 1), 0, true, 0, 0, 4, 0, true, 0);
        }
    }
}
