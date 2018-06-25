package org.telegram.tgnet;

public class TLRPC$TL_fileEncryptedLocation extends TLRPC$FileLocation {
    public static int constructor = 1431655764;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.dc_id = stream.readInt32(exception);
        this.volume_id = stream.readInt64(exception);
        this.local_id = stream.readInt32(exception);
        this.secret = stream.readInt64(exception);
        this.key = stream.readByteArray(exception);
        this.iv = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.dc_id);
        stream.writeInt64(this.volume_id);
        stream.writeInt32(this.local_id);
        stream.writeInt64(this.secret);
        stream.writeByteArray(this.key);
        stream.writeByteArray(this.iv);
    }
}
