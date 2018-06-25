package org.telegram.tgnet;

public class TLRPC$TL_sendMessageRecordVideoAction extends TLRPC$SendMessageAction {
    public static int constructor = -1584933265;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
