package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputChatPhoto;

public class TLRPC$TL_inputChatPhotoEmpty extends InputChatPhoto {
    public static int constructor = 480546647;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
