package org.telegram.tgnet;

public class TLRPC$TL_sendMessageUploadVideoAction_old extends TLRPC$TL_sendMessageUploadVideoAction {
    public static int constructor = -1845219337;

    public void readParams(AbstractSerializedData stream, boolean exception) {
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
