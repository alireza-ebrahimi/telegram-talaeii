package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageAction;

public class TLRPC$TL_messageActionCreatedBroadcastList extends MessageAction {
    public static int constructor = 1431655767;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
