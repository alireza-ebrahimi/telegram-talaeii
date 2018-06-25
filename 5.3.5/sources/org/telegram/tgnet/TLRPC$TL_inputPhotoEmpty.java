package org.telegram.tgnet;

public class TLRPC$TL_inputPhotoEmpty extends TLRPC$InputPhoto {
    public static int constructor = 483901197;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
