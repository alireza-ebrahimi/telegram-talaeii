package org.telegram.tgnet;

public class TLRPC$TL_messageActionPinMessage extends TLRPC$MessageAction {
    public static int constructor = -1799538451;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
