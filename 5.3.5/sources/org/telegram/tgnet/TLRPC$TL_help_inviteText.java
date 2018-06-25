package org.telegram.tgnet;

public class TLRPC$TL_help_inviteText extends TLObject {
    public static int constructor = 415997816;
    public String message;

    public static TLRPC$TL_help_inviteText TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_help_inviteText result = new TLRPC$TL_help_inviteText();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_help_inviteText", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.message = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.message);
    }
}
