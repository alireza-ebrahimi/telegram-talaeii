package org.telegram.tgnet;

public class TLRPC$TL_pageBlockVideo extends TLRPC$PageBlock {
    public static int constructor = -640214938;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.autoplay = z;
        if ((this.flags & 2) == 0) {
            z2 = false;
        }
        this.loop = z2;
        this.video_id = stream.readInt64(exception);
        this.caption = TLRPC$RichText.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.autoplay ? this.flags | 1 : this.flags & -2;
        this.flags = this.loop ? this.flags | 2 : this.flags & -3;
        stream.writeInt32(this.flags);
        stream.writeInt64(this.video_id);
        this.caption.serializeToStream(stream);
    }
}
