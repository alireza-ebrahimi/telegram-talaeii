package org.telegram.tgnet;

public class TLRPC$TL_messages_getAttachedStickers extends TLObject {
    public static int constructor = -866424884;
    public TLRPC$InputStickeredMedia media;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Vector vector = new TLRPC$Vector();
        int size = stream.readInt32(exception);
        for (int a = 0; a < size; a++) {
            TLRPC$StickerSetCovered object = TLRPC$StickerSetCovered.TLdeserialize(stream, stream.readInt32(exception), exception);
            if (object == null) {
                break;
            }
            vector.objects.add(object);
        }
        return vector;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.media.serializeToStream(stream);
    }
}
