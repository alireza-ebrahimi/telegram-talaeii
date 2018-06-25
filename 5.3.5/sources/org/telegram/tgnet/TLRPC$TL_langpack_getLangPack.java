package org.telegram.tgnet;

public class TLRPC$TL_langpack_getLangPack extends TLObject {
    public static int constructor = -1699363442;
    public String lang_code;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_langPackDifference.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.lang_code);
    }
}
