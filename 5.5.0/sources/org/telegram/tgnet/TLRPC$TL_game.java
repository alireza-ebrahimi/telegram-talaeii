package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.Photo;

public class TLRPC$TL_game extends TLObject {
    public static int constructor = -1107729093;
    public long access_hash;
    public String description;
    public Document document;
    public int flags;
    public long id;
    public Photo photo;
    public String short_name;
    public String title;

    public static TLRPC$TL_game TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_game tLRPC$TL_game = new TLRPC$TL_game();
            tLRPC$TL_game.readParams(abstractSerializedData, z);
            return tLRPC$TL_game;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_game", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.id = abstractSerializedData.readInt64(z);
        this.access_hash = abstractSerializedData.readInt64(z);
        this.short_name = abstractSerializedData.readString(z);
        this.title = abstractSerializedData.readString(z);
        this.description = abstractSerializedData.readString(z);
        this.photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 1) != 0) {
            this.document = Document.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeInt64(this.access_hash);
        abstractSerializedData.writeString(this.short_name);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeString(this.description);
        this.photo.serializeToStream(abstractSerializedData);
        if ((this.flags & 1) != 0) {
            this.document.serializeToStream(abstractSerializedData);
        }
    }
}
