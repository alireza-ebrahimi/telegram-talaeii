package org.telegram.tgnet;

public class TLRPC$TL_photos_updateProfilePhoto extends TLObject {
    public static int constructor = -256159406;
    public TLRPC$InputPhoto id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$UserProfilePhoto.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.id.serializeToStream(stream);
    }
}
