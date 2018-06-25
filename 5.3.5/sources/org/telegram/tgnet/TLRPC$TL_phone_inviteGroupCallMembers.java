package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_phone_inviteGroupCallMembers extends TLObject {
    public static int constructor = -862804260;
    public TLRPC$TL_inputGroupCall call;
    public int flags;
    public boolean uninvite;
    public ArrayList<TLRPC$InputUser> users = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.uninvite ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        this.call.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.users.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$InputUser) this.users.get(a)).serializeToStream(stream);
        }
    }
}
