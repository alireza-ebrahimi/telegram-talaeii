package org.telegram.tgnet;

public class TLRPC$TL_inputWebFileLocation extends TLObject {
    public static int constructor = -1036396922;
    public long access_hash;
    public String url;

    public static TLRPC$TL_inputWebFileLocation TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_inputWebFileLocation result = new TLRPC$TL_inputWebFileLocation();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputWebFileLocation", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.url = stream.readString(exception);
        this.access_hash = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.url);
        stream.writeInt64(this.access_hash);
    }
}
