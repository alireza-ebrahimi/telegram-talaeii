package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputChatPhoto;
import org.telegram.tgnet.TLRPC.InputFile;

public class TLRPC$TL_inputChatUploadedPhoto extends InputChatPhoto {
    public static int constructor = -1837345356;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.file = InputFile.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.file.serializeToStream(abstractSerializedData);
    }
}
