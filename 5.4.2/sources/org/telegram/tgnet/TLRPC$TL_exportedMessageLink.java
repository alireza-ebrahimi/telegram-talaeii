package org.telegram.tgnet;

public class TLRPC$TL_exportedMessageLink extends TLObject {
    public static int constructor = 524838915;
    public String link;

    public static TLRPC$TL_exportedMessageLink TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_exportedMessageLink tLRPC$TL_exportedMessageLink = new TLRPC$TL_exportedMessageLink();
            tLRPC$TL_exportedMessageLink.readParams(abstractSerializedData, z);
            return tLRPC$TL_exportedMessageLink;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_exportedMessageLink", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.link = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.link);
    }
}
