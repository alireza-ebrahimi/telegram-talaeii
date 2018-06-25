package org.telegram.tgnet;

public class TLRPC$TL_phone_leaveGroupCall extends TLObject {
    public static int constructor = 1625919071;
    public TLRPC$TL_inputGroupCall call;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.call.serializeToStream(stream);
    }
}
