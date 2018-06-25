package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_messages_forwardMessages extends TLObject {
    public static int constructor = 1888354709;
    public boolean background;
    public int flags;
    public InputPeer from_peer;
    public boolean grouped;
    public ArrayList<Integer> id = new ArrayList();
    public ArrayList<Long> random_id = new ArrayList();
    public boolean silent;
    public InputPeer to_peer;
    public boolean with_my_score;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        int i;
        int i2 = 0;
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.silent ? this.flags | 32 : this.flags & -33;
        this.flags = this.background ? this.flags | 64 : this.flags & -65;
        this.flags = this.with_my_score ? this.flags | 256 : this.flags & -257;
        this.flags = this.grouped ? this.flags | 512 : this.flags & -513;
        abstractSerializedData.writeInt32(this.flags);
        this.from_peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(481674261);
        int size = this.id.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            abstractSerializedData.writeInt32(((Integer) this.id.get(i)).intValue());
        }
        abstractSerializedData.writeInt32(481674261);
        i = this.random_id.size();
        abstractSerializedData.writeInt32(i);
        while (i2 < i) {
            abstractSerializedData.writeInt64(((Long) this.random_id.get(i2)).longValue());
            i2++;
        }
        this.to_peer.serializeToStream(abstractSerializedData);
    }
}
