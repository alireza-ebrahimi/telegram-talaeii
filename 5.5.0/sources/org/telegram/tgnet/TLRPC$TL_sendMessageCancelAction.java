package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.SendMessageAction;

public class TLRPC$TL_sendMessageCancelAction extends SendMessageAction {
    public static int constructor = -44119819;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
