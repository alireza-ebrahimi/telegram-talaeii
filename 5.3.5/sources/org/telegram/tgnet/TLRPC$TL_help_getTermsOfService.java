package org.telegram.tgnet;

public class TLRPC$TL_help_getTermsOfService extends TLObject {
    public static int constructor = 889286899;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_help_termsOfService.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
