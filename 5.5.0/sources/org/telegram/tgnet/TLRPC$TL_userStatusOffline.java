package org.telegram.tgnet;

public class TLRPC$TL_userStatusOffline extends TLRPC$UserStatus {
    public static int constructor = 9203775;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.expires = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.expires);
    }
}
