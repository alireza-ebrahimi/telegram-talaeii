package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_account_privacyRules;
import org.telegram.tgnet.TLRPC$TL_error;

class ContactsController$27 implements RequestDelegate {
    final /* synthetic */ ContactsController this$0;

    ContactsController$27(ContactsController this$0) {
        this.this$0 = this$0;
    }

    public void run(final TLObject response, final TLRPC$TL_error error) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (error == null) {
                    TLRPC$TL_account_privacyRules rules = response;
                    MessagesController.getInstance().putUsers(rules.users, false);
                    ContactsController.access$2602(ContactsController$27.this.this$0, rules.rules);
                    ContactsController.access$2702(ContactsController$27.this.this$0, 2);
                } else {
                    ContactsController.access$2702(ContactsController$27.this.this$0, 0);
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.privacyRulesUpdated, new Object[0]);
            }
        });
    }
}
