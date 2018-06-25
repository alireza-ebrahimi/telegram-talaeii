package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageAction;

public class TLRPC$TL_messageActionTTLChange extends MessageAction {
    public static int constructor = 1431655762;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.ttl = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.ttl);
    }
}
