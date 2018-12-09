package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputMedia;
import org.telegram.tgnet.TLRPC.InputPeer;
import org.telegram.tgnet.TLRPC.MessageMedia;

public class TLRPC$TL_messages_uploadMedia extends TLObject {
    public static int constructor = 1369162417;
    public InputMedia media;
    public InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return MessageMedia.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        this.media.serializeToStream(abstractSerializedData);
    }
}
