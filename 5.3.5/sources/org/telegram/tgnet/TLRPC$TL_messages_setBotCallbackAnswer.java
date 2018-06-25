package org.telegram.tgnet;

public class TLRPC$TL_messages_setBotCallbackAnswer extends TLObject {
    public static int constructor = -712043766;
    public boolean alert;
    public int cache_time;
    public int flags;
    public String message;
    public long query_id;
    public String url;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.alert ? this.flags | 2 : this.flags & -3;
        stream.writeInt32(this.flags);
        stream.writeInt64(this.query_id);
        if ((this.flags & 1) != 0) {
            stream.writeString(this.message);
        }
        if ((this.flags & 4) != 0) {
            stream.writeString(this.url);
        }
        stream.writeInt32(this.cache_time);
    }
}
