package org.telegram.messenger;

import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contactStatus;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_user;
import org.telegram.tgnet.TLRPC$TL_userStatusLastMonth;
import org.telegram.tgnet.TLRPC$TL_userStatusLastWeek;
import org.telegram.tgnet.TLRPC$TL_userStatusRecently;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC.User;

class ContactsController$23 implements RequestDelegate {
    final /* synthetic */ ContactsController this$0;
    final /* synthetic */ Editor val$editor;

    ContactsController$23(ContactsController this$0, Editor editor) {
        this.this$0 = this$0;
        this.val$editor = editor;
    }

    public void run(final TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    ContactsController$23.this.val$editor.remove("needGetStatuses").commit();
                    TLRPC$Vector vector = response;
                    if (!vector.objects.isEmpty()) {
                        ArrayList<User> dbUsersStatus = new ArrayList();
                        Iterator it = vector.objects.iterator();
                        while (it.hasNext()) {
                            TLRPC$TL_contactStatus object = it.next();
                            User toDbUser = new TLRPC$TL_user();
                            TLRPC$TL_contactStatus status = object;
                            if (status != null) {
                                if (status.status instanceof TLRPC$TL_userStatusRecently) {
                                    status.status.expires = -100;
                                } else if (status.status instanceof TLRPC$TL_userStatusLastWeek) {
                                    status.status.expires = FetchConst.ERROR_UNKNOWN;
                                } else if (status.status instanceof TLRPC$TL_userStatusLastMonth) {
                                    status.status.expires = FetchConst.ERROR_FILE_NOT_CREATED;
                                }
                                User user = MessagesController.getInstance().getUser(Integer.valueOf(status.user_id));
                                if (user != null) {
                                    user.status = status.status;
                                }
                                toDbUser.status = status.status;
                                dbUsersStatus.add(toDbUser);
                            }
                        }
                        MessagesStorage.getInstance().updateUsers(dbUsersStatus, true, true, true);
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(4)});
                }
            });
        }
    }
}
