package org.telegram.customization.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.dynamicadapter.data.TelegramMessage;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC$Chat;

public class ForwardFavMessage extends Service {
    private int counter = 0;
    NotificationCenterDelegate delegate = new C12161();
    private ArrayList<Favourite> favorites;

    /* renamed from: org.telegram.customization.service.ForwardFavMessage$1 */
    class C12161 implements NotificationCenterDelegate {
        C12161() {
        }

        public void didReceivedNotification(int id, Object... args) {
            FileLog.d("ForwardFavMessage 4");
            ArrayList<MessageObject> messArr = args[2];
            synchronized (this) {
                FileLog.d("ForwardFavMessage 5");
                if (messArr != null && messArr.size() > 0) {
                    FileLog.d("ForwardFavMessage 6");
                    int i = 0;
                    while (i < messArr.size()) {
                        int j = 0;
                        while (j < ForwardFavMessage.this.favorites.size()) {
                            FileLog.d("ForwardFavMessage 7");
                            if (((long) ((MessageObject) messArr.get(i)).getId()) == ((Favourite) ForwardFavMessage.this.favorites.get(j)).getMsg_id() && ((MessageObject) messArr.get(i)).getDialogId() == ((Favourite) ForwardFavMessage.this.favorites.get(j)).getChatID()) {
                                FileLog.d("ForwardFavMessage 8");
                                ArrayList<MessageObject> msgArr = new ArrayList();
                                msgArr.add(messArr.get(i));
                                SendMessagesHelper.getInstance().sendMessage(msgArr, (long) UserConfig.getClientUserId());
                                ForwardFavMessage.this.counter = ForwardFavMessage.this.counter + 1;
                            }
                            j++;
                        }
                        i++;
                    }
                }
                ForwardFavMessage.this.checkForStop();
            }
        }
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        FileLog.d("ForwardFavMessage 1");
        this.favorites = Favourite.getFavorites();
        if (this.favorites == null || this.favorites.isEmpty()) {
            FileLog.d("ForwardFavMessage 3");
            stopSelf();
            return;
        }
        FileLog.d("ForwardFavMessage 2");
        NotificationCenter.getInstance().addObserver(this.delegate, NotificationCenter.messagesDidLoaded);
        Iterator it = this.favorites.iterator();
        while (it.hasNext()) {
            Favourite favorite = (Favourite) it.next();
            MessagesController.getInstance().loadMessages(favorite.getChatID(), 1, (int) (favorite.getMsg_id() + 1), 0, true, 0, 0, 4, 0, true, 0);
        }
    }

    private void checkForStop() {
        FileLog.d("ForwardFavMessage 9 " + (this.favorites == null) + " - " + (this.favorites == null ? "0" : Integer.valueOf(this.favorites.size())) + " - " + this.counter);
        if (this.favorites == null || this.favorites.isEmpty() || this.counter >= this.favorites.size()) {
            FileLog.d("ForwardFavMessage 10 ");
            NotificationCenter.getInstance().removeObserver(this.delegate, NotificationCenter.messagesDidLoaded);
            stopSelf();
        }
    }

    private void loadSingleMessage(TelegramMessage baseMessage) {
        TLRPC$Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf(baseMessage.getMessage().to_id.channel_id));
        if (currentChat != null) {
            MessagesController.getInstance().loadPeerSettings(UserConfig.getCurrentUser(), currentChat);
            MessagesController.getInstance().loadMessages((long) (-baseMessage.getMessage().to_id.channel_id), 1, baseMessage.getMessage().id + 1, 0, true, 0, 0, 4, 0, true, 0);
            return;
        }
        this.counter++;
    }
}
