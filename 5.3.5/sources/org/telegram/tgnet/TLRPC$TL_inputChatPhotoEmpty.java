package org.telegram.tgnet;

public class TLRPC$TL_inputChatPhotoEmpty extends TLRPC$InputChatPhoto {
    public static int constructor = 480546647;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
