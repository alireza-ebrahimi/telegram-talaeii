package org.telegram.tgnet;

public class TLRPC$TL_wallPaperSolid extends TLRPC$WallPaper {
    public static int constructor = 1662091044;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt32(z);
        this.title = abstractSerializedData.readString(z);
        this.bg_color = abstractSerializedData.readInt32(z);
        this.color = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.id);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeInt32(this.bg_color);
        abstractSerializedData.writeInt32(this.color);
    }
}
