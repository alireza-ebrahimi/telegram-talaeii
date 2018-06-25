package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_editMessage extends TLObject {
    public static int constructor = 97630429;
    public ArrayList<TLRPC$MessageEntity> entities = new ArrayList();
    public int flags;
    public TLRPC$InputGeoPoint geo_point;
    public int id;
    public String message;
    public boolean no_webpage;
    public TLRPC$InputPeer peer;
    public TLRPC$ReplyMarkup reply_markup;
    public boolean stop_geo_live;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        stream.writeInt32(constructor);
        this.flags = this.no_webpage ? this.flags | 2 : this.flags & -3;
        if (this.stop_geo_live) {
            i = this.flags | 4096;
        } else {
            i = this.flags & -4097;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.id);
        if ((this.flags & 2048) != 0) {
            stream.writeString(this.message);
        }
        if ((this.flags & 4) != 0) {
            this.reply_markup.serializeToStream(stream);
        }
        if ((this.flags & 8) != 0) {
            stream.writeInt32(481674261);
            int count = this.entities.size();
            stream.writeInt32(count);
            for (int a = 0; a < count; a++) {
                ((TLRPC$MessageEntity) this.entities.get(a)).serializeToStream(stream);
            }
        }
        if ((this.flags & 8192) != 0) {
            this.geo_point.serializeToStream(stream);
        }
    }
}
