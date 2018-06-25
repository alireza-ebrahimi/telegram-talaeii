package org.telegram.tgnet;

public class TLRPC$TL_help_getAppChangelog extends TLObject {
    public static int constructor = -1877938321;
    public String prev_app_version;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.prev_app_version);
    }
}
