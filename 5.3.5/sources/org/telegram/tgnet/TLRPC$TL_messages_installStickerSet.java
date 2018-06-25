package org.telegram.tgnet;

public class TLRPC$TL_messages_installStickerSet extends TLObject {
    public static int constructor = -946871200;
    public boolean archived;
    public TLRPC$InputStickerSet stickerset;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_StickerSetInstallResult.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.stickerset.serializeToStream(stream);
        stream.writeBool(this.archived);
    }
}
