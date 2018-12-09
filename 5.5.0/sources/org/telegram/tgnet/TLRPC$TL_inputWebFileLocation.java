package org.telegram.tgnet;

public class TLRPC$TL_inputWebFileLocation extends TLObject {
    public static int constructor = -1036396922;
    public long access_hash;
    public String url;

    public static TLRPC$TL_inputWebFileLocation TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_inputWebFileLocation tLRPC$TL_inputWebFileLocation = new TLRPC$TL_inputWebFileLocation();
            tLRPC$TL_inputWebFileLocation.readParams(abstractSerializedData, z);
            return tLRPC$TL_inputWebFileLocation;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputWebFileLocation", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.url = abstractSerializedData.readString(z);
        this.access_hash = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
        abstractSerializedData.writeInt64(this.access_hash);
    }
}
