package org.telegram.customization.dynamicadapter.viewholder;

import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC$Chat;

class SlsMessageHolder$16 implements IResponseReceiver {
    final /* synthetic */ SlsMessageHolder this$0;

    SlsMessageHolder$16(SlsMessageHolder this$0) {
        this.this$0 = this$0;
    }

    public void onResult(Object object, int StatusCode) {
        TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(SlsMessageHolder.access$000(this.this$0).getMessage().to_id.channel_id));
        if (chat != null) {
            MessagesController.getInstance().loadPeerSettings(UserConfig.getCurrentUser(), chat);
            if (SlsMessageHolder.access$000(this.this$0) != null && SlsMessageHolder.access$000(this.this$0).getMessage() != null && SlsMessageHolder.access$000(this.this$0).getMessage().to_id != null) {
                MessagesController.getInstance().loadMessages((long) (-SlsMessageHolder.access$000(this.this$0).getMessage().to_id.channel_id), 1, SlsMessageHolder.access$000(this.this$0).getMessage().id + 1, 0, true, 0, 0, 3, 0, true, 0);
            }
        }
    }
}
