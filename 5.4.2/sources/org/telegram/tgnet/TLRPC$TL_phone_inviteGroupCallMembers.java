package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Bool;
import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_phone_inviteGroupCallMembers extends TLObject {
    public static int constructor = -862804260;
    public TLRPC$TL_inputGroupCall call;
    public int flags;
    public boolean uninvite;
    public ArrayList<InputUser> users = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.uninvite ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        this.call.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(481674261);
        int size = this.users.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((InputUser) this.users.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
