package org.telegram.tgnet;

public class TLRPC$TL_messageActionEmpty extends TLRPC$MessageAction {
    public static int constructor = -1230047312;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
