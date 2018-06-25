package org.telegram.tgnet;

public class TLRPC$TL_messages_getSavedGifs extends TLObject {
    public static int constructor = -2084618926;
    public int hash;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_SavedGifs.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.hash);
    }
}
