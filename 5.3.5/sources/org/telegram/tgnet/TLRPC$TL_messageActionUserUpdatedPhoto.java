package org.telegram.tgnet;

public class TLRPC$TL_messageActionUserUpdatedPhoto extends TLRPC$MessageAction {
    public static int constructor = 1431655761;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.newUserPhoto = TLRPC$UserProfilePhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.newUserPhoto.serializeToStream(stream);
    }
}
