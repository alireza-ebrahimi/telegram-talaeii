package org.telegram.tgnet;

public class TLRPC$TL_help_appUpdate extends TLRPC$help_AppUpdate {
    public static int constructor = -1987579119;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.critical = stream.readBool(exception);
        this.url = stream.readString(exception);
        this.text = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeBool(this.critical);
        stream.writeString(this.url);
        stream.writeString(this.text);
    }
}
