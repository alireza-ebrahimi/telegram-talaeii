package org.telegram.tgnet;

public class TLRPC$TL_updateShortSentMessage extends TLRPC$Updates {
    public static int constructor = 301019932;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.out = z;
        this.id = stream.readInt32(exception);
        this.pts = stream.readInt32(exception);
        this.pts_count = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        if ((this.flags & 512) != 0) {
            this.media = TLRPC$MessageMedia.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 128) != 0) {
            if (stream.readInt32(exception) == 481674261) {
                int count = stream.readInt32(exception);
                int a = 0;
                while (a < count) {
                    TLRPC$MessageEntity object = TLRPC$MessageEntity.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object != null) {
                        this.entities.add(object);
                        a++;
                    } else {
                        return;
                    }
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            }
        }
    }
}
