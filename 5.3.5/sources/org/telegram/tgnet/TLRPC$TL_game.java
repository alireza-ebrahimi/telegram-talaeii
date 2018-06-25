package org.telegram.tgnet;

public class TLRPC$TL_game extends TLObject {
    public static int constructor = -1107729093;
    public long access_hash;
    public String description;
    public TLRPC$Document document;
    public int flags;
    public long id;
    public TLRPC$Photo photo;
    public String short_name;
    public String title;

    public static TLRPC$TL_game TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_game result = new TLRPC$TL_game();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_game", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        this.short_name = stream.readString(exception);
        this.title = stream.readString(exception);
        this.description = stream.readString(exception);
        this.photo = TLRPC$Photo.TLdeserialize(stream, stream.readInt32(exception), exception);
        if ((this.flags & 1) != 0) {
            this.document = TLRPC$Document.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeString(this.short_name);
        stream.writeString(this.title);
        stream.writeString(this.description);
        this.photo.serializeToStream(stream);
        if ((this.flags & 1) != 0) {
            this.document.serializeToStream(stream);
        }
    }
}
