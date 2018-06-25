package org.telegram.messenger;

import java.util.Comparator;
import org.telegram.tgnet.TLRPC$Update;

class MessagesController$2 implements Comparator<TLRPC$Update> {
    final /* synthetic */ MessagesController this$0;

    MessagesController$2(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public int compare(TLRPC$Update lhs, TLRPC$Update rhs) {
        int ltype = MessagesController.access$000(this.this$0, lhs);
        int rtype = MessagesController.access$000(this.this$0, rhs);
        if (ltype != rtype) {
            return AndroidUtilities.compare(ltype, rtype);
        }
        if (ltype == 0) {
            return AndroidUtilities.compare(lhs.pts, rhs.pts);
        }
        if (ltype == 1) {
            return AndroidUtilities.compare(lhs.qts, rhs.qts);
        }
        if (ltype != 2) {
            return 0;
        }
        int lChannel = MessagesController.access$100(this.this$0, lhs);
        int rChannel = MessagesController.access$100(this.this$0, rhs);
        if (lChannel == rChannel) {
            return AndroidUtilities.compare(lhs.pts, rhs.pts);
        }
        return AndroidUtilities.compare(lChannel, rChannel);
    }
}
