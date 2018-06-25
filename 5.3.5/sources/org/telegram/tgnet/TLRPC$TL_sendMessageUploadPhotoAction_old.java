package org.telegram.tgnet;

public class TLRPC$TL_sendMessageUploadPhotoAction_old extends TLRPC$TL_sendMessageUploadPhotoAction {
    public static int constructor = -1727382502;

    public void readParams(AbstractSerializedData stream, boolean exception) {
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
