package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageAction;

public class TLRPC$TL_messageActionChatDeletePhoto extends MessageAction {
    public static int constructor = -1780220945;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
