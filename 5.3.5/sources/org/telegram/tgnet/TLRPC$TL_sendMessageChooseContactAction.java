package org.telegram.tgnet;

public class TLRPC$TL_sendMessageChooseContactAction extends TLRPC$SendMessageAction {
    public static int constructor = 1653390447;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
