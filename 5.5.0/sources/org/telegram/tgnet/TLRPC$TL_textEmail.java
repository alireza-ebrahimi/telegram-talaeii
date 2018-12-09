package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.RichText;

public class TLRPC$TL_textEmail extends RichText {
    public static int constructor = -564523562;
    public RichText text;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.text = RichText.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.email = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.text.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.email);
    }
}
