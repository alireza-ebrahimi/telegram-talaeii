package org.telegram.messenger;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$InputUser;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputUserSelf;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC.User;
import utils.app.AppPreferences;

class MessagesController$98 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$chat_id;
    final /* synthetic */ TLRPC$InputUser val$inputUser;
    final /* synthetic */ boolean val$isChannel;
    final /* synthetic */ User val$user;

    /* renamed from: org.telegram.messenger.MessagesController$98$1 */
    class C15231 implements Runnable {
        C15231() {
        }

        public void run() {
            MessagesController$98.this.this$0.deleteDialog((long) (-MessagesController$98.this.val$chat_id), 0);
        }
    }

    /* renamed from: org.telegram.messenger.MessagesController$98$2 */
    class C15242 implements IResponseReceiver {
        C15242() {
        }

        public void onResult(Object object, int StatusCode) {
        }
    }

    /* renamed from: org.telegram.messenger.MessagesController$98$3 */
    class C15253 implements Runnable {
        C15253() {
        }

        public void run() {
            MessagesController$98.this.this$0.loadFullChat(MessagesController$98.this.val$chat_id, 0, true);
        }
    }

    MessagesController$98(MessagesController this$0, User user, int i, boolean z, TLRPC$InputUser tLRPC$InputUser) {
        this.this$0 = this$0;
        this.val$user = user;
        this.val$chat_id = i;
        this.val$isChannel = z;
        this.val$inputUser = tLRPC$InputUser;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (this.val$user.id == UserConfig.getClientUserId()) {
            AndroidUtilities.runOnUIThread(new C15231());
        }
        if (error == null) {
            if (AppPreferences.isAdsEnable(ApplicationLoader.applicationContext)) {
                Category category;
                long id = 0;
                boolean isAds = false;
                Iterator it = AppPreferences.getAdsChannel(ApplicationLoader.applicationContext).iterator();
                while (it.hasNext()) {
                    category = (Category) it.next();
                    if (category.getChannelId() == this.val$chat_id) {
                        id = category.getId().longValue();
                        isAds = true;
                    }
                }
                if (isAds) {
                    Log.d("alireza", "alireza leave from ads");
                    category = new Category();
                    category.setId(Long.valueOf(id));
                    category.setStatus(2);
                    ArrayList<Category> categories = new ArrayList();
                    categories.add(category);
                    HandleRequest.getNew(ApplicationLoader.applicationContext, new C15242()).manageCategory(categories);
                }
            }
            this.this$0.processUpdates((TLRPC$Updates) response, false);
            if (this.val$isChannel && !(this.val$inputUser instanceof TLRPC$TL_inputUserSelf)) {
                AndroidUtilities.runOnUIThread(new C15253(), 1000);
            }
        }
    }
}
