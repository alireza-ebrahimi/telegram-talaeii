package org.telegram.messenger;

import android.content.ContentValues;
import android.net.Uri;

class ContactsController$20 implements Runnable {
    final /* synthetic */ ContactsController this$0;
    final /* synthetic */ String val$contactId;

    ContactsController$20(ContactsController this$0, String str) {
        this.this$0 = this$0;
        this.val$contactId = str;
    }

    public void run() {
        Uri uri = Uri.parse(this.val$contactId);
        ContentValues values = new ContentValues();
        values.put("last_time_contacted", Long.valueOf(System.currentTimeMillis()));
        ApplicationLoader.applicationContext.getContentResolver().update(uri, values, null, null);
    }
}
