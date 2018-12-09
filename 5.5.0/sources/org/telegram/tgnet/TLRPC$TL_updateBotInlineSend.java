package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GeoPoint;

public class TLRPC$TL_updateBotInlineSend extends TLRPC$Update {
    public static int constructor = 239663460;
    public String id;
    public TLRPC$TL_inputBotInlineMessageID msg_id;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.user_id = abstractSerializedData.readInt32(z);
        this.query = abstractSerializedData.readString(z);
        if ((this.flags & 1) != 0) {
            this.geo = GeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        this.id = abstractSerializedData.readString(z);
        if ((this.flags & 2) != 0) {
            this.msg_id = TLRPC$TL_inputBotInlineMessageID.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.user_id);
        abstractSerializedData.writeString(this.query);
        if ((this.flags & 1) != 0) {
            this.geo.serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeString(this.id);
        if ((this.flags & 2) != 0) {
            this.msg_id.serializeToStream(abstractSerializedData);
        }
    }
}
