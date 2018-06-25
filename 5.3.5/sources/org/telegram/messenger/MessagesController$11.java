package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_peerDialogs;
import org.telegram.tgnet.TLRPC$TL_updateReadChannelInbox;
import org.telegram.tgnet.TLRPC$TL_updateReadChannelOutbox;
import org.telegram.tgnet.TLRPC$TL_updateReadHistoryInbox;
import org.telegram.tgnet.TLRPC$TL_updateReadHistoryOutbox;
import org.telegram.tgnet.TLRPC$Update;

class MessagesController$11 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;

    MessagesController$11(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (response != null) {
            TLRPC$TL_messages_peerDialogs res = (TLRPC$TL_messages_peerDialogs) response;
            ArrayList<TLRPC$Update> arrayList = new ArrayList();
            for (int a = 0; a < res.dialogs.size(); a++) {
                TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) res.dialogs.get(a);
                if (dialog.read_inbox_max_id == 0) {
                    dialog.read_inbox_max_id = 1;
                }
                if (dialog.read_outbox_max_id == 0) {
                    dialog.read_outbox_max_id = 1;
                }
                if (dialog.id == 0 && dialog.peer != null) {
                    if (dialog.peer.user_id != 0) {
                        dialog.id = (long) dialog.peer.user_id;
                    } else if (dialog.peer.chat_id != 0) {
                        dialog.id = (long) (-dialog.peer.chat_id);
                    } else if (dialog.peer.channel_id != 0) {
                        dialog.id = (long) (-dialog.peer.channel_id);
                    }
                }
                Integer value = (Integer) this.this$0.dialogs_read_inbox_max.get(Long.valueOf(dialog.id));
                if (value == null) {
                    value = Integer.valueOf(0);
                }
                this.this$0.dialogs_read_inbox_max.put(Long.valueOf(dialog.id), Integer.valueOf(Math.max(dialog.read_inbox_max_id, value.intValue())));
                if (value.intValue() == 0) {
                    if (dialog.peer.channel_id != 0) {
                        TLRPC$TL_updateReadChannelInbox update = new TLRPC$TL_updateReadChannelInbox();
                        update.channel_id = dialog.peer.channel_id;
                        update.max_id = dialog.read_inbox_max_id;
                        arrayList.add(update);
                    } else {
                        TLRPC$TL_updateReadHistoryInbox update2 = new TLRPC$TL_updateReadHistoryInbox();
                        update2.peer = dialog.peer;
                        update2.max_id = dialog.read_inbox_max_id;
                        arrayList.add(update2);
                    }
                }
                value = (Integer) this.this$0.dialogs_read_outbox_max.get(Long.valueOf(dialog.id));
                if (value == null) {
                    value = Integer.valueOf(0);
                }
                this.this$0.dialogs_read_outbox_max.put(Long.valueOf(dialog.id), Integer.valueOf(Math.max(dialog.read_outbox_max_id, value.intValue())));
                if (value.intValue() == 0) {
                    if (dialog.peer.channel_id != 0) {
                        TLRPC$TL_updateReadChannelOutbox update3 = new TLRPC$TL_updateReadChannelOutbox();
                        update3.channel_id = dialog.peer.channel_id;
                        update3.max_id = dialog.read_outbox_max_id;
                        arrayList.add(update3);
                    } else {
                        TLRPC$TL_updateReadHistoryOutbox update4 = new TLRPC$TL_updateReadHistoryOutbox();
                        update4.peer = dialog.peer;
                        update4.max_id = dialog.read_outbox_max_id;
                        arrayList.add(update4);
                    }
                }
            }
            if (!arrayList.isEmpty()) {
                this.this$0.processUpdateArray(arrayList, null, null, false);
            }
        }
    }
}
