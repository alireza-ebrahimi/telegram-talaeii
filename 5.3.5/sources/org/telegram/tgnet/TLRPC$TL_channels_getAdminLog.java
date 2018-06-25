package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_channels_getAdminLog extends TLObject {
    public static int constructor = 870184064;
    public ArrayList<TLRPC$InputUser> admins = new ArrayList();
    public TLRPC$InputChannel channel;
    public TLRPC$TL_channelAdminLogEventsFilter events_filter;
    public int flags;
    public int limit;
    public long max_id;
    public long min_id;
    /* renamed from: q */
    public String f79q;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_channels_adminLogResults.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        this.channel.serializeToStream(stream);
        stream.writeString(this.f79q);
        if ((this.flags & 1) != 0) {
            this.events_filter.serializeToStream(stream);
        }
        if ((this.flags & 2) != 0) {
            stream.writeInt32(481674261);
            int count = this.admins.size();
            stream.writeInt32(count);
            for (int a = 0; a < count; a++) {
                ((TLRPC$InputUser) this.admins.get(a)).serializeToStream(stream);
            }
        }
        stream.writeInt64(this.max_id);
        stream.writeInt64(this.min_id);
        stream.writeInt32(this.limit);
    }
}
