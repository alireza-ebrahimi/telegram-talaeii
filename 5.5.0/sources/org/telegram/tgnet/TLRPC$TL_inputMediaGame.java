package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputGame;
import org.telegram.tgnet.TLRPC.InputMedia;

public class TLRPC$TL_inputMediaGame extends InputMedia {
    public static int constructor = -750828557;
    public InputGame id;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = InputGame.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.id.serializeToStream(abstractSerializedData);
    }
}
