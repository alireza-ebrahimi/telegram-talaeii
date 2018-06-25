package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$Updates;

class MessagesController$103 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;

    MessagesController$103(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            UserConfig.lastUpdateVersion = BuildVars.BUILD_VERSION_STRING;
            UserConfig.saveConfig(false);
        }
        if (response instanceof TLRPC$Updates) {
            this.this$0.processUpdates((TLRPC$Updates) response, false);
        }
    }
}
