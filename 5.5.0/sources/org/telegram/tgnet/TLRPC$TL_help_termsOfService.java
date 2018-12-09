package org.telegram.tgnet;

public class TLRPC$TL_help_termsOfService extends TLObject {
    public static int constructor = -236044656;
    public String text;

    public static TLRPC$TL_help_termsOfService TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_help_termsOfService tLRPC$TL_help_termsOfService = new TLRPC$TL_help_termsOfService();
            tLRPC$TL_help_termsOfService.readParams(abstractSerializedData, z);
            return tLRPC$TL_help_termsOfService;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_help_termsOfService", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.text = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.text);
    }
}
