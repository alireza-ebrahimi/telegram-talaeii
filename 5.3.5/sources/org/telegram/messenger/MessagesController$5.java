package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_photos_photo;
import org.telegram.tgnet.TLRPC$TL_userProfilePhoto;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$5 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;

    /* renamed from: org.telegram.messenger.MessagesController$5$1 */
    class C14881 implements Runnable {
        C14881() {
        }

        public void run() {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(2)});
            UserConfig.saveConfig(true);
        }
    }

    MessagesController$5(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            User user = this.this$0.getUser(Integer.valueOf(UserConfig.getClientUserId()));
            if (user == null) {
                user = UserConfig.getCurrentUser();
                this.this$0.putUser(user, true);
            } else {
                UserConfig.setCurrentUser(user);
            }
            if (user != null) {
                TLRPC$TL_photos_photo photo = (TLRPC$TL_photos_photo) response;
                ArrayList<TLRPC$PhotoSize> sizes = photo.photo.sizes;
                TLRPC$PhotoSize smallSize = FileLoader.getClosestPhotoSizeWithSize(sizes, 100);
                TLRPC$PhotoSize bigSize = FileLoader.getClosestPhotoSizeWithSize(sizes, 1000);
                user.photo = new TLRPC$TL_userProfilePhoto();
                user.photo.photo_id = photo.photo.id;
                if (smallSize != null) {
                    user.photo.photo_small = smallSize.location;
                }
                if (bigSize != null) {
                    user.photo.photo_big = bigSize.location;
                } else if (smallSize != null) {
                    user.photo.photo_small = smallSize.location;
                }
                MessagesStorage.getInstance().clearUserPhotos(user.id);
                ArrayList<User> users = new ArrayList();
                users.add(user);
                MessagesStorage.getInstance().putUsersAndChats(users, null, false, true);
                AndroidUtilities.runOnUIThread(new C14881());
            }
        }
    }
}
