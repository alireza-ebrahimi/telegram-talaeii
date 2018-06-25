package org.telegram.tgnet;

public class TLRPC$TL_wallPaperSolid extends TLRPC$WallPaper {
    public static int constructor = 1662091044;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.title = stream.readString(exception);
        this.bg_color = stream.readInt32(exception);
        this.color = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeString(this.title);
        stream.writeInt32(this.bg_color);
        stream.writeInt32(this.color);
    }
}
