package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$UserProfilePhoto;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$35 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;

    /* renamed from: org.telegram.messenger.MessagesController$35$1 */
    class C14851 implements Runnable {
        C14851() {
        }

        public void run() {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(MessagesController.UPDATE_MASK_ALL)});
            UserConfig.saveConfig(true);
        }
    }

    MessagesController$35(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            User user = this.this$0.getUser(Integer.valueOf(UserConfig.getClientUserId()));
            if (user == null) {
                user = UserConfig.getCurrentUser();
                this.this$0.putUser(user, false);
            } else {
                UserConfig.setCurrentUser(user);
            }
            if (user != null) {
                MessagesStorage.getInstance().clearUserPhotos(user.id);
                ArrayList<User> users = new ArrayList();
                users.add(user);
                MessagesStorage.getInstance().putUsersAndChats(users, null, false, true);
                user.photo = (TLRPC$UserProfilePhoto) response;
                AndroidUtilities.runOnUIThread(new C14851());
            }
        }
    }
}
