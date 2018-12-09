package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_messages_sendScreenshotNotification extends TLObject {
    public static int constructor = -914493408;
    public InputPeer peer;
    public long random_id;
    public int reply_to_msg_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.reply_to_msg_id);
        abstractSerializedData.writeInt64(this.random_id);
    }
}
