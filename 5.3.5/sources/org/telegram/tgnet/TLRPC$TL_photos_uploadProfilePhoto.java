package org.telegram.tgnet;

public class TLRPC$TL_photos_uploadProfilePhoto extends TLObject {
    public static int constructor = 1328726168;
    public TLRPC$InputFile file;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_photos_photo.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.file.serializeToStream(stream);
    }
}
