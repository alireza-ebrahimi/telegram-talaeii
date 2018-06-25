package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputFile;

public class TLRPC$TL_photos_uploadProfilePhoto extends TLObject {
    public static int constructor = 1328726168;
    public InputFile file;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_photos_photo.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.file.serializeToStream(abstractSerializedData);
    }
}
