package org.telegram.messenger;

import android.content.SharedPreferences.Editor;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_help_inviteText;

class ContactsController$2 implements RequestDelegate {
    final /* synthetic */ ContactsController this$0;

    ContactsController$2(ContactsController this$0) {
        this.this$0 = this$0;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (response != null) {
            final TLRPC$TL_help_inviteText res = (TLRPC$TL_help_inviteText) response;
            if (res.message.length() != 0) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        ContactsController.access$202(ContactsController$2.this.this$0, false);
                        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                        editor.putString("invitelink", ContactsController.access$302(ContactsController$2.this.this$0, res.message));
                        editor.putInt("invitelinktime", (int) (System.currentTimeMillis() / 1000));
                        editor.commit();
                    }
                });
            }
        }
    }
}
