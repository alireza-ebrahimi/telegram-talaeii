package org.telegram.messenger;

import java.util.Comparator;
import org.telegram.tgnet.TLRPC$Updates;

class MessagesController$108 implements Comparator<TLRPC$Updates> {
    final /* synthetic */ MessagesController this$0;

    MessagesController$108(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public int compare(TLRPC$Updates updates, TLRPC$Updates updates2) {
        return AndroidUtilities.compare(updates.pts, updates2.pts);
    }
}
