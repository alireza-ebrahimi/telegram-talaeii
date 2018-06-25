package org.telegram.messenger;

import android.util.Log;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$messages_Messages;

class MessagesController$58 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$classGuid;
    final /* synthetic */ int val$count;
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ int val$first_unread;
    final /* synthetic */ boolean val$isChannel;
    final /* synthetic */ int val$last_date;
    final /* synthetic */ int val$last_message_id;
    final /* synthetic */ int val$loadIndex;
    final /* synthetic */ int val$load_type;
    final /* synthetic */ int val$max_id;
    final /* synthetic */ int val$mentionsCount;
    final /* synthetic */ int val$offset_date;
    final /* synthetic */ boolean val$queryFromServer;
    final /* synthetic */ int val$unread_count;

    MessagesController$58(MessagesController this$0, int i, int i2, int i3, long j, int i4, int i5, int i6, int i7, int i8, int i9, boolean z, int i10, boolean z2, int i11) {
        this.this$0 = this$0;
        this.val$count = i;
        this.val$max_id = i2;
        this.val$offset_date = i3;
        this.val$dialog_id = j;
        this.val$classGuid = i4;
        this.val$first_unread = i5;
        this.val$last_message_id = i6;
        this.val$unread_count = i7;
        this.val$last_date = i8;
        this.val$load_type = i9;
        this.val$isChannel = z;
        this.val$loadIndex = i10;
        this.val$queryFromServer = z2;
        this.val$mentionsCount = i11;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        Log.d("LEE", "Debug1946 enter to run()  ");
        if (response != null) {
            Log.d("LEE", "Debug1946 i got message ");
            TLRPC$messages_Messages res = (TLRPC$messages_Messages) response;
            if (res.messages.size() > this.val$count) {
                res.messages.remove(0);
            }
            int mid = this.val$max_id;
            if (this.val$offset_date != 0 && !res.messages.isEmpty()) {
                mid = ((TLRPC$Message) res.messages.get(res.messages.size() - 1)).id;
                for (int a = res.messages.size() - 1; a >= 0; a--) {
                    TLRPC$Message message = (TLRPC$Message) res.messages.get(a);
                    if (message.date > this.val$offset_date) {
                        mid = message.id;
                        break;
                    }
                }
            }
            this.this$0.processLoadedMessages(res, this.val$dialog_id, this.val$count, mid, this.val$offset_date, false, this.val$classGuid, this.val$first_unread, this.val$last_message_id, this.val$unread_count, this.val$last_date, this.val$load_type, this.val$isChannel, false, this.val$loadIndex, this.val$queryFromServer, this.val$mentionsCount);
        }
    }
}
