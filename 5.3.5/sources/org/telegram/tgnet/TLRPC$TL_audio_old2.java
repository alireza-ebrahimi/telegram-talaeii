package org.telegram.tgnet;

public class TLRPC$TL_audio_old2 extends TLRPC$TL_audio_layer45 {
    public static int constructor = -945003370;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        this.user_id = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.duration = stream.readInt32(exception);
        this.mime_type = stream.readString(exception);
        this.size = stream.readInt32(exception);
        this.dc_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.date);
        stream.writeInt32(this.duration);
        stream.writeString(this.mime_type);
        stream.writeInt32(this.size);
        stream.writeInt32(this.dc_id);
    }
}
