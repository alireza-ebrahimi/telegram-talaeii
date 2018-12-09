package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_photos_getUserPhotos extends TLObject {
    public static int constructor = -1848823128;
    public int limit;
    public long max_id;
    public int offset;
    public InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$photos_Photos.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.user_id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.offset);
        abstractSerializedData.writeInt64(this.max_id);
        abstractSerializedData.writeInt32(this.limit);
    }
}
