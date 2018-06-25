package org.telegram.tgnet;

public class TLRPC$TL_inputFileLocation extends TLRPC$InputFileLocation {
    public static int constructor = 342061462;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.volume_id = stream.readInt64(exception);
        this.local_id = stream.readInt32(exception);
        this.secret = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.volume_id);
        stream.writeInt32(this.local_id);
        stream.writeInt64(this.secret);
    }
}
