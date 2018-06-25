package org.telegram.tgnet;

public class TLRPC$TL_auth_requestPasswordRecovery extends TLObject {
    public static int constructor = -661144474;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_auth_passwordRecovery.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
