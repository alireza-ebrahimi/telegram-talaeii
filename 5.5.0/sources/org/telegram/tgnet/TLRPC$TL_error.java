package org.telegram.tgnet;

public class TLRPC$TL_error extends TLObject {
    public static int constructor = -994444869;
    public int code;
    public String text;

    public static TLRPC$TL_error TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_error tLRPC$TL_error = new TLRPC$TL_error();
            tLRPC$TL_error.readParams(abstractSerializedData, z);
            return tLRPC$TL_error;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_error", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.code = abstractSerializedData.readInt32(z);
        this.text = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.code);
        abstractSerializedData.writeString(this.text);
    }
}
