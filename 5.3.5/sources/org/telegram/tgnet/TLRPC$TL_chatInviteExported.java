package org.telegram.tgnet;

public class TLRPC$TL_chatInviteExported extends TLRPC$ExportedChatInvite {
    public static int constructor = -64092740;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.link = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.link);
    }
}
