package org.telegram.tgnet;

public class TLRPC$TL_messageActionCreatedBroadcastList extends TLRPC$MessageAction {
    public static int constructor = 1431655767;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
