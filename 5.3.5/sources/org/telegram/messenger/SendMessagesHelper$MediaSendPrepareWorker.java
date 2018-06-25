package org.telegram.messenger;

import java.util.concurrent.CountDownLatch;
import org.telegram.tgnet.TLRPC$TL_photo;

class SendMessagesHelper$MediaSendPrepareWorker {
    public volatile TLRPC$TL_photo photo;
    public CountDownLatch sync;

    private SendMessagesHelper$MediaSendPrepareWorker() {
    }
}
