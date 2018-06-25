package org.telegram.tgnet;

public class TLRPC$TL_phone_getGroupCall extends TLObject {
    public static int constructor = 209498135;
    public TLRPC$TL_inputGroupCall call;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_phone_groupCall.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.call.serializeToStream(stream);
    }
}
