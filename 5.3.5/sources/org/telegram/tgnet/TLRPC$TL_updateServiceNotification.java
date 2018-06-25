package org.telegram.tgnet;

public class TLRPC$TL_updateServiceNotification extends TLRPC$Update {
    public static int constructor = -337352679;
    public String message;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.popup = z;
        if ((this.flags & 2) != 0) {
            this.inbox_date = stream.readInt32(exception);
        }
        this.type = stream.readString(exception);
        this.message = stream.readString(exception);
        this.media = TLRPC$MessageMedia.TLdeserialize(stream, stream.readInt32(exception), exception);
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

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        stream.writeInt32(constructor);
        if (this.popup) {
            i = this.flags | 1;
        } else {
            i = this.flags & -2;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
        if ((this.flags & 2) != 0) {
            stream.writeInt32(this.inbox_date);
        }
        stream.writeString(this.type);
        stream.writeString(this.message);
        this.media.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.entities.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$MessageEntity) this.entities.get(a)).serializeToStream(stream);
        }
    }
}
