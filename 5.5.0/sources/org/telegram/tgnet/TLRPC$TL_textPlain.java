package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.RichText;

public class TLRPC$TL_textPlain extends RichText {
    public static int constructor = 1950782688;
    public String text;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.text = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.text);
    }
}
