package org.telegram.tgnet;

public class TLRPC$TL_webPageUrlPending extends TLRPC$WebPage {
    public static int constructor = -736472729;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.url = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
    }
}
