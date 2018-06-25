package org.telegram.tgnet;

public class TLRPC$TL_video_old extends TLRPC$TL_video_layer45 {
    public static int constructor = 1510253727;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        this.user_id = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.caption = stream.readString(exception);
        this.duration = stream.readInt32(exception);
        this.size = stream.readInt32(exception);
        this.thumb = TLRPC$PhotoSize.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.dc_id = stream.readInt32(exception);
        this.w = stream.readInt32(exception);
        this.h = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.date);
        stream.writeString(this.caption);
        stream.writeInt32(this.duration);
        stream.writeInt32(this.size);
        this.thumb.serializeToStream(stream);
        stream.writeInt32(this.dc_id);
        stream.writeInt32(this.w);
        stream.writeInt32(this.h);
    }
}
