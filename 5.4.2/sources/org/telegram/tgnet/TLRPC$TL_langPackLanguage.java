package org.telegram.tgnet;

public class TLRPC$TL_langPackLanguage extends TLObject {
    public static int constructor = 292985073;
    public String lang_code;
    public String name;
    public String native_name;

    public static TLRPC$TL_langPackLanguage TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_langPackLanguage tLRPC$TL_langPackLanguage = new TLRPC$TL_langPackLanguage();
            tLRPC$TL_langPackLanguage.readParams(abstractSerializedData, z);
            return tLRPC$TL_langPackLanguage;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_langPackLanguage", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.name = abstractSerializedData.readString(z);
        this.native_name = abstractSerializedData.readString(z);
        this.lang_code = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.name);
        abstractSerializedData.writeString(this.native_name);
        abstractSerializedData.writeString(this.lang_code);
    }
}
