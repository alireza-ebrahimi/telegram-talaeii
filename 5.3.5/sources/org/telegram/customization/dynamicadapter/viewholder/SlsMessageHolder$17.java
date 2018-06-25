package org.telegram.customization.dynamicadapter.viewholder;

import java.util.ArrayList;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC$Chat;
import utils.app.AppPreferences;

class SlsMessageHolder$17 implements IResponseReceiver {
    final /* synthetic */ long val$expireAfter;
    final /* synthetic */ String val$username;

    SlsMessageHolder$17(long j, String str) {
        this.val$expireAfter = j;
        this.val$username = str;
    }

    public void onResult(Object object, int StatusCode) {
        TLRPC$Chat chat = (TLRPC$Chat) ((ArrayList) object).get(0);
        if (chat != null && ChatObject.isNotInChat(chat)) {
            MessagesController.getInstance().addUserToChat(chat.id, UserConfig.getCurrentUser(), null, 0, null, null);
            if (this.val$expireAfter != 0) {
                AppPreferences.setChannelJoinExpireDate(ApplicationLoader.applicationContext, this.val$username, System.currentTimeMillis() + this.val$expireAfter);
            }
        }
    }
}
