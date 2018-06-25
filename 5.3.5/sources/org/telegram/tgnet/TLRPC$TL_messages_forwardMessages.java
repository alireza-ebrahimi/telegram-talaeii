package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_forwardMessages extends TLObject {
    public static int constructor = 1888354709;
    public boolean background;
    public int flags;
    public TLRPC$InputPeer from_peer;
    public boolean grouped;
    public ArrayList<Integer> id = new ArrayList();
    public ArrayList<Long> random_id = new ArrayList();
    public boolean silent;
    public TLRPC$InputPeer to_peer;
    public boolean with_my_score;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int a;
        stream.writeInt32(constructor);
        this.flags = this.silent ? this.flags | 32 : this.flags & -33;
        this.flags = this.background ? this.flags | 64 : this.flags & -65;
        this.flags = this.with_my_score ? this.flags | 256 : this.flags & -257;
        this.flags = this.grouped ? this.flags | 512 : this.flags & -513;
        stream.writeInt32(this.flags);
        this.from_peer.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.id.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            stream.writeInt32(((Integer) this.id.get(a)).intValue());
        }
        stream.writeInt32(481674261);
        count = this.random_id.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            stream.writeInt64(((Long) this.random_id.get(a)).longValue());
        }
        this.to_peer.serializeToStream(stream);
    }
}
