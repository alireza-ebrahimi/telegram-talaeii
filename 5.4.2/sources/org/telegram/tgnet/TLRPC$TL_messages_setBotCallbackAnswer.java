package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Bool;

public class TLRPC$TL_messages_setBotCallbackAnswer extends TLObject {
    public static int constructor = -712043766;
    public boolean alert;
    public int cache_time;
    public int flags;
    public String message;
    public long query_id;
    public String url;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.alert ? this.flags | 2 : this.flags & -3;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.query_id);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeString(this.message);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeString(this.url);
        }
        abstractSerializedData.writeInt32(this.cache_time);
    }
}
