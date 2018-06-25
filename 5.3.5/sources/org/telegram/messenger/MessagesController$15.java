package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.messenger.query.BotQuery;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_botInfo;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_userFull;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$15 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$classGuid;
    final /* synthetic */ User val$user;

    /* renamed from: org.telegram.messenger.MessagesController$15$2 */
    class C14752 implements Runnable {
        C14752() {
        }

        public void run() {
            MessagesController.access$2700(MessagesController$15.this.this$0).remove(Integer.valueOf(MessagesController$15.this.val$user.id));
        }
    }

    MessagesController$15(MessagesController this$0, User user, int i) {
        this.this$0 = this$0;
        this.val$user = user;
        this.val$classGuid = i;
    }

    public void run(final TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    TLRPC$TL_userFull userFull = response;
                    MessagesController.access$2100(MessagesController$15.this.this$0, (long) MessagesController$15.this.val$user.id, userFull.notify_settings);
                    if (userFull.bot_info instanceof TLRPC$TL_botInfo) {
                        BotQuery.putBotInfo(userFull.bot_info);
                    }
                    MessagesController.access$2600(MessagesController$15.this.this$0).put(Integer.valueOf(MessagesController$15.this.val$user.id), userFull);
                    MessagesController.access$2700(MessagesController$15.this.this$0).remove(Integer.valueOf(MessagesController$15.this.val$user.id));
                    MessagesController.access$2800(MessagesController$15.this.this$0).add(Integer.valueOf(MessagesController$15.this.val$user.id));
                    String names = MessagesController$15.this.val$user.first_name + MessagesController$15.this.val$user.last_name + MessagesController$15.this.val$user.username;
                    ArrayList<User> users = new ArrayList();
                    users.add(userFull.user);
                    MessagesController$15.this.this$0.putUsers(users, false);
                    MessagesStorage.getInstance().putUsersAndChats(users, null, false, true);
                    if (!(names == null || names.equals(userFull.user.first_name + userFull.user.last_name + userFull.user.username))) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(1)});
                    }
                    if (userFull.bot_info instanceof TLRPC$TL_botInfo) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.botInfoDidLoaded, new Object[]{userFull.bot_info, Integer.valueOf(MessagesController$15.this.val$classGuid)});
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.userInfoDidLoaded, new Object[]{Integer.valueOf(MessagesController$15.this.val$user.id), userFull});
                }
            });
        } else {
            AndroidUtilities.runOnUIThread(new C14752());
        }
    }
}
