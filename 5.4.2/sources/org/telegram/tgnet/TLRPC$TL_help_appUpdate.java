package org.telegram.tgnet;

public class TLRPC$TL_help_appUpdate extends TLRPC$help_AppUpdate {
    public static int constructor = -1987579119;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt32(z);
        this.critical = abstractSerializedData.readBool(z);
        this.url = abstractSerializedData.readString(z);
        this.text = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.id);
        abstractSerializedData.writeBool(this.critical);
        abstractSerializedData.writeString(this.url);
        abstractSerializedData.writeString(this.text);
    }
}
