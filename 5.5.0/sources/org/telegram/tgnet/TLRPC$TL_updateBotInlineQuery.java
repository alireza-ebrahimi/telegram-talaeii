package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GeoPoint;

public class TLRPC$TL_updateBotInlineQuery extends TLRPC$Update {
    public static int constructor = 1417832080;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.query_id = abstractSerializedData.readInt64(z);
        this.user_id = abstractSerializedData.readInt32(z);
        this.query = abstractSerializedData.readString(z);
        if ((this.flags & 1) != 0) {
            this.geo = GeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        this.offset = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.query_id);
        abstractSerializedData.writeInt32(this.user_id);
        abstractSerializedData.writeString(this.query);
        if ((this.flags & 1) != 0) {
            this.geo.serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeString(this.offset);
    }
}
