package org.telegram.messenger;

import android.util.SparseArray;
import android.util.SparseIntArray;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputPeerChannel;
import org.telegram.tgnet.TLRPC$TL_messages_getMessagesViews;
import org.telegram.tgnet.TLRPC$Vector;

class MessagesController$52 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$key;
    final /* synthetic */ TLRPC$TL_messages_getMessagesViews val$req;

    MessagesController$52(MessagesController this$0, int i, TLRPC$TL_messages_getMessagesViews tLRPC$TL_messages_getMessagesViews) {
        this.this$0 = this$0;
        this.val$key = i;
        this.val$req = tLRPC$TL_messages_getMessagesViews;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            TLRPC$Vector vector = (TLRPC$Vector) response;
            final SparseArray<SparseIntArray> channelViews = new SparseArray();
            SparseIntArray array = (SparseIntArray) channelViews.get(this.val$key);
            if (array == null) {
                array = new SparseIntArray();
                channelViews.put(this.val$key, array);
            }
            int a = 0;
            while (a < this.val$req.id.size() && a < vector.objects.size()) {
                array.put(((Integer) this.val$req.id.get(a)).intValue(), ((Integer) vector.objects.get(a)).intValue());
                a++;
            }
            MessagesStorage.getInstance().putChannelViews(channelViews, this.val$req.peer instanceof TLRPC$TL_inputPeerChannel);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didUpdatedMessagesViews, new Object[]{channelViews});
                }
            });
        }
    }
}
