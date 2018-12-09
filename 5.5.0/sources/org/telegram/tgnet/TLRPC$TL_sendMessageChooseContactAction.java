package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.SendMessageAction;

public class TLRPC$TL_sendMessageChooseContactAction extends SendMessageAction {
    public static int constructor = 1653390447;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
