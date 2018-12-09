package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ChannelMessagesFilter;
import org.telegram.tgnet.TLRPC.InputChannel;

public class TLRPC$TL_updates_getChannelDifference extends TLObject {
    public static int constructor = 51854712;
    public InputChannel channel;
    public ChannelMessagesFilter filter;
    public int flags;
    public boolean force;
    public int limit;
    public int pts;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$updates_ChannelDifference.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.force ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        this.channel.serializeToStream(abstractSerializedData);
        this.filter.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.pts);
        abstractSerializedData.writeInt32(this.limit);
    }
}
