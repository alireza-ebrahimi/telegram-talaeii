package org.telegram.tgnet;

public class TLRPC$TL_help_getInviteText extends TLObject {
    public static int constructor = 1295590211;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_help_inviteText.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
