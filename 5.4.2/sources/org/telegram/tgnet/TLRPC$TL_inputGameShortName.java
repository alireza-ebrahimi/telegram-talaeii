package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputGame;
import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_inputGameShortName extends InputGame {
    public static int constructor = -1020139510;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.bot_id = InputUser.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.short_name = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.bot_id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.short_name);
    }
}
