package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPhoto;

public class TLRPC$TL_inputPhotoEmpty extends InputPhoto {
    public static int constructor = 483901197;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
