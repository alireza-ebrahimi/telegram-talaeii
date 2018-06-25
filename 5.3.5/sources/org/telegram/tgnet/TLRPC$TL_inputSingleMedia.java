package org.telegram.tgnet;

public class TLRPC$TL_inputSingleMedia extends TLObject {
    public static int constructor = 1588230153;
    public TLRPC$InputMedia media;
    public long random_id;

    public static TLRPC$TL_inputSingleMedia TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_inputSingleMedia result = new TLRPC$TL_inputSingleMedia();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputSingleMedia", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.media = TLRPC$InputMedia.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.random_id = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.media.serializeToStream(stream);
        stream.writeInt64(this.random_id);
    }
}
