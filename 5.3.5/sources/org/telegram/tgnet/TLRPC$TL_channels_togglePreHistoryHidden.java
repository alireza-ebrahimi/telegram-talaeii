package org.telegram.tgnet;

public class TLRPC$TL_channels_togglePreHistoryHidden extends TLObject {
    public static int constructor = -356796084;
    public TLRPC$InputChannel channel;
    public boolean enabled;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        stream.writeBool(this.enabled);
    }
}
