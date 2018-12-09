package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Bool;
import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_messages_setInlineGameScore extends TLObject {
    public static int constructor = 363700068;
    public boolean edit_message;
    public int flags;
    public boolean force;
    public TLRPC$TL_inputBotInlineMessageID id;
    public int score;
    public InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.edit_message ? this.flags | 1 : this.flags & -2;
        this.flags = this.force ? this.flags | 2 : this.flags & -3;
        abstractSerializedData.writeInt32(this.flags);
        this.id.serializeToStream(abstractSerializedData);
        this.user_id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.score);
    }
}
