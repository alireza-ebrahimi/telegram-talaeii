package org.telegram.tgnet;

public class TLRPC$TL_documentAttributeAudio_old extends TLRPC$TL_documentAttributeAudio {
    public static int constructor = 85215461;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.duration = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.duration);
    }
}
