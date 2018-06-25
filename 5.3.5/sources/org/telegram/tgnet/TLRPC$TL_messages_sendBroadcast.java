package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_sendBroadcast extends TLObject {
    public static int constructor = -1082919718;
    public ArrayList<TLRPC$InputUser> contacts = new ArrayList();
    public TLRPC$InputMedia media;
    public String message;
    public ArrayList<Long> random_id = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int a;
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.contacts.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$InputUser) this.contacts.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.random_id.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            stream.writeInt64(((Long) this.random_id.get(a)).longValue());
        }
        stream.writeString(this.message);
        this.media.serializeToStream(stream);
    }
}
