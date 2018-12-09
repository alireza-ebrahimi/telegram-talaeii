package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_phone_editGroupCallMember extends TLObject {
    public static int constructor = 1181064164;
    public TLRPC$TL_inputGroupCall call;
    public int flags;
    public boolean kicked;
    public boolean readonly;
    public byte[] streams;
    public InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.readonly ? this.flags | 1 : this.flags & -2;
        this.flags = this.kicked ? this.flags | 2 : this.flags & -3;
        abstractSerializedData.writeInt32(this.flags);
        this.call.serializeToStream(abstractSerializedData);
        this.user_id.serializeToStream(abstractSerializedData);
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeByteArray(this.streams);
        }
    }
}
