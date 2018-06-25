package org.telegram.tgnet;

public class TLRPC$TL_chatPhotoEmpty extends TLRPC$ChatPhoto {
    public static int constructor = 935395612;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
