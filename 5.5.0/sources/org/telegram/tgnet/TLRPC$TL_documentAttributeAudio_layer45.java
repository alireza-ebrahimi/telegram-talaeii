package org.telegram.tgnet;

public class TLRPC$TL_documentAttributeAudio_layer45 extends TLRPC$TL_documentAttributeAudio {
    public static int constructor = -556656416;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.duration = abstractSerializedData.readInt32(z);
        this.title = abstractSerializedData.readString(z);
        this.performer = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.duration);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeString(this.performer);
    }
}
