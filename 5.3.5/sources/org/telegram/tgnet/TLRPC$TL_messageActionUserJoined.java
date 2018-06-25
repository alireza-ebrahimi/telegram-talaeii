package org.telegram.tgnet;

public class TLRPC$TL_messageActionUserJoined extends TLRPC$MessageAction {
    public static int constructor = 1431655760;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
