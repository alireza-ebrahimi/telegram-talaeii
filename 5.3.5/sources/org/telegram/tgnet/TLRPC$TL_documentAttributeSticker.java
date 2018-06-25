package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_documentAttributeSticker extends DocumentAttribute {
    public static int constructor = 1662637586;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.mask = (this.flags & 2) != 0;
        this.alt = stream.readString(exception);
        this.stickerset = TLRPC$InputStickerSet.TLdeserialize(stream, stream.readInt32(exception), exception);
        if ((this.flags & 1) != 0) {
            this.mask_coords = TLRPC$TL_maskCoords.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.mask ? this.flags | 2 : this.flags & -3;
        stream.writeInt32(this.flags);
        stream.writeString(this.alt);
        this.stickerset.serializeToStream(stream);
        if ((this.flags & 1) != 0) {
            this.mask_coords.serializeToStream(stream);
        }
    }
}
