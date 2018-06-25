package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GroupCall;

public class TLRPC$TL_groupCall extends GroupCall {
    public static int constructor = 177149476;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.id = abstractSerializedData.readInt64(z);
        this.access_hash = abstractSerializedData.readInt64(z);
        if ((this.flags & 1) != 0) {
            this.channel_id = abstractSerializedData.readInt32(z);
        }
        this.admin_id = abstractSerializedData.readInt32(z);
        if ((this.flags & 2) != 0) {
            this.encryption_key = abstractSerializedData.readByteArray(z);
        }
        this.key_fingerprint = abstractSerializedData.readInt64(z);
        this.protocol = TLRPC$TL_phoneCallProtocol.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.connection = TLRPC$TL_groupCallConnection.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.reflector_group_tag = abstractSerializedData.readByteArray(z);
        this.reflector_self_tag = abstractSerializedData.readByteArray(z);
        this.reflector_self_secret = abstractSerializedData.readByteArray(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeInt64(this.access_hash);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.channel_id);
        }
        abstractSerializedData.writeInt32(this.admin_id);
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeByteArray(this.encryption_key);
        }
        abstractSerializedData.writeInt64(this.key_fingerprint);
        this.protocol.serializeToStream(abstractSerializedData);
        this.connection.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeByteArray(this.reflector_group_tag);
        abstractSerializedData.writeByteArray(this.reflector_self_tag);
        abstractSerializedData.writeByteArray(this.reflector_self_secret);
    }
}
