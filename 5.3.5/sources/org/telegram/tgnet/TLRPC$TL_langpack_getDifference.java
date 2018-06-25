package org.telegram.tgnet;

public class TLRPC$TL_langpack_getDifference extends TLObject {
    public static int constructor = 187583869;
    public int from_version;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_langPackDifference.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.from_version);
    }
}
