package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;

class ContactsController$24 implements RequestDelegate {
    final /* synthetic */ ContactsController this$0;

    ContactsController$24(ContactsController this$0) {
        this.this$0 = this$0;
    }

    public void run(final TLObject response, final TLRPC$TL_error error) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (error == null) {
                    ContactsController.access$2002(ContactsController$24.this.this$0, response.days);
                    ContactsController.access$2102(ContactsController$24.this.this$0, 2);
                } else {
                    ContactsController.access$2102(ContactsController$24.this.this$0, 0);
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.privacyRulesUpdated, new Object[0]);
            }
        });
    }
}
