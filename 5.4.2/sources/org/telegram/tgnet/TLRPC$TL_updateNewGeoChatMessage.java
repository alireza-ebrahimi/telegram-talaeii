package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GeoChatMessage;

public class TLRPC$TL_updateNewGeoChatMessage extends TLRPC$Update {
    public static int constructor = 1516823543;
    public GeoChatMessage message;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.message = GeoChatMessage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.message.serializeToStream(abstractSerializedData);
    }
}
