package org.telegram.tgnet;

public class TLRPC$TL_messageActionHistoryClear extends TLRPC$MessageAction {
    public static int constructor = -1615153660;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
