package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageAction;
import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_messageService_old extends TLRPC$TL_messageService {
    public static int constructor = -1618124613;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt32(z);
        this.from_id = abstractSerializedData.readInt32(z);
        this.to_id = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.out = abstractSerializedData.readBool(z);
        this.unread = abstractSerializedData.readBool(z);
        this.flags |= 256;
        this.date = abstractSerializedData.readInt32(z);
        this.action = MessageAction.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.id);
        abstractSerializedData.writeInt32(this.from_id);
        this.to_id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeBool(this.out);
        abstractSerializedData.writeBool(this.unread);
        abstractSerializedData.writeInt32(this.date);
        this.action.serializeToStream(abstractSerializedData);
    }
}
